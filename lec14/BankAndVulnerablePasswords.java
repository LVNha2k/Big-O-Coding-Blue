package lec14;

import java.io.*;

// Bank and Vulnerable Passwords CodeChef
public class BankAndVulnerablePasswords {
	MyReader rd = new MyReader();
	int N;
	boolean nonVulnerable = true;
	Trie trie = new Trie();

	void solve() throws IOException {
		N = rd.nextInt();

		while (N-- > 0 && nonVulnerable)
			nonVulnerable = trie.addWord(rd.next());

		System.out.print(nonVulnerable ? "non vulnerable" : "vulnerable");
	}

	public static void main(String[] args) throws IOException {
		new BankAndVulnerablePasswords().solve();
	}

	class Trie {
		class Node {
			Node[] child = new Node[R];
			boolean isLeaf;
		}

		static final int R = 26;
		Node root = new Node();

		boolean addWord(String s) {
			Node tmp = root;
			boolean flag = false;

			for (int i = 0, ch; i < s.length(); i++) {
				ch = s.charAt(i) - 'a';
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