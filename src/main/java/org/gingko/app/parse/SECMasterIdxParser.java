package org.gingko.app.parse;

import org.gingko.app.persist.domain.SecIdx;
import org.gingko.config.SecProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 *         <p/>
 *         文件保存位置 webroot/data/sec/daily-index/master.20140310.idx
 *         <p/>
 *         例子：
 *         Description:           Daily Index of EDGAR Dissemination Feed
 *         Last Data Received:    Mar 10, 2014
 *         Comments:              webmaster@sec.gov
 *         Anonymous FTP:         ftp://ftp.sec.gov/edgar/
 *         <p/>
 *         CIK|Company Name|Form Type|Date Filed|File Name
 *         --------------------------------------------------------------------------------
 *         1000209|MEDALLION FINANCIAL CORP|4|20140310|edgar/data/1000209/0001209191-14-018885.txt
 *         1000229|CORE LABORATORIES N V|4|20140310|edgar/data/1000229/0001000229-14-000037.txt
 *         1000232|KENTUCKY BANCSHARES INC /KY/|4|20140310|edgar/data/1000232/0001179110-14-004837.txt
 *         1000275|ROYAL BANK OF CANADA|FWP|20140310|edgar/data/1000275/0001214659-14-001858.txt
 *         <p/>
 *         有部分配置在代码内部，视数据格式而定
 *         考虑配置放在外部
 */
public class SecMasterIdxParser {

	private static final Logger LOG = LoggerFactory.getLogger(SecMasterIdxParser.class);

	// 从第八行来解析，如果这个网站格式变更，如何考虑改变规则
	// TODO: 配置亦或是如何制定解析规则
	private int parseStartLine = 8;

	// 分隔符
	private String parseSplitRegex = "\\|";

	// 数据列数
	private int parseAttrNum = 5;

	/**
	 * 解析ESC Master Idx文件
	 *
	 * @param dst
	 * @param date
	 */
	public List<SecIdx> parseMasterIdx(String dst, String date) {
		List<SecIdx> list = new ArrayList<SecIdx>();

		File file = new File(dst);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));

			int n = 1;
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0 && n >= parseStartLine) {
					String[] strArray = line.split(parseSplitRegex);
					if (strArray.length >= parseAttrNum) {
						// 默认属性
						String cik = strArray[0];
						String companyName = strArray[1];
						String formType = strArray[2];
						String dateField = strArray[3];
						String fileName = strArray[4];

						// 附加属性
						String fillingHtmlUrl = generateFillingHtmlUrl(fileName);
						String siid = generateSiid(cik, fileName);

						// 新建对象
						SecIdx item = new SecIdx();
						item.setCik(cik);
						item.setCompanyName(companyName);
						item.setFormType(formType);
						item.setDateField(dateField);
						item.setFileName(fileName);
						item.setFillingHtmlUrl(fillingHtmlUrl);
						item.setSiid(siid);
						item.setDate(date);
                        item.setAmount(0);
						list.add(item);
					}
				}
				n++;
			}
		} catch (FileNotFoundException e) {
			LOG.error("FileNotFoundException in parse master idx file.", e);
		} catch (IOException e) {
			LOG.error("IOException in parse master idx file.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error("IOException in parse master idx file.", e);
				}
			}
		}

		return list;
	}

	/**
	 * 生成完整的Fill Document Html地址
	 *
	 * @param fileName
	 * @return
	 */
	private String generateFillingHtmlUrl(String fileName) {
		String htmIndexFileName = fileName
				.substring(fileName.lastIndexOf("/") + 1)
				.replace(".txt", "-index.htm");
		String url = SecProperties.baseArchives + fileName.replace("-", "").replace(".txt", "") + "/" + htmIndexFileName;
		return url;
	}

	/**
	 * 只取出文件名
	 *
	 * @param cik
	 * @param fileName
	 * @return
	 */
	private String generateSiid(String cik, String fileName) {
		return cik + "-" + fileName
				.substring(fileName.lastIndexOf("/") + 1)
				.replace(".txt", "");
	}
}
