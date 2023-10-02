package lec14;

import java.io.*;
import java.util.*;

// Old Berland Language Codeforces
public class OldBerlandLanguage {
	class Trie {
		class Node {
			Node child[] = new Node[R], parent;
			boolean isBlock;
		}

		static final int R = 2;
		Node root = new Node();

		String add(int len) {
			Node tmp = root, child0, child1;
			StringBuilder word = new StringBuilder();

			for (int i = 0, next; i < len; i++) {
				next = -1;
				child0 = tmp.child[0];
				child1 = tmp.child[1];

				if (child0 == null || !child0.isBlock)
					next = 0;
				else if (child1 == null || !child1.isBlock)
					next = 1;
				if (next == -1)
					return null;

				if (tmp.child[next] == null) {
					tmp.child[next] = new Node();
					tmp.child[next].parent = tmp;
				}

				word.append(next);
				tmp = tmp.child[next];
			}
			tmp.isBlock = true;

			// update isBlock for parent if occur
			while (tmp.parent != null) {
				child0 = tmp.parent.child[0];
				child1 = tmp.parent.child[1];
				if (child0 != null && child1 != null && child0.isBlock && child1.isBlock)
					tmp.parent.isBlock = true;
				else
					break;
				tmp = tmp.parent;
			}

			return word.toString();
		}
	}

	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	int N;
	String[] ans;
	Length[] lengths;
	Trie trie = new Trie();

	void solve() throws IOException {
		lengths = new Length[N = rd.nextInt()];
		ans = new String[N];
		for (int i = 0; i < N; i++)
			lengths[i] = new Length(rd.nextInt(), i);
		Arrays.sort(lengths);

		for (int i = 0; i < N; i++) {
			String str = trie.add(lengths[i].len);
			if (str == null) {
				pw.print("NO");
				return;
			}
			ans[lengths[i].ind] = str;
		}

		pw.println("YES");
		for (String s : ans)
			pw.println(s);
	}

	public static void main(String[] args) throws IOException {
		new OldBerlandLanguage().solve();
		pw.close();
	}

	class Length implements Comparable<Length> {
		int len, ind;

		public Length(int len, int ind) {
			this.len = len;
			this.ind = ind;
		}

		@Override
		public int compareTo(Length that) {
			return Integer.compare(this.len, that.len);
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