package lec09_midterm;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Word Transformation
public class WordTransformation {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	List<List<Word>> list = Stream.generate(ArrayList<Word>::new).limit(11).collect(Collectors.toList());
	Word start, end;

	void solve() throws IOException {
		int tc = rd.nextInt();
		String wo;
		StringTokenizer sto;
		while (tc-- > 0) {
			for (List<Word> li : list)
				li.clear();

			while (!(wo = rd.next()).equals("*"))
				list.get(wo.length()).add(new Word(wo));

			while ((sto = new StringTokenizer(rd.readLine())).countTokens() == 2) {
				start = new Word(sto.nextToken());
				end = new Word(sto.nextToken());

				if (start.equals(end)) {
					pw.printf("%s %s %d\n", start, end, 0);
					continue;
				}
				BFS();
				pw.printf("%s %s %d\n", start, end, end.dist);
			}
			if (tc > 0)
				pw.println();
		}
		pw.close();
	}

	void BFS() {
		int len = start.str.length();
		for (Word wo : list.get(len))
			wo.dist = -1;

		start.dist = 0;
		Queue<Word> queue = new LinkedList<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			Word wu = queue.remove();

			for (Word wv : list.get(len))
				if (wv.dist == -1 && wv.isNeighbor(wu)) {
					wv.dist = wu.dist + 1;
					queue.add(wv);
					if (wv.equals(end)) {
						end.dist = wv.dist;
						return;
					}
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new WordTransformation().solve();
	}

	class Word {
		String str;
		int dist;

		public Word(String str) {
			this.str = str;
			dist = -1;
		}

		public boolean isNeighbor(Word that) {
			int count = 0;
			for (int i = 0; i < str.length() && count < 2; i++)
				if (this.str.charAt(i) != that.str.charAt(i))
					count++;
			return count == 1;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Word))
				return false;
			return this.str.equals(((Word) obj).str);
		}

		@Override
		public String toString() {
			return this.str;
		}
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