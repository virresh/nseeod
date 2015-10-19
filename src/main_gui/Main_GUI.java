/*
Copyright 2011 Rohit Jhunjhunwala

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
package main_gui;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import test_connection.CheckUpdates;
import test_connection.ValidateConnection;
import config.Config_Settings;

public class Main_GUI{
	
	public static void main(String args[]) throws IOException{
		File dir=new File(System.getProperty("user.dir")+"/Temp");
		File dirEOD=new File(System.getProperty("user.dir")+"/Eod");
		File dirEquity=new File(System.getProperty("user.dir")+"/Equity");
		File dirFutures=new File(System.getProperty("user.dir")+"/Futures");
		File dirCurr_fut=new File(System.getProperty("user.dir")+"/Currency Futures");
		File dirOptions=new File(System.getProperty("user.dir")+"/Options");
		
		if(!dirEOD.exists()){
			dirEOD.mkdir();
		}
		if(!dir.exists()){
			dir.mkdir();
		}
		if(!dirEquity.exists()){
			dirEquity.mkdir();
		}
		if(!dirFutures.exists()){
			dirFutures.mkdir();
		}if(!dirCurr_fut.exists()){
			dirCurr_fut.mkdir();
		}
		if(!dirOptions.exists()){
			dirOptions.mkdir();
		}
		if(!dirEOD.exists() ||!dir.exists()||!dirEquity.exists()||!dirFutures.exists()||!dirCurr_fut.exists()||!dirOptions.exists()){
			JOptionPane.showMessageDialog(null, "Cannot create temporary directories/files.\nPlease check whether you have sufficient privileges", "Error", JOptionPane.ERROR_MESSAGE);
		}
		File deleteFiles=new File(System.getProperty("user.dir")+"/Temp");
		File []tempFiles=deleteFiles.listFiles();
		for(int i=0;i<tempFiles.length;i++)
		{
			tempFiles[i].delete();
		}
		 //Check Latest version
		//Initialise Settings of checkboxes
		Config_Settings settings=new Config_Settings();
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GUI a=new GUI(settings);
		new ValidateConnection(a);
		a.getjLabelUpdate().setText("Checking for latest version");
		new CheckUpdates(a);
		a.setVisible(true);
	}
}
