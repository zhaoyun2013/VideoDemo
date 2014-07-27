package com.example.videodemo.library;

import java.io.File;

import android.net.Uri;
import android.os.Environment;

public class Util {
	
	public static String remotePath = "http://www.jsnxetd.org.cn/test.mp4";
	
	public static Uri getRemotePath()
	{
		Uri uri = Uri.parse("http://www.jsnxetd.org.cn/test.mp4");
		return uri;
	}
	
	public static Uri getLocalPath()
	{
        String path = Util.getSDPath();
    	Uri uri = Uri.parse(path+"/DCIM/Camera/VID_20140718_154525.mp4");
    	return uri;
	}
	
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

}
