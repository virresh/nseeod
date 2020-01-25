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
import logging.Logging;
import config.configxml.Settings;
import convertcsv.ConvertCurrFut;

public class CurrencyFuturesFiles extends DownloadFile {
	
	
	private Logging logger=Logging.getLogger();

	public  void currFutureCheckBoxDownload(String toDate){
		// -------------------------CURRENCT FUTURE CHECKBOX START-------------
		Settings settings= Settings.getSettings();
		if (isChkBoxSelected(settings.getDownload().getCurrencyfutures().getCheckboxes(),"Currency Futures Bhavcopy")) // Currency Futures
				// Bhavcopy
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.log("Starting Currency Futures Bhavcopy download","Currency Futures Bhavcopy");
				new ConvertCurrFut().convertToDesiredFormat(System.getProperty("user.dir")
						+ "/temp/cf" + fileDate + ".csv");
				logger.log("Completed Currency Futures Bhavcopy download");
			} catch (Exception e) {
				logger.log(e,"Cannot find Currency Futures Bhavcopy for the day "
								+ toDate,true);
			}
		}
		// -------------------------CURRENCT FUTURE CHECKBOX END---------------
	}
}
