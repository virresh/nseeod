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
package testconnection;

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
		try {
			readFile();
			read = new BufferedReader(new FileReader(new File(System
					.getProperty("user.dir")
					+ "/Temp/version.txt")));
			String version = read.readLine();
			if (version != null) {
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
				try {
					read.close();
				} catch (IOException e) {
					// do nothing
				}
		}
		return checkUpdatesResult;
	}

	public void readFile() throws Exception {
		int data = -1;
		String generateURL = "";

		BufferedOutputStream fileWriter = null;
		HttpURLConnection fileHttp = null;
		BufferedInputStream fileReader = null;
		try {
			// SET THE DESTINATION ZIP FILE
			// --------------------------------------------------------------
			//
			File tempFile = new File(System.getProperty("user.dir")
					+ "/Temp/version.txt");
			fileWriter = new BufferedOutputStream(
					new FileOutputStream(tempFile));
			// ---------------------------------------------------------------
			// READ THE FILE
			generateURL = "https://docs.google.com/document/d/1CJfTk3LVj_uY2-3MyCbWu3ie59E5DP4S_r5sa-b6jPI/export?format=txt&id=1CJfTk3LVj_uY2-3MyCbWu3ie59E5DP4S_r5sa-b6jPI&authkey=CPCd4a4F&tfe=yh_119";
			URL file = null;

			file = new URL(generateURL);
			fileHttp = (HttpURLConnection) file.openConnection();
			fileHttp
					.setRequestProperty(
							"user-agent",
							"User Agent: Mozilla/5.0 (compatible; Konqueror/4.1; Linux) KHTML/4.1.3 (like Gecko) SUSE");
			fileHttp.setDoInput(true);
			fileReader = new BufferedInputStream(fileHttp.getInputStream());
			while ((data = fileReader.read()) != -1) {
				fileWriter.write(data);
			}
		} catch (FileNotFoundException e) {
			throw new Exception(
					"Could not create " + System.getProperty("user.dir")
							+ "/Temp/version.txt file", e);
		} catch (MalformedURLException e) {
			throw new Exception("Version check link could not be reached", e);
		} catch (IOException e) {
			throw new Exception("Version check link could not be reached", e);
		} finally {
			if (fileWriter != null)
				fileWriter.close();
			if (fileReader != null)
				fileReader.close();
			if (fileHttp != null)
				fileHttp.disconnect();
		}
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
