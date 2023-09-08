package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Almost Shortest Path UVa
public class AlmostShortestPath {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 500, INF = (int) 1e9 + 7;
	int N, M, S, D, distS[] = new int[MAX], distD[] = new int[MAX], dist[] = new int[MAX];
	List<List<Pair>> graphS = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());
	List<List<Pair>> graphD = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		int U, V, P, minDist;
		while (true) {
			if ((N = rd.nextInt()) + (M = rd.nextInt()) == 0)
				break;
			S = rd.nextInt();
			D = rd.nextInt();

			for (int i = 0; i < N; i++) {
				graphS.get(i).clear();
				graphD.get(i).clear();
				graph.get(i).clear();
			}

			while (M-- > 0) {
				graphS.get(U = rd.nextInt()).add(new Pair(V = rd.nextInt(), P = rd.nextInt()));
				graphD.get(V).add(new Pair(U, P));
			}

			Arrays.fill(distS, 0, N, INF);
			dijkstra(S, distS, graphS);
			minDist = distS[D];

			Arrays.fill(distD, 0, N, INF);
			dijkstra(D, distD, graphD);

			for (int u = 0; u < N; u++)
				for (Pair p : graphS.get(u)) {
					int v = p.id, w = p.dist;
					if (distS[u] + w + distD[v] != minDist)
						graph.get(u).add(p);
				}

			Arrays.fill(dist, 0, N, INF);
			dijkstra(S, dist, graph);

			pw.println(dist[D] < INF ? dist[D] : -1);
		}
		pw.close();
	}

	void dijkstra(int start, int[] dist, List<List<Pair>> graph) {
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
		new AlmostShortestPath().solve();
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