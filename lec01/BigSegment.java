package lec01;

import java.io.*;

// Big Segment Codeforces
public class BigSegment {
	MyReader rd = new MyReader();
	int n;

	void solve() throws IOException {
		int L[] = new int[n = rd.nextInt()], R[] = new int[n], left = Integer.MAX_VALUE, right = 0;

		for (int i = 0; i < n; i++) {
			left = Math.min(left, L[i] = rd.nextInt());
			right = Math.max(right, R[i] = rd.nextInt());
		}

		for (int i = 0; i < n; i++)
			if (left == L[i] && right == R[i]) {
				System.out.print(i + 1);
				return;
			}
		System.out.print(-1);
	}

	public static void main(String[] args) throws IOException {
		new BigSegment().solve();
	}

	static class MyReader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;
		private boolean isWindows;
		static final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				din = new DataInputStream(file ? new FileInputStream(FILE) : System.in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
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
			if (neg)
				return -ret;
			return ret;
		}

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}