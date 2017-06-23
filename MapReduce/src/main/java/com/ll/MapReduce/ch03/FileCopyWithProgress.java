package com.ll.MapReduce.ch03;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			args = new String[] { "./test",
					"hdfs://localhost:9000/user/hadoop/out12" };
		}
		
		String localSrc = args[0];
		String dst = args[1];
		
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));

		FileSystem fs = FileSystem.get(URI.create(dst), new Configuration());
		FSDataOutputStream out = fs.create(new Path(dst), new Progressable(){
			@Override
			public void progress() {
				System.out.print(".");
			}
		});
		
		IOUtils.copyBytes(in, out, 4096, true);
		
	}
}


























