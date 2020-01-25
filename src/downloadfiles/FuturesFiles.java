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
import java.util.List;

import logging.Logging;
import unzipfiles.UnzipFiles;

import commonfunctions.FileUtil;

import config.configxml.Settings;
import config.configxml.download.DownloadPanelBase;
import convertcsv.ConvertFO;

public class FuturesFiles extends DownloadFile {

	private Logging logger=Logging.getLogger();
	
	public File futuresBhavcopyDownload(String toDate) {
		File downloadedFile=null;
		
		logger.log("Starting Futures Bhavcopy download");
		try {
			logger.sendMessageToDisplay("Futures Bhavcopy");
			File outputFile=download_zip("fobhav", toDate);
			if(outputFile==null)
			{
				logger.log("Cannot find Futures Bhavcopy for the day " + toDate,true);
			}
			else{
			List<File> extractedFiles=new UnzipFiles().unzipFiles(outputFile);
			outputFile.delete();	
			downloadedFile=new ConvertFO().convertToDesiredFormat(extractedFiles.get(0).getAbsolutePath());
			}
			logger.log("Completed Futures Bhavcopy download");
		
		} catch (Exception e) {
			logger.log(e,"Error while downloading Futures Bhavcopy for the day "
					+ toDate,true);
			downloadedFile=null;
		}
		return downloadedFile;
	}
	
	public void futureCheckBoxDownload(String toDate) {
		// ---------------------FUTURES CHECKBOX START-----------------------
		DownloadPanelBase settings = Settings.getSettings().getDownload().getFutures();
		if (isChkBoxSelected(settings.getCheckboxes(),"Market Activity Report")) // Market Activity Report
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.sendMessageToDisplay("Futures MAR");
				logger.log("Starting Futures MAR download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/fo" + fileDate
						+ ".doc", settings.getDirectory()
						+"/F&O MAR_" + generateDate(toDate) + ".doc");
				logger.log("Completed Futures MAR download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Futures MAR for the day " + toDate,true);
			}
		}
			if (isChkBoxSelected(settings.getCheckboxes(),"Top 10 future contracts")) // Top 10 future
				// Contracts
		{
			String fileDate = toDate.substring(0, 2) + toDate.substring(3, 5)
					+ toDate.substring(6, 10);
			try {
				logger.sendMessageToDisplay("Top 10 future Contracts");
				logger.log("Starting Top 10 future Contracts download");
				FileUtil.copyFileBinary(System.getProperty("user.dir") + "/temp/ttfut"
						+ fileDate + ".csv", settings.getDirectory()
						+"/Top 10 Future Contracts_"
						+ generateDate(toDate) + ".csv");
				logger.log("Completed Top 10 future Contracts download");
			} catch (IOException e) {
				logger.log(e,"Cannot find Top 10 future Contracts for the day "
						+ toDate,true);
			}
		}
		// -------------------------FUTURE CHECKBOX END------------------------
	}
}
