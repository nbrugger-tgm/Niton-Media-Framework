package com.niton.media.visual;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Canvas Class
 * @author Niton
 * @version 2018-04-11
 */
public abstract class Canvas extends JPanel{
	private static final long serialVersionUID = 1956962518976682936L;
	private long lastFrame = System.currentTimeMillis();
	@Getter
	@Setter
	private boolean limitFrames = true;
	@Getter
	@Setter
	private int fps = 120;
	public abstract void paint(Graphics2D g,int delta);
	
	public void paintRectangle(Rectangle rec,Graphics g) {
		g.drawRect(rec.x, rec.y, rec.width, rec.height);
	}
	public void paintRectangle(Rectangle rec,Graphics2D g,int size,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		Stroke sbev = g.getStroke();
		g.setStroke(new BasicStroke(size));
		paintRectangle(rec, g);
		g.setColor(bev);
		g.setStroke(sbev);
	}
	public void paintOval(Rectangle oval,Graphics g) {
		g.drawOval(oval.x, oval.y, oval.width, oval.height);
	}
	public void paintOval(Rectangle rec,Graphics2D g,int size,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		Stroke sbev = g.getStroke();
		g.setStroke(new BasicStroke(size));
		paintOval(rec, g);
		g.setColor(bev);
		g.setStroke(sbev);
	}
	
	public void fillRectangle(Rectangle rec,Graphics g) {
		g.fillRect(rec.x, rec.y, rec.width, rec.height);
	}
	public void fillRectangle(Rectangle rec,Graphics2D g,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		paintRectangle(rec, g);
		g.setColor(bev);
	}
	public void fillOval(Rectangle oval,Graphics g) {
		g.fillOval(oval.x, oval.y, oval.width, oval.height);
	}
	public void fillOval(Rectangle rec,Graphics2D g,Color color) {
		Color bev = g.getColor();
		g.setColor(color);
		paintOval(rec, g);
		g.setColor(bev);
	}
	public void write(String text,Color c,Font font,int x,int y,Graphics g) {
		Color cbev = g.getColor();
		Font fbev = g.getFont();
		g.setColor(c);
		g.setFont(font);
		g.drawString(text, x, y+g.getFontMetrics().stringWidth(text));
		g.setColor(cbev);
		g.setFont(fbev);
	}
	public void write(String text,Color c,String fontName,int fontSize,boolean cursive,boolean bold,int x,int y,Graphics g) {
		write(text, c, new Font(fontName, (cursive?Font.ITALIC:0)+(bold?Font.BOLD:0), fontSize), x, y, g);
	}
	public void write(String text,Color c,int x,int y,Graphics g) {
		write(text, c, g.getFont(), x, y, g);
	}
	public void write(String text,int x,int y,Graphics g) {
		write(text, getForeground(), x, y, g);
	}
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(getFont());
		long now = System.currentTimeMillis();
		int dif = (int) (now-lastFrame);
		if(limitFrames && dif < (1000/fps)) {
			try {
				Thread.sleep((1000/fps)-dif);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lastFrame = now;
		paint(g2, dif);
		repaint();
	}
}

