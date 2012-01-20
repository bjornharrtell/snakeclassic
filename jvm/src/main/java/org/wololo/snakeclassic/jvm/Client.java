package org.wololo.snakeclassic.jvm;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.wololo.snakeclassic.core.Game;
import org.wololo.snakeclassic.vmlayer.BitmapFactory;
import org.wololo.snakeclassic.vmlayer.CanvasFactory;
import org.wololo.snakeclassic.vmlayer.VMContext;

public class Client extends Canvas implements MouseListener,
		VMContext {

	private static final long serialVersionUID = 1L;
	
	static int screenWidth = 640;
	static int screenHeight = 480;

	public static void main(String[] args) {
		Client client = new Client();
		
		client.setMinimumSize(new Dimension(screenWidth, screenHeight));
		client.setMaximumSize(new Dimension(screenWidth, screenHeight));
		client.setPreferredSize(new Dimension(screenWidth, screenHeight));
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(client, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addMouseListener(client);

		frame.setVisible(true);

		new Thread(new Game(client)).start();
	}

	public void render(Object bitmap) {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(2);
		} else {
			Graphics graphics = bufferStrategy.getDrawGraphics();
			// NOTE: I think this is the fastest drawImage for this purpose
			graphics.drawImage((BufferedImage)bitmap, 0, 0, screenWidth, screenHeight, null);
			graphics.dispose();
			bufferStrategy.show();
		}
	}

	@Override
	public BitmapFactory createBitmapFactory() {
		return new AWTBitmapFactory();
	}

	@Override
	public CanvasFactory createCanvasFactory() {
		return new AWTCanvasFactory();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getScreenWidth() {
		return screenWidth;
	}

	@Override
	public int getScreenHeight() {
		return screenHeight;
	}

}
