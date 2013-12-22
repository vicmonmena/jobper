package es.vicmonmena.jobper.database.util;

/**
 * Constantes para trabajar con la persistencia de datos.
 * @author vicmonmena
 *
 */
public interface DBConstants {
	
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
	/**
	 * Campo location de la tabla job de la base de datos.
	 */
	public final String LOCATION = "location";
	/**
	 * Campos obtenidos para comprobar que existe un Job en la BBDD.
	 */
	public final String[] PROJECTION_SIMPLE = {JOB_ID};
	/**
	 * Campos que obtenemos para mostrar en un listado de viajes.
	 */
	public static final String[] PROJECTION = {_ID, JOB_ID, TITLE, UPDATE_AT, 
		SALARY_MIN, SALARY_MAX, LOCATION};
}
