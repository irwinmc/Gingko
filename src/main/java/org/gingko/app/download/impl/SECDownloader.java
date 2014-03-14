package org.gingko.app.download.impl;

import org.gingko.app.download.AbstractDownloader;
import org.gingko.app.vo.SECIdxItem;
import org.gingko.config.SECProperties;
import org.gingko.util.DateUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * @author Kyia
 *         <p/>
 *         TODO: 单线程同步下载，后期改为多线程异步处理
 */
public class SECDownloader extends AbstractDownloader {

	private static final Logger LOG = LoggerFactory.getLogger(SECDownloader.class);

	/**
	 * 下载ESC Master Idx文件以供解析，idx下载地址生成规则为替换日期，针对SEC网站使用的规则
	 * TODO: 规则匹配考虑后期写入配置
	 *
	 * @param time
	 */
	public String downloadMasterIdx(long time) {
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
	 * 下载txt文件
	 *
	 * @param item
	 * @return
	 */
	public String downloadTxt(SECIdxItem item) {
		// 获取文件名
		String fileName = item.getFileName();
		String date = item.getDateField();
		String url = SECProperties.base + fileName;
		String dst = PathUtils.getWebRootPath() + SECProperties.dataTxtDst + date + File.separator
				+ fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());

		try {
			if (downloadFile(url, dst)) {
				LOG.info("Download txt file completed. Save it as {}", dst);
			}
		} catch (Exception e) {
			LOG.error("Exception in download sec txt file {}.", url, e);
		}

		return dst;
	}

	/**
	 * 下载html索引文件
	 *
	 * @param item
	 * @return
	 */
	public String downloadIndexHtm(SECIdxItem item) {
		// 获取文件名
		String fileName = item.getFileName();
		String date = item.getDateField();

		String htmIndexFileName = fileName
				.substring(fileName.lastIndexOf("/") + 1, fileName.length())
				.replace(".txt", "-index.htm");

		String url = SECProperties.base + fileName.replace("-", "").replace(".txt", "") + "/" + htmIndexFileName;
		String dst = PathUtils.getWebRootPath() + SECProperties.dataHtmlIndexDst + date + File.separator + htmIndexFileName;

		try {
			if (downloadFile(url, dst)) {
				LOG.info("Download htm index file completed. Save it as {}", dst);
			}
		} catch (Exception e) {
			LOG.error("Exception in download sec htm index file {}.", url, e);
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

		String date = item.getDateField();
		String dst = PathUtils.getWebRootPath() + SECProperties.dataXlsDst + date + "/Financial_Report_" + item.getCik() + ".xls";

		try {
			if (downloadFile(url, dst)) {
				LOG.info("Download excel file completed. Save it as {}", dst);
			}
		} catch (Exception e) {
			LOG.error("Exception in download sec excel file {}.", url, e);
		}

		return dst;
	}

	/**
	 * 下载txt文件
	 *
	 * @param list
	 * @return
	 */
	public void multiThreadeDownloadIndexHtm(List<SECIdxItem> list) {
		String[] urls = new String[list.size()];
		String[] dsts = new String[list.size()];

		// 组成下载路径
		for (int i = 0; i < list.size(); i++) {
			SECIdxItem item = list.get(i);

			String fileName = item.getFileName();
			String date = item.getDateField();

			String htmIndexFileName = fileName
					.substring(fileName.lastIndexOf("/") + 1, fileName.length())
					.replace(".txt", "-index.htm");

			String url = SECProperties.base + fileName.replace("-", "").replace(".txt", "") + "/" + htmIndexFileName;
			String dst = PathUtils.getWebRootPath() + SECProperties.dataHtmlIndexDst + date + File.separator + htmIndexFileName;

			urls[i] = url;
			dsts[i] = dst;
		}

		try {
			multiThreadedDownloadFile(urls, dsts);
		} catch (Exception e) {
			LOG.error("Exception in download sec txt file {}.", e);
		}
	}
}
