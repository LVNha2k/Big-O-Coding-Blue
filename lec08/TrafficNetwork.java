package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Traffic Network SPOJ
public class TrafficNetwork {
	MyReader rd = new MyReader();
	static final int MAX = 10001, INF = (int) 1e9 + 7;
	int n, m, k, s, t, dist_S[] = new int[MAX], dist_T[] = new int[MAX];
	List<List<Pair>> graph_S = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());
	List<List<Pair>> graph_T = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		int nDataSets = rd.nextInt(), u, v, q, ans;
		while (nDataSets-- > 0) {
			n = rd.nextInt();
			m = rd.nextInt();
			k = rd.nextInt();
			s = rd.nextInt();
			t = rd.nextInt();
			for (int i = 1; i <= n; i++)
				graph_S.get(i).clear();
			for (int i = 1; i <= n; i++)
				graph_T.get(i).clear();

			while (m-- > 0) {
				graph_S.get(u = rd.nextInt()).add(new Pair(v = rd.nextInt(), q = rd.nextInt()));
				graph_T.get(v).add(new Pair(u, q));
			}

			Arrays.fill(dist_S, 1, n + 1, INF);
			dijkstra(s, graph_S, dist_S);
			
			Arrays.fill(dist_T, 1, n + 1, INF);
			dijkstra(t, graph_T, dist_T);

			ans = dist_S[t];
			while (k-- > 0) {
				u = rd.nextInt();
				v = rd.nextInt();
				q = rd.nextInt();
				ans = Math.min(ans, q + Math.min(dist_S[u] + dist_T[v], dist_S[v] + dist_T[u]));
			}

			System.out.println(ans < INF ? ans : -1);
		}
	}

	void dijkstra(int start, List<List<Pair>> graph, int[] dist) {
		dist[start] = 0;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 0));

		while (!pq.isEmpty()) {
			Pair p = pq.remove();
			int u = p.id, w = p.dist;
			if (dist[u] != w)
				continue;

			for (Pair pv : graph.get(u))
				if (w + pv.dist < dist[pv.id]) {
					dist[pv.id] = w + pv.dist;
					pq.add(new Pair(pv.id, dist[pv.id]));
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new TrafficNetwork().solve();
	}

	class Pair implements Comparable<Pair> {
		int id, dist;

		public Pair(int id, int dist) {
			this.id = id;
			this.dist = dist;
		}

		@Override
		public int compareTo(Pair that) {
			return Integer.compare(this.dist, that.dist);
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