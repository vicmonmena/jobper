package es.vicmonmena.jobper.services;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobAlarm {

	private Context context;
	
	public JobAlarm(Context context) {
		this.context = context;
	}
	
	public void startAlarm() {
		
		Intent bIntent = new Intent(context, JobAlarmReceiver.class);
		PendingIntent pIntent = PendingIntent.getBroadcast(
			context, 0, bIntent, PendingIntent.FLAG_CANCEL_CURRENT); 
		
		Calendar cal = Calendar.getInstance();
		
		int interval = 1000*60;
		
		AlarmManager alarm = (AlarmManager) 
			context.getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + interval, 
			interval, pIntent);
	}
	
	public void startAlarmService() {
		
		Intent serviceIntent = new Intent(context, JobService.class);
		PendingIntent intent = PendingIntent.getService(
			context, 0, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT); 
		
		Calendar cal = Calendar.getInstance();
		int interval = 1000*60;
		
		AlarmManager alarm = (AlarmManager) 
			context.getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + interval, 
			interval, intent);
	}
}
