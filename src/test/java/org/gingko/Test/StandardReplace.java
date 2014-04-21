package org.gingko.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.gingko.util.FileUtils;
import org.gingko.util.PathUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class StandardReplace {

	private 	static	Map<String,String>  map=new HashMap<String,String>();
	
	private static String relPath="";
	public static void main(String[] args) {
		StandardReplace.getStandardMap("usk127");
		String path="D:/workspace/trunk/webroot/data/sec/html/form/20140320/p/usk127-22444-0000022444-14-000010-cmc-2282014x10q.htm";
	    relPath=path.substring(0,path.length()-4).concat("_replace.htm");
	    StandardReplace.replaceStan(path);
	}
	
	public static void  replaceStan(String filepath){
		String content=ParserUnit.readFile(filepath );
		StringBuffer relCon=new StringBuffer(content);
		if(content!=null){
			Element  doc=Jsoup.parse(content);
			Elements  trs=doc.select("tr");
			for(Element tr:trs){
				Elements tds=tr.select("td");
				String cat=tds.get(0).text();
				String field=map.get(cat);
				if(field!=null){
					relCon=new StringBuffer(relCon.toString().replace(cat, field));
				}
			}
		}
		
		FileUtils.writeTofile(relCon.toString(),relPath);
	}
	
	public  static void   getStandardMap(String uskType){
		map=new HashMap<String,String>();
		String content = ParserUnit.readFile(PathUtils.getWebRootPath()
				+ File.separator + "data/usk/"+uskType+".xml" );
		if(content!=null){
			Document doc = Jsoup.parse(content);
			Elements  files=doc.select("file");
			for(Element file:files){
				map.put(file.attr("name"), file.attr("DBField"));
			}
		}
	}

}
