package lec04;

import java.io.*;
import java.util.*;

// Mass of Molecule SPOJ
public class MassOfMolecule {
	MyReader rd = new MyReader();

	int mass(char c) {
		if (c == 'C')
			return 12;
		else if (c == 'H')
			return 1;
		else if (c == 'O')
			return 16;
		else if (c == '(')
			return -1;
		return -1;
	}

	void solve() throws IOException {
		char[] chars = rd.next().toCharArray();
		Stack<Integer> stack = new Stack<>();
		int res;

		for (char c : chars)
			if (c == '(' || Character.isLetter(c))
				stack.push(mass(c));

			else if (Character.isDigit(c))
				stack.push(Character.getNumericValue(c) * stack.pop());

			else if (c == ')') {
				res = 0;
				while (stack.peek() != -1)
					res += stack.pop();
				stack.pop();
				stack.push(res);
			}

		res = 0;
		while (!stack.empty())
			res += stack.pop();
		System.out.print(res);
	}

	public static void main(String[] args) throws IOException {
		new MassOfMolecule().solve();
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