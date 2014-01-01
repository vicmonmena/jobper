package es.vicmonmena.jobper.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.ui.components.FavoriteJobsFragment;
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
	/**
	 * Indicador del TAB de la ActionBar que está marcado.
	 */
	public static final String CURRENT_TAB_INDEX = "CURRENT_TAB_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
		
        // Alarma para notificaciones
        SharedPreferences sharedPref = PreferenceManager
        	.getDefaultSharedPreferences(this);
		int interval = Integer.parseInt(
			sharedPref.getString(JobperPreferences.NOTIFICATION_KEY, "60"));
        Controller.getInstance().setAlarm(this, interval);
        
        // Definiendo pestañas de la action bar
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Tab tabJobs = actionBar.newTab();
        tabJobs.setText(R.string.action_jobs);
        tabJobs.setIcon(R.drawable.action_jobs);
        tabJobs.setTabListener(new JobperTabListener<JobsFragment>(
            	this, "jobs", JobsFragment.class));
        
        Tab tabFavorites = actionBar.newTab();
        tabFavorites.setText(R.string.action_favorites);
        tabFavorites.setIcon(R.drawable.action_favorites);
        tabFavorites.setTabListener(new JobperTabListener<FavoriteJobsFragment>(
        	this, "favorites", FavoriteJobsFragment.class));

        actionBar.addTab(tabJobs, true);
        actionBar.addTab(tabFavorites, false);

        if (getIntent().hasExtra(CURRENT_TAB_INDEX)) {
	        getActionBar().selectTab(getActionBar().getTabAt(
	        	getIntent().getIntExtra(CURRENT_TAB_INDEX, 0)));
        }
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        	case R.id.action_notification_config:
        		Intent i = new Intent(this, JobperPreferences.class);
        		startActivity(i);
				return true;
			case R.id.action_info:
				Toast.makeText(this, getString(R.string.action_info_text), Toast.LENGTH_LONG).show();
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * 
     * @author vicmonmena
     *
     * @param <T>
     */
    public static class JobperTabListener<T extends Fragment> implements ActionBar.TabListener {

    	/**
    	 * 
    	 */
    	private Fragment mFragment;
    	/**
    	 * 
    	 */
        private final Activity mActivity;
        /**
         * 
         */
        private final String mTag;
        /**
         * 
         */
        private final Class<T> mClass;
        
        /**
         * 
         * @param activity
         * @param tag
         * @param clz
         */
    	public JobperTabListener(Activity activity, String tag, Class<T> clz) {
    		this.mActivity = activity;
    		this.mTag = tag;
    		this.mClass = clz;
		}
    	
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// no hacer nada
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
			// Comprobando si ya sehan inicializado fragments
	        if (mFragment == null) {
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(R.id.jobs_fragment, mFragment, mTag);
	        } else {
	            ft.attach(mFragment);
	        }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
	            ft.detach(mFragment);
	        }
		}
    	
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt(MainActivity.CURRENT_TAB_INDEX, 
        	getActionBar().getSelectedTab().getPosition());
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	if (savedInstanceState != null) {
    		getActionBar().selectTab(getActionBar().getTabAt(
    			savedInstanceState.getInt(CURRENT_TAB_INDEX)));
    	}
    }
}
