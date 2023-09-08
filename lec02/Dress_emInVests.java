package lec02;

import java.io.*;

// Dress'em in Vests! Codeforces
public class Dress_emInVests {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m, x, y, a[], b[];

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		b = new int[m = rd.nextInt()];
		x = rd.nextInt();
		y = rd.nextInt();
		for (int i = 0; i < n; i++)
			a[i] = rd.nextInt();
		for (int i = 0; i < m; i++)
			b[i] = rd.nextInt();

		StringBuilder stb = new StringBuilder();
		int i = 0, j = 0, k = 0, size = 0;

		while (i < n && j < m) {
			size = a[i];
			if (size - x <= b[j] && b[j] <= size + y) {
				k++;
				stb.append(++i + " " + ++j + '\n');
			} else if (size - x > b[j])
				j++;
			else if (b[j] > size + y)
				i++;
		}

		pw.println(k);
		pw.print(stb.toString());
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new Dress_emInVests().solve();
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