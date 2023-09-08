package lec06;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// The Benefactor SPOJ
// Diameter of a tree
public class TheBenefactor {
	MyReader rd = new MyReader();
	static final int MAX = 50001;
	List<List<Pair>> graph = Stream.generate(ArrayList<Pair>::new).limit(MAX).collect(Collectors.toList());
	int t, n, maxDist, leaf, dist[] = new int[MAX];

	void solve() throws IOException {
		t = rd.nextInt();
		while (t-- > 0) {
			n = rd.nextInt();
			for (int i = 1; i <= n; i++)
				graph.get(i).clear();

			for (int i = 0, u, v, w; i < n - 1; i++) {
				graph.get(u = rd.nextInt()).add(new Pair(v = rd.nextInt(), w = rd.nextInt()));
				graph.get(v).add(new Pair(u, w));
			}

			maxDist = 0;

			Arrays.fill(dist, 1, n + 1, -1);
			dist[1] = 0;
			DFS(1);

			Arrays.fill(dist, 1, n + 1, -1);
			dist[leaf] = 0;
			DFS(leaf);

			System.out.println(maxDist);
		}
	}

	void DFS(int u) {
		for (Pair p : graph.get(u)) {
			int v = p.v, w = p.w;

			if (dist[v] == -1) {
				dist[v] = dist[u] + w;
				DFS(v);

				if (dist[v] > maxDist) {
					maxDist = dist[v];
					leaf = v;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new TheBenefactor().solve();
	}

	class Pair {
		int v, w;

		public Pair(int v, int w) {
			this.v = v;
			this.w = w;
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