package lec12;

import java.io.*;

// Solve It UVa
public class SolveIt {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int p, q, r, s, t, u;

	double f(double x) {
		return p * Math.exp(-x) + q * Math.sin(x) + r * Math.cos(x) + s * Math.tan(x) + t * Math.pow(x, 2) + u;
	}

	void solve() throws IOException {
		for (double left, right, mid;;) {
			p = rd.nextInt();
			if (rd.isEOF())
				break;
			q = rd.nextInt();
			r = rd.nextInt();
			s = rd.nextInt();
			t = rd.nextInt();
			u = rd.nextInt();

			if (f(0) < 0 || f(1) > 0) {
				pw.println("No solution");
				continue;
			}

			left = 0;
			right = 1;
			while (right - left > 1e-8) {
				mid = (left + right) / 2;

				if (f(mid) > 0)
					left = mid;
				else
					right = mid;
			}

			pw.printf("%.4f\n", left);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new SolveIt().solve();
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

		public boolean isEOF() {
			return buffer[0] == -1;
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