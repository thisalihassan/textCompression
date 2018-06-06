package BnGaya;

import java.util.Arrays;

import huff.FileHandleing;

public class bwt {
	public static void main(String[] args) {
		FileHandleing fh = new FileHandleing();
    	fh.filereader();
       // String test = "this is an example for huffman encoding";
    	 String s = fh.getText()+"$";
		
		
		//String s = "abrakadabra$"; // abrakadabra
		String e = Encode(s);
		Decode(e);
	}

	public static String Encode(String s) {
		String t = s + s;
		String BWT = "";
		int size = s.length();
		System.out.println(size);
		String[] s1 = new String[size];
		for (int i = 0; i < size; i++) {
			s1[i] = t.substring(i, i + size);
		}
		for (int i = 0; i < size; i++) {
			System.out.println(s1[i]);
		}
		System.out.println();
		Arrays.sort(s1);
		System.out.print(Arrays.toString(s1));
		for (int i = 0; i < size; i++) {
			BWT += s1[i].charAt(size - 1);
		}
		System.out.println(BWT);
		return BWT;
	}

	public static String Decode(String s) {
		int size = s.length();
		String BWT[] = new String[size];
		for (int j = 0; j < size; j++) {
			BWT[j] = "";
		}
		for (int j = 0; j < size; j++) {
			int count = 0;
			for (int i = 0; i < size; i++) {
				BWT[i] = s.charAt(count) + BWT[i];
				System.out.println(BWT[i]);
				count++;
			}
			Arrays.sort(BWT);
			System.out.print(Arrays.toString(BWT));
		}
		for (int i = 0; i < size; i++) {

			if (BWT[i].charAt(size - 1) == ('$')) {
				System.out.println(BWT[i]);
				return BWT[i];
			}
		}
		return null;
	}
}