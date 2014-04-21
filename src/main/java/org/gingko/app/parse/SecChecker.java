package org.gingko.app.parse;

import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.config.SecProperties;
import org.gingko.context.AppContext;
import org.gingko.util.DateUtils;
import org.gingko.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Kyia
 */
public class SecChecker {

	private static final Logger LOG = LoggerFactory.getLogger(SecChecker.class);

	private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
	private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);

	// 时间目录
	private String dateDir = DateUtils.formatTime(System.currentTimeMillis() - DateUtils.MILLISECOND_PER_DAY, "yyyyMMdd");

	/**
	 * 检查Idx文件
	 */
	public void checkIdxFiles() {
		// 文件名集合
		HashSet<String> fileNameSet = new HashSet<String>();

		// 获取所有文件名
		List<SecIdx> list = secIdxMapper.select();
		for (SecIdx secIdx : list) {
			String url = secIdx.getFillingHtmlUrl();
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			if (fileNameSet.contains(fileName)) {
				LOG.warn("{} exists.", fileName);
			} else {
				fileNameSet.add(fileName);
			}
		}
		LOG.info("There are total {} file names.", fileNameSet.size());

		// 目标文件夹
		String path = PathUtils.getWebRootPath() + SecProperties.dataHtmlIndexDst + dateDir;
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".htm");
			}
		});

		HashSet<String> localFileNameSet = new HashSet<String>();
		for (File file : files) {
			String fileName = file.getName();
			localFileNameSet.add(fileName);
		}
		LOG.info("There are total {} local file names.", localFileNameSet.size());
	}
}
