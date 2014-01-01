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

	/**
	 * 
	 */
	private Context context;
	
	/**
	 * 
	 * @param context
	 */
	public JobAlarm(Context context) {
		this.context = context;
	}
	
	/**
	 * Crea e inicializa una alarma que activará el servicio a diario.
	 */
	public void startAlarmService(int interval) {
		
		Intent serviceIntent = new Intent(context, JobService.class);
		PendingIntent intent = PendingIntent.getService(
			context, 0, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT); 
		
		Calendar cal = Calendar.getInstance();
		interval *= 1000;
		
		AlarmManager alarm = (AlarmManager) 
			context.getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + interval, 
			interval, intent);
	}
}
