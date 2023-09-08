package lec02;

import java.io.*;

// Approximating a Constant Range Codeforces
public class ApproximatingAConstantRange {
	MyReader rd = new MyReader();
	int n, a[], m, M;

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			a[i] = rd.nextInt();

		int start = 0, end = 1, len = 2, maxLen = 2;
		m = Math.min(a[0], a[1]);
		M = Math.max(a[0], a[1]);

		for (int value; end++ < n - 1;)
			if (Math.abs(a[end] - M) > 1 || Math.abs(a[end] - m) > 1) {
				start = end - 1;
				maxLen = Math.max(len, maxLen);
				len = 2;
				m = Math.min(a[start], a[end]);
				M = Math.max(a[start], a[end]);
				value = a[start];

				while (start > 0)
					if (a[--start] == value)
						len++;
					else {
						start++;
						break;
					}
			} else
				maxLen = Math.max(++len, maxLen);

		System.out.print(maxLen);
	}

	public static void main(String[] args) throws IOException {
		new ApproximatingAConstantRange().solve();
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

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}