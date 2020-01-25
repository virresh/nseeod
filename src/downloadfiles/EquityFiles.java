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
package downloadfiles;

import static commonfunctions.CommonFunctions.isChkBoxSelected;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import logging.Logging;
import unzipfiles.UnzipFiles;

import commonfunctions.CommonFunctions;
import commonfunctions.FileUtil;

import config.configxml.Settings;
import config.configxml.download.DownloadPanelBase;
import convertcsv.ConvertEQ;

public class EquityFiles extends DownloadFile {
	
	private Logging logger=Logging.getLogger();
	
	public File equityBhavcopyDownload(String toDate) {
		File downloadedFile=null;
		Settings settings= Settings.getSettings();
		if (CommonFunctions.isChkBoxSelected(settings.getDownload().getEquity().getCheckboxes(),"Equity Bhavcopy"))// Equities Bhavcopy
		{
			logger.log("Starting Equity Bhavcopy download");
			try {
				logger.sendMessageToDisplay("Equities Bhavcopy");
				File outputFile=download_zip("eqbhav", toDate);
				
				if(outputFile==null)
				{
					logger.log("Cannot find Bhavcopy for the day\r\nSkipping this Date " + toDate,true);
				}
				else{
					List<File> extractedFiles=new UnzipFiles().unzipFiles(outputFile);
					outputFile.delete();
					downloadedFile=new ConvertEQ().convertToDesiredFormat(extractedFiles.get(0).getAbsolutePath());
					}
				logger.log("Completed Equity Bhavcopy download");
				
			}
			// Added this feature as of request number 3197092 posted by
			// thenikxyz
			
			catch (Exception e) {
				logger.log(e,"Error while downloading Bhavcopy for the day\r\nSkipping this Date " + toDate,true);
				downloadedFile=null;
			}
		}
		return downloadedFile;
	}
	
	public void equityCheckBoxDownload(String toDate) {
		DownloadPanelBase settings= Settings.getSettings().getDownload().getEquity();
		// ---------------------EQUITY CHECKBOX START -------------------
		if (CommonFunctions.isChkBoxSelected(settings.getCheckboxes(),"New High or Low")) // New High or Low
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(8, 10);
			try {
				logger.sendMessageToDisplay("Equity New High/Low");
				logger.log("Starting Equity New High/Low download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/HL" + fileDate
						+ ".csv", settings.getDirectory()
						+"/New High Low_" + generateDate(toDate)
						+ ".csv");
				logger.log("Completed Equity New High/Low download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Equity New High/Low for the day "
						+ toDate,true);
			}
		}
		if (CommonFunctions.isChkBoxSelected(settings.getCheckboxes(),"Price Bands Hit")) // Price Band Hit
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.sendMessageToDisplay("Equity Price Band Hit");
				logger.log("Starting Equity Price Band Hit download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/bh" + fileDate
						+ ".csv", settings.getDirectory()+"/Price Band Hit_" + generateDate(toDate)
						+ ".csv");
				logger.log("Completed Equity Price Band Hit download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Equity Price Band Hit for the day "
						+ toDate ,true);
			}
		}
			if (isChkBoxSelected(settings.getCheckboxes(),"Top 10 Gainers & Loosers")) // Top 10 gainers &
				// loosers
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(8, 10);
			try {
				logger.sendMessageToDisplay("Equity Top 10 gainers & loosers");
				logger
						.log("Starting Equity Top 10 gainers & loosers download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/Gl" + fileDate
						+ ".csv", settings.getDirectory()+"/Top 10 Gainers & Loosers_"
						+ generateDate(toDate) + ".csv");
				logger
						.log("Completed Equity Top 10 gainers & loosers download");
			} catch (IOException e) {
				logger
						.log(e,"Cannot find Equity Top 10 gainers & loosers for the day "
								+ toDate,true);
			}
		}
		if (isChkBoxSelected(settings.getCheckboxes(),"Top 25 securities by Trading Values")) // Top 25 Trading
				// values stocks
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(8, 10);
			try {
				logger.sendMessageToDisplay("Equity Top 25 Trading values stocks");
				logger
						.log("Starting Equity Top 25 Trading values stocks download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/Tt" + fileDate
						+ ".csv", settings.getDirectory()+"/Top 25 Sec by Trading Values_"
						+ generateDate(toDate) + ".csv");
				logger
						.log("Completed Equity Top 25 Trading values stocks download");
			} catch (IOException e) {
				logger
						.log(e,"Cannot find Equity Top 25 Trading values stocks for the day "
								+ toDate,true);
			}
		}
		// ---------------------EQUITY CHECKBOX END--------------------------
	}
	
	public void downloadEquityMAR(String date)// EQUITIES MAR
	{
		DownloadPanelBase settings= Settings.getSettings().getDownload().getEquity();
			if (isChkBoxSelected(settings.getCheckboxes(),"Market Activity Report")) // EQUITIES MAR
		{
			try {
				logger.log("Starting Equity MAR download");
				String outputDir=System.getProperty("user.dir")+"/temp/";		//SET THE DESTINATION OF OUTPUT FILE
				String fileNameWithExtension="eqmkt_"+date+".csv";
				HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
				String generateURL=String.format(getBaseURLs().getPrimaryLink().appendEndURL(getLinks().getEquityMARLink()),CommonFunctions.getStringToDate(date, CommonFunctions.DDMMYYYYhifenFormat));
				downloadFile(generateURL, outputDir, fileNameWithExtension, requestPropertyMap);
				logger.sendMessageToDisplay("Equity Market Activity Report");
				logger.log("Completed Equity MAR download");
				FileUtil.copyFileBinary(outputDir+"/"+fileNameWithExtension, settings.getDirectory()+"/EQ_MAR_" + generateDate(date) + ".csv");
			} catch (Exception e) {
				logger.log(e, "Fatal error processing/copying/downloading file", true);
			}
		}
	}
}
