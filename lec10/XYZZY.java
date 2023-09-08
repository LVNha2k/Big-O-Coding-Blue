package lec10;

import java.io.*;
import java.util.*;

// XYZZY UVa
public class XYZZY {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 101, MAX_INF = (int) 1e9 + 7, MIN_INF = -MAX_INF;
	int n, dist[] = new int[MAX];
	boolean visited[] = new boolean[MAX];
	List<Edge> graph = new ArrayList<>();

	void solve() throws IOException {
		while ((n = rd.nextInt()) != -1) {
			graph.clear();

			for (int u = 1, energy, nLeaving; u <= n; u++) {
				energy = rd.nextInt();
				nLeaving = rd.nextInt();
				while (nLeaving-- > 0)
					graph.add(new Edge(u, rd.nextInt(), energy));
			}

			Arrays.fill(dist, MIN_INF);
			pw.println(bellmanFord(1, n) ? "winnable" : "hopeless");
		}
		pw.close();
	}

	boolean bellmanFord(int st, int fi) {
		dist[st] = 100;
		int u, v, w;
		for (int i = 0; i < n - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dist[u] > 0 && dist[u] + w > dist[v])
					dist[v] = dist[u] + w;
			}

		if (dist[fi] > 0)
			return true;

		for (Edge edge : graph) {
			u = edge.source;
			v = edge.target;
			w = edge.weight;
			if (dist[u] > 0 && dist[u] + w > dist[v] && hasPathBFS(u, fi))
				return true;
		}
		return false;
	}

	boolean hasPathBFS(int s, int f) {
		Arrays.fill(visited, false);
		visited[s] = true;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(s);

		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (Edge edge : graph)
				if (edge.source == u) {
					int v = edge.target;

					if (!visited[v]) {
						visited[v] = true;
						queue.add(v);

						if (v == f)
							return true;
					}
				}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		new XYZZY().solve();
	}

	class Edge {
		int source, target, weight;

		public Edge() {
		}

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