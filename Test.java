package BnGaya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Test {
public static void main (String args []){
	/*bwt b = new bwt();
	MTF m = new MTF();
	List<Integer> c = new LinkedList<Integer>();
	
	String symTable = "";//abcdefghijklmnopqrstuvwxyz
	char characters [] = new char[256];
	for(int i=0; i< characters.length; i++){
		characters[i]+=i;
		symTable=symTable+characters[i];
		System.out.println(characters[i]);
		}
	
	String a=b.Encode("kuchkuchhotahai$");
	c=m.encode(a, symTable);
	System.out.println(c);
	String d =m.decode(c, symTable);
	System.out.println(d);
	System.out.println(b.Decode(d));*/
	//BufferedReader buff = new BufferedReader(new FileReader(new File("C:\\Users\\DELL\\Downloads\\ab.txt")));
	
	
	
	/*FileHandleing fh = new FileHandleing("C:\\Users\\DELL\\Downloads\\ab.txt");
	BWTAd bwt = new BWTAd(fh.getText());
	bwt.Encode();
	try {
		BufferedWriter fw = new BufferedWriter(new FileWriter(new File("bwtFile.txt")));
		String[] arr = bwt.getBWT();
		for(int i=0;i<arr.length;i++){
			fw.write(arr[i]);
		}
		fw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	Compression comp =new Compression();
	//comp.beginHzipping("bwtFile.txt");
	comp.beginHzipping("C:\\Users\\DELL\\Downloads\\Forty-Rules (1).txt");//Forty-Rules (1)
	System.out.println("&#8364");
	//System.out.println(comp.manually(89));
	
	
	/*FileHandleing fhw = new FileHandleing("NewDecoded.txt");

	BWTAd a = new BWTAd(fhw.getText());
	a.Decode();
	try {
		BufferedWriter asa = new BufferedWriter(new FileWriter(new File("bwtDecode.txt")));
		String arr[] = a.getBWT();
		System.out.println(arr.length);
		for (int i = 0; i < arr.length; i++) {
			asa.write(arr[i]);
		}

		asa.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	/*byte b=60;
	int ret=b;
	ret = ~b;
	System.out.println(ret);
	ret = ret + 1;
	System.out.println(ret);
	ret = ret ^ 255;
	System.out.println(ret);
	ret += 1;
	//System.out.println(ret);
	//int b=76;
	System.out.println(ret);*/
	
}





}
