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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Marquee implements ActionListener {

    private final Timer timer = new Timer(100, this);
    private final JLabel label;
    private String displayString;
    private final int paddingCount;
    private int index;
    
    public Marquee(JLabel label, int paddingCount, String message) {
    	if(label ==null)
    		throw new IllegalArgumentException("Label cannot be null");
    	this.label=label;
    	displayString=message;
        if (displayString == null) {
            throw new IllegalArgumentException("Enter a valid message to display");
        }
        if(paddingCount<=0)
        	paddingCount=1;
        StringBuilder sb = new StringBuilder(paddingCount);
        for (int i = 0; i < paddingCount; i++) {
            sb.append(' ');
        }
        //Create padding
        displayString = sb + displayString + sb;
        this.paddingCount = paddingCount;
    }

    public void start() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index++;
        if (index > displayString.length() - paddingCount) {
            index = 0;
        }
        label.setText(displayString.substring(index, index + paddingCount));
    }
}