package lec10;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 106 Miles to Chicago URI Online Judge
public class _106MilesToChicago {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 101;
	int n, m;
	double dist[] = new double[MAX_N];
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX_N).collect(Collectors.toList());

	void solve() throws IOException {
		while ((n = rd.nextInt()) != 0) {
			m = rd.nextInt();
			for (List<Pair> li : graph)
				li.clear();

			for (int a, b, p; m-- > 0;) {
				graph.get(a = rd.nextInt()).add(new Pair(b = rd.nextInt(), p = rd.nextInt()));
				graph.get(b).add(new Pair(a, p));
			}

			Arrays.fill(dist, 0);
			dijkstra(1);
			pw.printf("%.6f percent\n", dist[n] * 100);
		}
		pw.close();
	}

	void dijkstra(int start) {
		dist[start] = 1.0;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 1.0));

		while (!pq.isEmpty()) {
			Pair p = pq.remove();
			int u = p.id;
			double w = p.dist;
			if (Double.compare(dist[u], w) != 0)
				continue;

			for (Pair pv : graph.get(u))
				if (w * pv.dist > dist[pv.id]) {
					dist[pv.id] = w * pv.dist;
					pq.add(new Pair(pv.id, dist[pv.id]));
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new _106MilesToChicago().solve();
	}

	class Pair implements Comparable<Pair> {
		int id;
		double dist;

		public Pair(int id, int dist) {
			this.id = id;
			this.dist = dist * 1.0 / 100;
		}

		public Pair(int id, double dist) {
			this.id = id;
			this.dist = dist;
		}

		@Override
		public int compareTo(Pair that) {
			return Double.compare(that.dist, this.dist);
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