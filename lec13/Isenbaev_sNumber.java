package lec13;

import java.io.*;
import java.util.*;

// Isenbaev's Number Timus Online Judge
public class Isenbaev_sNumber {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	TreeMap<String, Node> treeMap = new TreeMap<>();
	Node[] contestants = new Node[3];

	void solve() throws IOException {
		Node node, start;
		for (int n = rd.nextInt(); n-- > 0;) {

			for (int i = 0; i < 3; i++) {
				String name = rd.next();
				if ((node = treeMap.get(name)) == null) {
					node = new Node(name);
					treeMap.put(name, node);
				}
				contestants[i] = node;
			}

			for (int i = 0; i < 3; i++)
				for (int j = i + 1; j < 3; j++) {
					contestants[i].neighbor.add(contestants[j]);
					contestants[j].neighbor.add(contestants[i]);
				}
		}

		start = treeMap.get("Isenbaev");
		if (start == null)
			start = new Node("Isenbaev");
		BFS(start);

		for (Node e : treeMap.values())
			pw.println(e);
		pw.close();
	}

	void BFS(Node start) {
		start.number = 0;
		Queue<Node> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			Node u = queue.remove();
			for (Node v : u.neighbor)
				if (!v.isVisited()) {
					v.number = u.number + 1;
					queue.add(v);
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new Isenbaev_sNumber().solve();
	}

	class Node implements Comparable<Node> {
		String name;
		int number = -1;
		Set<Node> neighbor = new HashSet<>();

		public Node(String name) {
			this.name = name;
		}

		public boolean isVisited() {
			return number != -1;
		}

		@Override
		public int compareTo(Node that) {
			return this.name.compareTo(that.name);
		}

		@Override
		public String toString() {
			return String.format("%s %s", name, number != -1 ? number : "undefined");
		}

		@Override
		public int hashCode() {
			return -1;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Node))
				return false;
			return this.name.equals(((Node) obj).name);
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