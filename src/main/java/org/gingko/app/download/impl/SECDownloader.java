package org.gingko.app.download.impl;

import org.gingko.app.download.AbstractDownloader;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.config.SecProperties;
import org.gingko.util.DateUtils;
import org.gingko.util.FileUtils;
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
public class SecDownloader extends AbstractDownloader {

	private static final Logger LOG = LoggerFactory.getLogger(SecDownloader.class);

	/**
	 * 下载ESC Master Idx文件以供解析，idx下载地址生成规则为替换日期，针对SEC网站使用的规则
	 * TODO: 规则匹配考虑后期写入配置
	 *
	 * @param time
	 */
	public String downloadMasterIdx(long time) {
		// 获取指定格式的日期
		String date = DateUtils.formatTime(time, SecProperties.masterIdxDateFormat);
		// 获取文件名
		String fileName = SecProperties.masterIdxFileName.replace("${date}", date);
		// 下载URL连接
		String url = SecProperties.masterIdxUrl + fileName;
		// 本地文件保存位置
		String dst = PathUtils.getWebRootPath() + SecProperties.masterIdxDst + fileName;

		// TODO: 检查文件是否存在
//		File file = new File(dst);
//		if (file.exists() && !file.isDirectory() && file.length() >= 0) {
//			LOG.info("Daily master index file exist");
//		}

		try {
			// 下载逻辑
			downloadFile(url, dst);

			LOG.info("Download daily master index file successful.");
		} catch (Exception e) {
			LOG.error("Exception in download sec master idx file.", e);
		}

		return dst;
	}

	/**
	 * 批量下载Index Html文件
	 *
	 * @param list
	 * @param dateDir
	 * @return
	 */
	public void multiThreadDownloadIndexHtm(List<SecIdx> list, String dateDir) {
		String[] urls = new String[list.size()];
		String[] dsts = new String[list.size()];

		// 检查下载路径文件夹存在与否
		String root = PathUtils.getWebRootPath() + SecProperties.dataHtmlIndexDst + dateDir;
		if (FileUtils.mkdir(root).equals("")) {
			return;
		}

		// 组成下载路径
		for (int i = 0; i < list.size(); i++) {
			SecIdx item = list.get(i);

			String url = item.getFillingHtmlUrl();
			String dst = root + File.separator + item.getLocalFile();

			urls[i] = url;
			dsts[i] = dst;
		}

		try {
			LOG.info("Start download html index files, total {} files.", urls.length);

			// 开始多线程下载
			multiThreadedDownloadFile(urls, dsts);

			LOG.info("Download daily html index files successful.");
		} catch (Exception e) {
			LOG.error("Exception in download html index files {}.", e);
		}
	}

	/**
	 * 下载需要分析的真正数据文件
	 *
	 * @param list
	 * @param dateDir
	 */
	public void multiThreadDownloadDataHtm(List<SecHtmlIdx> list, String dateDir) {
		String[] urls = new String[list.size()];
		String[] dsts = new String[list.size()];

		// 检查下载路径文件夹存在与否
		String root = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst + dateDir;
		if (FileUtils.mkdir(root).equals("")) {
			return;
		}

		// 组成下载路径
		for (int i = 0; i < list.size(); i++) {
			SecHtmlIdx item = list.get(i);

			String url = item.getAnchor();
			String fileName = item.getDocument();
			String dst = root + File.separator + fileName;

			urls[i] = url;
			dsts[i] = dst;
		}

		try {
			LOG.info("Start download html data files, total {} files.", urls.length);

			// 开始多线程下载
			multiThreadedDownloadFile(urls, dsts);

			LOG.info("Download daily html data files successful.");
		} catch (Exception e) {
			LOG.error("Exception in download html data files {}.", e);
		}
	}
}
