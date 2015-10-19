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
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.configxml.CheckBox;
import config.configxml.Settings;
import dto.BaseURLs;
import dto.Links;

public class DBExecutor {
	
	//Need further optimization in terms of creating a cache and avoid hitting db again and again
	public BaseURLs readBaseLink() throws ClassNotFoundException, SQLException{
		Connection conn =null;
		BaseURLs baseURL = new BaseURLs();
		try{
		conn = DBConnection.getConnection();
		PreparedStatement ps= conn.prepareStatement(new Queries().readBaseLink());
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			baseURL.setPrimaryLink(rs.getString("base_url"));
		if(rs.next())
			baseURL.setSecondaryLink(rs.getString("base_url"));
		rs.close();
		ps.close();
		}
		finally{
			if(conn!=null && !conn.isClosed())
				conn.close();
		}
		return baseURL;
	}
	
	private String readRelativeLink(PreparedStatement ps, String productCode) throws SQLException{
		String link=null;
		ps.setString(1, productCode);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			link=rs.getString("product_link");
		rs.close();
		return link;
	}
	
	public Links readAllLinks() throws ClassNotFoundException, SQLException{
		Links links = new Links();
		Connection conn = null;
		try{
		conn = DBConnection.getConnection();
		PreparedStatement ps= conn.prepareStatement(new Queries().readRelativeLink());
		links.setEquityBhavLink( readRelativeLink(ps,"eqbhav"));
		links.setEquityMARLink(readRelativeLink(ps, "eqmkt"));
		links.setFuturesBhavLink(readRelativeLink(ps, "fobhav"));
		links.setOtherReportsLink(readRelativeLink(ps, "cmprzip"));
		links.setVixBhacopyLink(readRelativeLink(ps, "vixbhav"));
		links.setIndexBahvcopyLink(readRelativeLink(ps, "indexbhav"));
		links.setVersionLink(readRelativeLink(ps, "version"));
		links.setNewsLink(readRelativeLink(ps, "news"));
		ps.close();
		}
		finally{
			if(conn!=null && !conn.isClosed())
				conn.close();
		}
		
		return links;
	} 

	private void updateCheckboxSettings(CheckBox[] settings,PreparedStatement ps,String category,String subcategory) throws ClassNotFoundException, SQLException{
		for(int i=0;i<settings.length;i++)
		{	
			ps.setString(1, settings[i].getValue());
			ps.setString(2, settings[i].getName());
			ps.setString(3,category);
			ps.setString(4,subcategory);
			ps.executeUpdate();
		}
	}
	
	private void updateIndividualSetting(PreparedStatement ps,String setting_value,String setting_name,String category,String subcategory) throws SQLException
	{
		ps.setString(1, setting_value);
		ps.setString(2, setting_name);
		ps.setString(3,category);
		ps.setString(4,subcategory);
		ps.executeUpdate();
	}
	
	private String readIndividualSetting(PreparedStatement ps,String setting_name,String category,String subcategory) throws SQLException
	{
		ps.setString(1,category);
		ps.setString(2,subcategory);
		ps.setString(3, setting_name);
		ResultSet rs=ps.executeQuery();
		String result=null;
		if(rs.next())
			result=rs.getString("setting_value");	
		rs.close();
		return result;
	}
	
	private CheckBox[] readCheckboxSettings(PreparedStatement ps,String category,String subcategory) throws SQLException
	{
		List<CheckBox> checkboxes = new ArrayList<CheckBox>();
		ps.setString(1,category);
		ps.setString(2,subcategory);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{	
			CheckBox temp = new CheckBox();
			temp.setName(rs.getString("setting_name"));
			temp.setValue(rs.getString("setting_value"));
			checkboxes.add(temp);
		}
		CheckBox[] checkboxesArray= new CheckBox[checkboxes.size()];
		rs.close();
		return checkboxes.toArray(checkboxesArray);
	}
	
	public void updateAllSettings(Settings settings) throws ClassNotFoundException, SQLException{
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps= conn.prepareStatement(new Queries().updateSettings());
		CheckBox[] indexes=settings.getDownload().getIndex().getCheckboxes();
		CheckBox[] equities = settings.getDownload().getEquity().getCheckboxes();
		CheckBox[] futures=settings.getDownload().getFutures().getCheckboxes();
		CheckBox[] currencyFutures=settings.getDownload().getCurrencyfutures().getCheckboxes();
		CheckBox[] options=settings.getDownload().getOptions().getCheckboxes();
		CheckBox[] others=settings.getOthers().getCheckboxes();
		updateCheckboxSettings(indexes, ps, "download", "index");
		updateCheckboxSettings(equities, ps, "download", "equity");
		updateIndividualSetting(ps, settings.getDownload().getEquity().getDirectory(), "Equities Directory", "download", "equity");
		updateCheckboxSettings(futures, ps, "download", "futures");
		updateIndividualSetting(ps, settings.getDownload().getFutures().getDirectory(), "Futures Directory", "download", "futures");
		updateCheckboxSettings(currencyFutures, ps, "download", "currencyfutures");
		updateIndividualSetting(ps, settings.getDownload().getCurrencyfutures().getDirectory(), "Currency Futures Directory", "download", "currencyfutures");
		updateCheckboxSettings(options, ps, "download", "options");
		updateIndividualSetting(ps, settings.getDownload().getOptions().getDirectory(), "Options Directory", "download", "options");
		updateCheckboxSettings(others, ps, "others", "");
		updateIndividualSetting(ps, settings.getOthers().getDirectory(), "Consolidated Bhavcopy Directory", "others", "");
		ps.close();
		conn.close();
	}
	
	public void readAllSettings(Settings settings) throws ClassNotFoundException, SQLException{
		Connection conn =null;
		try{
		conn= DBConnection.getConnection();
		PreparedStatement ps= conn.prepareStatement(new Queries().readSettings());
		PreparedStatement individualPS=conn.prepareStatement(new Queries().readIndividualSetting());
		CheckBox[] indexes,equities, futures,currencyFutures,options,others;
		indexes=readCheckboxSettings( ps, "download", "index");
		equities=readCheckboxSettings( ps, "download", "equity");
		futures=readCheckboxSettings( ps, "download", "futures");
		currencyFutures=readCheckboxSettings( ps, "download", "currencyfutures");
		options=readCheckboxSettings( ps, "download", "options");
		others=readCheckboxSettings( ps, "others", "");
		settings.getDownload().getIndex().setCheckboxes(indexes);
		settings.getDownload().getEquity().setCheckboxes(equities);
		settings.getDownload().getEquity().setDirectory(readIndividualSetting(individualPS, "Equities Directory", "download", "equity"));
		
		settings.getDownload().getFutures().setCheckboxes(futures);
		settings.getDownload().getFutures().setDirectory(readIndividualSetting(individualPS,"Futures Directory", "download", "futures"));
		
		settings.getDownload().getCurrencyfutures().setCheckboxes(currencyFutures);
		settings.getDownload().getCurrencyfutures().setDirectory(readIndividualSetting(individualPS,"Currency Futures Directory", "download", "currencyfutures"));
		
		settings.getDownload().getOptions().setCheckboxes(options);
		settings.getDownload().getOptions().setDirectory(readIndividualSetting(individualPS, "Options Directory", "download", "options"));
		
		settings.getOthers().setCheckboxes(others);
		settings.getOthers().setDirectory(readIndividualSetting(individualPS, "Consolidated Bhavcopy Directory", "others", ""));
		ps.close();
		individualPS.close();
		}
		finally{
			if(conn!=null && !conn.isClosed())
				conn.close();
		}
	}
	
	public boolean checkForAnotherDBInstance(){
		boolean value=false;
		try{
			DBConnection.getConnection().close();
		}
		catch(SQLException e){
			if(e.getNextException().getMessage().contains("Another instance of Derby may have already booted the database"))
				value= true;
		}
		return value;
	}
	
}