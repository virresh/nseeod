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

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Convert_Indices {

	//fromDate=DD-MM-YYYY
	//indexSymbol=S&P CNX NIFTY
	//tempCSV=user.dir\temp\S&P CNX NIFTY_23-03-2011.csv
	public static void convert_indices(String indexSymbol,File tempCSV,String fromDate) throws Exception{
		
		BufferedReader reader=new BufferedReader(new FileReader(tempCSV));
		PrintWriter writer=new PrintWriter(System.getProperty("user.dir")+"/temp/temp_"+fromDate+".txt");
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
			fileName=fromDate.substring(0, 2)+common_functions.Common_functions.month_name(fromDate.substring(3, 5))+fromDate.substring(6, 10)+".txt";//DDMONYYYY.txt
			//System.out.println(fileName);
			//data.set(0,YYYYMMDD)
			data.set(0,fromDate.substring(6, 10)+fromDate.substring(3, 5)+fromDate.substring(0, 2));
			if(indexSymbol.equals("S&P CNX DEFTY") || indexSymbol.equals("S&P ESG INDIA INDEX"))
			{
			toBeWritten=convert_index_symbol(indexSymbol)+","+ //index symbol
			data.get(0)+","+//YYYYMMDD
			data.get(1)+","+//Open
			data.get(2)+","+//High
			data.get(3)+","+//Low
			data.get(4)+",0"+//Close,Volume
			",0";}//OpenInterest
			else
			{
				toBeWritten=convert_index_symbol(indexSymbol)+","+//index symbol
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
		File tempFile=new File (System.getProperty("user.dir")+"/temp/temp_"+fromDate+".txt");
		write(fileName,tempFile);
		tempFile.deleteOnExit();
	}
	public static void write(String fileName,File tempFile) throws Exception{
		BufferedReader reader=new BufferedReader(new FileReader(tempFile));
		String tempLine="";
		File checkFile=new File(System.getProperty("user.dir")+"/EOD/"+fileName);
		if (checkFile.exists()) //APPEND TO THE FILE
		{	
			FileWriter newFile=new FileWriter(System.getProperty("user.dir")+"/EOD/"+fileName,true);
			PrintWriter newFileWriter=new PrintWriter(newFile);
			
			while((tempLine=reader.readLine())!=null){
			newFileWriter.println(tempLine);
			}
			newFileWriter.close();
		}
		else  //CREATE NEW FILE
		{
		//http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6213298
		//System.out.println(tempFile.renameTo(checkFile));
		//THE ABOVE LINE OF CODE DOES NOT WORK.IT IS A BUG OF JAVA CODE
		//WORKAROUND FOR THE SAME
			FileWriter newFile=new FileWriter(System.getProperty("user.dir")+"/EOD/"+fileName);
			PrintWriter newFileWriter=new PrintWriter(newFile);
			while((tempLine=reader.readLine())!=null){
			newFileWriter.println(tempLine);
			}
			newFileWriter.close();
		}
	}
	//Added the function used to process date in files Defect ID::3197979
	                                  //DD-MON-YYYY
	/*public static String database_date(String date){
		int num_month=0;
		num_month=common_functions.Common_functions.month_number(date.substring(3,6).toUpperCase());
		//System.out.println(num_month);
		//System.out.println("Passed date is ::"+date);
		//System.out.println("Length is ::"+ date.length());
		if(num_month>9)
		{
			date=date.substring(7, 11)+String.valueOf(num_month)+date.substring(0, 2);
		}
		else
			{
			date=date.substring(7, 11)+"0"+String.valueOf(num_month)+date.substring(0, 2);}
		return date;
	}*/
	
	public static String convert_index_symbol(String indexSymbol){
		
		if (indexSymbol.equals("S&P CNX NIFTY"))
			return "NIFTY";
		else if (indexSymbol.equals("CNX NIFTY JUNIOR"))
			return "JUNIOR";
		else if (indexSymbol.equals( "BANK NIFTY"))
			 return "BANKNIFTY";	 
		else if (indexSymbol.equals("CNX 100"))
			 return "NSE100";
		else if (indexSymbol.equals("CNX MIDCAP"))
			return "NSEMIDCAP";
		else if (indexSymbol.equals("CNX IT"))
			return "NSEIT";
		else if (indexSymbol.equals("S&P CNX 500"))
		    return "NSE500";
		else if (indexSymbol.equals("S&P CNX DEFTY"))
			return "NSEDEFTY";
		else if (indexSymbol.equals("NIFTY MIDCAP 50"))
			return "MIDCAP50";
		else if (indexSymbol.equals("S&P ESG INDIA INDEX"))
			return "NSEESG";
		else if (indexSymbol.equals("S&P CNX NIFTY SHARIAH"))
			return "NSESHARIAH";
		else if (indexSymbol.equals("S&P CNX 500 SHARIAH"))
			return "SHARIAH500";
		else if (indexSymbol.equals("CNX INFRA"))
			return "NSEINFRA";
		else if (indexSymbol.equals("CNX REALTY"))
			return "NSEREALTY";
		else if (indexSymbol.equals("CNX ENERGY"))
			return "NSEENERGY";
		else if (indexSymbol.equals("CNX FMCG"))
			return "NSEFMCG";
		else if (indexSymbol.equals("CNX MNC"))
			return "NSEMNC";
		else if (indexSymbol.equals("CNX PHARMA"))
			return "NSEPHARMA";
		else if (indexSymbol.equals("CNX PSE"))
			return "NSEPSE";
		else if (indexSymbol.equals("CNX PSU BANK"))
			return "NSEPSUBANK";
		else if (indexSymbol.equals("CNX SERVICE"))
			return "NSESERVICE";
		return "";
		}
}
