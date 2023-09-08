package lec03;

import java.io.*;

// Sort the Array Codeforces
public class SortTheArray {
	MyReader rd = new MyReader();
	int n, a[], left, right;

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			a[i] = rd.nextInt();

		left = right = 0;
		for (int i = 0; i < n - 1; i++)
			if (a[i] > a[i + 1]) {
				left = i;
				while (i < n - 1 && a[i] > a[i + 1])
					i++;
				right = i;
				break;
			}

		for (int start = left, end = right, tmp; start < end; start++, end--) {
			tmp = a[start];
			a[start] = a[end];
			a[end] = tmp;
		}

		for (int i = 0; i < n - 1; i++)
			if (a[i] > a[i + 1]) {
				System.out.print("no");
				return;
			}

		System.out.printf("yes\n%d %d", ++left, ++right);
	}

	public static void main(String[] args) throws IOException {
		new SortTheArray().solve();
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