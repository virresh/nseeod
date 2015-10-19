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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import logging.logging;

import commonfunctions.Common_functions;

public class DownloadFile {
	
	private logging logger=logging.getLogger();
	
	public void downloadFile(String link,String outputDir,String fileNameWithExtension,HashMap<String,String> requestProperty) throws Exception{
		int data=-1;
		File tempFile=new File(outputDir+fileNameWithExtension);
		if(!new File(outputDir).exists())
			tempFile.mkdir();
		//Writer which will write the file to the client site
		BufferedOutputStream localFileWriter=null;
		//Reader which will read the file from the remote site
		BufferedInputStream remoteFileReader=null;
		URL url=null;
		HttpURLConnection httpConnection=null;
		try{
		localFileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));
		url = new URL(link);
		httpConnection = (HttpURLConnection)url.openConnection();
		//Set the request propeties
		Set<String> requestPropertyKeys=requestProperty.keySet();
		Iterator<String> iteratorKeys=requestPropertyKeys.iterator();
		while(iteratorKeys.hasNext())
			{
			String keyValue=iteratorKeys.next();
			httpConnection.setRequestProperty(keyValue, requestProperty.get(keyValue));	
			}
		httpConnection.setDoInput(true);
		remoteFileReader=new BufferedInputStream(httpConnection.getInputStream());
		while((data=remoteFileReader.read())!=-1){
			localFileWriter.write(data);
		}
		}
		catch(Exception e)
		{
			throw e;
		}
		finally{
			//Release resources
			remoteFileReader.close();
			httpConnection.disconnect();
			localFileWriter.close();
		}
	}
	
	public void postData(String urlLink,String postData,HashMap<String,String> requestProperty) throws Exception{
		URL nifty=null;
		HttpURLConnection httpConnection=null;
		DataOutputStream postDataWriter=null;
		try{
		nifty = new URL(urlLink);
		httpConnection = (HttpURLConnection)nifty.openConnection();
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		httpConnection.setUseCaches (false);
		postDataWriter = new DataOutputStream(httpConnection.getOutputStream());
		postDataWriter.writeBytes(postData);
		httpConnection.getResponseMessage();//Wait for the response message
		}
		catch(Exception e)
		{
		throw e;	
		}
		finally{
			//Release resources
			postDataWriter.close();
			httpConnection.disconnect();
		}
	}
	
	protected String generateDate(String toDate) {
//		String date = null;
//		date = toDate.substring(0, 2)
//				+ Common_functions.month_name(toDate.substring(3, 5))
//				+ toDate.substring(6, 10);
//		
		String date=null;
		try {
//			date= Common_functions.getDateFormat(toDate, "dd-MM-yyyy", "ddMMMyyyy");
			date= Common_functions.getDateFormat(toDate, Common_functions.DDMMYYYYhifenFormat,Common_functions.DDMMMYYYYFormat);
		} catch (ParseException e) {
			date="";
		}
		return date.toUpperCase();
	}
	
	protected void postDataOnArchive(String type,String fromDate,String typeName) throws Exception{
		String postData=null;
		HashMap<String,String> requestProperty=prepareRequestPropertyMap();
		String urlLink="http://www.nseindia.com/archives/archives.jsp";
		postData = 
		URLEncoder.encode("date","UTF-8") +"="+URLEncoder.encode(fromDate,"UTF-8")+"&"+
		URLEncoder.encode("fileType","UTF-8")+"="+URLEncoder.encode(type,"UTF-8");
		logger.log("Posting Data");
		postData(urlLink, postData,requestProperty);
		logger.sendMessageToDisplay(typeName);
		logger.log(typeName);
	}
	
	private HashMap<String,String>  prepareRequestPropertyMap(){
		HashMap<String,String> requestPropertyMap= new HashMap<String,String>();
//		requestPropertyMap.put("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		requestPropertyMap.put("user-agent", "User Agent: Java Client");
		return requestPropertyMap;
	}
	
	protected File download_zip(String type,String date){
		File outputFile=null;
		try{
		logger.log("Starting zip download");
		String generateURL="";
		String outputDir=System.getProperty("user.dir")+"/temp/";		
		String fileNameWithExtension=type+"_"+date+".zip";
		HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
		//generate URL link
		if(type.equals("cmprzip"))  //check if the downloading copy is PR.zip and generate URL accordingly
			{
//			generateURL=date.substring(0,2)+date.substring(3,5)+date.substring(8, 10)+".zip";
			generateURL=Common_functions.getDateFormat(date, Common_functions.DDMMYYYYhifenFormat, Common_functions.DDMMYYFormat)+".zip";
			}
		else
			{
//			generateURL=date.substring(0,2)+commonfunctions.Common_functions.month_name(date.substring(3,5))+date.substring(6, 10)+"bhav.csv.zip";
			generateURL=Common_functions.getDateFormat(date, Common_functions.DDMMYYYYhifenFormat, Common_functions.DDMMMYYYYFormat).toUpperCase()+"bhav.csv.zip";
			}
		if (type.equals("eqbhav"))
			generateURL="http://www.nseindia.com/content/historical/EQUITIES/"+Common_functions.getDateFormat(date,Common_functions.DDMMYYYYhifenFormat,Common_functions.YYYYFormat)+"/"+Common_functions.getDateFormat(date,Common_functions.DDMMYYYYhifenFormat,Common_functions.MMMFormat).toUpperCase()+"/"+"cm"+generateURL;
		else if(type.equals("fobhav"))
			generateURL="http://www.nseindia.com/content/historical/DERIVATIVES/"+Common_functions.getDateFormat(date,Common_functions.DDMMYYYYhifenFormat,Common_functions.YYYYFormat)+"/"+Common_functions.getDateFormat(date,Common_functions.DDMMYYYYhifenFormat,Common_functions.MMMFormat).toUpperCase()+"/"+"fo"+generateURL;
		else if(type.equals("cmprzip"))
			generateURL="http://www.nseindia.com/archives/equities/bhavcopy/pr/PR"+generateURL;
		downloadFile(generateURL, outputDir, fileNameWithExtension, requestPropertyMap);
		outputFile = new File(outputDir+fileNameWithExtension);
		logger.log("Completed zip download");
		return outputFile;
		}catch(Exception e)
		{
			logger.log(e);
			outputFile=null;
		}
		return outputFile;
//	try {
//		logger.log("Completed zip download");
//		logger.log("Deflating zip");
//		UnzipFiles.unzip_files(outputFile);
//		logger.log("Defalting done");
//		} catch (Exception e) {
//		logger.log(e,"Failed to unzip file",true);
//	}
//		finally{
//			outputFile.delete();
//		}	
	}
	
	protected File download_file(String type,String date,String extension) throws Exception{
		String generateURL="";
		String outputDir=System.getProperty("user.dir")+"/temp/";		//SET THE DESTINATION OF OUTPUT FILE
		String fileNameWithExtension=type+"_"+date+extension;
		HashMap<String,String> requestPropertyMap= prepareRequestPropertyMap();
		//STEP 1:
		if(type.equals("eqmkt"))  //check if the downloading copy is equity market activity report and generate URL accordingly
			generateURL=Common_functions.getDateFormat(date, Common_functions.DDMMYYYYhifenFormat, Common_functions.DDMMYYFormat)+extension;//DDMMYY.CSV
		//STEP 2:
		generateURL="http://www.nseindia.com/archives/equities/mkt/MA"+generateURL;
		downloadFile(generateURL, outputDir, fileNameWithExtension,requestPropertyMap);
		return new File(outputDir+"/"+fileNameWithExtension);
	}
}
