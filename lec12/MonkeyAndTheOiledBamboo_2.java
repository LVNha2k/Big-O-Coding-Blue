package lec12;

import java.io.*;

// Monkey and the Oiled Bamboo UVa
public class MonkeyAndTheOiledBamboo_2 {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 100001;
	int T, n, arr[] = new int[MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1, max_K, min_K; tc <= T; tc++) {
			n = rd.nextInt();

			max_K = 1;
			for (int i = 1; i <= n; i++) {
				arr[i] = rd.nextInt();
				max_K = Math.max(max_K, arr[i] - arr[i - 1]);
			}

			min_K = max_K;

			for (int i = 1, jump; i <= n; i++) {
				jump = arr[i] - arr[i - 1];
				if (jump == max_K)
					max_K--;
				else if (jump > max_K) {
					min_K++;
					break;
				}
			}

			pw.printf("Case %d: %d\n", tc, min_K);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new MonkeyAndTheOiledBamboo_2().solve();
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