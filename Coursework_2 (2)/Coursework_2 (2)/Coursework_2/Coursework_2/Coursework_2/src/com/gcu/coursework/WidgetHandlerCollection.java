/*
 * Marc Clelland S1113808
 */
package com.gcu.coursework;

import java.util.Calendar;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WidgetHandlerCollection extends DefaultHandler 
{

	private StringBuffer buffer = new StringBuffer();
	private LinkedList<WidgetChannel> alist;
	private WidgetChannel aWidget;
	private boolean parsedWidget = false;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException 
	{
		buffer.setLength(0);

		//
		// Create a new widget collection to store the widgets in
		//
		if (qName.equalsIgnoreCase("rss") || localName.equalsIgnoreCase("rss")) 
		{
			alist = new LinkedList<WidgetChannel>();
		}

		// used to find tags channel & item these are the main tags that we will
		// be retrieving data from and this is why
		// we set a new widget channel in here.
		if (qName.equalsIgnoreCase("channel") || localName.equalsIgnoreCase("channel") || qName.equalsIgnoreCase("item") || localName.equalsIgnoreCase("item")) 
		{
			aWidget = new WidgetChannel();
		} else {
			// Log.v("No widget!", "No widget created and local names are " +
			// localName + " " + qName);
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException 
	{
		if (parsedWidget == false) 
		{
			if (qName.equals("title") || localName.equals("title")) 
			{
				// Gets the start time from collecting the first 4 digits of the tag.
				String time = buffer.toString();
				String result = "";
				int count = 0;
				for (int t = 0; t < 4; t++) 
				{
					count++;
					if (count == 3) 
					{
						aWidget.setHour(result);
						
						result = result + ":";
						t--;
					} 
					else
						result = result + time.charAt(t);
				}
				
				aWidget.setTime(result);

				// This gets the rest of the digits that have to been collected
				// for time.
				String resultString = buffer.toString();
				String title = "";
				for (int i = 7; i < resultString.toCharArray().length; i++) 
				{
					title = title + resultString.charAt(i);
				}
				aWidget.setTitle(title);

				// Log.v("buffer title", "buffer is " + buffer.toString());
			} 
			else if (qName.equals("description") || localName.equals("description")) 
			{
				// Add description to widget.
				aWidget.setDesc(buffer.toString());
				// Log.v("buffer item", "buffer is " + buffer.toString());
			} 
			else if (qName.equals("link") || localName.equals("link")) 
			{
				// Add link to widget.
				aWidget.setLink(buffer.toString());
				parsedWidget = true;
				// Log.v("buffer link", "buffer is " + buffer.toString());
			}
		}

		// At the end of the widget definition, So add widget to the collection
		if (qName.equals("channel") || qName.equals("item")) 
		{
			parsedWidget = false;
			alist.add(aWidget);
		}
	}

	public void characters(char[] ch, int start, int length) 
	{
		buffer.append(ch, start, length);
		// Log.v("Char", "Chars found are " + buffer);
	}

	// gets all information from list that have been added from the widget.
	public LinkedList<WidgetChannel> retrieveWidgets() 
	{
		return alist;
	}

}
