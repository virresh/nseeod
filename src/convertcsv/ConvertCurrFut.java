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

public class ConvertCurrFut extends ConvertFile{
	public File convertToDesiredFormat(String filePath) throws Exception
	{
		String date=filePath.substring(filePath.length()-12,filePath.length()-4);
		FileReader reader=new FileReader(filePath);
		BufferedReader bufferReader=new BufferedReader(reader);
		String destinationDirectory=Settings.getSettings().getDownload().getCurrencyfutures().getDirectory();
		File convertedFile= new File(destinationDirectory+"/Curr_"+CommonFunctions.getDateFormat(date, CommonFunctions.DDMMYYYYFormat, CommonFunctions.DDMMMYYYYFormat)+".txt");
		convertedFile.getParentFile().mkdirs();
		FileWriter writer=new FileWriter(convertedFile);
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
		String previousStock=null;
		int count=1;
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(0).replaceAll(" ","").equals("FUTCUR"))
			{
				String curr_stock=((String) row.get(1)).replaceAll(" ", ""); //Using replaceAll to remove additional white spaces
				if(previousStock==null || !(previousStock.equals(curr_stock)))
				{
					previousStock=curr_stock;
					count=1;
				}
				curr_stock=addSuffixToCurrencyName(curr_stock, count);
				count++;
				String line2=curr_stock+","+ //stock name
				CommonFunctions.getDateFormat(date,CommonFunctions.DDMMYYYYFormat, CommonFunctions.YYYYMMDDformat)+","+ //date YYYYMMDD
				row.get(3).replaceAll(" ", "")+","+//Open
				row.get(4).replaceAll(" ", "")+","+//High
				row.get(5).replaceAll(" ", "")+","+//Low
				row.get(6).replaceAll(" ", "")+","+//Close
				row.get(9).replaceAll(" ", "")+","+//Volume
				row.get(7).replaceAll(" ", "");//Open Interest
				printWriter.println(line2);
			}
			else 
			{
				continue;
			}
		}
		printWriter.close();
		bufferReader.close();
		return convertedFile;
	}
	
	//New function added which will add -I,-II,-III Defect ID::3197916
	private String addSuffixToCurrencyName(String stock,int count){
		String var="";
		for(int i=1;i<=count;i++)
		{var=var+"I";}
		return (stock+"-"+var);
	}
}
