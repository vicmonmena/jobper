package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		jobDedatils = getArguments().getParcelable(JobDetailsFragment.JOB);
		return inflater.inflate(R.layout.activity_job_details, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		((TextView) getView().findViewById(R.id.jobTitleTxt))
			.setText(jobDedatils.getTitle());
		String date = Controller.getDateFormat(jobDedatils.getUpdateAt());
		((TextView) getView().findViewById(R.id.jobPublishedTxt))
			.setText(date);
		((TextView) getView().findViewById(R.id.jobSalaryminTxt))
			.setText(jobDedatils.getSalaryMin() + "$");
		((TextView) getView().findViewById(R.id.jobSalarymaxTxt))
			.setText(jobDedatils.getSalaryMax() + "$");
		((TextView) getView().findViewById(R.id.jobLocationTxt))
			.setText(jobDedatils.getLocation());
		
		ImageView img = (ImageView) getView().findViewById(R.id.markAsFavoriteImg);
		img.setClickable(true);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				boolean isFavorite = Controller.getInstance()
					.markJobAsFavorite(getActivity(), jobDedatils);
				jobDedatils.setFavorite(isFavorite);
				updateFavoriteImg(jobDedatils.isFavorite());
			}
		});
		
		updateFavoriteImg(Controller.getInstance()
			.checkJobIsFavorite(getActivity(), jobDedatils.getJobId()));
		
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
		
		((TextView) getView().findViewById(R.id.jobTitleTxt)).requestFocus();
	}
	
	/**
	 * Modifica el icono de favorito en función del valor pasado por parámetro.
	 * @param isFavorite
	 */
	public void updateFavoriteImg(boolean isFavorite) {
		ImageView img = (ImageView) getView().findViewById(R.id.markAsFavoriteImg);
		if (isFavorite) {
			img.setImageResource(R.drawable.ic_marked_as_favorite);
			img.setContentDescription(getString(R.string.cd_marked_as_favorite));
		} else {
			img.setImageResource(R.drawable.ic_unmarked_as_favorite);
			img.setContentDescription(getString(R.string.cd_unmarked_as_favorite));
		}
	}
}