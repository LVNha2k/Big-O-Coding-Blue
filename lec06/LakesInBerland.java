package lec06;

import java.io.*;
import java.util.*;

// Lakes in Berland Codeforces
public class LakesInBerland {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m, k, dr[] = { -1, 1, 0, 0 }, dc[] = { 0, 0, -1, 1 };
	char[][] map;
	boolean[][] visited;
	List<List<Integer>> lakes = new ArrayList<>();

	boolean isWater(int row, int col) {
		return map[row][col] == '.';
	}

	void solve() throws IOException {
		map = new char[n = rd.nextInt()][];
		visited = new boolean[n][m = rd.nextInt()];
		k = rd.nextInt();
		for (int i = 0; i < n; i++)
			map[i] = rd.next().toCharArray();

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (isWater(i, j) && !visited[i][j])
					BFS(i, j);

		Collections.sort(lakes, new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> li1, List<Integer> li2) {
				return li1.size() - li2.size();
			}
		});

		int sum_cell = 0;
		List<Integer> lake;

		for (int i = 0; i < lakes.size() - k; i++) {
			lake = lakes.get(i);
			sum_cell += lake.size() / 2;

			for (int j = 0; j < lake.size() - 1; j += 2)
				map[lake.get(j)][lake.get(j + 1)] = '*';
		}

		pw.println(sum_cell);
		for (char[] cs : map) {
			for (char c : cs)
				pw.print(c);
			pw.println();
		}
		pw.close();
	}

	void BFS(int row, int col) {
		visited[row][col] = true;
		Queue<Integer> queue = new LinkedList<>();
		queue.add(row);
		queue.add(col);
		List<Integer> lake = new ArrayList<>();
		boolean ocean = false;

		for (int r, c; !queue.isEmpty();) {
			lake.add(r = queue.remove());
			lake.add(c = queue.remove());

			if (onBorder(r, c))
				ocean = true;

			for (int i = 0; i < 4; i++) {
				int tmpr = r + dr[i], tmpc = c + dc[i];
				if (isValid(tmpr, tmpc) && isWater(tmpr, tmpc) && !visited[tmpr][tmpc]) {
					visited[tmpr][tmpc] = true;
					queue.add(tmpr);
					queue.add(tmpc);
				}
			}
		}
		if (!ocean)
			lakes.add(lake);
	}

	boolean onBorder(int row, int col) {
		return row == 0 || row == n - 1 || col == 0 || col == m - 1;
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < n && col >= 0 && col < m;
	}

	public static void main(String[] args) throws IOException {
		new LakesInBerland().solve();
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