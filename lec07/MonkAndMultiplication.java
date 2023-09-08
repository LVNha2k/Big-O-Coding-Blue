package lec07;

import java.io.*;
import java.util.*;

// Monk and Multiplication HackerEarth
public class MonkAndMultiplication {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N;
	PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

	void solve() throws IOException {
		N = rd.nextInt();
		// max3 < max2 < max1
		for (int i = 1, max3, max2, max1; i <= N; i++) {
			pq.add(rd.nextInt());
			if (i < 3)
				pw.println(-1);

			else {
				max1 = pq.remove();
				max2 = pq.remove();
				max3 = pq.remove();

				pw.println(1L * max1 * max2 * max3);

				pq.add(max1);
				pq.add(max2);
				pq.add(max3);
			}
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new MonkAndMultiplication().solve();
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