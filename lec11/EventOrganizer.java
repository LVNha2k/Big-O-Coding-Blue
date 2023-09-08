package lec11;

import java.io.*;
import java.util.*;

// Event Organizer CodeChef
public class EventOrganizer {
	MyReader rd = new MyReader();
	static final int MAX = 49;
	int T, N, dist[][] = new int[MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int S, E; T-- > 0;) {
			for (int[] arr : dist)
				Arrays.fill(arr, 0);

			N = rd.nextInt();
			while (N-- > 0)
				dist[S = rd.nextInt()][E = rd.nextInt()] = Math.max(dist[S][E], rd.nextInt());

			floydWarshall();
			System.out.println(dist[0][48]);
		}
	}

	void floydWarshall() {
		for (int k = 0; k < MAX; k++)
			for (int u = 0; u < MAX; u++)
				for (int v = 0; v < MAX; v++)
					if (u <= k && k <= v)
						dist[u][v] = Math.max(dist[u][v], dist[u][k] + dist[k][v]);
	}

	public static void main(String[] args) throws IOException {
		new EventOrganizer().solve();
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