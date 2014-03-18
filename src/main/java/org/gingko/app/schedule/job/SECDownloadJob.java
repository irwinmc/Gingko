package org.gingko.app.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 *         <p/>
 *         每日下载Idx文件之后，将老的内存（后期改为redis）中的数据删除
 *         之后将新的文件进行解析，新的数据放入内存之中
 *         而后开始针对处理之后的数据做进一步的下载分析
 *         这里触发下载队列
 */
public class SecDownloadJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SecDownloadJob.class);

	/**
	 * @param context
	 * @throws JobExecutionException
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

	}
}
