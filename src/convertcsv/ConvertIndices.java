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
package convertcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import commonfunctions.CommonFunctions;
import commonfunctions.FileUtil;

import config.configxml.Settings;

public class ConvertIndices extends ConvertFile{

	private String indexSymbol;
	
	public ConvertIndices(String indexSymbol){
		this.indexSymbol=indexSymbol;
	}
	//fromDate=DD-MM-YYYY
	//indexSymbol=S&P CNX NIFTY 
	//tempCSV=user.dir\temp\S&P CNX NIFTY_23-03-2011.csv
	public File convertToDesiredFormat(String filePath) throws Exception{
		String fromDate=filePath.substring(filePath.length()-14, filePath.length()-4);
		BufferedReader reader=new BufferedReader(new FileReader(filePath));
		String tempFilePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"temp"+System.getProperty("file.separator")+"temp_"+fromDate+".txt";
		PrintWriter writer=new PrintWriter(tempFilePath);
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
			List <String> data=Arrays.asList(line.split(","));
			fileName=CommonFunctions.getDateFormat(fromDate, CommonFunctions.DDMMYYYYhifenFormat,CommonFunctions.DDMMMYYYYFormat)+".txt";//DDMONYYYY.txt
			data.set(0, CommonFunctions.getDateFormat(data.get(0), CommonFunctions.DDMMMYYYYhifenFormat, CommonFunctions.YYYYMMDDformat) );
			if(indexSymbol.equals("S&P CNX DEFTY") || indexSymbol.equals("S&P ESG INDIA INDEX") || indexSymbol.equals("VIX"))
			{
			toBeWritten=CommonFunctions.convertIndexSymbol(indexSymbol)+","+ //index symbol
			data.get(0)+","+//YYYYMMDD
			data.get(1)+","+//Open
			data.get(2)+","+//High
			data.get(3)+","+//Low
			data.get(4)+",0"+//Close,Volume
			",0";}//OpenInterest
			else
			{
				toBeWritten=CommonFunctions.convertIndexSymbol(indexSymbol)+","+//index symbol
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
		File tempFile=new File (tempFilePath);
		FileUtil.copyFileTextMode(tempFilePath, Settings.getSettings().getOthers().getDirectory()+"/"+fileName);
		tempFile.delete();
		return new File(Settings.getSettings().getOthers().getDirectory()+"/"+fileName);
	}	
}
