package lec12;

import java.io.*;
import java.util.*;

// Where is the Marble UVa
public class WhereIsTheMarble {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 10001;
	int N, Q, arr[] = new int[MAX];

	void solve() throws IOException {
		for (int tc = 1;; tc++) {
			if ((N = rd.nextInt()) + (Q = rd.nextInt()) == 0)
				break;

			for (int i = 0; i < N; i++)
				arr[i] = rd.nextInt();
			Arrays.sort(arr, 0, N);

			pw.printf("CASE# %d:\n", tc);

			for (int x, ind; Q-- > 0;) {
				ind = lowerBound(arr, 0, N, x = rd.nextInt());
				if (arr[ind] == x)
					pw.printf("%d found at %d\n", x, ind + 1);
				else
					pw.printf("%d not found\n", x);
			}
		}
		pw.flush();
	}

	// [ )
	int lowerBound(int[] a, int left, int right, int x) {
		int pos = right, mid;
		while (left < right) {
			mid = left + (right - left) / 2;
			if (a[mid] >= x) {
				pos = mid;
				right = mid;
			} else
				left = mid + 1;
		}
		return pos;
	}

	public static void main(String[] args) throws IOException {
		new WhereIsTheMarble().solve();
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