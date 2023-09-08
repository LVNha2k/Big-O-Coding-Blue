package lec12;

import java.io.*;
import java.util.*;

// Pizzamania SPOJ
public class Pizzamania {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int t, n, m, arr[];

	void solve() throws IOException {
		t = rd.nextInt();
		for (int count; t-- > 0;) {
			arr = new int[n = rd.nextInt()];
			m = rd.nextInt();

			for (int i = 0; i < n; i++)
				arr[i] = rd.nextInt();
			Arrays.sort(arr);

			count = 0;
			for (int i = 0, x; i < n; i++) {
				x = m - arr[i];
				if (Arrays.binarySearch(arr, i + 1, n, x) >= 0) // [ )
					count++;
			}
			
			pw.println(count);
		}
		pw.flush();
	}

	public static void main(String[] args) throws IOException {
		new Pizzamania().solve();
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