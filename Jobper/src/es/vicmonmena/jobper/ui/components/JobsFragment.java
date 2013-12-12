package es.vicmonmena.jobper.ui.components;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.ui.DetailsActivity;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobsFragment extends ListFragment {
	
	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobsFragment";
	/**
	 * A true indica que trabajamos con un tablet.
	 */
	private boolean singleColumn = false;
	/**
	 * Adapter para el listado de Jobs
	 */
	private ArrayAdapter<CharSequence> adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG,"onActivityCreated");
		View jobDetailsFragment = getActivity()
			.findViewById(R.id.job_details_fragment);
		
		if (jobDetailsFragment == null) {
			singleColumn = true;
		}

		adapter = ArrayAdapter.createFromResource(getActivity(), 
			R.array.sample_jobs_array, android.R.layout.simple_list_item_1);
		
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String jobTitle = adapter.getItem(position).toString();

		if (singleColumn){
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra(JobDetailsFragment.JOB_TITLE, jobTitle);
			startActivity(intent);
		} else {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();

			Bundle args = new Bundle();
			args.putCharSequence(JobDetailsFragment.JOB_TITLE, jobTitle);
			
			JobDetailsFragment jobDetails = new JobDetailsFragment();
			jobDetails.setArguments(args);
			
			transaction.replace(R.id.job_details_fragment, jobDetails);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
		}
	}
}