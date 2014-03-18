package org.gingko.app.download;

/**
 * @author Kyia
 */
public interface Downloader {

	/**
	 * 每个数据来源都拥有一个下载器，下载器采用统一接口，针对不同的网站做出不同的处理方式。
	 * 可以根据有限的网站别名来定义下载器的名字，在外部可以通过手动配置开关下载器，定义下载器的路径等。
	 *
	 * 如：
	 * HashMap<String, Downloader> map = new HashMap<String, Downloader>();
	 * map.put("SEC", new SecDownloader());
	 *
	 * 使用时
	 *
	 * return map.get("SEC");
	 *
	 * 在定义时采用此种设计模式，创建时使用同样可以达到单例的效果。
	 */
}
