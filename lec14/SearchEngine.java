package lec14;

import java.io.*;

// Search Engine HackerEarth
public class SearchEngine {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, q;
	Trie trie = new Trie();

	void solve() throws IOException {
		n = rd.nextInt();
		q = rd.nextInt();
		while (n-- > 0)
			trie.addWord(rd.next(), rd.nextInt());

		while (q-- > 0)
			pw.println(trie.maxWeight(rd.next()));
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new SearchEngine().solve();
	}

	class Trie {
		class Node {
			Node[] child = new Node[R];
			int weight;
		}

		static final int R = 26;
		Node root = new Node();

		void addWord(String s, int weight) {
			Node tmp = root;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - 'a';
				if (tmp.child[ch] == null)
					tmp.child[ch] = new Node();

				tmp = tmp.child[ch];
				tmp.weight = Math.max(tmp.weight, weight);
			}
		}

		int maxWeight(String s) {
			Node tmp = root;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - 'a';
				if (tmp.child[ch] == null)
					return -1;
				tmp = tmp.child[ch];
			}

			return tmp.weight;
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