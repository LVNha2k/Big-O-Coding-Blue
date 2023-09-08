package lec12;

import java.io.*;

// The Playboy Chimp UVa
public class ThePlayboyChimp {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, Q, arr[];

	void solve() throws IOException {
		arr = new int[N = rd.nextInt()];
		for (int i = 0; i < N; i++)
			arr[i] = rd.nextInt();
		Q = rd.nextInt();

		for (int height, indPreLo, indUp; Q-- > 0;) {
			indPreLo = pre_LowerBound(0, N, height = rd.nextInt());
			indUp = upperBound(0, N, height);
			pw.printf("%s %s\n", indPreLo >= 0 ? arr[indPreLo] : "X", indUp >= 0 ? arr[indUp] : "X");
		}
		pw.flush();
	}

	// [ )
	int pre_LowerBound(int left, int right, int x) {
		int pos = N, mid;
		while (left < right) {
			mid = left + (right - left) / 2;

			if (arr[mid] >= x) {
				pos = mid;
				right = mid;
			} else
				left = mid + 1;
		}
		return --pos;
	}

	// [ )
	int upperBound(int left, int right, int x) {
		int pos = -1, mid;
		while (left < right) {
			mid = left + (right - left) / 2;

			if (arr[mid] > x) {
				pos = mid;
				right = mid;
			} else
				left = mid + 1;
		}
		return pos;
	}

	public static void main(String[] args) throws IOException {
		new ThePlayboyChimp().solve();
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