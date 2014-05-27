/*
 * Marc Clelland S1113808
 */
package com.gcu.coursework;

public class WidgetChannel 
{
	private String hour;
	private String time;
	private String title;
	private String desc;
	private String link;
	
	//Sets all variables to default.
	public WidgetChannel()
	{
		setTime("");
		title = "";
		desc = "";
		link = "";
		hour = "";
	}
	
	//Gets all information from what ever is calling the class.
	public WidgetChannel(String time, String endTime, String title, String link, String desc, String hour)
	{
		this.setTime(time);
		this.title = title;
		this.desc = desc;
		this.link = link;
		this.hour = hour;
	}
	
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getLink() 
	{
		return link;
	}

	public void setLink(String link) 
	{
		this.link = link;
	}
	
	//Used to output all the information that is collected by setters & getters.
	public String toString()
	{
		String temp;
		
		temp = getTime() + System.getProperty("line.separator") + getTitle() + System.getProperty("line.separator") + getDesc() ; // + getLink() + "\n";
		
		return temp;
		
	}

	public String getDesc() 
	{
		return desc;
	}

	public void setDesc(String desc) 
	{
		this.desc = desc;
	}

	public String getTime() 
	{
		return time;
	}

	public void setTime(String time) 
	{
		this.time = time;
	}

	public String getHour() 
	{
		return hour;
	}

	public void setHour(String hour) 
	{
		this.hour = hour;
	}
}
