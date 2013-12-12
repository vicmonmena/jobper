package es.vicmonmena.jobper.util;

/**
 * Iterfaz con valores constantes.
 * 
 * @author vicmonmena
 *
 */
public interface Jobper {
	
	// Net
	
	/**
	 * Servicio donde se localizan ls Jobs.
	 */
	public final String URI_JOBS = "https://api.angel.co/1/jobs";
	/**
	 * Servicio donde se localizan las startups.
	 */
	public final String URI_STARTUP = "https://api.angel.co/1/startups/";
	
	// Database
	
	/**
	 * Nombre de la base de datos.
	 */
	public final String DATABASE_NAME = "jobper.db";
	/**
	 * Versión de la base de datos.
	 */
	public static int DATABASE_VERSION = 1;
	/**
	 * Nombre de la tabla en la base de datos que almacena los Jobs.
	 */
	public final String JOB_TABLE_NAME = "job";
	/**
	 * Campo _id de la tabla job de la base de datos.
	 */
	public final String _ID = "_id";
	/**
	 * Campo job_id de la tabla job de la base de datos.
	 */
	public final String JOB_ID = "job_id";
	/**
	 * Campo title de la tabla job de la base de datos.
	 */
	public final String TITLE = "title";
	/**
	 * Campo update at de la tabla job de la base de datos.
	 */
	public final String UPDATE_AT = "updateat";
	/**
	 * Campo salary min de la tabla job de la base de datos.
	 */
	public final String SALARY_MIN = "salarymin";
	/**
	 * Campo salary max de la tabla job de la base de datos.
	 */
	public final String SALARY_MAX = "salarymax";
}
