package es.vicmonmena.jobper.services;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.ui.DetailsActivity;
import es.vicmonmena.jobper.ui.MainActivity;
import es.vicmonmena.jobper.ui.components.JobDetailsFragment;

/**
 * Receiver de las ejecuciones del servicio.
 * @author vicmonmena
 *
 */
public class JobAlarmReceiver extends BroadcastReceiver{

	/**
	 * TAG para los mensajes de log de esta clase.
	 */
	private static final String TAG = "JobAlarmReceiver";
	
	private static int NOTIFICATION_ID = 1;
	@Override
	public void onReceive(Context context, Intent intent) {
		Notification.Builder builder = new Builder(context);
		builder.setSmallIcon(R.drawable.ic_launcher);
		
		Job job = null;
		
		if (intent.getParcelableExtra(JobService.UPDATED_JOB) != null) {
			builder.setTicker(context.getString(R.string.notification_update));
			job = intent.getParcelableExtra(JobService.UPDATED_JOB);
			builder.setContentInfo(job.getTitle());
			
			Intent sIntent = new Intent(context, DetailsActivity.class);
			sIntent.putExtra(JobDetailsFragment.JOB, job);
			PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, sIntent, 0);
			builder.setContentIntent(pIntent);
			
		} else if (intent.getParcelableExtra(JobService.DELETED_JOB) != null) {
			builder.setTicker(context.getString(R.string.notification_delete));
			job = intent.getParcelableExtra(JobService.DELETED_JOB);
			builder.setContentInfo(job.getTitle());
		} else {
			builder.setTicker(context.getString(R.string.notification_no_changes));
			builder.setContentInfo(context
					.getString(R.string.notification_no_changes_content));
			
			Intent sIntent = new Intent(context, MainActivity.class);
			sIntent.putExtra(MainActivity.CURRENT_TAB_INDEX, 1);
			PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, sIntent, 0);
			builder.setContentIntent(pIntent);
		}
		
		builder.setLights(Color.parseColor("#c27ba0"), 0, 1);
		builder.setAutoCancel(true);
		Notification notif = builder.getNotification();
		notif.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationMng = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationMng.notify(NOTIFICATION_ID, notif);
	}
}
