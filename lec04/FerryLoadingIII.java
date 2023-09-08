package lec04;

import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
// Ferry Loading III UVa
public class FerryLoadingIII {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 10001;
	int c, n, t, m, ans[] = new int[MAX];
	Queue<Car> qSide[] = new Queue[2];

	void solve() throws IOException {
		qSide[0] = new LinkedList<>();
		qSide[1] = new LinkedList<>();
		c = rd.nextInt();
		while (c-- > 0) {
			n = rd.nextInt();
			t = rd.nextInt();
			m = rd.nextInt();

			for (int i = 1, arrived; i <= m; i++) {
				arrived = rd.nextInt();
				if (rd.next().charAt(0) == 'l')
					qSide[0].add(new Car(i, arrived));
				else
					qSide[1].add(new Car(i, arrived));
			}

			int curSide = 0, curTime = 0, nextTime;
			int waiting = (qSide[0].isEmpty() ? 0 : 1) + (qSide[1].isEmpty() ? 0 : 1);

			while (waiting > 0) {
				if (waiting == 1)
					nextTime = (qSide[0].isEmpty() ? qSide[1].peek().arriveTime : qSide[0].peek().arriveTime);
				else
					nextTime = Math.min(qSide[0].peek().arriveTime, qSide[1].peek().arriveTime);

				curTime = Math.max(curTime, nextTime);
				int carried = 0;

				while (!qSide[curSide].isEmpty()) {
					Car car = qSide[curSide].peek();
					if (car.arriveTime <= curTime && carried < n) {
						ans[car.id] = curTime + t;
						carried++;
						qSide[curSide].remove();
					} else
						break;
				}

				curTime += t;
				curSide = 1 - curSide;
				waiting = (!qSide[0].isEmpty() ? 1 : 0) + (!qSide[1].isEmpty() ? 1 : 0);
			}

			for (int i = 1; i <= m; i++)
				pw.println(ans[i]);
			if (c > 0)
				pw.println();
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new FerryLoadingIII().solve();
	}

	class Car {
		int id, arriveTime;

		public Car(int id, int arriveTime) {
			this.id = id;
			this.arriveTime = arriveTime;
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