package lec05;

import java.io.*;
import java.util.*;

// Sheep SPOJ
public class Sheep {
	MyReader rd = new MyReader();
	int N, M, nSheeps, nWolves, dr[] = { -1, 1, 0, 0 }, dc[] = { 0, 0, -1, 1 };
	char[][] mat;

	void solve() throws IOException {
		mat = new char[N = rd.nextInt()][];
		M = rd.nextInt();
		for (int i = 0; i < N; i++)
			mat[i] = rd.next().toCharArray();

		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				if (mat[i][j] != '#')
					BFS(i, j);

		System.out.print(nSheeps + " " + nWolves);
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < N && col >= 0 && col < M;
	}

	void BFS(int row, int col) {
		int sheep = (mat[row][col] == 'k') ? 1 : 0;
		int wolf = (mat[row][col] == 'v') ? 1 : 0;
		mat[row][col] = '#';
		boolean canEscape = false;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(row);
		queue.add(col);

		while (!queue.isEmpty()) {
			int r = queue.remove(), c = queue.remove();
			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];

				if (!isValid(tmpr, tmpc)) {
					canEscape = true;
					continue;
				}

				if (mat[tmpr][tmpc] != '#') {
					sheep += (mat[tmpr][tmpc] == 'k') ? 1 : 0;
					wolf += (mat[tmpr][tmpc] == 'v') ? 1 : 0;
					mat[tmpr][tmpc] = '#';
					queue.add(tmpr);
					queue.add(tmpc);
				}
			}
		}
		
		if (canEscape) {
			nSheeps += sheep;
			nWolves += wolf;
		} else {
			if (sheep > wolf)
				nSheeps += sheep;
			else
				nWolves += wolf;
		}
	}

	public static void main(String[] args) throws IOException {
		new Sheep().solve();
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