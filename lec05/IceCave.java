package lec05;

import java.io.*;
import java.util.*;

// Ice Cave Codeforces
public class IceCave {
	MyReader rd = new MyReader();
	int n, m, r1, c1, r2, c2;
	int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 };
	char[][] mat;

	void solve() throws IOException {
		mat = new char[n = rd.nextInt()][];
		m = rd.nextInt();
		for (int i = 0; i < n; i++)
			mat[i] = rd.next().toCharArray();
		r1 = rd.nextInt() - 1;
		c1 = rd.nextInt() - 1;
		r2 = rd.nextInt() - 1;
		c2 = rd.nextInt() - 1;

		System.out.print(BFS() ? "YES" : "NO");
	}

	boolean BFS() {
		mat[r1][c1] = 'X';
		Queue<Integer> queue = new LinkedList<>();
		queue.add(r1);
		queue.add(c1);

		while (!queue.isEmpty()) {
			int r = queue.remove(), c = queue.remove();
			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];

				if (tmpr == r2 && tmpc == c2 && mat[r2][c2] == 'X')
					return true;

				if (isValid(tmpr, tmpc) && mat[tmpr][tmpc] == '.') {
					mat[tmpr][tmpc] = 'X';
					queue.add(tmpr);
					queue.add(tmpc);
				}
			}
		}
		return false;
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < n && col >= 0 && col < m;
	}

	public static void main(String[] args) throws IOException {
		new IceCave().solve();
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