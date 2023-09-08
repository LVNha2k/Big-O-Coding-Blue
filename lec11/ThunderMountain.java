package lec11;

import java.io.*;
import java.util.*;

// Thunder Mountain UVa
public class ThunderMountain {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 100, INF = (int) 1e9 + 7;
	int N, n, x[] = new int[MAX], y[] = new int[MAX];
	double dist[][] = new double[MAX][MAX];

	void solve() throws IOException {
		double d, ans;
		N = rd.nextInt();
		for (int tc = 1; tc <= N; tc++) {
			n = rd.nextInt();
			for (int i = 0; i < n; i++) {
				x[i] = rd.nextInt();
				y[i] = rd.nextInt();
			}

			for (double[] arr : dist)
				Arrays.fill(arr, INF);

			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					d = Math.sqrt(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
					if (d <= 10)
						dist[i][j] = d;
				}

			floydWarshall();
			ans = 0;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					ans = Math.max(ans, dist[i][j]);

			pw.printf("Case #%d:\n", tc);
			if (ans >= INF)
				pw.println("Send Kurdy");
			else
				pw.printf("%.4f\n", ans);

			if (tc < N)
				pw.println();
		}
		pw.close();
	}

	void floydWarshall() {
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++) {
				if (Double.compare(dist[i][k], INF) == 0)
					continue;
				for (int j = 0; j < n; j++)
					if (Double.compare(dist[k][j], INF) != 0)
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
			}
	}

	public static void main(String[] args) throws IOException {
		new ThunderMountain().solve();
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