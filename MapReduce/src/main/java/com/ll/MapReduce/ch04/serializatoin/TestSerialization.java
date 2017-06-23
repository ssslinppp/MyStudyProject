package com.ll.MapReduce.ch04.serializatoin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.StringUtils;

public class TestSerialization {
	public static void main(String[] args) throws Exception {
		//序列化
		IntWritable int163 = new IntWritable(163);
		byte[] bytes = serialize(int163);
		System.out.println("++++++ serialize(163):" + StringUtils.byteToHexString(bytes));
		
		IntWritable newWritable = new IntWritable();
		deserialize(newWritable, bytes);
		System.out.println("++++++ deserialize(" + StringUtils.byteToHexString(bytes) + "):"
				+ newWritable.get());
	}

	public static byte[] serialize(Writable writable) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		writable.write(dataOut); // 序列化
		dataOut.close();
		return out.toByteArray();
	}

	public static byte[] deserialize(Writable writable, byte[] bytes)
			throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream dataIn = new DataInputStream(in);
		writable.readFields(dataIn); //反序列化
		dataIn.close();
		return bytes;
	}
}



















