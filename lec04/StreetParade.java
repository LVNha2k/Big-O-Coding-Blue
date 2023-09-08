package lec04;

import java.io.*;
import java.util.*;

// Street Parade SPOJ
public class StreetParade {
	MyReader rd = new MyReader();
	static final int MAX = 1001;
	int n, trucks[] = new int[MAX];
	Stack<Integer> side_trucks = new Stack<>();

	void solve() throws IOException {
		for (int ordering; (n = rd.nextInt()) > 0;) {
			for (int i = 0; i < n; i++)
				trucks[i] = rd.nextInt();

			ordering = 1;
			side_trucks.clear();

			for (int i = 0; i < n;)
				if (trucks[i] == ordering) {
					ordering++;
					i++;
				} else if (!side_trucks.empty() && side_trucks.peek().equals(ordering)) {
					ordering++;
					side_trucks.pop();
				} else
					side_trucks.push(trucks[i++]);

			while (!side_trucks.empty() && side_trucks.peek().equals(ordering)) {
				ordering++;
				side_trucks.pop();
			}

			System.out.println(ordering == n + 1 ? "yes" : "no");
		}
	}

	public static void main(String[] args) throws IOException {
		new StreetParade().solve();
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