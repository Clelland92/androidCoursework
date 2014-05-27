/*
 * Marc Clelland S1113808
 */
package com.gcu.coursework;

import com.gcu.coursework.MainActivity;
import com.gcu.coursework.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends Activity{

	// Context that will be used in the dialog box method
	private	final Context context = this;
	//Buttons that will be used in the app
	private Button channels;
	private Button exit;  
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		channels = (Button) findViewById(R.id.channels);
		exit = (Button)findViewById(R.id.exit);
		
		//Button that will take you to the list of programmes and channels 
		this.channels.setOnClickListener(new View.OnClickListener() 
		{
			
			public void onClick(View v) 
			{
				Class class_0;
				try 
				{
					class_0 = Class.forName("com.gcu.coursework.MainActivity");
					Intent ourIntent = new Intent(menu.this, class_0);
					startActivity(ourIntent);
				} 
				catch (ClassNotFoundException e) 
				{ 
					e.printStackTrace();
				}
				
			}
		});
		
		
		
		//Exit button code that allows the user to decide to leave the app or continue using it 
		exit.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				 
				// set title
				alertDialogBuilder.setTitle("Exit");
	 
				// set dialog message 
				alertDialogBuilder
					.setMessage("Are you sure you would like to Exit?" )
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() 
					{
					public void onClick(DialogInterface dialog,int id) 
					{
							// if this button is clicked, close
							// current activity
							menu.this.finish();
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog,int id) 
						{
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			}
		});
	}
}
