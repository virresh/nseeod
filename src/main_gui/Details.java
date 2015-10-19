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

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class Details extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextArea jTextArea1 = null;
	private JTextArea jTextArea2 = null;
	private JTextArea jTextArea3 = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton = null;  
	private JTextArea jTextArea = null;
	private GUI gui=null;
	/**
	 * This is the default constructor
	 */
	public Details(GUI gui) {
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
		this.setSize(429, 312);
		this.setContentPane(getJContentPane());
		this.setTitle("About");
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(gui);
		this.setPreferredSize(new Dimension(429, 312));
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
			jContentPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			//jContentPane.setBackground(SystemColor.inactiveCaptionText);
			jContentPane.setEnabled(true);
			jContentPane.add(getJTextArea1(), null);
			jContentPane.add(getJTextArea2(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.setBackground(Color.WHITE);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setBounds(new Rectangle(150, 49, 257, 148));
			jTextArea1.setText("This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.");
			jTextArea1.setEditable(false);
			jTextArea1.setLineWrap(true);
			jTextArea1.setFont(new Font("Tahoma", Font.PLAIN, 11));
			jTextArea1.setWrapStyleWord(true);
			//jTextArea1.setBackground(SystemColor.inactiveCaptionText);
		}
		return jTextArea1;
	}
	/**
	 * This method initializes jTextArea2	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea2() {
		if (jTextArea2 == null) {
			jTextArea2 = new JTextArea();
			jTextArea2.setBounds(new Rectangle(150, 15, 180, 32));
			jTextArea2.setText("NSE EOD Downloader version 2.0\nCreated By :: Rohit Jhunjhunwala");
			jTextArea2.setFont(new Font("Tahoma", Font.PLAIN, 11));
			jTextArea2.setEditable(false);
			//jTextArea2.setBackground(SystemColor.inactiveCaptionText);
		}
		return jTextArea2;
	}
	/**
	 * This method initializes jTextArea3	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea3() {
		if (jTextArea3 == null) {
			jTextArea3 = new JTextArea();
			//jTextArea3.setBackground(SystemColor.info);
			jTextArea3.setText(" For any issues please drop me an email at rohit6699@gmail.com\n Share this software with as many people as you can\n Knowledge is meant to be shared");
			jTextArea3.setFont(new Font("Tahoma", Font.PLAIN, 11));
			jTextArea3.setEditable(false);
		}
		return jTextArea3;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(14, 218, 401, 58));
			jScrollPane.setViewportView(getJTextArea3());
			jScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(14, 14, 124, 108));
			jButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButton.setPressedIcon(new ImageIcon(Details.class.getResource("/img/kelly-75.gif")));
			jButton.setDisabledIcon(new ImageIcon(Details.class.getResource("/img/kelly-75.gif")));
			jButton.setRolloverIcon(new ImageIcon(Details.class.getResource("/img/kelly-75.gif")));
			jButton.setEnabled(true);
			jButton.setIcon(new ImageIcon(Details.class.getResource("/img/kelly-75.gif")));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Desktop.getDesktop().browse(java.net.URI.create("http://nse-eod-downloader.blogspot.com"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // TODO Auto-generated Event stub actionPerformed()
				}
			});
			jButton.setFocusable(false);
		}
		return jButton;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(149, 197, 256, 18));
			jTextArea.setText("Copyright (C) 2011  Rohit Jhunjhunwala");
			jTextArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
			//jTextArea.setBackground(SystemColor.inactiveCaptionText);
		}
		return jTextArea;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.gui.setEnabled(true);
		super.dispose();
	}
}  //  @jve:decl-index=0:visual-constraint="99,38"
