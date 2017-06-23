package com.sssppp.Lucene.TextProcess.IndexData;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexCommit;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.NoDeletionPolicy;
import org.apache.lucene.index.PersistentSnapshotDeletionPolicy;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TransactionalCommit {
	static Version version = Version.LUCENE_47;

	public static void main(String a[]) throws IOException {
		testTransactional();
	}

	public static void testTransactional() throws IOException {
		Analyzer analyzer = new StandardAnalyzer(version);
		Directory indexDir = FSDirectory.open(new File("C:\\luceneIndex4"));
		Directory snapshotDir = FSDirectory
				.open(new File("C:\\luceneSnapShot"));

		//NoDeletionPolicy：所有的提交都不被删除
		PersistentSnapshotDeletionPolicy policy = new PersistentSnapshotDeletionPolicy(
				NoDeletionPolicy.INSTANCE, snapshotDir);
		// SnapshotDeletionPolicy policy = new SnapshotDeletionPolicy(NoDeletionPolicy.INSTANCE);
		IndexWriterConfig config = new IndexWriterConfig(version, analyzer);
		config.setIndexDeletionPolicy(policy);
		IndexWriter indexWriter = new IndexWriter(indexDir, config);

		IndexCommit lastSnapshot;
		// 第1次提交
		Document document = new Document();
		indexWriter.addDocument(document);
		indexWriter.commit();
		lastSnapshot = policy.snapshot(); // 对最后一次提交进行快照
		// 第2次提交
		document = new Document();
		indexWriter.addDocument(document);
		indexWriter.commit();
		lastSnapshot = policy.snapshot();
		// 回滚
		document = new Document();
		indexWriter.addDocument(document);
		indexWriter.rollback();
		indexWriter.close();

		List<IndexCommit> commits = DirectoryReader.listCommits(indexDir);
		System.out.println("=======>Commits count: " + commits.size());
		for (IndexCommit commit : commits) {
			IndexReader reader = DirectoryReader.open(commit);
			System.out.println("Commit " + commit.getSegmentCount());
			System.out.println("Number of docs: " + reader.numDocs());
		}
		System.out.println("\n======>Snapshots count: "
				+ policy.getSnapshotCount());
		List<IndexCommit> snapshots = policy.getSnapshots();
		for (IndexCommit snapshot : snapshots) {
			IndexReader reader = DirectoryReader.open(snapshot);
			System.out.println("Snapshot " + snapshot.getSegmentCount());
			System.out.println("Number of docs: " + reader.numDocs());
		}
		policy.release(lastSnapshot);
		System.out
				.println("\n=========>After release one Snapshort\nSnapshots count: "
						+ policy.getSnapshotCount());
	}
}
