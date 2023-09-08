package lec07;

import java.io.*;
import java.util.*;

// Restaurant Rating CodeChef
public class RestaurantRating {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, cmd, nReviews;
	PriorityQueue<Integer> pqMin = new PriorityQueue<>(); // displayed
	PriorityQueue<Integer> pqMax = new PriorityQueue<>(Collections.reverseOrder());

	void solve() throws IOException {
		N = rd.nextInt();
		for (int val; N-- > 0;) {
			cmd = rd.nextInt();
			if (cmd == 1) {
				val = rd.nextInt();
				
				if (!pqMin.isEmpty() && val > pqMin.peek()) {
					pqMax.add(pqMin.remove());
					pqMin.add(val);
				} else
					pqMax.add(val);
				
				if (++nReviews % 3 == 0)
					pqMin.add(pqMax.remove());
				
			} else
				pw.println(nReviews > 2 ? pqMin.peek() : "No reviews yet");
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new RestaurantRating().solve();
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