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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import main_gui.GUI;

public class ValidateConnection implements Runnable{

	private GUI object=null;
	public ValidateConnection(GUI object) {
		// TODO Auto-generated constructor stub
		this.object=object;
		Thread t;
		t=new Thread(this);
		t.start();
	}
	public void testConnection() {
		// TODO Auto-generated method stub
		InetSocketAddress socket=new InetSocketAddress("www.google.com", 8080);
		InetAddress address=socket.getAddress();
		if(address==null){
			object.getjLabelInternetState().setText("OFF");
			object.getjLabelInternetState().setForeground(Color.RED);
			object.setConnectionOK(false);
			
		}
		else
		{object.getjLabelInternetState().setText("OK");
		object.getjLabelInternetState().setForeground(Color.green);
		object.setConnectionOK(true);
		}
	}
	public void run() {
		// TODO Auto-generated method stub
		testConnection();
		
	}
}