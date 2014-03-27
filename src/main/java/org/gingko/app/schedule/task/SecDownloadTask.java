package org.gingko.app.schedule.task;

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
import org.gingko.util.FileUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Kyia
 */
public class SecDownloadTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(SecDownloadTask.class);

	private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
	private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);

	private static TaskManagerService taskManagerService = (TaskManagerService) AppContext.getBean(AppContext.TASK_SERVICE);

	private SecDownloader downloader;
	private String date;

	public SecDownloadTask(SecDownloader downloader, String date) {
		this.downloader = downloader;
		this.date = date;
	}

	@Override
	public void run() {
		// Download master idx file
		String dst = downloader.downloadMasterIdx(date);
		// Check file exists
		File file = new File(dst);
		if (!file.exists()) {
			LOG.warn("SEC daily index file not exists. Try again later.");

			// Try again after 5 minutes
			taskManagerService.schedule(new SecDownloadTask(downloader, date), 5, TimeUnit.MINUTES);
		}
		// File is exists, goto next step
		else {
			// Parse
			SecMasterIdxParser parser = new SecMasterIdxParser();
			List<SecIdx> list = parser.parseMasterIdx(dst, date);
			if (list.isEmpty()) {
				LOG.error("Master idx file parse error, file not exists or format error, please check the file and try again!");
				return;
			}

			// Check exist data
			secIdxMapper.deleteByDate(date);
			// Filter the need data
			List<SecIdx> filteredList = new ArrayList<>();
			for (SecIdx secIdx : list) {
				if (SecFilter.INSTANCE.doFilter(secIdx)) {
					filteredList.add(secIdx);
				}
			}
			// Batch insert into database
			secIdxMapper.insertList(filteredList);
			LOG.info("All filtered master idx data had been inserted into database.");

			// Next step
			downloadHtmlIdx();

			// Next step
			parseHtmlIdx();

			// Last step
			downloadData();
		}
	}

	/**
	 * Download fill Document
	 */
	private void downloadHtmlIdx() {
		// Select all master idx from database
		List<SecIdx> list = secIdxMapper.select();
		if (!list.isEmpty()) {
			downloader.multiThreadDownloadIndexHtm(list, date);
		} else {
			LOG.warn("No master idx data in database.");
		}

		// After download, you must check it again, make sure there are no item missed
		// CHECK the database record number, and compare with the file number
		// If not fit, you must download again.

		// ... TODO: SecChecker
	}

	/**
	 * Parse the html index file and insert into database
	 */
	private void parseHtmlIdx() {
		// List the target directory
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlIndexDst + date;
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".htm");
			}
		});

		// Html index parser
		List<SecHtmlIdx> list = new ArrayList<>();
		SecHtmlIndexParser parser = new SecHtmlIndexParser();
		for (File file : files) {
			List<SecHtmlIdx> l = parser.parseHtmlIdx(file, date);
			if (!l.isEmpty()) {
				list.addAll(l);
			}
		}

		// Database clear
		secHtmlIdxMapper.deleteByDate(date);

		// Filter the need data
		List<SecHtmlIdx> filteredList = new ArrayList<>();
		for (SecHtmlIdx secHtmlIdx : list) {
			if (SecFilter.INSTANCE.doFilter(secHtmlIdx)) {
				filteredList.add(secHtmlIdx);
			}
		}
		// Batch insert
		secHtmlIdxMapper.insertList(filteredList);
	}

	/**
	 * Download data file
	 */
	private void downloadData() {
		// From database
		List<SecHtmlIdx> list = secHtmlIdxMapper.select();
		if (!list.isEmpty()) {
			downloader.multiThreadDownloadDataHtm(list, date);
		} else {
			LOG.warn("No html idx data in database.");
		}
	}
}
