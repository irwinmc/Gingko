package org.gingko.app;

import org.gingko.app.download.impl.SecDownloader;
import org.gingko.app.filter.impl.SecFilter;
import org.gingko.app.parse.SecHtmlIndexParser;
import org.gingko.app.parse.SecMasterIdxParser;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.config.SecProperties;
import org.gingko.context.AppContext;
import org.gingko.services.TaskManagerService;
import org.gingko.util.DateUtils;
import org.gingko.util.FileUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyia
 */
public class SecSimulator {

	private static final Logger LOG = LoggerFactory.getLogger(SecSimulator.class);

	private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
	private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);

	private static TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	private long time;
	private String dateDir;
	private SecDownloader downloader = new SecDownloader();

	/**
	 * Prepare
	 */
	public void prepare() {
		// 昨天的时间
		time = System.currentTimeMillis() - DateUtils.MILLISECOND_PER_DAY;
		dateDir = DateUtils.formatTime(time, "yyyyMMdd");

		// 加载过滤器
		SecFilter.INSTANCE.load();
	}

	/**
	 * Clear
	 */
	public void clear() {
//		secIdxMapper.delete();
		secHtmlIdxMapper.delete();

		// 删除所有文件
//		String indexPath = PathUtils.getWebRootPath() + SecProperties.dataHtmlIndexDst + dateDir;
//		FileUtils.deleteAllFile(indexPath);

		String formPath = PathUtils.getWebRootPath() + SecProperties.dataHtmlFormDst + dateDir;
		FileUtils.deleteAllFile(formPath);
	}

	/**
	 * Download
	 */
	public void download() {
		// 下载总索引文件
//		downloadMasterIdx();

		// 下载HTML索引文件
//		downloadHtmlIdx();

		// 解析HTML索引文件
		parseHtmlIdx();

		// 下载真实数据文件
		downloadData();
	}

	/**
	 * 下载总索引文件
	 */
	private void downloadMasterIdx() {
		// 1. 第一步先下载idx文件
		String dst = downloader.downloadMasterIdx(time);

		// 2. 检查下载文件并解析
		SecMasterIdxParser parser = new SecMasterIdxParser();
		List<SecIdx> list = parser.parseMasterIdx(dst);
		if (list.isEmpty()) {
			// 没有数据
			LOG.error("Master idx file parse error, file not exists or format error, please check the file and try again!");
			return;
		}

		// 3. 过滤并且存入数据库
		List<SecIdx> filteredList = new ArrayList<SecIdx>();
		for (SecIdx secIdx : list) {
			if (SecFilter.INSTANCE.doFilter(secIdx)) {
				filteredList.add(secIdx);
			}
		}
		secIdxMapper.insertList(filteredList);

		LOG.info("All filtered master idx data had been inserted into database.");
	}

	/**
	 * 下载Html索引文件
	 * Fill Document
	 */
	private void downloadHtmlIdx() {
		List<SecIdx> list = secIdxMapper.select();
		if (!list.isEmpty()) {
			// 4. 批量下载HTML文件
			downloader.multiThreadDownloadIndexHtm(list, dateDir);
		} else {
			LOG.warn("No master idx data in database.");
		}
	}

	/**
	 * 解析Html索引文件插入数据库
	 */
	public void parseHtmlIdx() {
		// 目标文件夹
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

		// 过滤出必要的数据文件
		List<SecHtmlIdx> filteredList = new ArrayList<SecHtmlIdx>();
		for (SecHtmlIdx secHtmlIdx : list) {
			if (SecFilter.INSTANCE.doFilter(secHtmlIdx)) {
				filteredList.add(secHtmlIdx);
			}
		}
		secHtmlIdxMapper.insertList(filteredList);
	}

	/**
	 * 下载真正数据文件
	 */
	public void downloadData() {
		List<SecHtmlIdx> list = secHtmlIdxMapper.select();
		if (list.isEmpty()) {
			LOG.warn("No html idx data in database.");
		} else {
			// 批量下载HTML文件
			downloader.multiThreadDownloadDataHtm(list, dateDir);
		}
	}
}
