package es.vicmonmena.jobper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
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
	 * Devuelve una lista de Jobs.
	 * @return lista de Jobs.
	 */
	public List<Job> loadJobs() {
		
		List<Job> jobs = null;
		
		InputStream is =  null;
		try {
			is = CustomHttpConnection.getJobs(Jobper.URI_JOBS);
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
			is = CustomHttpConnection.getJobs(Jobper.URI_JOBS + "/" + id);
			job = JsonParser.parseJob(is);
		} catch (IOException e) {
			Log.e(TAG, "Exception in searchAngelListUser");
		}
		
		return job;
	}
	
	/**
	 * Load an image.
	 */
	public void loadImage() {
		
	}
	
	/**
	 * Mark/unmark a job as favorite.
	 */
	public boolean markJobAsFavorite() {
		return true;
	}
}