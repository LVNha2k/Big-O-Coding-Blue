package lec05;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Breadth First Search: Shortest Reach HackerRank
public class BFS_ShortestReach {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 1001;
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX_N).collect(Collectors.toList());
	int q, n, m, dist[] = new int[MAX_N];

	void solve() throws IOException {
		q = rd.nextInt();
		while (q-- > 0) {
			n = rd.nextInt();
			m = rd.nextInt();

			for (List<Integer> list : graph)
				list.clear();
			Arrays.fill(dist, -1);

			for (int u, v; m-- > 0;) {
				graph.get(u = rd.nextInt()).add(v = rd.nextInt());
				graph.get(v).add(u);
			}

			int start = rd.nextInt();
			BFS(start);

			for (int i = 1; i <= n; i++)
				if (i != start)
					pw.printf("%d ", dist[i] > 0 ? dist[i] * 6 : -1);

			pw.println();
		}
		pw.close();
	}

	void BFS(int start) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		dist[start] = 0;

		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int i = 0, v; i < graph.get(u).size(); i++) {
				v = graph.get(u).get(i);
				if (dist[v] == -1) {
					dist[v] = dist[u] + 1;
					queue.add(v);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new BFS_ShortestReach().solve();
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