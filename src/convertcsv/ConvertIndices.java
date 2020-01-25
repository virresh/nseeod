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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import commonfunctions.CommonFunctions;
import commonfunctions.FileUtil;
import config.configxml.Settings;
import dto.IndexSymbol;

public class ConvertIndices extends ConvertFile{

	private Set<String> oldStockSymbols;
	private Set<String> newStockSymbols;
	
	public ConvertIndices(List<IndexSymbol> stockSymbolList){
		oldStockSymbols = new HashSet<>();
		newStockSymbols = new HashSet<>();
		for(IndexSymbol stockSymbol:stockSymbolList){
			newStockSymbols.add(stockSymbol.getPrimaryCode());
			oldStockSymbols.add(stockSymbol.getAlternateCode());
		}
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
		String fileName=CommonFunctions.getDateFormat(fromDate, CommonFunctions.DDMMYYYYhifenFormat,CommonFunctions.DDMMMYYYYFormat)+".txt";//DDMONYYYY.txt
		String toBeWritten="";
		int count=0;
		while((line=reader.readLine())!=null){
			count++;
			if(count==1)
				continue;
			List <String> data=Arrays.asList(line.split(","));
			String nseIndexSymbol = data.get(0).toUpperCase().trim();
			if(newStockSymbols.contains(nseIndexSymbol) || oldStockSymbols.contains(nseIndexSymbol)){
				data.set(1, CommonFunctions.getDateFormat(data.get(1).trim(), CommonFunctions.DDMMYYYYhifenFormat, CommonFunctions.YYYYMMDDformat) );
				toBeWritten=CommonFunctions.convertIndexSymbol(data.get(0).trim())+","+//index symbol
				data.get(1).trim()+","+//YYYYMMDD
				(data.get(2).trim().equals("-")?"0":data.get(2).trim())+","+//Open
				(data.get(3).trim().equals("-")?"0":data.get(3).trim())+","+//High
				(data.get(4).trim().equals("-")?"0":data.get(4).trim())+","+//Low
				(data.get(5).trim().equals("-")?"0":data.get(5).trim())+","+//Close
				(data.get(8).trim().equals("-")?"0":data.get(8).trim())+",0";//Volume,OI
				writer.println(toBeWritten);
			}
		}
		reader.close();
		writer.close();
		File tempFile=new File (tempFilePath);
		FileUtil.copyFileTextMode(tempFilePath, Settings.getSettings().getOthers().getDirectory()+"/"+fileName);
		tempFile.delete();
		return new File(Settings.getSettings().getOthers().getDirectory()+"/"+fileName);
	}	
}
