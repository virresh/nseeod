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
			return "NIFTY 50";
		} else if (indexName.equalsIgnoreCase("NIFTY JUNIOR"))
			return "NIFTY NEXT 50";
		else if (indexName.equalsIgnoreCase("DEFTY"))
			return "NIFTY DEFTY";
		else if (indexName.equalsIgnoreCase("CNX 500"))
			return "NIFTY 500";
		else if (indexName.equalsIgnoreCase("CNX MIDCAP"))
			return "NIFTY MIDCAP 100";
		else if (indexName.equalsIgnoreCase("CNX IT"))
			return "NIFTY IT";
		else if (indexName.equalsIgnoreCase("BANK NIFTY"))
			return "NIFTY BANK";
		else if (indexName.equalsIgnoreCase("MIDCAP 50"))
			return "NIFTY MIDCAP 50";
		else if (indexName.equalsIgnoreCase("ESG INDIA IDX"))
			return "S&P ESG INDIA INDEX";
		else if (indexName.equalsIgnoreCase("SHARIAH"))
			return "NIFTY50 SHARIAH";
		else if (indexName.equalsIgnoreCase("500 SHARIAH"))
			return "NIFTY500 SHARIAH";
		else if (indexName.equalsIgnoreCase("CNX INFRA"))
			return "NIFTY INFRA";
		else if (indexName.equalsIgnoreCase("CNX REALTY"))
			return "NIFTY REALTY";
		else if (indexName.equalsIgnoreCase("CNX ENERGY"))
			return "NIFTY ENERGY";
		else if (indexName.equalsIgnoreCase("CNX MNC"))
			return "NIFTY MNC";
		else if (indexName.equalsIgnoreCase("CNX PHARMA"))
			return "NIFTY PHARMA";
		else if (indexName.equalsIgnoreCase("CNX PSE"))
			return "NIFTY PSE";
		else if (indexName.equalsIgnoreCase("CNX PSU BANK"))
			return "NIFTY PSU BANK";
		else if (indexName.equalsIgnoreCase("CNX SERVICE"))
			return "NIFTY SERV SECTOR";
		else if (indexName.equalsIgnoreCase("CNX FMCG"))
			return "NIFTY FMCG";
		else if (indexName.equalsIgnoreCase("CNX 100"))
			return "NIFTY 100";
		else if (indexName.equalsIgnoreCase("CNX AUTO"))
			return "NIFTY AUTO";
		else if (indexName.equalsIgnoreCase("CNX FINANCE"))
			return "NIFTY FIN SERVICE";
		else if (indexName.equalsIgnoreCase("CNX METAL"))
			return "NIFTY METAL";
		else if (indexName.equalsIgnoreCase("VIX"))
			return "INDIA VIX";
		return indexName;
	}
	
	public static String getOldIndexCode(String indexName) {
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
		else if (indexName.equalsIgnoreCase("VIX"))
			return "INDIA VIX";
		return indexName;
	}
	
	public static String convertIndexSymbol(String indexSymbol){
		if (indexSymbol.equalsIgnoreCase("CNX NIFTY") || indexSymbol.equalsIgnoreCase("NIFTY 50"))
			return "NIFTY";
		else if (indexSymbol.equalsIgnoreCase("CNX NIFTY JUNIOR") || indexSymbol.equalsIgnoreCase("NIFTY NEXT 50"))
			return "JUNIOR";
		else if (indexSymbol.equalsIgnoreCase( "BANK NIFTY") || indexSymbol.equalsIgnoreCase( "NIFTY BANK"))
			return "BANKNIFTY";	 
		else if (indexSymbol.equalsIgnoreCase("CNX 100") || indexSymbol.equalsIgnoreCase("NIFTY 100"))
			return "NSE100";
		else if (indexSymbol.equalsIgnoreCase("CNX MIDCAP") || indexSymbol.equalsIgnoreCase("NIFTY MIDCAP 100"))
			return "NSEMIDCAP";
		else if (indexSymbol.equalsIgnoreCase("CNX IT") || indexSymbol.equalsIgnoreCase("NIFTY IT"))
			return "NSEIT";
		else if (indexSymbol.equalsIgnoreCase("CNX 500") || indexSymbol.equalsIgnoreCase("NIFTY 500"))
			return "NSE500";
		else if (indexSymbol.equalsIgnoreCase("CNX DEFTY"))
			return "NSEDEFTY";
		else if (indexSymbol.equalsIgnoreCase("NIFTY MIDCAP 50"))
			return "MIDCAP50";
		else if (indexSymbol.equalsIgnoreCase("S&P ESG INDIA INDEX"))
			return "NSEESG";
		else if (indexSymbol.equalsIgnoreCase("CNX NIFTY SHARIAH") || indexSymbol.equalsIgnoreCase("NIFTY50 SHARIAH"))
			return "NSESHARIAH";
		else if (indexSymbol.equalsIgnoreCase("CNX 500 SHARIAH") || indexSymbol.equalsIgnoreCase("NIFTY500 SHARIAH"))
			return "SHARIAH500";
		else if (indexSymbol.equalsIgnoreCase("CNX INFRA") || indexSymbol.equalsIgnoreCase("NIFTY INFRA"))
			return "NSEINFRA";
		else if (indexSymbol.equalsIgnoreCase("CNX REALTY") || indexSymbol.equalsIgnoreCase("NIFTY REALTY"))
			return "NSEREALTY";
		else if (indexSymbol.equalsIgnoreCase("CNX ENERGY") || indexSymbol.equalsIgnoreCase("NIFTY ENERGY"))
			return "NSEENERGY";
		else if (indexSymbol.equalsIgnoreCase("CNX FMCG") || indexSymbol.equalsIgnoreCase("NIFTY FMCG"))
			return "NSEFMCG";
		else if (indexSymbol.equalsIgnoreCase("CNX MNC") || indexSymbol.equalsIgnoreCase("NIFTY MNC"))
			return "NSEMNC";
		else if (indexSymbol.equalsIgnoreCase("CNX PHARMA") || indexSymbol.equalsIgnoreCase("NIFTY PHARMA"))
			return "NSEPHARMA";
		else if (indexSymbol.equalsIgnoreCase("CNX PSE") || indexSymbol.equalsIgnoreCase("NIFTY PSE"))
			return "NSEPSE";
		else if (indexSymbol.equalsIgnoreCase("CNX PSU BANK") || indexSymbol.equalsIgnoreCase("NIFTY PSU BANK"))
			return "NSEPSUBANK";
		else if (indexSymbol.equalsIgnoreCase("CNX SERVICE") || indexSymbol.equalsIgnoreCase("NIFTY SERV SECTOR"))
			return "NSESERVICE";
		else if (indexSymbol.equalsIgnoreCase("CNX AUTO") || indexSymbol.equalsIgnoreCase("NIFTY AUTO"))
			return "NSEAUTO";
		else if (indexSymbol.equalsIgnoreCase("CNX FINANCE") || indexSymbol.equalsIgnoreCase("NIFTY FINANCE"))
			return "NSEFINANCE";
		else if (indexSymbol.equalsIgnoreCase("CNX METAL") || indexSymbol.equalsIgnoreCase("NIFTY METAL"))
			return "NSEMETAL";
		else if (indexSymbol.equalsIgnoreCase("INDIA VIX"))
			return "VIX";
		return indexSymbol;
	}
}