package es.vicmonmena.jobper.net.util;

/**
 * Constantes para trabajar con los servicios en Internet.
 * @author vicmonmena
 *
 */
public interface NETConstants {
	/**
	 * Servicio donde se localizan ls Jobs.
	 */
	public final String URI_JOBS = "http://api.angel.co/1/jobs";
	/**
	 * Servicio donde se localizan las startups.
	 */
	public final String URI_STARTUP = "http://api.angel.co/1/startups/";
	/**
	 * Parámetro prar solicitar un número de ittems al servicio pr página.
	 */
	public final String PARAM_PER_PAGE = "per_page=";
}
