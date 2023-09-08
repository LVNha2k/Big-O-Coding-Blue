package lec07;

import java.io.*;
import java.util.*;

// Promotion SPOJ
public class Promotion {
	MyReader rd = new MyReader();
	static final int MAX = 1000001;
	int n, k, id;
	long total;
	boolean[] taken = new boolean[MAX];
	PriorityQueue<Bill> minHeap = new PriorityQueue<>((b1, b2) -> b1.price - b2.price);
	PriorityQueue<Bill> maxHeap = new PriorityQueue<>((b1, b2) -> b2.price - b1.price);

	void solve() throws IOException {
		n = rd.nextInt();
		for (Bill bill, max, min; n-- > 0;) {
			k = rd.nextInt();
			while (k-- > 0) {
				bill = new Bill(id++, rd.nextInt());
				minHeap.add(bill);
				maxHeap.add(bill);
			}

			do {
				max = maxHeap.remove();
			} while (taken[max.id]);

			do {
				min = minHeap.remove();
			} while (taken[min.id]);

			taken[max.id] = taken[min.id] = true;
			total += max.price - min.price;
		}
		System.out.print(total);
	}

	public static void main(String[] args) throws IOException {
		new Promotion().solve();
	}

	class Bill {
		int id, price;

		public Bill(int id, int price) {
			this.id = id;
			this.price = price;
		}
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead;
		boolean isWindows;
		final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				bin = new BufferedInputStream(file ? new FileInputStream(FILE) : System.in, BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public int nextInt() throws IOException {
			int ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (isWindows && c == 13)
				read();
			return neg ? -ret : ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}