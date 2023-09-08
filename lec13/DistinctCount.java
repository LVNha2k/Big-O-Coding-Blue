package lec13;

import java.io.*;
import java.util.*;

// Distinct Count HackerEarth
public class DistinctCount {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int T, N, X;
	Set<Integer> set = new HashSet<>();

	void solve() throws IOException {
		for (T = rd.nextInt(); T-- > 0;) {
			N = rd.nextInt();
			X = rd.nextInt();
			set.clear();
			while (N-- > 0)
				set.add(rd.nextInt());

			pw.println(set.size() == X ? "Good" : set.size() > X ? "Average" : "Bad");
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new DistinctCount().solve();
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