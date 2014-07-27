package com.example.videodemo;

import com.example.videodemo.library.Util;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	protected static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            
            
            rootView.findViewById(R.id.btnLocalIntent).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "video/mp4";
			        Uri uri = Util.getLocalPath();
			        intent.setDataAndType(uri, type);
			        startActivity(intent);   
				}
			});
            rootView.findViewById(R.id.btnRemoteIntent).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
			        String type = "video/*";//没有这个 默认用浏览器打开这个URL
			        Uri uri = Util.getRemotePath();
			        intent.setDataAndType(uri, type);
			        startActivity(intent);   
				}
			});
            rootView.findViewById(R.id.btnPlayOne).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context,PlayOneActivity.class));
				}
			});
            rootView.findViewById(R.id.btnPlayTwo).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context,PlayTwoActivity.class));
				}
			});
            rootView.findViewById(R.id.btnPlayThree).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context,PlayThreeActivity.class));
				}
			});
            rootView.findViewById(R.id.btnPlayFour).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(context,VideoPlayerActivity.class));
				}
			});
            
            return rootView;
        }
    }

}
