package lec13;

import java.io.*;

// Tanya and Postcard Codeforces
public class TanyaAndPostcard {
	MyReader rd = new MyReader();
	String s, t;
	int yay, whoops, countS[] = new int[52], countT[] = new int[52];

	int ind(char c) {
		return Character.isUpperCase(c) ? (c - 'A') : (c - 'a' + 26);
	}

	void solve() throws IOException {
		s = rd.next();
		t = rd.next();
		for (int i = 0; i < s.length(); i++)
			countS[ind(s.charAt(i))]++;
		for (int i = 0; i < t.length(); i++)
			countT[ind(t.charAt(i))]++;

		for (int i = 0, min; i < 52; i++) {
			min = Math.min(countS[i], countT[i]);
			countS[i] -= min;
			countT[i] -= min;
			yay += min;
		}

		for (int i = 0; i < 26; i++)
			whoops += Math.min(countS[i], countT[i + 26]) + Math.min(countS[i + 26], countT[i]);

		System.out.print(yay + " " + whoops);
	}

	public static void main(String[] args) throws IOException {
		new TanyaAndPostcard().solve();
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