package tictactoe;

import java.awt.Color;
import java.util.Random;

import hsa2.GraphicsConsole;

public class Conway {

	public static void main(String[] args) {
		
		new Conway();
		
	}
	
	static final int SIZE = 10;
	int height = 700;
	int width = 700;
	int col = width/SIZE;
	int row = height/SIZE;
	boolean running = true;
	
	Random rand = new Random();
	
	int[][] board = new int[col][row];
	
	GraphicsConsole gc = new GraphicsConsole(width, height);
	
	Conway() {
		setupGraphics();
		initialBoard();
		draw();
	}

	private void handleclicks() {
		int mx = gc.getMouseX();
		int my = gc.getMouseY();
		int cols = mx/SIZE;
		int rows = my/SIZE;
		
		if(board[cols][rows] == 0) {
			board[cols][rows] = 1;
		}
		else {
			board[cols][rows] = 0;
		}
		
		gc.setTitle(rows + " : " + cols);
	}

	int gameRule(int status, int neighbours) {
//		Different game rule, does something else.
//		if(status == 0 && neighbours > 4) return(1);
//		else if (status == 1 && neighbours < 3) return(0);
//		else if (status == 1 && neighbours == 3) return(0);
		if(status == 1 && neighbours > 3) return (0);
		else if(status == 1 && neighbours < 2) return (0);
		else if (status == 0 && neighbours == 3) return(1);
		else return(status);
	}
	
	int countNeighbours(int x, int y) {
		int neighbours = 0;
	    for (int i=-1; i <= 1; i++) {
	      for (int j=-1; j <= 1; j++) {
	        neighbours += board[x+j][y+i];
	      }
	    }
	    neighbours -= board[x][y];
	    return (neighbours);
		
	}

	void initialBoard() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				int r = rand.nextInt(2);
				board[j][i] = r;
			}
		}
	}
	
	void drawGraphics(int [][]board) {
		width = gc.getDrawWidth();
		height = gc.getDrawHeight();
		
		synchronized(gc) {
			for(int y = 0; y < row; y++) {
				for(int x = 0; x < col; x++) {
					if(board[x][y] == 0) {
						gc.setColor(Color.BLACK);
						gc.fillRect(x*SIZE, y*SIZE, SIZE, SIZE);
					}
					else if(board[x][y] == 1) {
						gc.setColor(Color.WHITE);
						gc.fillRect(x*SIZE, y*SIZE, SIZE, SIZE);
					}
				}
			}
		}
	}
	
	void draw() {
		int[][] board2 = new int[col][row];
		for(int i = 1; i < row-1; i++) {
			for(int j = 1; j < col - 1; j++) {
				int neighbours = countNeighbours(i, j);
				board2[i][j] = gameRule(board[i][j], neighbours);
			}
		}
		board = board2;
		drawGraphics(board);
		gc.sleep(25);
		handleclicks();
		draw();
	}
	
	void setupGraphics() {
		gc.setTitle("Conway's Game of Life");
		gc.setAntiAlias(true);
		gc.setStroke(2);
		gc.setLocationRelativeTo(null);
		gc.showDialog("Welcome to god training. Your first task is to oversee the colony of life. Impact their lives as you wish.", "Welcome");
		gc.enableMouse();
	}
	
}