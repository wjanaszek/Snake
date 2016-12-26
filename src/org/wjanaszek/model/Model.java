package org.wjanaszek.model;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Model {
	private final static int WIDTH = 640;
	private final static int HEIGHT = 480;
	private final static String TITLE = "Snake";
	
	private int highScore;
	private int score;
	
	private int cordX;
	private int cordY;
	
	private int randX;
	private int randY;
	private boolean generatedX = false;
	private boolean generatedY = false;
	
	private static Random r;
	
	private boolean pause = false;
	
	private Snake snake;
	
	public Model()	{
		r = new Random();
		cordX = 0;
		cordY = 0;
		generateCoord();
		snake = new Snake(cordX, cordY, this);
		try {
			highScore = readHighScore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void generateCoord(){
		cordX = r.nextInt(630);
		while(cordX % 16 != 0){
			cordX = r.nextInt(630);
		}
		
		cordY = r.nextInt(470);
		while(cordY % 16 != 0){
			cordY = r.nextInt(470);
		}
	}
	
	public boolean update()	{
		boolean tmp = true;
		generatePoint();
		checkPointColision();
		tmp = snake.move();
		if(score > highScore){
			try {
				writeHighScore(score);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return tmp;
	}
	
	private boolean checkPointColision()	{
		if(snake.getSnakeList().get(0).getX() == randX && 
				snake.getSnakeList().get(0).getY() == randY){
			return true;
		}
		return false;
	}
	
	public void generatePoint()	{
		if(!generatedX){
			generateX();
		}
		if(!generatedY){
			generateY();
		}
		System.out.println("x = " + randX + ", y = " + randY);
	}
	
	public int generateX()	{
		if(!generatedX){
			randX = 1;
			while(randX % 16 != 0){
				randX = r.nextInt(630);
				if(randX == 0){
					continue;
				}
			}
			generatedX = true;
		}
		return randX;
	}
	
	public int generateY()	{
		if(!generatedY){
			randY = 1;
			while(randY % 16 != 0){
				randY = r.nextInt(470);
				if(randY == 0){
					continue;
				}
			}
			generatedY = true;
		}
		return randY;
	}
	
	private void incrementScore()	{
		score++;
	}
	
	public void resetGame()	{
		score = 0;
		generateCoord();
		snake = new Snake(cordX, cordY, this);
		update();
	}
	
	public int getScore()	{
		return score;
	}
	
	/**
	 * Metoda do odczytu najlepszego wyniku z pliku. Wywo�ywana tylko przy tworzeniu modelu.
	 * @return rekord
	 * @throws FileNotFoundException gdy plik nie zosta� znaleziony
	 * @see File, Scanner
	 */
	private static int readHighScore() throws FileNotFoundException	{
		Scanner s = new Scanner(new File("data.txt"));
		return Integer.valueOf(s.nextLine());
	}
	
	/**
	 * Metoda do zapisu warto�ci wyniku do pliku, je�li zosta� pobity nowy rekord. 
	 * @param highScore wartosc rekordu do zapisania w pliku
	 * @throws FileNotFoundException gdy plik nie zosta� znaleziony 
	 */
	public static void writeHighScore(int highScore) throws FileNotFoundException	{
		FileWriter pw = null;
		try {
			pw = new FileWriter(new File("data.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally	{
			try {
				pw.write(Integer.toString(highScore));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public int getRandX(){
		return randX;
	}
	
	public int getRandY(){
		return randY;
	}
	
	public int getHighScore()	{
		return highScore;
	}
	
	public static String getTitle()	{
		return TITLE;
	}
	
	public Snake getSnake()	{
		return snake;
	}
	
	public void generatingFlags(){
		generatedX = false;
		generatedY = false;
	}
	
	public void increaseScore(){
		score++;
	}
	
	public void setRandX(int val){
		randX = val;
	}
	
	public void setRandY(int val){
		randY = val;
	}
	
	public void setPause(boolean b){
		pause = b;
	}
	
	/**
	 * Metoda do zwracania warto�ci pola pause
	 * @return true, je�li pause == true, false w p.p.
	 */
	public boolean getPauseVal(){
		return pause;
	}
}
