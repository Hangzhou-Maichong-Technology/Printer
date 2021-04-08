package com.hzmct.printer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Bitmap.Config;

import com.lvrenyang.io.Pos;

import java.io.IOException;
import java.io.InputStream;

public class PrintUtil {
	public static void printText(Pos pos, int nPrintWidth, boolean bCutter, boolean bBeeper, int nCount) {
		for(int i = 0; i < nCount; ++i)
		{
			if(!pos.GetIO().IsOpened()) {
				break;
			}

			pos.POS_FeedLine();
			pos.POS_S_Align(1);
			pos.POS_TextOut("Printer 测试\r\n", 0, 0, 0, 0, 0, 0);
			pos.POS_FeedLine();
			pos.POS_S_SetBarcode("2021", 0, 72, 3, 60, 0, 2);
			pos.POS_FeedLine();
		}

		if(bBeeper) {
			pos.POS_Beep(1, 5);
		}

		if(bCutter) {
			pos.POS_CutPaper();
		}
	}

	public static void printQrCode(Pos pos, int nPrintWidth, boolean bCutter, boolean bBeeper, int nCount) {
		for(int i = 0; i < nCount; ++i)
		{
			if(!pos.GetIO().IsOpened()) {
				break;
			}

			pos.POS_FeedLine();
			pos.POS_S_Align(1);
			pos.POS_S_SetQRcode("欢迎使用二维码 Welcome", 8, 0, 3);
			pos.POS_FeedLine();
			pos.POS_S_SetBarcode("2021", 0, 72, 3, 60, 0, 2);
			pos.POS_FeedLine();
		}

		if(bBeeper) {
			pos.POS_Beep(1, 5);
		}

		if(bCutter) {
			pos.POS_CutPaper();
		}
	}

	public static void printTicket(Pos pos, int nPrintWidth, boolean bCutter, boolean bBeeper, int nCount) {
		for(int i = 0; i < nCount; ++i)
		{
			if(!pos.GetIO().IsOpened()) {
				break;
			}

			pos.POS_FeedLine();
			pos.POS_S_Align(1);
			pos.POS_TextOut("Printer 测试\r\n", 0, 0, 0, 0, 0, 0);
			pos.POS_TextOut("扫二维码\r\n", 0, 0, 0, 0, 0, 0);
			pos.POS_S_SetQRcode("欢迎使用二维码 Welcome", 8, 0, 3);
			pos.POS_FeedLine();
			pos.POS_S_SetBarcode("2021", 0, 72, 3, 60, 0, 2);
			pos.POS_FeedLine();
		}

		if(bBeeper) {
			pos.POS_Beep(1, 5);
		}

		if(bCutter) {
			pos.POS_CutPaper();
		}
	}

	public static String ResultCodeToString(int code) {
		switch (code) {
			case 0:
				return "打印成功";
			case -1:
				return "连接断开";
			case -2:
				return "写入失败";
			case -3:
				return "读取失败";
			case -4:
				return "打印机脱机";
			case -5:
				return "打印机缺纸";
			case -7:
				return "实时状态查询失败";
			case -8:
				return "查询状态失败";
			case -6:
			default:
				return "未知错误";
		}
	}
	
	/**
	 * 从Assets中读取图片
	 */
	public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
		Bitmap image = null;
		AssetManager am = ctx.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
	
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
		// load the origial Bitmap
		Bitmap BitmapOrg = bitmap;

		int width = BitmapOrg.getWidth();
		int height = BitmapOrg.getHeight();
		int newWidth = w;
		int newHeight = h;

		// calculate the scale
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the Bitmap
		matrix.postScale(scaleWidth, scaleHeight);
		// if you want to rotate the Bitmap
		// matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);

		// make a Drawable from Bitmap to allow to set the Bitmap
		// to the ImageView, ImageButton or what ever
		return resizedBitmap;
	}
	
	public static Bitmap getTestImage(int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();

		paint.setColor(Color.WHITE);
		canvas.drawRect(0, 0, width, height, paint);
		
		paint.setColor(Color.BLACK);
		for(int i = 0; i < 8; ++i) {
			for(int x = i; x < width; x += 8) {
				for(int y = i; y < height; y += 8) {
					canvas.drawPoint(x, y, paint);
				}
			}
		}
		return bitmap;
	}
}
