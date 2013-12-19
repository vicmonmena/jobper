package es.vicmonmena.jobper.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.ui.components.JobsFragment;

/**
 * 
 * @author vicmonmena
 *
 */
public class MainActivity extends Activity {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "MainActivity";
	
	private JobsFragment jobsFragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        
        initActionBar();
        
        jobsFragment = new JobsFragment();
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction initTransaction = fm.beginTransaction();
        initTransaction.add(R.id.jobs_fragment, jobsFragment);
        initTransaction.commit();
        
        jobsFragment = (JobsFragment) fm.findFragmentById(R.id.jobs_fragment);
    }

    /**
     * Inicializa la action bar.
     */
    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c27ba0")));
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
			case R.id.action_jobs:
				Toast.makeText(this, getString(R.string.action_jobs), Toast.LENGTH_SHORT).show();
				return true;
			case R.id.action_favorites:
				Toast.makeText(this, getString(R.string.action_favorites), Toast.LENGTH_SHORT).show();
				return true;
			case R.id.action_info:
				Toast.makeText(this, getString(R.string.action_info), Toast.LENGTH_SHORT).show();
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
