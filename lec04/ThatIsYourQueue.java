package lec04;

import java.io.*;
import java.util.*;

// That is Your Queue UVa
public class ThatIsYourQueue {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int P, C, x;
	Deque<Integer> deque = new LinkedList<>();

	void solve() throws IOException {
		for (int tc = 1;; tc++) {
			P = rd.nextInt();
			C = rd.nextInt();
			if (P == 0 && C == 0)
				break;
			pw.printf("Case %d:\n", tc);

			deque.clear();
			for (int i = 1; i <= Math.min(P, C); i++)
				deque.add(i);

			while (C-- > 0)
				if (rd.next().equals("N")) {
					pw.println(x = deque.poll());
					deque.addLast(x);
				} else {
					deque.removeLastOccurrence(x = rd.nextInt());
					deque.addFirst(x);
				}
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new ThatIsYourQueue().solve();
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}