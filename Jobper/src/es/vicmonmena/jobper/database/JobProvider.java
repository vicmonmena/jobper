package es.vicmonmena.jobper.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;


/**
 * 
 * @author vicmonmena
 *
 */
public class JobProvider extends ContentProvider {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobProvider";
	
	private static final String AUTHORITY = "es.vicmonmena.jobper";
	
	public static final Uri CONTENT_URI = 
			Uri.parse("content://" + AUTHORITY + "/jobs");

	private static final int URI_JOBS = 1;
	private static final int URI_JOBS_ITEM = 2;
	
	private static final UriMatcher mUriMatcher;
	
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, "jobs", URI_JOBS);
		mUriMatcher.addURI(AUTHORITY, "jobs/#", URI_JOBS_ITEM);
	}
	
	private JobperDatabaseHelper dbHelper;
	
	@Override
	public boolean onCreate() {
		dbHelper = new JobperDatabaseHelper(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}
