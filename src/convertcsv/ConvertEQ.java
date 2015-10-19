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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import config.configxml.Settings;

public class ConvertEQ  extends ConvertFile{
	
	public File convertToDesiredFormat(String filePath) throws Exception
	{
		String date=filePath.substring(filePath.length()-17, filePath.length()-8);//DDMONYYYY
		//READ FILE
		FileReader reader=new FileReader(filePath);
		BufferedReader bufferReader=new BufferedReader(reader);
		//Overwrites the file as per Request number 3208914
		String equityDir= Settings.getSettings().getDownload().getEquity().getDirectory();
		File convertedFile=new File(equityDir+System.getProperty("file.separator")+"EQ_"+date+".txt");
		convertedFile.getParentFile().mkdirs();
		FileWriter writer=new FileWriter(convertedFile);
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(1).replaceAll(" ", "").equalsIgnoreCase("EQ")||row.get(1).replaceAll(" ", "").equalsIgnoreCase("BE"))
			{
				String line2=row.get(0).replaceAll(" ", "")+","+//stock name
				convertToDate(date)+","+//stock date YYYYMMDD
				row.get(2).replaceAll(" ", "")+","+//Open
				row.get(3).replaceAll(" ", "")+","+//High
				row.get(4).replaceAll(" ", "")+","+//Low
				row.get(5).replaceAll(" ", "")+","+//Close
				row.get(8).replaceAll(" ", "")+","+//Volume
				"0";//Open Interest
				printWriter.println(line2);
			}
		}
		printWriter.close();
		writer.close();
		bufferReader.close();
		reader.close();
		return convertedFile;
	}
}
