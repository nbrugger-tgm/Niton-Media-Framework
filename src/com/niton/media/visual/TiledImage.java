package com.niton.media.visual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This is the TiledImage Class<br>
 * A tiled image is a Image with many sub images<br>
 * It is used for World Tilesets or scalable pixel graphics
 * 
 * @author Nils
 * @version 2017-08-18
 */
public class TiledImage {
	private BufferedImage image;
	private int rows;
	private int cols;

	/**
	 * Reads the input Stream as image Creates an Instance of TiledImage.java
	 * 
	 * @author Nils
	 * @version 2017-10-06
	 * @param source
	 *            the source of the image. Often will be a file
	 * @param rows
	 *            count of the rows the image has
	 * @param cols
	 *            count of the columns the image has
	 */
	public TiledImage(InputStream source, int rows, int cols) {
		try {
			setImage(ImageIO.read(source));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading image!");
			e.printStackTrace();
		}
		setRows(rows);
		setCols(cols);
	}

	/**
	 * Scales the whole image about the factor
	 * 
	 * @author Nils
	 * @version 2017-10-06
	 * @param factor
	 *            the factor to multiply
	 */
	public void scale(double factor) {
		BufferedImage scaled = new BufferedImage((int) (factor * image.getWidth()), (int) (factor * image.getHeight()),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D grph = (Graphics2D) scaled.getGraphics();
		grph.scale(factor, factor);
		grph.drawImage(image, 0, 0, null);
		grph.dispose();
		image = scaled;
	}

	/**
	 * returns a tile of the image
	 * 
	 * @author Nils
	 * @version 2017-10-06
	 * @param x
	 *            starting at 1
	 * @param y
	 *            starting at 1
	 * @return the tile at the position
	 */
	public BufferedImage getTile(int x, int y) {
		if (x > cols || y > rows) {
			throw new IllegalArgumentException("Tile out of bound");
		}
		int tileWidth = image.getWidth() / cols;
		int tileHeight = image.getHeight() / rows;

		BufferedImage tile = new BufferedImage(tileWidth, tileHeight, image.getType());
		int[] raster = image.getRGB(x * tileWidth, y * tileHeight, tileWidth, tileHeight, null, 0, tileHeight);
		tile.setRGB(0, 0, tileWidth, tileHeight, raster, 0, tileHeight);
		return tile;
	}

	/**
	 * 
	 * returns a tile of the image
	 * 
	 * @author Nils
	 * @version 2017-10-06
	 * @param x
	 *            starting at 1
	 * @param y
	 *            starting at 1
	 * @param color
	 *            the color as RGB all pixels are multiplied
	 * @return the tile at the position
	 */
	public BufferedImage getTile(int x, int y, int color) {
		if (x > cols || y > rows) {
			throw new IllegalArgumentException("Tile out of bound");
		}
		int tileWidth = image.getWidth() / cols;
		int tileHeight = image.getHeight() / rows;

		BufferedImage tile = new BufferedImage(tileWidth, tileHeight, image.getType());
		int nx = 0;
		int ny = 0;
		for (int ax = (x - 1) * tileWidth; ax < x * tileWidth; ax++) {
			ny = 0;
			for (int ay = (y - 1) * tileHeight; ay < y * tileHeight; ay++) {
				// System.out.println("For pixel : \nx: "+ax+"\ny: "+y );
				tile.setRGB(nx, ny, (image.getRGB(ax, ay) + color) / 2);
				ny++;
			}
			nx++;
		}
		return tile;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols
	 *            the cols to set
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}
}
