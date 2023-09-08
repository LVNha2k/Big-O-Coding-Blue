package lec09_midterm;

import java.io.*;
import java.util.*;

// Printer Queue UVa
public class ProblemA {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m;
	Queue<Job> queue = new LinkedList<>();
	PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

	void solve() throws IOException {
		for (int tc = rd.nextInt(), count; tc-- > 0;) {
			n = rd.nextInt();
			m = rd.nextInt();
			pq.clear();
			queue.clear();

			for (int i = 0, priority; i < n; i++) {
				queue.add(new Job(i, priority = rd.nextInt()));
				pq.add(priority);
			}

			count = 0;
			while (true) {
				Job job = queue.remove();
				if (job.priority == pq.peek()) {
					count++;
					pq.remove();
					if (job.pos == m)
						break;
				} else
					queue.add(job);
			}

			pw.println(count);
		}
		pw.close();
	}

	class Job {
		int pos, priority;

		public Job(int pos, int priority) {
			this.pos = pos;
			this.priority = priority;
		}
	}

	public static void main(String[] args) throws IOException {
		new ProblemA().solve();
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