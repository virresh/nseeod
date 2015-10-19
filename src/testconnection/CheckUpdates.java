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
package testconnection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import downloadfiles.DownloadFile;
import dto.CheckUpdatesDTO;

public class CheckUpdates implements Runnable {
	private CheckUpdatesListener checkUpdateListener;

	public CheckUpdates(CheckUpdatesListener checkUpdateListener) {
		this.checkUpdateListener = checkUpdateListener;
		Thread t;
		t = new Thread(this);
		t.start();
	}

	public CheckUpdatesDTO checkLatestVersion() throws Exception{
		CheckUpdatesDTO checkUpdatesResult = new CheckUpdatesDTO();
		BufferedReader read = null;
		String outputDir=System
				.getProperty("user.dir")
				+ "/Temp/";
		String fileNameWithExtension = "version.txt";
		DownloadFile downloader = new DownloadFile();
		try {
			downloader.downloadFile(downloader.getLinks().getVersionLink(), outputDir, fileNameWithExtension);
			read = new BufferedReader(new InputStreamReader(new FileInputStream(outputDir+fileNameWithExtension),"UTF-8"));
			String version = read.readLine();
			if (version != null) {
				//for backward compatibility
				version = version.substring(version.indexOf("-") + 1);// store
				// the
				// substring
				// starting
				// with the
				// index
				// after the
				// "-"
				// character
				if (Float.valueOf(version)<=Float.valueOf(commonfunctions.Constants.versionNumber)) {
					read.close();
					checkUpdatesResult
							.setMessage("You are using the latest version");
					checkUpdatesResult.setUpdateAvailable(false);
				} else {
					checkUpdatesResult.setMessage("New Update Available v" + version);
					checkUpdatesResult.setUpdateAvailable(true);
				}
				checkUpdatesResult.setSuccessful(true);
			} else {
				checkUpdatesResult.setSuccessful(true);
				checkUpdatesResult.setUpdateAvailable(false);
				checkUpdatesResult
						.setMessage("Could not check for latest version");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (read != null)
				read.close();
		}
		return checkUpdatesResult;
	}

	public void run() {
		CheckUpdatesDTO check = null;
		try {
			check = checkLatestVersion();
		} catch (Exception e) {
			check = new CheckUpdatesDTO();
			check.setMessage("Cannot find updates");
			check.setSuccessful(false);
			check.setUpdateAvailable(false);
		} finally {
			checkUpdateListener.checkUpdatesCompleted(check);
		}
	}
}
