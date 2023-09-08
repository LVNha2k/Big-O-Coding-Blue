package lec05;

import java.io.*;
import java.util.*;

// Dhoom 4 HackerEarth
public class Dhoom4 {
	MyReader rd = new MyReader();
	static final int MAX = (int) 1e5, MOD = (int) 1e5;
	int N, samKey, lockKey, arr[], dist[] = new int[MAX];

	void solve() throws IOException {
		samKey = rd.nextInt();
		lockKey = rd.nextInt();
		arr = new int[N = rd.nextInt()];
		for (int i = 0; i < N; i++)
			arr[i] = rd.nextInt();

		Arrays.fill(dist, -1);

		System.out.print(BFS());
	}

	int BFS() {
		dist[samKey] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(samKey);

		while (!queue.isEmpty()) {
			int u = queue.poll();

			for (int i = 0, v; i < N; i++) {
				v = (int) ((1L * arr[i] * u) % MOD);

				if (dist[v] == -1) {
					dist[v] = dist[u] + 1;
					queue.add(v);
					if (v == lockKey)
						return dist[v];
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) throws IOException {
		new Dhoom4().solve();
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