package lec14;

import java.io.*;
import java.util.*;

// Diccionario Portunol Live Archive
public class DiccionarioPortunol {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int R = 26;
	int P, S;
	Trie pTrie = new Trie(), sTrie = new Trie();

	void solve() throws IOException {
		long duplicate, ans;
		while ((P = rd.nextInt()) + (S = rd.nextInt()) != 0) {

			pTrie.clear();
			while (P-- > 0)
				pTrie.add(rd.next());

			sTrie.clear();
			while (S-- > 0)
				sTrie.add(new StringBuilder(rd.next()).reverse().toString());

			duplicate = 0;
			for (int i = 0; i < R; i++)
				duplicate += pTrie.endsWith[i] * sTrie.endsWith[i]; // endsWith * startsWith

			ans = 1L * pTrie.totalWord * sTrie.totalWord - duplicate;
			pw.println(ans);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new DiccionarioPortunol().solve();
	}

	class Trie {
		class Node {
			Node[] child = new Node[R];
		}

		Node root = new Node();
		int totalWord, endsWith[] = new int[R];

		void add(String s) {
			Node tmp = root;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - 'a';

				if (tmp.child[ch] == null) {
					tmp.child[ch] = new Node();
					totalWord++;
					if (i >= 1) // depth >= 1
						endsWith[ch]++;
				}
				tmp = tmp.child[ch];
			}
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
			totalWord = 0;
			Arrays.fill(endsWith, 0);
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