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
package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//To be replaced with Java logging classes
public class logging {
	private File file = new File(System.getProperty("user.dir") + "/app.log"); // @jve:decl-index=0:
	private Boolean logFlag = true;
	private DisplayLogMessageListener displayMessageListener;
	private static logging logger;

	public static logging getLogger() {
		if (logger == null)
			logger = new logging();
		return logger;
	}

	public void log(String msg) {
		if (logFlag == true) // Check if logging is on
		{
			try {
				FileWriter writeLog = new FileWriter(file, true);
				writeLog.write(msg + "\n");
				writeLog.close();
				writeLog = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void log(Exception e) {
		if(e==null)
			return ;
		log(e.getMessage());
		for (int i = 0; i < e.getStackTrace().length; i++) {
			log(e.getStackTrace()[i].toString());
		}
	}
	
	public void log(String message, boolean showOnScreen) {
		if (showOnScreen)
			log(message, message);
		else
			log(message);
	}

	public void log(String message, String screenMessage) {
		log(message);
		sendMessageToDisplay(screenMessage);
	}
	
	public void log(Exception e, String userMessage){
		log(userMessage);
		log(e);
	}
	
	public void log(Exception e, String userMessage,boolean showOnScreen){
		if(showOnScreen)
			log(e, userMessage, userMessage);
		else
			log(e,userMessage);
	}
	
	public void log(Exception e, String userMessage, String screenMessage) {
		log(e);
		sendMessageToDisplay(screenMessage);
	}

	public void setLogFlag(Boolean flag) {
		logFlag = flag;
	}

	public void sendMessageToDisplay(String screenMessage) {
		if (displayMessageListener != null)
			displayMessageListener.newMessage(screenMessage);
	}

	public void setDisplayMessageListener(
			DisplayLogMessageListener displayMessageListener) {
		this.displayMessageListener = displayMessageListener;
	}
}
