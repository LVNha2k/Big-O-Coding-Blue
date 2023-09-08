package lec06;

import java.io.*;
import java.util.*;

// ALL IZZ WELL SPOJ
public class AllIzzWell {
	MyReader rd = new MyReader();
	static char[] sen = "ALLIZZWELL".toCharArray();
	static final int MAX = 101;
	int t, R, C, last = sen.length - 1;
	boolean found, visited[][] = new boolean[MAX][MAX];
	int[] dr = { 1, 1, 1, 0, 0, -1, -1, -1 };
	int[] dc = { 1, 0, -1, 1, -1, 1, 0, -1 };
	char[][] mat = new char[MAX][MAX];
	Queue<Integer> queue = new LinkedList<>();

	void solve() throws IOException {
		t = rd.nextInt();
		while (t-- > 0) {
			R = rd.nextInt();
			C = rd.nextInt();
			queue.clear();

			for (int i = 0; i < R; i++)
				for (int j = 0; j < C; j++) {
					mat[i][j] = rd.nextPrintableChar();
					if (mat[i][j] == sen[0]) {
						queue.add(i);
						queue.add(j);
					}
				}

			found = false;
			while (!queue.isEmpty() && !found)
				DFS(queue.remove(), queue.remove(), 0);

			System.out.println(found ? "YES" : "NO");
		}
	}

	void DFS(int row, int col, int ind) {
		if (ind == last) {
			found = true;
			return;
		}

		for (int i = 0; i < 8 && !found; i++) {
			int r = row + dr[i], c = col + dc[i];
			
			if (isValid(r, c) && mat[r][c] == sen[ind + 1] && !visited[r][c]) {
				visited[r][c] = true;
				DFS(r, c, ind + 1);
				visited[r][c] = false;
			}
		}
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < R && col >= 0 && col < C;
	}

	public static void main(String[] args) throws IOException {
		new AllIzzWell().solve();
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

		public char nextPrintableChar() throws IOException {
			do {
				c = read();
			} while (c < 32 || c > 126); // ascii
			return (char) c;
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