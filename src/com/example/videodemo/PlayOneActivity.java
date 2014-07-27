package com.example.videodemo;

import com.example.videodemo.library.Player;
import com.example.videodemo.library.Util;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.os.Build;

public class PlayOneActivity extends ActionBarActivity {
	private static final String TAG ="PlayOneActivity";
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_play_one);
		context = this;
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_one, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private SurfaceView surfaceView;  
	    private Button btnPause, btnPlayUrl, btnStop;  
	    private SeekBar skbProgress;  
	    private Player player;  
	    
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_play_one,
					container, false);
			
	        surfaceView = (SurfaceView) rootView.findViewById(R.id.surfaceView1);  
	  
	        btnPlayUrl = (Button) rootView.findViewById(R.id.btnPlayUrl);  
	        btnPlayUrl.setOnClickListener(new ClickEvent());  
	  
	        btnPause = (Button) rootView.findViewById(R.id.btnPause);  
	        btnPause.setOnClickListener(new ClickEvent());  
	  
	        btnStop = (Button) rootView.findViewById(R.id.btnStop);  
	        btnStop.setOnClickListener(new ClickEvent());  
	  
	        skbProgress = (SeekBar) rootView.findViewById(R.id.skbProgress);  
	        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());  
	        player = new Player(surfaceView, skbProgress,context);  
	        
			return rootView;
		}
		
		class ClickEvent implements OnClickListener {  
			  
	        @Override  
	        public void onClick(View arg0) {  
	            if (arg0 == btnPause) {  
	                player.pause();  
	            } else if (arg0 == btnPlayUrl) {  
//	            	String path = Util.getSDPath();
//	            	Uri uri = Uri.parse(path+"/DCIM/Camera/VID_20140718_154525.mp4");
//	                String url=path+"/DCIM/Camera/VID_20140718_154525.mp4";  
	                player.playUrl(Util.getRemotePath());  
	            } else if (arg0 == btnStop) {  
	                player.stop();  
	            }  
	  
	        }  
	    }  
	  
	    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {  
	        int progress;  
	  
	        @Override  
	        public void onProgressChanged(SeekBar seekBar, int progress,  
	                boolean fromUser) {  
	            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()  
	            this.progress = progress * player.mediaPlayer.getDuration()  
	                    / seekBar.getMax();  
	        }  
	  
	        @Override  
	        public void onStartTrackingTouch(SeekBar seekBar) {  
	  
	        }  
	  
	        @Override  
	        public void onStopTrackingTouch(SeekBar seekBar) {  
	            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字  
	            player.mediaPlayer.seekTo(progress);  
	        }  
	    }  
		
	}

}
