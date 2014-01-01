package es.vicmonmena.jobper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import es.vicmonmena.jobper.database.JobProvider;
import es.vicmonmena.jobper.database.util.DBConstants;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.model.Startup;
import es.vicmonmena.jobper.net.CustomHttpConnection;
import es.vicmonmena.jobper.net.util.JsonParser;
import es.vicmonmena.jobper.net.util.NETConstants;
import es.vicmonmena.jobper.services.JobAlarm;
import es.vicmonmena.jobper.ui.JobperPreferences;

/**
 * Clase controlador del Patrón de diseño MVC.
 * 
 * @author vicmonmena
 *
 */
public class Controller {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "Controller";
	/**
	 * Controller class instace. Singleton pattern.
	 */
	private static Controller controller;
	/**
	 * Alrma para notificaciones.
	 */
	private JobAlarm jAlarm;
	/**
	 * Controller class constructor. Singleton patter.
	 */
	private Controller() {
	}
	
	/**
	 * Get the Controller class instance. Singleton pattern.
	 * @return
	 */
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	/**
	 * Devuelve una lista de Jobs del servicio en Internet.
	 * @return lista de Jobs.
	 */
	public List<Job> loadJobs(Context context, AsyncTask task) {
		
		List<Job> jobs = null;
		
		InputStream is =  null;
		try {
			SharedPreferences sharedPref = PreferenceManager
					.getDefaultSharedPreferences(context);
				int jobsPerPage = Integer.parseInt(sharedPref.getString(
					JobperPreferences.JOBS_PER_PAGE_KEY, "50"));
			is = CustomHttpConnection.customGetRequest(NETConstants.URI_JOBS + 
				"?" + NETConstants.PARAM_PER_PAGE + jobsPerPage);
			jobs = JsonParser.parseJobs(task, is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in loadJobs");
		}
		
		return jobs;
	}
	
	/**
	 * Devuelve una lista de Jobs del servicio en Internet.
	 * @return lista de Jobs.
	 */
	public Job loadJob(String jobId) {
		
		Job job = null;
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.customGetRequest(NETConstants.URI_JOBS + "/" + jobId);
			job = JsonParser.parseJob(is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in loadJob");
		}
		
		return job;
	}
	
	/**
	 * Devuelve la información de una Startup asociada a un Job.
	 * @param id - identificador del job buscado.
	 * @return Jobs con el identificador encontrado.
	 */
	public Startup loadStartup(AsyncTask task, String id) {
		
		Startup startup = null;
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.customGetRequest(NETConstants.URI_JOBS + "/" + id);
			startup = JsonParser.parseStartup(task, is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in loadStartup");
		}
		
		return startup;
	}
	
	/**
	 * Carga una imagen a partir de una URL 
	 * @param uri
	 * @return
	 */
	public Drawable loadImage(String uri) {	
		Drawable image = null;
		try {
            InputStream is = CustomHttpConnection.customGetRequest(uri);
            if (is != null) {
                   image = Drawable.createFromStream(is, "image");
            }
		} catch (IOException e) {
			Log.e(TAG, "Exception loading image...");
		}
		
		return image;
	}
	
	/**
	 * Devuelve una lista de Jobs almacenados en BBDD, marcados como favoritos.
	 * @return lista de Jobs.
	 */
	public Cursor loadFavoriteJobs(Context context) {
		
		ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(JobProvider.CONTENT_URI, 
        		DBConstants.PROJECTION, null, null, null);
		
		return c;
	}
	
	/**
	 * Marca/desmarca un empleo como favorito.
	 * @param context
	 * @param job
	 * @return true if is marked as favorite, false if is unmarked as famorite.
	 */
	public boolean markJobAsFavorite(Context context, Job job) {
		boolean markedAs = false;
		ContentResolver cr = context.getContentResolver();
		
		if (!job.isFavorite()) {
			ContentValues values = new ContentValues(1);
			
			values.put(DBConstants.JOB_ID, job.getJobId());
			values.put(DBConstants.TITLE, job.getTitle());
			values.put(DBConstants.UPDATE_AT, job.getUpdateAt());
			values.put(DBConstants.SALARY_MIN, job.getSalaryMin());
			values.put(DBConstants.SALARY_MAX, job.getSalaryMax());
			values.put(DBConstants.LOCATION, job.getLocation());
			
			if (cr.insert(JobProvider.CONTENT_URI, values) != null) {
				markedAs = true;
			}
		} else {
			int result = cr.delete(JobProvider.CONTENT_URI,
					DBConstants.JOB_ID + "=" + job.getJobId(), null);
			if (result > 0) {
				markedAs = false;
			}
		}
		return markedAs;
	}
	
	/**
	 * Marca/desmarca un empleo como favorito.
	 * @param context
	 * @param job
	 * @return true if is marked as favorite, false if is unmarked as famorite.
	 */
	public boolean updateFavoriteJob(Context context, Job job) {
		boolean markedAs = false;
		ContentResolver cr = context.getContentResolver();
		
		if (!job.isFavorite()) {
			ContentValues values = new ContentValues(1);
			
			values.put(DBConstants.JOB_ID, job.getJobId());
			values.put(DBConstants.TITLE, job.getTitle());
			values.put(DBConstants.UPDATE_AT, job.getUpdateAt());
			values.put(DBConstants.SALARY_MIN, job.getSalaryMin());
			values.put(DBConstants.SALARY_MAX, job.getSalaryMax());
			values.put(DBConstants.LOCATION, job.getLocation());
			
			cr.update(JobProvider.CONTENT_URI, values, 
					DBConstants.JOB_ID + " = " + DBConstants.JOB_ID, null);
		}
		
		return markedAs;
	}
	
	public boolean checkJobIsFavorite(Context context, String jobId) {
		ContentResolver cr = context.getContentResolver();
		Uri uri = Uri.parse(JobProvider.CONTENT_URI + "/" + jobId);
		return cr.query(uri, DBConstants.PROJECTION_SIMPLE, 
	        	null, null, null).getCount() == 1;
	}
	/**
	 * Comparte un Job en redes socialesemail,..
	 * @param job
	 * @param context
	 */
	public void shareJob(Context context, Job job) {
    	Log.d(TAG, "Sharing job...");
    	StringBuilder text = new StringBuilder();
    	text.append(context.getString(R.string.msg_new_job_offer));
    	text.append(": ");
        text.append(job.getTitle());
        text.append(" (");
        text.append(job.getLocation());
        text.append(") ");
        text.append(context.getString(R.string.msg_from_jobper));
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.putExtra(Intent.EXTRA_TEXT, text.toString());
    	intent.setType("text/plain");
		context.startActivity(Intent.createChooser(intent, context.getString(R.string.menu_share_job)));
	}
	
	/**
	 * Obtiene una fecha formateada a dd MMM yyyy de un String con una fecha con 
	 * formato yyyy-MM-ddTHH:mm:ssZ
	 * @param date
	 * @return
	 */
	public static String getDateFormat(String date) {
	    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    DateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
	    Date parsed = new Date();
	    try {
	        parsed = inputFormat.parse(date);
	    } catch (ParseException e) {
	      Log.e(TAG, "Error formating date...");
	    }
	    return outputFormat.format(parsed);
	}
	
	/**
	 * Obtiene una objeto Date de un String con una fecha con formato 
	 * yyyy-MM-ddTHH:mm:ssZ
	 * @param date
	 * @return
	 */
	public static Date getDateFromString(String dateString) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			date = format.parse(dateString);  
		} catch (ParseException e) {  
			Log.e(TAG, "Error formating date...");
		}
	    return date;
	}
	
	/**
	 * Comprueba si una fecha es distinta a otra
	 * @param dateIn
	 * @param dateOut
	 * @return
	 */
	public static boolean compareDateChanges(Date dateIn, Date dateOut) {
		return false;
	}
	
	/**
	 * Crea una alarm
	 * @param context
	 */
	public void setAlarm(Context context, int interval) {
		Log.d(TAG, "Setting alarm: " + interval + " seconds");
		if (jAlarm == null) {
			jAlarm = new JobAlarm(context);
			jAlarm.startAlarmService(interval);
		}
	}
	
	/**
	 * Crea una alarm
	 * @param context
	 */
	public void resetAlarm(Context context, int interval) {
		Log.d(TAG, "Setting alarm: " + interval + " seconds");
		jAlarm = new JobAlarm(context);
		jAlarm.startAlarmService(interval);
	}
}