package au.com.hacd.android.playr;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


public class PlayrActivity
        extends Activity
{
    private static final String TAG = "Playr";
    
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        Log.v( TAG, "-> onCreate" );
        
        super.onCreate( savedInstanceState );
        this.setContentView( R.layout.main );
        
        Log.v( TAG, "<- onCreate" );
    }
    

    /**
     * Triggers a "go to previous track" event
     * 
     * @param v
     */
    public void fireBackwardEvent( View v )
    {
        Log.v( TAG, "-> fireBackwardEvent" );
        
        // write intent to screen
        Log.i( TAG, "Skipping to previous track" );
        this.updateActionLabel( "Skipping to previous track" );
        
        // broadcast intent
        this.triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_PREVIOUS );
        
        Log.v( TAG, "<- fireBackwardEvent" );
    }
    

    /**
     * Triggers a "play/pause current song" event
     * 
     * @param v
     */
    public void firePlayEvent( View v )
    {
        Log.v( TAG, "-> firePlayEvent" );
        
        // write intent to screen
        Log.i( TAG, "Playing/pausing current track" );
        this.updateActionLabel( "Playing/pausing current track" );
        
        // broadcast intent
        this.triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE );
        
        Log.v( TAG, "<- firePlayEvent" );
    }
    

    /**
     * Triggers a "go to next track" event
     * 
     * @param v
     */
    public void fireForwardEvent( View v )
    {
        Log.v( TAG, "-> fireForwardEvent" );
        
        // write intent to screen
        Log.i( TAG, "Skipping to next track" );
        this.updateActionLabel( "Skipping to next track" );
        
        // broadcast intent
        this.triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_NEXT );
        
        Log.v( TAG, "<- fireForwardEvent" );
    }
    

    /**
     * Appends the given String to the beginning to the top of the Action Label
     * 
     * @param s
     *            the string to preppend
     */
    private void updateActionLabel( String s )
    {
        String verboseCall = "-> updateActionLabel";
        
        if(Log.isLoggable( TAG, Log.VERBOSE ))
        {
            verboseCall = "-> updateActionLabel( s:" + s + ")";
        }
        
        Log.v( TAG, verboseCall );
        
        // fetch action label
        TextView actionLabel = ( TextView ) findViewById( R.id.actionLabel );
        
        // append text to action label
        actionLabel.setText( s + "\n" + actionLabel.getText( ) );
        
        Log.v( TAG, "<- updateActionLabel" );
    }
    

    /**
     * Triggers the given event via the Intent API
     * 
     * @param key
     *            the key to send with the event
     */
    private void triggerMediaIntent( int key )
    {
        String verboseCall = "-> triggerMediaIntent";
        
        if(Log.isLoggable( TAG, Log.VERBOSE ))
        {
            verboseCall = "-> triggerMediaIntent( key:" + key + ")";
        }
        
        Log.v( TAG, verboseCall );
        
        String action = "android.intent.action.MEDIA_BUTTON";
        String extra = "android.intent.extra.KEY_EVENT";
        
        // create the media button event
        Intent i = new Intent( action );
        KeyEvent ke = new KeyEvent( KeyEvent.ACTION_DOWN, key );
        
        // send key down action
        Log.d( TAG, "Triggering key down action" );
        i.putExtra( extra, ke );
        sendBroadcast( i );
        
        // send key up action
        Log.d( TAG, "Triggering key up action" );
        ke = KeyEvent.changeAction( ke, KeyEvent.ACTION_UP );
        i.removeExtra( extra );
        i.putExtra( extra, ke );
        sendBroadcast( i );
        
        Log.v( TAG, "<- triggerMediaIntent" );
    }
    
}
