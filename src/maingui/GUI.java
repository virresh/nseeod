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
package maingui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import logging.DisplayLogMessageListener;
import logging.Logging;

import org.jdesktop.swingx.JXDatePicker;

import testconnection.CheckNews;
import testconnection.CheckNewsListener;
import testconnection.CheckUpdates;
import testconnection.CheckUpdatesListener;
import testconnection.DateValidation;
import testconnection.ValidateConnection;
import testconnection.ValidateConnectionListener;

import commonfunctions.CommonFunctions;

import config.ConfigSettings;
import db.DBExecutor;
import downloadfiles.DownloadFileThread;
import downloadfiles.DownloadFilesListener;
import dto.CheckUpdatesDTO;
import dto.ValidationDTO;
import exceptionhandler.ExceptionHandler;

public class GUI extends JFrame implements ValidateConnectionListener,
		CheckUpdatesListener,CheckNewsListener, DownloadFilesListener,DisplayLogMessageListener {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabelFrom = null;
	private JLabel jLabelTo = null;
	private JButton jButtonDownload = null;
	private JScrollPane jScrollPaneText = null;
	private JTextArea jTextArea = null;
	private Logging logger=Logging.getLogger(); 
	private JLabel jLabelInternet = null;
	private JLabel jLabelInternetState = null; // @jve:decl-index=0:visual-constraint="10,10"
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu1 = null;
	private JMenuItem jMenuItem = null;
	private JMenu jMenu0 = null;
	private JMenuItem jMenuItem1 = null;
	private JLabel jLabelUpdate = null;
	private JMenuItem jMenuItem2 = null;
	private JXDatePicker toDate = null;
	private JXDatePicker fromDate = null;
	private ConfigSettings configSettingsWindow=null;
	private JButton btnGetItNow;
	private JLabel lblNewLabel;
	/**
	 * This is the default constructor
	 */
	public GUI() {
		super();
		initialize();
		logger.addDisplayMessageListener(this);
		fromDate.setDate(CommonFunctions.getCurrentDateTime());
		toDate.setDate(CommonFunctions.getCurrentDateTime());
		getjLabelUpdate().setText("Checking for latest version");
		if(new DBExecutor().checkForAnotherDBInstance()){
			String message="Another instance of NSE EOD Data Downloader is already running";
			new JOptionPane(message).createDialog("").setVisible(true);
			logger.log(message);
			System.exit(1);
		}
		else
		{
		new ValidateConnection(this);
		new CheckUpdates(this);
		new CheckNews(this);
		
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {

		this.setSize(658, 487);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("NSE EOD Data Downloader v"
				+ commonfunctions.Constants.versionNumber);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocation(200, 200);
		this.setResizable(false);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelUpdate = new JLabel();
			jLabelUpdate.setBounds(new Rectangle(18, 405, 470, 24));
			jLabelUpdate.setText("");
			jLabelInternetState = new JLabel();
			jLabelInternetState.setBounds(new Rectangle(611, 404, 38, 26));
			jLabelInternetState.setText("");
			jLabelInternet = new JLabel();
			jLabelInternet.setBounds(new Rectangle(494, 404, 115, 26));
			jLabelInternet.setText("Internet Connection");
			jLabelTo = new JLabel();
			jLabelTo.setBounds(new Rectangle(231, 16, 53, 16));
			jLabelTo.setText(" To Date");
			jLabelFrom = new JLabel();
			jLabelFrom.setBounds(new Rectangle(25, 15, 66, 16));
			jLabelFrom.setText(" From Date");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPaneText(), null);
			jContentPane.add(jLabelFrom, null);
			jContentPane.add(jLabelTo, null);
			jContentPane.add(getJButtonDownload(), null);
			jContentPane.add(jLabelInternet, null);
			jContentPane.add(jLabelInternetState, null);
			jContentPane.add(jLabelUpdate, null);
			jContentPane.add(new JXDatePicker().getMonthView());
			jContentPane.add(getToDate(), null);
			jContentPane.add(getFromDate(), null);
			
			btnGetItNow = new JButton("Get it now !!");
			btnGetItNow.setBounds(200, 406, 101, 23);
			btnGetItNow.setVisible(false);
			jContentPane.add(btnGetItNow);
			
			lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 62, 715, 14);
			jContentPane.add(lblNewLabel);
		}
		return jContentPane;
	}

	private JXDatePicker getToDate() {
		if (toDate == null) {
			toDate = new JXDatePicker();
			toDate.setFormats("dd-MMM-yyyy");
			toDate.setBounds(new Rectangle(290, 11, 122, 25));
		}
		return toDate;
	}

	private JXDatePicker getFromDate() {
		if (fromDate == null) {
			fromDate = new JXDatePicker();
			fromDate.setFormats("dd-MMM-yyyy");
			fromDate.setBounds(new Rectangle(95, 11, 122, 25));
		}
		return fromDate;

	}

	public JLabel getjLabelInternetState() {
		return jLabelInternetState;
	}

	/**
	 * This method initializes jButtonDownload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonDownload() {
		if (jButtonDownload == null) {
			jButtonDownload = new JButton();
			jButtonDownload.setIcon(new ImageIcon(GUI.class
					.getResource("/img/Download_button.png")));
			jButtonDownload.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonDownload.setPressedIcon(new ImageIcon(GUI.class
					.getResource("/img/Download_MouseOver.png")));
			jButtonDownload.setBounds(new Rectangle(533, 10, 101, 32));
			jButtonDownload.setText("");
			jButtonDownload.setFocusable(false);
			jButtonDownload.setEnabled(false);
			jButtonDownload
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
								if (jMenu0.isEnabled()) {
									if (validateDate() == 1) {
										jTextArea.setText("");
										jMenu0.setEnabled(false);// Disable the settings menu before starting download
										//Will be replaced by Swing Worker thread in the later version
										//Need to switch to JRE 1.6 to use Swing Worker, which will take time considering the target consumers of this application
										new DownloadFileThread(
												getStringFromDate(),
												getStringToDate(),
												getDownloadFilesListener());
									}
								}
								else{
									showDownloadRunningMessage();
								}
						
						}
					});
		}
		return jButtonDownload;
	}
	
	private void showDownloadRunningMessage(){
		JOptionPane.showConfirmDialog(this,
				"Another download is in progress","Error",JOptionPane.PLAIN_MESSAGE);
	}

	protected int validateDate() {
		int validate = 1;
		ValidationDTO validation = new DateValidation().validateDate(
				fromDate.getDate(), toDate.getDate());
		if (!validation.isSuccess())
			JOptionPane.showMessageDialog(null, validation.getMessage(),
					"Error", JOptionPane.PLAIN_MESSAGE);
		return validate;
	}

	/**
	 * This method initializes jScrollPaneText
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneText() {
		if (jScrollPaneText == null) {
			jScrollPaneText = new JScrollPane();
			jScrollPaneText.setBounds(new Rectangle(15, 87, 621, 305));
			jScrollPaneText.setViewportView(getJTextArea());
			jScrollPaneText.setVisible(true);
		}
		return jScrollPaneText;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu0());
			jJMenuBar.add(getJMenu1());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu1
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("About");
			jMenu1.add(getJMenuItem());
			jMenu1.add(getJMenuItem2());
		}
		return jMenu1;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("Details");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doAction();
				}
			});
		}
		return jMenuItem;
	}

	private void doAction() {
		new Details(this).setVisible(true);
	}

	/**
	 * This method initializes jMenu0
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setName("");
			jMenu0.setMnemonic(KeyEvent.VK_UNDEFINED);
			jMenu0.setText("Settings");
			jMenu0.add(getJMenuItem1());
		}
		return jMenu0;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("Config");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {	
					doActionConfig();
				}
			});
		}
		return jMenuItem1;
	}

	private void doActionConfig() {
		try {
			configSettingsWindow=getConfigSettingsWindow();
			configSettingsWindow.showConfig_Settings(this);
		} catch (Exception e) {
			ExceptionHandler.showException(e);
		}
	}

	public JLabel getjLabelUpdate() {
		return jLabelUpdate;
	}

	@Override
	public void dispose() {
		Logging.getLogger().log("Dispose called on GUI.java");
		setVisible(true);
		if (!jMenu0.isEnabled()) // Check if download is in progress
		{
			int option = JOptionPane.showConfirmDialog(this,
					"Download is in progress\nAre you sure you want to quit",
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (option == JOptionPane.YES_OPTION) {
				super.dispose();
				Logging.getLogger().log("Fatal error :: User Interrupted");
				//All background thread will be automatically killed
				System.exit(0);
			} else {
				setVisible(true);
			}
		} else {
			super.dispose();
			Logging.getLogger().log("Application closed");
			System.exit(0);
		}
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("Notice");
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doActionNotice();
				}
			});
		}
		return jMenuItem2;
	}

	private void doActionNotice() {
		new Notice(this).setVisible(true);
	}

	public String getStringFromDate() {
		return CommonFunctions.getDDMMYYYYDate(fromDate.getDate());
	}

	public String getStringToDate() {
		return CommonFunctions.getDDMMYYYYDate(toDate.getDate());
	}

	public void connectionValidationCompleted(final boolean isConnectionOk) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (isConnectionOk) {
					jButtonDownload.setEnabled(true);
					getjLabelInternetState().setText("OK");
					getjLabelInternetState().setForeground(new Color(34,139,34));
				} else {
					jButtonDownload.setEnabled(false);
					getjLabelInternetState().setText("OFF");
					getjLabelInternetState().setForeground(Color.RED);
				}
				getjLabelInternetState().setFont(getjLabelUpdate().getFont().deriveFont(Font.BOLD));
			}
		});
	}

	public void checkUpdatesCompleted(final CheckUpdatesDTO checkUpdateResult) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (checkUpdateResult.isSuccessful()) {
					if (checkUpdateResult.isUpdateAvailable())
						{
						getjLabelUpdate().setForeground(new Color(34,139,34));
						getjLabelUpdate().setFont(getjLabelUpdate().getFont().deriveFont(Font.BOLD));
						btnGetItNow.setVisible(true);
						btnGetItNow.addActionListener(new ActionListener() {
							
							public void actionPerformed(ActionEvent e) {
								if(Desktop.isDesktopSupported())
								{
									try{
									Desktop.getDesktop().browse(new URI("http://nse-eod-downloader.blogspot.in/"));
									}
									catch(Exception e1)
									{
										logger.log(e1, "Error while opening link", true);
									}
								}
								
							}
						});
						}
				} else {
					getjLabelUpdate().setForeground(Color.RED);
					getjLabelUpdate().setFont(getjLabelUpdate().getFont().deriveFont(Font.BOLD));
				}
				getjLabelUpdate().setText(checkUpdateResult.getMessage());
				
			}
		});
	}

	public void downloadComplete() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jMenu0.setEnabled(true);
			}});
	}

	private DownloadFilesListener getDownloadFilesListener() {
		return this;
	}

	public void newMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextArea.append(message+"\n");
			}});
	}
	
	public ConfigSettings getConfigSettingsWindow() throws Exception{
		try{
		if(configSettingsWindow==null)
			{
			configSettingsWindow=new ConfigSettings();
			}
		return configSettingsWindow;
		}catch(Exception e)
		{
			logger.log(e,"Error while initializing config windows");
			throw e;
		}
	}

	@Override
	public void checkNewsCompleted(final CheckUpdatesDTO checkUpdateResult) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(Font.BOLD));
				if(checkUpdateResult.isSuccessful())
					lblNewLabel.setForeground(new Color(34,139,34));
				else
					lblNewLabel.setForeground(Color.RED);
				new Marquee(lblNewLabel, 250,checkUpdateResult.getMessage() ).start();
			}});
		
	}
} // @jve:decl-index=0:visual-constraint="53,35"
