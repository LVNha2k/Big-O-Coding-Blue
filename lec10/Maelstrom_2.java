package lec10;

import java.io.*;
import java.util.*;

// Maelstrom UVa
public class Maelstrom_2 {
	MyReader rd = new MyReader();
	static final int MAX_N = 101, INF = (int) 1e9 + 7;
	int n, dist[] = new int[MAX_N];
	List<Edge> graph = new ArrayList<>();

	void solve() throws IOException {
		n = rd.nextInt();
		String str;

		for (int row = 2; row <= n; row++)
			for (int col = 1; col < row; col++)
				if (!(str = rd.next()).equals("x")) {
					graph.add(new Edge(row, col, Integer.parseInt(str)));
					graph.add(new Edge(col, row, Integer.parseInt(str)));
				}

		Arrays.fill(dist, INF);
		bellmanFord(1);
		int ans = 0;
		for (int i = 1; i <= n; i++)
			ans = Math.max(ans, dist[i]);

		System.out.print(ans);
	}

	void bellmanFord(int start) {
		dist[start] = 0;
		for (int i = 0, u, v, w; i < n - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				dist[v] = Math.min(dist[v], dist[u] + w);
			}
	}

	public static void main(String[] args) throws IOException {
		new Maelstrom_2().solve();
	}

	class Edge {
		int source, target, weight;

		public Edge(int source, int target, int weight) {
			this.source = source;
			this.target = target;
			this.weight = weight;
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