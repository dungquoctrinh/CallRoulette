package io.github.juntrinh.callroulette;

import android.content.Context;
import android.util.Log;
import com.twilio.client.Connection;
import com.twilio.client.Device;
import com.twilio.client.Twilio;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kid on 8/4/16.
 */
public class CallRoulette implements Twilio.InitListener{

    private Device mDevice;
    private String TAG = "CallRoulette";
    private Connection mConnection;
    private Context mContext;

    /**
     * The device and connection are Twilio objects;
     * the device represents your phone and the connection maintains your link to the Twilio server
     * @param context
     */
    public CallRoulette(Context context)
    {
        this.mContext = context;
        Twilio.initialize(context, this);
    }

    @Override
    public void onInitialized() {
        Log.d(TAG, "Twilio SDK is ready");
        new HttpHandler(){
            @Override
            public HttpUriRequest getHttpRequestMethod(){
                Log.d(TAG, mContext.getString(R.string.app_capability_url));
                return new HttpGet(mContext.getString(R.string.app_capability_url));
            }
            @Override
            public void onResponse(String token) {
                mDevice = Twilio.createDevice(token, null);
                Log.d(TAG, "Capability token: " + token);
            }
        }.execute();
    }

    @Override
    public void onError(Exception e) {

    }

    public void connect()
    {
        Map<String, String> parameters = new HashMap<String, String>();
        mConnection = mDevice.connect(parameters, null);
        if (mConnection == null)
            Log.w(TAG, "Failed to create new connection");
    }

    /**
     * Ends the connection, retrieves the capability token, and tells the server to ‘hangup’ via a get request.
     * We’ll only receive the capability token when we’ve authenticated successfully with the server,
     * and it allows us to sign our communications from the device without exposing our AuthToken.
     */
    public void disconnect()
    {
        if (mConnection != null) {
            mConnection.disconnect();
            mConnection = null;
        }
        /** a handler to perform a GET request on the hangup URL
        in order to tell the server that the client has ‘hung up’ the phone.*/
        new HttpHandler(){
            @Override
            public HttpUriRequest getHttpRequestMethod(){
                Log.d(TAG, mContext.getString(R.string.app_hangup_url));
                return new HttpGet(mContext.getString(R.string.app_hangup_url));
            }
            @Override
            public void onResponse(String token) {
                Log.d(TAG, token);
            }
        }.execute();
    }

    @Override
    protected void finalize()
    {
        if (mDevice != null)
            mDevice.release();
    }
}
