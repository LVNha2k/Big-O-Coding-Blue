package lec03;

import java.io.*;
import java.util.*;

// GukiZ and Contest Codeforces
public class GukiZAndContest {
	MyReader rd = new MyReader();
	static final int MAX_A = 2001;
	int n, ranked[] = new int[MAX_A];
	List<Integer> a, sorted_a;

	void solve() throws IOException {
		a = new ArrayList<>(n = rd.nextInt());
		sorted_a = new ArrayList<>(n);
		
		for (int i = 0, tmp; i < n; i++) {
			a.add(tmp = rd.nextInt());
			sorted_a.add(tmp);
		}
		Collections.sort(sorted_a, Collections.reverseOrder());

		for (int i = 0, rating; i < n; i++) {
			rating = sorted_a.get(i);
			if (ranked[rating] == 0)
				ranked[rating] = i + 1;
		}

		for (int rating : a)
			System.out.print(ranked[rating] + " ");
	}

	public static void main(String[] args) throws IOException {
		new GukiZAndContest().solve();
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