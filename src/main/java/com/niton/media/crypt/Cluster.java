package com.niton.media.crypt;
/**
 * This is the Cluster Class
 * A Cluster is a Class to organize a 2D byte array and move the rows and cols
 * @author Niton
 * @version 2018-04-05
 */
public class Cluster {
	private byte[][] cluster;

	/**
	 * Creates an size*size Cluster
	 * @author Niton
	 * @version 2018-04-05
	 * @param size the side size of the cluster
	 */
	public Cluster(int size) {
		cluster = new byte[size][size];
	}
	/**
	 * Creates an size*size Cluster and fills the array
	 * @author Niton
	 * @version 2018-04-05
	 * @param size the side size of the cluster
	 * @param fill the byte to fill into the Cluster
	 */
	public Cluster(int size, byte fill) {
		this(size);
		for (int i = 0; i < cluster.length; i++) {
			for (int j = 0; j < cluster[i].length; j++) {
				cluster[i][j] = fill;
			}
		}
	}

	/**
	 * Creates an Instance of Cluster with the size as side Size and fills up with data
	 * @author Niton
	 * @version 2018-04-05
	 * @param size the side size to use -> should be the sqrt of data.length
	 * @param data the data to fill in. Has to be a x^x size -> sqrt from length should be an byte
	 */
	public Cluster(int size, byte[] data) {
		this(size);
		setData(data);
	}

	/**
	 * Creates an sqrt(data.length) Cluster
	 * @author Niton
	 * @version 2018-04-05
	 * @param data the array the cluster should be made of. the length has to be an sqrt() valid int
	 */
	public Cluster(byte[] data) {
		double sqrt = Math.sqrt(data.length);
		if (sqrt == (long) sqrt) {
			cluster = new byte[(int) sqrt][(int) sqrt];
			setData(data);
		} else {
			throw new IllegalArgumentException("Data has to be an byte[n²] Value");
		}
	}

	/**
	 * Creates Cluster
	 * @author Niton
	 * @version 2018-04-05
	 * @param cluster the cluster in the right format
	 */
	public Cluster(byte[][] cluster) {
		if(cluster.length < 1)
			throw new IllegalArgumentException("The Square needs to have a minimum size of 1x1");
		if(cluster.length != cluster[0].length)
			throw new IllegalArgumentException("The byte[][] is not A Square");
		this.cluster = cluster;
	}

	/**
	 * Description : pushes the column downwards -> increases the index
	 * @author Niton
	 * @version 2018-04-05
	 * @param col the column index to push
	 * @param push the number how much it should be pushed
	 */
	public void pushColum(int col, int push) {
		Cluster original = new Cluster(getByteArray());
		for (int j = 0; j < cluster.length; j++) {
			byte my = original.get(col, j);
			int moveToIndex = j + push;
			moveToIndex %= cluster.length;
			while(moveToIndex < 0)
				moveToIndex += cluster.length;
			set(col, moveToIndex, (byte) (my/*+push*/));
		}
	}

	/**
	 * Description : equal to {@link Cluster#pushColum(int, int)}. The only difference is that a Row instead of a column is pushed
	 * @author Niton
	 * @version 2018-04-05
	 * @param row the row index to push
	 * @param push how much to push
	 */
	public void pushRow(int row, int push) {
		byte[] rowA = cluster[row];
		byte[] newRow = new byte[rowA.length];
		for (int i = 0; i < newRow.length; i++) {
			int newIndex = i + push;
			newIndex %= newRow.length;
			while(newIndex < 0) {
				newIndex += rowA.length;
			}
			newRow[newIndex] = (byte) (rowA[i]/*+push*/);
		}
		cluster[row] = newRow;
	}

	/**
	 * Sets the data to the Grid<br>
	 * the data length needs to be a sqrt without after comma numbers
	 * @author Niton
	 * @version 2018-04-05
	 * @param data the data to write
	 */
	public void setData(byte[] data) {
		double sqrt = Math.sqrt(data.length);
		if (sqrt != (long) sqrt) 
			throw new IllegalArgumentException("Data has to be an byte[n²] Value");
		int c = 0;
		for (int i = 0; i < cluster.length; i++) {
			byte[] b = cluster[i];
			for (int j = 0; j < b.length; j++) {
				cluster[i][j] = data[c];
				c++;
			}
		}
	}

	/**
	 * Returns the value from the grid at the postion col and row 
	 * @author Niton
	 * @version 2018-04-05
	 * @param col the column where the number of your interest is -> x
	 * @param row the row where the number of your interest is -> y
	 * @return the number at this position
	 */
	public byte get(int col, int row) {
		return cluster[row][col];
	}

	
	/**
	 * Writes a byte to the Grid 
	 * @author Niton
	 * @version 2018-04-05
	 * @param col the col (x axis) to put the value in
	 * @param row the roq (y axis) to put the value in
	 * @param value the value to put into the pos
	 * @return the byte you set
	 */
	public byte set(int col, int row, byte value) {
		return cluster[row][col] = value;
	}

	/**
	 * Description : Returns the Cluster as an 2D Byte Array
	 * @author Niton
	 * @version 2018-04-05
	 * @return the cluster pure data
	 */
	public byte[][] getCluster() {
		return cluster;
	}

	/**
	 * Description : Converts the Cluster to a 1D Byte array
	 * @author Niton
	 * @version 2018-04-05
	 * @return the Clusters Data as 1D Array
	 */
	public byte[] getByteArray() {
		int size = 0;
		for (int i = 0; i < cluster.length; i++) {
			size += cluster[i].length;
		}
		byte[] array = new byte[size];
		int m = 0;
		for (int i = 0; i < cluster.length; i++) {
			byte[] sub = cluster[i];
			for (int j = 0; j < sub.length; j++) {
				array[m] = sub[j];
				m++;
			}
		}
		return array;
	}

	/**
	 * An String representation of the clusters data<br>
	 * <u>For Example:</u><br>
	 * <code>
	 * [ 1][ 4][10]<br>
	 * [67][ 5][ 3]<br>
	 * [ 0][19][ 8]<br>
	 *	</code>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String line = "\n";
		for (int i = 0; i < cluster.length; i++) {
			byte[] bs = cluster[i];
			for (int j = 0; j < bs.length; j++) {
				byte b = bs[j];
				builder.append('[');
				if (b < 10 && b > -10) {
					builder.append(' ');
					builder.append(' ');
				} else if (b < 100&& b > -100) {

					builder.append(' ');
				}
				builder.append(b);
				builder.append(']');
			}
			builder.append(line);
		}
		return builder.toString();
	}
}
