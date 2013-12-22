package es.vicmonmena.jobper.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import es.vicmonmena.jobper.Controller;

/**
 * Servicio encargado de realizar relacionadas con Jobs.
 * @author vicmonmena
 *
 */
public class JobService extends Service{

	/**
	 * TAGpara los mensajes de log de est
	 */
	private static final String TAG = "JobService";
	/**
	 * Tipo de mensaje para actualizar la IU.
	 */
	public static final int UPDATE_UI = 1;
	/**
	 * 
	 */
	private final IBinder binder = new JobBinder();
	/**
	 * Envía mensajes a la Activity.
	 */
	private Messenger messenger;
	/**
	 * Set messenger
	 * @param messenger
	 */
	public void setMessenger(Messenger messenger) {
		this.messenger = messenger;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

    public class JobBinder extends Binder {
    	public JobService getService() {
            return JobService.this;
        }
    }
    
	@Override
	public void onCreate() {
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	/**
	 * Comprueba si el Job con id pasado por parámetro está marcado como 
	 * favorito, es decir, está registrado en bbdd.
	 * @param jobId
	 * @return
	 */
	public void checkIsFavorite(final String jobId) {
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				boolean isFavorite = Controller.getInstance()
					.checkJobIsFavorite(getApplication(), jobId);
				if (isFavorite) {
					sendMessage();
				}
			}
		}).start();
	}
	
	private void sendMessage() {
		try {
			Message msg = Message.obtain(null, UPDATE_UI);
			messenger.send(msg);
		} catch (RemoteException e) {
			Log.i(TAG, "RemoteException");
		}
	}
}
