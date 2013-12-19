package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobDetailsFragment extends Fragment {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobDetailsFragment";
	/**
	 * Cadena key de paso de dato entre activities.
	 */
	public static final String JOB = "es.vicmonmena.jobper.model.job";

	/**
	 * 
	 */
	private Job jobDedatils;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG,"onCreateView");
		
		jobDedatils = getArguments().getParcelable(JobDetailsFragment.JOB);
		return inflater.inflate(R.layout.activity_job_details, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		((TextView) getView().findViewById(R.id.jobTitleTxt)).setText(jobDedatils.getTitle());
		((TextView) getView().findViewById(R.id.jobPublishedTxt)).setText(jobDedatils.getUpdateAt());
		((TextView) getView().findViewById(R.id.jobSalaryminTxt))
			.setText(jobDedatils.getSalaryMin() + "$");
		((TextView) getView().findViewById(R.id.jobSalarymaxTxt))
			.setText(jobDedatils.getSalaryMax() + "$");
		
		ImageView img = (ImageView) getView().findViewById(R.id.markAsFavoriteImg);
		img.setClickable(true);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ImageView img = (ImageView) getView().findViewById(R.id.markAsFavoriteImg);
				if (jobDedatils.isFavorite()) {
					img.setImageResource(R.drawable.ic_unmark_as_favorite);
					img.setContentDescription(getString(R.string.cd_unmarked_as_favorite));
					jobDedatils.setFavorite(false);
				} else {
					img.setImageResource(R.drawable.ic_mark_as_favorite);
					img.setContentDescription(getString(R.string.cd_marked_as_favorite));
					jobDedatils.setFavorite(true);
				}
				
			}
		});
		
		if (jobDedatils.isFavorite()) {
			img.setImageResource(R.drawable.ic_mark_as_favorite);
			img.setContentDescription(getString(R.string.cd_marked_as_favorite));
		}
		
		// Cargamos la información referente a la startup
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();

		Bundle args = new Bundle();
		args.putString(StartupDetailsFragment.JOB_ID, jobDedatils.getJobId());
		
		StartupDetailsFragment startupDetails = new StartupDetailsFragment();
		startupDetails.setArguments(args);
		
		transaction.replace(R.id.startup_details_fragment, startupDetails);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
		
	}
}