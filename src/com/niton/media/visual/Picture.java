package com.niton.media.visual;

import java.awt.GridLayout;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
@Deprecated
public class Picture extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7074827198461976632L;
	@Deprecated
	public Picture(Path path) {
		try {
			@SuppressWarnings("deprecation")
			Icon icon = new ImageIcon(path.toFile().toURL());
			super.setLayout(null);
			JLabel label = new JLabel(icon);
			label.setVisible(true);
			this.add(label);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Deprecated
	public Picture(Path path, String text) {
		try {
			@SuppressWarnings("deprecation")
			Icon icon = new ImageIcon(path.toFile().toURL());
			super.setLayout(new GridLayout());
			JLabel label = new JLabel(icon);
			label.setText(text);
			label.setVisible(true);
			this.add(label);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
