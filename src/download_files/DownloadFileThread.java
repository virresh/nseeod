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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common_functions.Common_functions;

import logging.logging;
import main_gui.GUI;
import config.Config_Settings;
import convert_csv.Convert_Curr_Fut;

public class DownloadFileThread implements Runnable{

	private JTextField jTextFrom = null;
	private JTextField jTextTo = null;
	private JTextArea jTextArea = null;
	private Config_Settings settings=null;
	private JMenu menuSettings=null;
	Thread t;
	public DownloadFileThread(JTextField jTextFrom,JTextField jTextTo,JTextArea jTextArea,Config_Settings settings,JMenu menuSettings) {
		this.jTextArea=jTextArea;
		this.jTextTo=jTextTo;
		this.jTextFrom=jTextFrom;
		this.settings=settings;
		this.menuSettings=menuSettings;
		t=new Thread(this);
		t.start();
		}
	public void run() 
	{
		Boolean prSelected=false;
		Boolean prFlag=false;
		menuSettings.setEnabled(false);//Disable the settings menu before starting download
		logging.setLogFlag(settings.getjCheckBoxLogging().isSelected());//Set the logging flag 
		int attempt=1;
		//String tempSymbol="S&P CNX NIFTY,CNX NIFTY JUNIOR,BANK NIFTY,CNX 100,CNX MIDCAP,CNX IT,S&P CNX 500,S&P CNX DEFTY,NIFTY MIDCAP 50";
		String tempSymbol=generateIndexString();
		String fromDate=jTextFrom.getText();
		String toDate=jTextTo.getText();
		GregorianCalendar fromCalendar=new GregorianCalendar(Integer.parseInt(fromDate.substring(6, 10)), Integer.parseInt(fromDate.substring(3, 5))-1, Integer.parseInt(fromDate.substring(0, 2)));
		GregorianCalendar toCalendar=new GregorianCalendar(Integer.parseInt(toDate.substring(6, 10)), Integer.parseInt(toDate.substring(3, 5))-1, Integer.parseInt(toDate.substring(0, 2)));
		fromCalendar.setLenient(false);
		toCalendar.setLenient(false);
		logging.log("Starting download");
		while((fromCalendar.compareTo(toCalendar))<=0)
		{
		String day_of_month=Integer.toString((fromCalendar.get(GregorianCalendar.DAY_OF_MONTH)));
		String month=Integer.toString((fromCalendar.get(GregorianCalendar.MONTH))+1);
		String year=Integer.toString((fromCalendar.get(GregorianCalendar.YEAR)));
		toDate=(day_of_month.length()==1?"0"+day_of_month:day_of_month)+"-"//DD-
				   +(month.length()==1?"0"+month:month)+"-"//MM-
				   +year;//YYYY
		logging.log("Date : "+toDate);
		if(settings.getjCheckBoxSkipWeekend().isSelected()) //Check if the Skip Weekend Box is Checked
		{
				if(fromCalendar.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.SATURDAY )
				{
					jTextArea.append("Skipping Saturday :: "+toDate+"\n");
					logging.log("Skipping Saturday :: "+toDate);
					fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
					continue;
				}
				if(fromCalendar.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.SUNDAY)
				{
					jTextArea.append("Skipping Sunday :: "+toDate+"\n");
					logging.log("Skipping Sunday :: "+toDate);
					fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
					continue;
				}
			
		}
		String stockSymbol[]=tempSymbol.split(",");
		
		//---------------------DOWNLOAD DATA FOR BHAVCOPY-----------------------------
		if(settings.getjCheckBoxEqBhav().isSelected())
		{
		if(!equityBhavcopyDownload(toDate)) //If exception found increment the loop
		{
			fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
			continue;
		}
		}
		if(settings.getjCheckBoxFuBhav().isSelected())
		{
			futuresBhavcopyDownload(toDate);
		}
		//CREATE CONSOLIDATED BHAVCOPY
		if(settings.getjCheckBoxAppEqFu().isSelected())
		{
			//Check if either of the checkbox is selected
			if(settings.getjCheckBoxEqBhav().isSelected() ||settings.getjCheckBoxFuBhav().isSelected())
			{
				new File(System.getProperty("user.dir")+"/EOD/"+generateDate(toDate)+".txt").delete();//Delete if there is any previous file
				if(settings.getjCheckBoxEqBhav().isSelected())
				{
					try {
						copyBhavcopyFile(System.getProperty("user.dir")+"/Equity/EQ_"+generateDate(toDate)+".txt", System.getProperty("user.dir")+"/EOD/"+generateDate(toDate)+".txt");
					} catch (IOException e) {
						jTextArea.append("Cannot find Equity Bhavcopy for the day "+toDate+"\n");
						logging.log("Could not append Equity Bhavcopy- IO Exception");
						logging.log(e.getMessage());
						 for(int j=0;j<e.getStackTrace().length;j++){
							  logging.log(e.getStackTrace()[j].toString());
						 }
					}
				}
				if(settings.getjCheckBoxFuBhav().isSelected())
				{
					try {
						copyBhavcopyFile(System.getProperty("user.dir")+"/Futures/FU_"+generateDate(toDate)+".txt", System.getProperty("user.dir")+"/EOD/"+generateDate(toDate)+".txt");
					} catch (IOException e) {
						jTextArea.append("Cannot find Futures Bhavcopy for the day "+toDate+"\n");
						logging.log("Could not append Futures Bhavcopy- IO Exception");
						logging.log(e.getMessage());
						 for(int j=0;j<e.getStackTrace().length;j++){
							  logging.log(e.getStackTrace()[j].toString());
						 }
					}
				}
			}
		}
		
		//----------------------END DOWNLOAD DATA FOR BHAVCOPY--------------------------------------------------
		
		//---------------------DOWNLOAD DATA FOR INDEX--------------------------------
		//Added the try catch block which retries if posting data is failed
		//As per request no 3197092 from thenikxyz 
		{
		logging.log("<INDEXES>");
		logging.log("Starting Index download");
		int i=0;
		while(i<stockSymbol.length && (!tempSymbol.equalsIgnoreCase(""))) //if symbol exists ir there is not symbol selected
		{
			try {
				download_files.DownloadFiles.post_data_index(stockSymbol[i],toDate,toDate,jTextArea);
			} catch (IOException e) {
				if(attempt==3)
				{
					i++;
					jTextArea.append("Attemp Failed\n");
					logging.log("Attemp Failed");
					logging.log(e.getMessage());
					  for(int j=0;j<e.getStackTrace().length;j++){
						  logging.log(e.getStackTrace()[j].toString());
					 }
					attempt=1;
					continue;
				}
				else
				{
					jTextArea.append("Retrying attempt :: "+attempt+"\n");
					logging.log("Retrying attempt :: "+attempt);
					attempt++;
					continue;
				}
			}
			i++;
			attempt=1;
		}
		logging.log("Ending Index download");
		logging.log("</INDEXES>");
		}
		//---------------------END DOWNLOAD DATA FOR INDEX--------------------
		//DOWNLOAD EQUITY MARKET ACTIVITY REPORT
		if(settings.getjCheckBoxEQMAR().isSelected())
		{
			downloadEquityMAR(toDate);
		}
		//---------------------DOWNLOAD DATA FOR PR---------------------------
		//Check of other reports are checked then only intiate this download else not
		if(settings.getjCheckBoxEQNHL().isSelected() || settings.getjCheckBoxEQPBH().isSelected()||settings.getjCheckBoxEQtop10GL().isSelected()||settings.getjCheckBoxEQtop25sec().isSelected()
		|| settings.getjCheckBoxFUMAR().isSelected()||settings.getjCheckBoxFUtop10Fu().isSelected()
		|| settings.getjCheckBoxCFbhav().isSelected()||settings.getjCheckBoxCFMAR().isSelected()
		|| settings.getjCheckBoxOPbhav().isSelected()||settings.getjCheckBoxOPtop10().isSelected())
		{		
			prSelected=true;
			try {
				logging.log("<OTHER REPORTS>");
				logging.log("Starting Other reports download");
				download_files.DownloadFiles.post_data("cmprzip",toDate,jTextArea,"Other Reports");
				logging.log("Starting Zip download");
				download_files.DownloadFiles.download_zip("cmprzip", toDate, jTextArea,settings);
				logging.log("Completed Other reports download");
				prFlag=true;//Set the PR flag so that other reports are downloaded
				logging.log("</OTHER REPORTS>");
			} catch (IOException e) {
				jTextArea.append("Cannot find Other reports for the day "+toDate+"\n");
				logging.log("Cannot find Other reports for the day "+toDate);
				prFlag=false;//Set the PR flag so that other reports are not downloaded
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		//---------------------END DOWNLOAD DATA FOR PR---------------------------
		if(prSelected) //Check if the other reports are selected
		{
			if(prFlag) //Check if PR reports are downloaded then only process the checkBox
			{
			equityCheckBoxDownload(toDate);
			futureCheckBoxDownload(toDate);
			curr_futureCheckBoxDownload(toDate);
			optionsCheckBoxDownload(toDate);
			}
			else
			{
				jTextArea.append("Other reports unavailable\n");
				logging.log("PR reports not available hence not downloading other reports");
			}
		}
		
		//---------------DELETE TEMOPORARY FILES---------------------------
		File deleteFiles=new File(System.getProperty("user.dir")+"/temp");
		File []tempFiles=deleteFiles.listFiles();
		for(int i=0;i<tempFiles.length;i++)
		{
			tempFiles[i].delete();
		}
		jTextArea.append("Done :: "+toDate+"\n");
		logging.log("Done :: "+toDate);
		fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}//END OF WHILE LOOP
		jTextArea.append("Download Complete\n");
		logging.log("Download Complete");
		GUI.setThreadRunning(0);
		menuSettings.setEnabled(true);//Enable the settings menu after finishing download
		logging.log("Ending download");
	}
	void copyFile(String source,String destination) throws IOException
	{
		
		FileInputStream readSource=new FileInputStream(source);
		FileOutputStream outputDestination=new FileOutputStream(destination);
		byte read[]=new byte[1024];
		int count=0;
		while((count=readSource.read(read, 0, 1024))!=-1)
		{
			outputDestination.write(read, 0, count);
		}
		outputDestination.flush();
		outputDestination.close();
		readSource.close();		
	}
	void copyBhavcopyFile(String source,String destination) throws IOException
	{
		String readLine=null;
		BufferedReader readSource=new BufferedReader(new FileReader(new File(source)));
		BufferedWriter writeSource=new BufferedWriter(new FileWriter(new File(destination),true));
		while((readLine=readSource.readLine())!=null)
		{
			writeSource.write(readLine);
			writeSource.newLine();
		}
		writeSource.close();
		readSource.close();
	}
	void futuresBhavcopyDownload(String toDate)
	{
		logging.log("<FUTURES>");
		logging.log("Starting Futures Bhavcopy download");
		try {
			download_files.DownloadFiles.post_data("fobhav",toDate,jTextArea,"Futures Bhavcopy");
			logging.log("Starting zip download");
			download_files.DownloadFiles.download_zip("fobhav", toDate, jTextArea,settings);
			logging.log("Completed Futures Bhavcopy download");
			logging.log("</FUTURES>");
		} catch (IOException e) {
			jTextArea.append("Cannot find Futures Bhavcopy for the day "+toDate+"\n");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
			
		}
	}
	Boolean equityBhavcopyDownload(String toDate)
	{
		if(settings.getjCheckBoxEqBhav().isSelected()) //Equities Bhavcopy
		{
			logging.log("<EQUITY>");
			logging.log("Starting Equity Bhavcopy download");
			try{
				download_files.DownloadFiles.post_data("eqbhav",toDate,jTextArea,"Equities Bhavcopy");
				logging.log("Starting zip download");
				download_files.DownloadFiles.download_zip("eqbhav", toDate, jTextArea,settings);
				logging.log("Completed Equity Bhavcopy download");
				logging.log("</EQUITY>");
			}
			//Added this feature as of request number 3197092 posted by thenikxyz
			catch (IOException e) {
				jTextArea.append("Cannot find Bhavcopy for the day "+toDate+"\n");
				jTextArea.append("Skipping this Date\n");
				logging.log("Cannot find Bhavcopy for the day "+toDate);
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
				logging.log("Skipping this Date\n");
				return false;
			}
		}
		return true;
	}
	void equityCheckBoxDownload(String toDate){
		//---------------------EQUITY CHECKBOX START -------------------
		if(settings.getjCheckBoxEQNHL().isSelected())  //New High or Low
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(8, 10);
			try {
				jTextArea.append("Equity New High/Low \n");
				logging.log("Starting Equity New High/Low download");
				copyFile(System.getProperty("user.dir")+"/temp/HL"+fileDate+".csv",System.getProperty("user.dir")+"/Equity/New High Low_"+generateDate(toDate)+".csv");
				logging.log("Completed Equity New High/Low download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Equity New High/Low for the day "+toDate+"\n");
				logging.log("Cannot find Equity New High/Low for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxEQPBH().isSelected()) //Price Band Hit
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try {
				jTextArea.append("Equity Price Band Hit\n");
				logging.log("Starting Equity Price Band Hit download");
				copyFile(System.getProperty("user.dir")+"/temp/bh"+fileDate+".csv",System.getProperty("user.dir")+"/Equity/Price Band Hit_"+generateDate(toDate)+".csv");
				logging.log("Completed Equity Price Band Hit download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Equity Price Band Hit for the day "+toDate+"\n");
				logging.log("Cannot find Equity Price Band Hit for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxEQtop10GL().isSelected()) //Top 10 gainers & loosers
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(8, 10);
			try {
				jTextArea.append("Equity Top 10 gainers & loosers\n");
				logging.log("Starting Equity Top 10 gainers & loosers download");
				copyFile(System.getProperty("user.dir")+"/temp/Gl"+fileDate+".csv",System.getProperty("user.dir")+"/Equity/Top 10 Gainers & Loosers_"+generateDate(toDate)+".csv");
				logging.log("Completed Equity Top 10 gainers & loosers download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Equity Top 10 gainers & loosers for the day "+toDate+"\n");
				logging.log("Cannot find Equity Top 10 gainers & loosers for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxEQtop25sec().isSelected()) //Top 25 Trading values stocks 
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(8, 10);
			try {
				jTextArea.append("Equity Top 25 Trading values stocks\n");
				logging.log("Starting Equity Top 25 Trading values stocks download");
				copyFile(System.getProperty("user.dir")+"/temp/Tt"+fileDate+".csv",System.getProperty("user.dir")+"/Equity/Top 25 Sec by Trading Values_"+generateDate(toDate)+".csv");
				logging.log("Completed Equity Top 25 Trading values stocks download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Equity Top 25 Trading values stocks for the day "+toDate+"\n");
				logging.log("Cannot find Equity Top 25 Trading values stocks for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		//---------------------EQUITY CHECKBOX END--------------------------
	}
	void futureCheckBoxDownload(String toDate){
		//---------------------FUTURES CHECKBOX START-----------------------
		if(settings.getjCheckBoxFUMAR().isSelected()) //Market Acticity Report
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try 
			{
				jTextArea.append("Futures MAR \n");
				logging.log("Starting Futures MAR download");
				copyFile(System.getProperty("user.dir")+"/temp/fo"+fileDate+".doc",System.getProperty("user.dir")+"/Futures/F&O MAR_"+generateDate(toDate)+".doc");
				logging.log("Completed Futures MAR download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Futures MAR for the day "+toDate+"\n");
				logging.log("Cannot find Futures MAR for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxFUtop10Fu().isSelected()) // Top 10 future Contracts
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try 
			{
				jTextArea.append("Top 10 future Contracts\n");
				logging.log("Starting Top 10 future Contracts download");
				copyFile(System.getProperty("user.dir")+"/temp/ttfut"+fileDate+".csv",System.getProperty("user.dir")+"/Futures/Top 10 Future Contracts_"+generateDate(toDate)+".csv");
				logging.log("Completed Top 10 future Contracts download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Top 10 future Contracts for the day "+toDate+"\n");
				logging.log("Cannot find Top 10 future Contracts for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		//-------------------------FUTURE CHECKBOX END------------------------
	}
	void curr_futureCheckBoxDownload(String toDate){
		//-------------------------CURRENCT FUTURE CHECKBOX START-------------
		if(settings.getjCheckBoxCFMAR().isSelected()) //Currency Futures MAR
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try 
			{
				jTextArea.append("Currency Futures MAR\n");
				logging.log("Starting Currency Futures MAR download");
				copyFile(System.getProperty("user.dir")+"/temp/cd"+fileDate+".doc",System.getProperty("user.dir")+"/Currency Futures/Currency Future MAR_"+generateDate(toDate)+".doc");
				logging.log("Completed Currency Futures MAR download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Currency Futures MAR for the day "+toDate+"\n");
				logging.log("Cannot find Currency Futures MAR for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxCFbhav().isSelected()) //Currency Futures Bhavcopy
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try {
				jTextArea.append("Currency Futures Bhavcopy\n");
				logging.log("Starting Currency Futures Bhavcopy download");
				Convert_Curr_Fut.convert_curr_fu(System.getProperty("user.dir")+"/temp/cf"+fileDate+".csv");
				logging.log("Completed Currency Futures Bhavcopy download");
			} catch (Exception e) {
				jTextArea.append("Cannot find Currency Futures Bhavcopy for the day "+toDate+"\n");
				logging.log("Cannot find Currency Futures Bhavcopy for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		//-------------------------CURRENCT FUTURE CHECKBOX END---------------
	}
	void optionsCheckBoxDownload(String toDate){
		//-------------------------OPTIONS CHECKBOX START------------
		if(settings.getjCheckBoxOPbhav().isSelected())  //Options Bhavcopy
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try 
			{
				jTextArea.append("Options Bhavcopy\n");
				logging.log("Starting Options Bhavcopy download");
				copyFile(System.getProperty("user.dir")+"/temp/op"+fileDate+".csv",System.getProperty("user.dir")+"/Options/Options Bhavcopy_"+generateDate(toDate)+".csv");
				logging.log("Completed Options Bhavcopy download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Options Bhavcopy for the day "+toDate+"\n");
				logging.log("Cannot find Options Bhavcopy for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		if(settings.getjCheckBoxOPtop10().isSelected()) //Top 10 Options contracts
		{
			String fileDate=toDate.substring(0, 2)+toDate.substring(3, 5)+toDate.substring(6, 10);
			try 
			{
				jTextArea.append("Top 10 Options contracts\n");
				logging.log("Starting Top 10 Options contracts download");
				copyFile(System.getProperty("user.dir")+"/temp/ttopt"+fileDate+".csv",System.getProperty("user.dir")+"/Options/Top 10 Options Contracts_"+generateDate(toDate)+".csv");
				logging.log("Completed Top 10 Options contracts download");
			} catch (IOException e) {
				jTextArea.append("Cannot find Top 10 Options contracts for the day "+toDate+"\n");
				logging.log("Cannot find Top 10 Options contracts for the day "+toDate+"\n");
				logging.log(e.getMessage());
				  for(int i=0;i<e.getStackTrace().length;i++){
					  logging.log(e.getStackTrace()[i].toString());
				 }
			}
		}
		//---------------OPTIONS CHECKBOX END---------------------
	}
	String generateIndexString(){
		JPanel indexPanel=settings.getjPanel();
		String temp="";
		for(int i=0;i<indexPanel.getComponentCount();i++)
		{
			//Check if the component is checkbox
			if(indexPanel.getComponent(i).getClass().getSimpleName().equalsIgnoreCase("JCheckBox"))
			{
				//Check if the checkbox is selected
				if(((JCheckBox)indexPanel.getComponent(i)).isSelected())
				{
					//Check if this is the first checkbox that will be stored in temp
					if(temp.equalsIgnoreCase(""))
						temp+=((JCheckBox)indexPanel.getComponent(i)).getToolTipText();//
					else
						temp+=","+((JCheckBox)indexPanel.getComponent(i)).getToolTipText();//Add comma
				}
			}
		}
		return temp;
	}
    //DDM0NYYYY         //DD-MM-YYYY
	String generateDate(String toDate)
	{
		String date=null;
		date=toDate.substring(0, 2)+Common_functions.month_name(toDate.substring(3, 5))+toDate.substring(6, 10);
		return date;
		
	}
	void downloadEquityMAR(String toDate)//EQUITIES MAR
{
	if(settings.getjCheckBoxEQMAR().isSelected())   //EQUITIES MAR 
	{
		String fileDate=toDate.substring(0,2)+toDate.substring(3,5)+toDate.substring(6, 10);
		try{
		logging.log("Starting Equity MAR download");
		download_files.DownloadFiles.post_data("eqmkt", toDate, jTextArea, "Equity Market Activity Report");
		download_files.DownloadFiles.download_file("eqmkt", toDate, jTextArea, ".doc");
		logging.log("Completed Equity MAR download");
		}
		catch(IOException e) {
			jTextArea.append("Cannot find Equity MAR for the day "+toDate+"\n");
			logging.log("Cannot find Equity MAR for the day "+toDate);
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++){
				  logging.log(e.getStackTrace()[i].toString());
			 }
		}
		
		try {
			copyFile(System.getProperty("user.dir")+"/temp/eqmkt_"+toDate+".doc", System.getProperty("user.dir")+"/Equity/EQ_MAR_"+generateDate(toDate)+".doc");
		} catch (IOException e) {
			jTextArea.append("Fatal error processing/copying downloaded file\n");
			logging.log("Fatal error copying temporary file\n"+"Source was "+System.getProperty("user.dir")+"/temp/eqmkt_"+toDate+".doc"+"\n"+"Destination was "+System.getProperty("user.dir")+"/Equity/EQ_MAR_"+fileDate+".doc");
			logging.log(e.getMessage());
			  for(int i=0;i<e.getStackTrace().length;i++)
			  {
				  logging.log(e.getStackTrace()[i].toString());
			  }
		}
	}
}
}
