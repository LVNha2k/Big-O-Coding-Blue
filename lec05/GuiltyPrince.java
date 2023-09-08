package lec05;

import java.io.*;
import java.util.*;

// Guilty Prince LightOJ
public class GuiltyPrince {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 21;
	int T, W, H, sRow, sCol, dr[] = { -1, 1, 0, 0 }, dc[] = { 0, 0, -1, 1 };
	boolean[][] mat = new boolean[MAX][MAX], visited = new boolean[MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			W = rd.nextInt();
			H = rd.nextInt();
			for (boolean[] arr : mat)
				Arrays.fill(arr, false);

			for (int i = 0; i < H; i++)
				for (int j = 0; j < W; j++) {
					char c = rd.nextPrintableChar();
					if (c == '.' || c == '@')
						mat[i][j] = true;
					if (c == '@') {
						sRow = i;
						sCol = j;
					}
				}

			for (boolean[] arr : visited)
				Arrays.fill(arr, false);

			pw.printf("Case %d: %d\n", tc, BFS());
		}
		pw.close();
	}

	boolean isLand(int row, int col) {
		return row >= 0 && row < H && col >= 0 && col < W && mat[row][col];
	}

	int BFS() {
		int count = 1;
		visited[sRow][sCol] = true;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(sRow);
		queue.add(sCol);

		while (!queue.isEmpty()) {
			int r = queue.remove(), c = queue.remove();

			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];

				if (isLand(tmpr, tmpc) && !visited[tmpr][tmpc]) {
					visited[tmpr][tmpc] = true;
					queue.add(tmpr);
					queue.add(tmpc);
					count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
		new GuiltyPrince().solve();
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