package com.niton.media.audio.nio.controll;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileFilter;

import com.niton.media.audio.nio.MusicFilter;
import com.niton.media.audio.nio.SoundManager;
import com.niton.media.filesystem.Directory;
import com.niton.media.filesystem.NFile;

public final class Listeners {
	private static JList<NFile> lastList;
	public static Directory selected = new Directory(File.listRoots()[0]);
	public static FileFilter allSupportedFilter = new FileFilter() {

		@Override
		public String getDescription() {
			return "All (*.mp4, *.mp3, *.ogg, *.pls)";
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().toLowerCase().endsWith(".mp3")
					|| f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".ogg")
					|| f.getName().toLowerCase().endsWith(".wav") || f.getName().toLowerCase().endsWith(".pls"));
		}
	};
	public static FileFilter playlistFilter = new FileFilter() {

		@Override
		public String getDescription() {
			return "Playlists (*.pls)";
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".pls");
		}
	};
	public static FileFilter musicFilter = new FileFilter() {

		@Override
		public String getDescription() {
			return "Music File (*.mp3, *.wav, *.ogg)";
		}

		@Override
		public boolean accept(File f) {
			return f.isDirectory() || (f.getName().toLowerCase().endsWith(".mp3")
					|| f.getName().toLowerCase().endsWith(".ogg") || f.getName().toLowerCase().endsWith(".wav"));
		}
	};

//	public static void onPressStopButton() {
//		System.out.println("Listeners.onPressStopButton()");
//		SoundManager.stopMusic();
//	}
//
//	public static void onPressPlayButton() {
//		System.out.println("Listeners.onPressPlayButton()");
//		if (SoundManager.next == null || SoundManager.next.getFile().toFile().getAbsolutePath()
//				.equals(SoundManager.curentMusic.getFile().toFile().getAbsolutePath())) {
//			switch (SoundManager.curentMusic.getPlayState()) {
//			case PAUSED:
//				SoundManager.continueMusic();
//				break;
//			case PLAY:
//				SoundManager.pauseMusic();
//				break;
//			case FINISHED:
//			case STOPPED:
//				SoundManager.startMusic();
//				break;
//			}
//		} else
//			SoundManager.startMusic();
//	}
//
//	public static void onPressPauseButton() {
//		System.out.println("Listeners.onPressPauseButton()");
//		SoundManager.pauseMusic();
//	}

	public static void onSelectMusic(InputStream selected) {
		System.out.println("Listeners.onSelectMusic()");
		if (selected == null)
			return;
//		int selectedIndex = 0;
//		for (int i = 0; i < lastList.getModel().getSize(); i++) {
//			String thisFile = lastList.getModel().getElementAt(i).getPathAsString();
//			if (selected.getPathAsString().equals(thisFile)) {
//				selectedIndex = i;
//				break;
//			}
//		}
//		if (selectedIndex != lastList.getSelectedIndex()) {
//			lastList.setSelectedIndex(selectedIndex);
//			return;
//		}
		SoundManager.setMusic(selected);
//		GuiStarter.splitPane.setRightComponent(new PureMusicScene(selected).getSceneComps());
	}

//	public static void onPressNextButton() {
//		System.out.println("Listeners.onPressNextButton()");
//
//		int index = 0;
//		NFile[] mp3s = new MusicFilter().filter(selected.getSubFiles());
//		for (int i = 0; i < mp3s.length; i++) {
//			NFile nFile = mp3s[i];
//			if (nFile.getPathAsString().equals(SoundManager.curentMusic.getFile().toFile().getAbsolutePath()))
//				index = i + 1;
//		}
//		NFile next = mp3s[index];
//		onSelectMusic(next);
//		SoundManager.startMusic();
//	}
//
//	public static void onPressBackButton() {
//		System.out.println("Listeners.onPressNextButton()");
//		if (SoundManager.curentMusic.getCurentPosition() > 3) {
//			System.out.println("Current pos > 3");
//			SoundManager.stopMusic();
//			SoundManager.startMusic();
//			return;
//		}
//
//		int index = 0;
//		NFile[] mp3s = new MusicFilter().filter(selected.getSubFiles());
//		for (int i = 0; i < mp3s.length; i++) {
//			NFile nFile = mp3s[i];
//			if (nFile.getPathAsString().equals(SoundManager.curentMusic.getFile().toFile().getAbsolutePath()))
//				index = i + -1;
//		}
//		NFile next = mp3s[index];
//		onSelectMusic(next);
//		SoundManager.startMusic();
//	}

//	public static void onSelectFolderOnTree(JTree tree, TreeSelectionEvent e, FolderView folderView) {
//		System.out.println("Listeners.onSelectFolderOnTree()");
//		TreePath path = e.getPath();
//		Object[] parts = path.getPath();
//		Directory dir;
//		String realPath = "";
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i < parts.length; i++) {
//			String object = parts[i].toString();
//			if (i != 0) {
//				builder.append(object);
//				if (i != 1)
//					builder.append(System.getProperty("file.separator"));
//			}
//		}
//		realPath = builder.toString();
//		dir = new Directory(realPath);
//		final DefaultMutableTreeNode before = (DefaultMutableTreeNode) path.getLastPathComponent();
//		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getLastPathComponent();
//		parentNode = folderModel.expand(parentNode, dir);
//		tree.expandPath(path);
//		selected = dir;
//		aktualizeFileView(null);
//	}

//	public static void onCollapseFolderTree(JTree tree, FolderView folderView, TreeExpansionEvent arg0) {
//		System.out.println("Listeners.onCollapseFolderTree()");
//	}
//
//	public static DefaultFolderModel getFolderModel() {
//		System.out.println("Listeners.getFolderModel()");
//		return folderModel;
//	}

	public static void onTimeChange(int pos) {
		System.out.println("Listeners.onTimeChange()");
		int sec = pos % 60;
		int min = (pos - sec) / 60;
//		if (sec > 9)
//			GuiStarter.timeSign.setText(min + ":" + sec);
//		else
//			GuiStarter.timeSign.setText(min + ":0" + sec);
//		GuiStarter.timeline.setValue(pos);
//		GuiStarter.timeline.repaint();
	}

//	public static void aktualizeFileView(JList<NFile> list) {
//		System.out.println("Listeners.aktualizeFileView()");
//		if (list == null) {
//			list = lastList;
//		}
//		try {
//			NFile[] files = selected.getSubFiles();
//			NFile[] musicFiles = new MusicFilter().filter(files);
//			list.setListData(musicFiles);
//			list.repaint();
//			lastList = list;
//		} catch (NullPointerException e) {
//		}
//	}

//	public static ListCellRenderer<NFile> getFileListRenderer() {
//		System.out.println("Listeners.getFileListRenderer()");
//		ListCellRenderer<NFile> renderer = new ListCellRenderer<NFile>() {
//
//			@Override
//			public Component getListCellRendererComponent(JList<? extends NFile> paramJList, NFile paramE, int paramInt,
//					boolean paramBoolean1, boolean paramBoolean2) {
//				JPanel panel = new JPanel();
//				FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 5, 5);
//				panel.setLayout(layout);
//				JLabel label = new JLabel(paramE.getPath().toFile().getName());
//				label.setForeground(Color.white);
//				panel.setBackground(paramBoolean1 ? Color.BLUE : Color.BLACK);
//				label.setFont(new Font("monospace", Font.PLAIN, 16));
//				panel.add(label);
//				return panel;
//			}
//		};
//		return renderer;
//	}

//	public static TreeCellRenderer getDirListRenderer() {
//		System.out.println("Listeners.getDirListRenderer()");
//		// TreeCellRenderer renderer = new DefaultDirectoryCellRenderer();
//		return null;
//	}

//	public static void setupFolderTree(JTree tree) {
//		System.out.println("Listeners.setupFolderTree()");
//		DefaultTreeSelectionModel selection = new DefaultTreeSelectionModel();
//		selection.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
//		tree.setSelectionModel(selection);
//	}

	public static void onPlayEnded() {
		System.out.println("Listeners.onPlayEnded()");
	}

	public static void onClickTimeLine(JProgressBar progressBar, MouseEvent arg0) {
		System.out.println("Listeners.onClickTimeLine()");
	}

	public static void onPressRepeatButton() {
		System.out.println("Listeners.onPressRepeatButton()");
	}

	public static void onPressOpenFile() {
		System.out.println("Listeners.onPressOpenFile()");
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(playlistFilter);
		chooser.addChoosableFileFilter(musicFilter);
		chooser.setFileFilter(allSupportedFilter);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showOpenDialog(null);
		File f = chooser.getSelectedFile();
		if(f == null)
			return;
		else if(f.isDirectory())
			return;
		try {
			onSelectMusic(new NFile(f).getInputStream());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}
}