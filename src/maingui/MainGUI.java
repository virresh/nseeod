/*
Copyright (c) 2011,2012,2013 Rohit Jhunjhunwala

The program is distributed under the terms of the GNU General Public License

This file is part of NSE EOD Data Downloader.

NSE EOD Data Downloader is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

NSE EOD Data Downloader is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with NSE EOD Data Downloader.  If not, see <http://www.gnu.org/licenses/>.
 */
package maingui;

import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import logging.logging;
import config.configxml.SettingsFactory;

public class MainGUI {

	public static void main(String args[]) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		File dir = new File(System.getProperty("user.dir")+"/Temp");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File deleteFiles = new File(System.getProperty("user.dir") + "/Temp");
		File[] tempFiles = deleteFiles.listFiles();
		for (int i = 0; i < tempFiles.length; i++) {
			tempFiles[i].delete();
		}
		if (System.getProperty("sun.desktop").toLowerCase().contains(
				"windows".toLowerCase()))
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		else
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		SettingsFactory.getSettings();
		logging.getLogger().log(System.getProperty("java.version"));
		new GUI().setVisible(true);
	}
}
