package es.vicmonmena.jobper.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
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
	/**
	 * Fragment contenedor de los detalles de un Job.
	 */
	private JobDetailsFragment jobDetailsFragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActionBar().setDisplayShowHomeEnabled(true);
        
        jobDetailsFragment = new JobDetailsFragment();
        jobDetailsFragment.setArguments(getIntent().getExtras());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(android.R.id.content, jobDetailsFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        	case android.R.id.home:
        		Intent intent = new Intent(this,MainActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(intent);
	        	return true;
        	case R.id.action_share:
        		Job jobDedatils = getIntent().getExtras().getParcelable(JobDetailsFragment.JOB);
        		Controller.getInstance().shareJob(this, jobDedatils);
				return true;
			case R.id.action_info:
				Toast.makeText(this, getString(R.string.action_info), Toast.LENGTH_SHORT).show();
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	/**
	 * Recoge el evento onClick de los elementos de la IU.
	 * @param view
	 */
	public void onClickView(View view) {
    	switch (view.getId()) {
		case R.id.startupLogoImg:
			if (!TextUtils.isEmpty((String)view.getTag())) {
				Intent browserIntent = new Intent(
					Intent.ACTION_VIEW, Uri.parse((String)view.getTag()));
				startActivity(browserIntent);
			} else {
				Toast.makeText(DetailsActivity.this, getString(R.string.msg_uri_not_found), Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
    }
}
