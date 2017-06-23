package com.ll.MapReduce.ch03;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class FileSystemCat {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			args = new String[] { "hdfs://localhost:9000/user/hadoop/input/sample.txt" };
		}
		String uri = args[0];

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FSDataInputStream in = null;
		try {
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096, false);
			System.out.println("====>Current pos: " + in.getPos());
			in.seek(0);
			System.out.println("========================");
			IOUtils.copyBytes(in, System.out, 4096, false);
		} finally {
			IOUtils.closeStream(in);
		}
	}

}
