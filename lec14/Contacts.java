package lec14;

import java.io.*;

// Contacts HackerRank
public class Contacts {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n;
	Trie trie = new Trie();

	void solve() throws IOException {
		n = rd.nextInt();

		while (n-- > 0)
			if (rd.next().charAt(0) == 'a')
				trie.addWord(rd.next());
			else
				pw.println(trie.find(rd.next()));

		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new Contacts().solve();
	}

	class Trie {
		class Node {
			Node[] child = new Node[Trie.R];
			int count;
		}

		static final int R = 26;
		Node root = new Node();

		void addWord(String s) {
			Node tmp = root;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - 'a';
				if (tmp.child[ch] == null)
					tmp.child[ch] = new Node();

				tmp = tmp.child[ch];
				tmp.count++;
			}
		}

		int find(String prefix) {
			Node tmp = root;

			for (int i = 0, ch; i < prefix.length(); i++) {
				ch = prefix.charAt(i) - 'a';
				if (tmp.child[ch] == null)
					return 0;
				tmp = tmp.child[ch];
			}

			return tmp.count;
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