package lec11;

import java.io.*;
import java.util.*;

// Risk UVa
public class Risk {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 21, INF = (int) 1e9 + 7;
	int X, N, dist[][] = new int[MAX][MAX];

	void solve() throws IOException {
		X = rd.nextInt();
		for (int tc = 1, J, A, B; !rd.isEOF(); tc++) {
			for (int i = 1; i < MAX; i++) {
				Arrays.fill(dist[i], INF);
				dist[i][i] = 0;
			}

			while (X-- > 0)
				dist[1][J = rd.nextInt()] = dist[J][1] = 1;
			for (int i = 2; i <= 19; i++) {
				X = rd.nextInt();
				while (X-- > 0)
					dist[i][J = rd.nextInt()] = dist[J][i] = 1;
			}

			floydWarshall();
			pw.printf("Test Set #%d\n", tc);

			N = rd.nextInt();
			while (N-- > 0)
				pw.printf("%2d to %2d: %d\n", A = rd.nextInt(), B = rd.nextInt(), dist[A][B]);

			X = rd.nextInt();
			if (!rd.isEOF())
				pw.println();
		}
		pw.close();
	}

	void floydWarshall() {
		for (int k = 1; k < MAX; k++)
			for (int i = 1; i < MAX; i++) {
				if (dist[i][k] == INF)
					continue;
				for (int j = 1; j < MAX; j++)
					if (dist[k][j] != INF)
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
			}
	}

	public static void main(String[] args) throws IOException {
		new Risk().solve();
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

		public boolean isEOF() {
			return buffer[0] == -1;
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