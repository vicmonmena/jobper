package es.vicmonmena.jobper.ui.components;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.ui.DetailsActivity;
import es.vicmonmena.jobper.ui.components.widget.JobCursorAdapter;

/**
 * 
 * @author vicmonmena
 *
 */
public class FavoriteJobsFragment extends ListFragment {
	
	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "FavoriteJobsFragment";
	/**
	 * A true indica que trabajamos con un tablet.
	 */
	private boolean singleColumn = false;
	/**
	 * Adapter para el listado de Jobs
	 */
	private JobCursorAdapter adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View jobDetailsFragment = getActivity()
			.findViewById(R.id.job_details_fragment);
		
		if (jobDetailsFragment == null) {
			singleColumn = true;
		}

		new FavoriteJobsAsyncTask().execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Job selectedJob = adapter.getItem(position);
		if (singleColumn){
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra(JobDetailsFragment.JOB, selectedJob);
			startActivity(intent);
		} else {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();

			Bundle args = new Bundle();
			args.putParcelable(JobDetailsFragment.JOB, selectedJob);
			
			JobDetailsFragment jobDetails = new JobDetailsFragment();
			jobDetails.setArguments(args);
			
			transaction.replace(R.id.job_details_fragment, jobDetails);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
		}
	}
	
	/**
	 * 
	 * @author vicmonmena
	 *
	 */
	private class FavoriteJobsAsyncTask extends AsyncTask<Void, Void, Cursor> {

		
		@Override
		protected Cursor doInBackground(Void... params) {
			return Controller.getInstance().loadFavoriteJobs(getActivity());
		}
		
		@Override
		protected void onPostExecute(Cursor result) {
			if (result != null) {
				adapter = new JobCursorAdapter(getActivity(), result);
				setListAdapter(adapter);
			}
			super.onPostExecute(result);
		}
	}

}
