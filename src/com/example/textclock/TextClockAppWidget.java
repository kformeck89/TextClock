package com.example.textclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TextClockAppWidget extends AppWidgetProvider {
	
	// Fields -----------------------------------------------------------------
	private static final String TAG = "TextClockWidget";
	private static final Intent update = 
			new Intent(TextClockService.ACTION_UPDATE);
	private Context context;
	
	// Life Cycle Callbacks
	@Override
	public void onUpdate(
			Context context, 
			AppWidgetManager appWidgetManager,
			int[] appWidgetIds){
		Log.d(TAG, "onUpdate");
		this.context = context;
		this.context.startService(update);
	}
}
