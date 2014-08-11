package com.weather.utils;

import com.weather.view.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;

public class BitmapUtils {
	

	 public static Bitmap zoomImg(Resources res, int resId, int newWidth ,int newHeight){   
		    // 获得图片的宽高  
			 Options op = new Options(); 
			 op.inJustDecodeBounds = true;  
			 Bitmap bm = BitmapFactory.decodeResource(res, resId);
		    int width = bm.getWidth();   
		    int height = bm.getHeight();   
		    // 计算缩放比例   
		    int scaleWidth = newWidth / width;   
		    int scaleHeight =  newHeight / height;   
		    op.inSampleSize=scaleWidth>scaleHeight ? scaleWidth : scaleHeight ;
		    // 取得想要缩放的matrix参数   
		    op.inJustDecodeBounds = false; 
		    // 得到新的图片   www.2cto.com
		    bm=null;
		    Bitmap newbm = BitmapFactory.decodeResource(res, resId, op);   
		    return newbm; 
	
}

	 

	
}
