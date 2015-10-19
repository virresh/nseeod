/*
Copyright 2011 Rohit Jhunjhunwala


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
package download_files;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

import logging.logging;

import config.Config_Settings;
import convert_csv.Convert_Indices;

public class DownloadFiles {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws Exception {
		/*String tempSymbol="S&P CNX NIFTY,CNX NIFTY JUNIOR,BANK NIFTY,CNX 100,CNX MIDCAP,CNX IT,S&P CNX 500,S&P CNX DEFTY,NIFTY MIDCAP 50";
		String fromDate="08-02-2011";
		String toDate="08-02-2011";
		String stockSymbol[]=tempSymbol.split(",");
		for(int i=0;i<stockSymbol.length;i++)
		{
			post_data_index(stockSymbol[i],fromDate,toDate);
		}
		post_data_bhavcopy("eqbhav",fromDate);
		post_data_bhavcopy("fobhav",fromDate);
		System.out.println("Successful Execution");
		
		File deleteFiles=new File(System.getProperty("user.dir")+"\\temp");
		File []tempFiles=deleteFiles.listFiles();
		for(int i=0;i<tempFiles.length;i++)
		{
			tempFiles[i].delete();
		}
		*/
	}
	public static void post_data(String type,String fromDate, JTextArea jTextArea,String typeName)throws IOException{
		URL nifty=null;
		HttpURLConnection nifty_http=null;
		String postData=null;
		DataOutputStream postDataWriter=null;
		try {
			nifty = new URL("http://www.nseindia.com/archives/archives.jsp");
			nifty_http = (HttpURLConnection)nifty.openConnection();
			postData = 
			URLEncoder.encode("date","UTF-8") +"="+URLEncoder.encode(fromDate,"UTF-8")+"&"+
			URLEncoder.encode("fileType","UTF-8")+"="+URLEncoder.encode(type,"UTF-8");
		}
		catch (MalformedURLException e) {
			//System.out.println("URL http://www.nseindia.com/archives/archives.jsp not found");
			jTextArea.append("URL http://www.nseindia.com/archives/archives.jsp not found \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		catch (UnsupportedEncodingException e) {
			//System.out.println("The coding standard is not appropiate");
			jTextArea.append("The coding standard is not appropiate\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		catch (IOException e) {
			//System.out.println("Not able to open URL");
			jTextArea.append("Not able to open URL\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		
		nifty_http.setRequestProperty("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		nifty_http.setDoInput(true);
		nifty_http.setDoOutput(true);
		nifty_http.setUseCaches (false);
		
		try {
			postDataWriter = new DataOutputStream(nifty_http.getOutputStream());
			logging.log("Posting Data");
			jTextArea.append(typeName+"\n");
			logging.log(typeName);
			postDataWriter.writeBytes(postData);
			//System.out.println(nifty_http.getResponseMessage());
			logging.log(nifty_http.getResponseMessage());
		} catch (IOException e) {
			//System.out.println("Error posting Data");
			jTextArea.append("Error posting Data \n");
			logging.log("Error posting Data");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			jTextArea.append("Thread Interuppted\n");
			logging.log("Thread Interrupted");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		//logging.log("Starting zip download");
		//download_zip(type,fromDate,jTextArea);
	}
	public static void post_data_index(String symbol,String fromDate,String toDate, JTextArea jTextArea) throws IOException{
		URL nifty=null;
		HttpURLConnection nifty_http=null;
		String postData=null;
		try {
			nifty = new URL("http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp");
			nifty_http = (HttpURLConnection)nifty.openConnection();
			postData = URLEncoder.encode("indexType", "UTF-8")+"="+URLEncoder.encode(symbol, "UTF-8")+"&"+
			URLEncoder.encode("fromDate","UTF-8") +"="+URLEncoder.encode(fromDate,"UTF-8")+"&"+
			URLEncoder.encode("toDate","UTF-8")+"="+URLEncoder.encode(toDate,"UTF-8");
		} 
		catch (MalformedURLException e) {
			
			//System.out.println("URL not found");
			jTextArea.append("URL http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp not found \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		catch (UnsupportedEncodingException e) {
			//System.out.println("The coding standard is not appropiate");
			jTextArea.append("The coding standard is not appropiate \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		catch (IOException e) {
			//System.out.println("Not able to open URL http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp");
			jTextArea.append("Not able to open URL http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		nifty_http.setRequestProperty("user-agent", "Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		nifty_http.setDoInput(true);
		nifty_http.setDoOutput(true);
		nifty_http.setUseCaches (false);
		DataOutputStream postDataWriter=null;
		try {
			postDataWriter = new DataOutputStream(nifty_http.getOutputStream());
		} catch (IOException e) {
			//System.out.println("Failed to open site to post data");
			jTextArea.append("Failed to open site to post data \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		logging.log("Posting Data");
		jTextArea.append(symbol+"\n");
		logging.log(symbol);
		try {
			postDataWriter.writeBytes(postData);
			//System.out.println(nifty_http.getResponseMessage());
			logging.log(nifty_http.getResponseMessage());
		} catch (IOException e) {
			//System.out.println("Error posting data");
			jTextArea.append("Error posting data \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		download_csv(symbol,fromDate,toDate,jTextArea);
	}
	public static void download_csv(String symbol,String fromDate,String toDate,JTextArea jTextArea)  throws IOException{
		String lineReader="";
		
		//SET THE DESTINATION CSV FILE
		//--------------------------------------------------------------
		File tempCSV=new File(System.getProperty("user.dir")+"/temp/"+symbol+"_"+fromDate+".csv");
		PrintWriter lineWriter=null;
		try {
			lineWriter = new PrintWriter(tempCSV);
		} catch (FileNotFoundException e) {
			//System.out.println("Unable to create temporary  "+symbol+"_"+fromDate+".csv");
			jTextArea.append("Unable to create temporary  "+symbol+"_"+fromDate+".csv\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		//---------------------------------------------------------------
		
		
		
		//READ THE CSV FILE
		//--------------------------------------------------------------
		URL csv=null;
		HttpURLConnection csvHttp=null;
		BufferedReader csvReader=null;
		try {
			csv = new URL(("http://www.nseindia.com/content/indices/histdata/"+symbol+fromDate+"-"+toDate+".csv").replace(" ", "%20"));
			csvHttp = (HttpURLConnection)csv.openConnection();
		} 
		catch (MalformedURLException e) {
			//System.out.println("Invalid URL");
			jTextArea.append("Invalid URL\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		catch (java.net.ConnectException e){
			//System.out.println("Connection Timeout :: File "+symbol+"_"+fromDate+".csv\n");
			jTextArea.append("Connection Timeout :: File "+symbol+"_"+fromDate+".csv\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		catch (IOException e) {
			//System.out.println("File "+symbol+"_"+fromDate+".csv not found on website");
			jTextArea.append("File "+symbol+"_"+fromDate+".csv not found on website\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		csvHttp.setRequestProperty("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		csvHttp.setDoInput(true);
		
		try {
			csvReader = new BufferedReader(new InputStreamReader(csvHttp.getInputStream()));
		
			while((lineReader=csvReader.readLine())!=null){
				lineWriter.println(lineReader);
				//System.out.println(lineReader);
			}
		} catch (IOException e) {
			jTextArea.append("File "+symbol+"_"+fromDate+".csv not found on website\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			throw new IOException();
			//e.printStackTrace();
			//return;
		}
		lineWriter.close();
		//------------------------------------------------------------
		
		
		
		//CONVERT THE CSV FILE INTO TEXT FILE
		//------------------------------------------------------------
		try {
			Convert_Indices.convert_indices(symbol, tempCSV,fromDate);
		} catch (Exception e) {
			//System.out.println("Could not process the file"+symbol+"_"+fromDate+".csv");
			jTextArea.append("Could not process the file"+symbol+"_"+fromDate+".csv\n");
			logging.log(e.getMessage());
			for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		tempCSV.deleteOnExit();
		//------------------------------------------------------------
	}
	public static void download_zip(String type,String date, JTextArea jTextArea,Config_Settings settings) throws IOException{

		int data=-1;
		String generateURL="";
		//SET THE DESTINATION ZIP FILE
		//--------------------------------------------------------------
		File tempZIP=new File(System.getProperty("user.dir")+"/temp/"+type+"_"+date+".zip");
		BufferedOutputStream zipWriter=null;
		try {
			zipWriter = new BufferedOutputStream(new FileOutputStream(tempZIP));
		} catch (FileNotFoundException e) {
			//System.out.println("Unable to create temporary "+type+"_"+date+".csv");
			jTextArea.append("Unable to create temporary "+type+"_"+date+".zip\n");
			logging.log("Unable to create temporary "+type+"_"+date+".zip");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		//---------------------------------------------------------------
		
		
		//READ THE ZIP FILE
		//--------------------------------------------------------------
		
		//STEP 1:
		if(type.equals("cmprzip"))  //check if the downloading copy is PR.zip and generate URL accordingly
			generateURL=date.substring(0,2)+date.substring(3,5)+date.substring(8, 10)+".zip";
		else
			generateURL=date.substring(0,2)+common_functions.Common_functions.month_name(date.substring(3,5))+date.substring(6, 10)+"bhav.csv.zip";
		
		//STEP 2:
		if (type.equals("eqbhav"))
		{
			generateURL="http://www.nseindia.com//content//historical//EQUITIES//"+date.substring(6, 10)+"//"+common_functions.Common_functions.month_name(date.substring(3,5))+"//"+"cm"+generateURL;
			
		}
		else if(type.equals("fobhav"))
		{
			generateURL="http://www.nseindia.com//content//historical//DERIVATIVES//"+date.substring(6, 10)+"//"+common_functions.Common_functions.month_name(date.substring(3,5))+"//"+"fo"+generateURL;
		}
		else if(type.equals("cmprzip"))
		{
			generateURL="http://www.nseindia.com/archives/equities/bhavcopy/pr/PR"+generateURL;
		}
				
		URL zip=null;
		HttpURLConnection zipHttp=null;
		try {
			zip = new URL(generateURL);
			zipHttp = (HttpURLConnection)zip.openConnection();
		} 
		catch (MalformedURLException e) {
			//System.out.println("Invalid URL "+generateURL);
			jTextArea.append("Invalid URL "+generateURL+"\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		catch (IOException e) {
			//System.out.println("File "+type+"_"+date+".zip not found on website");
			jTextArea.append("File "+type+"_"+date+".zip not found on website \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			throw new IOException();
		}
		zipHttp.setRequestProperty("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		zipHttp.setDoInput(true);
		BufferedInputStream zipReader=null;
		try {
			zipReader=new BufferedInputStream(zipHttp.getInputStream());
			while((data=zipReader.read())!=-1){
				zipWriter.write(data);
			}
			zipWriter.close();
		} catch (IOException e) {
			//System.out.println("File "+type+"_"+date+".zip not found on website");
			jTextArea.append("File "+type+"_"+date+".zip not found on website \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			throw new IOException();
		}
	try {
		logging.log("Completed zip download");
		logging.log("Defalting zip");
		unzip_files.UnzipFiles.unzip_files(tempZIP,settings);
		logging.log("Defalting done");
		} catch (Exception e) {
		//System.out.println("Failed to unzip file");
		e.printStackTrace();
		jTextArea.append("Failed to unzip file \n");
		logging.log("Failed to unzip file");
		logging.log(e.getMessage());
		  for(int i=0;i<e.getStackTrace().length;i++){
			  logging.log(e.getStackTrace()[i].toString());
		 }
		return;
	}
	}
	public static void download_file(String type,String date,JTextArea jTextArea,String extension) throws IOException{
		int data=-1;
		String generateURL="";
		//SET THE DESTINATION ZIP FILE
		//--------------------------------------------------------------
		File tempFile=new File(System.getProperty("user.dir")+"/temp/"+type+"_"+date+extension);
		BufferedOutputStream fileWriter=null;
		try {
			fileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));
		} catch (FileNotFoundException e) {
			//System.out.println("Unable to create temporary "+type+"_"+date+".csv");
			jTextArea.append("Unable to create temporary "+type+"_"+date+extension+" \n");
			logging.log("Unable to create temporary "+type+"_"+date+extension);
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		//---------------------------------------------------------------
		//READ THE ZIP FILE
		//--------------------------------------------------------------
		
		//STEP 1:
		if(type.equals("eqmkt"))  //check if the downloading copy is PR.zip and generate URL accordingly
			generateURL=date.substring(0,2)+date.substring(3,5)+date.substring(6, 10)+extension;//DDMMYYYY.DOC
				
		//STEP 2:
		generateURL="http://www.nseindia.com//archives//equities//mkt//mkt"+generateURL;
		URL file=null;
		HttpURLConnection fileHttp=null;
		try {
			file = new URL(generateURL);
			fileHttp = (HttpURLConnection)file.openConnection();
		} 
		catch (MalformedURLException e) {
			//System.out.println("Invalid URL "+generateURL);
			jTextArea.append("Invalid URL "+generateURL+"\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			return;
		}
		catch (IOException e) {
			//System.out.println("File "+type+"_"+date+".zip not found on website");
			jTextArea.append("File "+type+"_"+date+extension+" not found on website \n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			throw new IOException();
		}
		fileHttp.setRequestProperty("user-agent", "User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
		fileHttp.setDoInput(true);
		BufferedInputStream fileReader=null;
		try {
			fileReader=new BufferedInputStream(fileHttp.getInputStream());
			while((data=fileReader.read())!=-1){
				fileWriter.write(data);
			}
			fileWriter.close();
		} catch (IOException e) {
			//System.out.println("File "+type+"_"+date+".zip not found on website");
			jTextArea.append("File "+type+"_"+date+extension+" not found on website \n");
			logging.log("File "+type+"_"+date+extension+" not found on website");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			throw new IOException();
		}
	}
}
