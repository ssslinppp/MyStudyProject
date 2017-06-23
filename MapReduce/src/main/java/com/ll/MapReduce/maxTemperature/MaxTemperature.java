package com.ll.MapReduce.maxTemperature;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperature {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			args = new String[] {
					"hdfs://localhost:9000/user/hadoop/input/sample.txt",
					"hdfs://localhost:9000/user/hadoop/out2" };
		}

		Job job = new Job(); // 指定作业执行规范
		job.setJarByClass(MaxTemperature.class);
		job.setJobName("Max temperature");

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1])); // Reduce函数输出文件的写入路径

		job.setMapperClass(MaxTemperatureMapper.class);
		job.setCombinerClass(MaxTemperatureReducer.class);
		job.setReducerClass(MaxTemperatureReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
// ^^ MaxTemperature

