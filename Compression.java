package BnGaya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

import javax.xml.soap.Node;

import org.apache.commons.lang3.StringEscapeUtils;

public class Compression {
	static PriorityQueue<TREE> pq = new PriorityQueue<TREE>();
	static int[] freq = new int[300];
	static String[] ss = new String[300];
	static int exbits;
	static byte bt;
	static int cnt;
	static int ext = 0;
	static String decodeArr[] = new String[300];

	static class TREE implements Comparable<TREE> {
		TREE Lchild;
		TREE Rchild;

		public String deb;
		public int Bite;
		public int Freqnc;

		public int compareTo(TREE T) {

			if (this.Freqnc < T.Freqnc)
				return -1;
			if (this.Freqnc > T.Freqnc)
				return 1;
			return 0;
		}
	}

	static TREE Root;

	public static void CalFreq(String fname) {
		File file = null;
		Byte bt;
		file = new File(fname);
		try {
			FileInputStream file_input = new FileInputStream(file);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				try {

					bt = data_in.readByte();
					// System.out.println(bt);
					freq[to(bt)]++;
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}
			file_input.close();
			data_in.close();
		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		file = null;
	}

	public static int to(Byte b) {
		int ret = b;
		if (ret < 0) {
			ret = ~b;
			ret = ret + 1;
			ret = ret ^ 255;
			ret += 1;
		}
		return ret;
	}

	public static void initHzipping() {
		int i;
		cnt = 0;
		if (Root != null)
			fredfs(Root);
		for (i = 0; i < 300; i++)
			freq[i] = 0;
		for (i = 0; i < 300; i++) {
			ss[i] = "";
			// decodeArr[i]="";
		}
		pq.clear();
	}

	public static void fredfs(TREE now) {
		// System.out.println("This is ca;lled");
		if (now.Lchild == null && now.Rchild == null) {
			now = null;
			return;
		}
		if (now.Lchild != null)
			fredfs(now.Lchild);
		if (now.Rchild != null)
			fredfs(now.Rchild);
	}

	public static void dfs(TREE now, String st) {

		now.deb = st;
		if ((now.Lchild == null) && (now.Rchild == null)) {
			ss[now.Bite] = st;
			decodeArr[now.Bite] = st;
			// System.out.println("just checking "+ decodeArr[now.Bite] + "
			// now.Bite "+ now.Bite);
			// System.out.println("st is "+ st );
			return;
		}
		if (now.Lchild != null) {
			// System.out.println("left ka ");
			dfs(now.Lchild, st + "0");
		}
		if (now.Rchild != null) {
			// System.out.println("right ka ");
			dfs(now.Rchild, st + "1");
		}

	}

	public static void MakeNode() {
		for (int i = 0; i < 300; i++) {
			// ss[i] = "";
			decodeArr[i] = "";
		}
		int i;
		pq.clear();

		for (i = 0; i < 300; i++) {
			if (freq[i] != 0) {

				TREE Temp = new TREE();
				Temp.Bite = i;
				Temp.Freqnc = freq[i];
				Temp.Lchild = null;
				Temp.Rchild = null;
				pq.add(Temp);
				cnt++;
				// System.out.println(pq.peek().Freqnc);
				// System.out.println("nodes are "+ cnt);
				// System.out.println("on node "+cnt +" Freqnc "+Temp.Freqnc);
			}

		}
		TREE Temp1, Temp2;

		if (cnt == 0) {
			return;
		} else if (cnt == 1) {
			for (i = 0; i < 300; i++)
				if (freq[i] != 0) {
					ss[i] = "0";
					break;
				}
			return;
		}
		while (pq.size() != 1) {
			TREE Temp = new TREE();
			Temp1 = pq.poll();
			Temp2 = pq.poll();
			Temp.Lchild = Temp1;
			Temp.Rchild = Temp2;
			Temp.Freqnc = Temp1.Freqnc + Temp2.Freqnc;
			// System.out.println("frewkjksef "+Temp.Freqnc+" byte is
			// "+Temp.Bite);
			pq.add(Temp);
		}
		Root = pq.poll();
		// System.out.println("Root bit "+ Root);
	}

	public static void encrypt(String fname) {
		File file = null;

		file = new File(fname);
		try {
			FileInputStream file_input = new FileInputStream(file);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				try {

					bt = data_in.readByte();
					freq[bt]++;
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}
			file_input.close();
			data_in.close();

		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		file = null;
	}

	public static void fakezip(String fname) {

		File filei, fileo;
		int i;

		filei = new File(fname);
		fileo = new File("fakezipped.txt");
		try {
			FileInputStream file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			PrintStream ps = new PrintStream(fileo);

			while (true) {
				try {
					bt = data_in.readByte();
					ps.print(ss[to(bt)]);
				} catch (EOFException eof) {
					System.out.println("End of File");
					break;
				}
			}

			file_input.close();
			data_in.close();
			ps.close();

		} catch (IOException e) {
			System.out.println("IO Exception =: " + e);
		}
		filei = null;
		fileo = null;

	}

	public static void realzip(String fname, String fname1) {
		File filei, fileo;
		int i, j = 10;
		Byte btt;

		filei = new File(fname);
		fileo = new File(fname1);

		try {
			FileInputStream file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			FileOutputStream file_output = new FileOutputStream(fileo);
			DataOutputStream data_out = new DataOutputStream(file_output);

			// data_out.writeInt(cnt);
			/*
			 * for (i = 0; i < 256; i++) { if (freq[i] != 0) { btt = (byte) i;
			 * data_out.write(btt); // for decoding //
			 * System.out.println("char is "+(char)(int)btt);
			 * data_out.writeInt(freq[i]); // System.out.println("table "+
			 * freq[i]); } }
			 */
			long texbits;
			texbits = filei.length() % 8;/// reads no. of characters
			System.out.println("size nhai " + filei.length());
			System.out.println("tetxbits " + texbits);
			texbits = (8 - texbits) % 8;
			// System.out.println("tetxbits2 " + texbits);
			exbits = (int) texbits;
			ext = exbits;
			// System.out.println();
			System.out.println("This s extrabit " + exbits);
			data_out.write(exbits);
			int length = 0;
			while (true) {
				try {
					bt = 0;
					length = 0;
					byte ch;
					// String cv="";
					for (exbits = 0; exbits < 8; exbits++) {
						// System.out.println("exbit is "+ exbits);
						// System.out.println("bt is "+ bt);
						ch = data_in.readByte();
						// cv+=(int)ch;
						// System.out.println("byte check " + ch);
						length *= 2;
						bt *= 2;
						// System.out.println("bt is " + bt);
						// System.out.println("here " +
						// Integer.toBinaryString((int) bt));
						if (ch == '1') {
							length++;
							bt++;
						}

					}
					// System.out.println("bt is " + bt);
					// System.out.println("here
					// "+Integer.toBinaryString((int)bt));
					// System.out.println("exbitnew is "+ exbits);
					/// if(bt<0){
					// length = bt & 0xFF;
					// System.out.println("cv is "+cv);
					// System.out.println("bt is "+bt);
					// System.out.println("Lenght is "+length);
					// data_out.write(length);
					data_out.write(length);
					// }
					// else
					// data_out.write(bt);

				} catch (EOFException eof) {
					int x;
					if (exbits != 0) {
						for (x = exbits; x < 8; x++) {
							bt *= 2;
							length *= 2;

						}
						// System.out.println("bt is " + bt);
						// System.out.println("here " +
						// Integer.toBinaryString((int) bt));

						// if(bt<0){
						// length = bt & 0xFF;
						data_out.write(length);
						// }
						// else
						// data_out.write(bt);

						// CheckSpecial(c);
						// data_out.writeChar(128);
						// System.out.println("here
						// "+Integer.toBinaryString((int)bt));
					}

					exbits = (int) texbits;

					// System.out.println("extrabits: " + exbits);
					System.out.println("End of File");
					// System.out.println("length "+ fileo.length());
					break;
				}
			}
			data_in.close();
			data_out.close();
			file_input.close();
			file_output.close();
			// System.out.println("output file's size: " + fileo.length());

		} catch (IOException e) {
			System.out.println("IO exception = " + e);
		}
		// filei.delete();
		filei = null;
		fileo = null;
	}

	public static void beginHzipping(String arg1) {

		initHzipping();
		CalFreq(arg1);
		MakeNode();
		if (cnt > 1)
			dfs(Root, "");

		fakezip(arg1);
		realzip("fakezipped.txt", "realZipped.txt");
		initHzipping();
		// FileHandleing();
		unZip2("realZipped.txt");
		FileHandleing();
		// char a='€';
		// System.out.println((char) 127);

		// manually();
		// System.out.println((char) 128);

	}

	static char store[];

	public static void decode(String line) {

		// FileHandleing fh = new FileHandleing();
		// fh.filereader();
		store = line.toCharArray();
		System.out.println("length is " + store.length);
		// System.out.println(Arrays.toString(store));
		// System.out.println("checking "+Root.Lchild.deb);
		// System.out.println(Arrays.toString(decodeArr));
		// System.out.println(decodeArr[97]);
		// System.out.println(result.toString());
		decodidation(Root);
		File file = new File("NewDecoded.txt");
		BufferedWriter a;
		try {
			a = new BufferedWriter(new FileWriter(file));
			// System.out.println("resu "+ result.size());
			for (int i = 0; i < result.size(); i++) {
				int intt = new Integer(result.get(i));
				// System.out.println(result.toString());

				a.write((char) intt);
			}

			a.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		// iterative_preorder();

		// ArrayList<String> pro = fh.getText();
		/*
		 * for(int i=0; i< store.length();i++){ pro.add(store);
		 * 
		 * }
		 */

	}

	static int index = 0;
	static String con = "";
	static // static String result="";
	ArrayList<Integer> result = new ArrayList<>();

	public static void decodeAgain(TREE Node) {

		try {

			// System.out.println("Alwayyyyyyyyyyyyyys "+ Node.Bite);
			if (Node == null) {

				// System.out.println("dikhaa");
				con = "";

				return;
			} else if (Node.Lchild == null && Node.Rchild == null) {

				if (con.equals(Node.deb)) {

					// System.out.println("decode is: "+ " ss is "+ Node.deb);
					result.add(Node.Bite);
					// System.out.println("index is "+ index);
					// index++;
					// System.out.println("index is "+ index);
					con = "";

					// break;

				}
				decodeAgain(Root);
				return;

			}
			// }

			else if (Node != null) {

				/*
				 * int temp = Node.deb.length(); Node.deb.lastIndexOf(temp);
				 */
				if (store[index] == '0') {

					con += store[index];
					// System.out.println("Left index is "+ index);
					index++;

					decodeAgain(Node.Lchild);
					return;
				} else {
					con += store[index];
					// System.out.println("sfffffffff " + con);
					// System.out.println("Right index is "+ index);
					index++;
					decodeAgain(Node.Rchild);
					return;

				}
			}
		} catch (Exception e) {
			// System.out.println(result.toString());
			return;
		}

	}

	public static void decodidation(TREE Node) {
		int temp = cnt;
		try {
			while (true) {
				if (index > store.length)
					break;
				// System.out.println(index);
				if (Node == null) {

					con = "";
					return;
				}

				// while ((Node.Lchild != null || Node.Rchild != null)) {

				else if ((Node.Lchild != null || Node.Rchild != null)) {
					if (index >= store.length)
						break;
					// System.out.println("last "+ Node.deb);
					if (store[index] == '0') {

						con += store[index];

						index++;
						Node = Node.Lchild;
						// break;
					} else {
						con += store[index];

						index++;
						Node = Node.Rchild;
						// break;

					}
				}

				else if (Node.Lchild == null && Node.Rchild == null) {
					if (con.equals(Node.deb)) {

						// System.out.println("ho gaya");
						// System.out.println("con is "+ con);
						result.add(Node.Bite);
						// temp--;
						con = "";
						Node = Root;
						/*
						 * if(temp==0) break;
						 */
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	static void iterative_preorder() {
		Stack<TREE> s = new Stack<TREE>();
		TREE P = Root;
		while (P != null) {
			System.out.println(P.deb);
			if (P.Rchild != null)
				s.push(P.Rchild);

			P = P.Lchild;
			if (P == null || s.isEmpty())
				if (!s.isEmpty())
					P = s.pop();
		}

	}

	private static FileReader file;
	private static BufferedReader read;
	private static String reader;

	public static void FileHandleing() {
		reader = "";
		try {
			// file = new
			// FileReader("C:\\Users\\Kalicifer\\Downloads\\Compressed\\New Text
			// Document.txt");
			file = new FileReader("G:\\3rd semester workplace\\Huffman\\UnZip2.txt");
			read = new BufferedReader(file);
			filereader();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ArrayList<String> arr ;
	public static void filereader() {
		try {

			String line = read.readLine();
			while (line != null) {
				decode(line);

				// arr.add(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void unZip2(String filename) {
		File filei = new File(filename);
		File fileo = new File("UnZip2.txt");
		FileInputStream file_input = null;
		ArrayList<Byte> ab = new ArrayList<Byte>();
		try {
			file_input = new FileInputStream(filei);
			DataInputStream data_in = new DataInputStream(file_input);
			while (true) {
				ab.add(data_in.readByte());
			}
			
		} catch (Exception e1) {
			System.out.println("File ended unzpi2");
		//	e1.printStackTrace();
		}
		
		//System.out.println(ab.toString()+" thisss");
		//FileOutputStream file_output = null;
		BufferedWriter file_output = null;
		try {
			//file_output = new FileOutputStream(fileo);
			//DataOutputStream data_out = new DataOutputStream(file_output);
			file_output = new BufferedWriter((new FileWriter(fileo)));
			String s = "";
			for (int i = 0; i < ab.size(); i++) {
				if (i == 0) {
					// System.out.println("arr is "+(int)arr[i]);
					ext = ab.get(i);

				} else {

					s = toBinary(to(ab.get(i)));
					 if (i == arr.size() - 1)
					 s = s.substring(0, s.length() - ext);
//					if(i==ab.size()-1){
//						int[] acc= new int[abb.length-ext];
//						for(int o=0; o<abb.length-ext;o++){
//							acc[o] = abb[o];
//						}
//						abb = acc;
//						
//					}
						
//					
//					
//					for (int p = 0; p < abb.length; p++){
//						System.out.println("This is write "+abb[p]);
//						file_output.write(abb[p]);
//					}
					file_output.write(s);
						//data_out.write(abb[p]);
					// Binary
					 s = "";
				}

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		try {
			System.out.println("This is closed");
			file_output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		/*try {
			BufferedReader buff = new BufferedReader(new FileReader(new File(filename)));
			String line = buff.readLine();
			String reader = "";
			while (line != null) {
				reader += line;
				// arr.add(line);
				line = buff.readLine();

			}
			File f = new File("unZip.txt");

			BufferedWriter uff = new BufferedWriter(new FileWriter(f));
			char[] arr = reader.toCharArray();
			String s = "";
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					// System.out.println("arr is "+(int)arr[i]);
					ext = arr[i];

				} else {

					byte bt[] = toBinary(arr[i]);

					/*
					 * if (Integer.toBinaryString((int) arr[i]).length() <= 7) {
					 * for (int j = 0; j < 8 - Integer.toBinaryString((int)
					 * arr[i]).length(); j++) { s += "0"; //
					 * System.out.println("on pass j "+ j + " This is s // "+s
					 * ); } }
					 
					// s += Integer.toBinaryString((int) arr[i]);
					// if (i == arr.length - 1)
					// s = s.substring(0, s.length() - ext);
					for (int k = 0; k < bt.length; k++)
						uff.write(bt[k]);
					s = "";
				}

			}*/
			// System.out.println("Before "+s+" ext "+ ext);
			// s = s.substring(0,s.length()-ext);
			// System.out.println("After "+s);

		/*	uff.write(s);
			uff.close();

		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public static void unZip(String filename) {
		try {

			BufferedReader buff = new BufferedReader(new FileReader(new File(filename)));
			String line = buff.readLine();
			String reader = "";
			while (line != null) {
				reader += line;
				// arr.add(line);
				line = buff.readLine();

			}
			File f = new File("unZip.txt");

			BufferedWriter uff = new BufferedWriter(new FileWriter(f));
			char[] arr = reader.toCharArray();
			String s = "";
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					// System.out.println("arr is "+(int)arr[i]);
					ext = arr[i];
					// System.out.println("char is "+s);
				} else {
					// System.out.println("arr is "+(int)arr[i]);
					// s = toBinary(arr[i]);
					// s=manually(arr[i]);
					// System.out.println("char is "+s);

					/*
					 * if (Integer.toBinaryString((int) arr[i]).length() <= 7) {
					 * for (int j = 0; j < 8 - Integer.toBinaryString((int)
					 * arr[i]).length(); j++) { s += "0"; //
					 * System.out.println("on pass j "+ j + " This is s // "+s
					 * ); } }
					 */
					// s += Integer.toBinaryString((int) arr[i]);
					if (i == arr.length - 1)
						s = s.substring(0, s.length() - ext);

					uff.write(s);
					s = "";
				}

			}
			// System.out.println("Before "+s+" ext "+ ext);
			// s = s.substring(0,s.length()-ext);
			// System.out.println("After "+s);

			uff.write(s);
			uff.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static ArrayList<String> arr = new ArrayList<String>();

	public static String manually(char a) {
		int c = a;
		int e = 0;
		String d = "";
		while (c != 1 && c != 0) {
			e = c % 2;
			c = c / 2;
			d = e + d;
		}
		d = c + d;
		if (d.length() < 8) {
			for (int i = 0; i < 8 - d.length(); i++)
				d = "0" + d;
		}
		return d;
	}

	static File fi;
	static File fi2;

	private static BufferedReader temRead;
	private static BufferedWriter temWrite;

	public static String toBinary(int b) {
	//	System.out.println(" char is " + (char) b + " and int is " + b);
		// b=html
		int arr[] = new int[8];
		String s = "";
		for (int i = 0; i < 8; i++) {
			arr[i] =  (b % 2);
			b = b / 2;

		}
		int arr2[] = new int[8];
		int j = 0;
		for (int i = 7; i >= 0; i--, j++) {
			s += arr[i];
		}

		//System.out.println(s);

		return s;

	}

	static BufferedWriter bu;

	/*
	 * public static void CheckSpecial(byte a, String b) { char temp =(char)a;
	 * fi2 = new File("temp.txt"); byte by=0; try { temWrite = new
	 * BufferedWriter(new FileWriter(fi2)); temWrite.write(temp);
	 * temWrite.close(); FileInputStream file_output = new FileInputStream(fi2);
	 * DataInputStream data_out = new DataInputStream(file_output);
	 * by=data_out.readByte();
	 * 
	 * //temRead= new BufferedReader(new FileReader(fi2)); //String line =
	 * temRead.readLine(); } catch (IOException e1) { // TODO Auto-generated
	 * catch block e1.printStackTrace(); }
	 * 
	 * String temp1 = Integer.toBinaryString(temp);
	 * 
	 * if (by > 256) { try {
	 * 
	 * bu.write(by+" "); bu.write(b+" | "); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * }
	 */
}