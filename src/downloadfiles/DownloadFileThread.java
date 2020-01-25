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
package downloadfiles;

import static commonfunctions.CommonFunctions.isChkBoxSelected;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import logging.Logging;
import commonfunctions.CommonFunctions;
import commonfunctions.FileUtil;
import config.configxml.CheckBox;
import config.configxml.Settings;
import config.configxml.download.DownloadPanelBase;
import config.configxml.others.Others;
import dto.IndexSymbol;

public class DownloadFileThread implements Runnable {

	private Logging logger = Logging.getLogger();
	private String jTextFrom = null;
	private String jTextTo = null;
	private DownloadFilesListener downloadFilesListener;
	private Thread t;
	private static final String LOGGING="Enable Verbose Logging";
	private static final String SKIPWEEKENDS="Skip Weekends";
	private static final String EQUITY_BHAVCOPY="Equity Bhavcopy";
	private static final String FUTURES_BHAVCOPY="Futures Bhavcopy";
	private static final String MAR="Market Activity Report";
	private static final String CONSOLIDATED_BHAVCOPY="Create consolidated Bhavcopy";
	private static final String NEW_HIGH_LOW="New High or Low";
	private static final String PRICE_BAND_HIT="Price Bands Hit";
	private static final String TOP_10_GAINERS_LOOSERS="Top 10 Gainers & Loosers";
	private static final String TOP_25_SECURITIES="Top 25 securities by Trading Values";
	private static final String TOP_10_FUTURES="Top 10 future contracts";
	private static final String CURRENCY_FUTURES_BHAVCOPY="Currency Futures Bhavcopy";
	private static final String OPTIONS_BHAVCOPY="Options Bhavcopy";
	private static final String TOP_10_OPTIONS="Top 10 Options contracts";
	private static final String OTHER_REPORTS="Other Reports";

	public DownloadFileThread(String jTextFrom, String jTextTo,
			DownloadFilesListener downloadFilesListener) {
		this.downloadFilesListener = downloadFilesListener;
		this.jTextTo = jTextTo;
		this.jTextFrom = jTextFrom;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			startDownload();
		} catch (RuntimeException e) {
			downloadFilesListener.downloadComplete();
			logger.log(e,"An unknown error occured while downloading data", "Error occured while downloading");
		}
	}

	public void startDownload() {
		Others otherBase = Settings.getSettings().getOthers();
		DownloadPanelBase equityBase = Settings.getSettings()
				.getDownload().getEquity();
		DownloadPanelBase futuresBase = Settings.getSettings()
				.getDownload().getFutures();
		DownloadPanelBase currFuturesBase = Settings.getSettings()
				.getDownload().getCurrencyfutures();
		DownloadPanelBase optionsBase = Settings.getSettings()
				.getDownload().getOptions();
		Boolean prSelected = false;
		Boolean prFlag = false;
		// Set the verbose logging flag
		logger.setVerboseLogFlag(isChkBoxSelected(otherBase.getCheckboxes(),
				LOGGING));
		String fromDate = jTextFrom;
		String toDate = jTextTo;
		GregorianCalendar fromCalendar = new GregorianCalendar();
		GregorianCalendar toCalendar = new GregorianCalendar();
		try {
			fromCalendar.setTime(CommonFunctions.getStringToDate(fromDate,
					CommonFunctions.DDMMYYYYhifenFormat));
			toCalendar.setTime(CommonFunctions.getStringToDate(toDate,
					CommonFunctions.DDMMYYYYhifenFormat));
		} catch (Exception e) {
			logger.sendMessageToDisplay("Enter date in correct format");
			throw new RuntimeException();
		}
		fromCalendar.setLenient(false);
		toCalendar.setLenient(false);
		logger.log("Starting download");
		EquityFiles equityFiles = new EquityFiles();
		FuturesFiles futureFiles = new FuturesFiles();
		while ((fromCalendar.compareTo(toCalendar)) <= 0) {
			toDate = CommonFunctions.getDateFormat(fromCalendar.getTime(),
					CommonFunctions.DDMMYYYYhifenFormat);
			logger.log("Date : " + toDate);
			if (isChkBoxSelected(otherBase.getCheckboxes(), SKIPWEEKENDS))
			// Check if the Skip Weekend Box is Checked
			{
				if (fromCalendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
					logger.log("Skipping Saturday :: " + toDate, true);
					fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
					continue;
				}
				if (fromCalendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
					logger.log("Skipping Sunday :: " + toDate, true);
					fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
					continue;
				}
			}
			File eqBhavcopy=null,fuBhavcopy=null;
			// ---------------------DOWNLOAD DATA FOR BHAVCOPY-----------------------------
			if (isChkBoxSelected(equityBase.getCheckboxes(), EQUITY_BHAVCOPY)) {
				eqBhavcopy=equityFiles.equityBhavcopyDownload(toDate);
				if (eqBhavcopy==null) // If exception found increment the loop
				{
					fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
					continue;
				}
			}
			if (isChkBoxSelected(futuresBase.getCheckboxes(),
					FUTURES_BHAVCOPY)) {
				fuBhavcopy=futureFiles.futuresBhavcopyDownload(toDate);
			}
			// CREATE CONSOLIDATED BHAVCOPY
			if (isChkBoxSelected(otherBase.getCheckboxes(),CONSOLIDATED_BHAVCOPY)) {
				// Check if either of the checkbox is selected
				if (isChkBoxSelected(equityBase.getCheckboxes(),
						EQUITY_BHAVCOPY)
						|| isChkBoxSelected(futuresBase.getCheckboxes(),
								FUTURES_BHAVCOPY)) {
					
					File consolidatedBhavcopy=new File(otherBase.getDirectory()+"/" + generateDate(toDate)
							+ ".txt");
					consolidatedBhavcopy.mkdirs();
					if(consolidatedBhavcopy.exists())
						consolidatedBhavcopy.delete();// Delete if there is any previous file
					if (isChkBoxSelected(equityBase.getCheckboxes(),
							EQUITY_BHAVCOPY)) {
						try {
							FileUtil.copyFileTextMode(eqBhavcopy.getAbsolutePath(),
									consolidatedBhavcopy.getAbsolutePath());
						} catch (IOException e) {
							logger.log(e,
									"Could not append Equity Bhavcopy- IO Exception",
									"Cannot find Equity Bhavcopy for the day "
											+ toDate);
						}
					}
					if (isChkBoxSelected(futuresBase.getCheckboxes(),
							FUTURES_BHAVCOPY)) {
						try {
							FileUtil.copyFileTextMode(
									fuBhavcopy.getAbsolutePath(),
									consolidatedBhavcopy.getAbsolutePath());
						} catch (IOException e) {
							logger.log(
									e,
									"Could not append Futures Bhavcopy- IO Exception",
									"Cannot find Futures Bhavcopy for the day "
											+ toDate);
						}
					}
				}
			}

			// ----------------------END DOWNLOAD DATA FOR BHAVCOPY--------------------------------------------------
			// ---------------------DOWNLOAD DATA FOR INDEX--------------------------------
			List<IndexSymbol> tempSymbol = generateIndexString();
			new IndexFiles().downloadIndexData(tempSymbol, toDate);
			// ---------------------END DOWNLOAD DATA FOR INDEX--------------------
			// DOWNLOAD EQUITY MARKET ACTIVITY REPORT
			if (isChkBoxSelected(equityBase.getCheckboxes(),
					MAR)) {
				equityFiles.downloadEquityMAR(toDate);
			}
			// ---------------------DOWNLOAD DATA FOR PR---------------------------
			// Check of other reports are checked then only initiate this download else not
			if (isChkBoxSelected(equityBase.getCheckboxes(),NEW_HIGH_LOW)
					|| isChkBoxSelected(equityBase.getCheckboxes(),
							PRICE_BAND_HIT)
					|| isChkBoxSelected(equityBase.getCheckboxes(),
							TOP_10_GAINERS_LOOSERS)
					|| isChkBoxSelected(equityBase.getCheckboxes(),
							TOP_25_SECURITIES)
					|| isChkBoxSelected(futuresBase.getCheckboxes(),
							MAR)
					|| isChkBoxSelected(futuresBase.getCheckboxes(),
							TOP_10_FUTURES)
					|| isChkBoxSelected(currFuturesBase.getCheckboxes(),
							CURRENCY_FUTURES_BHAVCOPY)
					|| isChkBoxSelected(currFuturesBase.getCheckboxes(),
							MAR)
					|| isChkBoxSelected(optionsBase.getCheckboxes(),
							OPTIONS_BHAVCOPY)
					|| isChkBoxSelected(optionsBase.getCheckboxes(),
							TOP_10_OPTIONS)) {
				prSelected = true;
					OtherFiles otherFiles = new OtherFiles();
					prFlag=otherFiles.downloadOtherReports("cmprzip", toDate,
							OTHER_REPORTS);// Set the PR flag so that other reports are
					// downloaded
			}
			// ---------------------END DOWNLOAD DATA FOR PR---------------------------
			if (prSelected) // Check if the other reports are selected
			{
				if (prFlag) // Check if PR reports are downloaded then only
							// process the checkBox
				{
					equityFiles.equityCheckBoxDownload(toDate);
					futureFiles.futureCheckBoxDownload(toDate);
					CurrencyFuturesFiles currencyFuturesFiles = new CurrencyFuturesFiles();
					currencyFuturesFiles.currFutureCheckBoxDownload(toDate);
					OptionsFiles optionsFiles = new OptionsFiles();
					optionsFiles.optionsCheckBoxDownload(toDate);
				} else {
					logger.log(
							"PR reports not available hence not downloading other reports",
							"Other reports unavailable");
				}
			}

			// ---------------DELETE TEMOPORARY FILES---------------------------
			File deleteFiles = new File(System.getProperty("user.dir")
					+ "/temp");
			File[] tempFiles = deleteFiles.listFiles();
			for (int i = 0; i < tempFiles.length; i++) {
				tempFiles[i].delete();
			}
			logger.log("Done :: " + toDate, true);
			fromCalendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}// END OF WHILE LOOP
		logger.log("Download Complete", true);
		downloadFilesListener.downloadComplete();
		// Enable the settings menu after finishing download
		logger.log("Ending download");
	}

	private List<IndexSymbol> generateIndexString()  {
		List<IndexSymbol> indexSymbols = new ArrayList<>();
		CheckBox[] indexes = Settings.getSettings().getDownload()
				.getIndex().getCheckboxes();
		for (int i = 0; i < indexes.length; i++) {
			if (Boolean.valueOf(indexes[i].getValue())) {
				IndexSymbol symbol = new IndexSymbol();
				symbol.setPrimaryCode(CommonFunctions.getIndexCode(indexes[i].getName()));
				symbol.setAlternateCode(CommonFunctions.getOldIndexCode(indexes[i].getName()));
				indexSymbols.add(symbol);
			}
		}
		return indexSymbols;
	}

	// DDM0NYYYY //DD-MM-YYYY
	private String generateDate(String toDate) {
		String date = null;
		try {
			date = CommonFunctions.getDateFormat(toDate, "dd-MM-yyyy",
					"ddMMMyyyy");
		} catch (ParseException e) {
			date = "";
		}
		return date.toUpperCase();
	}
}
