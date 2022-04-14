import java.util.*;
import java.io.*;

public class asgn1{
    public static void main(String[] args) throws IOException{
        
        BufferedReader br=null;
        FileReader fr=null;
        FileWriter fw=null;
        BufferedWriter bw=null;
        
        //Input Output file handling
        int LC=0;
        String Instropcode=null;

        String inputfilename = "test_input.txt";
        fr = new FileReader(inputfilename);
        br = new BufferedReader(fr);

        String OUTPUTFILENAME ="output.txt";
        fw= new FileWriter(OUTPUTFILENAME);
        bw= new BufferedWriter(fw);
        
        
        Hashtable <String, String> is = new Hashtable<>();
        is.put("STOP", "00");
        is.put("ADD", "01");
        is.put("SUB", "02");
        is.put("MULT", "03");
        is.put("MOVER", "04");
        is.put("MOVEM", "05");
        is.put("COMP", "06");
        is.put("BC", "07");
        is.put("DIV", "08");
        is.put("READ", "09");
        is.put("PRINT", "10");
        //System.out.println("Imperative statements : " + is);

        Hashtable <String, String> ad = new Hashtable<>();
        ad.put("START", "01");
        ad.put("END", "02");
        ad.put("ORIGIN", "03");
        ad.put("EQU", "04");
        ad.put("LTORG", "05");
        //System.out.println("Assembler Directives : " + ad);
        
        Hashtable <String, String> d1 = new Hashtable<>();
        d1.put("DC", "01");
        d1.put("DS", "02");
        //System.out.println("Declarative Statements : " + d1);

        Hashtable <String, String> registers = new Hashtable<>();
        registers.put("AREG", "1");
        registers.put("BREG", "2");
        registers.put("CREG", "3");
        registers.put("DREG", "4");
        //System.out.println("Regsiters : " + registers);

        //------------------------------------------------------------------------------Code Conversion
        Hashtable <String, Integer> LC1 = new Hashtable<String,Integer>();
        String name=null;
        
        Hashtable <String, String> symtab = new Hashtable<>();
        String reg="";
        String ICcode=null;
        while ((name = br.readLine()) != null) {
        
            //System.out.println(name);
            String s1 = name.split(" ")[0];
            // String temp;
            if (s1.equals("START")){
                String s2 = name.split(" ")[1];
                LC=Integer.parseInt(s2);
                for (Map.Entry <String, String> m : ad.entrySet()) {
                    if (s1.equals(m.getKey())) {
                    Instropcode=(String)m.getValue();
                    }
                }

                ICcode="-\t"+ "AD,"+ Instropcode + "\t-" + "\tC," + LC;
                System.out.println(ICcode);
            }

            else if (s1.equals("END")){
                for (Map.Entry <String, String> m : ad.entrySet()) {
                    if (s1.equals(m.getKey())) {
                        Instropcode=(String)m.getValue();
                    }
                }
                ICcode="-\t"+ "AD,"+ Instropcode+"\t-\t-";
                System.out.println(ICcode);
            }

            else if(s1.equals("ORIGIN")){ //check
                String s2 = name.split(" ")[1];
                boolean isNumeric = s2.chars().allMatch(Character::isDigit);
				// System.out.println("s2: "+s2);

				if(s2 == "LOOP"){
					LC += 3;
				}

                if(isNumeric){
                    LC= Integer.parseInt(s2);
                }

                
                else{
                    String ss1 = name.split(" ")[1];
					// System.out.println("ss1: "+ss1);
                    for(Map.Entry<String, String> m : symtab.entrySet()){
                        if(ss1.equals(m.getKey())){
							// System.out.println("Hello ");
                            LC =Integer.parseInt(m.getValue());
							LC += 3;
							
                        }
                    }
                }

                for (Map.Entry <String, String> m : ad.entrySet()) {
                    if (s1.equals(m.getKey())) {
                        Instropcode=(String)m.getValue();
                    }
                }
                ICcode="-\t"+ "AD,"+ Instropcode + "\t-" + "\tC," + LC;
                System.out.println(ICcode);
            }

            //Other Instructions
            else if(is.containsKey(s1)){
                for (Map.Entry<String, String> m : is.entrySet()) {
                    if (s1.equals(m.getKey())) {
                        Instropcode=(String)m.getValue();
                    }
                }

                String s2= name.split(" ")[1];
                for (Map.Entry <String, String> m : registers.entrySet()) {
                    if (s2.equals(m.getKey())) {
                        reg=(String)m.getValue();
                    }
                }

                String s3= name.split(" ")[2];
                symtab.put(s3, "");

                int s4 = 1;
                for (Map.Entry <String, String> m : symtab.entrySet()) {
                    if (s3.equals(m.getKey())) {
                        break;
                    }
                    else{
                        s4 += 1;
                    }
                }

                ICcode=LC+"\t"+ "IS,"+ Instropcode + "\t" + reg +"\tS,"+s4;
                LC=LC+1;
                System.out.println(ICcode);
            }

            else if((name.split(" ")[1]).equals("DS")|| name.split(" ")[1].equals("DC")){
                symtab.replace(s1,""+LC);
                String s2= name.split(" ")[1];
                String s3= name.split(" ")[2];
                if(s2.equals("DS")){
                    ICcode=LC+"\tDL,02\t-\tC,"+s3;
                    System.out.println(ICcode);
                }
                if(s2.equals("DC")){
                    ICcode=LC+"\tDL,01\t-\tC,"+s3;
                    System.out.println(ICcode);
                }
                LC=LC+ (Integer.parseInt(s3));
            }

            else {

                symtab.put(s1, ""+LC);
                String s2 = name.split(" ")[1];
                for (Map.Entry <String, String> m : is.entrySet()) {
                    if (s2.equals(m.getKey())) {
                        Instropcode=(String)m.getValue();
                    }
                }

                String s3 = name.split(" ")[2];
                for (Map.Entry <String, String> m : registers.entrySet()) {
                    if (s3.equals(m.getKey())) {
                        reg=(String)m.getValue();
                    }
                }

                String s4 = name.split(" ")[3];
                symtab.put(s4,"");
                LC1.put(s1,LC);
                ICcode=LC+"\t"+ "IS,"+ Instropcode + "\t" + reg +"\tS,"+s4;
                System.out.println(ICcode);
                LC=LC+1;
            }
            bw.write(ICcode+"\n");
        }

        br.close();
        bw.close();
        int s = 1;
        System.out.println("---------------------SYMTAB is:---------------------");
        for (Map.Entry<String, String> m : symtab.entrySet()) {
            System.out.println(s+"\t"+m.getKey()+"\t"+m.getValue()+"\n");
            s += 1;
        }
        
    }
}