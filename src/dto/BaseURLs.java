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


public class BaseURLs {

	private BaseURL primaryLink;
	private BaseURL secondaryLink;
	
	public BaseURL getPrimaryLink(){
		return primaryLink;
	}
	public void setPrimaryLink(String primaryLink) {
		this.primaryLink = new BaseURL(primaryLink);
	}
	public BaseURL getSecondaryLink() {
		return secondaryLink;
	}
	public void setSecondaryLink(String secondaryLink) {
		this.secondaryLink =new BaseURL( secondaryLink);
	}
	
	public class BaseURL{
		private String  link;
		
		private BaseURL(String link){
			this.link = link;
		}
		
		public String appendEndURL(String endLink){
			return link.concat("/").concat(endLink).toString();
		}
	}
}
