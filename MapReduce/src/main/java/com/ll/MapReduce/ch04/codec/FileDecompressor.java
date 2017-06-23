package com.ll.MapReduce.ch04.codec;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

public class FileDecompressor {
	public static void main(String[] args) throws Exception {
//		String uri = args[0];
		String uri = "hdfs://localhost:9000/user/hadoop/input/fileCompress.gz";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);

		Path inputPath = new Path(uri);

		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		// 根据文件后缀名获取相应的Codec
		CompressionCodec codec = factory.getCodec(inputPath);
		if (codec == null) {
			System.err.println("No codec found for " + uri);
			System.exit(1);
		}

		//获取输出路径
		String outputUri = CompressionCodecFactory.removeSuffix(uri,
				codec.getDefaultExtension());
		System.out.println("codec.getDefaultExtension():"
				+ codec.getDefaultExtension());
		System.out.println("outputUri :" + outputUri);

		InputStream in = null;
		OutputStream out = null;
		try {
			in = codec.createInputStream(fs.open(inputPath));
			out = fs.create(new Path(outputUri));
			IOUtils.copyBytes(in, out, conf);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
	}
}

















