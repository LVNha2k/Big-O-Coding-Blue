package lec05;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
// Kefa and Park Codeforces
public class KefaAndPark {
	MyReader rd = new MyReader();
	int n, m, a[], cat[];
	boolean visited[];
	List<Integer>[] graph;

	void solve() throws IOException {
		n = rd.nextInt();
		m = rd.nextInt();
		a = new int[n + 1];
		cat = new int[n + 1];
		visited = new boolean[n + 1];
		graph = new List[n + 1];

		for (int i = 1; i <= n; i++) {
			a[i] = rd.nextInt();
			graph[i] = new ArrayList<>();
		}
		for (int i = 0, u, v; i < n - 1; i++) {
			graph[u = rd.nextInt()].add(v = rd.nextInt());
			graph[v].add(u);
		}

		System.out.print(BFS(1));
	}

	int BFS(int start) {
		int ans = 0;
		visited[start] = true;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(start);
		cat[start] = (a[start] == 1) ? 1 : 0;

		while (!queue.isEmpty()) {
			int u = queue.remove();

			for (int v : graph[u])
				if (!visited[v]) {
					visited[v] = true;

					if (a[v] == 1)
						cat[v] = cat[u] + 1;

					if (cat[v] <= m)
						if (graph[v].size() == 1) // Leaf
							ans++;
						else
							queue.add(v);
				}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		new KefaAndPark().solve();
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