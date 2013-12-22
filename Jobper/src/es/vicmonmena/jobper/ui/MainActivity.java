package es.vicmonmena.jobper.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
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
	 * Fragment contenedor de la lista de Jobs completa.
	 */
	private JobsFragment jobsFragment;
	/**
	 * Fragment contenedor de la lista de Jobs msarcados como favoritos. 
	 */
	private FavoriteJobsFragment favJobsFragment;
	/**
	 * 
	 */
	private static final String CURRENT_TAB_INDEX = "CURRENT_TAB_INDEX";
	/**
	 * 
	 */
	private static final String CURRENT_FRAGMENT_STATE = "CURRENT_FRAGMENT_STATE";
			
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
        
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
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
			case R.id.action_info:
				Toast.makeText(this, getString(R.string.action_info), Toast.LENGTH_SHORT).show();
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

    	private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        
    	public JobperTabListener(Activity activity, String tag, Class<T> clz) {
    		this.mActivity = activity;
    		this.mTag = tag;
    		this.mClass = clz;
		}
    	
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
			// Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(R.id.jobs_fragment, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
		}
    	
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	
    	outState.putInt(MainActivity.CURRENT_TAB_INDEX, 
        	getActionBar().getSelectedTab().getPosition());
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	if (savedInstanceState != null) {
    		getActionBar().selectTab(getActionBar().getTabAt(
    			savedInstanceState.getInt(CURRENT_TAB_INDEX)));
    	}
    }
}
