package com.weather.utils;

import com.weather.view.R;

import android.R.integer;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ParseTempNumber {

	public static Bitmap[] getTempNumber(Resources res, String text) {
		Bitmap[] arr = new Bitmap[2];
		String s1 = text.substring(text.indexOf("：") + 1, text.indexOf("℃"));

		String resNum = s1.substring((s1.indexOf("：") + 1));

		// 判断是单位数，还是双位数
		int len = resNum.length();
		Bitmap bm = null;
		if (len == 1) {

			switch (Integer.parseInt(resNum)) {
			case 1:
				bm = BitmapFactory.decodeResource(res, R.drawable.t1);
				break;
			case 2:

				bm = BitmapFactory.decodeResource(res, R.drawable.t2);
				break;
			case 3:

				bm = BitmapFactory.decodeResource(res, R.drawable.t3);
				break;
			case 4:
				bm = BitmapFactory.decodeResource(res, R.drawable.t4);

				break;
			case 5:
				bm = BitmapFactory.decodeResource(res, R.drawable.t5);

				break;
			case 6:
				bm = BitmapFactory.decodeResource(res, R.drawable.t6);

				break;
			case 7:

				bm = BitmapFactory.decodeResource(res, R.drawable.t7);
				break;
			case 8:
				bm = BitmapFactory.decodeResource(res, R.drawable.t8);

				break;
			case 9:

				bm = BitmapFactory.decodeResource(res, R.drawable.t9);
				break;

			}

			arr[0] = bm;

		} else {

			int num = Integer.parseInt(resNum);

			int a = num % 10;// 个位数
			int b = num / 10 % 10;// 十位数
			if (1 == a) {

		       bm = BitmapFactory.decodeResource(res, R.drawable.t1);
			} else if (2 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t2);
			} else if (3 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t3);
			} else if (4 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t4);
			} else if (5 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t5);
			} else if (6 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t6);
			} else if (7 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t7);
			} else if (8 == a) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t8);
			} else if (9 == a) {
				bm = BitmapFactory.decodeResource(res, R.drawable.t9);

			}else if(0==a){
				 bm=BitmapFactory.decodeResource(res, R.drawable.t0);
			}
			arr[1]=bm;

			if (1 == b) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t1);
			} else if (2 == b) {
				bm = BitmapFactory.decodeResource(res, R.drawable.t2);

			} else if (3 == b) {
				bm = BitmapFactory.decodeResource(res, R.drawable.t3);

			} else if (4 == b) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t4);
			} else if (5 == b) {
				bm = BitmapFactory.decodeResource(res, R.drawable.t5);

			} else if (6 == b) {
				bm = BitmapFactory.decodeResource(res, R.drawable.t6);

			} else if (7 == b) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t7);
			} else if (8 == b) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t8);
			} else if (9 == b) {

				bm = BitmapFactory.decodeResource(res, R.drawable.t9);
			}

			arr[0]=bm;
		}

		return arr;
	}

}
