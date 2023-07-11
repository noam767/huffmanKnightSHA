import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

/**

 A class representing the user interface for encryption/decryption.

 Inherits from JFrame.
 */
public class UserScreen extends JFrame {

    private JButton enc, dec;
    private JTextField txtBox;
    private byte[] encTXT;

    private Point hint;

    /**

     Constructor for the UserScreen class.

     Initializes the JFrame and sets its properties.

     Also sets up the UI components and adds event listeners to the buttons.

     @throws FileNotFoundException if the file specified cannot be found.
     */

    public UserScreen() throws FileNotFoundException { //O(1)

        super("Encryption/Decryption");

        // background color
        getContentPane().setBackground(new Color(123, 224, 224));

        // Set layout
        setLayout(null); // set the layout

        // Create the Encrypt button
        enc = new JButton("Encrypt");
        enc.setBounds(250, 180, 150, 50); // set button position and size
        enc.setBackground(new Color(45, 125, 154)); // set button background color
        enc.setForeground(Color.WHITE); // set button text color
        enc.setFont(new Font("Arial", Font.BOLD, 20)); // set button font

        // Create the Decrypt button
        dec = new JButton("Decrypt");
        dec.setBounds(420, 180, 150, 50); // set button position and size
        dec.setBackground(new Color(45, 125, 154)); // set button background color
        dec.setForeground(Color.WHITE); // set button text color
        dec.setFont(new Font("Arial", Font.BOLD, 20)); // set button font

        // Create the JTextField
        txtBox = new JTextField(36);
        txtBox.setBounds(150, 50, 500, 30); // set text field position and size
        txtBox.setMargin(new Insets(0, -5, 0, 0));
        txtBox.setBackground(new Color(245, 245, 245)); // set text field background color
        txtBox.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // set text field border color

        // Add components to the frame
        add(enc);
        add(dec);
        add(txtBox);

        // Set focus on text field
        txtBox.requestFocusInWindow();

        // Add action listener to encrypt button
        enc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = txtBox.getText();
                // Call encryption method with input parameter
                String encrypted = null;

                try {
                    encrypted = Encrypt(input);
                } catch (UnsupportedEncodingException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                // Display result in text field
                txtBox.setText(encrypted);
            }
        });

        // Add action listener to decrypt button
        dec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte[] input = encTXT;
                // Call decryption method with input parameter
                String decrypted = null;
                try {
                    decrypted = Decrypt(input);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                // Display result in text field
                txtBox.setText(decrypted);
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    /**
     * Encrypts the given text using Huffman encoding and a knight's tour encryption algorithm.
     *
     * @param link the link to the text to be encrypted as a String.
     * @return the encrypted text as a String.
     * @throws UnsupportedEncodingException if the character encoding is not supported.
     * @throws FileNotFoundException        if the file is not found.
     * @throws InterruptedException         if the thread is interrupted.
     */
    public String Encrypt(String link) throws UnsupportedEncodingException, InterruptedException { //O(n^2)

        String text;
        // Now calling Files.readString() method to
        // read the file
        try {
            text = Files.readString(Path.of(link));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create a Huffman tree from the input text
        HuffmanTree tree = new HuffmanTree(text);
        Map<Character, String> codes = tree.draw(); //O(nlogn)

        // Encode the text using the Huffman codes
        byte[] toEncode = text.getBytes(StandardCharsets.UTF_8);
        String encodedText = tree.encode(toEncode); //O(1)

        // Debug printouts
        System.out.println("Encoded text: " + encodedText);
        tree.printCodes();
        tree.printHashSet();

        // Create a new board for the knight's tour encryption algorithm in O(1)
        System.out.println("The knight's travels:");
        Board b = new Board();
        byte[] key = b.initGame();
        Point x = b.GetStartPoint();
        String pToStr = x.getX() +"," + x.getY();
        try {
            // Now calling Files.writeString() method
            // with path , content & standard charsets
            Files.writeString(Path.of("D:/secret.txt"), pToStr,
                    StandardCharsets.UTF_8);
        }

        // Catch block to handle the exception
        catch (IOException ex) {
            // Print messqage exception occurred as
            // invalid. directory local path is passed
            System.out.print("Invalid Path");
        }

        String s = program.ByteArrToString(toEncode);

        // Debug printout
        System.out.println("Before Encryption: " + s);

        // Encrypt the encoded text using the knight's tour algorithm
        byte[] toEncrypt = encodedText.getBytes(StandardCharsets.UTF_8);
        toEncrypt = program.encryption(toEncrypt, key);  //O(n^2)
        s = program.ByteArrToString(toEncrypt);

        // Debug printout
        System.out.println("After Encryption: " + s);

        // Set the encrypted text in the text box and disable the encrypt button
        this.txtBox.setText(s);
        // Try block to check for exceptions
        try {
            // Now calling Files.writeString() method
            // with path , content & standard charsets
            Files.writeString(Path.of(link), s,
                    StandardCharsets.UTF_8);
        }

        // Catch block to handle the exception
        catch (IOException ex) {
            // Print messqage exception occurred as
            // invalid. directory local path is passed
            System.out.print("Invalid Path");
        }
        this.encTXT = toEncrypt;
        this.enc.setEnabled(false);
        this.enc.setFocusable(false);
        return s;
    }







    /**
     * Decrypts the given byte array using the encryption key obtained from the game board.
     *
     * @param text the byte array to be decrypted
     * @return a string "DECRYPTED" indicating that the decryption process has been completed successfully
     * @throws Exception if an error occurs during decryption
     */
    public String Decrypt(byte[] text) throws Exception { //O(n^2)
        // Get the start point of the knight's travel
        Point p;
        String strToPoint;
        // Now calling Files.readString() method to
        // read the file
        try {
            strToPoint = Files.readString(Path.of("D:/secret.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] coords = strToPoint.split(",");

        p = new Point((int) Double.parseDouble(coords[0]), (int) Double.parseDouble(coords[1]));
        System.out.println();
        System.out.println("Starts decryption..."); // /nStarts decryption /n
        System.out.println();
        // Create a new board with the start point
        Board b = new Board(p);
        // Obtain the encryption key by initializing the game board
        byte[] key = b.initGame();

        byte [] toDecode = text;
        // Decrypt the byte array using the encryption key
        toDecode= program.encryption(toDecode, key);
        String s= program.ByteArrToString(toDecode);

        System.out.println("After Decryption: "+s);

        // Decode the decrypted byte array using the Huffman Tree
        String decodedTxt = HuffmanTree.decode(toDecode);
        System.out.println("Decoded text: " + decodedTxt);

        // Disable the Decrypt button and hide the window
        this.dec.setFocusable(false);
        this.dec.setEnabled(false);
        this.setVisible(false);
        // Return a string indicating that the decryption process has been completed successfully
        return "DECRYPTED";
    }

}
