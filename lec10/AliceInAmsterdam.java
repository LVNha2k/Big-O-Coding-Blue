package lec10;

import java.io.*;
import java.util.*;

// Alice in Amsterdam, I mean Wonderland SPOJ
public class AliceInAmsterdam {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final long MAX_INF = (long) 1e15, MIN_INF = -MAX_INF;
	static final int MAX_N = 100;
	long[][] dist = new long[MAX_N][MAX_N];
	boolean[] hasDist = new boolean[MAX_N];
	int N, Q;
	List<Edge> graph = new ArrayList<>(MAX_N * MAX_N);
	List<String> monuments = new ArrayList<>(MAX_N);

	void solve() throws IOException {
		for (int tc = 1; (N = rd.nextInt()) != 0; tc++) {
			monuments.clear();
			graph.clear();

			for (int i = 0, w; i < N; i++) {
				monuments.add(rd.next());
				for (int j = 0; j < N; j++) {
					if ((w = rd.nextInt()) == 0 && i != j)
						continue;
					graph.add(new Edge(i, j, w));
				}
			}

			pw.printf("Case #%d:\n", tc);
			Arrays.fill(hasDist, false);

			Q = rd.nextInt();
			for (int st, fi; Q-- > 0;) {
				st = rd.nextInt();
				fi = rd.nextInt();

				if (!hasDist[st]) {
					Arrays.fill(dist[st], MAX_INF);
					bellmanFord(st, dist[st]);
					hasDist[st] = true;
				}
				printlnDist(st, fi);
			}
		}
		pw.close();
	}

	void bellmanFord(int start, long[] dis) {
		dis[start] = 0;
		int u, v, w;
		for (int i = 0; i < N - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dis[u] != MAX_INF && dis[u] + w < dis[v])
					dis[v] = dis[u] + w;
			}

		for (int i = 0; i < N - 1; i++)
			for (Edge edge : graph) {
				u = edge.source;
				v = edge.target;
				w = edge.weight;
				if (dis[u] != MAX_INF && dis[u] + w < dis[v])
					dis[v] = MIN_INF;
			}
	}

	void printlnDist(int st, int fi) {
		if (dist[st][fi] == MIN_INF)
			pw.println("NEGATIVE CYCLE");
		else if (dist[st][fi] == MAX_INF)
			pw.printf("%s-%s NOT REACHABLE\n", monuments.get(st), monuments.get(fi));
		else
			pw.printf("%s-%s %d\n", monuments.get(st), monuments.get(fi), dist[st][fi]);
	}

	public static void main(String[] args) throws IOException {
		new AliceInAmsterdam().solve();
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