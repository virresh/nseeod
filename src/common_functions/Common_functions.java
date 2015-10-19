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


package common_functions;

public class Common_functions {

	public static int month_number(String mon){
		int num_mon=0;
		if(mon.equals("JAN"))
			num_mon=1;
		else if(mon.equals("FEB"))
			num_mon=2;
		else if(mon.equals("MAR"))
			num_mon=3;
		else if(mon.equals("APR"))
			num_mon=4;
		else if(mon.equals("MAY"))
			num_mon=5;
		else if(mon.equals("JUN"))
			num_mon=6;
		else if(mon.equals("JUL"))
			num_mon=7;
		else if(mon.equals("AUG"))
			num_mon=8;
		else if(mon.equals("SEP"))
			num_mon=9;
		else if(mon.equals("OCT"))
			num_mon=10;
		else if(mon.equals("NOV"))
			num_mon=11;
		else if(mon.equals("DEC"))
			num_mon=12;
		return num_mon;
	}
	public static String month_name(String mon){
		String num_mon=null;
		if(mon.equals("01"))
			num_mon="JAN";
		else if(mon.equals("02"))
			num_mon="FEB";
		else if(mon.equals("03"))
			num_mon="MAR";
		else if(mon.equals("04"))
			num_mon="APR";
		else if(mon.equals("05"))
			num_mon="MAY";
		else if(mon.equals("06"))
			num_mon="JUN";
		else if(mon.equals("07"))
			num_mon="JUL";
		else if(mon.equals("08"))
			num_mon="AUG";
		else if(mon.equals("09"))
			num_mon="SEP";
		else if(mon.equals("10"))
			num_mon="OCT";
		else if(mon.equals("11"))
			num_mon="NOV";
		else if(mon.equals("12"))
			num_mon="DEC";
		return num_mon;
	}
}
