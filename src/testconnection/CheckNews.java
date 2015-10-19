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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import downloadfiles.DownloadFile;
import dto.CheckUpdatesDTO;

public class CheckNews implements Runnable {

	private CheckNewsListener checkUpdateListener;

	public CheckNews(CheckNewsListener checkUpdateListener) {
		this.checkUpdateListener = checkUpdateListener;
		Thread t;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		CheckUpdatesDTO check = null;
		try {
			check = checkLatestNews();
		} catch (Exception e) {
			check = new CheckUpdatesDTO();
			check.setMessage("Could not check for news");
			check.setSuccessful(false);
			check.setUpdateAvailable(false);
		} finally {
			checkUpdateListener.checkNewsCompleted(check);
		}
	}
	
	public CheckUpdatesDTO checkLatestNews() throws Exception{
		CheckUpdatesDTO checkNewsResult = new CheckUpdatesDTO();
		BufferedReader read = null;
		String outputDir=System
				.getProperty("user.dir")
				+ "/Temp/";
		String fileNameWithExtension = "news.txt";
		DownloadFile downloader = new DownloadFile();
		try {
			downloader.downloadFile(downloader.getLinks().getNewsLink(), outputDir, fileNameWithExtension);
			read = new BufferedReader(new InputStreamReader(new FileInputStream(outputDir+fileNameWithExtension),"UTF-8"));
			String news = read.readLine();
			if (news != null) {
				checkNewsResult.setMessage(news);
				checkNewsResult.setSuccessful(true);
			} else {
				checkNewsResult.setSuccessful(true);
				checkNewsResult
						.setMessage("No news available");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(read!=null)
				read.close();
			new File(outputDir+fileNameWithExtension).delete();
		}
		return checkNewsResult;
	}
//	public static void main(String[] args) throws SQLException {
//		DBConnection. initDataSource();
//		new CheckNews(null);
//	}
}
