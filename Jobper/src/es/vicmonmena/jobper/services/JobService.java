package es.vicmonmena.jobper.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.database.util.DBConstants;
import es.vicmonmena.jobper.model.Job;

/**
 * Servicio encargado de consultar actualizaciones de jobs favoritos.
 * @author vicmonmena
 *
 */
public class JobService extends IntentService{

	public static final int NOTIFICATION_REQUEST_CODE = 1;
	public static final String UPDATED_JOB = "es.vicmonmena.jobper.job.updated";
	public static final String DELETED_JOB = "es.vicmonmena.jobper.job.deleted";
	
	/**
	 * TAG para los mensajes de log de esta clase.
	 */
	private static final String TAG = "JobService";
	
	/**
	 * 
	 */
	public JobService() {
		super("JobService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "Cheking changes in favoritres...");
		Intent bIntent = new Intent(getApplicationContext(), JobAlarmReceiver.class);
		bIntent.setAction("es.vicmonmena.openuax.notify");
		
		Cursor cursor  = Controller.getInstance().loadFavoriteJobs(getApplication());
		Job job =  null;
		
		// Comprobamos si hay algún JOB de mis favoritos actualizado
		while (cursor.moveToNext()) {
			
			String jobId = cursor.getString(cursor.getColumnIndex(DBConstants.JOB_ID));
			String updatedAt = cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_AT));
			String title = cursor.getString(cursor.getColumnIndex(DBConstants.TITLE));
			
			job = Controller.getInstance().loadJob(jobId);
			
			if (job != null) {
				if (!job.getUpdateAt().equals(updatedAt)) {
					
					// Marca como favorito y actualiza los nuevos datos en BBDD
					job.setFavorite(true);
					Controller.getInstance().updateFavoriteJob(getApplicationContext(), job);
					
					// Datos del JOB para notificar al usuario
					bIntent.putExtra(UPDATED_JOB, job);
					break;
				}
			} else {
				// Ya no existe el job => Eliminamos de favoritos
				job = new Job();
				job.setJobId(jobId);
				job.setTitle(title);
				job.setFavorite(false);
				
				Controller.getInstance().markJobAsFavorite(
					getApplicationContext(), job);
				
				bIntent.putExtra(DELETED_JOB, job);
				break;
			}
		}
		cursor.close();
		sendBroadcast(bIntent);
	}
}
