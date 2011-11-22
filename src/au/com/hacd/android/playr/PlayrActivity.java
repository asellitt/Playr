package au.com.hacd.android.playr;


import net.dinglisch.android.tasker.TaskerIntent;

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
    
    private boolean useTasker;
    
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        Log.i( getString( R.string.app_name ), "-> onCreate" );
        
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );
        
        this.useTasker = false;
        this.swapTrigger( );
        
        Log.i( getString( R.string.app_name ), "<- onCreate" );
    }
    

    /**
     * Triggers a "go to previous track" event
     * 
     * @param v
     */
    public void fireBackwardEvent( View v )
    {
        String task = "PLAYR_SKIP_BACK";
        
        Log.i( getString( R.string.app_name ), "-> fireBackwardEvent" );
        updateActionLabel( "back called: " + task );
        
        if ( useTasker )
        {
            triggerTaskerTask( task );
        }
        else
        {
            triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_PREVIOUS );
        }
        
        Log.i( getString( R.string.app_name ), "<- fireBackwardEvent" );
    }
    

    /**
     * Triggers a "play/pause current song" event
     * 
     * @param v
     */
    public void firePlayEvent( View v )
    {
        String task = "PLAYR_PLAY_MUSIC";
        
        Log.i( getString( R.string.app_name ), "-> firePlayEvent" );
        updateActionLabel( "play called: " + task );
        
        if ( useTasker )
        {
            triggerTaskerTask( task );
        }
        else
        {
            triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE );
        }
        
        Log.i( getString( R.string.app_name ), "<- firePlayEvent" );
    }
    

    /**
     * Triggers a "go to next track" event"
     * 
     * @param v
     */
    public void fireForwardEvent( View v )
    {
        String task = "PLAYR_SKIP_NEXT";
        
        Log.i( getString( R.string.app_name ), "-> fireForwardEvent" );
        updateActionLabel( "next called: " + task );
        
        if ( useTasker )
        {
            triggerTaskerTask( task );
        }
        else
        {
            triggerMediaIntent( KeyEvent.KEYCODE_MEDIA_NEXT );
        }
        
        Log.i( getString( R.string.app_name ), "<- fireForwardEvent" );
    }
    

    /**
     * Toggles between using Tasker as the trigger or via the native Intent API
     * 
     * @param v
     */
    public void toggleTriggerType( View v )
    {
        Log.i( getString( R.string.app_name ), "-> toggleTriggerType" );
        
        swapTrigger( );
        
        Log.i( getString( R.string.app_name ), "<- toggleTriggerType" );
    }
    

    /**
     * Appends the given String to the beginning to the top of the Action Label
     * 
     * @param s
     *            the string to preppend
     */
    private void updateActionLabel( String s )
    {
        Log.i( getString( R.string.app_name ), "-> updateActionLabel( s:" + s + ")" );
        TextView actionLabel = ( TextView ) findViewById( R.id.actionLabel );
        
        actionLabel.setText( s + "\n" + actionLabel.getText( ) );
        
        Log.i( getString( R.string.app_name ), "<- updateActionLabel" );
    }
    

    /**
     * Triggers a given task via Tasker
     * 
     * @param task
     *            the name of the task to send with the event
     */
    private void triggerTaskerTask( String task )
    {
        Log.i( getString( R.string.app_name ), "-> triggerTaskerTask( task:" + task + ")" );
        updateActionLabel( "triggering via Tasker" );
        
        TaskerIntent i = new TaskerIntent( task );
        sendBroadcast( i );
        
        Log.i( getString( R.string.app_name ), "<- triggerTaskerTask" );
    }
    

    /**
     * Triggers the given event via the Intent API
     * 
     * @param keyevent
     *            the key to send with the event
     */
    private void triggerMediaIntent( int keyevent )
    {
        Log.i( getString( R.string.app_name ), "-> triggerMediaIntent( keyevent:" + keyevent + ")" );
        updateActionLabel( "triggering via API" );
        
        KeyEvent ke = new KeyEvent( KeyEvent.ACTION_DOWN, keyevent );
        
        Intent i = new Intent( "android.intent.action.MEDIA_BUTTON" );
        i.putExtra( "android.intent.extra.KEY_EVENT", ke );
        sendBroadcast( i );
        
        ke = new KeyEvent( KeyEvent.ACTION_UP, keyevent );
        
        i = new Intent( "android.intent.action.MEDIA_BUTTON" );
        i.putExtra( "android.intent.extra.KEY_EVENT", ke );
        sendBroadcast( i );
        
        Log.i( getString( R.string.app_name ), "<- triggerMediaIntent" );
    }
    

    /**
     * Toggles between Tasker or the API to trigger events (updates the toggle
     * label)
     */
    private void swapTrigger( )
    {
        Log.i( getString( R.string.app_name ), "-> swapTrigger" );
        
        this.useTasker = !this.useTasker;
        
        TextView toggleLabel = ( TextView ) findViewById( R.id.toggleLabel );
        
        if ( useTasker )
        {
            toggleLabel.setText( R.string.tasker );
        }
        else
        {
            toggleLabel.setText( R.string.api );
        }
        
        Log.i( getString( R.string.app_name ), "<- swapTrigger" );
    }
}
