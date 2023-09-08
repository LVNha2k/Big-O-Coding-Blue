package lec07;

import java.io.*;
import java.util.*;

// Qheap 1 HackerRank
public class Qheap1 {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int Q, cmd;
	PriorityQueue<Integer> pq = new PriorityQueue<>(), pqRemove = new PriorityQueue<>();

	void solve() throws IOException {
		Q = rd.nextInt();
		while (Q-- > 0) {
			cmd = rd.nextInt();

			if (cmd == 1)
				pq.add(rd.nextInt());
			else if (cmd == 2)
				pqRemove.add(rd.nextInt());
			else {
				while (!pqRemove.isEmpty() && pq.peek().intValue() == pqRemove.peek()) {
					pq.poll();
					pqRemove.poll();
				}
				pw.println(pq.peek());
			}
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new Qheap1().solve();
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