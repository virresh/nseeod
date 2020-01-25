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
package config;

import java.io.File;

import config.configxml.Settings;
import config.configxml.download.Download;
import config.configxml.download.DownloadPanelBase;
import config.configxml.others.Others;
import db.DBExecutor;

public class ConfigRW {

	public Settings readConfig(Settings settings) throws Exception{
		settings.setDownload(new Download());
		settings.getDownload().setIndex(new DownloadPanelBase());
		settings.getDownload().setCurrencyfutures(new DownloadPanelBase());
		settings.getDownload().setEquity(new DownloadPanelBase());
		settings.getDownload().setFutures(new DownloadPanelBase());
		settings.getDownload().setOptions(new DownloadPanelBase());
		settings.setOthers(new Others());
		new DBExecutor().readAllSettings(settings);
		if(settings.getDownload().getEquity().getDirectory()==null || settings.getDownload().getEquity().getDirectory().equalsIgnoreCase("") ||
				!(new File(settings.getDownload().getEquity().getDirectory()).exists()) )
		{
			settings.getDownload().getEquity().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Equity");
		}
		if(settings.getDownload().getFutures().getDirectory()==null || settings.getDownload().getFutures().getDirectory().equalsIgnoreCase("") || 
				!(new File(settings.getDownload().getFutures().getDirectory()).exists()))
		{
			settings.getDownload().getFutures().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Futures");	
		}
		if(settings.getDownload().getOptions().getDirectory()==null || settings.getDownload().getOptions().getDirectory().equalsIgnoreCase("") ||
				!(new File(settings.getDownload().getOptions().getDirectory()).exists()))
		{
			settings.getDownload().getOptions().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Options");	
		}
		if(settings.getDownload().getCurrencyfutures().getDirectory()==null || settings.getDownload().getCurrencyfutures().getDirectory().equalsIgnoreCase("") ||
				!(new File(settings.getDownload().getCurrencyfutures().getDirectory()).exists()))
		{
			settings.getDownload().getCurrencyfutures().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Currency_Futures");	
		}
		if(settings.getOthers().getDirectory()==null || settings.getOthers().getDirectory().equalsIgnoreCase("") || 
				!(new File(settings.getOthers().getDirectory()).exists()))
		{
			settings.getOthers().setDirectory(System.getProperty("user.dir")+System.getProperty("file.separator")+"Eod");	
		}			
		return settings;
	}
	
	public void writeConfig() throws Exception {
		Settings settings=Settings.getSettings();
		new DBExecutor().updateAllSettings(settings);
	}
}
