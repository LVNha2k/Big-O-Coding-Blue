package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Sending Email UVa
public class SendingEmail {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 20000, INF = (int) 1e9 + 7;
	int N, n, m, S, T, time[] = new int[MAX];
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		N = rd.nextInt();
		for (int tc = 1; tc <= N; tc++) {
			n = rd.nextInt();
			m = rd.nextInt();
			S = rd.nextInt();
			T = rd.nextInt();
			for (int i = 0; i < n; i++)
				graph.get(i).clear();

			for (int u, v, w; m-- > 0;) {
				graph.get(u = rd.nextInt()).add(new Pair(v = rd.nextInt(), w = rd.nextInt()));
				graph.get(v).add(new Pair(u, w));
			}

			Arrays.fill(time, 0, n, INF);
			dijkstra(S, T);

			pw.printf("Case #%d: %s\n", tc, time[T] < INF ? time[T] : "unreachable");
		}
		pw.close();
	}

	void dijkstra(int start, int fi) {
		time[start] = 0;
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.add(new Pair(start, 0));

		while (!pq.isEmpty()) {
			Pair p = pq.remove();
			int u = p.id, w = p.dist;
			if (u == fi)
				break;
			if (time[u] != w)
				continue;

			for (Pair pv : graph.get(u))
				if (w + pv.dist < time[pv.id]) {
					time[pv.id] = w + pv.dist;
					pq.add(new Pair(pv.id, time[pv.id]));
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new SendingEmail().solve();
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