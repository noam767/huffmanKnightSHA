import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

public class Solution {

	private static int N = 8;
	int[][] solutions = new int[2][65];
	static Vector<Point> cordinates = new Vector<>();

	/**
	 * Finds possible positions for the knight in a chess board.
	 *
	 * @param matOptions a 2D array containing the number of possible moves in each square.
	 * @param mat        a 2D array representing the chess board.
	 * @param rows       an array containing the possible moves for a row.
	 * @param cols       an array containing the possible moves for a column.
	 * @param row        the current row position of the knight.
	 * @param col        the current column position of the knight.
	 * @param parashNo   the current move number.
	 * @param solutions  a 2D array containing the solutions found.
	 */
	public static void func(int matOptions[][], int mat[][], int rows[], int cols[], int row, int col, int parashNo, int solutions[][]) //O(1)
	{
		int min = 9; //אחד מעל הכמות המקסימאלית של צעדים
		int rowmin = 0, colmin = 0;
		Point cord = new Point(row, col);
		cordinates.add(cord);
		if (((((row - 1) > -1) && ((col + 2) < 8)) && (mat[row - 1][col + 2] == 0))) {
			matOptions[row + rows[0]][col + cols[0]]--;
			if ((matOptions[row + rows[0]][col + cols[0]]) < min) {
				min = matOptions[row + rows[0]][col + cols[0]];
				rowmin = row + rows[0];
				colmin = col + cols[0];
			}
		}

		if ((((row + 1) < 8) && ((col + 2) < 8)) && (mat[row + 1][col + 2] == 0)) {
			matOptions[row + rows[1]][col + cols[1]]--;
			if ((matOptions[row + rows[1]][col + cols[1]]) < min) {
				min = matOptions[row + rows[1]][col + cols[1]];
				rowmin = row + rows[1];
				colmin = col + cols[1];
			}
		}
		if ((((row + 2) < 8) && ((col + 1) < 8)) && (mat[row + 2][col + 1] == 0)) {
			matOptions[row + rows[2]][col + cols[2]]--;
			if ((matOptions[row + rows[2]][col + cols[2]]) < min) {
				min = matOptions[row + rows[2]][col + cols[2]];
				rowmin = row + rows[2];
				colmin = col + cols[2];
			}
		}
		if ((((row + 2) < 8) && ((col - 1) > -1)) && (mat[row + 2][col - 1] == 0)) {
			matOptions[row + rows[3]][col + cols[3]]--;
			if ((matOptions[row + rows[3]][col + cols[3]]) < min) {
				min = matOptions[row + rows[3]][col + cols[3]];
				rowmin = row + rows[3];
				colmin = col + cols[3];
			}
		}
		if ((((row + 1) < 8) && ((col - 2) > -1)) && (mat[row + 1][col - 2] == 0)) {
			matOptions[row + rows[4]][col + cols[4]]--;
			if ((matOptions[row + rows[4]][col + cols[4]]) < min) {
				min = matOptions[row + rows[4]][col + cols[4]];
				rowmin = row + rows[4];
				colmin = col + cols[4];
			}
		}
		if ((((row - 1) > -1) && ((col - 2) > -1)) && (mat[row - 1][col - 2] == 0)) {
			matOptions[row + rows[5]][col + cols[5]]--;
			if ((matOptions[row + rows[5]][col + cols[5]]) < min) {
				min = matOptions[row + rows[5]][col + cols[5]];
				rowmin = row + rows[5];
				colmin = col + cols[5];
			}
		}
		if ((((row - 2) > -1) && ((col - 1) > -1)) && (mat[row - 2][col - 1] == 0)) {
			matOptions[row + rows[6]][col + cols[6]]--;
			if ((matOptions[row + rows[6]][col + cols[6]]) < min) {
				min = matOptions[row + rows[6]][col + cols[6]];
				rowmin = row + rows[6];
				colmin = col + cols[6];
			}
		}
		if ((((row - 2) > -1) && ((col + 1) < 8)) && (mat[row - 2][col + 1] == 0)) {
			matOptions[row + rows[7]][col + cols[7]]--;
			if ((matOptions[row + rows[7]][col + cols[7]]) < min) {
				min = matOptions[row + rows[7]][col + cols[7]];
				rowmin = row + rows[7];
				colmin = col + cols[7];
			}
		}
		if (((((row - 1) > -1) && ((col + 2) < 8)) && (mat[row - 1][col + 2] == 0))) {
			matOptions[row + rows[8]][col + cols[8]]--;
			if ((matOptions[row + rows[8]][col + cols[8]]) < min) {
				min = matOptions[row + rows[8]][col + cols[8]];
				rowmin = row + rows[8];
				colmin = col + cols[8];
			}
		}

		solutions[0][parashNo] = rowmin;
		solutions[1][parashNo] = colmin;
	}

	/**
	 * Prints the given matrix to the console.
	 *
	 * @param solutions A two-dimensional integer array representing a matrix.
	 */

	public static void printMatrix(int[][] solutions) { //O(1)
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j < 65; j++) {
				System.out.print(solutions[i][j] + " ");

			}
			System.out.println();
		}

	}

	/**
	 * Generates a key based on a Knight's Tour on a chessboard.
	 *
	 * @param row The row index of the starting position.
	 * @param col The column index of the starting position.
	 * @return A byte array representing the generated key.
	 */

	public byte[] main(int row, int col) //O(1)
	{

		int i = 0, j = 0;
		int[][] matOptions = {{2, 3, 4, 4, 4, 4, 3, 2}, {3, 4, 6, 6, 6, 6, 4, 3}, {4, 6, 8, 8, 8, 8, 6, 4}, {4, 6, 8, 8, 8, 8, 6, 4}, {4, 6, 8, 8, 8, 8, 6, 4}, {4, 6, 8, 8, 8, 8, 6, 4}, {3, 4, 6, 6, 6, 6, 4, 3}, {2, 3, 4, 4, 4, 4, 3, 2}};
		int[] rows = {-1, 1, 2, 2, 1, -1, -2, -2, -1};
		int[] cols = {2, 2, 1, -1, -2, -2, -1, 1, 2};
		/*
		האלגוריתם עבד על 63 משבצות, כלומר היה מקרה אחד שבו האלגוריתם לא עבד. אז פעלתי כך: שיניתי את סדר הבדיקה של המשבצות.
הסדר שנקבע באופן שרירותי שוּנה במקרה שהמשבצת שהוגרלה היא המשבצת שבה האלגוריתם לא עבד.
 דבר זה הביא לכך שלא חשוב מאיפה יתחיל, המחשב ידע לפתור את החידה.

		 */
		int[][] mat = new int[N][N];
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++)
				mat[i][j] = 0;
		}
		mat[row][col] = 1;
		solutions[0][1] = row;
		solutions[1][1] = col;
		for (i = 2; i < 65; i++) {
			func(matOptions, mat, rows, cols, row, col, i, solutions);
			row = solutions[0][i];
			col = solutions[1][i];

			mat[row][col] = 1;
		}
		System.out.println(cordinates.toString());

		byte[] key = generateKey(cordinates, 256);
		cordinates.clear();
		System.out.println("The key (in bytes) : " + program.ByteArrToString(key));
		return key;
	}

	/**
	 * Generates a key from the given vector of points using SHA-256.
	 *
	 * @param points    A vector of points representing the Knight's Tour on a chessboard.
	 * @param keyLength The length of the generated key, in bits.
	 * @return A byte array representing the generated key.
	 */
	public static byte[] generateKey(Vector<Point> points, int keyLength) {
		// Convert vector of points to byte array
		byte[] pointBytes = new byte[points.size() * 8];
		for (int i = 0; i < points.size(); i++) { //O(1) always 64 points
			Point point = points.get(i);
			int x = (int) point.getX();
			int y = (int) point.getY();
			pointBytes[i * 8] = (byte) ((x >> 24) & 0xFF);
			pointBytes[i * 8 + 1] = (byte) ((x >> 16) & 0xFF);
			pointBytes[i * 8 + 2] = (byte) ((x >> 8) & 0xFF);
			pointBytes[i * 8 + 3] = (byte) (x & 0xFF);
			pointBytes[i * 8 + 4] = (byte) ((y >> 24) & 0xFF);
			pointBytes[i * 8 + 5] = (byte) ((y >> 16) & 0xFF);
			pointBytes[i * 8 + 6] = (byte) ((y >> 8) & 0xFF);
			pointBytes[i * 8 + 7] = (byte) (y & 0xFF);
		}

		byte[] key = new byte[keyLength / 8];

		// Hash the byte array using SHA-256
		byte[] hash = Sha256.hash(pointBytes); //A.C O(1)
		System.arraycopy(hash, 0, key, 0, key.length);


		return key;
	}

}

