package com.sssppp.Lucene.TextProcess.SearchIndexes.CustomCollector;

/**
 * 
 * Collector比较底层，一般用不到； 
 * IndexSearcher.search()默认会使用TopScoreDocCollector
 * IndexSearcher.search()搜索到匹配的Document之后，会将Documents发送给Collector，
 * Collector负责对Document进行算score和sort。
 * 
 * 当希望控制查询后被匹配的Document如果计算score以及sort、filter时，Collector就派上用场了
 * @author linliu
 * 
 */
public class CustomCollector {

}
