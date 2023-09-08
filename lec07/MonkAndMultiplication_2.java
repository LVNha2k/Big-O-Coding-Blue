package lec07;

import java.io.*;

// Monk and Multiplication HackerEarth
public class MonkAndMultiplication_2 {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N;

	void solve() throws IOException {
		N = rd.nextInt();
		// max3 < max2 < max1
		for (int i = 1, val, max3 = -1, max2 = -1, max1 = -1; i <= N; i++) {
			val = rd.nextInt();

			if (max1 < val) {
				max3 = max2;
				max2 = max1;
				max1 = val;

			} else if (max2 < val && val <= max1) {
				max3 = max2;
				max2 = val;

			} else if (max3 < val && val <= max2)
				max3 = val;

			pw.println(i < 3 ? -1 : 1L * max1 * max2 * max3);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new MonkAndMultiplication_2().solve();
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