package lec06;

import java.io.*;
import java.util.*;

// ABC Path SPOJ
public class ABCPath {
	MyReader rd = new MyReader();
	static final int MAX = 51;
	int H, W, maxLen;
	int[] dr = { 1, 1, 1, 0, 0, -1, -1, -1 };
	int[] dc = { 1, 0, -1, 1, -1, 1, 0, -1 };
	char[][] mat = new char[MAX][MAX];
	boolean[][] visited = new boolean[MAX][MAX];
	Queue<Integer> queue = new LinkedList<>();

	void solve() throws IOException {
		for (int tc = 1;; tc++) {
			H = rd.nextInt();
			W = rd.nextInt();
			if (H == 0 && W == 0)
				return;

			for (int i = 0; i < H; i++)
				for (int j = 0; j < W; j++) {
					mat[i][j] = rd.nextPrintableChar();
					if (mat[i][j] == 'A') {
						queue.add(i);
						queue.add(j);
					}
				}

			maxLen = 0;
			for (boolean[] arr : visited)
				Arrays.fill(arr, false);

			while (!queue.isEmpty())
				DFS(queue.remove(), queue.remove(), 1);

			System.out.printf("Case %d: %d\n", tc, maxLen);
		}
	}

	void DFS(int row, int col, int len) {
		visited[row][col] = true;
		char cur = mat[row][col];

		for (int i = 0; i < 8; i++) {
			int r = row + dr[i], c = col + dc[i];
			if (isValid(r, c) && mat[r][c] == cur + 1 && !visited[r][c])
				DFS(r, c, len + 1);
		}

		maxLen = Math.max(maxLen, len);
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < H && col >= 0 && col < W;
	}

	public static void main(String[] args) throws IOException {
		new ABCPath().solve();
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