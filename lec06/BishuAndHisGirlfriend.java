package lec06;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Bishu and his Girlfriend HackerEarth
public class BishuAndHisGirlfriend {
	MyReader rd = new MyReader();
	int N, Q, dist[];
	List<List<Integer>> graph;

	void solve() throws IOException {
		N = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(N + 1).collect(Collectors.toList());
		for (int i = 0, u, v; i < N - 1; i++) {
			graph.get(u = rd.nextInt()).add(v = rd.nextInt());
			graph.get(v).add(u);
		}
		dist = new int[N + 1];
		Arrays.fill(dist, -1);

		dist[1] = 0;
		DFS(1);

		Q = rd.nextInt();
		int x, minDist = N, minId = N;
		for (int i = 0; i < Q; i++) {
			x = rd.nextInt();
			if (dist[x] < minDist || (dist[x] == minDist && x < minId)) {
				minDist = dist[x];
				minId = x;
			}
		}
		System.out.print(minId);
	}

	void DFS(int u) {
		for (int v : graph.get(u))
			if (dist[v] == -1) {
				dist[v] = dist[u] + 1;
				DFS(v);
			}
	}

	public static void main(String[] args) throws IOException {
		new BishuAndHisGirlfriend().solve();
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