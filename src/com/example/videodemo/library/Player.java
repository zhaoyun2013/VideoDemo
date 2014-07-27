package com.example.videodemo.library;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

/**
 * Player.java是本文的核心，Player.java实现了“进度条更新”、“数据缓冲”、“SurfaceHolder生命周期”等功能，
 * 其中“SurfaceHolder生命周期”是视频与音频播放的最大区别，
 * 通过surfaceCreated()、surfaceDestroyed()、surfaceChanged()可以创建/释放某些资源。
 *
 */
public class Player implements OnBufferingUpdateListener,
		OnCompletionListener, MediaPlayer.OnPreparedListener,
		SurfaceHolder.Callback {
	private static final String TAG ="Player";
	private Context context;
	private int videoWidth;
	private int videoHeight;
	public MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	private SeekBar skbProgress;
	private Timer mTimer=new Timer();
	public Player(SurfaceView surfaceView,SeekBar skbProgress,Context context)
	{
		this.skbProgress=skbProgress;
		this.context=context;
		surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
	}
	
	/*******************************************************
	 * 通过定时器和Handler来更新进度条
	 ******************************************************/
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if(mediaPlayer==null)
				return;
			if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}
		}
	};
	
	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {

			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();
			
			if (duration > 0) {
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
		};
	};
	//*****************************************************
	
	public void play()
	{
		if(!mediaPlayer.isPlaying()){
			mediaPlayer.start();
		}
	}
	
	public void playUrl(Uri uri)
	{
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(context, uri);
			mediaPlayer.prepareAsync();
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void pause()
	{
		mediaPlayer.pause();
	}
	
	public void stop()
	{
		if (mediaPlayer != null) { 
			mediaPlayer.stop();
            mediaPlayer.release(); 
            mediaPlayer = null; 
        } 
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.i(TAG, "surface changed");
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			Log.e("mediaPlayer", "error", e);
		}
		Log.i(TAG, "surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.i(TAG, "surface destroyed");
	}

	
	/**
	 * 通过onPrepared播放
	 */
	@Override
	public void onPrepared(MediaPlayer arg0) {
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {
			//有些视频是android播放器不能播放的，不能播放时videoHeight=0，videoWidth=0，以此来判断是否播放视频。
			arg0.start();
		}
		Log.i(TAG, "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
		skbProgress.setSecondaryProgress(bufferingProgress);
		int currentProgress=skbProgress.getMax()*mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration();
		Log.i(TAG,currentProgress+"% play ***"+bufferingProgress + "% buffer");
		
	}

}

