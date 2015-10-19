/*
Copyright (c) 2011,2012,2013 Rohit Jhunjhunwala

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

import static commonfunctions.Common_functions.isChkBoxSelected;
import logging.logging;
import config.configxml.Settings;
import config.configxml.SettingsFactory;
import convertcsv.Convert_Curr_Fut;

public class CurrencyFuturesFiles extends DownloadFile {
	
	
	private logging logger=logging.getLogger();

	public  void curr_futureCheckBoxDownload(String toDate) {
		// -------------------------CURRENCT FUTURE CHECKBOX START-------------
		Settings settings= SettingsFactory.getSettings();
		//Discontinued this product from NSE
//		if (settings.getjCheckBoxCFMAR().isSelected()) // Currency Futures MAR
//		if(Common_functions.isChkBoxSelected(settings.getDownload().getCurrencyfutures().getCheckboxes(),"Market Activity Report"))
//		{
//			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
//					+ toDate.substring(6, 10);
//			try {
//				logger.sendMessageToDisplay("Currency Futures MAR");
//				logger.log("Starting Currency Futures MAR download");
////				copyFile(System.getProperty("user.dir") + "/temp/cd" + fileDate
////						+ ".doc", settings.getCurrFuturesfileChooser().getSelectedDirectory()
////						+"/Currency Future MAR_"
////						+ generateDate(toDate) + ".doc");
//				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/cd" + fileDate
//						+ ".doc", settings.getDownload().getCurrencyfutures().getDirectory()
//						+"/Currency Future MAR_"
//						+ generateDate(toDate) + ".doc");
//				logger.log("Completed Currency Futures MAR download");
//			} catch (IOException e) {
//				logger.log("Cannot find Currency Futures MAR for the day "
//						+ toDate,true);
//				logger.log(e.getMessage());
//			}
//		}
//		if (settings.getjCheckBoxCFbhav().isSelected()) // Currency Futures
														// Bhavcopy
			if (isChkBoxSelected(settings.getDownload().getCurrencyfutures().getCheckboxes(),"Currency Futures Bhavcopy")) // Currency Futures
				// Bhavcopy
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.log("Starting Currency Futures Bhavcopy download","Currency Futures Bhavcopy");
				Convert_Curr_Fut.convert_curr_fu(System.getProperty("user.dir")
						+ "/temp/cf" + fileDate + ".csv",settings.getDownload().getCurrencyfutures().getDirectory());
				logger.log("Completed Currency Futures Bhavcopy download");
			} catch (Exception e) {
				logger.log("Cannot find Currency Futures Bhavcopy for the day "
								+ toDate,true);
				logger.log(e);
			}
		}
		// -------------------------CURRENCT FUTURE CHECKBOX END---------------
	}
}
