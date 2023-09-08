package lec05;

import java.io.*;
import java.util.*;

// Validate The Maze SPOJ
public class ValidateTheMaze {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 21;
	int t, m, n, dr[] = { -1, 1, 0, 0 }, dc[] = { 0, 0, -1, 1 };
	boolean[][] maze = new boolean[MAX][MAX], visited = new boolean[MAX][MAX];
	List<Integer> openings = new ArrayList<>();

	void checkOpening(int row, int col) {
		if (row == 0 || row == m - 1 || col == 0 || col == n - 1) {
			openings.add(row);
			openings.add(col);
		}
	}

	void solve() throws IOException {
		t = rd.nextInt();
		while (t-- > 0) {
			m = rd.nextInt();
			n = rd.nextInt();
			for (boolean[] arr : maze)
				Arrays.fill(arr, false);
			openings.clear();

			for (int i = 0; i < m; i++)
				for (int j = 0; j < n; j++) {
					char c = rd.nextPrintableChar();
					if (c == '.') {
						maze[i][j] = true;
						checkOpening(i, j);
					}
				}

			if (openings.size() != 4) {
				pw.println("invalid");
				continue;
			}

			for (boolean[] arr : visited)
				Arrays.fill(arr, false);

			pw.println(BFS() ? "valid" : "invalid");
		}
		pw.close();
	}

	boolean isSpace(int row, int col) {
		return row >= 0 && row < m && col >= 0 && col < n && maze[row][col] == true;
	}

	boolean BFS() {
		int rowEntry = openings.get(0), colEntry = openings.get(1);
		int rowExit = openings.get(2), colExit = openings.get(3);
		visited[rowEntry][colEntry] = true;

		Queue<Integer> queue = new LinkedList<>();
		queue.add(rowEntry);
		queue.add(colEntry);

		while (!queue.isEmpty()) {
			int r = queue.poll(), c = queue.poll();
			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];

				if (isSpace(tmpr, tmpc) && !visited[tmpr][tmpc]) {
					if (tmpr == rowExit && tmpc == colExit)
						return true;

					visited[tmpr][tmpc] = true;
					queue.add(tmpr);
					queue.add(tmpc);
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		new ValidateTheMaze().solve();
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