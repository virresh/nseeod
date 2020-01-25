/*
Author: Rohit Jhunjhunwala (2011-2016)
Modified by: Viresh Gupta (@virresh)

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

//As of now only two levels(INFO,SEVERE) are retrofitted to avoid major code refactoring. With time old methods will be deprecated/removed and replaced with new methods

public class Logging {
	
	
	private List<DisplayLogMessageListener> displayMessageListeners;
	private static Logging logger;
	private static Logger JUL;
	
	//avoiding double checked locking or similar optimization as it would be an overkill
	public synchronized static Logging getLogger() {
		if (logger == null){
			logger = new Logging();
			logger.displayMessageListeners = new ArrayList<DisplayLogMessageListener>();
			JUL=Logger.getLogger("");
			try {
				Handler[] handlers= JUL.getHandlers();
				//Default level info
				JUL.setLevel(Level.INFO);
				for(int i=0;i<handlers.length;i++){
					JUL.removeHandler(handlers[i]);}
				FileHandler fileHandler =new FileHandler(System.getProperty("user.dir") + "/app.log",true);
				Formatter customFormatter = new Formatter() {
					@Override
					public String format(LogRecord record) {
						if(record.getThrown() ==null)
						return String.format("%1$s %2$s \r\n",
	                         new Timestamp(record.getMillis()).toString(),
	                         record.getMessage());
						else{
							return String.format("%1$s %2$s %3$s \r\n",
		                             new Timestamp(record.getMillis()).toString(),
		                             record.getMessage(),convertStackTraceToString(record.getThrown()));
						}
					}
				};
				fileHandler.setFormatter(customFormatter);
				JUL.addHandler(fileHandler);
			} catch (SecurityException e) {
				throw new RuntimeException("Make sure you have permission to read or write to the directory");
			} catch (IOException e) {
				throw new RuntimeException("I/O Error occured");
			}
		}
		return logger;
	}

	public void log(String msg) {
		 //Replaced code with java logger
		 log(Level.INFO , msg);
	}
	
	private void log(Level logLevel,String msg){
		JUL.log(logLevel , msg);
	}
	
	private void log(Level logLevel,String msg,Throwable thrown){
		JUL.log(logLevel , msg,thrown);
	}
	
	private static String convertStackTraceToString(Throwable e){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(stream);
		e.printStackTrace(ps);
		return new String(stream.toByteArray());
	}
	
	@Deprecated
	public void log(Exception e) {
		if(e==null)
			return ;
		log(Level.SEVERE,e.getMessage(),e);
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		PrintStream ps = new PrintStream(stream);
//		e.printStackTrace(ps);
//		log(Level.SEVERE,convertStackTraceToString(e));
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
	
	public void log(Throwable e, String userMessage){
		log(Level.SEVERE,userMessage,e);
//		log(e);
	}
	
	public void log(Throwable e, String userMessage,boolean showOnScreen){
		if(showOnScreen)
			log(e, userMessage, userMessage);
		else
			log(e,userMessage);
	}
	
	public void log(Throwable e, String userMessage, String screenMessage) {
		log(e,userMessage);
		sendMessageToDisplay(screenMessage);
	}

	public void sendMessageToDisplay(String screenMessage) {
		for (DisplayLogMessageListener listener : displayMessageListeners) {
			listener.newMessage(screenMessage);
		}
	}
	
	public void setVerboseLogFlag(boolean flag){
		Level logLevel = convertLoggingLevel(flag);
		log(JUL.getLevel(),"Switching log level to : " + logLevel.toString());
		JUL.setLevel(logLevel);
	}

	//Only one display listener can be attached
	//If required a can be added 
	public synchronized void addDisplayMessageListener(
			DisplayLogMessageListener displayMessageListener) {
		if (!this.displayMessageListeners.contains(displayMessageListener))
			this.displayMessageListeners.add(displayMessageListener);
	}
	
	public synchronized void removeDisplayMessageListener(
			DisplayLogMessageListener displayMessageListener) {
		this.displayMessageListeners.remove(displayMessageListener);
	}
	
	private Level convertLoggingLevel(boolean flag){
		return flag ? Level.INFO:Level.SEVERE;
	}
}
