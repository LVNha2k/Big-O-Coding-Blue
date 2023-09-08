package lec06;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Prayatna SPOJ
public class Prayatna {
	MyReader rd = new MyReader();
	static final int MAX = 100000;
	int t, N, e;
	boolean visited[] = new boolean[MAX];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		t = rd.nextInt();
		while (t-- > 0) {
			N = rd.nextInt();
			e = rd.nextInt();

			Arrays.fill(visited, 0, N, false);
			for (int i = 0; i < N; i++)
				graph.get(i).clear();

			for (int i = 0, u, v; i < e; i++) {
				graph.get(u = rd.nextInt()).add(v = rd.nextInt());
				graph.get(v).add(u);
			}

			int count = 0;
			for (int i = 0; i < N; i++)
				if (!visited[i]) {
					count++;
					DFS(i);
				}
			System.out.println(count);
		}
	}

	void DFS(int u) {
		visited[u] = true;
		for (int v : graph.get(u))
			if (!visited[v])
				DFS(v);
	}

	public static void main(String[] args) throws IOException {
		new Prayatna().solve();
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