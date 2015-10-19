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
import java.net.URLEncoder;
import java.util.HashMap;

import convertcsv.Convert_Indices;

import logging.logging;

public class IndexFiles extends DownloadFile {

	private logging logger=logging.getLogger();
	//Need to properly refactor the code
	private void downloadIndexData(String symbol,String fromDate,String toDate) throws Exception{
		post_data_index(symbol, fromDate, toDate);
		download_csv(symbol, fromDate, toDate);	
	}
	
	public void downloadIndexData(String[] stockSymbol,String date){
		// Added the try catch block which retries if posting data is failed
					// As per request no 3197092 from thenikxyz
		logger.log("Starting Index download");
		int i = 0;
		int attempt = 1;
		while (stockSymbol!=null && i < stockSymbol.length)
		{
			try {
				downloadIndexData(stockSymbol[i],
						date, date);
			} catch (Exception e) {
				if (attempt == 3) {
					i++;
					logger.log(e, "Attemp Failed", true);
					attempt = 1;
					continue;
				} else {
					logger.log(e, "Retrying attempt :: " + attempt,
							true);
					attempt++;
					continue;
				}
			}
			i++;
			attempt = 1;
		}
		logger.log("Ending Index download");
	}
	
	private void post_data_index(String symbol,String fromDate,String toDate) throws Exception{
		String postData=null;
		HashMap<String,String> requestProperty=prepareRequestPropertyMap();
		String urlLink="http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp";
		postData = URLEncoder.encode("indexType", "UTF-8")+"="+URLEncoder.encode(symbol, "UTF-8")+"&"+
		URLEncoder.encode("fromDate","UTF-8") +"="+URLEncoder.encode(fromDate,"UTF-8")+"&"+
		URLEncoder.encode("toDate","UTF-8")+"="+URLEncoder.encode(toDate,"UTF-8");
		logger.log("Posting Data");
		new DownloadFile().postData(urlLink, postData, requestProperty);
		logger.sendMessageToDisplay(symbol);
		logger.log(symbol);
//		download_csv(symbol,fromDate,toDate);
	}
	
	private void download_csv(String symbol,String fromDate,String toDate)  throws Exception{
		String link=("http://www.nseindia.com/content/indices/histdata/"+symbol+fromDate+"-"+toDate+".csv").replace(" ", "%20");
		String outputDir=System.getProperty("user.dir")+"/temp/";		//SET THE DESTINATION OF OUTPUT FILE
		String fileNameWithExtension=symbol+"_"+fromDate+".csv";
		HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
		new DownloadFile().downloadFile(link, outputDir, fileNameWithExtension, requestPropertyMap);
		//CONVERT THE CSV FILE INTO TEXT FILE
		//------------------------------------------------------------
		try {
			new Convert_Indices().convert_indices(symbol,new File(outputDir+fileNameWithExtension),fromDate);
		} catch (Exception e) {
			logger.log(e,"Could not process the file"+symbol+"_"+fromDate+".csv",true);
			
		}
		//------------------------------------------------------------
	}
	
	private HashMap<String,String>  prepareRequestPropertyMap(){
		HashMap<String,String> requestPropertyMap= new HashMap<String,String>();
		requestPropertyMap.put("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		return requestPropertyMap;
	}
}
