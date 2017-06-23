package com.sssppp.Lucene.TextProcess.SearchIndexes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ObtainIndexReader {

	public static void aa() throws IOException {
		Directory directory = FSDirectory.open(new File("/data/index"));
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		// pull a list of underlying AtomicReaders
		List<AtomicReaderContext> atomicReaderContexts = directoryReader
				.leaves();
		// retrieve the first AtomicReader from the list
		AtomicReader atomicReader = atomicReaderContexts.get(0).reader();
		
		//如果Index已经改变了，则返回一个新的IndexReader，若没有改变，则返回null
		DirectoryReader newDirectoryReader = DirectoryReader
				.openIfChanged(directoryReader);
		if (newDirectoryReader != null) {
			IndexSearcher indexSearcher = new IndexSearcher(newDirectoryReader);
			//关闭old DirectoryReader
			directoryReader.close();
		}
	}
}




















