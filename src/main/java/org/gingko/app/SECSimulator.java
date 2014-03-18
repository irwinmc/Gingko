package org.gingko.app;

import org.gingko.app.cache.SecCache;
import org.gingko.app.download.impl.SecDownloader;
import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.parse.SecHtmlIndexParser;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.config.SecProperties;
import org.gingko.context.AppContext;
import org.gingko.util.DateUtils;
import org.gingko.util.PathUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SecSimulator {

	private long time;

	private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean("secHtmlIdxMapper");

	/**
	 * Prepare
	 */
	public void prepare() {
		// 昨天的时间
		time = System.currentTimeMillis() - DateUtils.MILLISECOND_PER_DAY;

		// 加载过滤器
		SecFilter.INSTANCE.load();
	}

	/**
	 * Download
	 */
	public void download() {
		// 第一步先下载idx文件
		SecDownloader downloader = new SecDownloader();
		String dst = downloader.downloadMasterIdx(time);

		// 解析idx并且放入内存
		SecCache.INSTANCE.putIdxItems(dst);
		List<SecIdx> list = SecCache.INSTANCE.getIdxItems();

		// 检查
		if (list.isEmpty()) {
			// 有错误
		} else {
			// 需要创建的子文件夹名称
			String dateDir = DateUtils.formatTime(time, "yyyyMMdd");
			// 批量下载HTML文件
			downloader.multiThreadDownloadIndexHtm(list, dateDir);
		}
	}

	/**
	 * Parse
	 */
	public void parse() {
		// 时间文件夹
		String dateDir = DateUtils.formatTime(time, "yyyyMMdd");
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlIndexDst + dateDir;
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".htm");
			}
		});

		// 解析器
		List<SecHtmlIdx> list = new ArrayList<SecHtmlIdx>();
		SecHtmlIndexParser parser = new SecHtmlIndexParser();
		for (File file : files) {
			List<SecHtmlIdx> l = parser.parseHtmlIdx(file);
			if (!l.isEmpty()) {
				list.addAll(l);
			}
		}
		SecCache.INSTANCE.putHtmlIdxItems(list);
	}

	/**
	 * Fetch, just other download, real download
	 */
	public void fetch() {
		SecDownloader downloader = new SecDownloader();

		List<SecHtmlIdx> list = SecCache.INSTANCE.getHtmlIdxItems();
		// 检查
		if (list.isEmpty()) {
			// 有错误
		} else {
			// 需要创建的子文件夹名称
			String dateDir = DateUtils.formatTime(time, "yyyyMMdd");
			// 批量下载HTML文件
			downloader.multiThreadDownloadDataHtm(list, dateDir);
		}
	}

	public void insertIdx() {
		SecHtmlIdx secHtmlIdx = new SecHtmlIdx();
		secHtmlIdx.setSeq(1);
		secHtmlIdx.setDescription("FORM 8-K OF AMERICAN EXPRESS COMPANY");
		secHtmlIdx.setDocument("creditstatmar172013.htm");
		secHtmlIdx.setType("8-K");
		secHtmlIdx.setSize(60096);
		secHtmlIdx.setAnchor("http://www.sec.gov/Archives/edgar/data/4962/000000496214000019/creditstatmar172013.htm");

		secHtmlIdxMapper.insert(secHtmlIdx);
	}
}
