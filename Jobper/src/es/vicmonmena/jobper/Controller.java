package es.vicmonmena.jobper;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
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
	private static final String TAG = "CustomHttpConnection";

	/**
	 * Controller class instace. Singleton pattern.
	 */
	private static Controller controller;
	
	/**
	 * 
	 */
	private Context context;
	
	/**
	 * Controller class constructor. Singleton patter.
	 */
	private Controller() {
		// TODO Auto-generated constructor stub
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
	 * Load a list of jobs
	 */
	public void loadJobs() {
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.getJobs(Jobper.URI_JOBS);
			
			JsonParser.parseJobs(is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in searchAngelListUser");
		}
	}
	
	
	/**
	 * Load an image.
	 */
	public void loadImage() {
		
	}
	
	/**
	 * Load Job details.
	 */
	public void loadJobDetails() {
		
	}
	
	/**
	 * Mark/unmark a job as favorite.
	 */
	public void markJobAsFavorite() {
		// Service
	}
}