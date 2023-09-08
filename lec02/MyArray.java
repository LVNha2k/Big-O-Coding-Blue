package lec02;

import java.io.*;

// Array Codeforces
public class MyArray {
	MyReader rd = new MyReader();
	static final int MAX = (int) (1e5) + 1;
	int n, k, arr[], count[] = new int[MAX];

	void solve() throws IOException {
		arr = new int[n = rd.nextInt()];
		k = rd.nextInt();

		for (int i = 0, j = 0, unique = 0; i < n; i++) {
			arr[i] = rd.nextInt();
			if (count[arr[i]]++ == 0)
				unique++;

			for (; unique == k; j++)
				if (--count[arr[j]] == 0) {
					System.out.printf("%d %d", j + 1, i + 1);
					return;
				}
		}
		System.out.print("-1 -1");
	}

	public static void main(String[] args) throws IOException {
		new MyArray().solve();
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