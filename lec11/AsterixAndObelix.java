package lec11;

import java.io.*;
import java.util.*;

// Asterix and Obelix UVa
public class AsterixAndObelix {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 81, INF = (int) 1e9 + 7;
	int C, R, Q, move[][] = new int[MAX][MAX], feast[][] = new int[MAX][MAX], meal[] = new int[MAX];

	void solve() throws IOException {
		C = rd.nextInt();
		for (int tc = 1, s, t; C != 0; tc++) {
			R = rd.nextInt();
			Q = rd.nextInt();
			for (int i = 1; i <= C; i++)
				meal[i] = feast[i][i] = rd.nextInt();

			for (int i = 1; i <= C; i++) {
				Arrays.fill(move[i], INF);
				move[i][i] = 0;
			}
			while (R-- > 0) {
				move[s = rd.nextInt()][t = rd.nextInt()] = move[t][s] = rd.nextInt();
				feast[s][t] = feast[t][s] = Math.max(meal[s], meal[t]);
			}

			floydWarshall();
			floydWarshall();

			pw.printf("Case #%d\n", tc);
			while (Q-- > 0)
				pw.println((move[s = rd.nextInt()][t = rd.nextInt()] >= INF) ? -1 : move[s][t] + feast[s][t]);

			if ((C = rd.nextInt()) != 0)
				pw.println();
		}
		pw.close();
	}

	void floydWarshall() {
		int newMove, maxFeast;
		for (int k = 1; k <= C; k++)
			for (int u = 1; u <= C; u++) {
				if (move[u][k] == INF)
					continue;
				
				for (int v = 1; v <= C; v++)
					if (move[k][v] != INF) {
						newMove = move[u][k] + move[k][v];
						maxFeast = Math.max(feast[u][k], feast[k][v]);

						if (newMove + maxFeast < move[u][v] + feast[u][v]) {
							move[u][v] = move[v][u] = newMove;
							feast[u][v] = feast[v][u] = maxFeast;
						}
					}
			}
	}

	public static void main(String[] args) throws IOException {
		new AsterixAndObelix().solve();
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