import java.util.*;
import java.io.*;

public class asgn1 {

	public static void main(String[] args) throws IOException{
		HashMap<String, String> IS = new HashMap<>();
		IS.put("STOP", "00");
		IS.put("ADD", "01");
		IS.put("SUB", "02");
		IS.put("MULT", "03");
		IS.put("MOVER", "04");
		IS.put("MOVEM", "05");
		IS.put("COMP", "06");
		IS.put("BC", "07");
		IS.put("DIV", "08");
		IS.put("READ", "09");
		IS.put("PRINT", "10");
		
		//System.out.println("map: " + IS);
		
		HashMap<String, String> DL = new HashMap<>();
		DL.put("DC", "01");
		DL.put("DS", "02");
		
		HashMap<String, String> AD = new HashMap<>();
		AD.put("START", "01");
		AD.put("END", "02");
		AD.put("ORIGIN", "03");
		AD.put("EQU", "04");
		AD.put("LOTRG", "05");
		
		HashMap<String, String> registers = new HashMap<>();
		registers.put("AREG", "01");
		registers.put("BREG", "02");
		registers.put("CREG", "03");
		registers.put("DREG", "04");

		HashMap<String, String> CC = new HashMap<>();
		CC.put("LT", "1");
		CC.put("LE", "2");
		CC.put("EQ", "3");
		CC.put("GT", "4");
		CC.put("GE", "5");
		CC.put("ANY", "6");
		
		int ch;
		
		FileReader fr = null;
		try {
			String path = System.getProperty("user.dir");
			// System.out.println(path);
			fr = new FileReader(path + "/input.txt");
		} catch (FileNotFoundException fe) {
			System.out.println("File Not Found!");
		}
		
		String str = "";
		while((ch = fr.read()) != -1) {
			str += (char)ch;
			// System.out.println(str);
		}
		System.out.println(str);
		fr.close();
		
		StringTokenizer st = new StringTokenizer(str, " \n");
		
		String token1 = st.nextToken();
		// System.out.println(token1);
		
		int memAddress = Integer.parseInt(st.nextToken());
		
		System.out.println("\t" + "(AD," + AD.get(token1) + ")\t" + "\t(C," + memAddress + ")");
		
		while(st.hasMoreElements()) {
			String token = st.nextToken();
			// System.out.println("The Tokens are");
			// System.out.println(token);
			memAddress++;

			if(IS.containsKey(token)) {
				// System.out.println(IS.get(token));
				//System.out.println(memAddress + ")\t" + " (IS," + IS.get(token) + ")\t(" + registers.get(st.nextToken()) + ")(C," + st.nextToken() + ")");
				if(IS.get(token) == "07"){
					System.out.println(memAddress + ")\t" + " (IS," + IS.get(token) + ")\t(" + CC.get(st.nextToken()) + ")(C," + st.nextToken() + ")");
				} else {
					System.out.println(memAddress + ")\t" + " (IS," + IS.get(token) + ")\t(" + registers.get(st.nextToken()) + ")(C," + st.nextToken() + ")");
				}				
			} else if(DL.containsKey(token)) {
				System.out.println(memAddress + ")\t" + " (DL," + DL.get(token) + ")\t" + "(" + st.nextToken() + ")");
			} else if(AD.containsKey(token)) {
				System.out.println(memAddress + ")\t" + "(AD," + AD.get(token) + ")");
			}
		}

	}

}
