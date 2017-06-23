package com.ll.MapReduce.ch04.codec;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.ll.MapReduce.maxTemperature.MaxTemperature;
import com.ll.MapReduce.maxTemperature.MaxTemperatureMapper;
import com.ll.MapReduce.maxTemperature.MaxTemperatureReducer;

public class MaxTemperatureWithCompression {
	public static void main(String[] args) throws Exception{
		if (args.length != 2) {
			System.err
					.println("Usage: MaxTemperatureWithCompression <input path> "
							+ "<output path>");
			//若输入文件也是压缩文件，则MapReduce在读取文件时，会自动解压缩文件
			args = new String[] {
					"hdfs://localhost:9000/hillstone/input/sample.txt.gz",
					"hdfs://localhost:9000/hillstone/out1" };
		}

		@SuppressWarnings("deprecation")
		Job job = new Job();
		job.setJarByClass(MaxTemperature.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//设置Reduce输出采用 gzip压缩格式压缩
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		job.setMapperClass(MaxTemperatureMapper.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}














