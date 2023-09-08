package lec11;

import java.io.*;

// Greg and Graph Codeforces
public class GregAndGraph {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, dist[][], delete[];
	long[] ans;

	void solve() throws IOException {
		n = rd.nextInt();
		dist = new int[n + 1][n + 1];
		delete = new int[n + 1]; // middle vertices
		ans = new long[n + 1];

		for (int row = 1; row <= n; row++)
			for (int col = 1; col <= n; col++)
				dist[row][col] = rd.nextInt();
		for (int i = 0; i < n; i++)
			delete[i] = rd.nextInt();

		floydWarshall();

		for (int i = 0; i < n; i++)
			pw.print(ans[i] + " ");
		pw.close();
	}

	void floydWarshall() {
		long sum;
		for (int index = n - 1, k; index >= 0; index--) {
			k = delete[index];
			for (int u = 1; u <= n; u++)
				for (int v = 1; v <= n; v++)
					dist[u][v] = Math.min(dist[u][v], dist[u][k] + dist[k][v]);

			sum = 0;
			for (int i = index; i < n; i++) {
				int u = delete[i];
				for (int j = index; j < n; j++) {
					int v = delete[j];
					sum += dist[u][v];
				}
			}
			ans[index] = sum;
		}
	}

	public static void main(String[] args) throws IOException {
		new GregAndGraph().solve();
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