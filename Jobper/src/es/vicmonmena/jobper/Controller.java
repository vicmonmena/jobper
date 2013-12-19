package es.vicmonmena.jobper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.net.CustomHttpConnection;
import es.vicmonmena.jobper.net.util.JsonParser;
import es.vicmonmena.jobper.util.Jobper;

/**
 * MVC pattern.
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
	 * Devuelve una lista de Jobs.
	 * @return lista de Jobs.
	 */
	public List<Job> loadJobs() {
		
		List<Job> jobs = null;
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.customGetRequest(Jobper.URI_JOBS);
			jobs = JsonParser.parseJobs(is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in searchAngelListUser");
		}
		
		return jobs;
	}
	
	/**
	 * Devuelve la información completa de un Jobs.
	 * @param id - identificador del job buscado.
	 * @return Jobs con el identificador encontrado.
	 */
	public Job loadJob(String id) {
		
		Job job = null;
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.customGetRequest(Jobper.URI_JOBS + "/" + id);
			job = JsonParser.parseJob(is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in searchAngelListUser");
		}
		
		return job;
	}
	
	/**
	 * Carga una imagen a partir de una URL
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
	 * Mark/unmark a job as favorite.
	 */
	public boolean markJobAsFavorite() {
		return true;
	}
	
	public void shareJob(Job job, Context context) {
    	Log.i(TAG, "Sharing job...");
    	String text = "";
        	
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.putExtra(Intent.EXTRA_TEXT, text);
    	intent.setType("text/plain");
		context.startActivity(Intent.createChooser(intent, context.getString(R.string.menu_share_job)));
		
	}
	
	/**
	 * Obtiene una fecha formateada a dd MMM yyyy de un String con una fecha con 
	 * formato yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String getDateFormat(String date) {
	    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	    DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
	    Date parsed = new Date();
	    try {
	        parsed = inputFormat.parse(date);
	    } catch (ParseException e) {
	      Log.e(TAG, "Error formating date...");
	    }
	    return outputFormat.format(parsed);
	}
}