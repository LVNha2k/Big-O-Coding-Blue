package lec06;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Dudu Service Maker URI Online Judge
public class DuduServiceMaker {
	MyReader rd = new MyReader();
	static final int MAX = 10001;
	int T, N, M, visited[] = new int[MAX];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			N = rd.nextInt();
			M = rd.nextInt();

			Arrays.fill(visited, 1, N + 1, 0);
			for (int i = 1; i <= N; i++)
				graph.get(i).clear();

			for (int i = 0; i < M; i++)
				graph.get(rd.nextInt()).add(rd.nextInt());

			boolean isCyclic = false;
			for (int u = 1; u <= N && !isCyclic; u++)
				isCyclic = DFS(u);

			System.out.println(isCyclic ? "YES" : "NO");
		}
	}

	boolean DFS(int u) {
		visited[u] = 1;

		for (int v : graph.get(u))
			if (visited[v] == 1)
				return true;
			else if (visited[v] == 0)
				if (DFS(v))
					return true;

		visited[u] = 2;
		return false;
	}

	public static void main(String[] args) throws IOException {
		new DuduServiceMaker().solve();
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