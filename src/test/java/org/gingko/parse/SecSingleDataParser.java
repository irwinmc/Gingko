package org.gingko.parse;

import org.gingko.app.collect.ArrayBasedTable;
import org.gingko.app.parse.SecHtmlDataParser;
import org.gingko.app.parse.SecTableProcessor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Kyia
 */
public class SecSingleDataParser extends SecDataParser {

	private static final Logger LOG = LoggerFactory.getLogger(SecSingleDataParser.class);

	/**
	 * Just run!
	 *
	 * @throws Exception
	 */
	@Override
	public void run() throws Exception {
		// File reader
		String filePath = getFilePath();
		File input = new File(filePath);
		Document doc = Jsoup.parse(input, "iso-8859-1", baseUrl);

		// Get specific element
		Element element = doc.getElementsByTag("table").get(9);

		// Html data parser
		SecHtmlDataParser parser = new SecHtmlDataParser();
		ArrayBasedTable table = parser.parseTable(element);

		// Processor
		SecTableProcessor processor = new SecTableProcessor();
		if (!table.isEmpty()) {
			table = processor.processBasicField(table);

			StringBuffer sb = new StringBuffer();
			sb.append(toHtmlString(table));
			sb.append(NEWLINE);
			writeHtmlFile(outputFileSingle, doc.title(), sb.toString());
		}

		LOG.info("Complete.");
	}
}
