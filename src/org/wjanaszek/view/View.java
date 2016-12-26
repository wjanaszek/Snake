package org.wjanaszek.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.wjanaszek.model.Model;

public class View extends JPanel {
	private Model model;
	
	private JFrame window;
	
	private final static int WIDTH = 640;
	private final static int HEIGHT = 480;
	
	private BufferStrategy bs;
	
	public View(Model model){
		super();
		this.model = model;
		
		window = new JFrame(model.getTitle());
		//panel = new JPanel();
		Dimension dim = new Dimension(WIDTH, HEIGHT);
		
		window.setPreferredSize(dim);
		window.setMinimumSize(dim);
		window.setMaximumSize(dim);
		window.add(this);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setVisible(true);
		
	}
	
	public void update()	{
		Graphics g = this.getGraphics();
		render(g);
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		renderScore(model.getScore(), g);
		renderHighScore(model.getHighScore(), g);
		
		renderSnake(g);
		renderPoint(g);
		
		g.dispose();
	}
	
	public void renderNewHighScore(int newHighScore, Graphics g){
		g.setColor(Color.yellow);
		g.drawRect(200, 100, 320, 80);
		g.fillRect(200, 100, 320, 80);
		g.setColor(Color.black);
		g.drawString("--> Nowy najlepszy wynik: " + model.getHighScore(), 250, 150);
	}
	
	private void renderScore(int score, Graphics g)	{
		g.setColor(Color.gray);
		g.drawString("WYNIK: " + score, 570, 470);
		g.drawString("Nacisnij 'q', aby wyjsc z gry, lub 'SPACE', aby wlaczyc pauze", 190, 470);
	}
	
	private void renderHighScore(int highScore, Graphics g){
		g.setColor(Color.gray);
		g.drawString("NAJLEPSZY WYNIK: " + highScore, 20, 470);
	}
	
	private void renderSnake(Graphics g){
		//System.out.println("-- snake --");
		g.setColor(Color.black);
		for(int i = 0; i < model.getSnake().getSnakeList().size(); i++){
			//System.out.println(model.getSnake().getSnakeList().get(i).getX() + ", " + model.getSnake().getSnakeList().get(i).getY());
			g.fillRect(model.getSnake().getSnakeList().get(i).getX(), model.getSnake().getSnakeList().get(i).getY(), 16, 16);
		}
		//System.out.println("");
	}
	
	/**
	 * Metoda do wy�wietlenia okna dialogowego po zako�czeniu rozgrywki, zawieraj�cego pytanie o rozpocz�cie
	 * nowej rozgrywki, lub zako�czenie dzia�ania aplikacji (wyj�cie z gry).
	 * @param g bufor, po kt�rym rysujemy
	 */
	public void renderNewGameQuestion(Graphics g){
		g.setColor(Color.gray);
		//g.drawRect(200, 160, 320, 100);
		g.fillRect(200, 160, 320, 200);
		g.setColor(Color.black);
		g.drawString("--> Nacisnij 'space', aby rozpoczac nowa gre", 250, 480/2 - 45);
		g.setColor(Color.yellow);
		g.drawString("--> Nacisnij 'q', aby wyjsc z gry", 250, 640/2);
	}
	
	public void renderPause(Graphics g){
		g.setColor(Color.gray);
		g.drawRect(150, 140, 320, 120);
		g.fillRect(150, 140, 320, 120);
		g.setColor(Color.black);
		g.drawString("PAUSE", 150, 170);
		g.drawString("--> Nacisnij 'space', aby wrocic do gry", 180, 200);
		g.drawString("--> Nacisnij 'q', aby wyjsc z gry", 180, 230);
	}
	
	private void renderPoint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(model.generateX(), model.generateY(), 16, 16);
	}
	
	public JFrame getWindow()	{
		return window;
	}
}
