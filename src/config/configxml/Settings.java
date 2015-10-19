/*
Copyright (c) 2011,2012,2013,2014 Rohit Jhunjhunwala

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
package config.configxml;

import config.ConfigRW;
import config.configxml.download.Download;
import config.configxml.others.Others;

public class Settings {

	private Download download;
	private Others others;
	private static Settings settings;
	
	private Settings() {
	}

	public static Settings getSettings() throws RuntimeException {
		if (settings == null) {
			settings=new Settings();
			try {
				new ConfigRW().readConfig(settings);
			} catch (Exception e) {
				throw new RuntimeException("Error while reading settings from database",e);
			}
		}
		return settings;
	}
	
	public Download getDownload() {
		return download;
	}

	public void setDownload(Download download) {
		this.download = download;
	}

	public Others getOthers() {
		return others;
	}

	public void setOthers(Others others) {
		this.others = others;
	}
	
	public void commit() throws Exception{
		new ConfigRW().writeConfig();
	}
}
