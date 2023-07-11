import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Board extends JFrame implements Runnable
{
	private int knightCounter=0;
	private JButton[][] gBoard; // The buttons of the board

	private int[][] solution; // 2D array to store the solution for the Knight's tour
	private int[] moveX = { 2, 2, 1, -1, -2, -2, -1, 1, 2 };
	private int[] moveY = { -1, 1, 2, 2, 1, -1, -2, -2, -1 };
	private int firstRow=-1;
	private int firstCol=-1;
	private Thread t = new Thread(this); // A thread to animate the solution of the knight tour

	/**
	 * The constructor of the Board class. It initializes the board, sets its size and visibility, and sets the default close operation.
	 */
	public Board()
	{
		setTitle("Knight Tour Game!");
		initBoard();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setVisible(true);

	}

	/**

	 Constructor for Board class.
	 Initializes the game board with a given starting point and sets up the JFrame.
	 @param p a Point object representing the starting point for the Knight
	 */
	public Board(Point p){
		setTitle("Knight Tour Game!");
		initBoard();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setVisible(true);
		this.firstRow = (int) p.getX();
		this.firstCol = (int) p.getY();
	}


	/**
	 * Calls the `endGame()` method to initialize the board and obtain the key.
	 * @return a byte array containing the key for the solution
	 */
	public byte[] initGame() { //O(1)
		byte[] k = endGame();
		return k;
	}

	/**
	 * A method to initialize the board. It creates the buttons of the board, sets their background color, and adds them to the board.
	 */
	public void initBoard() //O(1)
	{
		gBoard = new JButton[8][8];

		solution = new int[2][65];
		setLayout(new GridLayout(8,8));
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{

				gBoard[i][j]=new JButton();
				
				if((i+j)%2==0)
					gBoard[i][j].setBackground(Color.black);
				else
					gBoard[i][j].setBackground(Color.white);
				

				add(gBoard[i][j]);
			}
		}
	}
	/**
	 * A method to get the start point of the knight.
	 * @return The initial point of the knight.
	 */
	public Point GetStartPoint() { //O(1)
		Point k = new Point(firstRow, firstCol);
		return k;
	}





	/**
	 * The run method of the thread. It animates the solution of the knight tour.
	 */
	public void run()
		{
			printAnimatedSolution();
		} //O(1)
	/**
	 * A method to animate the solution of the knight tour. It sets the text and background color of the buttons,
	 * and sleeps for 500ms between each move.
	 */
	public void printAnimatedSolution()
	{ //O(1)
		for(int i=1;i<65;i++)
		{		gBoard[solution[0][i]][solution[1][i]].setText(Integer.toString(i));
				gBoard[solution[0][i]][solution[1][i]].setBackground(Color.green);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
		}


	}
	/**
	 * Sets up the board for the end of the game, generates a random starting position if none is provided,
	 * runs the algorithm to solve the Knight's Tour puzzle and starts a thread to animate the solution on the board.
	 * @return a byte array containing the key for the solution
	 */
	/*

	 */
	public byte[] endGame()//O(1)
	{
			Solution s = new Solution();
			for(int i=0;i<8;i++)
			{
				for(int j=0;j<8;j++)
				{
					if((i+j)%2==0)
						gBoard[i][j].setBackground(Color.black);
					else
						gBoard[i][j].setBackground(Color.white);
					gBoard[i][j].setFont(new Font("arial",Font.BOLD,30));
					gBoard[i][j].setText("");
					gBoard[i][j].setEnabled(false);
				}
			}

			if(firstCol == -1 && firstRow==-1) {
				Random rnd = new Random();
				firstRow = rnd.nextInt(8);
				firstCol = rnd.nextInt(8);
			}
			// else - is decryption
			byte[] key = s.main(firstRow, firstCol);
			
			for(int i=1;i<65;i++)
			{
				solution[0][i]=s.solutions[0][i];
				solution[1][i]=s.solutions[1][i];
			}

			t.start();
			return key;


	}



}
