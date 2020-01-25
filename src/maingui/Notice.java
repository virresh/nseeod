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
package maingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

public class Notice extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private GUI gui=null;
	private JTextArea jTextArea = null;
	private JScrollPane jScrollPane = null;
	/**
	 * This is the default constructor
	 */
	public Notice(GUI gui) {
		super();
		initialize();
		this.gui=gui;
		this.gui.setEnabled(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("Notice");
		this.setBounds(new Rectangle(0, 0, 514, 715));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(gui);
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
			jContentPane.setBackground(Color.white);
			jContentPane.add(getJScrollPane(), null);
		}
		return jContentPane;
	}
	@Override
	public void dispose() {
		this.gui.setEnabled(true);
		super.dispose();
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setText("POST YOUR REQUESTS:\n\nIf you want any feature to be added or improved please let me know at\n\nhttp://sourceforge.net/tracker/?atid=2048768&group_id=503741&func=browse\n\nand I will try my level best to include it in the next version. Even this version has some features suggested by the users and I am very thankful to them to help me improve this software.\n\nREPORT ANY BUG:\n\nIf you find a bug/issue with the software please let me know at\n\nhttp://sourceforge.net/tracker/?atid=2048768&group_id=503741&func=browse\n\nIf possible I will fix that bug/issue and release it on the very next day.\nVersion 1.1.1 was a bug fix of Version 1.1 and was fixed overnight after user reported an issue.\n\nRECOMMENDATIONS:\n\nI have given a lot of time and effort to this project. In return I am asking no money or donation. All I request you is that if you like this software please recommend me at\n\nhttp://sourceforge.net/projects/nse-eod/reviews/\n\nYou can also Hit the Facebook LIKE button at\n\nhttp://nse-eod-downloader.blogspot.com/\n\nIt will only take a minute to do so but will encourage me a lot to further develop this project\n\nVISIT:\n\nFollow me on\nhttp://nse-eod-downloader.blogspot.com/\nto know about day to day activities on the latest developement and other works on this software.\n\nKNOWLEDGE IS MEANT TO BE SHARED:\n\nShare this software with as many people as you can");
			jTextArea.setWrapStyleWord(true);
			jTextArea.setLineWrap(true);
			jTextArea.setLocation(new Point(0, 0));
			jTextArea.setSize(new Dimension(487, 900));
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setLocation(new Point(1, 1));
			jScrollPane.setSize(new Dimension(508, 688));
			jScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
