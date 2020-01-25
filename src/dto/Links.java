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
package dto;

public class Links {

	private String equityBhavLink;
	private String equityMARLink;
	private String futuresBhavLink;
	private String otherReportsLink;
	private String vixBhacopyLink;
	private String indexBahvcopyLink;
	private String versionLink;
	private String newsLink;
	private String indexArchiveLink;
	
	public String getIndexArchiveLink() {
		return indexArchiveLink;
	}
	
	public void setIndexArchiveLink(String indexArchiveLink) {
		this.indexArchiveLink = indexArchiveLink;
	}
	
	public String getVersionLink() {
		return versionLink;
	}
	public void setVersionLink(String versionLink) {
		this.versionLink = versionLink;
	}
	public String getNewsLink() {
		return newsLink;
	}
	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}
	public String getIndexBahvcopyLink() {
		return indexBahvcopyLink;
	}
	public void setIndexBahvcopyLink(String indexBahvcopyLink) {
		this.indexBahvcopyLink = indexBahvcopyLink;
	}
	public String getVixBhacopyLink() {
		return vixBhacopyLink;
	}
	public void setVixBhacopyLink(String vixBhacopyLink) {
		this.vixBhacopyLink = vixBhacopyLink;
	}
	public String getEquityBhavLink() {
		return equityBhavLink;
	}
	public void setEquityBhavLink(String equityBhavLink) {
		this.equityBhavLink = equityBhavLink;
	}
	public String getEquityMARLink() {
		return equityMARLink;
	}
	public void setEquityMARLink(String equityMARLink) {
		this.equityMARLink = equityMARLink;
	}
	public String getFuturesBhavLink() {
		return futuresBhavLink;
	}
	public void setFuturesBhavLink(String futuresBhavLink) {
		this.futuresBhavLink = futuresBhavLink;
	}
	public String getOtherReportsLink() {
		return otherReportsLink;
	}
	public void setOtherReportsLink(String otherReportsLink) {
		this.otherReportsLink = otherReportsLink;
	}
}
