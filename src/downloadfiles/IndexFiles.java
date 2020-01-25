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

import java.io.File;
import java.util.HashMap;
import java.util.List;

import logging.Logging;

import commonfunctions.CommonFunctions;

import convertcsv.ConvertIndices;
import dto.IndexSymbol;

public class IndexFiles extends DownloadFile {

	private Logging logger=Logging.getLogger();
	
	public void downloadIndexData(List<IndexSymbol> stockSymbolList,String date){
		// Added the try catch block which retries if posting data is failed
		// As per request no 3197092 from thenikxyz
		logger.log("Starting Index download");
		IndexSymbol[] stockSymbol = new IndexSymbol[stockSymbolList.size()];
		stockSymbolList.toArray(stockSymbol);
		String path="";
		int attempt = 0;
		while (attempt<3){
			try {
				path = downloadCSV("Indices",
						date);
				break;
			}catch (Exception e) {
				logger.log(e, "Retrying attempt :: " + attempt,
						true);
				attempt++;
			}
		}
		if (attempt == 3){
			logger.log("Attemp Failed", true);
			return;
		}
		//CONVERT THE CSV FILE INTO TEXT FILE
		//------------------------------------------------------------
		try {
			new ConvertIndices(stockSymbolList).convertToDesiredFormat(path);
		} catch (Exception e) {
			logger.log(e,"Could not process the index file "+date+".csv",true);
		}
		//------------------------------------------------------------
		logger.log("Ending Index download");
	}
	
	private void pollLink(String symbol,String fromDate,String toDate) throws Exception{
		String link = String.format("%1$s?indexType=%2$s&fromDate=%3$s&toDate=%4$s",getBaseURLs().getPrimaryLink().appendEndURL(getLinks().getIndexArchiveLink()) ,symbol.replace(" ", "%20"), 
				fromDate,toDate);
		String outputDir=System.getProperty("user.dir")+"/temp/";		//SET THE DESTINATION OF OUTPUT FILE
		String fileNameWithExtension="temp_"+fromDate+".csv";
		HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
		downloadFile(link, outputDir, fileNameWithExtension, requestPropertyMap);
	}

	private String downloadCSV(String symbol,String fromDate)  throws Exception{
		logger.log(symbol, symbol);
		String link=null;
		link=String.format(getBaseURLs().getPrimaryLink().appendEndURL(getLinks().getIndexBahvcopyLink()),CommonFunctions.getStringToDate(fromDate, CommonFunctions.DDMMYYYYhifenFormat));
		String outputDir=System.getProperty("user.dir")+"/temp/";		//SET THE DESTINATION OF OUTPUT FILE
		String fileNameWithExtension=symbol+"_"+fromDate+".csv";
		HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
		downloadFile(link, outputDir, fileNameWithExtension, requestPropertyMap);
		return outputDir +File.separator+ fileNameWithExtension;
	}
}
