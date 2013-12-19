package es.vicmonmena.jobper.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
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
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(android.R.id.content, jobDetailsFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

	public void onClickView(View view) {
    	switch (view.getId()) {
		case R.id.startupLogoImg:
			if (!TextUtils.isEmpty((String)view.getTag())) {
				Intent browserIntent = new Intent(
					Intent.ACTION_VIEW, Uri.parse((String)view.getTag()));
				startActivity(browserIntent);
				// Log.i(TAG, "URI: " + (String)view.getTag());
			} else {
				Toast.makeText(DetailsActivity.this, getString(R.string.msg_uri_not_found), Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
    }
}
