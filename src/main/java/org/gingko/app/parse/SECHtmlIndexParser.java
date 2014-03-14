package org.gingko.app.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kyia
 *
 * 文件保存位置 webroot/data/sec/html/index/master.20140310.idx
 * 这部分解析html格式的Index文件，里面包含了一个重要的索引表格，
 * 模拟人操作的方式，即获取相应类型的表单数据
 * <div>
 *     <div>
 *         <p>Document Format Files</p>
 *         <table class="tableFile" summary="Document Format Files">
 *             <tr>
 *                 <th scope="col" style="width: 5%;"><acronym title="Sequence Number">Seq</acronym></th>
 *                 <th scope="col" style="width: 40%;">Description</th>
 *                 <th scope="col" style="width: 20%;">Document</th>
 *                 <th scope="col" style="width: 10%;">Type</th>
 *                 <th scope="col">Size</th>
 *             </tr>
 *             <tr>
 *                 <td scope="row">1</td>
 *                 <td scope="row">FORM 6-K</td>
 *                 <td scope="row"><a href="/Archives/edgar/data/1455886/000106299314001292/form6k.htm">form6k.htm</a></td>
 *                 <td scope="row">6-K</td>
 *                 <td scope="row">10380</td>
 *             </tr>
 *             ...
 *
 */
public class SECHtmlIndexParser {

	private static final Logger LOG = LoggerFactory.getLogger(SECHtmlIndexParser.class);


}
