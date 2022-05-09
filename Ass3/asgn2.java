import java.io.*;
import java.util.*;

public class asgn2{
	static List<String> MDT;
	static Map<String,String> MNT;
	static Map<String,String> ALA;
	static List<String> outFile;
	static int mntPtr, mdtPtr, filePtr;
	static int argIndex = 1;
	
	
	public static void main(String[] args) {
				try {
					pass1();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
	
			static void pass1() throws Exception{
				MDT = new ArrayList<String>();
				MNT = new LinkedHashMap<String,String>();
				ALA = new HashMap<String,String>();
				outFile = new ArrayList<String>();
				mntPtr=0; mdtPtr=0; filePtr=0;
				
				BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
				String s;
				boolean processingMacroDefinition = false;
				boolean processMacroName = false;

				
				while((s = input.readLine())!= null)
				{
					
					String s_arr[] = tokenizeString(s," ");
					String curToken = s_arr[0];
					if(curToken.equalsIgnoreCase("MACRO")) {
					
						processingMacroDefinition = true;
						processMacroName = true;
						continue;
					}
					else{
						
						if(processingMacroDefinition ==false && processMacroName==false) {
								outFile.add(filePtr,s);
								filePtr++;
						}
					}
						
					if(processMacroName==true)
					{
						MNT.put(curToken,mdtPtr+"");
						
						mntPtr++;
						processMacroName = false;
						
						for(int i=1;i<s_arr.length;i++){
							ALA.put(s_arr[i], "#"+argIndex);
							argIndex=argIndex+1;
						}
						MDT.add(mdtPtr,s);
						
						mdtPtr++;
						continue;
					}
					if(processingMacroDefinition==true)
					{
						String strIndexArg;
						strIndexArg =processArguments(s);
						MDT.add(mdtPtr,strIndexArg);
						
						mdtPtr++;
					}
					if(curToken.equalsIgnoreCase("MEND")) {
						//MDT.add(mdtPtr++,s);
						processingMacroDefinition = false;
						processMacroName=false;
					}
				}
				input.close();
				
				System.out.println("-------MNT-------");
				Iterator<String> itMNT = MNT.keySet().iterator();
				String key,mntRow,mdtRow,outFileRow;
				
				while(itMNT.hasNext()) {
					key = (String)itMNT.next();
					mntRow = key + " " + MNT.get(key);
					System.out.println(mntRow);
				}
				
				System.out.println("--------ALA--------");
				Iterator<String> itALA = ALA.keySet().iterator();
				String key1,alaRow;
				
				while(itALA.hasNext()) {
					key1=(String)itALA.next();
					alaRow=key1+" "+ALA.get(key1);
					System.out.println(alaRow);
				}
				
				System.out.println("-------MDT--------");
				
				for(int i=0;i<MDT.size();i++)
				{
					
					mdtRow=(i+1)+" "+MDT.get(i);
					System.out.println(mdtRow);
					
				}
				
				System.out.println("----------output file-----------");
				
				for(int i=0;i<outFile.size();i++)
				{
					outFileRow =i+" "+outFile.get(i);
					System.out.println(outFileRow);
				}
			}
				
			static String[] tokenizeString(String str,String seperator)
			{
				StringTokenizer st = new StringTokenizer(str,seperator,false);
				String s_arr[]=new String[st.countTokens()];
				for( int i=0;i<s_arr.length;i++) {
					s_arr[i]=st.nextToken();
				}
				
				return s_arr;
			}
			// static void processArgumentList(String argList) {
			// 	StringTokenizer st = new StringTokenizer(argList," ",false);
			// 	// int argCount = st.countTokens();
			// 	// System.out.print(st);
			// 	// String curArg;
			// 	// for(int i =1; i <= argCount; i++)
			// 	// {				
			// 	// 	curArg = st.nextToken();
			// 	// 	ALA.put(curArg, "#"+i);
			// 	// 	i++;
			// 	// }
				
				
			// }
			static String processArguments(String argList) {
				StringTokenizer st = new StringTokenizer(argList," ",false);
				int argCount = st.countTokens();
				String curArg,argIndexed;
				for(int i=1;i<=argCount;i++) {				
					curArg = st.nextToken();
					argIndexed = ALA.get(curArg);
					
					if(argIndexed==null)
						continue;
					argList=argList.replaceAll(curArg, argIndexed);
					
				}
				return argList;
				
			}
				
}