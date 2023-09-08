package lec01;

import java.io.*;
import java.util.*;

// Vitaly and Strings Codeforces
public class VitalyAndStrings {
	MyReader rd = new MyReader();

	void solve() throws IOException {
		char[] s = rd.next().toCharArray(), t = rd.next().toCharArray();

		for (int i = s.length - 1; i >= 0; i--)
			if (s[i] == 'z')
				s[i] = 'a';
			else {
				s[i]++;
				break;
			}
		
		System.out.print(Arrays.equals(s, t) ? "No such string".toCharArray() : s);
	}

	public static void main(String[] args) throws IOException {
		new VitalyAndStrings().solve();
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