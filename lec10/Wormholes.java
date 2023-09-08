package lec10;

import java.io.*;
import java.util.*;

// Wormholes UVa
public class Wormholes {
	MyReader rd = new MyReader();
	static final int MAX_N = 1000, MAX_M = 2000;
	int c, n, m, dist[] = new int[MAX_N];
	List<Edge> graph = new ArrayList<>(MAX_M);

	void solve() throws IOException {
		for (c = rd.nextInt(); c-- > 0;) {
			n = rd.nextInt();
			m = rd.nextInt();
			graph.clear();

			while (m-- > 0)
				graph.add(new Edge(rd.nextInt(), rd.nextInt(), rd.nextInt()));

			Arrays.fill(dist, Integer.MAX_VALUE);
			System.out.println(bellmanFord(0) ? "possible" : "not possible");
		}
	}

	boolean bellmanFord(int start) {
		dist[start] = 0;
		int u, v, w;
		while (n-- > 1)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
					dist[v] = dist[u] + w;
			}

		for (Edge edge : graph) {
			u = edge.source;
			v = edge.target;
			w = edge.weight;
			if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
				return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		new Wormholes().solve();
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