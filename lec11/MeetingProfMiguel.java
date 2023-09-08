package lec11;

import java.io.*;
import java.util.*;

// Meeting Prof. Miguel ... UVa
public class MeetingProfMiguel {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int INF = (int) 1e9 + 7;
	int N, young, prof, minEffort, distY[][] = new int[26][26], distM[][] = new int[26][26];

	int ind(String str) {
		return str.charAt(0) - 65;
	}

	void solve() throws IOException {
		while ((N = rd.nextInt()) != 0) {
			for (int i = 0; i < 26; i++) {
				Arrays.fill(distY[i], INF);
				Arrays.fill(distM[i], INF);
				distY[i][i] = distM[i][i] = 0;
			}

			for (int s, t, w, dist[][]; N-- > 0;) {
				dist = rd.next().equals("Y") ? distY : distM;
				String UB = rd.next();
				dist[s = ind(rd.next())][t = ind(rd.next())] = Math.min(dist[s][t], w = rd.nextInt());
				if (UB.equals("B"))
					dist[t][s] = Math.min(dist[t][s], w);
			}

			young = ind(rd.next());
			prof = ind(rd.next());

			floydWarshall(distY);
			floydWarshall(distM);
			minEffort = INF;

			for (int i = 0; i < 26; i++)
				minEffort = Math.min(minEffort, distY[young][i] + distM[prof][i]);

			if (minEffort >= INF) {
				pw.println("You will never meet.");
				continue;
			}

			StringBuilder stb = new StringBuilder(minEffort + " ");
			for (int i = 0; i < 26; i++)
				if (distY[young][i] + distM[prof][i] == minEffort)
					stb.append((char) (i + 65) + " ");

			pw.println(stb.substring(0, stb.length() - 1));
		}
		pw.close();
	}

	void floydWarshall(int[][] dist) {
		for (int k = 0; k < 26; k++)
			for (int i = 0; i < 26; i++) {
				if (dist[i][k] == INF)
					continue;
				for (int j = 0; j < 26; j++)
					if (dist[k][j] != INF)
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
			}
	}

	public static void main(String[] args) throws IOException {
		new MeetingProfMiguel().solve();
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