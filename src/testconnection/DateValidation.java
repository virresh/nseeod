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
package testconnection;

import java.util.Date;
import java.util.GregorianCalendar;

import commonfunctions.CommonFunctions;

import dto.ValidationDTO;

public class DateValidation {
	
	public ValidationDTO validateDate(Date fromDate,Date toDate){
		ValidationDTO validation = new ValidationDTO();   
		validation.setSuccess(true);
		GregorianCalendar fromDateCalendar=null;
		GregorianCalendar toDateCalendar=null;
		GregorianCalendar currentDateCalendar=new GregorianCalendar();
		currentDateCalendar.setTime(CommonFunctions.getCurrentDateTime());
		fromDateCalendar=new GregorianCalendar();
		fromDateCalendar.setTime(fromDate);
		toDateCalendar=new GregorianCalendar();
		toDateCalendar.setTime(toDate);
		fromDateCalendar.setLenient(false);
		toDateCalendar.setLenient(false);
		fromDateCalendar.getTime();
		toDateCalendar.getTime();
		if((fromDateCalendar.compareTo(currentDateCalendar)==1||toDateCalendar.compareTo(currentDateCalendar)==1)){
			validation.setMessage("From/To date cannot be more than future date");
			validation.setSuccess(false);
			return validation;
		}
		if((fromDateCalendar.compareTo(toDateCalendar))==1){
			validation.setMessage("To Date cannot be less than From Date");
			validation.setSuccess(false);
			return validation;
		}
		return validation;
	}
}
