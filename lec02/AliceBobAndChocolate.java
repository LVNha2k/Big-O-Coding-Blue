package lec02;

import java.io.*;

// Alice Bob and Chocolate Codeforces
public class AliceBobAndChocolate {
	MyReader rd = new MyReader();
	int n, choco[];

	void solve() throws IOException {
		choco = new int[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			choco[i] = rd.nextInt();

		int t_alice = 0, t_bob = 0, i = 0, j = n - 1;

		while (i <= j)
			if (t_alice + choco[i] <= t_bob + choco[j])
				t_alice += choco[i++];
			else
				t_bob += choco[j--];

		System.out.printf("%d %d", i, n - i);
	}

	public static void main(String[] args) throws IOException {
		new AliceBobAndChocolate().solve();
	}

	static class MyReader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;
		private boolean isWindows;
		static final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				din = new DataInputStream(file ? new FileInputStream(FILE) : System.in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
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
			if (neg)
				return -ret;
			return ret;
		}

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}