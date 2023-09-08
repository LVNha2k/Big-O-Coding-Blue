package lec11;

import java.io.*;
import java.util.*;

// Arbitrage UVa
public class Arbitrage {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 30;
	int n, m;
	double dist[][] = new double[MAX_N][MAX_N];
	List<String> currencies = new ArrayList<>();

	void solve() throws IOException {
		for (int tc = 1, st;; tc++) {
			if ((n = rd.nextInt()) == 0)
				break;
			currencies.clear();

			for (int i = 0; i < n; i++) {
				currencies.add(rd.next());
				Arrays.fill(dist[i], 0);
				dist[i][i] = 1.0;
			}

			m = rd.nextInt();
			for (double w; m-- > 0;) {
				st = currencies.indexOf(rd.next());
				w = rd.nextDouble();
				dist[st][currencies.indexOf(rd.next())] = w;
			}

			pw.printf("Case %d: %s\n", tc, floydWarshall() ? "Yes" : "No");
		}
		pw.close();
	}

	boolean floydWarshall() {
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++) {
				if (Double.compare(dist[i][k], 0) == 0)
					continue;
				for (int j = 0; j < n; j++)
					if (Double.compare(dist[k][j], 0) != 0)
						dist[i][j] = Math.max(dist[i][j], dist[i][k] * dist[k][j]);
			}

		for (int i = 0; i < n; i++)
			if (dist[i][i] > 1)
				return true;
		return false;
	}

	public static void main(String[] args) throws IOException {
		new Arbitrage().solve();
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

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
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