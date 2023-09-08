package lec13;

import java.io.*;
import java.util.*;

// Megacity Codeforces
public class Megacity {
	MyReader rd = new MyReader();
	TreeMap<Integer, Integer> treeMap = new TreeMap<>();
	int n, s;

	void solve() throws IOException {
		n = rd.nextInt();
		s = rd.nextInt();
		for (int x, y, d2; n-- > 0;) {
			x = rd.nextInt();
			y = rd.nextInt();
			d2 = x * x + y * y;
			treeMap.put(d2, rd.nextInt() + treeMap.getOrDefault(d2, 0));
		}

		for (Map.Entry<Integer, Integer> e : treeMap.entrySet())
			if ((s += e.getValue()) >= 1000000) {
				System.out.print(Math.sqrt(e.getKey()));
				return;
			}

		System.out.print(-1);
	}

	public static void main(String[] args) throws IOException {
		new Megacity().solve();
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