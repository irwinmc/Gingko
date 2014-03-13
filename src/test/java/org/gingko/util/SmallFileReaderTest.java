package org.gingko.util;

import org.gingko.app.filter.impl.SECFilter;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author Kyia
 */
public class SmallFileReaderTest {

	// @Test
	public void changeToLine() {
		String targetFileName = PathUtils.getConfPath() + "sec-filter" + File.separator + "cik-1.txt";

		SECFilter filter = new SECFilter();
		filter.loadCiksFile();
		try {
			HashSet<String> set = filter.getCiks();
			System.out.println(set.size());

			FileWriter writer = new FileWriter(targetFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			for (String cik : set) {
				bufferedWriter.write(cik);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
