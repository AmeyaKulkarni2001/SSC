import java.util.*;
import java.io.*;

public class asgn2 {
	
	static List<String> MDT;
	static List<String> outFile;
	static Map<String,String>MNT;
	static int mntPtr, mdtPtr, filePtr,alaPtr;
	static Map<String,String> ALA;
	
	public static void main(String[] args) {
		
		try {
			pass1();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void pass1() throws Exception {
		//Initialize data structures
		
		MDT = new ArrayList<String>();
		MNT = new LinkedHashMap<String,String>();
		ALA = new HashMap<String,String>();
		outFile = new ArrayList<String>();
		mntPtr = 0; mdtPtr = 0; filePtr = 0;alaPtr = 0;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		String s;
		boolean processingMacroDefinition = false;
		boolean processMacroName = false;
		
		System.out.println("======= PASS1 OUTPUT ======");
		//Reading input one line at a time
		
		while((s = input.readLine())!=null) {
			//separating tokens from each line
			String s_arr[] = tokenizeString(s," ");
			String curToken = s_arr[0];
			
			if(curToken.equalsIgnoreCase("MACRO")) {
				processingMacroDefinition = true;
                processMacroName = true;
				continue;
			} else if(!processingMacroDefinition && !processMacroName) {
                outFile.add(filePtr, s);
                filePtr++;

            }
			if(processMacroName) {
				MNT.put(curToken, mdtPtr + "");
				mntPtr++;
				processMacroName = false;
				processArgumentList(s_arr[1]);
				
				MDT.add(mdtPtr,s);
				mdtPtr++;
				continue;
			}
			
			if(processingMacroDefinition) {
				String strIndexArg;
				strIndexArg = processArguments(s);
				MDT.add(mdtPtr,strIndexArg);
				
				mdtPtr++;
			}
			
			if(curToken.equalsIgnoreCase("MEND")) {
                //MDT.add(mdtPtr++,s);
                processingMacroDefinition = false;
				continue;
			}
			
		}
		input.close();
		
		System.out.println("======= MNT =======");
		Iterator<String> itMNT = MNT.keySet().iterator();
		String key,mntRow,mdtRow;
		while(itMNT.hasNext()) {
			key = (String)itMNT.next();
			mntRow = key + " "+ MNT.get(key);
			System.out.println(mntRow);
		}

		System.out.println("======= ALA =======");
		Iterator<String> itALA = ALA.keySet().iterator();
		String key1,alaRow;
		while(itALA.hasNext()) {
			key1 = (String)itALA.next();
			alaRow = key1 + " " + ALA.get(key1);
			System.out.println(alaRow);
			
		}
		
		System.out.println("======= MDT =======");
		for(int i=0; i<MDT.size();i++) {
			mdtRow = i + " " + MDT.get(i);
			System.out.println(mdtRow);
		}
		
	}
	
	static String[] tokenizeString(String str,String separator) {
		StringTokenizer st = new StringTokenizer(str,separator,false);
		//Construct an array of the separated tokens
		
		String s_arr[] = new String[st.countTokens()];
		for(int i=0;i<s_arr.length;i++) {
			s_arr[i] = st.nextToken();
		}
		return s_arr;
			
		
	}
	
	static void processArgumentList(String argList) {
		StringTokenizer st = new StringTokenizer(argList,",",false);
		int argCount = st.countTokens();
		String curArg;
		
		for(int i=alaPtr; i < argCount + alaPtr; i++) {
			curArg = st.nextToken();
			ALA.put(curArg, "#" + (i + 1)) ;   
		}
        alaPtr = argCount;
	}
	
	static String processArguments(String argList) {
		StringTokenizer st = new StringTokenizer(argList,",",false);
		int argCount = st.countTokens();
		String curArg=null,argIndexed=null;
		
		for(int i=1;i<=argCount;i++) {
			curArg = st.nextToken();
            argIndexed = ALA.get(curArg);
			
			if(argIndexed==null) {
				continue;
			}
			argList = argList.replaceAll(curArg, argIndexed);		
		}
		
		return argList;
	}
}
