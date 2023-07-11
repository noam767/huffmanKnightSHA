import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.awt.Graphics;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class program {
    //main class




    /**

     The main method creates an instance of the UserScreen class.
     @param args command-line arguments (not used)
     @throws Exception if there is an error creating the UserScreen object
     */
    public static void main(String args[]) throws Exception {
        UserScreen scr = new UserScreen(); //O(n^2)
    }
    /**

     This method converts a byte array to a string using the UTF-8 encoding.
     @param toStr the byte array to be converted to a string
     @return the resulting string
     */
    public static String ByteArrToString(byte[] toStr){
        String s = new String(toStr, StandardCharsets.UTF_8); return s;
    }
    /**

     This method performs simple XOR encryption on a byte array using a key byte.
     @param data the byte array to be encrypted
     @param Key the key byte to use for encryption
     @return the encrypted byte array
     */
    public static byte[] encryption(byte[] data, byte [] Key) { //O(n^2)
        for(byte A : Key){
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (data[i] ^ A);
            }
        }
        return data;
    }


}

