package es.vicmonmena.jobper.services;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
	private static final String TAG = "JobService";
	
	private static int NOTIFICATION_ID = 1;
	@Override
	public void onReceive(Context context, Intent intent) {
		Notification.Builder builder = new Builder(context);
		builder.setSmallIcon(R.drawable.ic_launcher);
		
		String ticker = "";
		Job job = null;
		
		if (intent.getParcelableExtra(JobService.UPDATED_JOB) != null) {
			ticker = context.getString(R.string.notification_update);
			job = intent.getParcelableExtra(JobService.UPDATED_JOB);
			
			Intent sIntent = new Intent(context, DetailsActivity.class);
			sIntent.putExtra(JobDetailsFragment.JOB, job);
			PendingIntent pIntent = PendingIntent
				.getActivity(context, 0, sIntent, 0);
			builder.setContentIntent(pIntent);
			
			Log.i(TAG, "JOB UPDATED: " + job.getJobId());
			
		} else if (intent.getParcelableExtra(JobService.DELETED_JOB) != null) {
			ticker = context.getString(R.string.notification_delete);
			job = intent.getParcelableExtra(JobService.DELETED_JOB);
			
			Log.i(TAG, "JOB DELETED: " + job.getJobId());
		}
		if (job != null) {
			builder.setTicker(ticker);
			builder.setContentInfo(job.getTitle());
			builder.setAutoCancel(true);
			
			NotificationManager notificationMng = (NotificationManager)
					context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationMng.notify(NOTIFICATION_ID, builder.getNotification());
		}
	}
}
