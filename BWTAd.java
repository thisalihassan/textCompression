package BnGaya;

import java.util.Arrays;
import java.util.StringTokenizer;

public class BWTAd {
	private StringTokenizer isolator;
	private String Tokens[];
	private String bwtToken[];
	

	BWTAd(String text) {
		isolator = new StringTokenizer(text, "\r\n ()/:á-_’”';,.", true);
		bwtToken = new String[isolator.countTokens()];
		for(int i=0;i<bwtToken.length;i++){
			bwtToken[i] = "";
		}
	}
	
	public void Encode() {
		int i = 0;
		//bwtToken = new String[isolator.countTokens()];
		Tokens = new String[isolator.countTokens()];
		while (isolator.hasMoreTokens()) {
			Tokens[i] = isolator.nextToken();
			Encode(Tokens[i]+"$", i);
			i++;
		}
	}

	public void Encode(String s,int k) {
		String t = s + s;
		String BWT = "";
		int size = s.length();
		String[] s1 = new String[size];
		for (int i = 0; i < size; i++) {
			s1[i] = t.substring(i, i + size);
		}
		Arrays.sort(s1);
		for (int i = 0; i < size; i++) {
			BWT += s1[i].charAt(size - 1);
		}
		bwtToken[k] = BWT;
	}
	
	public void Decode() {
		int i = 0;
	//	bwtToken = new String[isolator.countTokens()];
		Tokens = new String[isolator.countTokens()];
		while (isolator.hasMoreTokens()) {
			Tokens[i] = isolator.nextToken();
			Decode(Tokens[i], i);
			i++;
		}
	}

	public String Decode(String s,int k) {
		int size = s.length();
		String BWT[] = new String[size];
		for (int j = 0; j < size; j++) {
			BWT[j] = "";
		}
		for (int j = 0; j < size; j++) {
			int count = 0;
			for (int i = 0; i < size; i++) {
				BWT[i] = s.charAt(count) + BWT[i];
				count++;
			}
			Arrays.sort(BWT);
		}
		for (int i = 0; i < size; i++) {

			if (BWT[i].charAt(size - 1) == ('$')) {
				return bwtToken[k] = BWT[i];
			}
		}
		return null;
	}
	
	public String[] getBWT() {
		return bwtToken;
	}
}