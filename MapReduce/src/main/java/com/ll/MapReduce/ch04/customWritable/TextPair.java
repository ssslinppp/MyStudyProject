package com.ll.MapReduce.ch04.customWritable;

//cc TextPair A Writable implementation that stores a pair of Text objects
//cc TextPairComparator A RawComparator for comparing TextPair byte representations
//cc TextPairFirstComparator A custom RawComparator for comparing the first field of TextPair byte representations
//vv TextPair
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;

public class TextPair implements WritableComparable<TextPair> {

	private Text first;
	private Text second;

	public TextPair() {
		super();
		set(new Text(), new Text());
	}

	public TextPair(String first, String second) {
		super();
		set(new Text(first), new Text(second));
	}

	public TextPair(Text first, Text second) {
		super();
		set(first, second);
	}

	public void set(Text first, Text second) {
		this.first = first;
		this.second = second;
	}

	public Text getFirst() {
		return first;
	}

	public Text getSecond() {
		return second;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		first.write(out);
		second.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		first.readFields(in);
		second.readFields(in);
	}
	
	/**
	 * use for reduce partition
	 */
	@Override
	public int hashCode() {
		return first.hashCode()*163 + second.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TextPair) {
			TextPair tp = (TextPair)obj;
			return first.equals(tp.first) && second.equals(tp.second);
		}
		return false;
	}
	
	/**
	 * 定义 Map/Reduce 输出格式
	 */
	@Override
	public String toString() {
		return first + "\t" + second;
	}

	/**
	 * 用于 key 的排序
	 */
	@Override
	public int compareTo(TextPair o) {
		int cmp = first.compareTo(o.first);
		if (cmp != 0) {
			return cmp;
		}

		return second.compareTo(o.second);
	}

	/**
	 * 进行Key排序时，需要使用compareTo();
	 * 通过注册TextPair.class的Comparator类实现速度的提升
	 * 
	 * @author linliu
	 *
	 */
	public static class Comparator extends WritableComparator{
		private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();
		
		public Comparator(){
			super(TextPair.class);
		}
		
		@Override
		public int compare(byte[] b1, int s1, int l1,
				byte[] b2, int s2, int l2) {
			try {
				int n1 = WritableUtils.decodeVIntSize(b1[s1]) + readInt(b1, s1);
				int n2 = WritableUtils.decodeVIntSize(b2[s2]) + readInt(b2, s2);
				int cmp = TEXT_COMPARATOR.compare(b1, s1, n1, b2, s2, n2);
				if (cmp != 0) {
					return cmp;
				}
				return TEXT_COMPARATOR.compare(b1, s1 + n1, l1 - n1, b2, s2
						+ n2, l2 - n2);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	
	static{
		WritableComparator.define(TextPair.class, new Comparator());
	}

	public static class FirstComparator extends WritableComparator {

		private static final Text.Comparator TEXT_COMPARATOR = new Text.Comparator();

		public FirstComparator() {
			super(TextPair.class);
		}

		@Override
		public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {

			try {
				int firstL1 = WritableUtils.decodeVIntSize(b1[s1])
						+ readVInt(b1, s1);
				int firstL2 = WritableUtils.decodeVIntSize(b2[s2])
						+ readVInt(b2, s2);
				return TEXT_COMPARATOR
						.compare(b1, s1, firstL1, b2, s2, firstL2);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}

		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			if (a instanceof TextPair && b instanceof TextPair) {
				return ((TextPair) a).first.compareTo(((TextPair) b).first);
			}
			return super.compare(a, b);
		}
	}
}




























