package lec01;

import java.io.*;

// Suffix Structures Codeforces
public class SuffixStructures {
	MyReader rd = new MyReader();
	String s, t;

	boolean contains(String s, String t) {
		int index = 0;
		for (int i = 0; i < s.length() && index < t.length(); i++)
			if (s.charAt(i) == t.charAt(index))
				index++;
		return index == t.length();
	}

	void solve() throws IOException {
		if (contains(s = rd.next(), t = rd.next())) {
			System.out.print("automaton");
			return;
		}

		int countS[] = new int[26], countT[] = new int[26];
		for (int i = 0; i < s.length(); i++)
			countS[s.charAt(i) - 'a']++;
		for (int i = 0; i < t.length(); i++)
			countT[t.charAt(i) - 'a']++;

		for (int i = 0; i < 26; i++)
			if (countT[i] > countS[i]) {
				System.out.print("need tree");
				return;
			}
		
		System.out.print(t.length() < s.length() ? "both" : "array");
	}

	public static void main(String[] args) throws IOException {
		new SuffixStructures().solve();
	}

	static class MyReader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;
		private boolean isWindows;
		static final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				din = new DataInputStream(file ? new FileInputStream(FILE) : System.in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			byte c;
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}