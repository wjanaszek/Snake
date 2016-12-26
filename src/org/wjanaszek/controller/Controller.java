package org.wjanaszek.controller;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;

import org.wjanaszek.model.Model;
import org.wjanaszek.view.View;

public class Controller implements Runnable {
	private Thread thread;
	private Model model;
	private View view;
	
	private boolean isRunning = false;
	private boolean inGame = false;
	private boolean newHighScore = false;
	private boolean inGameAgain = false;
	
	public Controller(Model model, View view){
		this.model = model;
		this.view = view;
		addKeyListener(this);
		start();
	}
	
	private void addKeyListener(Controller controller) {
		view.getWindow().addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					if (inGame) {
						if (model.getPauseVal()) {
							model.setPause(false);
						} else {
							model.setPause(true);
						} 
					}
					else	{
						inGame = true;
					}
				}
				if(e.getKeyChar() == 'q')	{
					System.exit(1);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					model.getSnake().left = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					model.getSnake().right = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					model.getSnake().down = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					model.getSnake().up = true;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					model.getSnake().left = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					model.getSnake().right = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					model.getSnake().down = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					model.getSnake().up = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
	}
	
	private synchronized void start()	{
		if(isRunning == true)	{
			return;
		}
		else	{
			isRunning = true;
			inGame = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private synchronized void stop()	{
		if(isRunning == true){
			isRunning = false;
			inGame = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else	{
			return;
		}
	}

	private void updateView()	{
		BufferStrategy bs = view.getWindow().getBufferStrategy();
		if(bs == null)	{
			view.getWindow().createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		view.render(g);
		bs.show();
	}
	
	@Override
	public void run() {
		view.getWindow().requestFocus();
		int fps = 0;
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double targetTick = 60.0;
		double ns = 1000000000/targetTick;
		double timer = System.currentTimeMillis();
		boolean firstTime = true;
		
		int counter = 0;
		
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			
			while(delta >= 1){
				if(inGame){
					if(counter == 0 || counter == 10 || counter == 20 || counter == 30 || counter == 40 || counter == 50){
						if(!model.getPauseVal()){
							inGame = model.update();
							updateView();
						}
						else	{
							BufferStrategy bs = view.getWindow().getBufferStrategy();
							if(bs == null)	{
								view.getWindow().createBufferStrategy(3);
								return;
							}
							Graphics g = bs.getDrawGraphics();
							view.renderPause(g);
							g.dispose();
							bs.show();
						}
					}
					counter++;
				}
				else	{
					BufferStrategy bs = view.getWindow().getBufferStrategy();
					if(bs == null)	{
						view.getWindow().createBufferStrategy(3);
						return;
					}
					Graphics g = bs.getDrawGraphics();
					if(newHighScore){
						view.renderNewHighScore(model.getHighScore(), g);
						try {
							model.writeHighScore(model.getScore());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
					view.renderNewGameQuestion(g);
					bs.show();
					g.dispose();
					
					final boolean kPress = false;
					
					view.getWindow().addKeyListener(new KeyListener(){
						
						@Override
						public void keyPressed(KeyEvent e) {
							if(e.getKeyCode() == KeyEvent.VK_SPACE && !kPress)	{
								//kPress = true;
								inGameAgain = true;
							}
							if(e.getKeyChar() == 'q' && !kPress){
								//kPress = true;
								isRunning = false;
								System.exit(1);
							}
						}
						
						@Override
						public void keyReleased(KeyEvent e) {}

						@Override
						public void keyTyped(KeyEvent e) {}
						
					});
				
					if(inGameAgain){
						restartGame();
					}
					
					delta = 0;
					break;
				}
				fps++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000)	{
				//System.out.println(fps);
				//System.out.println(model.getSnake().getSnakeList().size());
				counter = 0;
				fps = 0;
				timer += 1000;
			}
		}
		stop();
	}

	private void restartGame() {
		model.resetGame();
		newHighScore = false;
		inGameAgain = false;
		inGame = true;
		
	}
}
