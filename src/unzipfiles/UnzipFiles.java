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
package unzipfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import logging.Logging;

public class UnzipFiles {

	private static Logging logger=Logging.getLogger();
	static int nesting_on=0;
	/*
	 * This above flag is set to true when this function is called recursively.
	  This variable is introduced because when recursively called it unzip contents with file name
	  starting with "fo" or "eq" which may call convert_fo and convert_eq thus thowring exception
	  Hence a flag is applied to know that if the function is called recursively then do not execute
	  convert_fo and convert_eq as the files will not be actual bhavcopy files
	 */ 
	 
	public List<File> unzipFiles(File tempZip) throws Exception{
		List<File> extractedFiles=new ArrayList<File>();
		try{
		logger.log("Deflating zip");
		unzipFiles(tempZip, extractedFiles);
		logger.log("Defalting done");
		return extractedFiles;
		}catch(Exception e)
		{
			logger.log(e,"Failed to unzip file",true);
			throw e;
		}
	}
	
	private void unzipFiles(File tempZip,List<File> extractedFiles) throws Exception{
		int buffersize=3072;
		byte read[]=new byte[(int)buffersize];
		ZipInputStream zipRead=new ZipInputStream (new FileInputStream(tempZip));
		ZipEntry zipEntry=null;
		try{
			while((zipEntry=zipRead.getNextEntry())!=null){
				if(zipEntry.isDirectory()){
					continue;
				}
				int count=0;
				String fileName=zipEntry.getName();
				if(fileName.contains("/"))//If the path is like /PR/abc.zip
				{
					String[] splittedNames=zipEntry.getName().split("/");
					fileName=splittedNames[splittedNames.length-1];
				}
				File file=new File(System.getProperty("user.dir")+"/temp/"+fileName);
				FileOutputStream extractFile=new FileOutputStream(file);
				while((count=zipRead.read(read, 0, buffersize))!=-1){
					extractFile.write(read,0,count);
				}
				extractFile.flush();
				extractFile.close();
				extractedFiles.add(file);
				//Check if the extracted file is .zip
				if(fileName.substring(fileName.length()-3, fileName.length()).equalsIgnoreCase("zip"))
				{
					File temp=new File(System.getProperty("user.dir")+"/temp/"+fileName);
					nesting_on++; // keeps a count of the number of recurssion
					unzipFiles(temp,extractedFiles);
					nesting_on--; // decreases the count when comes out of the recursssion
				}
			}
		}
		finally{
			zipRead.close();
		}
	}
}
