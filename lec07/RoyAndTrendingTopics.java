package lec07;

import java.io.*;
import java.util.*;

// Roy and Trending Topics HackerEarth
public class RoyAndTrendingTopics {
	MyReader rd = new MyReader();
	int N;
	PriorityQueue<Topic> heap = new PriorityQueue<>();

	void solve() throws IOException {
		N = rd.nextInt();
		while (N-- > 0)
			heap.add(new Topic(rd.nextInt(), rd.nextInt(), rd.nextInt(), rd.nextInt(), rd.nextInt(), rd.nextInt()));

		for (int i = 0; i < 5; i++) {
			Topic t = heap.remove();
			System.out.println(t.ID + " " + t.newZ);
		}
	}

	public static void main(String[] args) throws IOException {
		new RoyAndTrendingTopics().solve();
	}

	class Topic implements Comparable<Topic> {
		int ID, Z, P, L, C, S;
		long newZ, changeZ;

		public Topic(int iD, int z, int p, int l, int c, int s) {
			ID = iD;
			Z = z;
			P = p;
			L = l;
			C = c;
			S = s;
			newZ = P * 50 + L * 5 + C * 10 + S * 20;
			changeZ = newZ - Z;
		}

		@Override
		public int compareTo(Topic that) {
			int c = Long.compare(that.changeZ, this.changeZ);
			if (c != 0)
				return c;
			return Integer.compare(that.ID, this.ID);
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