package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// The Shortest Path SPOJ
public class TheShortestPath {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 10001, INF = (int) 1e9 + 7;
	int s, n, p, r, dist[] = new int[MAX];
	Map<String, Integer> map = new HashMap<>();
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		s = rd.nextInt();
		while (s-- > 0) {
			n = rd.nextInt();
			map.clear();
			for (int i = 1; i <= n; i++)
				graph.get(i).clear();

			for (int i = 1; i <= n; i++) {
				map.put(rd.next(), i);
				p = rd.nextInt();
				while (p-- > 0)
					graph.get(i).add(new Pair(rd.nextInt(), rd.nextInt()));
			}

			r = rd.nextInt();
			for (int dest; r-- > 0;) {
				Arrays.fill(dist, 1, n + 1, INF);
				dijkstra(map.get(rd.next()), dest = map.get(rd.next()));
				pw.println(dist[dest]);
			}
		}
		pw.close();
	}

	void dijkstra(int s, int f) {
		dist[s] = 0;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(s, 0));

		while (!pq.isEmpty()) {
			Pair p = pq.remove();
			int u = p.id, w = p.dist;
			if (u == f)
				break;
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
		new TheShortestPath().solve();
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