/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptpro;

import static encryptpro.MainBody.jEButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Rakesh's PC
 */
public class Encrypt {


    public static String encryptFile(File f, JTextField jTextField1) throws IOException {
        int basek[] = new int[5];
    int count = 0, limit = 0;
    int maink[];
    char enckey[];
        String filename = f.getName();
        String filefullloc = f.getAbsolutePath();
        int i = filefullloc.lastIndexOf(filename);
        String fileloc = filefullloc.substring(0, i);
        String text = "";
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        int fileSize = 0;
        while (br.read() != -1) {
            fileSize++;
        }
        br.close();
        if (fileSize == 0) {
            JOptionPane.showMessageDialog(null, "Empty file can't be encrypted");
            return "";
        }
        System.out.println("!!FILE SIZE " + fileSize);
        fr = new FileReader(f);
        br = new BufferedReader(fr);
        FileWriter w = new FileWriter(fileloc + "encrypted" + filename);
        Random r = new Random();
        int n = r.nextInt(8) + 1;
        n = r.nextInt(8) + 1;
        n = r.nextInt(8) + 1;
        System.out.println(n);
        String s = "Encrypted through EncryptPro 2012.1." + n + ".7.6";
        System.out.println(s);
        s = fixedenc(s);
        System.out.println(s);
        w.write(s + "\n");

        int maxLength = r.nextInt((int) Math.round((fileSize - 1) / 2))
                + (fileSize - 1) / 2;
        System.out.println(maxLength);
        int t1 = r.nextInt(maxLength / 2), t2 = maxLength / t1;
        int n3 = t1 > t2 ? t1 : t2;
        n3 = n3 == 0 ? maxLength - 2 : n3;
        int n2 = t1 > t2 ? t2 : t1;
        n2 = n2 < 1 ? 1 : n2;
        //int n2 = r.nextInt(10) + 10;
        //int n3 = r.nextInt(60) + 20;
        limit = n3;
        maink = new int[n3];
        enckey = new char[n3];
        s = "" + n2 + "_" + n3 + "_" + fileSize + "_";
        int fiven = 0;
        while (fiven < 5) {
            int ran = (int) ((int) r.nextInt(8) + (int) 1);
            basek[fiven] = ran;
            s = s + String.valueOf(ran);
            fiven++;
        }
        System.out.println(s);
        s = simpleenc(s, n);
        System.out.println(s);
        w.write(s + "\n");
        int c = 0;
        while (c < n3) {
            int ran = (int) ((int) r.nextInt(8) + (int) 1);
            maink[c] = ran;
            c++;
        }
        System.out.println("" + maink.length + Arrays.toString(maink));
        int n2c = 0, n3c = 0;
        int m = 0, b = 0, d;
        while ((d = br.read()) != -1) {
            //  String ss=jTextArea1.getText();
            //if (!(ss == null || ss.equalsIgnoreCase("null"))) {
            //  System.out.println(ss.length() + "_" + ss);
            //for (int i2 = 0; i2 < ss.length(); i2++) {
            count = count >= limit ? 0 : count;
            char v = mainenc(d, maink[count]);
            System.out.println("wrote " + v);
            w.write(v);
            text += ""+v;
            count++;
            n2c++;
            //    System.out.println(n2c + "_" + i2 + "_" + ss.length());
            //  char d = ss.charAt(i2);
            System.out.println((char) d);
            if (n3c < n3) {
                if (n2c == n2) {
                    n2c = 0;
                    if (b > 4) {
                        b = 0;
                    }
                    System.out.println("b " + b + "_" + "maink[ " + m + " ]_" + limit);
                    System.out.println("basek[b]" + basek[b] + "_" + "maink[ " + m + " ]" + maink[m]);
                    System.out.println(maink[m]);
                    char point;
                    point = String.valueOf(maink[m]).charAt(0);
                    System.out.println("point at s1=" + point);
                    point = (char) (point + basek[b]);
                    System.out.println("point at s2=" + point);
                    enckey[m] = point;
                    w.write(point);
                    text += point;
                    System.out.println("wrote key " + point);
                    m++;
                    b++;
                    n3c++;
                }

            }
            if (n3c == n3) {
                n3c++;
                String pi = pickaboo(), fpi = "";
                System.out.println(pi);
                for (int i3 = 0; i3 < 5; i3++) {
                    String word = "" + pi.charAt(i3);
                    word = simpleenc(word, basek[i3]);
                    fpi = fpi + word;
                }
                System.out.println();
                System.out.println();
                System.out.println("_______fpi=" + fpi + "________________________________________________________");
                System.out.println();
                System.out.println();
                w.write(fpi);
            }

            // n2c++;                           
            //}
            //w.write("\n");
            //n2c=n2c+2;                                             
        }
        while (n3c < n3) {

            if (b > 4) {
                b = 0;
            }
            System.out.println("b " + b + "_" + "maink[ " + m + " ]_" + limit);
            System.out.println("basek[b]" + basek[b] + "_" + "maink[ " + m + " ]" + maink[m]);
            System.out.println(maink[m]);
            char point;
            point = String.valueOf(maink[m]).charAt(0);
            System.out.println("point at s1=" + point);
            point = (char) (point + basek[b]);
            System.out.println("point at s2=" + point);
            enckey[m] = point;
            w.write(point);
            m++;
            b++;
            n3c++;
        }
        System.out.println("enckey=" + Arrays.toString(enckey));
        System.out.println("___________done_______________");
        JOptionPane.showMessageDialog(null,
                "!__________FILE ENCRYPTION SUCCESSFUL__________!\n"
                + "!___ENCRYPTED FILE IS SAVED IN THE LOCATION OF OLD FILE___!\n"
                + "!___ i.e. FILE IS SAVED IN\n"
                + fileloc + "encrypted" + filename);
        w.close();
        jEButton.setVisible(false);
        jTextField1.setText(fileloc + "encrypted" + filename);
        return text;
    }

    private static String fixedenc(String s) {
        String z = "";
        for (int i = 0; i < s.length(); i++) {
            char c = (char) ((int) s.charAt(i) - 4);
            z = z + c;
        }
        return z;
    }

    private static String simpleenc(String s, int n) {
        String z = "";
        for (int i = 0; i < s.length(); i++) {
            char c = (char) ((int) s.charAt(i) + n);
            z = z + c;
        }
        return z;
    }

    private static char mainenc(int s, int v) {
        char z;
      //  if (count >= limit) {
      //      count = 0;
      //  }
        //System.out.println("mainenc" + count + "_" + limit);
        z = (char) (s + v);
       // count++;
        return z;
    }

    private static String pickaboo() {
        String s = JOptionPane.showInputDialog("type a fivedigit pin");
        String z;
        if (s.length() != 5) {
            JOptionPane.showMessageDialog(null, "type 5 digit pin only");
            pickaboo();
        } else {
            int check = 0;
            for (int i = 0; i < s.length(); i++) {
                int c = (int) s.charAt(i);
                if (c < 48 || c > 57) {
                    check = 1;
                    JOptionPane.showMessageDialog(null, "type 5 digit pin only");
                    pickaboo();
                }
            }
            if (check == 0) {
                return s;
            }
        }
        return null;
    }
}
