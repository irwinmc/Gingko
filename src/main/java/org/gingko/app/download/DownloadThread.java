package org.gingko.app.download;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.*;

/**
 * @author Kyia
 */
public class DownloadThread extends Thread {

	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpGet;
	private final String dst;

	public DownloadThread(CloseableHttpClient httpClient, HttpGet httpGet, String dst) {
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpGet = httpGet;
		this.dst = dst;
	}

	@Override
	public void run() {
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet, context);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					// get the response body as an array of bytes
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
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			System.out.println(httpGet.getRequestLine());
			e.printStackTrace();
		}
	}
}
