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

package commonfunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JComponent;

import config.configxml.CheckBox;

public class CommonFunctions {

	public static final String DDMMYYYYhifenFormat = "dd-MM-yyyy";
	public static final String DDMMMYYYYhifenFormat = "dd-MMM-yyyy";
	public static final String YYYYMMDDhifenFormat = "yyyy-MM-dd";
	public static final String YYYYMMDDformat = "yyyyMMdd";
	public static final String DDMMYYFormat="ddMMyy";
	public static final String DDMMMYYYYFormat="ddMMMyyyy";
	public static final String DDMMYYYYFormat ="ddMMyyyy";
	public static final String MMMFormat ="MMM";
	public static final String YYYYFormat ="yyyy";

	public static Date getStringToDate(String date,String inputDateFormat) throws ParseException{
		return new SimpleDateFormat(inputDateFormat).parse(date);
	}
	public static String getDDMMYYYYDate(Date date) {
		return new SimpleDateFormat(DDMMYYYYhifenFormat).format(date);
	}

	public static String getDateFormat(Date date, String expectedFormat) {
		return new SimpleDateFormat(expectedFormat).format(date);
	}

	public static String getDateFormat(String inputDate,
			String inputDateFormat, String outputDateFormat)
					throws ParseException {
		Date date = getStringToDate(inputDate,inputDateFormat);
		return new SimpleDateFormat(outputDateFormat).format(date);
	}

	public static Date getCurrentDateTime() {
		return new GregorianCalendar().getTime();
	}

	public static JComponent getComponentByName(boolean enableDeepSearch,
			JComponent rootComponent, String name) {
		for (int i = 0; i < rootComponent.getComponentCount(); i++) {
			if (handleNull(rootComponent.getComponent(i).getName())
					.equalsIgnoreCase(name))
				return (JComponent) rootComponent.getComponent(i);
			else {
				if (getComponentByName(enableDeepSearch,
						(JComponent) rootComponent.getComponent(i), name) == null)
					continue;
				else
					return getComponentByName(enableDeepSearch,
							(JComponent) rootComponent.getComponent(i), name);
			}
		}
		return null;
	}

	public static String handleNull(Object val) {
		if (val == null)
			return "";
		return val.toString();

	}

	public static Boolean isChkBoxSelected(CheckBox[] chkboxes, String name) {
		for (int i = 0; i < chkboxes.length; i++) {
			if (chkboxes[i].getName().equalsIgnoreCase(name)) {
				return Boolean.valueOf(chkboxes[i].getValue());
			}
		}
		return false;
	}

	public static String getIndexCode(String indexName) {
		if (indexName.equalsIgnoreCase("NIFTY")) {
			return "CNX NIFTY";
		} else if (indexName.equalsIgnoreCase("NIFTY JUNIOR"))
			return "CNX NIFTY JUNIOR";
		else if (indexName.equalsIgnoreCase("DEFTY"))
			return "CNX DEFTY";
		else if (indexName.equalsIgnoreCase("CNX 500"))
			return "CNX 500";
		else if (indexName.equalsIgnoreCase("CNX MIDCAP"))
			return "CNX MIDCAP";
		else if (indexName.equalsIgnoreCase("CNX IT"))
			return "CNX IT";
		else if (indexName.equalsIgnoreCase("BANK NIFTY"))
			return "BANK NIFTY";
		else if (indexName.equalsIgnoreCase("MIDCAP 50"))
			return "NIFTY MIDCAP 50";
		else if (indexName.equalsIgnoreCase("ESG INDIA IDX"))
			return "S&P ESG INDIA INDEX";
		else if (indexName.equalsIgnoreCase("SHARIAH"))
			return "CNX NIFTY SHARIAH";
		else if (indexName.equalsIgnoreCase("500 SHARIAH"))
			return "CNX 500 SHARIAH";
		else if (indexName.equalsIgnoreCase("CNX INFRA"))
			return "CNX INFRA";
		else if (indexName.equalsIgnoreCase("CNX REALTY"))
			return "CNX REALTY";
		else if (indexName.equalsIgnoreCase("CNX ENERGY"))
			return "CNX ENERGY";
		else if (indexName.equalsIgnoreCase("CNX MNC"))
			return "CNX MNC";
		else if (indexName.equalsIgnoreCase("CNX PHARMA"))
			return "CNX PHARMA";
		else if (indexName.equalsIgnoreCase("CNX PSE"))
			return "CNX PSE";
		else if (indexName.equalsIgnoreCase("CNX PSU BANK"))
			return "CNX PSU BANK";
		else if (indexName.equalsIgnoreCase("CNX SERVICE"))
			return "CNX SERVICE";
		else if (indexName.equalsIgnoreCase("CNX FMCG"))
			return "CNX FMCG";
		else if (indexName.equalsIgnoreCase("CNX 100"))
			return "CNX 100";
		else if (indexName.equalsIgnoreCase("CNX AUTO"))
			return "CNX AUTO";
		else if (indexName.equalsIgnoreCase("CNX FINANCE"))
			return "CNX FINANCE";
		else if (indexName.equalsIgnoreCase("CNX METAL"))
			return "CNX METAL";
		return indexName;
	}

	public static String convertIndexSymbol(String indexSymbol){
		if (indexSymbol.equals("CNX NIFTY"))
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
		else if (indexSymbol.equals("CNX 500"))
			return "NSE500";
		else if (indexSymbol.equals("CNX DEFTY"))
			return "NSEDEFTY";
		else if (indexSymbol.equals("NIFTY MIDCAP 50"))
			return "MIDCAP50";
		else if (indexSymbol.equals("S&P ESG INDIA INDEX"))
			return "NSEESG";
		else if (indexSymbol.equals("CNX NIFTY SHARIAH"))
			return "NSESHARIAH";
		else if (indexSymbol.equals("CNX 500 SHARIAH"))
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
		else if (indexSymbol.equalsIgnoreCase("CNX AUTO"))
			return "NSEAUTO";
		else if (indexSymbol.equalsIgnoreCase("CNX FINANCE"))
			return "NSEFINANCE";
		else if (indexSymbol.equalsIgnoreCase("CNX METAL"))
			return "NSEMETAL";
		return indexSymbol;
	}
}