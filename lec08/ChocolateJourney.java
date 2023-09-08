package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Chocolate Journey HackerEarth
public class ChocolateJourney {
	MyReader rd = new MyReader();
	static final int INF = (int) 1e9 + 7;
	int N, M, k, x, A, B, distA[], distB[], cities[];
	List<List<Pair>> graph;

	void solve() throws IOException {
		N = rd.nextInt();
		distA = new int[N + 1];
		distB = new int[N + 1];
		graph = Stream.generate(ArrayList<Pair>::new).limit(N + 1).collect(Collectors.toList());

		M = rd.nextInt();
		cities = new int[k = rd.nextInt()];
		x = rd.nextInt();

		for (int i = 0; i < k; i++)
			cities[i] = rd.nextInt();

		for (int u, v, d; M-- > 0;) {
			graph.get(u = rd.nextInt()).add(new Pair(v = rd.nextInt(), d = rd.nextInt()));
			graph.get(v).add(new Pair(u, d));
		}
		A = rd.nextInt();
		B = rd.nextInt();

		Arrays.fill(distA, INF);
		dijkstra(A, distA);

		Arrays.fill(distB, INF);
		dijkstra(B, distB);

		int ans = INF;
		for (int city : cities)
			if (distB[city] < x)
				ans = Math.min(ans, distA[city] + distB[city]);

		System.out.print(ans < INF ? ans : -1);
	}

	void dijkstra(int start, int[] dist) {
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
		new ChocolateJourney().solve();
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