package es.vicmonmena.jobper.ui.components.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.database.util.DBConstants;
import es.vicmonmena.jobper.model.Job;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobCursorAdapter extends ResourceCursorAdapter {

	/**
	 * 
	 */
	private LayoutInflater inflater; 
	
	/**
	 * 
	 * @param context
	 * @param c
	 */
	public JobCursorAdapter(Context context, Cursor c) {
		super(context, R.layout.job_row_item, c, true);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.job_row_item, parent, false);
		ViewHolder holder = new ViewHolder();
		holder.jobTitleTextView = (TextView) view.findViewById(R.id.jobTitleTextView);
		holder.jobDateTextView = (TextView) view.findViewById(R.id.jobDateTextView);
		view.setTag(holder);

		return view;
	}
	
	@Override
	public void bindView(View view, Context ctx, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.jobTitleTextView.setText(
			cursor.getString(cursor.getColumnIndex(DBConstants.TITLE)));
		String date = Controller.getDateFormat(
			cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_AT)));
		holder.jobDateTextView.setText(date);
	}

	@Override
	public Job getItem(int position) {
		Job job = null;
		Cursor cursor = getCursor();
		if (cursor.moveToPosition(position)) {
			job = new Job();
			job.setTitle(cursor.getString(cursor.getColumnIndex(DBConstants.TITLE)));
			job.setJobId(cursor.getString(cursor.getColumnIndex(DBConstants.JOB_ID)));
			job.setUpdateAt(cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_AT)));
			job.setSalaryMin(cursor.getString(cursor.getColumnIndex(DBConstants.SALARY_MIN)));
			job.setSalaryMax(cursor.getString(cursor.getColumnIndex(DBConstants.SALARY_MAX)));
			job.setLocation(cursor.getString(cursor.getColumnIndex(DBConstants.LOCATION)));
		}
		return job;
	}
	
	static class ViewHolder {
		TextView jobTitleTextView;
		TextView jobDateTextView;
	}
}
