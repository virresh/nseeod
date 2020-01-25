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
package db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource40;

class DBConnection {
	
	private static DataSource ds;
	
	private static void initDataSource(){
		EmbeddedConnectionPoolDataSource40 ecps=new EmbeddedConnectionPoolDataSource40();
		ecps.setDatabaseName(System.getProperty("user.dir")+"/nsedb");
		ecps.setCreateDatabase("create");
		ecps.setUser("nseeod");
		ecps.setPassword("nseeod");
		ds=ecps;
	}
	//Avoiding hibernate as it would be an overkill
	//Although synchronization is at function level, performance drop will be negligible since
	//connections are not fetched frequently. Hence avoiding double checked locking or similar optimization
	public static synchronized Connection getConnection() throws SQLException{
		if(ds==null)
		{
			initDataSource();
//			System.out.print(ds.getConnection().isClosed() + "\n");
		}
		return ds.getConnection();
	}
}
