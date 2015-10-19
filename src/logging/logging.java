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
package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class logging {
	private static File file=new File(System.getProperty("user.dir")+"/app.log");  //  @jve:decl-index=0:
	private static Boolean logFlag=false;
	public static void log(String msg){
		
		if(logFlag==true) //Check if logging is on
		{
			try {
				FileWriter writeLog=new FileWriter(file,true);
				writeLog.write(msg+"\n");
				writeLog.close();
				writeLog=null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void setLogFlag(Boolean flag){
		logFlag=flag;
	}
}
