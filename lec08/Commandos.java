package lec08;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Commandos LightOJ
public class Commandos {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 100;
	int T, N, R, s, d, dist_s[] = new int[MAX], dist_d[] = new int[MAX];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1, ans; tc <= T; tc++) {
			N = rd.nextInt();
			R = rd.nextInt();
			for (List<Integer> list : graph)
				list.clear();

			for (int u, v; R-- > 0;) {
				graph.get(u = rd.nextInt()).add(v = rd.nextInt());
				graph.get(v).add(u);
			}
			s = rd.nextInt();
			d = rd.nextInt();

			Arrays.fill(dist_s, -1);
			BFS(s, dist_s);

			Arrays.fill(dist_d, -1);
			BFS(d, dist_d);

			ans = 0;
			for (int i = 0; i < N; i++)
				ans = Math.max(ans, dist_s[i] + dist_d[i]);

			pw.printf("Case %d: %d\n", tc, ans);
		}
		pw.close();
	}

	void BFS(int start, int[] dist) {
		dist[start] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			int u = queue.remove();
			for (int v : graph.get(u))
				if (dist[v] == -1) {
					dist[v] = dist[u] + 1;
					queue.add(v);
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new Commandos().solve();
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