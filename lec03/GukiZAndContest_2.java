package lec03;

import java.io.*;
import java.util.*;

// GukiZ and Contest Codeforces
public class GukiZAndContest_2 {
	MyReader rd = new MyReader();
	int n, a[];
	List<Integer> sorted_a;

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		sorted_a = new ArrayList<>(n);

		for (int i = 0; i < n; i++)
			sorted_a.add(a[i] = rd.nextInt());

		Collections.sort(sorted_a, Collections.reverseOrder());

		for (int rating : a)
			System.out.print(sorted_a.indexOf(rating) + 1 + " ");
	}

	public static void main(String[] args) throws IOException {
		new GukiZAndContest_2().solve();
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