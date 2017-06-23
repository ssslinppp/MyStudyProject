【IndexReader】
抽象类-->具体实现类：
1. AtomicReader: is a single reader that's atomic for a single segment of an index;
	子类：SlowCompositeReaderWrapper
	SlowCompositeReaderWrapper用于将CompositeReader转换为AtomicReader
2. CompositeReader: contains a list of AtomicReaders on multiple segments;
	子类：DirectoryReader
===========================================================
【搜索结果排序问题】
 * 默认情况下，Lucene的搜索结果，是根据 Relevant score（相关性得分）的降序进行排序的；
 * Sort class有两个静态的排序方式：
 * 1. RELEVANCE ：基于Relevant Score进行排序（默认行为）；
 * 2. INDEX ORDER：基于Index顺序进行排序；

============================================================
【查询结果分页】
使用IndexSearcher的searchAfter()

================================================
【Collector】
 * Collector比较底层，一般用不到； 
 * IndexSearcher.search()默认会使用TopScoreDocCollector
 * IndexSearcher.search()搜索到匹配的Document之后，会将Documents发送给Collector，
 * Collector负责对Document进行算score和sort。
 * 
 * 当希望控制查询后被匹配的Document,如果计算score以及sort、filter时，Collector就派上用场了

====================================================



