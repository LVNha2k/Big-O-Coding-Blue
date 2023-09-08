package lec07;

import java.io.*;
import java.util.*;

// I Can Guess the Data Structure! UVa
public class ICanGuessTheDataStructure {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, cmd, x;
	Stack<Integer> stack = new Stack<>();
	Queue<Integer> queue = new LinkedList<>();
	PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	boolean sta, que, priq;

	void solve() throws IOException {
		while (true) {
			n = rd.nextInt();
			if (rd.isEOF())
				break;

			sta = que = priq = true;
			stack.clear();
			queue.clear();
			pq.clear();

			while (n-- > 0) {
				cmd = rd.nextInt();
				x = rd.nextInt();
				if (cmd == 1) {
					if (sta)
						stack.add(x);
					if (que)
						queue.add(x);
					if (priq)
						pq.add(x);
					
				} else { // cmd == 2
					if (sta && !stack.pop().equals(x))
						sta = false;
					if (que && !queue.remove().equals(x))
						que = false;
					if (priq && !pq.remove().equals(x))
						priq = false;
				}
			}

			if (!sta && !que && !priq)
				pw.println("impossible");
			else if ((sta && que) || (sta && priq) || (que && priq))
				pw.println("not sure");
			else if (sta)
				pw.println("stack");
			else if (que)
				pw.println("queue");
			else if (priq)
				pw.println("priority queue");

			if (rd.isEOF())
				break;
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new ICanGuessTheDataStructure().solve();
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

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}