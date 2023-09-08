package lec10;

import java.io.*;
import java.util.*;

// 106 Miles to Chicago URI Online Judge
public class _106MilesToChicago_2 {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 101, MAX_M = 100 * (100 - 1) / 2;
	int n, m;
	double dist[] = new double[MAX_N];
	List<Edge> graph = new ArrayList<>(MAX_M);

	void solve() throws IOException {
		while ((n = rd.nextInt()) != 0) {
			m = rd.nextInt();
			graph.clear();

			while (m-- > 0)
				graph.add(new Edge(rd.nextInt(), rd.nextInt(), rd.nextInt() * 1.0 / 100));

			Arrays.fill(dist, -1);
			bellmanFord(1);
			pw.printf("%.6f percent\n", dist[n] * 100);
		}
		pw.close();
	}

	void bellmanFord(int start) {
		dist[start] = 1;
		for (int i = 0; i < n - 1; i++)
			for (Edge edge : graph) {
				int u = edge.source, v = edge.target;
				double w = edge.weight;

				dist[u] = Math.max(dist[u], w * dist[v]);
				dist[v] = Math.max(dist[v], w * dist[u]);
			}
	}

	public static void main(String[] args) throws IOException {
		new _106MilesToChicago_2().solve();
	}

	class Edge {
		int source, target;
		double weight;

		public Edge(int source, int target, double weight) {
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