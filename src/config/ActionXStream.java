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
package config;

import java.io.File;
import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import config.configxml.CheckBox;
import config.configxml.Settings;
import config.configxml.SettingsFactory;

public class ActionXStream {

	/**
	 * @param args
	 */

	public Settings readConfigFile(){
		XStream xstream= new XStream(new DomDriver());
		xstream.alias("checkbox", CheckBox.class);
		xstream.alias("settings", Settings.class);
		File file= new File(System.getProperty("user.dir")+System.getProperty("file.separator")+"config.xml");
		Settings settings=(Settings)xstream.fromXML(file);
		if(settings.getDownload().getEquity().getDirectory()==null || settings.getDownload().getEquity().getDirectory().equalsIgnoreCase(""))
		{
			settings.getDownload().getEquity().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Equity");
		}
		if(settings.getDownload().getFutures().getDirectory()==null || settings.getDownload().getFutures().getDirectory().equalsIgnoreCase(""))
		{
			settings.getDownload().getFutures().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Futures");	
		}
		if(settings.getDownload().getOptions().getDirectory()==null || settings.getDownload().getOptions().getDirectory().equalsIgnoreCase(""))
		{
			settings.getDownload().getOptions().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Options");	
		}
		if(settings.getDownload().getCurrencyfutures().getDirectory()==null || settings.getDownload().getCurrencyfutures().getDirectory().equalsIgnoreCase(""))
		{
			settings.getDownload().getCurrencyfutures().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Currency_Futures");	
		}
		if(settings.getOthers().getDirectory()==null || settings.getOthers().getDirectory().equalsIgnoreCase(""))
		{
			settings.getOthers().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Eod");	
		}			
		return settings;
	}
	
	public void writeConfigFile() throws Exception {
		Settings settings=SettingsFactory.getSettings();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("settings", Settings.class);
		xstream.alias("checkbox", CheckBox.class);
		FileWriter file = new FileWriter(System.getProperty("user.dir")
				+ "/config.xml");
		xstream.toXML(settings, file);
	}
	
	// Saves each tab data
	// Right now every thing is merged into single function since the UI is same
	// for all the panels
	// In future when the UI will not be same across all panels then functions
	// will have diff implementations..may be standalone impl classes
}
