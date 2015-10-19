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
package test_connection;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import main_gui.GUI;

public class CheckUpdates implements Runnable{
	private GUI gui=null;
	
	
	public CheckUpdates(GUI gui) {
		this.gui=gui;
		Thread t;
		t=new Thread(this);
		t.start();
	}
	
	public static String checkLatestVersion() throws IOException
	{
		readFile();
		BufferedReader read=new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"/Temp/version.txt")));
		String version=read.readLine();
		version=version.substring(version.indexOf("-")+1);//store the substring starting with the index after the "-" character
		//System.out.println(version);
		if(version!=null)
		{			
			if(version.equalsIgnoreCase("2.0"))
			{
				read.close();
				return "You are using the latest version";
				
			}
			else
			{
				read.close();
				return "v"+version;
			}
		}
		else
		{
			read.close();
			return "Could not check for latest version";
		}
	}
	public static void readFile()
	{
		int data=-1;
		String generateURL="";
		//SET THE DESTINATION ZIP FILE
		//--------------------------------------------------------------
		//
		File tempFile=new File(System.getProperty("user.dir")+"/Temp/version.txt");
		BufferedOutputStream fileWriter=null;
		try {
			fileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//---------------------------------------------------------------
		//READ THE FILE
		generateURL="https://docs.google.com/document/d/1CJfTk3LVj_uY2-3MyCbWu3ie59E5DP4S_r5sa-b6jPI/export?format=txt&id=1CJfTk3LVj_uY2-3MyCbWu3ie59E5DP4S_r5sa-b6jPI&authkey=CPCd4a4F&tfe=yh_119";
		URL file=null;
		HttpURLConnection fileHttp=null;
		try {
			file = new URL(generateURL);
			fileHttp = (HttpURLConnection)file.openConnection();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	} 
	public void run() {
		try{
		String result="";	
		result=checkLatestVersion();
		if(result.equalsIgnoreCase("You are using the latest version"))
		{
			gui.getjLabelUpdate().setText("You are using the latest version");
			gui.getjLabelInternetState().setForeground(Color.GREEN);
		}
		else if(result.equalsIgnoreCase("Could not check for latest version"))
		{
			gui.getjLabelUpdate().setText("Cannot find updates");
			gui.getjLabelInternetState().setForeground(Color.RED);
		}
		else 
		{
			gui.getjLabelUpdate().setText("Urgent New Version available : "+result);
			gui.getjLabelUpdate().setForeground(Color.RED);
		}
			
		}
		catch (Exception e)
		{
			gui.getjLabelUpdate().setText("Cannot find updates");
			gui.getjLabelInternetState().setForeground(Color.RED);
		}
	}
}
