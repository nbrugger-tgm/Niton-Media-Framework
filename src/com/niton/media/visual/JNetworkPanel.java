package com.niton.media.visual;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * This is the JNetworkPanel Class
 * 
 * @author Niton
 * @version 2018-04-05
 */
public class JNetworkPanel extends JPanel implements MouseWheelListener {
	@Delegate
	private final JNetworkPaint paint = new JNetworkPaint();
	/**
	 * <b>Type:</b> long<br>
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = -2562507359297954124L;

	@Getter
	@Setter
    private boolean autorefresh;

	@Getter
	@Setter
	private int framerate = 120;

	public JNetworkPanel() {
		new Thread(()->{
			while(true) {
				if(isVisible() && autorefresh)
					this.repaint();
				try {
					Thread.sleep(1000/framerate);
				} catch (InterruptedException e) {}
			}
		},"Network panel refresher").start();
		addMouseWheelListener(this);
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		paint.setMouse(getMousePosition(true));
		paint.paint((Graphics2D) g);
		paintComponents(g);
		paintChildren(g);
	}

	public void spawnBallz(int nr){
		paint.spawnBallz(nr, getWidth(), getHeight());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		paint.setReactiveStrenght(paint.getReactiveStrenght()+(e.getPreciseWheelRotation()*0.1));
		System.out.println(paint.getReactiveStrenght());
		if(Math.abs(paint.getReactiveStrenght()) >= 1)
			paint.setReactiveStrenght(paint.getReactiveStrenght() < 0 ? -1 : 1);
	}
}
