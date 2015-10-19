/*
Copyright (c) 2011,2012,2013,2014 Rohit Jhunjhunwala

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
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.DisplayLogMessageListener;
import logging.Logging;
import testconnection.DateValidation;

import commonfunctions.CommonFunctions;

import db.DBExecutor;
import downloadfiles.DownloadFileThread;
import downloadfiles.DownloadFilesListener;
import dto.ValidationDTO;

public class NseEOD implements DisplayLogMessageListener,DownloadFilesListener{
	
	private static Logging logger = Logging.getLogger();

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException {		
		File dir = new File(System.getProperty("user.dir")+"/Temp");
		if (!dir.exists()) {
			dir.mkdir();
		}
		else{
			File[] tempFiles = dir.listFiles();
			for (int i = 0; i < tempFiles.length; i++) {
				tempFiles[i].delete();
			}
		}
		logger.log("JRE version is "+ System.getProperty("java.version"));
		if(args.length >0){			
			logger.log("Starting in console mode");
			NseEOD consoleModeDownloader= new NseEOD();
			logger.addDisplayMessageListener(consoleModeDownloader);
			if(args[0].equalsIgnoreCase("-help") || args.length!=2)
				consoleModeDownloader.showCommandLineUsage();
			else{
				consoleModeDownloader.startDownloadInConsoleMode(args[0], args[1]);
			}
		}
		else{
		if (System.getProperty("sun.desktop").toLowerCase().contains(
				"windows".toLowerCase()))
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		else
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		logger.log("Starting in GUI mode");
		new GUI().setVisible(true);
		}
	}
	
	@Override
	public void newMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void downloadComplete() {
		//No implementation needed	
	}
	private void startDownloadInConsoleMode(String fromDateStr,String toDateStr) throws SQLException{
		if(new DBExecutor().checkForAnotherDBInstance()){
			String message="Another instance of NSE EOD Data Downloader is already running.";
			logger.log(message,true);
			logger.log("Exiting application",true);
		}
		else
		{
		Date fromDate=null , toDate = null;
		try{
		fromDate = CommonFunctions.getStringToDate(fromDateStr, CommonFunctions.DDMMYYYYhifenFormat);
		toDate =  CommonFunctions.getStringToDate(toDateStr, CommonFunctions.DDMMYYYYhifenFormat);
		}
		catch (ParseException e){
			Logging.getLogger().log(String.format("Date should be in %1$s format",CommonFunctions.DDMMYYYYhifenFormat), true);
			this.showCommandLineUsage();
			throw new RuntimeException(e);
		}
		ValidationDTO validation = new DateValidation().validateDate(
				fromDate, toDate);
		if (!validation.isSuccess()){
			logger.log(validation.getMessage(), true);
		}
		else{
		new DownloadFileThread(fromDateStr, toDateStr, this);
		}
		}
	}
	
	private void showCommandLineUsage(){
		logger.log("Usage : NSEEODDataDownloader [From date (dd-mm-yyyy)] [To date (dd-mm-yyyy)] \n All configuration settings need to configured from the GUI",true);
	}
}
