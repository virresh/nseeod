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

import java.io.File;

import logging.logging;
import unzipfiles.UnzipFiles;

public class OtherFiles extends DownloadFile {

	private logging logger=logging.getLogger();
	
	public boolean downloadOtherReports(String type,String date,String typeName){
		boolean success;
		try {
		logger.log("<OTHER REPORTS>");
		logger.log("Starting Other reports download");
//		downloadFiles.post_data("cmprzip", date,
//				jTextArea, "Other Reports");
		postDataOnArchive("cmprzip", date,
				 "Other Reports");
		File outputFile=download_zip("cmprzip",
				date);
		if(outputFile==null)
		{
			logger.log("Cannot find Futures Bhavcopy for the day " + date,true);
		}
		else{
			new UnzipFiles().unzip_files(outputFile);
			outputFile.delete();
		}
		logger.log("Completed Other reports download");
		logger.log("</OTHER REPORTS>");
		success=true;
		} catch (Exception e) {
			logger.log(e, "Error while downloading Other reports for the day "
					+ date, true);
			success = false;// Set the PR flag so that other reports are
							// not downloaded
		}
		return success;
	}
}
