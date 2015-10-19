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
package config;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import logging.logging;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Actions {
	static List <JCheckBox> indexCheckboxes=new ArrayList<JCheckBox>();
	static List <JCheckBox> equityCheckboxes=new ArrayList<JCheckBox>();
	static List <JCheckBox> futuresCheckboxes=new ArrayList<JCheckBox>();
	static List <JCheckBox> currency_futuresCheckboxes=new ArrayList<JCheckBox>();
	static List <JCheckBox> optionsCheckboxes=new ArrayList<JCheckBox>();
	static List <JCheckBox> checkBoxes=new ArrayList<JCheckBox>();
	void writeConfigFile(JComponent jTabbedPane) 
	{	
		FileWriter XMLfile;
		try {
		XMLfile = new FileWriter(System.getProperty("user.dir")+"/config.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder;
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    Document document = documentBuilder.newDocument();
	    Element rootElement = document.createElement("settings");
	    document.appendChild(rootElement);
	    createXML(jTabbedPane,rootElement,document);
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(document);
	    StreamResult outputTarget =  new StreamResult(XMLfile);
	    transformer.transform(source, outputTarget);
	    XMLfile.close();
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Unable to create File");
			e1.printStackTrace();
		}
	}
	private void createXML(JComponent jComponent,Node root,Document document) {
		// TODO Auto-generated method stub
		int count=jComponent.getComponentCount();
		for(int i=0;i<count;i++)
		{
			if(jComponent.getComponent(i).getClass().getSimpleName().equals("JPanel"))
			{
				Element em=document.createElement(((JPanel)jComponent.getComponent(i)).getName());
				createXML((JComponent)jComponent.getComponent(i), em, document);
				root.appendChild(em);
			}
			if(jComponent.getComponent(i).getClass().getSimpleName().equals("JCheckBox"))
			{
				Element em=document.createElement("checkBox");
				Element em1=document.createElement("name");
				em1.appendChild(document.createTextNode(((JCheckBox)jComponent.getComponent(i)).getText()));
				em.appendChild(em1);//append name to checkbox
				em1=document.createElement("value");
				em1.appendChild(document.createTextNode(Boolean.toString(((JCheckBox)jComponent.getComponent(i)).isSelected())));
				em.appendChild(em1);//append value to checkbox
				root.appendChild(em);//append the checkbox to its root element
			}
			if(jComponent.getComponent(i).getClass().getSimpleName().equals("JTabbedPane"))
			{
				createXML((JComponent)jComponent.getComponent(i), root, document);
			}
		}
	}
	void readConfigFile(JPanel index,JPanel equity,JPanel futures,JPanel curr_futures,JPanel options,JPanel others)
	{try{
		File xml=new File(System.getProperty("user.dir")+"/config.xml");
		DocumentBuilderFactory xmlFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlBuilder=xmlFactory.newDocumentBuilder();
		Document xmlDocument=xmlBuilder.parse(xml);
		xmlDocument.normalize();
		Element rootSettings=(Element)xmlDocument.getFirstChild();
		Element download=(Element)rootSettings.getElementsByTagName("download").item(0);
		readDownloadCheckboxSettings(download,"index",index);//FOR INDEX
		readDownloadCheckboxSettings(download,"equity",equity);//FOR EQUITIES
		readDownloadCheckboxSettings(download,"futures",futures);//FOR Futures 
		readDownloadCheckboxSettings(download,"currency_futures",curr_futures);//FOR Currence Futures
		readDownloadCheckboxSettings(download,"options",options);//FOR Options
		readDownloadCheckboxSettings(rootSettings,"others",others);//FOR Others
	}
	catch(Exception e){
		logging.log("Fatal Error while reading Config file");
	}
	}
	void readDownloadCheckboxSettings(Element download,String tagname,JPanel jPanel)
	{
		Element indices=(Element)download.getElementsByTagName(tagname).item(0);
		NodeList indices_subnodes=indices.getElementsByTagName("checkBox");
		JCheckBox chkbox=null;
		//Loop through the checkbox elements
		for (int i=0;i<indices_subnodes.getLength();i++)
		{
			Node chkboxnode=indices_subnodes.item(i);
			//Loop through the inner elements of a checkbox element
			for(int j=0;j<chkboxnode.getChildNodes().getLength();j++)
			{
				Node chkboxnode_subnode=chkboxnode.getChildNodes().item(j);
				if(chkboxnode_subnode.getNodeType()!=Node.ELEMENT_NODE)
					continue;
				else
				{
					if(chkboxnode_subnode.getNodeName().equals("name"))
					{
				        chkbox=new JCheckBox();		
						chkbox.setText(chkboxnode_subnode.getFirstChild().getNodeValue());//set the text of the checkbox
					}
					if(chkboxnode_subnode.getNodeName().equals("value"))
					{
						//System.out.println(Boolean.parseBoolean( chkboxnode_subnode.getFirstChild().getNodeValue()));
						chkbox.setSelected(Boolean.parseBoolean( chkboxnode_subnode.getFirstChild().getNodeValue()));//set the selected boolean value of the checkbox
						checkBoxes.add(chkbox);
						
					}
				}
			}//End of inner for loop
		}
		//Read from the List and then store it
		int count=jPanel.getComponentCount();
		for (Iterator<JCheckBox> iterator = checkBoxes.iterator(); iterator.hasNext();) 
		{
			JCheckBox readCheckBox=(JCheckBox)iterator.next(); //check box read from XML
			//Scroll through components inside JPanel
			for(int i=0;i<count;i++)
			{
				//Check if the component is JCheckbox 
				if(jPanel.getComponent(i).getClass().getSimpleName().equals("JCheckBox"))
				{
					JCheckBox temp=(JCheckBox)jPanel.getComponent(i);
					//Check is the check box text read from xml matches with the check box text of the application
					if(temp.getText().equalsIgnoreCase(readCheckBox.getText()))
					{
						temp.setSelected(readCheckBox.isSelected());
					}
				}
				
			}//End of inner for loop
		}//End of outer for loop
	}

}