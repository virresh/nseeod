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

import logging.Logging;

import commonfunctions.FileUtil;

import config.configxml.Settings;
import config.configxml.download.DownloadPanelBase;

public class OptionsFiles extends DownloadFile {

	private Logging logger=Logging.getLogger();
	
	public void optionsCheckBoxDownload(String toDate) {
		// -------------------------OPTIONS CHECKBOX START------------
		DownloadPanelBase settings= Settings.getSettings().getDownload().getOptions();
		if (isChkBoxSelected(settings.getCheckboxes(),"Options Bhavcopy") ) // Options Bhavcopy
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			new File(settings.getDirectory()).mkdirs();
			try {
				logger.sendMessageToDisplay("Options Bhavcopy");
				logger.log("Starting Options Bhavcopy download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/op" + fileDate
						+ ".csv",settings.getDirectory()+"/Options Bhavcopy_" + generateDate(toDate)
						+ ".csv");
				logger.log("Completed Options Bhavcopy download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Options Bhavcopy for the day "
						+ toDate,true);
			}
		}
			if (isChkBoxSelected(settings.getCheckboxes(),"Top 10 Options contracts")) // Top 10 Options
				// contracts
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.sendMessageToDisplay("Top 10 Options contracts");
				logger.log("Starting Top 10 Options contracts download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/ttopt"
						+ fileDate + ".csv", settings.getDirectory()
						+ "/Top 10 Options Contracts_"
						+ generateDate(toDate) + ".csv");
				logger.log("Completed Top 10 Options contracts download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Top 10 Options contracts for the day "
						+ toDate ,true);
			}
		}
		// ---------------OPTIONS CHECKBOX END---------------------
	}
}


