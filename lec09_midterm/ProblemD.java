package lec09_midterm;

import java.io.*;
import java.util.*;

// Bombs! NO they are Mines!! UVa
public class ProblemD {
	MyReader rd = new MyReader();
	static final int MAX = 1001;
	int R, C, rows, sRow, sCol, fRow, fCol, dist[][] = new int[MAX][MAX];
	int[] dr = { 0, 0, 1, -1 }, dc = { 1, -1, 0, 0 };
	boolean[][] bombs = new boolean[MAX][MAX];

	void solve() throws IOException {
		while (true) {
			if ((R = rd.nextInt()) + (C = rd.nextInt()) == 0)
				break;
			rows = rd.nextInt();
			for (boolean[] arr : bombs)
				Arrays.fill(arr, false);

			for (int row, nBomb; rows-- > 0;) {
				row = rd.nextInt();
				nBomb = rd.nextInt();
				while (nBomb-- > 0)
					bombs[row][rd.nextInt()] = true;
			}
			sRow = rd.nextInt();
			sCol = rd.nextInt();
			fRow = rd.nextInt();
			fCol = rd.nextInt();

			for (int[] arr : dist)
				Arrays.fill(arr, -1);

			BFS();
			System.out.println(dist[fRow][fCol]);
		}
	}

	void BFS() {
		dist[sRow][sCol] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(sRow);
		queue.add(sCol);

		while (!queue.isEmpty()) {
			int r = queue.remove(), c = queue.remove();
			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];

				if (isValid(tmpr, tmpc) && !bombs[tmpr][tmpc] && dist[tmpr][tmpc] == -1) {
					dist[tmpr][tmpc] = dist[r][c] + 1;
					if (tmpr == fRow && tmpc == fCol)
						return;
					queue.add(tmpr);
					queue.add(tmpc);
				}
			}
		}
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < R && col >= 0 && col < C;
	}

	public static void main(String[] args) throws IOException {
		new ProblemD().solve();
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