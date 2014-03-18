package org.gingko.app.download;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
	protected void downloadFile(String url, String dst) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(url);
			// Request configuration can be overridden at the request level.
			// They will take precedence over the one set at the client level.
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000)
					.setConnectTimeout(3000)
					.build();
			httpGet.setConfig(requestConfig);

			CloseableHttpResponse response = httpClient.execute(httpGet);
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
					LOG.debug("URL: {} download successful.", url);
				}
			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
	}

	/**
	 * 多线程下载方法
	 *
	 * @param urls
	 * @param dsts
	 * @throws Exception
	 */
	protected void multiThreadedDownloadFile(String[] urls, String[] dsts) throws Exception {
		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);

		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm)
				.build();

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(3000)
				.setConnectTimeout(3000)
				.build();

		try {
			// create a thread for each URI
			DownloadThread[] threads = new DownloadThread[urls.length];
			for (int i = 0; i < threads.length; i++) {
				HttpGet httpGet = new HttpGet(urls[i]);
//				httpGet.setConfig(requestConfig);
				threads[i] = new DownloadThread(httpClient, httpGet, dsts[i]);
			}

			// start the threads
			for (int j = 0; j < threads.length; j++) {
				threads[j].start();
			}

			// join the threads
			for (int j = 0; j < threads.length; j++) {
				threads[j].join();
			}
		} finally {
			httpClient.close();
		}
	}
}
