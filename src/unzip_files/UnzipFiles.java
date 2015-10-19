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


package unzip_files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.*;

import config.Config_Settings;

public class UnzipFiles {

	
	static int nesting_on=0;
	/*
	 * This above flag is set to true when this function is called recursively.
	  This variable is introduced because when recursively called it unzip contents with file name
	  starting with "fo" or "eq" which may call convert_fo and convert_eq thus thowring exception
	  Hence a flag is applied to know that if the function is called nestedly then do not execute
	  convert_fo and convert_eq as the files will not be actual bhavcopy files
	 */ 
	 
	public static void unzip_files(File tempZip,Config_Settings settings ) throws Exception{
		
		int buffersize=3072;
		byte read[]=new byte[(int)buffersize];
		ZipInputStream zipRead=new ZipInputStream (new FileInputStream(tempZip));
		ZipEntry zipEntry=null;
		while((zipEntry=zipRead.getNextEntry())!=null){
			if(zipEntry.isDirectory()){
				continue;
			}
			int count=0;
			FileOutputStream extractFile=new FileOutputStream(System.getProperty("user.dir")+"/temp/"+zipEntry.getName());
			while((count=zipRead.read(read, 0, buffersize))!=-1){
				extractFile.write(read,0,count);
			}
			extractFile.flush();
			extractFile.close();
			//Check if the extracted file is .zip
			if(zipEntry.getName().substring(zipEntry.getName().length()-3, zipEntry.getName().length()).equalsIgnoreCase("zip"))
			{
				File temp=new File(System.getProperty("user.dir")+"/temp/"+zipEntry.getName());
				nesting_on++; // keeps a count of the number of recurssion
				unzip_files(temp,settings);
				nesting_on--; // decreases the count when comes out of the recursssion
			}
			if(tempZip.getName().substring(0, 7).equalsIgnoreCase("cmprzip"))
				continue;
			else
			{
				if(nesting_on==0)  // if the function is not in recursive mode
				{
					if(zipEntry.getName().substring(0, 2).equals("fo"))
					{
						convert_csv.Convert_FO.convert_fo(System.getProperty("user.dir")+"/temp/"+zipEntry.getName(),settings);
				    }
					if(zipEntry.getName().substring(0, 2).equals("cm"))
					{
						convert_csv.Convert_EQ.convert_eq(System.getProperty("user.dir")+"/temp/"+zipEntry.getName(),settings);
					}
				}
			}
		}
	}
}
