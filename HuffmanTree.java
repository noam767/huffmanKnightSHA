import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.swing.*;
//THIS IS THE UPDATED VERSION


/**
 * A HuffmanTree is a binary tree used for encoding and decoding text. This implementation builds a
 * HuffmanTree from a given text string or byte array, and provides methods for encoding and decoding
 * text using the tree. The tree can also be visualized using the draw() method.
 */
public class HuffmanTree {

    private static Node root;

    /**
     * A node in the HuffmanTree. Each node contains a character, a frequency, and left and right child nodes.
     * Nodes are comparable based on their frequency.
     */
    private static class Node implements Comparable<Node> {
        private final char character;
        private final int frequency;
        private final Node left;
        private final Node right;

        Node(char character, int frequency, Node left, Node right) {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        /**
         * Returns true if the node is a leaf (i.e., has no left or right child nodes).
         *
         * @return true if the node is a leaf, false otherwise
         */
        boolean isLeaf() {
            return left == null && right == null;
        } //O(1)

        /**
         * Compares this node to another node based on their frequency.
         *
         * @param other the node to compare to
         * @return a negative integer, zero, or a positive integer as this node is less than, equal to,
         *         or greater than the other node.
         */
        public int compareTo(Node other) {
            return frequency - other.frequency;
        } //O(1)
    }
    /**
     * Constructs a HuffmanTree from the given text string. The frequency of each character in the string is
     * used to build the tree.
     *
     * @param text the text string to build the tree from
     */
    public HuffmanTree(String text) { //O(nlogn), where n is the number of distinct characters in the input text.
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }//O(n) => O(1) hashing * O(n) characters

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c : frequencies.keySet()) {
            pq.offer(new Node(c, frequencies.get(c), null, null));
        }//O(nlogn)

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            pq.offer(new Node('\0', left.frequency + right.frequency, left, right));
        }//O(nlogn) => offer(like insertion to heap) is O(logn) * O(n) nodes
        root = pq.poll();
        /*
        The HuffmanTree constructor takes a string as input and creates a Huffman tree based on the frequency of characters in the input string.

First, the function creates a HashMap called frequencies to store the frequency of each character in the input string.
 This is done by iterating over each character in the input string and updating the frequency count in the HashMap.

Next, a priority queue called pq is created to store the nodes of the Huffman tree.
For each unique character in the input string, a new Node object is created and added to the priority queue.
The node objects are constructed with the character, its frequency count, and null for its left and right children.
        The function then builds the Huffman tree by iteratively merging the two nodes with the smallest frequency count until only
         one node remains in the priority queue. This is done using a loop that continues while the priority queue contains more than one element.
         Inside the loop, the two nodes with the smallest frequency count are removed from the priority queue and merged into a new Node object
         with a frequency count equal to the sum of the two nodes. This new node is added back to the priority queue.

Finally, the function retrieves the root node of the Huffman tree from the priority queue and assigns it to the root of the instance variable.

The total time complexity of the constructor is O(nlogn), where n is the number of distinct characters in the input text.
 The reason for this is the construction of the HashMap, which has a time complexity of O(n),
 and the creation of the priority queue and the construction of the Huffman tree, both of which have a time complexity of O(nlogn).
        */
    }

    /**
     * Recursively assigns codes to each node in the tree.
     *
     * @param node the current node being processed
     * @param code the code assigned to the current node
     * @param codes a map of character codes to codes strings
     */

    private void assignCodes(Node node, String code, Map<Character, String> codes) { //O(n) - traverses eventually on all the nodes of a tree
        if (!node.isLeaf()) {
            assignCodes(node.left, code + "0", codes);
            assignCodes(node.right, code + "1", codes);
        } else {
            codes.put(node.character, code); //O(1)
        }
    }

    /**
     * Displays the Huffman Tree using Java Swing and returns a map of the Huffman codes for each character in the tree.
     *
     * @return Map of Huffman codes for each character in the tree
     */

    public Map<Character, String>  draw() { //O(n)
        JFrame frame = new JFrame("Huffman Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (root != null) {
                    int x = getWidth() / 2;
                    int y = 50;
                    int levelWidth = getWidth() / 4;
                    int levelHeight = 50;
                    drawNode(g, root, x, y, levelWidth, levelHeight); //O(n)
                }
            }
        };
        panel.setPreferredSize(new Dimension(800, 600));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        Map<Character, String> codes = new HashMap<>();
        assignCodes(root, "", codes);

        System.out.println("Huffman codes:");
        for (char c : codes.keySet()) {
            System.out.println(c + ": " + codes.get(c));
        }

        return codes;
    }

    /**
     * Recursive helper function to draw nodes in the Huffman Tree.
     *
     * @param g           Graphics object used for drawing
     * @param node        Current node being drawn
     * @param x           X-coordinate of current node
     * @param y           Y-coordinate of current node
     * @param levelWidth  Width of current level of nodes
     * @param levelHeight Height between current level of nodes
     */

    private void drawNode(Graphics g, Node node, int x, int y, int levelWidth, int levelHeight) { //O(n)
        g.setColor(Color.BLACK);
        g.drawOval(x - 15, y - 15, 30, 30);
        g.drawString(node.character + ": " + node.frequency, x - 10, y + 5);
        if (node.left != null) {
            int xLeft = x - levelWidth;
            int yLeft = y + levelHeight;
            g.setColor(Color.RED);
            g.drawLine(x, y, xLeft, yLeft);
            drawNode(g, node.left, xLeft, yLeft, levelWidth / 2, levelHeight);
        }
        if (node.right != null) {
            int xRight = x + levelWidth;
            int yRight = y + levelHeight;
            g.setColor(Color.BLUE);
            g.drawLine(x, y, xRight, yRight);
            drawNode(g, node.right, xRight, yRight, levelWidth / 2, levelHeight);
        }
    }



    /**
     * Encodes a byte array using the Huffman Tree and returns the encoded text as a string.
     *
     * @param data Byte array to encode
     * @return Encoded string
     * @throws UnsupportedEncodingException If the character encoding is not supported
     */
    public String encode(byte[] data) throws UnsupportedEncodingException { //O(n)

        Map<Character, String> codes = new HashMap<>();
        assignCodes(root, "", codes);
        StringBuilder sb = new StringBuilder();
        String text = new String(data, StandardCharsets.UTF_8);
        for (char c : text.toCharArray()) {
            sb.append(codes.get(c));
        }
        return sb.toString();
    }


    /**

     Decodes a given Huffman encoded byte array into its original string form.
     @param data the Huffman encoded byte array to be decoded
     @return the original string form of the given Huffman encoded byte array
     @throws UnsupportedEncodingException if the encoding is not supported
     */

    public static String decode(byte[] data) throws UnsupportedEncodingException { //O(n)
        Node current = root;
        StringBuilder sb = new StringBuilder();
        String encodedText = program.ByteArrToString(data);
        for (char c : encodedText.toCharArray()) {
            if (c == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.isLeaf()) {
                sb.append(current.character);
                current = root;
            }
        }
        return sb.toString();
    }





    /**

     Prints the Huffman codes for all characters in the Huffman tree.
     */

    public void printCodes() { //O(n)
        Map<Character, String> codes = new HashMap<>();
        assignCodes(root, "", codes);
        System.out.println("Huffman codes:");
        for (char c : codes.keySet()) {
            System.out.println(c + ": " + codes.get(c));
        }
    }

    /**

     Prints all unique characters present in the Huffman tree.
     */

    public void printHashSet() { //O(n)
        Set<Character> characters = new HashSet<>();
        getCharacters(root, characters);
        System.out.println("Characters in tree:");
        for (char c : characters) {
            System.out.println(c);
        }
    }

    /**

     A recursive helper method that populates the given set with all unique
     characters present in the Huffman tree.
     @param node the current node being processed in the Huffman tree
     @param characters the set of unique characters encountered so far
     */

    private void getCharacters(Node node, Set<Character> characters) { //O(n)
        if (!node.isLeaf()) {
            getCharacters(node.left, characters);
            getCharacters(node.right, characters);
        } else {
            characters.add(node.character);
        }
    }





}

