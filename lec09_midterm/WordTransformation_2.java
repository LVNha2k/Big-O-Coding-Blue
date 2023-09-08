package lec09_midterm;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Word Transformation
public class WordTransformation_2 {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 201;
	int st, fi, V, dist[] = new int[MAX];
	String start, end;
	List<String> dict = new ArrayList<>(MAX);
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX).collect(Collectors.toList());

	void solve() throws IOException {
		String wo;
		StringTokenizer sto;

		for (int tc = rd.nextInt(), count; tc-- > 0;) {
			dict.clear();
			for (List<Integer> li : graph)
				li.clear();

			while (!(wo = rd.next()).equals("*"))
				dict.add(wo);
			V = dict.size();

			for (int u = 0; u < V - 1; u++)
				for (int v = u + 1; v < V; v++)
					if (dict.get(u).length() == dict.get(v).length()) {
						count = 0;

						for (int i = 0; i < dict.get(u).length() && count < 2; i++)
							count += (dict.get(u).charAt(i) != dict.get(v).charAt(i) ? 1 : 0);

						if (count == 1) {
							graph.get(u).add(v);
							graph.get(v).add(u);
						}
					}

			while ((sto = new StringTokenizer(rd.readLine())).countTokens() == 2) {
				st = dict.indexOf(start = sto.nextToken());
				fi = dict.indexOf(end = sto.nextToken());

				Arrays.fill(dist, -1);
				BFS();
				pw.printf("%s %s %d\n", start, end, dist[fi]);
			}
			if (tc > 0)
				pw.println();
		}
		pw.close();
	}

	void BFS() {
		dist[st] = 0;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(st);

		while (!queue.isEmpty()) {
			int u = queue.remove();

			for (int v : graph.get(u))
				if (dist[v] == -1) {
					dist[v] = dist[u] + 1;
					queue.add(v);
					if (v == fi)
						return;
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new WordTransformation_2().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 24;
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[lineLength];
			int cnt = 0;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = c;
			}
			if (isWindows && buf[cnt - 1] == 13)
				return new String(buf, 0, cnt - 1);
			return new String(buf, 0, cnt);
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

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}