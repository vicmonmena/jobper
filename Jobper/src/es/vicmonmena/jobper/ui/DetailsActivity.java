package es.vicmonmena.jobper.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.ui.components.JobDetailsFragment;

/**
 * 
 * @author vicmonmena
 *
 */
public class DetailsActivity extends Activity {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "DetailsActivity";
	
	private JobDetailsFragment jobDetailsFragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        jobDetailsFragment = new JobDetailsFragment();
        jobDetailsFragment.setArguments(getIntent().getExtras());
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(android.R.id.content, jobDetailsFragment).commit();
    }

}
