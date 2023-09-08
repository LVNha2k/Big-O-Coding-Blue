package lec07;

import java.io.*;
import java.util.*;

// The Lazy Programmer SPOJ
public class TheLazyProgrammer {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 100000;
	int t, N, curTime;
	double money;
	Contract[] arr = new Contract[MAX];
	// max heap = descending by a
	PriorityQueue<Contract> pq = new PriorityQueue<>((con1, con2) -> con2.a - con1.a);

	void solve() throws IOException {
		for (int i = 0; i < arr.length; i++)
			arr[i] = new Contract();
		t = rd.nextInt();

		while (t-- > 0) {
			N = rd.nextInt();
			for (int i = 0; i < N; i++) {
				arr[i].a = rd.nextInt();
				arr[i].b = rd.nextInt();
				arr[i].deadline = rd.nextInt();
			}
			Arrays.sort(arr, 0, N); // ascending by deadline

			money = curTime = 0;
			pq.clear();

			for (int i = 0; i < N; i++) {
				Contract con = arr[i];
				pq.add(con);
				curTime += con.b;

				while (curTime > con.deadline) {
					Contract top = pq.remove();

					if (curTime - con.deadline >= top.b) {
						money += top.b * 1.0 / top.a; // x= b/a
						curTime -= top.b;
						top.b = 0;

					} else {
						money += (curTime - con.deadline) * 1.0 / top.a; // x= (b-d)/a
						top.b -= (curTime - con.deadline);
						curTime = con.deadline;
						pq.add(top);
					}
				}
			}

			pw.printf("%.2f\n", money);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new TheLazyProgrammer().solve();
	}

	class Contract implements Comparable<Contract> {
		int a, b, deadline;

		@Override
		public int compareTo(Contract that) {
			return Integer.compare(this.deadline, that.deadline);
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