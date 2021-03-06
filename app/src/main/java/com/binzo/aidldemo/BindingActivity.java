package com.binzo.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by liubingzhao on 2018/1/26.
 */

public class BindingActivity extends AppCompatActivity {

    public static final String TAG = "BindingActivity";

    /*
    /** The primary interface we will be calling on the service. */
    IRemoteService mService = null;

    Button mKillButton;
    Button mGetPidButton;
    Button mCallbackButton;
    Button mParcelButton;
    TextView mText;

    private boolean mIsBound;

    /**
     * Standard initialization of this activity.  Set up the UI, then wait
     * for the user to poke it before doing anything.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_service_binding);

        // Watch for button clicks.
        Button button = findViewById(R.id.bind);
        button.setOnClickListener(mBindListener);
        button = findViewById(R.id.unbind);
        button.setOnClickListener(mUnbindListener);
        mKillButton = findViewById(R.id.kill);
        mKillButton.setOnClickListener(mKillListener);
        mKillButton.setEnabled(false);

        mParcelButton = findViewById(R.id.parcel);
        mParcelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if (mService != null) {
                        mService.transferInParcel(new BasicTypesParcel(1,1,false,1,1, "1"));
                    } else {
                        mText.setText("Service is null.");
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        mParcelButton.setEnabled(false);

        mCallbackButton = findViewById(R.id.callback);
        mCallbackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if (mService != null) {
                        mService.testCallback();
                    } else {
                        mText.setText("Service is null.");
                    }
                } catch (RemoteException e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        mCallbackButton.setEnabled(false);

        mGetPidButton = findViewById(R.id.getpid);
        mGetPidButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    mText.setText("pid: " + mService.getPid());
                } catch (RemoteException e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        mGetPidButton.setEnabled(false);

        mText = (TextView)findViewById(R.id.text);
        mText.setText("Not attached.");
    }

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = IRemoteService.Stub.asInterface(service);
            mParcelButton.setEnabled(true);
            mCallbackButton.setEnabled(true);
            mGetPidButton.setEnabled(true);
            mKillButton.setEnabled(true);

            mText.setText("Attached.");

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
            }

            // As part of the sample, tell the user what happened.
            Toast.makeText(BindingActivity.this, R.string.remote_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mParcelButton.setEnabled(false);
            mCallbackButton.setEnabled(false);
            mGetPidButton.setEnabled(false);
            mKillButton.setEnabled(false);
            mText.setText("Disconnected.");

            // As part of the sample, tell the user what happened.
            Toast.makeText(BindingActivity.this, R.string.remote_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener mBindListener = new View.OnClickListener() {
        public void onClick(View v) {
            // Establish a couple connections with the service, binding
            // by interface names.  This allows other applications to be
            // installed that replace the remote service by implementing
            // the same interface.
            Intent intent = new Intent(BindingActivity.this, RemoteService.class);
            intent.setAction(IRemoteService.class.getName());
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            mIsBound = true;
            mText.setText("BindingActivity.");
        }
    };

    private View.OnClickListener mUnbindListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (mIsBound) {
                // If we have received the service, and hence registered with
                // it, then now is the time to unregister.
                if (mService != null) {
                    try {
                        mService.unregisterCallback(mCallback);
                    } catch (RemoteException e) {
                        // There is nothing special we need to do if the service
                        // has crashed.
                    }
                }

                // Detach our existing connection.
                unbindService(mConnection);
                mParcelButton.setEnabled(false);
                mCallbackButton.setEnabled(false);
                mGetPidButton.setEnabled(false);
                mKillButton.setEnabled(false);
                mIsBound = false;
                mText.setText("Unbinding.");
            }
        }
    };

    private View.OnClickListener mKillListener = new View.OnClickListener() {
        public void onClick(View v) {
            // To kill the process hosting our service, we need to know its
            // PID.  Conveniently our service has a call that will return
            // to us that information.
            if (mService != null) {
                try {
                    int pid = mService.getPid();
                    // Note that, though this API allows us to request to
                    // kill any process based on its PID, the kernel will
                    // still impose standard restrictions on which PIDs you
                    // are actually able to kill.  Typically this means only
                    // the process running your application and any additional
                    // processes created by that app as shown here; packages
                    // sharing a common UID will also be able to kill each
                    // other's processes.
                    Process.killProcess(pid);
                    mText.setText("Killed service process.");
                } catch (RemoteException ex) {
                    // Recover gracefully from the process hosting the
                    // server dying.
                    // Just for purposes of the sample, put up a notification.
                    Toast.makeText(BindingActivity.this,
                            R.string.remote_call_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    // ----------------------------------------------------------------------
    // Code showing how to deal with callbacks.
    // ----------------------------------------------------------------------

    /**
     * This implementation is used to receive callbacks from the remote
     * service.
     */
    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        /**
         * This is called by the remote service regularly to tell us about
         * new values.  Note that IPC calls are dispatched through a thread
         * pool running in each process, so the code executing here will
         * NOT be running in our main thread like most other things -- so,
         * to update the UI, we need to use a Handler to hop over there.
         */
        public void valueChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(BUMP_MSG, value, 0));
        }
    };

    private static final int BUMP_MSG = 1;

    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case BUMP_MSG:
                    mText.setText("Received from service: " + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

    };
}