package lec03;

import java.io.*;
import java.util.*;

// Eight Point Sets Codeforces
public class EightPointSets {
	MyReader rd = new MyReader();
	static final int MAX = (int) 1e6 + 1;
	boolean[] freqX = new boolean[MAX], freqY = new boolean[MAX];
	List<Integer> uniqueX = new ArrayList<>(3), uniqueY = new ArrayList<>(3);
	List<MyPoint> points = new ArrayList<>(8);

	void solve() throws IOException {
		int x, y, times = 8;
		while (times-- > 0) {
			points.add(new MyPoint(x = rd.nextInt(), y = rd.nextInt()));
			if (!freqX[x]) {
				freqX[x] = true;
				uniqueX.add(x);
			}
			if (!freqY[y]) {
				freqY[y] = true;
				uniqueY.add(y);
			}
		}
		if (uniqueX.size() != 3 || uniqueY.size() != 3) {
			System.out.print("ugly");
			return;
		}
		Collections.sort(uniqueX);
		Collections.sort(uniqueY);
		Collections.sort(points);
		int index = 0;
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					continue;
				x = points.get(index).x;
				y = points.get(index).y;
				
				if (uniqueX.get(i) == x && uniqueY.get(j) == y)
					index++;
				else {
					System.out.print("ugly");
					return;
				}
			}
		
		System.out.print("respectable");
	}

	public static void main(String[] args) throws IOException {
		new EightPointSets().solve();
	}

	class MyPoint implements Comparable<MyPoint> {
		int x, y;

		public MyPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(MyPoint that) {
			int c = Integer.compare(this.x, that.x);
			return c != 0 ? c : Integer.compare(this.y, that.y);
		}
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