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

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ValidateConnection implements Runnable {

	private ValidateConnectionListener handler;
	private final static String URL="www.google.com";

	public ValidateConnection(ValidateConnectionListener handler) {
		this.handler = handler;
		Thread t;
		t = new Thread(this);
		t.start();
	}

	private void testConnection() {

		InetSocketAddress socket = new InetSocketAddress(URL, 8080);
		InetAddress address = socket.getAddress();
		if (address == null)
			handler.connectionValidationCompleted(false);
		else
			handler.connectionValidationCompleted(true);
	}

	public void run() {
		testConnection();
	}
}