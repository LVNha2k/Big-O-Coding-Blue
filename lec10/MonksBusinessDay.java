package lec10;

import java.io.*;
import java.util.*;

// Monk's Business Day HackerEarth
public class MonksBusinessDay {
	MyReader rd = new MyReader();
	static final int MAX_N = 101, MAX_M = 1000, MAX_INF = (int) 1e9 + 7, MIN_INF = -MAX_INF;
	int T, N, M, dist[] = new int[MAX_N];
	List<Edge> graph = new ArrayList<>(MAX_M);

	void solve() throws IOException {
		for (T = rd.nextInt(); T-- > 0;) {
			N = rd.nextInt();
			M = rd.nextInt();
			graph.clear();

			while (M-- > 0)
				graph.add(new Edge(rd.nextInt(), rd.nextInt(), rd.nextInt()));

			Arrays.fill(dist, MIN_INF);
			System.out.println(bellmanFord(1) ? "Yes" : "No");
		}
	}

	boolean bellmanFord(int start) {
		dist[start] = 0;
		int u, v, w;
		for (int i = 0; i < N - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dist[u] != MIN_INF && dist[u] + w > dist[v])
					dist[v] = dist[u] + w;
			}

		for (Edge edge : graph) {
			u = edge.source;
			v = edge.target;
			w = edge.weight;
			if (dist[u] != MIN_INF && dist[u] + w > dist[v])
				return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		new MonksBusinessDay().solve();
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