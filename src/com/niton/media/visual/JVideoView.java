package com.niton.media.visual;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jcodec.api.JCodecException;
import org.jcodec.api.SequenceEncoder;
import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;

import com.googlecode.mp4parser.util.ChannelHelper;
import com.niton.media.State;

/**
 * This is the JVideoView Class
 * @author Nils
 * @version 2017-08-16
 */
@Deprecated
public class JVideoView extends JPanel implements Runnable{
	/**
	 * <b>Type:</b> long<br> 
	 * <b>Description:</b><br>
	 */
	private static final long serialVersionUID = 5575891321912261977L;
	private JLabel pic = new JLabel();
	private Video v;
	public JVideoView(Video v) {
		this.setV(v);
		setLayout(new BorderLayout());
	}
	/**
	 * @return the v
	 */
	public Video getV() {
		return v;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(Video v) {
		stopPlay();
		this.v = v;
	}
	/**
	 * Description : 
	 * @author Nils
	 * @version 2017-08-16
	 */
	public void stopPlay() {
		v.setState(State.STOPPED);
	}
	
	public void startPlay() {
		v.setState(State.PLAYING);
	}
	
	public void pausePlay() {
		v.setState(State.PAUSED);
	}
	
	public void resumePlay() {
		v.setState(State.PLAYING);
	}
	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		msg.png
	}
}

