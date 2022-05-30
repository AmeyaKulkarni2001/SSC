
import java.io.*;
import java.util.*;

public class asgn4 {
	static List<String> MDT;
	static List<String> outFile;
	static Map<String, Integer> MNT;
	static int mntPtr, mdtPtr, aptPtr;
	static Map<String, String> APT;
	
	public static void main(String[] args) {
		try {
			pass2();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void pass2() throws Exception {

		//reading MDT and MNT file created by Pass 1
		BufferedReader mdtin = new BufferedReader(new InputStreamReader(new FileInputStream("MDT.txt")));
		BufferedReader mntin = new BufferedReader(new InputStreamReader(new FileInputStream("MNT.txt")));
	
		
		MDT = new ArrayList<String>();
		MNT = new LinkedHashMap<String, Integer>();
		APT = new HashMap<String, String>();
		outFile = new ArrayList<String>();
		mntPtr = 0; mdtPtr = 0; aptPtr = 0;
		
		//creating MDT and MNT from files
		String m;
		String defLine;
		String mname;
		while((m = mntin.readLine()) != null) {
			mname = tokenizeString(m," ")[0];
			MNT.put(mname, mdtPtr);
			defLine = mdtin.readLine();
			while(defLine != null) {
				if(!defLine.equals("MEND")) {
					MDT.add(mdtPtr, defLine);
					mdtPtr++;
					defLine = mdtin.readLine();
				} else {
					MDT.add(mdtPtr, "MEND");
					mdtPtr++;
					break;
				}	
			}
		}
		
		/*MNT.put("INCR", mdtPtr+"");
		MDT.add(mdtPtr, "INCR &ARG1,&ARG2");
		mdtPtr++;
		MDT.add(mdtPtr, "ADD AREG,#0");
		mdtPtr++;
		MDT.add(mdtPtr, "ADD BREG,#1");
		mdtPtr++;
		MDT.add(mdtPtr, "SUB BREG,#2");
		mdtPtr++;
		MDT.add(mdtPtr, "MEND");
		mdtPtr++;
		mdtPtr++;*/
		
		System.out.println("--------------MNT--------------");
		Iterator<String> itMNT = MNT.keySet().iterator();
		String key, mntRow, mdtRow;
		while(itMNT.hasNext()) {
			key = (String)itMNT.next();
			mntRow = key + " " + MNT.get(key);
			System.out.println(mntRow);
		}
		System.out.println("--------------MDT--------------");
		for(int i = 0; i< MDT.size();i++) {
			mdtRow = i + " " + MDT.get(i);
			System.out.println(mdtRow);
		}
		
		//reading input file created by Pass 1
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		String s;
		
		System.out.println("--------------Pass 2 Output--------------");
		while((s=input.readLine()) != null) {
			String s_arr[] = tokenizeString(s," ");
			int flag = 0;
			if(MNT.containsKey(s_arr[0])) {
				flag = 1;
				int mdtptr = MNT.get(s_arr[0])+1;
				
				//inserting actual arguments into Argument List
				String ap[] = tokenizeString(s_arr[1],", ");
				for(int i = 0; i<ap.length;i++) {
					//System.out.println("check"+ap[i]+" #"+aptPtr);
					APT.put(ap[i], "#"+aptPtr++);					
				}
				
				
				mdtRow = MDT.get(mdtptr);
				while(!mdtRow.equalsIgnoreCase("MEND")) {
					String mdt_arr[] = tokenizeString(mdtRow," ");
					
					System.out.print(mdt_arr[0]+" ");
					String mdt_par[] = tokenizeString(mdt_arr[1],",");
					//System.out.println("check" + mdt_par[1]);
					//replacing dummy parameters with actual parameters in macro call
					for(int i=0;i<mdt_par.length;i++) {
						if(mdt_par[i].startsWith("#")) {
							Iterator<String> itALA = APT.keySet().iterator();
							String key1;
							while(itALA.hasNext()) {
								key1 = (String)itALA.next();
								String ind = APT.get(key1);
								if(mdt_par[i].equalsIgnoreCase(ind)) {
									System.out.println(key1);
								}
							}
						}
						else {
							//System.out.println("check");
							System.out.print(mdt_par[i]+ " ");
						}
					}
					mdtptr = mdtptr+1;
					mdtRow = MDT.get(mdtptr);
				}
			}
			if(flag==0) {
				System.out.println(s);
			}
			
		}
		
		input.close();
		mdtin.close();
		mntin.close();
	}
	static String[] tokenizeString(String str, String seperator) {
		StringTokenizer st = new StringTokenizer(str, seperator, false);
		
		String s_arr[] = new String[st.countTokens()];
		for(int i=0; i<s_arr.length;i++) {
			s_arr[i] = st.nextToken();
		}
		return s_arr;
	}
}
