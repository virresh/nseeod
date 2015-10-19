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
package main_gui;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.GregorianCalendar;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import logging.logging;
import config.Config_Settings;
import download_files.DownloadFileThread;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jTextFrom = null;
	private JTextField jTextTo = null;
	private JLabel jLabelFrom = null;
	private JLabel jLabelTo = null;
	private JButton jButtonDownload = null;
	private JScrollPane jScrollPaneText = null;
	private JTextArea jTextArea = null;
	private JLabel jLabelFromFormat = null;
	private JLabel jLabelToFormat = null;
	private static int threadRunning=0;
	private JLabel jLabelInternet = null;
	private JLabel jLabelInternetState = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private boolean connectionOK=false;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu1 = null;
	private JMenuItem jMenuItem = null;
	private JMenu jMenu0 = null;
	private JMenuItem jMenuItem1 = null;
	private Config_Settings settings=null;
	private JLabel jLabelUpdate = null;
	private JMenuItem jMenuItem2 = null;
	
	/**
	 * This is the default constructor
	 */
	public GUI(Config_Settings settings) {
		super();
		initialize();
		this.settings=settings;
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
		this.setTitle("NSE EOD Data Downloader v2.0");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocation(200,200);
		this.setResizable(false);
		
	}
	/**
	 * This method initializes connectionOK
	 * 
	 * @return boolean
	 */
	public void setConnectionOK(boolean connectionOK) {
	this.connectionOK = connectionOK;
	}
	private boolean getConnectionOK(){
		return connectionOK;
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
			jLabelToFormat = new JLabel();
			jLabelToFormat.setBounds(new Rectangle(285, 36, 77, 16));
			jLabelToFormat.setText(" DD-MM-YYYY");
			jLabelFromFormat = new JLabel();
			jLabelFromFormat.setBounds(new Rectangle(89, 36, 77, 15));
			jLabelFromFormat.setText(" DD-MM-YYYY");
			jLabelTo = new JLabel();
			jLabelTo.setBounds(new Rectangle(231, 16, 53, 16));
			jLabelTo.setText(" To Date");
			jLabelFrom = new JLabel();
			jLabelFrom.setBounds(new Rectangle(25, 15, 66, 16));
			jLabelFrom.setText(" From Date");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextFrom(), null);
			jContentPane.add(getJTextTo(), null);
			jContentPane.add(getJScrollPaneText(), null);
			jContentPane.add(jLabelFrom, null);
			jContentPane.add(jLabelTo, null);
			jContentPane.add(getJButtonDownload(), null);
			jContentPane.add(jLabelFromFormat, null);
			jContentPane.add(jLabelToFormat, null);
			jContentPane.add(jLabelInternet, null);
			jContentPane.add(jLabelInternetState, null);
			jContentPane.add(jLabelUpdate, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextFrom	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFrom() {
		if (jTextFrom == null) {
			jTextFrom = new JTextField();
			jTextFrom.setBounds(new Rectangle(91, 14, 71, 20));
			
		}
		return jTextFrom;
	}
	public String getjTextFrom() {
		return jTextFrom.getText();
	}
	public String getjTextTo() {
		return jTextTo.getText();
	}
	public JLabel getjLabelInternetState(){
		return jLabelInternetState; 
	}
	/**
	 * This method initializes jTextTo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextTo() {
		if (jTextTo == null) {
			jTextTo = new JTextField();
			jTextTo.setBounds(new Rectangle(286, 14, 72, 20));
			
		}
		return jTextTo;
		
	}

	/**
	 * This method initializes jButtonDownload	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDownload() {
		if (jButtonDownload == null) {
			jButtonDownload = new JButton();
			jButtonDownload.setIcon(new ImageIcon(GUI.class.getResource("/img/Download_button.png")));
			jButtonDownload.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonDownload.setPressedIcon(new ImageIcon(GUI.class.getResource("/img/Download_MouseOver.png")));
			jButtonDownload.setBounds(new Rectangle(533, 10, 101, 32));
			jButtonDownload.setText("");
			jButtonDownload.setFocusable(false);
			jButtonDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					// TODO Auto-generated Event stub actionPerformed()
					if(getConnectionOK())
					{
						if(getThreadRunning()==0)
						{
							if(validateDate()==1)
							{
							jMenu0.setEnabled(false);
							GUI.setThreadRunning(1);
							jTextArea.setText("");
							new DownloadFileThread(jTextFrom, jTextTo, jTextArea,settings,jMenu0);
							}
						}
					}
					else
					{
							JOptionPane.showMessageDialog(null, "Please check the Internet Connection", "Error", JOptionPane.INFORMATION_MESSAGE);		
					}
				}
			});
		}
		return jButtonDownload;
	}


	protected int validateDate() {
		// TODO Auto-generated method stub
		int validate=1;
		GregorianCalendar fromDate=null;
		GregorianCalendar toDate=null;
		GregorianCalendar currentDate=new GregorianCalendar();
		try{
		fromDate=new GregorianCalendar(Integer.parseInt(jTextFrom.getText().substring(6, 10)), Integer.parseInt(jTextFrom.getText().substring(3, 5))-1, Integer.parseInt(jTextFrom.getText().substring(0, 2)));
		toDate=new GregorianCalendar(Integer.parseInt(jTextTo.getText().substring(6, 10)), Integer.parseInt(jTextTo.getText().substring(3, 5))-1, Integer.parseInt(jTextTo.getText().substring(0, 2)));
		fromDate.setLenient(false);
		toDate.setLenient(false);
		fromDate.getTime();
		toDate.getTime();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, "Please enter the correct Date", "Error", JOptionPane.ERROR_MESSAGE);
			validate=0;
			jTextFrom.setText("");
			jTextTo.setText("");
			return validate;
		}
		if(jTextFrom.getText().length()!=10 || jTextTo.getText().length()!=10)
		{
			JOptionPane.showMessageDialog(null, "Please enter the Date of proper length", "Error", JOptionPane.ERROR_MESSAGE);
			validate=0;
			return validate;
		}
		if((!jTextFrom.getText().substring(2, 3).equalsIgnoreCase("-"))||(!jTextFrom.getText().substring(5, 6).equalsIgnoreCase("-"))||(!jTextTo.getText().substring(2, 3).equalsIgnoreCase("-"))||(!jTextTo.getText().substring(5,6).equalsIgnoreCase("-")))
		{
			JOptionPane.showMessageDialog(null, "Please enter the Date in correct format(-)", "Error", JOptionPane.ERROR_MESSAGE);
			validate=0;
			return validate;
		}
		if((fromDate.compareTo(currentDate)==1||toDate.compareTo(currentDate)==1)){
			JOptionPane.showMessageDialog(null, "From/To date cannot be more than future date", "Error", JOptionPane.ERROR_MESSAGE);
			validate=0;
			return validate;
		}
		if((fromDate.compareTo(toDate))==1){
			JOptionPane.showMessageDialog(null, "To Date is less than from Date", "Error", JOptionPane.ERROR_MESSAGE);
			validate=0;
			return validate;
		}
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
					// TODO Auto-generated Event stub actionPerformed()
					doAction();
				}
			});
		}
		return jMenuItem;
	}
  private void doAction(){
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
				doActionConfig(); // TODO Auto-generated Event stub actionPerformed()
			}
		});
	}
	return jMenuItem1;
}
public static void setThreadRunning(int threadRunning) {
	GUI.threadRunning = threadRunning;
	}
public static int getThreadRunning() {
		return GUI.threadRunning;
	}
private void doActionConfig(){
	settings.showConfig_Settings(this);
//new Config_Settings(this).setVisible(true);
}

public JLabel getjLabelUpdate() {
	return jLabelUpdate;
}
@Override
public void dispose() 
{
	setVisible(true);
	if(getThreadRunning()==1) //Check if download is in progress
	{
		int option=JOptionPane.showConfirmDialog(this,"Download is in progress\nAre you sure you want to quit", "Warning", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
		if(option==JOptionPane.YES_OPTION)
		{
			super.dispose();
			logging.log("Fatal error :: User Interrupted");
			System.exit(0);
		}
		else
		{
			setVisible(true);
		}
	}
	else
	{
		super.dispose();
		logging.log("Fatal error :: User Interrupted");
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
				doActionNotice(); // TODO Auto-generated Event stub actionPerformed()
			}
		});
	}
	return jMenuItem2;
}

private void doActionNotice()
{
	new Notice(this).setVisible(true);
}

}  //  @jve:decl-index=0:visual-constraint="53,35"
