package lec10;

import java.io.*;
import java.util.*;

// Extended Traffic LightOJ
public class ExtendedTraffic {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 201, MAX_INF = (int) 1e9 + 7, MIN_INF = -MAX_INF;
	int T, n, m, q, dist[] = new int[MAX], busy[] = new int[MAX];
	List<Edge> graph = new ArrayList<>();

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1, dest; tc <= T; tc++) {
			n = rd.nextInt();
			for (int i = 1; i <= n; i++)
				busy[i] = rd.nextInt();

			m = rd.nextInt();
			graph.clear();
			for (int i = 0; i < m; i++)
				graph.add(new Edge(rd.nextInt(), rd.nextInt()));

			Arrays.fill(dist, MAX_INF);
			bellmanFord(1);

			pw.printf("Case %d:\n", tc);
			q = rd.nextInt();
			while (q-- > 0) {
				dest = rd.nextInt();
				pw.println((dist[dest] < 3 || dist[dest] == MAX_INF) ? "?" : dist[dest]);
			}
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
		new ExtendedTraffic().solve();
	}

	class Edge {
		int source, target, weight;

		public Edge() {
		}

		public Edge(int source, int target) {
			this.source = source;
			this.target = target;
			this.weight = (int) Math.pow(busy[target] - busy[source], 3);
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