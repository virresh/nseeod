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
package convertcsv;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import commonfunctions.Common_functions;

import config.configxml.SettingsFactory;

public class Convert_Indices {

	//fromDate=DD-MM-YYYY
	//indexSymbol=S&P CNX NIFTY
	//tempCSV=user.dir\temp\S&P CNX NIFTY_23-03-2011.csv
	public void convert_indices(String indexSymbol,File tempCSV,String fromDate) throws Exception{
		
		BufferedReader reader=new BufferedReader(new FileReader(tempCSV));
		PrintWriter writer=new PrintWriter(System.getProperty("user.dir")+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+"temp_"+fromDate+".txt");
		String line="";
		String fileName="";
		String toBeWritten="";
		int count=0;
		while((line=reader.readLine())!=null){
			count++;
			if(count==1)
				continue;
			line=line.replace("\"", ""); //replacing " with nothing
			line=line.replace(" ", ""); //replacing spaces with nothing
			//System.out.println(line);
			List <String> data=Arrays.asList(line.split(","));
			//fileName=data.get(0).replace("-", "").toUpperCase()+".txt"; //DDMONYYYY.txt
//			fileName=fromDate.substring(0, 2)+commonfunctions.Common_functions.month_name(fromDate.substring(3, 5))+fromDate.substring(6, 10)+".txt";//DDMONYYYY.txt
			fileName=Common_functions.getDateFormat(fromDate, Common_functions.DDMMYYYYhifenFormat,Common_functions.DDMMMYYYYFormat)+".txt";//DDMONYYYY.txt
			
			//System.out.println(fileName);
			//data.set(0,YYYYMMDD)
			data.set(0,fromDate.substring(6, 10)+fromDate.substring(3, 5)+fromDate.substring(0, 2));
			if(indexSymbol.equals("S&P CNX DEFTY") || indexSymbol.equals("S&P ESG INDIA INDEX"))
			{
			toBeWritten=Common_functions.convert_index_symbol(indexSymbol)+","+ //index symbol
			data.get(0)+","+//YYYYMMDD
			data.get(1)+","+//Open
			data.get(2)+","+//High
			data.get(3)+","+//Low
			data.get(4)+",0"+//Close,Volume
			",0";}//OpenInterest
			else
			{
				toBeWritten=Common_functions.convert_index_symbol(indexSymbol)+","+//index symbol
				data.get(0)+","+//YYYYMMDD
				data.get(1)+","+//Open
				data.get(2)+","+//High
				data.get(3)+","+//Low
				data.get(4)+","+//Close
				data.get(5)+",0";//Volume,OI
			}
			writer.println(toBeWritten);
		}
		reader.close();
		writer.close();
		File tempFile=new File (System.getProperty("user.dir")+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+"temp_"+fromDate+".txt");
		write(fileName,tempFile);
		tempFile.deleteOnExit();
	}
	private void write(String fileName,File tempFile) throws Exception{
		BufferedReader reader=new BufferedReader(new FileReader(tempFile));
		String tempLine="";
		String directory=SettingsFactory.getSettings().getOthers().getDirectory();
		String destinationFilePath=directory+"/"+fileName;
		File checkFile=new File(destinationFilePath);
		if (checkFile.exists()) //APPEND TO THE FILE
		{	
			FileWriter newFile=new FileWriter(destinationFilePath,true);
			PrintWriter newFileWriter=new PrintWriter(newFile);
			
			while((tempLine=reader.readLine())!=null){
			newFileWriter.println(tempLine);
			}
			newFileWriter.close();
		}
		else  //CREATE NEW FILE
		{
		//http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6213298
		//THE ABOVE LINE OF CODE DOES NOT WORK.IT IS A BUG OF JAVA CODE
		//WORKAROUND FOR THE SAME
			FileWriter newFile=new FileWriter(destinationFilePath+fileName);
			PrintWriter newFileWriter=new PrintWriter(newFile);
			while((tempLine=reader.readLine())!=null){
			newFileWriter.println(tempLine);
			}
			newFileWriter.close();
		}
	}
	
	
}
