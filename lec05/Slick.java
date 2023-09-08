package lec05;

import java.io.*;
import java.util.*;

// Slick SPOJ
public class Slick {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 251;
	int N, M, nSlicks, image[][] = new int[MAX][MAX], count[] = new int[MAX * MAX];
	int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 };

	void solve() throws IOException {
		while (true) {
			N = rd.nextInt();
			M = rd.nextInt();
			if (N == 0 && M == 0)
				break;

			Arrays.fill(count, 0);
			nSlicks = 0;

			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++)
					image[i][j] = rd.nextInt();

			for (int i = 0; i < N; i++)
				for (int j = 0; j < M; j++)
					if (image[i][j] == 1) {
						nSlicks++;
						BFS(i, j);
					}

			pw.println(nSlicks);
			for (int size = 1; size <= N * M; size++)
				if (count[size] > 0)
					pw.println(size + " " + count[size]);
		}
		pw.close();
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < N && col >= 0 && col < M;
	}

	void BFS(int row, int col) {
		image[row][col] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(row);
		queue.add(col);
		int r, c, tmpr, tmpc, slickSize = 1;

		while (!queue.isEmpty()) {
			r = queue.remove();
			c = queue.remove();
			for (int i = 0; i < 4; i++) {
				tmpr = r + dr[i];
				tmpc = c + dc[i];
				if (isValid(tmpr, tmpc) && image[tmpr][tmpc] == 1) {
					image[tmpr][tmpc] = 0;
					queue.add(tmpr);
					queue.add(tmpc);
					slickSize++;
				}
			}
		}
		count[slickSize]++;
	}

	public static void main(String[] args) throws IOException {
		new Slick().solve();
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