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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import logging.Logging;

import commonfunctions.CommonFunctions;

import db.DBExecutor;
import dto.BaseURLs;
import dto.Links;

public class DownloadFile {
	
	protected Links links;
	protected BaseURLs baseURLs;
	private Logging logger=Logging.getLogger();
	
	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public BaseURLs getBaseURLs() {
		return baseURLs;
	}

	public void setBaseURLs(BaseURLs baseURLs) {
		this.baseURLs = baseURLs;
	}

	public DownloadFile()
	{
		try {
			baseURLs= new DBExecutor().readBaseLink();
			links = new DBExecutor().readAllLinks();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load database driver",e);
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception occured",e);
		}
	}
	
	public void downloadFile(String link,String outputDir,String fileNameWithExtension) throws Exception{
		downloadFile(link, outputDir, fileNameWithExtension, prepareRequestPropertyMap());
	}
	
	public void downloadFile(String link,String outputDir,String fileNameWithExtension,HashMap<String,String> requestProperty) throws Exception{
		HttpURLConnection httpConnection=null;
		int data=-1;
		File tempFile=new File(outputDir+fileNameWithExtension);
		if(!new File(outputDir).exists())
			new File(outputDir).mkdirs();
		//Writer which will write the file to the client site
		BufferedOutputStream localFileWriter=null;
		//Reader which will read the file from the remote site
		BufferedInputStream remoteFileReader=null;
		URL url=null;
		try{
		localFileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));
		url = new URL(link);
		httpConnection = (HttpURLConnection)url.openConnection();
		//Set the request properties
		Set<String> requestPropertyKeys=requestProperty.keySet();
		Iterator<String> iteratorKeys=requestPropertyKeys.iterator();
		while(iteratorKeys.hasNext()){
			String keyValue=iteratorKeys.next();
			httpConnection.setRequestProperty(keyValue, requestProperty.get(keyValue));	
		}
		httpConnection.setDoInput(true);
		remoteFileReader=new BufferedInputStream(httpConnection.getInputStream());
		while((data=remoteFileReader.read())!=-1){
			localFileWriter.write(data);
		}
		}
		catch(IOException e){
			remoteFileReader=new BufferedInputStream(httpConnection.getErrorStream());
			if(remoteFileReader!=null)
				remoteFileReader.close();
			throw e;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			//Release resources
			if(remoteFileReader!=null)
			remoteFileReader.close();
			if(localFileWriter!=null)
			localFileWriter.close();
			if(httpConnection !=null)
				httpConnection.disconnect();
		}
	}

	protected String generateDate(String toDate) {
		String date=null;
		try {
			date= CommonFunctions.getDateFormat(toDate, CommonFunctions.DDMMYYYYhifenFormat,CommonFunctions.DDMMMYYYYFormat);
		} catch (ParseException e) {
			date="";
		}
		return date.toUpperCase();
	}

	protected HashMap<String,String>  prepareRequestPropertyMap(){
		HashMap<String,String> requestPropertyMap= new HashMap<String,String>();
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
		if (type.equals("eqbhav")){
			generateURL=String.format(baseURLs.getPrimaryLink().appendEndURL(links.getEquityBhavLink()),CommonFunctions.getStringToDate(date, CommonFunctions.DDMMYYYYhifenFormat));
		}
		else if(type.equals("fobhav")){		
			generateURL=String.format(baseURLs.getPrimaryLink().appendEndURL(links.getFuturesBhavLink()),CommonFunctions.getStringToDate(date, CommonFunctions.DDMMYYYYhifenFormat));
		}
		else if(type.equals("cmprzip")){
			generateURL=String.format(baseURLs.getPrimaryLink().appendEndURL(links.getOtherReportsLink()),CommonFunctions.getStringToDate(date, CommonFunctions.DDMMYYYYhifenFormat));
		}
		System.out.println(generateURL);
		downloadFile(generateURL, outputDir, fileNameWithExtension, requestPropertyMap);
		outputFile = new File(outputDir+fileNameWithExtension);
		logger.log("Completed zip download");
		return outputFile;
		}catch(Exception e){
			logger.log(e,"Error while downloading file");
			outputFile=null;
		}
		return outputFile;
	}
}
