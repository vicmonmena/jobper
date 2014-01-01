package es.vicmonmena.jobper.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobperPreferences extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "MainActivity";
	/**
	 * Key de la propiedad de checking de Jobs marcados como favoritos.
	 */
	public static final String NOTIFICATION_KEY = "notification_frequency_options";
	/**
	 * Key de la propiedad que indica cuántos Jobs se mostrarán en la lista.
	 */
	public static final String JOBS_PER_PAGE_KEY = "jobs_per_page";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		SharedPreferences sharedPref = PreferenceManager
	        .getDefaultSharedPreferences(this);
		sharedPref.registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, 
		String key) {
		Log.d(TAG, key);
		if (key.equals(NOTIFICATION_KEY)) {
			SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
			int interval = Integer.parseInt(sharedPref.getString(
				JobperPreferences.NOTIFICATION_KEY, "60"));
			
			Controller.getInstance().resetAlarm(getApplicationContext(), interval);
        }
	}
}
