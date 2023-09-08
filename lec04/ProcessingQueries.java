package lec04;

import java.io.*;
import java.util.*;

// Processing Queries Codeforces
public class ProcessingQueries {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, b, t, d;

	void solve() throws IOException {
		n = rd.nextInt();
		b = rd.nextInt();
		long nextFreeTime = 0;
		Queue<Long> queue = new LinkedList<>();

		while (n-- > 0) {
			t = rd.nextInt();
			d = rd.nextInt();
			while (!queue.isEmpty() && queue.peek() <= t)
				queue.remove();

			if (queue.size() <= b) {
				nextFreeTime = Math.max(nextFreeTime, t) + d;
				queue.add(nextFreeTime);
				pw.print(nextFreeTime + " ");
			} else
				pw.print(-1 + " ");
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new ProcessingQueries().solve();
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