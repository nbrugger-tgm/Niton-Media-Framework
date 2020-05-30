package com.niton.media.visual;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

@Setter
@Getter
public class JNetworkPaint {
    private Color back1 = new Color(90, 10, 210);
    private Color back2 = new Color(50, 15, 220);
    private Color dots = new Color(255, 255, 255);
    private Color connectiones = new Color(230, 240, 255);
    private int radius = 150;
    private int thic = 5;
    private int conDist = 200;
    private double speed = 1.5;
    private int nodeSize = 15;
    private ArrayList<Point2D.Double> nodes = new ArrayList<>();
    private ArrayList<Point2D.Double> speeds = new ArrayList<>();
    private long last = System.currentTimeMillis();
    private boolean randomDotsize;
    private boolean reactive = true;
    private double reactiveStrenght = 0.5;
    private Point mouse;

    public void paint(Graphics2D g) {
        int delta = (int) (System.currentTimeMillis() - last);
        last = System.currentTimeMillis();
        double timeMultiplyer = (double) delta/(1000/60);

        //Background
        GradientPaint gradient = new GradientPaint(0, 0, back1, (float) g.getClipBounds().getWidth(), (float) g.getClipBounds().getHeight(), back2);
        g.setPaint(gradient);
        g.fillRect((int) g.getClipBounds().getX(), (int) g.getClipBounds().getY(), (int) g.getClipBounds().getWidth(), (int) g.getClipBounds().getHeight());
        g.setColor(connectiones);

        //Draw connections
        for (Point2D point : nodes) {
            for (Point2D point2 : nodes) {
                if (point != point2) {
                    int disdance = (int) point.distance(point2);
                    if (disdance < conDist) {
                        int thic = (int) (this.thic * (1-disdance/conDist));
                        g.setStroke(new BasicStroke(thic));
                        g.drawLine((int) (point.getX() + (nodeSize / 2)), (int) (point.getY() + (nodeSize / 2)), (int) (point2.getX() + (nodeSize / 2)),
                                (int) (point2.getY() + (nodeSize / 2)));
                    }
                }
            }
        }

        //draw nodes
        g.setColor(dots);
        for (Point2D point : nodes) {
            // g2d.setPaint(new RadialGradientPaint(point, 20,new float[]{0.0f,1.0f}, new
            // Color[]{dots,Color.BLUE} ));
            g.fillOval((int) point.getX(), (int) point.getY(), nodeSize, nodeSize);
        }

        //move nodes by speed
        for (int i = 0; i < nodes.size(); i++) {
            Point2D p = nodes.get(i);
            p.setLocation(p.getX() + (speeds.get(i).getX() * timeMultiplyer), p.getY() + (speeds.get(i).getY() * timeMultiplyer));
            if (p.getX() <= 0 || p.getY() <= 0 || p.getX() >= g.getClipBounds().getWidth() || p.getY() >= g.getClipBounds().getHeight()) {
                nodes.remove(i);
                speeds.remove(i);
                spawnBallz(1, g.getClipBounds().width, g.getClipBounds().height);
            }
        }
        if (reactive && mouse != null) {
            for (int i = 0; i < nodes.size(); i++) {
                Point2D point = nodes.get(i);
                if (mouse.distance(point) < radius) {
                    double yChange = speed * reactiveStrenght, xChange = speed * reactiveStrenght;
                    if (point.getX() < mouse.getX())
                        xChange *= -1;
                    if (point.getY() < mouse.getY())
                        yChange *= -1;
                    double dist = (1 - (mouse.distance(point) / radius))*Math.abs(reactiveStrenght);
                    xChange *= dist;
                    yChange *= dist;
                    System.out.println("Dist multi : " + dist + " -> xc:" + xChange + " yc: " + yChange);
//                    System.out.println("Old speed " + speeds.get(i));
                    speeds.get(i).setLocation((speeds.get(i).getX() + xChange*timeMultiplyer), (speeds.get(i).getY() + yChange*timeMultiplyer));
                }
            }
        }
    }

    public void spawnBallz(int count, int xlim, int ylim) {
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            nodes.add(new Point2D.Double(r.nextInt(xlim), r.nextInt(ylim)));
            double s = (r.nextDouble() + speed / 10) % speed;
            boolean neg = r.nextBoolean();
            Point2D.Double p = new Point2D.Double();
            p.setLocation(s * (neg ? -1 : 1), 0);
            s = (r.nextDouble() + speed / 10) % speed;
            neg = r.nextBoolean();
            p.setLocation(p.getX(), s * (neg ? -1 : 1));
            speeds.add(p);
        }
    }
}
