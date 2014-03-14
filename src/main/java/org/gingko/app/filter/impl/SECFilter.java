package org.gingko.app.filter.impl;

import org.gingko.app.vo.SECIdxItem;
import org.gingko.config.SECProperties;
import org.gingko.util.PathUtils;
import org.gingko.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;

/**
 * @author Kyia
 */
public enum SECFilter {

	INSTANCE;

	private static final Logger LOG = LoggerFactory.getLogger(SECFilter.class);

	// 将过滤条件放入内存
	private final HashSet<String> ciks = new HashSet<String>();
	private final HashSet<String> formTypes = new HashSet<String>();

	/**
	 * 初始化
	 */
	public void load() {
		loadCiksFile();
		loadFormTypesFile();
	}

	/**
	 * 从文件中读取
	 * 当文件发生变化之时，可以自动或者手动调用该方法重新加载过滤信息
	 */
	public void loadCiksFile() {
		ciks.clear();

		String fileName = PathUtils.getConfPath() + SECProperties.filterCik;
		readFromFile(fileName, ciks);

		LOG.info("Load SEC ciks complete.");
	}

	public void loadFormTypesFile() {
		formTypes.clear();

		String fileName = PathUtils.getConfPath() + SECProperties.filterFormType;
		readFromFile(fileName, formTypes);

		LOG.info("Load SEC formTypes complete.");
	}

	/**
	 * 通用读取配置文件方法
	 *
	 * @param fileName
	 * @param set
	 */
	private void readFromFile(String fileName, HashSet<String> set) {
		File file = new File(fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				line = StringUtils.replaceBlank(line);
				if (line.length() > 0) {
					set.add(line);
				}
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
	}

	/**
	 * 过滤方法
	 *
	 * @param item
	 * @return
	 */
	public boolean doFilter(SECIdxItem item) {
		String cik = item.getCik();
		String formType = item.getFormType();
		return ciks.contains(cik) && formTypes.contains(formType);
	}

	public HashSet<String> getCiks() {
		return ciks;
	}

	public HashSet<String> getFormTypes() {
		return formTypes;
	}
}
