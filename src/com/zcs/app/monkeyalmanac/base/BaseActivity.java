package com.zcs.app.monkeyalmanac.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public abstract class BaseActivity extends Activity implements OnClickListener {
	/** Constants */
	protected final static String TAG = "MonkeyAlmanac";

	/** Variable */
	protected Gson gson;

	/** 屏幕尺寸 */
	private int screenWidth;
	private int screenHeight;

	/** 标题栏View */
	protected View titlebarView;
	protected ImageView titleBtnLeft, titleBtnRight;
	protected TextView titleTxtCenter, titleTxtRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setBackgroundDrawable(null);
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		screenWidth = mDisplayMetrics.widthPixels;
		screenHeight = mDisplayMetrics.heightPixels;
		gson = new Gson();
	}

	/**
	 * 初始化布局，内部调用initTitlebar()和initComponent()
	 */
	protected void init() {
		initTitlebar();
		initComponent();
	}

	/**
	 * 初始化Titlebar，在super.init()中自动调用此方法
	 */
	protected abstract void initTitlebar();

	/**
	 * 初始化组件，在super.init()中自动调用此方法
	 */
	protected abstract void initComponent();

	private static Toast mToast;
	private static Context context;

	protected void showToast(String text) {
		if (context == null) {
			context = this;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	/**
	 * 获取版本名
	 * 
	 * @return 当前应用的版本名
	 */
	public String getVersionName() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "0.0.0";
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
