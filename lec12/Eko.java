package lec12;

import java.io.*;

// Eko SPOJ
public class Eko {
	MyReader rd = new MyReader();
	int N, M, arr[];

	void solve() throws IOException {
		arr = new int[N = rd.nextInt()];
		M = rd.nextInt();
		int max = 0;
		for (int i = 0; i < N; i++)
			max = Math.max(max, arr[i] = rd.nextInt());

		System.out.print(binarySearch(0, max));
	}

	// [ ]
	int binarySearch(int left, int right) {
		long sum;
		int mid, ans = 0;

		while (left <= right) {
			mid = left + (right - left) / 2;
			sum = 0;
			
			for (int i = 0; i < N; i++)
				if (arr[i] > mid)
					if ((sum += (arr[i] - mid)) >= M)
						break;

			if (sum >= M) {
				ans = mid;
				left = mid + 1;
			} else
				right = mid - 1;
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		new Eko().solve();
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