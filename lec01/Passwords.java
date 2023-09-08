package lec01;

import java.io.*;

// Passwords Codeforces
public class Passwords {
	MyReader rd = new MyReader();
	int n, k;
	String password;

	void solve() throws IOException {
		n = rd.nextInt();
		k = rd.nextInt();
		int count[] = new int[101], best = 0, worst = 0;
		while (n-- > 0)
			count[rd.next().length()]++;

		password = rd.next();
		for (int i = 0; i < password.length(); i++)
			best += count[i];

		worst = best + count[password.length()] - 1;
		best += (best / k) * 5;
		worst += (worst / k) * 5;

		System.out.printf("%d %d", best + 1, worst + 1);
	}

	public static void main(String[] args) throws IOException {
		new Passwords().solve();
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			byte c;
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}