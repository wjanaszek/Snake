package org.wjanaszek.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {
	private ArrayList<MovingObject> snake;
	private int direction;
	private int lastDirection;
	private int wrongDirection;
	private int speed = 16;
	public boolean left, right, down, up = false;
	
	private static Model model;
	
	private static final byte DIR_LEFT = 0;
	private static final byte DIR_RIGHT = 1;
	private static final byte DIR_DOWN = 2;
	private static final byte DIR_UP = 3;
	
	public Snake(int cordX, int cordY, Model model)	{
		this.model = model;
		snake = new ArrayList<>();
		snake.add(new MovingObject(cordX, cordY));
		Random r = new Random();
		direction = r.nextInt(4);
		lastDirection = direction;
		setWrongDirection(direction);
	}
	
	private void setWrongDirection(int direction){
		if(direction == DIR_LEFT){
			wrongDirection = DIR_RIGHT;
		}
		if(direction == DIR_RIGHT){
			wrongDirection = DIR_LEFT;
		}
		if(direction == DIR_DOWN){
			wrongDirection = DIR_UP;
		}
		if(direction == DIR_UP){
			wrongDirection = DIR_DOWN;
		}
	}
	
	private boolean checkLeft(){
		if(snake.get(0).getX() - speed == model.getRandX() && snake.get(0).getY() == model.getRandY()){
			return true;
		}
		else	{
			return false;
		}
	}
	
	private boolean checkRight(){
		if(snake.get(0).getX() + speed == model.getRandX() && snake.get(0).getY() == model.getRandY()){
			return true;
		}
		else	{
			return false;
		}
	}
	
	private boolean checkDown(){
		if(snake.get(0).getX() == model.getRandX() && snake.get(0).getY() + speed == model.getRandY()){
			return true;
		}
		else	{
			return false;
		}
	}
	
	private boolean checkUp(){
		if(snake.get(0).getX() == model.getRandX() && snake.get(0).getY() - speed == model.getRandY()){
			return true;
		}
		else	{
			return false;
		}
	}
	
	private boolean checkCollisionWithYourself(){
		for(int i = 0; i < snake.size(); i++){
			for(int j = 0; j < snake.size(); j++){
				if(i != j){
					if(snake.get(i).getX() == snake.get(j).getX() && snake.get(i).getY() == snake.get(j).getY()){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkWrongDirection(int direction){
		if(direction == wrongDirection){
			return true;
		}
		return false;
	}
	
	private void checkBounds(int i)	{
		if(snake.get(i).getX() > 640){
			snake.get(i).setX(0);
		}
		if(snake.get(i).getX() < 0){
			snake.get(i).setX(640);
			
		}
		if(snake.get(i).getY() > 480){
			snake.get(i).setY(0);
			
		}
		if(snake.get(i).getY() < 0){
			snake.get(i).setY(480);
			
		}
	}
	
	private boolean updateSnake(byte direction){
		if(checkCollisionWithYourself()){
			System.out.println("Koniec gry");
			return false;
		}
		if(direction == getDirLeft()){
			//snake.get(0).setXY(snake.get(0).getX() - speed, snake.get(0).getY());
			if(checkLeft()){
				snake.add(0, new MovingObject(model.getRandX(), model.getRandY()));
				for(int i = 0; i < snake.size(); i++){
					checkBounds(i);
				}
				model.generatingFlags();
				model.increaseScore();
				model.generatePoint();
				return true;
			}
			// calculate head:
			int x, y;
			x = snake.get(0).getX();
			y = snake.get(0).getY();
			x -= speed;
			for(int i = snake.size() - 1; i > 0; i--){
				snake.get(i).setXY(snake.get(i - 1).getX(), snake.get(i - 1).getY()); 
				checkBounds(i);
			}
			snake.get(0).setXY(x,  y);
			checkBounds(0);
			return true;
		}
		if(direction == getDirRight()){
			if(checkRight()){
				snake.add(0, new MovingObject(model.getRandX(), model.getRandY()));
				for(int i = 0; i < snake.size(); i++){
					checkBounds(i);
				}
				model.generatingFlags();
				model.increaseScore();
				model.generatePoint();
				return true;
			}
			// calculate head:
			int x, y;
			x = snake.get(0).getX();
			y = snake.get(0).getY();
			x += speed;
			for(int i = snake.size() - 1; i > 0; i--){
				snake.get(i).setXY(snake.get(i - 1).getX(), snake.get(i - 1).getY()); 
				checkBounds(i);
			}
			snake.get(0).setXY(x,  y);
			checkBounds(0);
			return true;
		}
		if(direction == getDirDown()){
			if(checkDown()){
				snake.add(0, new MovingObject(model.getRandX(), model.getRandY()));
				for(int i = 0; i < snake.size(); i++){
					checkBounds(i);
				}
				model.generatingFlags();
				model.increaseScore();
				model.generatePoint();
				return true;
			}
			// calculate head:
			int x, y;
			x = snake.get(0).getX();
			y = snake.get(0).getY();
			y += speed;
			for(int i = snake.size() - 1; i > 0; i--){
				snake.get(i).setXY(snake.get(i - 1).getX(), snake.get(i - 1).getY()); 
				checkBounds(i);
			}
			snake.get(0).setXY(x,  y);
			checkBounds(0);
			return true;
		}
		if(direction == getDirUp()){
			if(checkUp()){
				snake.add(0, new MovingObject(model.getRandX(), model.getRandY()));
				for(int i = 0; i < snake.size(); i++){
					checkBounds(i);
				}
				model.generatingFlags();
				model.increaseScore();
				model.generatePoint();
				return true;
			}
			// calculate head:
			int x, y;
			x = snake.get(0).getX();
			y = snake.get(0).getY();
			y -= speed;
			for(int i = snake.size() - 1; i > 0; i--){
				snake.get(i).setXY(snake.get(i - 1).getX(), snake.get(i - 1).getY()); 
				checkBounds(i);
			}
			snake.get(0).setXY(x,  y);
			checkBounds(0);
			return true;
		}
		return true;
	}
	
	public boolean move(){
		if(left | right | down | up){
			if(left && lastDirection != DIR_RIGHT){
				direction = getDirLeft();
				lastDirection = direction;
				return updateSnake(getDirLeft());
				
			}
			if(right && lastDirection != DIR_LEFT){
				direction = getDirRight();
				lastDirection = direction;
				return updateSnake(getDirRight());
			
			}
			if(down && lastDirection != DIR_UP){
				direction = getDirDown();
				lastDirection = direction;
				return updateSnake(getDirDown());
				
			}
			if(up && lastDirection != DIR_DOWN){
				direction = getDirUp();
				lastDirection = direction;
				return updateSnake(getDirUp());
				
			}
		}
		else	{
			return moveWithoutAction();
			
		}
		return true;
	}
	
	private boolean moveWithoutAction()	{
		if(direction == getDirLeft()){
			/*snake.get(0).setXY(snake.get(0).getX() - speed,
					snake.get(0).getY()); */
			return updateSnake(getDirLeft());
			
		}
		if(direction == getDirRight()){
			return updateSnake(getDirRight());
			
		}
		if(direction == getDirDown()){
			return updateSnake(getDirDown());
			
		}
		if(direction == getDirUp()){
			return updateSnake(getDirUp());
			
		}
		return true;
	}
	
	public int getDirection()	{
		return direction;
	}
	
	public List<MovingObject> getSnakeList()	{
		return snake;
	}

	public static byte getDirLeft() {
		return DIR_LEFT;
	}

	public static byte getDirRight() {
		return DIR_RIGHT;
	}

	public static byte getDirDown() {
		return DIR_DOWN;
	}

	public static byte getDirUp() {
		return DIR_UP;
	}
}
