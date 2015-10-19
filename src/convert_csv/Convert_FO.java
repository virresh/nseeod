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

import config.Config_Settings;

public class Convert_FO {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void convert_fo(String filePath,Config_Settings settings) throws Exception {
		// TODO Auto-generated method stub
		String date=filePath.substring(filePath.length()-17, filePath.length()-8);
		//System.out.println(filePath);
		//System.out.println("Parsed date is::"+date);
		//int day_of_month=Integer.parseInt(date.substring(0, 2));
		//String mon=date.substring(2, 5);
		//int year=Integer.parseInt(date.substring(5,date.length()));
		//int num_mon=common_functions.Common_functions.month_number(mon);
		//System.out.println(day_of_month+" "+num_mon+" "+year);
	    //GregorianCalendar date_calendar=new GregorianCalendar (year,num_mon-1,day_of_month);
	    //System.out.println(date_calendar.getTime());
	    //READ FILE
		FileReader reader=new FileReader(filePath);
		FileWriter writer =null;
		BufferedReader bufferReader=new BufferedReader(reader);
		writer=new FileWriter(System.getProperty("user.dir")+"/Futures/FU_"+date+".txt");
		PrintWriter printWriter=new PrintWriter(writer);
		String line=bufferReader.readLine();
		String previous_stock=null;
		int count=1;
		while((line=bufferReader.readLine())!=null)
		{
			List<String> row=Arrays.asList(line.split(","));
			if(row.get(0).replaceAll(" ", "").equals("FUTIDX") || row.get(0).replaceAll(" ", "").equals("FUTSTK"))
			{
				String curr_stock=(String) row.get(1).replaceAll(" ", "");
			if(previous_stock==null || !(previous_stock.equals(curr_stock))){
				previous_stock=curr_stock;
				count=1;
				}
				curr_stock=stock_name(curr_stock, count,settings.getjCheckBoxFuPrefix().isSelected());
				count++;
			
			//String line2=stock_name((String) row.get(1),row.get(2),date_calendar)+","+
			String line2=curr_stock+","+//Stock name
			database_date(date)+","+//Date YYYYMMDD
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
			//break;
			continue;
			
		}
			
		}
		//printWriter.println();
		printWriter.close();
		writer.close();
		bufferReader.close();
		reader.close();
		}
	
	/*Commented as a bug was detected Defect ID::3197979
	public static String database_date(String date){
		int num_month=0;
		num_month=common_functions.Common_functions.month_number(date.substring(3,6).toUpperCase());
		//System.out.println(num_month);
		System.out.println("Passed date is ::"+date);
		System.out.println("Length is ::"+ date.length());
		if(num_month>9)
		{
			date=date.substring(7, 11)+String.valueOf(num_month)+date.substring(0, 2);
		}
		else
			{
			date=date.substring(7, 11)+"0"+String.valueOf(num_month)+date.substring(0, 2);}
		return date;
	}*/
	//Added the function used to process date in files Defect ID::3197979
	public static String database_date(String date){
		int num_month=0;
		num_month=common_functions.Common_functions.month_number(date.substring(2,5).toUpperCase());
		
		if(num_month>9)
		{
			date=date.substring(5, 9)+String.valueOf(num_month)+date.substring(0, 2);
		}
		else
			date=date.substring(5, 9)+"0"+String.valueOf(num_month)+date.substring(0, 2);
		return date;
	}
	//New function added which will add -I,-II,-III Defect ID::3197916
	public static String stock_name(String stock,int count,Boolean prefix){
		String var="";
		for(int i=1;i<=count;i++)
		{
			var=var+"I";
		}
		//System.out.println(var);
		if(prefix)
		{
			return (var+"-"+stock);
		}
		else
		{	
			return (stock+"-"+var);
		}
	}
//Commented as a bug was detected Defect ID::3197916
/*	public static String stock_name(String stock,String strDate,GregorianCalendar date_calendar){
		//System.out.println(strDate.substring(3, 6).toUpperCase());
		//System.out.println(date_calendar.get(GregorianCalendar.MONTH));
		GregorianCalendar expiryDate=expiry_date(date_calendar);
		int num_month=common_functions.Common_functions.month_number(strDate.substring(3, 6).toUpperCase());
		if((num_month-(expiryDate.get(GregorianCalendar.MONTH)+1))==0){}
		
		if(expiryDate.get(GregorianCalendar.MONTH)-(date_calendar.get(GregorianCalendar.MONTH)+1)==0)
			stock=stock+"-I";
		else if(expiryDate.get(GregorianCalendar.MONTH)-(date_calendar.get(GregorianCalendar.MONTH)+1)==1)
			stock=stock+"-II";
		else if(expiryDate.get(GregorianCalendar.MONTH)-(date_calendar.get(GregorianCalendar.MONTH)+1)==2)
			stock=stock+"-III";
			
		return stock;	
	}
	
	public static GregorianCalendar expiry_date(GregorianCalendar date_calendar){
		GregorianCalendar expiryDate=date_calendar;
		//set the expiry date to the last day of the month
		expiryDate.set(GregorianCalendar.DAY_OF_MONTH, date_calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
	    //subtract the date from the expiry date till last thursday of month is encountered
	    while(true){
	    if(expiryDate.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.THURSDAY)
	    	break;
	    expiryDate.add(GregorianCalendar.DAY_OF_MONTH,-1);
	    }
	    //System.out.println("The expiry date is ::" +expiryDate.getTime());
	    //System.out.println(date_calendar.get(GregorianCalendar.MONTH));
	    return expiryDate;
	}*/
}
