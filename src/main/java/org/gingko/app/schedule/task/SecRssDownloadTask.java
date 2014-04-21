package org.gingko.app.schedule.task;

import org.gingko.app.download.impl.SecRssDownloader;
import org.gingko.app.parse.SecRssFeedParser;
import org.gingko.app.persist.PersistContext;
import org.gingko.app.persist.domain.SecHtmlIdx;
import org.gingko.app.persist.domain.SecIdx;
import org.gingko.app.persist.mapper.SecHtmlIdxMapper;
import org.gingko.app.persist.mapper.SecIdxMapper;
import org.gingko.config.SecProperties;
import org.gingko.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
* @ClassName: SecRssDownloadTask
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Hy
* @date 2014年4月2日 下午2:29:16
* @version 1.0
 */
public class SecRssDownloadTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(SecRssDownloadTask.class);

	private static SecHtmlIdxMapper secHtmlIdxMapper = (SecHtmlIdxMapper) AppContext.getBean(PersistContext.SEC_HTML_IDX_MAPPER);
	private static SecIdxMapper secIdxMapper = (SecIdxMapper) AppContext.getBean(PersistContext.SEC_IDX_MAPPER);
	private SecRssDownloader downloader;

	public SecRssDownloadTask(SecRssDownloader downloader) {
		this.downloader = downloader;
	}

	@Override
	public void run() {
		LOG.info("Start to download the htm");

		SecRssFeedParser parser = new SecRssFeedParser();
		String content = downloader.downloadRssFile(SecProperties.rssUrl);
		// 判断下载的内容是否合法
		if (content != null) {
			List<SecIdx> list = parser.parseRssContent(content);

            List<SecIdx> newList = new ArrayList<SecIdx>();
			List<SecHtmlIdx> htmList = new ArrayList<SecHtmlIdx>();

			for (SecIdx secIdx : list) {
				// 判断是否处理过

				if (isExist(secIdx)) {
					// 根据一个SecIdx对象得到SecHtmlIdx对象
					SecHtmlIdx secHtm = parser.processDetailHtm(secIdx);

					// download htm file
					downloader.downloadFile(secHtm);
					htmList.add(secHtm);
					newList.add(secIdx);
				}
			}

			// Check list if valid before inserrt into database
			if (htmList.size() != 0) {
				secHtmlIdxMapper.insertList(htmList);
			}
			if (newList.size() != 0) {
				secIdxMapper.insertList(newList);
				LOG.info("Finished  to download  the  htm");
			} else {
				LOG.info("There are no new htm require to download");
			}
		}
	}

	/**
	 * 检查该条记录是否已经下载，如果下载过，返回false
	 *
	 * @param sexIdx
	 * @return boolean 返回类型
	 */
	public boolean isExist(SecIdx sexIdx) {
		// 根据siid查数据库中是否存在
		List<SecHtmlIdx> list = secHtmlIdxMapper.selectBySiid(sexIdx.getSiid());
		if (list.size() == 0) {
			return true;
		}
		return false;
	}

}
