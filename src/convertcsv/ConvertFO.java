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

import commonfunctions.CommonFunctions;

import config.configxml.Settings;

public class ConvertFO extends ConvertFile{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public File convertToDesiredFormat(String filePath) throws Exception {
		
		Settings settings= Settings.getSettings();
		String date=filePath.substring(filePath.length()-17, filePath.length()-8);
	    //READ FILE
		FileReader reader=new FileReader(filePath);
		FileWriter writer =null;
		BufferedReader bufferReader=new BufferedReader(reader);
		String futuresDir= Settings.getSettings().getDownload().getFutures().getDirectory();
		File convertedFile = new File(futuresDir+"/FU_"+date+".txt");
		convertedFile.getParentFile().mkdirs();
		writer=new FileWriter(convertedFile);
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
		String previousStock=null;
		int count=1;
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(0).replaceAll(" ", "").equals("FUTIDX") || row.get(0).replaceAll(" ", "").equals("FUTSTK"))
			{
				String curr_stock=(String) row.get(1).replaceAll(" ", "");
			if(previousStock==null || !(previousStock.equals(curr_stock))){
				previousStock=curr_stock;
				count=1;
				}
				curr_stock=addPrefixSuffixToCurrencyName(curr_stock, count,CommonFunctions.isChkBoxSelected(settings.getDownload().getFutures().getCheckboxes(),"Add (-I) as prefix"));
				count++;
			
			String line2=curr_stock+","+//Stock name
			convertToDate(date)+","+//Date YYYYMMDD
			row.get(5).replaceAll(" ", "")+","+//Open
			row.get(6).replaceAll(" ", "")+","+//High
			row.get(7).replaceAll(" ", "")+","+//Low
			row.get(8).replaceAll(" ", "")+","+//Close
			row.get(10).replaceAll(" ", "")+","+//Volume
			row.get(12).replaceAll(" ", "");//Open Interest
			printWriter.println(line2);
		}
		else 
		{
			continue;
		}
			
		}
		printWriter.close();
		writer.close();
		bufferReader.close();
		reader.close();
		return convertedFile;
		}
	
	
	//New function added which will add -I,-II,-III Defect ID::3197916
	private String addPrefixSuffixToCurrencyName(String stock,int count,Boolean prefix){
		String var="";
		for(int i=1;i<=count;i++)
		{
			var=var+"I";
		}
		if(prefix)
		{
			return (var+"-"+stock);
		}
		else
		{	
			return (stock+"-"+var);
		}
	}
}
