package org.gingko.app.download.impl;

import org.gingko.app.download.AbstractDownloader;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.config.SecProperties;
import org.gingko.util.FileUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author Hy
 */
public class SecRssDownloader extends AbstractDownloader {

	private static final Logger LOG = LoggerFactory.getLogger(SecRssDownloader.class);

	/**
	 * 批量下载Index Html文件
	 *
	 * @param list
	 * @param date
	 * @return
	 */
	public void multiThreadDownloadIndexHtm(List<SecIdx> list, String date) {
		String[] urls = new String[list.size()];
		String[] dsts = new String[list.size()];

		// 检查下载路径文件夹存在与否
		String path = PathUtils.getWebRootPath()
				+ SecProperties.dataHtmlIndexDst + date;
		if (FileUtils.mkdir(path).equals("")) {
			return;
		}

		// 下载之前删除该文件夹下所有文件
		FileUtils.deleteAllFile(path);

		// 组成下载路径
		for (int i = 0; i < list.size(); i++) {
			SecIdx item = list.get(i);

			String url = item.getFillingHtmlUrl();
			String dst = path + File.separator + item.getSiid() + ".htm";

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
	 * @param date
	 */
	public void multiThreadDownloadDataHtm(List<SecHtmlIdx> list, String date) {
		String[] urls = new String[list.size()];
		String[] dsts = new String[list.size()];

		// 检查下载路径文件夹存在与否
		String path = PathUtils.getWebRootPath()
				+ SecProperties.dataHtmlFormDst + date;
		if (FileUtils.mkdir(path).equals("")) {
			return;
		}

		// 下载之前删除该文件夹下所有文件
		FileUtils.deleteAllFile(path);

		// 组成下载路径
		for (int i = 0; i < list.size(); i++) {
			SecHtmlIdx item = list.get(i);

			String url = item.getAnchor();
			String dst = path + File.separator + item.getLocalFile();

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

	/**
	 * 每30秒轮询一次下载Rss源文件，每次取第一页的，即最近时间点更新的
	 *
	 * @param rssUrl
	 */
	public String downloadRssFile(String rssUrl) {
		String content = null;
		try {
			URL newUrl = new URL(rssUrl);
			URLConnection connect = newUrl.openConnection();
			connect.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			DataInputStream dis = new DataInputStream(connect.getInputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(dis, "UTF-8"));
			String readLine = null;
			while ((readLine = in.readLine()) != null) {
				content = content + readLine;
			}
			in.close();
		} catch (MalformedURLException me) {
			LOG.error("failed to download  rss feed file" + me.getMessage());
		} catch (IOException ioe) {
			LOG.error("failed to download  rss feed file" + ioe.getMessage());
		}
		return content;
	}

	/**
	 * 下载每个需要的htm
	 *
	 * @param secHtm
	 */
	public void downloadFile(SecHtmlIdx secHtm) {
		//获取下载路径
		String url = secHtm.getAnchor();

		//下载保存地址
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst + secHtm.getDate() + "/" + secHtm.getSiid() + "-" + secHtm.getDocument();
		if (!new File(path).exists()) {
			FileUtils.createCatalog(path);
		}
		try {
			super.downloadFile(url, path);
			LOG.info("download success ,the url is {}", url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("download failed ,the url is {},ths  save  path is {}", url, path);
		}
	}
}
