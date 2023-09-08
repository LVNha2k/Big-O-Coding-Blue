package lec13;

import java.io.*;
import java.util.*;

// Hardwood Species UVa
public class HardwoodSpecies {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	Map<String, Integer> treeMap = new TreeMap<>();

	void solve() throws IOException {
		int tc = rd.nextInt(), sum;
		String str = rd.readLine();
		while (tc-- > 0) {
			treeMap.clear();
			sum = 0;
			while ((str = rd.readLine()).length() > 0) {
				treeMap.put(str, 1 + treeMap.getOrDefault(str, 0));
				sum++;
			}

			for (Map.Entry<String, Integer> e : treeMap.entrySet())
				pw.printf("%s %.4f\n", e.getKey(), e.getValue() * 1.0 / sum * 100);

			if (tc > 0)
				pw.println();
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new HardwoodSpecies().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 40;
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