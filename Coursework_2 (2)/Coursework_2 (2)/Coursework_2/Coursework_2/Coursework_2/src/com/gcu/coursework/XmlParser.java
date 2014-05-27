/*
 * Marc Clelland S1113808  
 */
package com.gcu.coursework;

import java.io.StringReader;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XmlParser 
{
	
	 private XMLReader initializeReader() throws ParserConfigurationException, SAXException 
	 {
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        // create a parser
	        SAXParser parser = factory.newSAXParser();
	        // create the reader (scanner)
	        XMLReader xmlreader = parser.getXMLReader();
	        return xmlreader;
	    }
	    
	    public LinkedList<WidgetChannel> parseWidgetXMLString(String xml) 
	    {
	        try 
	        {
	            XMLReader xmlreader = initializeReader();
	            
	            WidgetHandlerCollection aWidgetHandler = new WidgetHandlerCollection();

	            // assign our handler
	            xmlreader.setContentHandler(aWidgetHandler);
	            // perform the synchronous parse
	            xmlreader.parse(new InputSource(new StringReader(xml)));
	            
	         
	            return aWidgetHandler.retrieveWidgets();
	            
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	            return null;
	        }
	        
	    }

}
