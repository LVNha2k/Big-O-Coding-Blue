package lec11;

import java.io.*;
import java.util.*;

// Possible Friends SPOJ
public class PossibleFriends {
	MyReader rd = new MyReader();
	static final int MAX_M = 50, INF = (int) 1e9 + 7;
	int T, M, dist[][] = new int[MAX_M][MAX_M];

	void solve() throws IOException {
		T = rd.nextInt();
		String str;
		for (int count, maxCount, minId; T-- > 0;) {
			M = (str = rd.next()).length();
			
			for (int[] arr : dist)
				Arrays.fill(arr, INF);

			dist[0][0] = 0;
			for (int i = 1; i < M; i++)
				if (str.charAt(i) == 'Y')
					dist[0][i] = 1;

			for (int row = 1; row < M; row++)
				for (int col = 0; col < M; col++)
					if (rd.nextPrintableChar() == 'Y')
						dist[row][col] = 1;
					else if (row == col)
						dist[row][col] = 0;

			floydWarshall();
			count = maxCount = minId = 0;
			
			for (int i = 0; i < M; i++) {
				count = 0;
				for (int j = 0; j < M; j++)
					if (dist[i][j] == 2)
						count++;
				if (count > maxCount) {
					maxCount = count;
					minId = i;
				}
			}

			System.out.println(minId + " " + maxCount);
		}
	}

	void floydWarshall() {
		for (int k = 0; k < M; k++)
			for (int i = 0; i < M; i++) {
				if (dist[i][k] == INF)
					continue;
				for (int j = 0; j < M; j++)
					if (dist[k][j] != INF)
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
			}
	}

	public static void main(String[] args) throws IOException {
		new PossibleFriends().solve();
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

		public char nextPrintableChar() throws IOException {
			do {
				c = read();
			} while (c < 32 || c > 126); // ascii
			return (char) c;
		}

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
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

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}