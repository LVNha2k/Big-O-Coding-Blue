package lec14;

import java.io.*;

// Consistency Checker LightOJ
public class ConsistencyChecker {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int T, n;
	boolean consistent;
	Trie trie = new Trie();

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			n = rd.nextInt();

			consistent = true;
			trie.clear();

			for (String s; n-- > 0;) {
				s = rd.next();
				if (consistent)
					consistent = trie.addWord(s);
			}

			pw.printf("Case %d: %s\n", tc, consistent ? "YES" : "NO");
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new ConsistencyChecker().solve();
	}

	class Trie {
		class Node {
			Node[] child = new Node[R];
			boolean isLeaf;
		}

		static final int R = 10;
		Node root = new Node();

		boolean addWord(String s) {
			Node tmp = root;
			boolean flag = false;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - '0';
				if (tmp.child[ch] == null) {
					tmp.child[ch] = new Node();
					flag = true;
				}

				tmp = tmp.child[ch];
				if (tmp.isLeaf)
					return flag = false;
			}

			tmp.isLeaf = true;
			return flag;
		}

		void clear() {
			clear(root);
		}

		void clear(Node root) {
			for (int i = 0; i < R; i++)
				if (root.child[i] != null) {
					clear(root.child[i]);
					root.child[i] = null;
				}
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