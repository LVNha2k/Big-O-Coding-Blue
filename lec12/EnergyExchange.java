package lec12;

import java.io.*;

// Energy Exchange Codeforces
public class EnergyExchange {
	MyReader rd = new MyReader();
	static final int MAX_A = 1000;
	int n, k, arr[];

	void solve() throws IOException {
		arr = new int[n = rd.nextInt()];
		k = rd.nextInt();
		for (int i = 0; i < n; i++)
			arr[i] = rd.nextInt();

		double left = 0, right = MAX_A, mid;
		while (right - left > 1e-6) {
			mid = (left + right) / 2;

			if (canTransfer(mid))
				left = mid;
			else
				right = mid;
		}

		System.out.print(left);
	}

	boolean canTransfer(double target) {
		double give = 0, receive = 0;
		for (int i = 0; i < n; i++)
			if (arr[i] > target)
				give += (arr[i] - target);
			else
				receive += (target - arr[i]);

		give -= (give * k / 100.0);
		return give >= receive;
	}

	public static void main(String[] args) throws IOException {
		new EnergyExchange().solve();
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