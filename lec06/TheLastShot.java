package lec06;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// THE LAST SHOT SPOJ
public class TheLastShot {
	MyReader rd = new MyReader();
	List<List<Integer>> graph;
	int N, M;
	boolean[] visited;

	void solve() throws IOException {
		N = rd.nextInt();
		M = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(N + 1).collect(Collectors.toList());
		visited = new boolean[N + 1];
		while (M-- > 0)
			graph.get(rd.nextInt()).add(rd.nextInt());

		int imp, maxImpact = -1;
		for (int i = 1; i <= N; i++) {
			Arrays.fill(visited, false);
			imp = DFS(i);
			maxImpact = Math.max(maxImpact, imp);
		}

		System.out.print(maxImpact);
	}

	int DFS(int u) {
		int impact = 1;
		visited[u] = true;

		for (int v : graph.get(u))
			if (!visited[v])
				impact += DFS(v);

		return impact;
	}

	public static void main(String[] args) throws IOException {
		new TheLastShot().solve();
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