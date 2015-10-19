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
package config;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logging.Logging;
import rjswing.RJFileChooser;

import commonfunctions.CommonFunctions;

import config.configxml.CheckBox;
import config.configxml.CheckBoxHolder;
import config.configxml.Settings;
import config.configxml.download.Download;
import config.configxml.download.DownloadPanelBase;
import config.configxml.others.Others;
import exceptionhandler.ExceptionHandler;

//TODO replace strings with string constants
public class ConfigSettings extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="10,10"
	private JTabbedPane jTabbedPane = null;
	private JPanel Download = null;
	private JTabbedPane jTabbedPane1 = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JCheckBox jCheckBoxNifty = null;
	private JCheckBox jCheckBoxNiftyJr = null;
	private JCheckBox jCheckBoxDefty = null;
	private JCheckBox jCheckBoxCNX500 = null;
	private JCheckBox jCheckBoxCNXMidcap = null;
	private JCheckBox jCheckBoxCNXIt = null;
	private JCheckBox jCheckBoxBankNifty = null;
	private JCheckBox jCheckBoxMidcap50 = null;
	private JCheckBox jCheckBoxESGIndiaIdx = null;
	private JCheckBox jCheckBoxShariah = null;
	private JCheckBox jCheckBoxShariah500 = null;
	private JCheckBox jCheckBoxCNXInfra = null;
	private JCheckBox jCheckBoxCNXRealty = null;
	private JCheckBox jCheckBoxCNXEnergy = null;
	private JCheckBox jCheckBoxCNXMnc = null;
	private JCheckBox jCheckBoxCNXPharma = null;
	private JCheckBox jCheckBoxCNXPse = null;
	private JCheckBox jCheckBoxCNXPsuBank = null;
	private JCheckBox jCheckBoxCNXService = null;
	private JCheckBox jCheckBoxCNXFmcg = null;
	private JCheckBox jCheckBoxCNX100 = null;
	private JCheckBox jCheckBoxCNXAuto = null;
	private JCheckBox jCheckBoxCNXFinance=null;
	private JCheckBox jCheckBoxCNXMetal=null;
	private JCheckBox jCheckBoxVIX = null;
	private JButton jButtonIdxCheckAll = null;
	private JButton jButtonIdxUncheckAll = null;
	private JSeparator jSeparator = null;
	private JSeparator jSeparatorEquityRight = null;
	private JSeparator jSeparatorEquityTop = null;
	private JLabel jLabel6 = null;
	private JCheckBox jCheckBoxEQMAR = null;
	private JLabel jLabel7 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JCheckBox jCheckBoxEQNHL = null;
	private JCheckBox jCheckBoxEQtop25sec = null;
	private JCheckBox jCheckBoxEQtop10GL = null;
	private JSeparator jSeparatorEquityTop1 = null;
	private JLabel jLabel9 = null;
	private JCheckBox jCheckBoxFUtop10Fu = null;
	private JSeparator jSeparatorEquityTop11 = null;
	private JCheckBox jCheckBoxCFbhav = null;
	private JSeparator jSeparatorEquityTop111 = null;
	private JLabel jLabel13 = null;
	private JLabel jLabel14 = null;
	private JCheckBox jCheckBoxOPbhav = null;
	private JCheckBox jCheckBoxOPtop10 = null;
	private JPanel jPanel5 = null;
	private JCheckBox jCheckBoxSkipWeekend = null;
	private JCheckBox jCheckBoxLogging = null;
	private JFrame callingFrame = null;
	private JCheckBox jCheckBoxEqBhav = null;
	private JCheckBox jCheckBoxFuBhav = null;
	private JCheckBox jCheckBoxFuPrefix = null;
	private JCheckBox jCheckBoxAppEqFu = null;
	private RJFileChooser eodFileChooser = null;

	public RJFileChooser getEodFileChooser() {
		return eodFileChooser;
	}

	public void setEodFileChooser(RJFileChooser eodFileChooser) {
		this.eodFileChooser = eodFileChooser;
	}

	public RJFileChooser getEquityFileChooser() {
		return equityFileChooser;
	}

	public void setEquityFileChooser(RJFileChooser equityFileChooser) {
		this.equityFileChooser = equityFileChooser;
	}

	public RJFileChooser getFutureFileChooser() {
		return futureFileChooser;
	}

	public void setFutureFileChooser(RJFileChooser futureFileChooser) {
		this.futureFileChooser = futureFileChooser;
	}

	public RJFileChooser getCurrFuturesfileChooser() {
		return currFuturesfileChooser;
	}

	public void setCurrFuturesfileChooser(RJFileChooser currFuturesfileChooser) {
		this.currFuturesfileChooser = currFuturesfileChooser;
	}

	private RJFileChooser equityFileChooser = null;
	private RJFileChooser futureFileChooser = null;
	private RJFileChooser currFuturesfileChooser = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public ConfigSettings() throws Exception{
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() throws Exception {
		this.setContentPane(getJContentPane());
		this.setEnabled(true);
		this.setResizable(false);
		this.setSize(new Dimension(534, 469));
		this.setTitle("Settings");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setLocation(250, 210);
		addWindowListener(this);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
			ExceptionHandler.showException(e);
		}
		restoreSettings(jTabbedPane);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setPreferredSize(new Dimension(500, 500));
			jContentPane.setSize(new Dimension(423, 301));
			jContentPane.add(getJTabbedPane(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(0, 0, 531, 448));
			jTabbedPane.addTab("Download", null, getDownload(), null);
			jTabbedPane.addTab("Others", null, getJPanel5(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes Download
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getDownload() {
		if (Download == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(188, 0, 151, 26));
			jLabel7.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel7.setText("Select Options");
			Download = new JPanel();
			Download.setLayout(null);
			Download.setName("download");
			Download.add(getJTabbedPane1(), null);
			Download.add(jLabel7, null);
		}
		return Download;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setSize(new Dimension(529, 394));
			jTabbedPane1.setLocation(new Point(0, 24));
			jTabbedPane1.setTabPlacement(JTabbedPane.LEFT);
			jTabbedPane1.addTab("Index", null, getJPanel(), null);
			jTabbedPane1.addTab("Equity", null, getJPanel1(), null);
			jTabbedPane1.addTab("Futures", null, getJPanel2(), null);
			jTabbedPane1.addTab("Currency Futures", null, getJPanel3(), null);
			jTabbedPane1.addTab("Options", null, getJPanel4(), null);
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setName("index");
			jPanel.add(getJCheckBoxNifty(), null);
			jPanel.add(getJCheckBoxNiftyJr(), null);
			jPanel.add(getJCheckBoxDefty(), null);
			jPanel.add(getJCheckBoxCNX500(), null);
			jPanel.add(getJCheckBoxCNXMidcap(), null);
			jPanel.add(getJCheckBoxCNXIt(), null);
			jPanel.add(getJCheckBoxBankNifty(), null);
			jPanel.add(getJCheckBoxMidcap50(), null);
			jPanel.add(getJCheckBoxESGIndiaIdx(), null);
			jPanel.add(getJCheckBoxShariah(), null);
			jPanel.add(getJCheckBoxShariah500(), null);
			jPanel.add(getJCheckBoxCNXInfra(), null);
			jPanel.add(getJCheckBoxCNXRealty(), null);
			jPanel.add(getJCheckBoxCNXEnergy(), null);
			jPanel.add(getJCheckBoxCNXMnc(), null);
			jPanel.add(getJCheckBoxCNXPharma(), null);
			jPanel.add(getJCheckBoxCNXPse(), null);
			jPanel.add(getJCheckBoxCNXPsuBank(), null);
			jPanel.add(getJCheckBoxCNXService(), null);
			jPanel.add(getJCheckBoxCNXFmcg(), null);
			jPanel.add(getJCheckBoxCNX100(), null);
			jPanel.add(getJCheckBoxCNXAuto(),null);
			jPanel.add(getJCheckBoxFinance(),null);
			jPanel.add(getJCheckBoxCNXMetal(),null);
			jPanel.add(getJCheckBoxVIX(),null);
			jPanel.add(getJButtonIdxCheckAll(), null);
			jPanel.add(getJButtonIdxUncheckAll(),null);
			jPanel.add(getJSeparator(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(15, 7, 76, 16));
			jLabel6.setText("Equity");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setName("equity");
			jPanel1.add(getJSeparatorEquityRight(), null);
			jPanel1.add(getJSeparatorEquityTop(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getJCheckBoxEQMAR(), null);
			jPanel1.add(getJCheckBoxEQNHL(), null);
			jPanel1.add(getJCheckBoxEQtop25sec(), null);
			jPanel1.add(getJCheckBoxEQtop10GL(), null);
			jPanel1.add(getJCheckBoxEqBhav(), null);
			equityFileChooser = new RJFileChooser();
//			equityFileChooser.setDefaultDirectory(System
//					.getProperty("user.dir")
//					+ System.getProperty("file.separator") + "Equity");
			equityFileChooser.setTextAreaColumns(30);
			equityFileChooser.setBounds(28, 220, 348, 33);
			jPanel1.add(equityFileChooser);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jCheckBoxNifty
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxNifty() {
		if (jCheckBoxNifty == null) {
			jCheckBoxNifty = new JCheckBox();
			jCheckBoxNifty.setBounds(new Rectangle(13, 12, 117, 21));
			jCheckBoxNifty.setText("NIFTY");
			jCheckBoxNifty.setToolTipText("S&P CNX NIFTY");
			jCheckBoxNifty.setSelected(true);
			jCheckBoxNifty.setName("");
		}
		return jCheckBoxNifty;
	}

	/**
	 * This method initializes jCheckBoxNiftyJr
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxNiftyJr() {
		if (jCheckBoxNiftyJr == null) {
			jCheckBoxNiftyJr = new JCheckBox();
			jCheckBoxNiftyJr.setBounds(new Rectangle(13, 42, 108, 21));
			jCheckBoxNiftyJr.setToolTipText("CNX NIFTY JUNIOR");
			jCheckBoxNiftyJr.setSelected(true);
			jCheckBoxNiftyJr.setText("NIFTY JUNIOR");
		}
		return jCheckBoxNiftyJr;
	}

	/**
	 * This method initializes jCheckBoxDefty
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxDefty() {
		if (jCheckBoxDefty == null) {
			jCheckBoxDefty = new JCheckBox();
			jCheckBoxDefty.setBounds(new Rectangle(12, 72, 108, 21));
			jCheckBoxDefty.setToolTipText("S&P CNX DEFTY");
			jCheckBoxDefty.setText("DEFTY");
		}
		return jCheckBoxDefty;
	}

	/**
	 * This method initializes jCheckBoxCNX500
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNX500() {
		if (jCheckBoxCNX500 == null) {
			jCheckBoxCNX500 = new JCheckBox();
			jCheckBoxCNX500.setBounds(new Rectangle(12, 102, 109, 21));
			jCheckBoxCNX500.setToolTipText("S&P CNX 500");
			jCheckBoxCNX500.setSelected(false);
			jCheckBoxCNX500.setText("CNX 500");
		}
		return jCheckBoxCNX500;
	}

	/**
	 * This method initializes jCheckBoxCNXMidcap
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXMidcap() {
		if (jCheckBoxCNXMidcap == null) {
			jCheckBoxCNXMidcap = new JCheckBox();
			jCheckBoxCNXMidcap.setBounds(new Rectangle(13, 132, 107, 21));
			jCheckBoxCNXMidcap.setToolTipText("CNX MIDCAP");
			jCheckBoxCNXMidcap.setSelected(true);
			jCheckBoxCNXMidcap.setText("CNX MIDCAP");
		}
		return jCheckBoxCNXMidcap;
	}

	/**
	 * This method initializes jCheckBoxCNXIt
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXIt() {
		if (jCheckBoxCNXIt == null) {
			jCheckBoxCNXIt = new JCheckBox();
			jCheckBoxCNXIt.setBounds(new Rectangle(13, 162, 108, 21));
			jCheckBoxCNXIt.setToolTipText("CNX IT");
			jCheckBoxCNXIt.setSelected(true);
			jCheckBoxCNXIt.setText("CNX IT");
		}
		return jCheckBoxCNXIt;
	}

	/**
	 * This method initializes jCheckBoxBankNifty
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxBankNifty() {
		if (jCheckBoxBankNifty == null) {
			jCheckBoxBankNifty = new JCheckBox();
			jCheckBoxBankNifty.setBounds(new Rectangle(13, 193, 108, 21));
			jCheckBoxBankNifty.setToolTipText("BANK NIFTY");
			jCheckBoxBankNifty.setSelected(true);
			jCheckBoxBankNifty.setText("BANK NIFTY");
		}
		return jCheckBoxBankNifty;
	}

	/**
	 * This method initializes jCheckBoxMidcap50
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxMidcap50() {
		if (jCheckBoxMidcap50 == null) {
			jCheckBoxMidcap50 = new JCheckBox();
			jCheckBoxMidcap50.setBounds(new Rectangle(133, 13, 108, 21));
			jCheckBoxMidcap50.setToolTipText("NIFTY MIDCAP 50");
			jCheckBoxMidcap50.setText("MIDCAP 50");
		}
		return jCheckBoxMidcap50;
	}

	/**
	 * This method initializes jCheckBoxESGIndiaIdx
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxESGIndiaIdx() {
		if (jCheckBoxESGIndiaIdx == null) {
			jCheckBoxESGIndiaIdx = new JCheckBox();
			jCheckBoxESGIndiaIdx.setBounds(new Rectangle(133, 43, 108, 21));
			jCheckBoxESGIndiaIdx.setToolTipText("S&P ESG INDIA INDEX");
			jCheckBoxESGIndiaIdx.setText("ESG INDIA IDX");
		}
		return jCheckBoxESGIndiaIdx;
	}

	/**
	 * This method initializes jCheckBoxShariah
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxShariah() {
		if (jCheckBoxShariah == null) {
			jCheckBoxShariah = new JCheckBox();
			jCheckBoxShariah.setBounds(new Rectangle(133, 72, 109, 21));
			jCheckBoxShariah.setToolTipText("S&P CNX NIFTY SHARIAH");
			jCheckBoxShariah.setText("SHARIAH");
		}
		return jCheckBoxShariah;
	}

	/**
	 * This method initializes jCheckBoxShariah500
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxShariah500() {
		if (jCheckBoxShariah500 == null) {
			jCheckBoxShariah500 = new JCheckBox();
			jCheckBoxShariah500.setBounds(new Rectangle(133, 102, 108, 21));
			jCheckBoxShariah500.setToolTipText("S&P CNX 500 SHARIAH");
			jCheckBoxShariah500.setText("500 SHARIAH");
		}
		return jCheckBoxShariah500;
	}

	/**
	 * This method initializes jCheckBoxCNXInfra
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXInfra() {
		if (jCheckBoxCNXInfra == null) {
			jCheckBoxCNXInfra = new JCheckBox();
			jCheckBoxCNXInfra.setBounds(new Rectangle(133, 132, 108, 21));
			jCheckBoxCNXInfra.setToolTipText("CNX INFRA");
			jCheckBoxCNXInfra.setText("CNX INFRA");
		}
		return jCheckBoxCNXInfra;
	}

	/**
	 * This method initializes jCheckBoxCNXRealty
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXRealty() {
		if (jCheckBoxCNXRealty == null) {
			jCheckBoxCNXRealty = new JCheckBox();
			jCheckBoxCNXRealty.setBounds(new Rectangle(133, 162, 107, 21));
			jCheckBoxCNXRealty.setToolTipText("CNX REALTY");
			jCheckBoxCNXRealty.setText("CNX REALTY");
		}
		return jCheckBoxCNXRealty;
	}

	/**
	 * This method initializes jCheckBoxCNXEnergy
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXEnergy() {
		if (jCheckBoxCNXEnergy == null) {
			jCheckBoxCNXEnergy = new JCheckBox();
			jCheckBoxCNXEnergy.setBounds(new Rectangle(132, 192, 109, 21));
			jCheckBoxCNXEnergy.setToolTipText("CNX ENERGY");
			jCheckBoxCNXEnergy.setText("CNX ENERGY");
		}
		return jCheckBoxCNXEnergy;
	}

	/**
	 * This method initializes jCheckBoxCNXMnc
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXMnc() {
		if (jCheckBoxCNXMnc == null) {
			jCheckBoxCNXMnc = new JCheckBox();
			jCheckBoxCNXMnc.setBounds(new Rectangle(253, 13, 108, 21));
			jCheckBoxCNXMnc.setToolTipText("CNX MNC");
			jCheckBoxCNXMnc.setText("CNX MNC");
		}
		return jCheckBoxCNXMnc;
	}

	/**
	 * This method initializes jCheckBoxCNXPharma
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXPharma() {
		if (jCheckBoxCNXPharma == null) {
			jCheckBoxCNXPharma = new JCheckBox();
			jCheckBoxCNXPharma.setBounds(new Rectangle(253, 42, 108, 21));
			jCheckBoxCNXPharma.setToolTipText("CNX PHARMA");
			jCheckBoxCNXPharma.setText("CNX PHARMA");
		}
		return jCheckBoxCNXPharma;
	}

	/**
	 * This method initializes jCheckBoxCNXPse
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXPse() {
		if (jCheckBoxCNXPse == null) {
			jCheckBoxCNXPse = new JCheckBox();
			jCheckBoxCNXPse.setBounds(new Rectangle(253, 72, 108, 21));
			jCheckBoxCNXPse.setToolTipText("CNX PSE");
			jCheckBoxCNXPse.setText("CNX PSE");
		}
		return jCheckBoxCNXPse;
	}

	/**
	 * This method initializes jCheckBoxCNXPsuBank
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXPsuBank() {
		if (jCheckBoxCNXPsuBank == null) {
			jCheckBoxCNXPsuBank = new JCheckBox();
			jCheckBoxCNXPsuBank.setBounds(new Rectangle(253, 103, 108, 21));
			jCheckBoxCNXPsuBank.setToolTipText("CNX PSU BANK");
			jCheckBoxCNXPsuBank.setText("CNX PSU BANK");
		}
		return jCheckBoxCNXPsuBank;
	}

	/**
	 * This method initializes jCheckBoxCNXService
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXService() {
		if (jCheckBoxCNXService == null) {
			jCheckBoxCNXService = new JCheckBox();
			jCheckBoxCNXService.setBounds(new Rectangle(253, 132, 108, 21));
			jCheckBoxCNXService.setToolTipText("CNX SERVICE");
			jCheckBoxCNXService.setText("CNX SERVICE");
		}
		return jCheckBoxCNXService;
	}

	/**
	 * This method initializes jCheckBoxCNXFmcg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNXFmcg() {
		if (jCheckBoxCNXFmcg == null) {
			jCheckBoxCNXFmcg = new JCheckBox();
			jCheckBoxCNXFmcg.setBounds(new Rectangle(253, 162, 109, 21));
			jCheckBoxCNXFmcg.setToolTipText("CNX FMCG");
			jCheckBoxCNXFmcg.setText("CNX FMCG");
		}
		return jCheckBoxCNXFmcg;
	}

	/**
	 * This method initializes jCheckBoxCNX100
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCNX100() {
		if (jCheckBoxCNX100 == null) {
			jCheckBoxCNX100 = new JCheckBox();
			jCheckBoxCNX100.setBounds(new Rectangle(253, 191, 109, 21));
			jCheckBoxCNX100.setToolTipText("CNX 100");
			jCheckBoxCNX100.setText("CNX 100");
		}
		return jCheckBoxCNX100;
	}
	
	private JCheckBox getJCheckBoxCNXAuto(){
		if(jCheckBoxCNXAuto==null){
			jCheckBoxCNXAuto = new JCheckBox();
			jCheckBoxCNXAuto.setBounds(new Rectangle(13, 224, 108, 21));
			jCheckBoxCNXAuto.setToolTipText("CNX AUTO");
			jCheckBoxCNXAuto.setSelected(true);
			jCheckBoxCNXAuto.setText("CNX AUTO");
		}
		return jCheckBoxCNXAuto;
	}
	
	private JCheckBox getJCheckBoxCNXMetal() {
		if (jCheckBoxCNXMetal == null) {
			jCheckBoxCNXMetal = new JCheckBox();
			jCheckBoxCNXMetal.setBounds(new Rectangle(253, 224, 108, 21));
			jCheckBoxCNXMetal.setToolTipText("CNX METAL");
			jCheckBoxCNXMetal.setText("CNX METAL");
		}
		return jCheckBoxCNXMetal;
	}
	
	private JCheckBox getJCheckBoxFinance() {
		if (jCheckBoxCNXFinance == null) {
			jCheckBoxCNXFinance = new JCheckBox();
			jCheckBoxCNXFinance.setBounds(new Rectangle(132, 224, 108, 21));
			jCheckBoxCNXFinance.setToolTipText("CNX FINANCE");
			jCheckBoxCNXFinance.setSelected(true);
			jCheckBoxCNXFinance.setText("CNX FINANCE");
		}
		return jCheckBoxCNXFinance;
	}

	private JCheckBox getJCheckBoxVIX() {
		if (jCheckBoxVIX == null) {
			jCheckBoxVIX = new JCheckBox();
			jCheckBoxVIX.setBounds(new Rectangle(13, 255, 108, 21));
			jCheckBoxVIX.setToolTipText("VIX");
			jCheckBoxVIX.setSelected(true);
			jCheckBoxVIX.setText("VIX");
		}
		return jCheckBoxVIX;
	}
	
	/**
	 * This method initializes jButtonIdxCheckAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonIdxCheckAll() {
		if (jButtonIdxCheckAll == null) {
			jButtonIdxCheckAll = new JButton();
			jButtonIdxCheckAll.setBounds(new Rectangle(13, 293, 104, 25));
			jButtonIdxCheckAll.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonIdxCheckAll.setText("Check All");
			jButtonIdxCheckAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkAllButtonAction();
						}
					});
		}
		return jButtonIdxCheckAll;
	}
	
	/**
	 * This method initializes jButtonIdxUncheckAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonIdxUncheckAll() {
		if (jButtonIdxUncheckAll == null) {
			jButtonIdxUncheckAll = new JButton();
			jButtonIdxUncheckAll.setBounds(new Rectangle(132, 293, 104, 25));
			jButtonIdxUncheckAll.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonIdxUncheckAll.setText("Uncheck All");
			jButtonIdxUncheckAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							uncheckAllButtonAction();
						}
					});
		}
		return jButtonIdxUncheckAll;
	}

	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new Rectangle(9, 278, 364, 10));
		}
		return jSeparator;
	}

	/**
	 * This method initializes jSeparatorEquityRight
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparatorEquityRight() {
		if (jSeparatorEquityRight == null) {
			jSeparatorEquityRight = new JSeparator();
			jSeparatorEquityRight.setBounds(new Rectangle(510, 29, 8, 364));
			jSeparatorEquityRight.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparatorEquityRight;
	}

	/**
	 * This method initializes jSeparatorEquityTop
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparatorEquityTop() {
		if (jSeparatorEquityTop == null) {
			jSeparatorEquityTop = new JSeparator();
			jSeparatorEquityTop.setBounds(new Rectangle(0, 28, 405, 8));
		}
		return jSeparatorEquityTop;
	}

	/**
	 * This method initializes jCheckBoxEQMAR
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxEQMAR() {
		if (jCheckBoxEQMAR == null) {
			jCheckBoxEQMAR = new JCheckBox();
			jCheckBoxEQMAR.setBounds(new Rectangle(28, 43, 184, 21));
			jCheckBoxEQMAR.setText("Market Activity Report");
		}
		return jCheckBoxEQMAR;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(15, 8, 75, 16));
			jLabel9.setText("Futures");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setName("futures");
			jPanel2.add(getJSeparatorEquityTop1(), null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(getJCheckBoxFUtop10Fu(), null);
			jPanel2.add(getJCheckBoxFuBhav(), null);
			jPanel2.add(getJCheckBoxFuPrefix(), null);
			futureFileChooser = new RJFileChooser();
//			futureFileChooser.setDefaultDirectory(System
//					.getProperty("user.dir")
//					+ System.getProperty("file.separator") + "Futures");
			futureFileChooser.setTextAreaColumns(30);
			futureFileChooser.setBounds(10, 160, 348, 33);
			jPanel2.add(futureFileChooser);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(16, 7, 134, 16));
			jLabel13.setText("Currency Futures");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setName("currencyfutures");
			jPanel3.add(getJSeparatorEquityTop11(), null);
			jPanel3.add(getJCheckBoxCFbhav(), null);
			jPanel3.add(jLabel13, null);
			currFuturesfileChooser = new RJFileChooser();
			currFuturesfileChooser.setTextAreaColumns(30);
			currFuturesfileChooser.setBounds(13, 106, 348, 33);
			jPanel3.add(currFuturesfileChooser);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(16, 8, 106, 16));
			jLabel14.setText("Options");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setName("options");
			jPanel4.add(getJSeparatorEquityTop111(), null);
			jPanel4.add(jLabel14, null);
			jPanel4.add(getJCheckBoxOPbhav(), null);
			jPanel4.add(getJCheckBoxOPtop10(), null);
			RJFileChooser fileChooser = new RJFileChooser();
//			fileChooser.setDefaultDirectory(System.getProperty("user.dir")
//					+ System.getProperty("file.separator") + "Options");
			fileChooser.setTextAreaColumns(30);
			fileChooser.setBounds(16, 100, 348, 33);
			jPanel4.add(fileChooser);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jCheckBoxEQNHL
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxEQNHL() {
		if (jCheckBoxEQNHL == null) {
			jCheckBoxEQNHL = new JCheckBox();
			jCheckBoxEQNHL.setBounds(new Rectangle(28, 67, 195, 21));
			jCheckBoxEQNHL.setText("New High or Low");
		}
		return jCheckBoxEQNHL;
	}

	/**
	 * This method initializes jCheckBoxEQtop25sec
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxEQtop25sec() {
		if (jCheckBoxEQtop25sec == null) {
			jCheckBoxEQtop25sec = new JCheckBox();
			jCheckBoxEQtop25sec.setBounds(new Rectangle(28, 96, 228, 21));
			jCheckBoxEQtop25sec.setText("Top 25 securities by Trading Values");
		}
		return jCheckBoxEQtop25sec;
	}

	/**
	 * This method initializes jCheckBoxEQtop10GL
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxEQtop10GL() {
		if (jCheckBoxEQtop10GL == null) {
			jCheckBoxEQtop10GL = new JCheckBox();
			jCheckBoxEQtop10GL.setBounds(new Rectangle(28, 126, 183, 21));
			jCheckBoxEQtop10GL.setText("Top 10 Gainers & Loosers");
		}
		return jCheckBoxEQtop10GL;
	}

	/**
	 * This method initializes jSeparatorEquityTop1
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparatorEquityTop1() {
		if (jSeparatorEquityTop1 == null) {
			jSeparatorEquityTop1 = new JSeparator();
			jSeparatorEquityTop1.setBounds(new Rectangle(-1, 31, 403, 10));
		}
		return jSeparatorEquityTop1;
	}

	/**
	 * This method initializes jCheckBoxFUtop10Fu
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxFUtop10Fu() {
		if (jCheckBoxFUtop10Fu == null) {
			jCheckBoxFUtop10Fu = new JCheckBox();
			jCheckBoxFUtop10Fu.setBounds(new Rectangle(9, 48, 167, 21));
			jCheckBoxFUtop10Fu.setText("Top 10 future contracts");
		}
		return jCheckBoxFUtop10Fu;
	}

	/**
	 * This method initializes jSeparatorEquityTop11
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparatorEquityTop11() {
		if (jSeparatorEquityTop11 == null) {
			jSeparatorEquityTop11 = new JSeparator();
			jSeparatorEquityTop11.setBounds(new Rectangle(-1, 30, 404, 10));
		}
		return jSeparatorEquityTop11;
	}

	/**
	 * This method initializes jCheckBoxCFbhav
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxCFbhav() {
		if (jCheckBoxCFbhav == null) {
			jCheckBoxCFbhav = new JCheckBox();
			jCheckBoxCFbhav.setBounds(new Rectangle(9, 47, 182, 21));
			jCheckBoxCFbhav.setText("Currency Futures Bhavcopy");
		}
		return jCheckBoxCFbhav;
	}

	/**
	 * This method initializes jSeparatorEquityTop111
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparatorEquityTop111() {
		if (jSeparatorEquityTop111 == null) {
			jSeparatorEquityTop111 = new JSeparator();
			jSeparatorEquityTop111.setBounds(new Rectangle(-1, 30, 404, 13));
		}
		return jSeparatorEquityTop111;
	}

	/**
	 * This method initializes jCheckBoxOPbhav
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxOPbhav() {
		if (jCheckBoxOPbhav == null) {
			jCheckBoxOPbhav = new JCheckBox();
			jCheckBoxOPbhav.setBounds(new Rectangle(13, 42, 184, 21));
			jCheckBoxOPbhav.setText("Options Bhavcopy");
		}
		return jCheckBoxOPbhav;
	}

	/**
	 * This method initializes jCheckBoxOPtop10
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxOPtop10() {
		if (jCheckBoxOPtop10 == null) {
			jCheckBoxOPtop10 = new JCheckBox();
			jCheckBoxOPtop10.setBounds(new Rectangle(13, 72, 183, 21));
			jCheckBoxOPtop10.setText("Top 10 Options contracts");
		}
		return jCheckBoxOPtop10;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setName("others");
			jPanel5.add(getJCheckBoxSkipWeekend(), null);
			jPanel5.add(getJCheckBoxLogging(), null);
			jPanel5.add(getJCheckBoxAppEqFu(), null);
			eodFileChooser = new RJFileChooser();
			eodFileChooser.setTextAreaColumns(30);
			eodFileChooser.setBounds(198, 75, 318, 33);
			jPanel5.add(eodFileChooser);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jCheckBoxSkipWeekend
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxSkipWeekend() {
		if (jCheckBoxSkipWeekend == null) {
			jCheckBoxSkipWeekend = new JCheckBox();
			jCheckBoxSkipWeekend.setBounds(new Rectangle(12, 27, 136, 21));
			jCheckBoxSkipWeekend.setText("Skip Weekends");
		}
		return jCheckBoxSkipWeekend;
	}

	/**
	 * This method initializes jCheckBoxLogging
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxLogging() {
		if (jCheckBoxLogging == null) {
			jCheckBoxLogging = new JCheckBox();
			jCheckBoxLogging.setBounds(new Rectangle(12, 57, 150, 21));
			jCheckBoxLogging.setText("Enable Verbose Logging");
		}
		return jCheckBoxLogging;
	}

	/**
	 * This method initializes jCheckBoxEqBhav
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxEqBhav() {
		if (jCheckBoxEqBhav == null) {
			jCheckBoxEqBhav = new JCheckBox();
			jCheckBoxEqBhav.setBounds(new Rectangle(28, 156, 230, 21));
			jCheckBoxEqBhav.setSelected(true);
			jCheckBoxEqBhav.setText("Equity Bhavcopy");
		}
		return jCheckBoxEqBhav;
	}

	/**
	 * This method initializes jCheckBoxFuBhav
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxFuBhav() {
		if (jCheckBoxFuBhav == null) {
			jCheckBoxFuBhav = new JCheckBox();
			jCheckBoxFuBhav.setBounds(new Rectangle(9, 78, 190, 21));
			jCheckBoxFuBhav.setSelected(true);
			jCheckBoxFuBhav.setText("Futures Bhavcopy");
		}
		return jCheckBoxFuBhav;
	}

	public JCheckBox getjCheckBoxBankNifty() {
		return jCheckBoxBankNifty;
	}

	public JCheckBox getjCheckBoxCFbhav() {
		return jCheckBoxCFbhav;
	}

	
	public JCheckBox getjCheckBoxCNX100() {
		return jCheckBoxCNX100;
	}

	public JCheckBox getjCheckBoxCNX500() {
		return jCheckBoxCNX500;
	}

	public JCheckBox getjCheckBoxCNXEnergy() {
		return jCheckBoxCNXEnergy;
	}

	public JCheckBox getjCheckBoxCNXFmcg() {
		return jCheckBoxCNXFmcg;
	}

	public JCheckBox getjCheckBoxCNXInfra() {
		return jCheckBoxCNXInfra;
	}

	public JCheckBox getjCheckBoxCNXIt() {
		return jCheckBoxCNXIt;
	}

	public JCheckBox getjCheckBoxCNXMidcap() {
		return jCheckBoxCNXMidcap;
	}

	public JCheckBox getjCheckBoxCNXMnc() {
		return jCheckBoxCNXMnc;
	}

	public JCheckBox getjCheckBoxCNXPharma() {
		return jCheckBoxCNXPharma;
	}

	public JCheckBox getjCheckBoxCNXPse() {
		return jCheckBoxCNXPse;
	}

	public JCheckBox getjCheckBoxCNXPsuBank() {
		return jCheckBoxCNXPsuBank;
	}

	public JCheckBox getjCheckBoxCNXRealty() {
		return jCheckBoxCNXRealty;
	}

	public JCheckBox getjCheckBoxCNXService() {
		return jCheckBoxCNXService;
	}

	public JCheckBox getjCheckBoxDefty() {
		return jCheckBoxDefty;
	}

	public JCheckBox getjCheckBoxEQMAR() {
		return jCheckBoxEQMAR;
	}

	public JCheckBox getjCheckBoxEQNHL() {
		return jCheckBoxEQNHL;
	}


	public JCheckBox getjCheckBoxEQtop10GL() {
		return jCheckBoxEQtop10GL;
	}

	public JCheckBox getjCheckBoxEQtop25sec() {
		return jCheckBoxEQtop25sec;
	}

	public JCheckBox getjCheckBoxESGIndiaIdx() {
		return jCheckBoxESGIndiaIdx;
	}


	public JCheckBox getjCheckBoxFUtop10Fu() {
		return jCheckBoxFUtop10Fu;
	}

	public JCheckBox getjCheckBoxMidcap50() {
		return jCheckBoxMidcap50;
	}

	public JCheckBox getjCheckBoxNifty() {
		return jCheckBoxNifty;
	}

	public JCheckBox getjCheckBoxNiftyJr() {
		return jCheckBoxNiftyJr;
	}

	public JCheckBox getjCheckBoxOPbhav() {
		return jCheckBoxOPbhav;
	}

	public JCheckBox getjCheckBoxOPtop10() {
		return jCheckBoxOPtop10;
	}

	public JCheckBox getjCheckBoxShariah() {
		return jCheckBoxShariah;
	}

	public JCheckBox getjCheckBoxShariah500() {
		return jCheckBoxShariah500;
	}

	public JCheckBox getjCheckBoxSkipWeekend() {
		return jCheckBoxSkipWeekend;
	}

	public JCheckBox getjCheckBoxLogging() {
		return jCheckBoxLogging;
	}

	public JPanel getjPanel() {
		return jPanel;
	}

	public JCheckBox getjCheckBoxEqBhav() {
		return jCheckBoxEqBhav;
	}

	public JCheckBox getjCheckBoxFuBhav() {
		return jCheckBoxFuBhav;
	}

	public JCheckBox getjCheckBoxFuPrefix() {
		return jCheckBoxFuPrefix;
	}

	public JCheckBox getjCheckBoxAppEqFu() {
		return jCheckBoxAppEqFu;
	}

	public void setjCheckBoxAppEqFu(JCheckBox jCheckBoxAppEqFu) {
		this.jCheckBoxAppEqFu = jCheckBoxAppEqFu;
	}

	public void setjCheckBoxFuPrefix(JCheckBox jCheckBoxFuPrefix) {
		this.jCheckBoxFuPrefix = jCheckBoxFuPrefix;
	}

	public void setjCheckBoxBankNifty(JCheckBox jCheckBoxBankNifty) {
		this.jCheckBoxBankNifty = jCheckBoxBankNifty;
	}

	public void setjCheckBoxCFbhav(JCheckBox jCheckBoxCFbhav) {
		this.jCheckBoxCFbhav = jCheckBoxCFbhav;
	}



	public void setjCheckBoxCNX100(JCheckBox jCheckBoxCNX100) {
		this.jCheckBoxCNX100 = jCheckBoxCNX100;
	}

	public void setjCheckBoxCNX500(JCheckBox jCheckBoxCNX500) {
		this.jCheckBoxCNX500 = jCheckBoxCNX500;
	}

	public void setjCheckBoxCNXEnergy(JCheckBox jCheckBoxCNXEnergy) {
		this.jCheckBoxCNXEnergy = jCheckBoxCNXEnergy;
	}

	public void setjCheckBoxCNXFmcg(JCheckBox jCheckBoxCNXFmcg) {
		this.jCheckBoxCNXFmcg = jCheckBoxCNXFmcg;
	}

	public void setjCheckBoxCNXInfra(JCheckBox jCheckBoxCNXInfra) {
		this.jCheckBoxCNXInfra = jCheckBoxCNXInfra;
	}

	public void setjCheckBoxCNXIt(JCheckBox jCheckBoxCNXIt) {
		this.jCheckBoxCNXIt = jCheckBoxCNXIt;
	}

	public void setjCheckBoxCNXMidcap(JCheckBox jCheckBoxCNXMidcap) {
		this.jCheckBoxCNXMidcap = jCheckBoxCNXMidcap;
	}

	public void setjCheckBoxCNXMnc(JCheckBox jCheckBoxCNXMnc) {
		this.jCheckBoxCNXMnc = jCheckBoxCNXMnc;
	}

	public void setjCheckBoxCNXPharma(JCheckBox jCheckBoxCNXPharma) {
		this.jCheckBoxCNXPharma = jCheckBoxCNXPharma;
	}

	public void setjCheckBoxCNXPse(JCheckBox jCheckBoxCNXPse) {
		this.jCheckBoxCNXPse = jCheckBoxCNXPse;
	}

	public void setjCheckBoxCNXPsuBank(JCheckBox jCheckBoxCNXPsuBank) {
		this.jCheckBoxCNXPsuBank = jCheckBoxCNXPsuBank;
	}

	public void setjCheckBoxCNXRealty(JCheckBox jCheckBoxCNXRealty) {
		this.jCheckBoxCNXRealty = jCheckBoxCNXRealty;
	}

	public void setjCheckBoxCNXService(JCheckBox jCheckBoxCNXService) {
		this.jCheckBoxCNXService = jCheckBoxCNXService;
	}

	public void setjCheckBoxDefty(JCheckBox jCheckBoxDefty) {
		this.jCheckBoxDefty = jCheckBoxDefty;
	}

	public void setjCheckBoxEQMAR(JCheckBox jCheckBoxEQMAR) {
		this.jCheckBoxEQMAR = jCheckBoxEQMAR;
	}

	public void setjCheckBoxEQNHL(JCheckBox jCheckBoxEQNHL) {
		this.jCheckBoxEQNHL = jCheckBoxEQNHL;
	}

	public void setjCheckBoxEQtop10GL(JCheckBox jCheckBoxEQtop10GL) {
		this.jCheckBoxEQtop10GL = jCheckBoxEQtop10GL;
	}

	public void setjCheckBoxEQtop25sec(JCheckBox jCheckBoxEQtop25sec) {
		this.jCheckBoxEQtop25sec = jCheckBoxEQtop25sec;
	}

	public void setjCheckBoxESGIndiaIdx(JCheckBox jCheckBoxESGIndiaIdx) {
		this.jCheckBoxESGIndiaIdx = jCheckBoxESGIndiaIdx;
	}

	public void setjCheckBoxFUtop10Fu(JCheckBox jCheckBoxFUtop10Fu) {
		this.jCheckBoxFUtop10Fu = jCheckBoxFUtop10Fu;
	}

	public void setjCheckBoxMidcap50(JCheckBox jCheckBoxMidcap50) {
		this.jCheckBoxMidcap50 = jCheckBoxMidcap50;
	}

	public void setjCheckBoxNifty(JCheckBox jCheckBoxNifty) {
		this.jCheckBoxNifty = jCheckBoxNifty;
	}

	public void setjCheckBoxNiftyJr(JCheckBox jCheckBoxNiftyJr) {
		this.jCheckBoxNiftyJr = jCheckBoxNiftyJr;
	}

	public void setjCheckBoxOPbhav(JCheckBox jCheckBoxOPbhav) {
		this.jCheckBoxOPbhav = jCheckBoxOPbhav;
	}

	public void setjCheckBoxOPtop10(JCheckBox jCheckBoxOPtop10) {
		this.jCheckBoxOPtop10 = jCheckBoxOPtop10;
	}

	public void setjCheckBoxShariah(JCheckBox jCheckBoxShariah) {
		this.jCheckBoxShariah = jCheckBoxShariah;
	}

	public void setjCheckBoxShariah500(JCheckBox jCheckBoxShariah500) {
		this.jCheckBoxShariah500 = jCheckBoxShariah500;
	}

	public void setjCheckBoxEqBhav(JCheckBox jCheckBoxEqBhav) {
		this.jCheckBoxEqBhav = jCheckBoxEqBhav;
	}

	public void setjCheckBoxFuBhav(JCheckBox jCheckBoxFuBhav) {
		this.jCheckBoxFuBhav = jCheckBoxFuBhav;
	}

	private void checkAllButtonAction() {

		for (int i = 0; i < jPanel.getComponentCount(); i++) {
			if (jPanel.getComponent(i).getClass().getSimpleName()
					.equalsIgnoreCase("JCheckBox")) {
				((JCheckBox) jPanel.getComponent(i)).setSelected(true);
			}
		}
	}
	
	private void uncheckAllButtonAction() {

		for (int i = 0; i < jPanel.getComponentCount(); i++) {
			if (jPanel.getComponent(i).getClass().getSimpleName()
					.equalsIgnoreCase("JCheckBox")) {
				((JCheckBox) jPanel.getComponent(i)).setSelected(false);
			}
		}
	}

	public void showConfig_Settings(JFrame callingFrame) {
		Logging.getLogger().log("in show config");
		this.setVisible(true);
		this.callingFrame = callingFrame;
		callingFrame.setEnabled(false);
	}

	/**
	 * This method initializes jCheckBoxFuPrefix
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxFuPrefix() {
		if (jCheckBoxFuPrefix == null) {
			jCheckBoxFuPrefix = new JCheckBox();
			jCheckBoxFuPrefix.setBounds(new Rectangle(9, 107, 197, 21));
			jCheckBoxFuPrefix.setText("Add (-I) as prefix");
		}
		return jCheckBoxFuPrefix;
	}

	/**
	 * This method initializes jCheckBoxAppEqFu
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxAppEqFu() {
		if (jCheckBoxAppEqFu == null) {
			jCheckBoxAppEqFu = new JCheckBox();
			jCheckBoxAppEqFu.setBounds(new Rectangle(13, 87, 171, 21));
			jCheckBoxAppEqFu.setSelected(true);
			jCheckBoxAppEqFu.setText("Create consolidated Bhavcopy");
			jCheckBoxAppEqFu.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					jCheckBoxAppEqFuStateChanged();
				}
			});
		}
		return jCheckBoxAppEqFu;
	}

	private void jCheckBoxAppEqFuStateChanged() {
		if (jCheckBoxAppEqFu.isSelected())
			eodFileChooser.setEnabled(true);
		else
			eodFileChooser.setEnabled(false);
	}

	private void restoreSettings(JComponent jTabbedPane) {
		// Download
		// Index
		Settings settings= Settings.getSettings();
		JPanel download = (JPanel) CommonFunctions.getComponentByName(true,
				jTabbedPane, "download");
		JPanel index = (JPanel) CommonFunctions.getComponentByName(true,
				download, "index");
		restoreDownloadPanelBaseSettings(settings.getDownload().getIndex(),
				index);
		// Equity
		JPanel equity = (JPanel) CommonFunctions.getComponentByName(true,
				download, "equity");
		restoreDownloadPanelBaseSettings(settings.getDownload().getEquity(),
				equity);
		// Futures
		JPanel futures = (JPanel) CommonFunctions.getComponentByName(true,
				download, "futures");
		restoreDownloadPanelBaseSettings(settings.getDownload().getFutures(),
				futures);
		// CurrencyFutures
		JPanel currencyfutures = (JPanel) CommonFunctions.getComponentByName(
				true, download, "currencyfutures");
		restoreDownloadPanelBaseSettings(settings.getDownload().getCurrencyfutures(),
				currencyfutures);
		// Options
		JPanel options = (JPanel) CommonFunctions.getComponentByName(true,
				download, "options");
		restoreDownloadPanelBaseSettings(settings.getDownload().getOptions(),
				options);

		// Other options
		JPanel others = (JPanel) CommonFunctions.getComponentByName(true,
				jTabbedPane, "others");
		restoreOtherSettings(settings.getOthers(), others);
	}

	private void restoreDownloadPanelBaseSettings(DownloadPanelBase holder,
			JComponent jComponent) {
		restoreCheckBoxSettings(holder, jComponent);
		restoreDirectorySettings(holder.getDirectory(), jComponent);
	}

	private void restoreOtherSettings(Others holder, JComponent jComponent) {
		restoreCheckBoxSettings(holder, jComponent);
		restoreDirectorySettings(holder.getDirectory(), jComponent);
	}

	private void restoreDirectorySettings(String directory,
			JComponent jComponent) {
		for (int i = 0; i < jComponent.getComponentCount(); i++) {
			if (jComponent.getComponent(i).getClass() == RJFileChooser.class) {
				RJFileChooser chooser = (RJFileChooser) jComponent
						.getComponent(i);
				chooser.setDefaultDirectory(directory);
			}
		}
	}

	private void restoreCheckBoxSettings(CheckBoxHolder holder,
			JComponent jComponent) {
		for (int i = 0; i < jComponent.getComponentCount(); i++) {
			if (jComponent.getComponent(i).getClass() == JCheckBox.class) {
				JCheckBox checkBox = ((JCheckBox) jComponent.getComponent(i));
				for (int j = 0; j < holder.getCheckboxes().length; j++) {
					if (checkBox.getText().equalsIgnoreCase(
							holder.getCheckboxes()[j].getName())) {
						checkBox.setSelected(Boolean.valueOf(holder
								.getCheckboxes()[j].getValue()));
					}
				}
			}
		}
	}

	// Aggregates the entire data
	private Download saveDownloadPanelSettings(JPanel download) {
		Download downloadData = new Download();
		JPanel index = (JPanel) CommonFunctions.getComponentByName(true,
				download, "index");
		DownloadPanelBase indexData = saveDownloadPanelData(index);
		JPanel equity = (JPanel) CommonFunctions.getComponentByName(true,
				download, "equity");
		DownloadPanelBase equityData = saveDownloadPanelData(equity);
		JPanel futures = (JPanel) CommonFunctions.getComponentByName(true,
				download, "futures");
		DownloadPanelBase futuresData = saveDownloadPanelData(futures);
		JPanel currencyFutures = (JPanel) CommonFunctions.getComponentByName(
				true, download, "currencyfutures");
		DownloadPanelBase currencyFuturesData = saveDownloadPanelData(currencyFutures);
		JPanel options = (JPanel) CommonFunctions.getComponentByName(true,
				download, "options");
		DownloadPanelBase optionsData = saveDownloadPanelData(options);
		downloadData.setIndex(indexData);
		downloadData.setEquity(equityData);
		downloadData.setFutures(futuresData);
		downloadData.setCurrencyfutures(currencyFuturesData);
		downloadData.setOptions(optionsData);
		return downloadData;
	}

	private DownloadPanelBase saveDownloadPanelData(JPanel tab) {
		DownloadPanelBase panelData = new DownloadPanelBase();
		panelData.setCheckboxes(saveAllCheckBoxesFromComponent(tab)
				.getCheckboxes());
		panelData.setDirectory(saveDirectoryData(tab));
		return panelData;
	}

	private String saveDirectoryData(JPanel tab) {
		for (int i = 0; i < tab.getComponentCount(); i++) {
			if (tab.getComponent(i).getClass() == RJFileChooser.class) {
				RJFileChooser dirChooser = (RJFileChooser) tab.getComponent(i);
				return dirChooser.getSelectedDirectory();
			}
		}
		return null;
	}

	// Extracts all the check box's settings and store it in the dto
	private CheckBoxHolder saveAllCheckBoxesFromComponent(
			JComponent parentComponent) {
		CheckBoxHolder checkBoxHolder = new CheckBoxHolder();
		ArrayList<CheckBox> indexChkBoxes = new ArrayList<CheckBox>();
		for (int i = 0; i < parentComponent.getComponentCount(); i++) {
			if (parentComponent.getComponent(i).getClass() == JCheckBox.class) {
				CheckBox chkBox = new CheckBox();
				chkBox.setName(((JCheckBox) parentComponent.getComponent(i))
						.getText());
				chkBox.setValue(Boolean.toString(((JCheckBox) parentComponent
						.getComponent(i)).isSelected()));
				indexChkBoxes.add(chkBox);
			}
		}
		CheckBox[] arrChkBox = new CheckBox[indexChkBoxes.size()];
		indexChkBoxes.toArray(arrChkBox);
		checkBoxHolder.setCheckboxes(arrChkBox);
		return checkBoxHolder;
	}

	private Others saveOtherPanelSettings(JPanel others) {
		Others otherData = new Others();
		otherData.setCheckboxes(saveAllCheckBoxesFromComponent(others)
				.getCheckboxes());
		otherData.setDirectory(saveDirectoryData(others));
		return otherData;
	}
	
	private void updateSettingsModel(JComponent jTabbedPane) throws Exception {
		Settings settings = Settings.getSettings();
		JPanel download = (JPanel) CommonFunctions.getComponentByName(true,
				jTabbedPane, "download");
		JPanel others = (JPanel) CommonFunctions.getComponentByName(true,
				jTabbedPane, "others");
		settings.setDownload(saveDownloadPanelSettings(download));
		settings.setOthers(saveOtherPanelSettings(others));
		settings.commit();
	}

	public void windowOpened(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e){
		try {
			updateSettingsModel(jTabbedPane);
			callingFrame.setEnabled(true);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	public void windowClosed(WindowEvent e) {
		
		
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowActivated(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		
	}
} // @jve:decl-index=0:visual-constraint="44,13"