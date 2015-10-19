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
package config.configxml.download;

public class Download {
	
	private DownloadPanelBase index;
	private DownloadPanelBase equity;
	private DownloadPanelBase futures;
	private DownloadPanelBase currencyfutures;
	
	public DownloadPanelBase getCurrencyfutures() {
		return currencyfutures;
	}
	public void setCurrencyfutures(DownloadPanelBase currencyfutures) {
		this.currencyfutures = currencyfutures;
	}
	private DownloadPanelBase options;
	
	public DownloadPanelBase getIndex() {
		return index;
	}
	public void setIndex(DownloadPanelBase index) {
		this.index = index;
	}
	public DownloadPanelBase getEquity() {
		return equity;
	}
	public void setEquity(DownloadPanelBase equity) {
		this.equity = equity;
	}
	public DownloadPanelBase getFutures() {
		return futures;
	}
	public void setFutures(DownloadPanelBase futures) {
		this.futures = futures;
	}
	public DownloadPanelBase getOptions() {
		return options;
	}
	public void setOptions(DownloadPanelBase options) {
		this.options = options;
	}
}
