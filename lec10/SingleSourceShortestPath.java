package lec10;

import java.io.*;
import java.util.*;

// Single source shortest path, negative weights Kattis
public class SingleSourceShortestPath {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 1000, MAX_M = 5000, MAX_INF = (int) 1e9 + 7, MIN_INF = -MAX_INF;
	int n, m, q, s, dist[] = new int[MAX_N];
	List<Edge> graph = new ArrayList<>(MAX_M);

	void solve() throws IOException {
		n = rd.nextInt();
		while (n != 0) {
			m = rd.nextInt();
			q = rd.nextInt();
			s = rd.nextInt();
			graph.clear();

			while (m-- > 0)
				graph.add(new Edge(rd.nextInt(), rd.nextInt(), rd.nextInt()));

			Arrays.fill(dist, MAX_INF);
			bellmanFord(s);

			for (int dest; q-- > 0;)
				pw.println(dist[dest = rd.nextInt()] == MAX_INF ? "Impossible"
						: dist[dest] == MIN_INF ? "-Infinity" : dist[dest]);

			if ((n = rd.nextInt()) != 0)
				pw.println();
		}
		pw.close();
	}

	void bellmanFord(int start) {
		dist[start] = 0;
		int u, v, w;
		for (int i = 0; i < n - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dist[u] != MAX_INF && dist[u] + w < dist[v])
					dist[v] = dist[u] + w;
			}

		for (int i = 0; i < n - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dist[u] != MAX_INF && dist[u] + w < dist[v])
					dist[v] = MIN_INF;
			}
	}

	public static void main(String[] args) throws IOException {
		new SingleSourceShortestPath().solve();
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