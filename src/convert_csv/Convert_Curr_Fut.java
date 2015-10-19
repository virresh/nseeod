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
package convert_csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Convert_Curr_Fut {

	public static void convert_curr_fu(String filePath) throws Exception
	{
		String date=filePath.substring(filePath.length()-12,filePath.length()-4);
		FileReader reader=new FileReader(filePath);
		BufferedReader bufferReader=new BufferedReader(reader);
		FileWriter writer=new FileWriter(System.getProperty("user.dir")+"/Currency Futures/Curr_"+date.substring(0, 2)+common_functions.Common_functions.month_name(date.substring(2, 4))+date.substring(4, 8)+".txt");
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
		String previous_stock=null;
		int count=1;
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(0).replaceAll(" ","").equals("FUTCUR"))
			{
				String curr_stock=((String) row.get(1)).replaceAll(" ", ""); //Using replaceAll to remove additional white spaces
				if(previous_stock==null || !(previous_stock.equals(curr_stock)))
				{
					previous_stock=curr_stock;
					count=1;
				}
				curr_stock=stock_name(curr_stock, count);
				count++;
				//String line2=stock_name((String) row.get(1),row.get(2),date_calendar)+","+
				String line2=curr_stock+","+ //stock name
				database_date(date)+","+ //date YYYYMMDD
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
		//printWriter.println();
		printWriter.close();
		bufferReader.close();
	}
	public static String database_date(String date){
			date=date.substring(4, 8)+date.substring(2,4)+date.substring(0, 2);
		return date;
	}
	//New function added which will add -I,-II,-III Defect ID::3197916
	public static String stock_name(String stock,int count){
		String var="";
		for(int i=1;i<=count;i++)
		{var=var+"I";}
		//System.out.println(var);
		return (stock+"-"+var);
	}
}
