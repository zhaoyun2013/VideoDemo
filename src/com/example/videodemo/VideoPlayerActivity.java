package com.example.videodemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class VideoPlayerActivity extends Activity {

	private RelativeLayout topBar, bottomBar;
	private SurfaceView surface;
	private SeekBar seekBar;

	private EffectInVisiableHandler mtimeHandler;
	private final static int ACTIONBAR_HIDDEN = 1;
	private final int DELAY_MILLIS = 3000;

	protected View.OnTouchListener touchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				showBar();
				resetHandler();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			return true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_video_play);

		topBar = (RelativeLayout) findViewById(R.id.topBar);
		bottomBar = (RelativeLayout) findViewById(R.id.bottomBar);
		surface = (SurfaceView) findViewById(R.id.surface);
		surface.setOnTouchListener(touchListener);
		seekBar = (SeekBar)findViewById(R.id.seekBar);

		mtimeHandler = new EffectInVisiableHandler(this);
		sendMessage();
	}

	protected static class EffectInVisiableHandler extends Handler {

		private VideoPlayerActivity context;

		public EffectInVisiableHandler(VideoPlayerActivity context) {
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ACTIONBAR_HIDDEN:
				hiddenBar();
				break;

			}
		}
		// 代码方式的动画隐藏
		// TranslateAnimation mHiddenAction = new TranslateAnimation(
		// Animation.RELATIVE_TO_SELF, 0.0f,
		// Animation.RELATIVE_TO_SELF, 0.0f,
		// Animation.RELATIVE_TO_SELF, 0.0f,
		// Animation.RELATIVE_TO_SELF, -1.0f);
		// mHiddenAction.setDuration(500);

		public void hiddenBar() {
			if (context.topBar.getVisibility() == View.VISIBLE) {
				Animation animation = AnimationUtils.loadAnimation(context,
						R.anim.up_hide);
				Animation animation2 = AnimationUtils.loadAnimation(context,
						R.anim.down_hide);
				context.topBar.startAnimation(animation);
				context.bottomBar.startAnimation(animation2);
				context.topBar.setVisibility(View.GONE);
				context.bottomBar.setVisibility(View.GONE);
			}
		}
	}

	protected void showBar() {
		if (topBar.getVisibility() == View.GONE) {
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.up_show);
			Animation animation2 = AnimationUtils.loadAnimation(this,
					R.anim.down_show);
			topBar.startAnimation(animation);
			bottomBar.startAnimation(animation2);
			topBar.setVisibility(View.VISIBLE);
			bottomBar.setVisibility(View.VISIBLE);
		}
	}

	protected void resetHandler() {
		mtimeHandler.removeMessages(ACTIONBAR_HIDDEN);
		sendMessage();
	}

	protected void sendMessage() {
		Message msg = mtimeHandler.obtainMessage(ACTIONBAR_HIDDEN);
		mtimeHandler.sendMessageDelayed(msg, DELAY_MILLIS);
	}

}
