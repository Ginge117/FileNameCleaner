package com.home.filenamecleaner;

import com.home.filenamecleaner.FileNameScrubber;

public class StartUp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				FileNameScrubber fileScrub = new FileNameScrubber();
				fileScrub.createAndShowGUI();
			}
		});
	}

}
