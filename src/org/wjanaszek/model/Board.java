package org.wjanaszek.model;

public class Board {
	private byte[][] board;
	private Snake snake;
	
	public static final byte POINT = 0;
	public static final byte SNAKE = 1;
	public static final byte NOTHING = 3;
	
	private static final byte DIR_LEFT = 0;
	private static final byte DIR_RIGHT = 1;
	private static final byte DIR_DOWN = 2;
	private static final byte DIR_UP = 3;
	
	public Board(int width, int height, Snake snake)	{
		board = new byte[height][width];
		this.snake = snake;
	}
	
	public void updateBoard(int direction){
		if(direction == DIR_LEFT)	{
			
		}
		if(direction == DIR_RIGHT)	{
			
		}
		if(direction == DIR_DOWN)	{
			
		}
		if(direction == DIR_UP)		{
			
		}
	}
	
	public byte[][] getLayer()	{
		return board;
	}
	
}
