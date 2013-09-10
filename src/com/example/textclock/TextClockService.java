package com.example.textclock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class TextClockService extends IntentService{

	// Fields -----------------------------------------------------------------
	private static final SimpleDateFormat dateFormat = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final String TAG = "TextClockService";
	
	public static final String ACTION_UPDATE = 
			"com.example.textclock.ACTION_UPDATE";
	
	// Constructor ------------------------------------------------------------
	public TextClockService(){
		super(TAG);
	}
	
	// Life Cycle Callback
	@Override
	protected void onHandleIntent(Intent intent){
		if (intent.getAction().equals(ACTION_UPDATE)){
			Calendar now = Calendar.getInstance();
			updateTime(now);
		}
	}
	private void updateTime(Calendar date){
		Log.d(TAG, "update: " + dateFormat.format(date.getTime()));
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName name = new ComponentName(this, TextClockAppWidget.class);
		int[] appIds = manager.getAppWidgetIds(name);
		String[] words = TimeToWords.timeToWords(date);
		for (int id : appIds){
			RemoteViews view = new RemoteViews(
					getPackageName(),
					R.layout.appwidget);
			updateTime(words, view);
			manager.updateAppWidget(id, view);
		}
	}
	private void updateTime(String[] words, RemoteViews view){
		view.setTextViewText(R.id.hours, words[0]);
		if (words.length == 1){
			view.setViewVisibility(R.id.minutes, View.INVISIBLE);
			view.setViewVisibility(R.id.tens, View.INVISIBLE);
		}else if (words.length == 2){
			view.setViewVisibility(R.id.minutes, View.INVISIBLE);
			view.setViewVisibility(R.id.tens, View.VISIBLE);
			view.setTextViewText(R.id.tens, words[1]);
		}else{
			view.setViewVisibility(R.id.minutes, View.VISIBLE);
			view.setViewVisibility(R.id.tens, View.VISIBLE);
			view.setTextViewText(R.id.tens, words[1]);
			view.setTextViewText(R.id.minutes, words[2]);
		}
	}
}
