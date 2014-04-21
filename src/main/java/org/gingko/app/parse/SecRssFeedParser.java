package org.gingko.app.parse;

import org.gingko.app.download.impl.SecRssDownloader;
import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.config.SecProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hy
 *         <p/>
 *         <entry>
 *         <title>5/A - Giarraputo Jeffrey (0001603105) (Reporting)</title>
 *         <link  rel="alternate" type="text/html" href="http://www.sec.gov/Archives/edgar/data/1603105/000107997414000180/0001079974-14-000180-index.htm" />
 *         <summary type="html"> &lt;b&gt;Filed:&lt;/b&gt; 2014-03-28 &lt;b&gt;AccNo:&lt;/b&gt; 0001079974-14-000180 &lt;b&gt;Size:&lt;/b&gt; 5 KB </summary>
 *         <updated>2014-03-28T21:54:30-04:00</updated>
 *         <category scheme="http://www.sec.gov/" label="form type" term="5/A"/>
 *         <id>urn:tag:sec.gov,2008:accession-number=0001079974-14-000180</id>
 *         </entry>
 */
public class SecRssFeedParser {

	private static final Logger LOG = LoggerFactory.getLogger(SecRssFeedParser.class);

	private static final int SPLITNUM = 39;// 截去http://www.sec.gov/Archives/edgar/data/字串,个数为38

	private static final String fileSeperator = "/";

	/**
	 * 连接符“-”，以此为左标识位，截取得到公司名，例下: 4 - RENTRAK CORP (0000800458) (Issuer)
	 */
	private static final String prefix = "-";

	/**
	 * 做括号“（”，以此为右标识位，截取得到公司名
	 */
	private static final String suffix = "(";

	private static final String SUMMARY = "Document Format Files";

	private static final String file = "edgar/data/";

	/**
	 * 解析Rss Feed的内容,其主要由以下格式的entry构成，根据cik和formtype筛选需要的link节点中的url
	 *
	 * @param content 设定文件
	 */
	public List<SecIdx> parseRssContent(String content) {
		List<SecIdx> list = new ArrayList<SecIdx>();
		Document doc = Jsoup.parse(content);
		// 检查条件是否匹配
		if (doc != null) {
			// 以entry作为一个单独对象判断
			for (Element entry : doc.select("entry")) {
				// 循环解析每个entry
				SecIdx sexIdx = new SecIdx();
				Elements category = entry.select("category");

				// entry中term属性即为fromtype
				Elements link = entry.select("link");
				String href = link.attr("href");

				// 从连接中截取出cik码
				String cik = getCikFromHref(href);

				// 获取filename和siid
				String fillingUrlSuffix = href.substring(href.lastIndexOf(fileSeperator) + 1);
				String siidSuffix = fillingUrlSuffix.replace("-index.htm", ".txt");
				String fileName = file + cik + fileSeperator + siidSuffix;
				String siid = cik + prefix + siidSuffix.replace(".txt", "");

				//先设置cik和type，用来判断是否为需要的数据
				sexIdx.setCik(cik);
				sexIdx.setFormType(category.attr("term"));
				if (SecFilter.INSTANCE.doFilter(sexIdx)) {
					// 获取公司名
					Elements title = entry.select("title");
					String companyName = title.text();
					String realComName = getCompanyName(companyName);

					//设置值
					sexIdx.setCompanyName(realComName);
					sexIdx.setFillingHtmlUrl(href);
					sexIdx.setFileName(fileName);
					sexIdx.setSiid(siid);
					sexIdx.setDateField(new SimpleDateFormat("yyyyMMdd").format(new Date()));
					sexIdx.setDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                    sexIdx.setAmount(0);
//					sexIdx.setState(0);
//					sexIdx.setOperator("");
					list.add(sexIdx);
				}
			}
		} else {
			// Rss 源获取失败
			LOG.warn("Rss Feed is invalid !");
		}
		return list;
	}

	/**
	 * 解析-index.htm文件内容 主题格式为：
	 * <p/>
	 * Seq    Description    Document                      Type      Size
	 * 1      10-Q           schl2014228-10qlive.htm       10-Q      1342948
	 * 2      EXHIBIT        schl-ex102_2014228xq3.htm     EX-10.2   416012
	 * <p/>
	 * 目的是获取符合要求的Type的Document列的htm的Url,以下载用
	 *
	 * @param secIdx
	 */
	public SecHtmlIdx processDetailHtm(SecIdx secIdx) {
		// 先跟据地址下载
		String fillingDetailUrl = secIdx.getFillingHtmlUrl();
		String detailContent = new SecRssDownloader().downloadRssFile(fillingDetailUrl);

		// 再解析
		Document doc = Jsoup.parse(detailContent);

		// SecHtmlIdx对象
		SecHtmlIdx secHtm = new SecHtmlIdx();
		if (doc != null) {
			for (Element table : doc.select("table")) {
				String summary = table.attr("summary");
				if (summary.equals(SUMMARY)) {
					for (Element tr : table.select("tr")) {
						Elements tds = tr.select("td");
						for (Element td : tds) {
							if (td.text() != null) {
								for (Element a : td.select("a")) {
									if (a.text() != null) {
										String suffixAnchor = a.attr("href");
										String anchor = SecProperties.base + suffixAnchor.substring(1);
										String type = tds.get(3).text();
										if (type.equals(secIdx.getFormType())) {
											// 获取SecHtmlIdx对象值
											int seq = Integer.parseInt(tds.get(0).text());
											String description = tds.get(1).text();
											String document = tds.get(2).text();
											int size = Integer.parseInt(tds.get(4).text());
											String localFile = generateLocalFile(secIdx.getSiid(), document);

											// 设置对象
											secHtm.setAnchor(anchor);
											secHtm.setDate(secIdx.getDate());
											secHtm.setDescription(description);
											secHtm.setDocument(document);
											secHtm.setLocalFile(localFile);
											secHtm.setSeq(seq);
											secHtm.setSiid(secIdx.getSiid());
											secHtm.setSize(size);
											secHtm.setType(type);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return secHtm;
	}

	/**
	 * 从连接中得到cik码
	 *
	 * @param href
	 */
	public String getCikFromHref(String href) {
		href = href.substring(SPLITNUM, href.length());
		href = href.substring(0, href.indexOf(fileSeperator));
		return href;
	}

	/**
	 * 生成唯一的本地文件
	 *
	 * @param siid
	 * @param document
	 * @return String
	 */
	private String generateLocalFile(String siid, String document) {
		return siid + "-" + document;
	}

	/**
	 * 得到公司的真实名称
	 *
	 * @param companyName
	 */
	private String getCompanyName(String companyName) {
		int first = companyName.indexOf(prefix);
		int second = companyName.indexOf(suffix);
		String realComName = companyName.substring(first + 1, second);

		//判断是否存在第二个suffix（-）
		int num = realComName.indexOf(prefix);
		if (num != -1) {
			realComName = realComName.substring(num + 1);
		}
		return realComName;
	}

}
