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
package exceptionhandler;

import javax.swing.JOptionPane;

import logging.Logging;

public class ExceptionHandler {

	private static Logging logger = Logging.getLogger();
	
	//Need to add functionality that will send mail to support
	public static void showException(Exception e){
		new JOptionPane("An error has occured").createDialog("").setVisible(true);
		logger.log(e);
	}
	
	public static void showException(Exception e,String message){
		new JOptionPane(message).createDialog("").setVisible(true);
		logger.log(e,message);
	}
}
