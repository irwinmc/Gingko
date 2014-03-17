package org.gingko.app.parse;

import org.gingko.app.vo.SECHtmlIdxItem;
import org.gingko.config.SECProperties;
import org.gingko.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 *         <p/>
 *         文件保存位置 webroot/data/sec/html/index/master.20140310.idx
 *         这部分解析html格式的Index文件，里面包含了一个重要的索引表格，
 *         模拟人操作的方式，即获取相应类型的表单数据
 *         <div>
 *         <div>
 *         <p>Document Format Files</p>
 *         <table class="tableFile" summary="Document Format Files">
 *         <tr>
 *         <th scope="col" style="width: 5%;"><acronym title="Sequence Number">Seq</acronym></th>
 *         <th scope="col" style="width: 40%;">Description</th>
 *         <th scope="col" style="width: 20%;">Document</th>
 *         <th scope="col" style="width: 10%;">Type</th>
 *         <th scope="col">Size</th>
 *         </tr>
 *         <tr>
 *         <td scope="row">1</td>
 *         <td scope="row">FORM 6-K</td>
 *         <td scope="row"><a href="/Archives/edgar/data/1455886/000106299314001292/form6k.htm">form6k.htm</a></td>
 *         <td scope="row">6-K</td>
 *         <td scope="row">10380</td>
 *         </tr>
 *         ...
 */
public class SECHtmlIndexParser {

	private static final Logger LOG = LoggerFactory.getLogger(SECHtmlIndexParser.class);

	// 表单的属性中包含有以下属性，并且值为如下
	// TODO: 这里为表单的定位条件，需要考虑在外部配置
	private String tableCssQuery = "table[summary=Document Format Files]";

	// 数据列数
	private int parseAttrNum = 5;

	/**
	 * 解析ESC Html Idx文件
	 * 这里的解析也不够灵活，如果变动会导致无法解析，但是目前这个格式和Master Idx一样是该网站的固定格式
	 * 改动的几率较小
	 * <p/>
	 * 这里产生的是内存中间对象，具体的解析文件需要输入相应的配置规则进行定义，中间对象不做持久化
	 *
	 * @param dst
	 */
	public List<SECHtmlIdxItem> parseHtmlIdx(String dst) {
		List<SECHtmlIdxItem> list = new ArrayList<SECHtmlIdxItem>();

		File file = new File(dst);
		try {
			Document doc = Jsoup.parse(file, "UTF-8", SECProperties.base);
			Element table = doc.select(tableCssQuery).first();
			// 检查条件是否匹配
			if (table != null) {
				// 以tr每行作为一个单独对象判断
				for (Element tr : table.select("tr")) {
					// HTML idx文件在定义的时候有非常规范的格式，他的列名是由th定义的
					// 因此可以直接跳过th部分直接解析td，后期需要考虑如何动态配置列名th
					Elements tds = tr.select("td");
					if (tds.size() >= parseAttrNum) {
						// 开始解析对象属性
						String strSeq = tds.get(0).text();
						if (!StringUtils.isNumeric(strSeq)) {
							continue;
						}

						// 新建对象
						int seq = Integer.parseInt(tds.get(0).text());
						String description = tds.get(1).text();
						String document = tds.get(2).text();
						String type = tds.get(3).text();
						int size = Integer.parseInt(tds.get(4).text());
						String anchor = tds.get(2).select("a[href]").first().attr("href");
						SECHtmlIdxItem item = new SECHtmlIdxItem(seq, description, document, type, size, anchor);
						list.add(item);
					}
				}
			} else {
				// 表格不存在！需要修改匹配规则
				LOG.warn("DFF Table not detected, please check the conditions!");
			}
		} catch (IOException e) {
			LOG.error("IOException in parse html idx file.", e);
		}

		return list;
	}
}
