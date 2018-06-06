package BnGaya;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandleing {
	private FileReader file;
	private BufferedReader read;
	private String reader;
	FileHandleing(String path){
		reader = "";
		try {
			//file = new FileReader("C:\\Users\\Kalicifer\\Downloads\\Compressed\\New Text Document.txt");
			file = new FileReader(path);
			read = new BufferedReader(file);
			filereader();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
	}
	FileHandleing(){
		reader = "";
		try {
			//file = new FileReader("C:\\Users\\Kalicifer\\Downloads\\Compressed\\New Text Document.txt");
			file = new FileReader("G:\\3rd semester workplace\\Huffman\\Forty-Rules (1).txt");
			read = new BufferedReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//ArrayList<String> arr ;
	public void filereader() {
		try {
			
			String line = read.readLine();
			while(line!=null) {
				reader += line;
				
				//arr.add(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getText() {
	//	System.out.println(arr.toString());
		return reader;
	}
}
