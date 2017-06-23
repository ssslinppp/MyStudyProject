// cc MapFileWriteDemo Writing a MapFile
package com.ll.MapReduce.ch04.mapFile;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;

// vv MapFileWriteDemo
public class MapFileWriteDemo {
  
	private static final String[] DATA = {
	    "One, two, buckle my shoe",
	    "Three, four, shut the door",
	    "Five, six, pick up sticks",
	    "Seven, eight, lay them straight",
	    "Nine, ten, a big fat hen"
	};
  
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			args = new String[] { "hdfs://localhost:9000/hillstone/numbersKeyText.map" };
		}
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);

//		IntWritable key = new IntWritable();
		Text key = new Text();
		Text value = new Text();
		
		MapFile.Writer writer = null;
		try {
			writer = new MapFile.Writer(conf, fs, uri, key.getClass(),
					value.getClass());
			writer.setIndexInterval(3);

			for (int i = 0; i < 9; i++) {
				key.set("k"+(i + 1));
				value.set(DATA[i % DATA.length]);
				writer.append(key, value);
			}
		} finally {
			IOUtils.closeStream(writer);
		}
	}
}
// ^^ MapFileWriteDemo




























