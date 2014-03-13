package org.gingko.app.download;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Kyia
 */
public abstract class AbstractDownloader implements Downloader {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractDownloader.class);

	/**
	 * 下载文件方法
	 *
	 * @param url  文件地址
	 * @param dst 本地保存地址
	 * @return
	 */
	protected boolean downloadFile(String url, String dst) throws Exception {
		boolean success = false;

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			LOG.info("Executing request " + httpget.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						InputStream in = entity.getContent();
						try {
							OutputStream out = new FileOutputStream(new File(dst));
							byte b[] = new byte[1024];
							int len;
							while ((len = in.read(b)) != -1) {
								out.write(b, 0, len);
							}
							out.flush();
							out.close();
						} catch (IOException e) {
							throw e;
						} finally {
							in.close();
						}
					}
					success = true;
				}
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}

		return success;
	}
}
