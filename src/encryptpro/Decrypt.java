/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptpro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Rakesh's PC
 */
public class Decrypt {
            
    public static String decryptFile(File f) throws FileNotFoundException, IOException{
        String upass = null,spass = "";
        String text = "";
            int limit=0,count=0;
            int[] maink;
              FileReader subfr=new FileReader(f);
              BufferedReader subbr=new BufferedReader(subfr);
              String filename=f.getName();
            String filefullloc=f.getAbsolutePath();
            int z=filefullloc.lastIndexOf(filename);
            String fileloc=filefullloc.substring(0,z);
            File tempfile=new File(fileloc+"tempdec.txt");
        try (FileWriter w = new FileWriter(tempfile)) {
            String intro=subbr.readLine();
            intro=fixeddec(intro);
            System.out.println(intro);
            int basek[]=new int[5];
            if(intro.length()==41){
                if(intro.substring(0,intro.indexOf("2012.1")).equals("Encrypted through EncryptPro ")){
                    System.out.println("This File can be decrypted");
                    System.out.println("Starting decryption process");
                    int fixedk=Integer.valueOf(""+intro.charAt(36));
                    System.out.println("fik="+fixedk);
                    String baseinfo=subbr.readLine();
                    System.out.println("enc bas="+baseinfo);
                    baseinfo=simpledec(baseinfo,fixedk);
                    System.out.println("dec bas="+baseinfo);
                    int interval=getInt(baseinfo);
                    limit=getLim(baseinfo);
                    int fileSize = getSize(baseinfo);
                    System.out.println("interval="+interval);
                    System.out.println("limit="+limit);
                    maink=new int[limit];
                    for(int i=0;i<5;i++)
                        basek[i]=getBas(baseinfo,i);
                    System.out.println("basek="+Arrays.toString(basek));
                    int n2c=0,n3c=0,i=0;int b = 0, d;
                    String line ="";int charCount = 0;
                    while((d = subbr.read()) != -1){
                        System.out.println("line["+charCount+" , "+n2c+" ] "+(char)d);
                        if(n3c<limit){
                            n2c++;
                            if((n2c == interval+1) || charCount > fileSize){
                                if(b>4)b=0;
                                char c=(char)d;
                                System.out.println("enc c="+c);
                                c=getMain(c,basek[b]);
                                b++;
                                System.out.println("dec c="+c);
                                maink[n3c]=Integer.valueOf(""+c);
                                n2c=0;n3c++;
                            }
                            else
                            {
                                line += ""+(char)d;
                                charCount++;
                            }
                        }
                        else if(n3c==limit){
                            n3c++;
                            char pass[] = new char[5];
                            pass[0] = (char) d;
                            for (int t = 1;t < 5; t++)
                                pass[t] = (char) subbr.read();
                            System.out.println("______________spass before="+Arrays.toString(pass)+"___________");
                            String temp="";
                            for(int was=0;was<5;was++){
                                String let = ""+pass[was];
                                let=simpledec(let,basek[was]);
                                temp=temp+let;
                            }
                            spass=temp;
                            System.out.println();
                            System.out.println("______________spass after="+spass+"___________");
                            System.out.println();
                            
                        }
                        else
                        {
                            line += ""+(char)d;
                            charCount++;
                        }
                    }  w.write(line+"\n");
                    
                    
                    upass=JOptionPane.showInputDialog("Type password to decrypt");
                    if(spass.equals(upass)){
                        w.close();
                        try (FileReader mainfr = new FileReader(tempfile); 
                                BufferedReader mainbr = new BufferedReader(mainfr)) {
                            int maini=0;
                            line="";int ss;
                            while((ss=mainbr.read()) != -1){
                                char ch = (char)ss;
                                count = count >= limit ? 0 : count;
                                ch=maindec(ch, maink[count]);
                                count++;
                                line=line+ch;
                            }
                            text = line;
                            System.out.println("maink="+Arrays.toString(maink));
                            MainBody.jSButton.setVisible(true);
                            MainBody.jDButton.setVisible(false);
                            JOptionPane.showMessageDialog(null,"File Decrypted Successfully");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Sorry File cannot be decrypted without correct password");
                        MainBody.jSButton.setVisible(false);
                        MainBody.jDButton.setVisible(true);
                    }
                    MainBody.jEButton.setVisible(false);
                }
                else{
                    System.out.println("File cannot be decrypted because it s not "
                            + "encrypted through software if it is encrpyted in first place");
                    JOptionPane.showMessageDialog(null,"File cannot be decrypted because it s not "
                            + "encrypted through this software if it is encrpyted in first place");
                    MainBody.jEButton.setVisible(true);
                }
            }
            else{
                System.out.println("File cannot be decrypted because it s not "
                        + "encrypted through software if it is encrpyted in first place");
                JOptionPane.showMessageDialog(null,"File cannot be decrypted because it s not "
                        + "encrypted through this software if it is encrpyted in first place");
                MainBody.jEButton.setVisible(true);
            }
        }
            tempfile.deleteOnExit();
            System.out.println("temperory files deleted");
            return text;
                                                                    }
    private static String fixeddec(String s){
           String z="";
        for(int i=0;i<s.length();i++){
            char c=(char)((int)s.charAt(i)+4);
            z=z+c;
                                     }
        return z;
                                     }
    private  static String simpledec(String s,int n){
        String z="";
        for(int i=0;i<s.length();i++){
            char c=(char)((int)s.charAt(i)-n);
            z=z+c;
                                     }
        return z;
                                             }
    private static Integer getInt(String s){
        int i = Integer.valueOf(s.split("_")[0]);
        return i;
                                    }
     private static Integer getLim(String s){
        String vals[] = s.split("_");
        int i=Integer.valueOf(vals[1]);
        return i;
                                    }
     private static Integer getSize(String s){
        String vals[] = s.split("_");
        int i=Integer.valueOf(vals[2]);
        return i;
                                    }
     private static Integer getBas(String s,int i){
        s = s.split("_")[3];
       char c=s.charAt(i); 
       int j=Integer.valueOf(""+c);
        return j;
                                           }
     private static char getMain(char c,int i){
        char z=(char)((int)c-i);
         return z;
                                       }
     private static char maindec(char s, int v){
        char z;
        //if(count>=limit)count=0;
        //System.out.println("mainenc"+count+"_"+limit);
        z=(char)((int)s - v);
        //count++;
        return z;
                                      }
}
