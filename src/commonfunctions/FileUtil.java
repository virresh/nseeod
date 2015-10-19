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
package commonfunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	
	public static void copyFileBinary(String source, String destination) throws IOException {

		FileInputStream readSource = new FileInputStream(source);
		FileOutputStream outputDestination = new FileOutputStream(destination);
		byte read[] = new byte[1024];
		int count = 0;
		while ((count = readSource.read(read, 0, 1024)) != -1) {
			outputDestination.write(read, 0, count);
		}
		outputDestination.flush();
		outputDestination.close();
		readSource.close();
	}
	
	public static void copyFileTextMode(String source, String destination) throws IOException {
		String readLine = null;
		BufferedReader readSource = new BufferedReader(new FileReader(new File(
				source)));
		BufferedWriter writeSource = new BufferedWriter(new FileWriter(
				new File(destination), true));
		while ((readLine = readSource.readLine()) != null) {
			writeSource.write(readLine);
			writeSource.newLine();
		}
		writeSource.close();
		readSource.close();
	}
}
