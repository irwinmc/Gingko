package org.gingko.ext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * Created by Administrator on 14-4-2.
 */
public enum Stock {

    INSTANCE;

    public void download() throws Exception {
        String url = "http://www.gpcxw.com/funds/600743.html";
        String dst = "F:/data/" + "hi.html";

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
                    System.out.println("URL download successful. " +  url);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

    }
}
