package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Mice and Maze SPOJ
public class MiceAndMaze {
	MyReader rd = new MyReader();
	static final int MAX = 101, INF = (int) 1e9 + 7;
	int N, E, T, M, dist[] = new int[MAX];
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		N = rd.nextInt();
		E = rd.nextInt();
		T = rd.nextInt();
		M = rd.nextInt();
		for (int u, v; M-- > 0;) {
			u = rd.nextInt();
			v = rd.nextInt();
			graph.get(v).add(new Pair(u, rd.nextInt()));
		}

		int count = 0;
		Arrays.fill(dist, INF);
		dijkstra(E);

		for (int i = 1; i <= N; i++)
			if (dist[i] <= T)
				count++;

		System.out.print(count);
	}

	void dijkstra(int s) {
		dist[s] = 0;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(s, 0));

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
		new MiceAndMaze().solve();
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