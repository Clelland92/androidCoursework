/*
 * Marc Clelland S1113808
 */

package com.gcu.coursework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener , Runnable    
{
	private Spinner tvChannelSelector;
	private Spinner daySelector;
	public static String URL = "http://bleb.org/tv/data/rss.php?ch=bbc1_scotland&day=0"; 
	private ListView lv;
	private boolean running;
	private Thread thread;
	private Calendar c = Calendar.getInstance();
	private String hour = c.get(Calendar.HOUR_OF_DAY) + "";
	private LinkedList<WidgetChannel> aList;
	private long time;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		//Time variable to get the time to display the correct programme at a certain time
		time = System.currentTimeMillis();
		
		//Spinner and array adapter to select the channel you want displayed 
		tvChannelSelector = (Spinner)findViewById(R.id.tvChannelSelector);
		ArrayAdapter myAdapter = 
				ArrayAdapter.createFromResource(this, R.array.ChannelSelector, android.R.layout.simple_spinner_item);
				myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tvChannelSelector.setAdapter(myAdapter);
			tvChannelSelector.setOnItemSelectedListener(this);
			
		//Spinner and array adapter to select the day you want displayed
		daySelector = (Spinner)findViewById(R.id.daySelector);
		ArrayAdapter arrayAdapter = 
				ArrayAdapter.createFromResource(this, R.array.DaySelect, android.R.layout.simple_spinner_item);
				arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			daySelector.setAdapter(arrayAdapter);
			daySelector.setOnItemSelectedListener(this);
			
		//This is a string concatenation method to create a URL based on what channel and day are selected from both spinners  
		URL = "http://bleb.org/tv/data/rss.php?ch=" + tvChannelSelector.getSelectedItem().toString() + "&day=" + daySelector.getSelectedItemPosition();
		//This instantiates the list view for the channels 
		lv = (ListView) findViewById(R.id.lvTest);
		String result = " ";
		try 
		{
			result = tvListingString(URL);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		XmlParser myXMLParser = new XmlParser();
		aList = myXMLParser.parseWidgetXMLString(result);
		ArrayList<String> channels = new ArrayList<String>();
		for(int i = 0; i < aList.size(); i++){
			channels.add(aList.get(i).toString()); 
		}
		
		if(!channels.isEmpty())
			lv.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, channels));
		
		for(int i = 0; i < aList.size(); i++){
			if(hour.contains(aList.get(i).getHour())){
				lv.setSelection(i);
			}
		}
		
	}

	protected void onPause() 
	{
		super.onPause();
		
		running = false;

		try 
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		thread = null;
	}
	
	protected void onResume() 
	{
		super.onResume();
		
		running = true;

		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private static String tvListingString(String urlString) throws IOException 
	{
		String result = "";
		InputStream anInStream = null;
		int response = -1;
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not a HTTP connection");
		try 
		{
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) 
			{
				anInStream = httpConn.getInputStream();
				InputStreamReader in = new InputStreamReader(anInStream);
				BufferedReader bin = new BufferedReader(in);
				// result = bin.readLine();
				// Input is over multiple lines
				String line = new String();
				while (((line = bin.readLine())) != null) 
				{
					result = result + line;
				}
			}
		} 
		catch (Exception ex) 
		{
			throw new IOException("Error connecting");
		}
		return result;
	}

	@Override
	
	public void onClick(View theView)
	{

	}
	
	
	//This code is run when an item from either of the spinners has been selected and will then parse the url again and display the new items in the list 
	public void onItemSelected(AdapterView<?> parent,View theView,int pos, long id)
	{
				URL = "http://bleb.org/tv/data/rss.php?ch=" + tvChannelSelector.getSelectedItem().toString() + "&day=" + daySelector.getSelectedItemPosition();
				//This instantiates the list view for the channels 
				lv = (ListView) findViewById(R.id.lvTest);
				String result = " ";
				try 
				{
					result = tvListingString(URL);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				XmlParser myXMLParser = new XmlParser();
				aList = myXMLParser.parseWidgetXMLString(result);
				ArrayList<String> channels = new ArrayList<String>();
				for(int i = 0; i < aList.size(); i++){
					channels.add(aList.get(i).toString());
				}
				
				if(!channels.isEmpty())
					lv.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, channels));
				
				for(int i = 0; i < aList.size(); i++){
					if(hour.contains(aList.get(i).getHour())){
						lv.setSelection(i);
					}
				}	
	}
	
	public void onNothingSelected(AdapterView<?> parent)
	{
		
	}

	public void run() 
	{
		
		while(running)
		{
			//Updates every 5 mins.
			if (System.currentTimeMillis() - time > 300000) 
			{
				time += 300000;
				//This is where the time is threaded so that it will update after a given amount of time.
				this.runOnUiThread(new Runnable() 
				{
					public void run() 
					{
						for(int i = 0; i < aList.size(); i++)
						{
							if(hour.contains(aList.get(i).getHour()))
							{
								lv.setSelection(i);
							}
						}
					}
				});
			}
		}
	}
	
}
