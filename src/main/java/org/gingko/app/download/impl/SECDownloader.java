package org.gingko.app.download.impl;

import org.gingko.app.download.AbstractDownloader;
import org.gingko.app.vo.SECIdxItem;
import org.gingko.config.SECProperties;
import org.gingko.util.DateUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 *         <p/>
 *         TODO: 单线程同步下载，后期改为多线程异步处理
 */
public class SECDownloader extends AbstractDownloader {

	private static final Logger LOG = LoggerFactory.getLogger(SECDownloader.class);

	// 临时方法，获取前一天的目前时间点
	private long time = System.currentTimeMillis() - 2 * DateUtils.MILLISECOND_PER_DAY;

	/**
	 * 下载ESC Master Idx文件以供解析，idx下载地址生成规则为替换日期，针对SEC网站使用的规则
	 * TODO: 规则匹配考虑后期写入配置
	 */
	public String downloadMasterIdx() {
		// 获取指定格式的日期
		String date = DateUtils.formatTime(time, SECProperties.masterIdxDateFormat);
		// 获取文件名
		String fileName = SECProperties.masterIdxFileName.replace("${date}", date);
		// 下载URL连接
		String url = SECProperties.masterIdxUrl + fileName;
		// 本地文件保存位置
		String dst = PathUtils.getWebRootPath() + SECProperties.masterIdxDst + fileName;

		// 下载逻辑
		try {
			if (downloadFile(url, dst)) {
				LOG.info("Download master idx file completed. Save it as {}", dst);
			}
		} catch (Exception e) {
			LOG.error("Exception in download sec master idx file.", e);
		}

		return dst;
	}

	/**
	 * 下载Excel文件
	 *
	 * @param item
	 * @return
	 */
	public String downloadXls(SECIdxItem item) {
		// 获取文件名
		String url = SECProperties.base + item.getFileName()
				.replace("-", "")
				.replace(".txt", "/Financial_Report.xls");

		String date = DateUtils.formatTime(time, SECProperties.masterIdxDateFormat);
		String dst = PathUtils.getWebRootPath() + SECProperties.xlsDst + date + "/Financial_Report_" + item.getCik() + ".xls";

//		System.out.println(url);
//		System.out.println(dst);

		try {
			if (downloadFile(url, dst)) {
				LOG.info("Download excel file completed. Save it as {}", dst);
			}
		} catch (Exception e) {
			LOG.error("Exception in download sec excel file {}.", url, e);
		}

		return "";
	}
}
