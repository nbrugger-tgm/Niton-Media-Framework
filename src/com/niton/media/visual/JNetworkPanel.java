package com.niton.media.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * This is the JNetworkPanel Class
 * 
 * @author Niton
 * @version 2018-04-05
 */
public class JNetworkPanel extends JPanel {
	/**
	 * <b>Type:</b> long<br>
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = -2562507359297954124L;
	private Color back1 = new Color(90, 10, 210);
	private Color back2 = new Color(50, 15, 220);
	private Color dots = new Color(255, 255, 255);
	private Color connectiones = new Color(230, 240, 255);
	private int radius = 150;
	private int thic = 5;
	private int conDist = 200;
	private int speed = 2;
	private int nodeSize = 15;
	private ArrayList<Point> nodes = new ArrayList<>();
	private ArrayList<Point> speeds = new ArrayList<>();

	public JNetworkPanel() {

	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics arg0) {
		System.out.println("Paint");
		Graphics2D g2d = (Graphics2D) arg0;
		GradientPaint gradient = new GradientPaint(0, 0, back1, getWidth(), getHeight(), back2);
		g2d.setPaint(gradient);
		g2d.fillRect(getX(), getY(), getWidth(), getHeight());
		g2d.setColor(connectiones);
		for (Point point : nodes) {
			for (Point point2 : nodes) {
				if (point != point2) {
					int disdance = (int) point.distance(point2);
					if (disdance < conDist) {
						double onePercent = conDist / 100;
						double disPercent = disdance / onePercent;
						int thic = (int) (this.thic - (this.thic * (disPercent / 100)));
						g2d.setStroke(new BasicStroke(thic));
						g2d.drawLine(point.x + (nodeSize / 2), point.y + (nodeSize / 2), point2.x + (nodeSize / 2),
								point2.y + (nodeSize / 2));
					}
				}
			}
		}
		g2d.setColor(dots);
		for (Point point : nodes) {
			// g2d.setPaint(new RadialGradientPaint(point, 20,new float[]{0.0f,1.0f}, new
			// Color[]{dots,Color.BLUE} ));
			g2d.fillOval(point.x, point.y, nodeSize, nodeSize);
		}
		paintComponents(g2d);
		paintChildren(g2d);
		for (int i = 0; i < nodes.size(); i++) {
			Point p = nodes.get(i);
			p.translate(speeds.get(i).x, speeds.get(i).y);
			if (p.getX() <= 0 || p.getY() <= 0 || p.getX() >= getWidth() || p.getY() >= getHeight()) {
				nodes.remove(i);
				speeds.remove(i);
				spawnBallz(1);
			}
		}
		if (getMousePosition(true) != null) {
			Point m = getMousePosition(true);
			for (int i = 0; i < nodes.size(); i++) {
				Point point = nodes.get(i);
				if (m.distance(point) < radius) {
					if (point.getX() > m.getX())
						speeds.get(i).x = speed;
					else
						speeds.get(i).x = -speed;
					if (point.getY() > m.getY())
						speeds.get(i).y = speed;
					else
						speeds.get(i).y = -speed;
				}
			}
		}
	}

	/**
	 * @return the back1
	 */
	public Color getBack1() {
		return back1;
	}

	/**
	 * @param back1
	 *            the back1 to set
	 */
	public void setBack1(Color back1) {
		this.back1 = back1;
	}

	/**
	 * @return the back2
	 */
	public Color getBack2() {
		return back2;
	}

	/**
	 * @param back2
	 *            the back2 to set
	 */
	public void setBack2(Color back2) {
		this.back2 = back2;
	}

	/**
	 * @return the dots
	 */
	public Color getDots() {
		return dots;
	}

	/**
	 * @param dots
	 *            the dots to set
	 */
	public void setDots(Color dots) {
		this.dots = dots;
	}

	/**
	 * @return the connectiones
	 */
	public Color getConnectiones() {
		return connectiones;
	}

	/**
	 * @param connectiones
	 *            the connectiones to set
	 */
	public void setConnectiones(Color connectiones) {
		this.connectiones = connectiones;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius
	 *            the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * @return the thic
	 */
	public int getThic() {
		return thic;
	}

	/**
	 * @param thic
	 *            the thic to set
	 */
	public void setThic(int thic) {
		this.thic = thic;
	}

	/**
	 * @return the conDist
	 */
	public int getConDist() {
		return conDist;
	}

	/**
	 * @param conDist
	 *            the conDist to set
	 */
	public void setConDist(int conDist) {
		this.conDist = conDist;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the nodeSize
	 */
	public int getNodeSize() {
		return nodeSize;
	}

	/**
	 * @param nodeSize
	 *            the nodeSize to set
	 */
	public void setNodeSize(int nodeSize) {
		this.nodeSize = nodeSize;
	}

	/**
	 * @return the nodes
	 */
	public ArrayList<Point> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(ArrayList<Point> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the speeds
	 */
	public ArrayList<Point> getSpeeds() {
		return speeds;
	}

	/**
	 * @param speeds
	 *            the speeds to set
	 */
	public void setSpeeds(ArrayList<Point> speeds) {
		this.speeds = speeds;
	}

	public void spawnBallz(int count) {
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			nodes.add(new Point(r.nextInt(getWidth()), r.nextInt(getHeight())));
			int s = r.nextInt(speed) + 1;
			boolean neg = r.nextBoolean();
			Point p = new Point();
			p.setLocation(s * (neg ? -1 : 1), 0);
			s = r.nextInt(speed);
			neg = r.nextBoolean();
			p.setLocation(p.getX(), s * (neg ? -1 : 1));
			speeds.add(p);
		}
	}
}
