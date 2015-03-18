package com.zcs.app.monkeyalmanac.utils;

import android.content.Context;
import android.view.View.OnClickListener;

/**
 * 自定义Dialog参数类
 * 
 * @author ZengCS
 * @since 2014年10月16日
 */
public class DialogParam {
	private Context context;// 父级容器
	private String title;// 标题
	private String msg;// 信息
	private boolean textIsSelectable = false;// 是否可以选择文字
	private String okBtnStr;// 确定按钮文字
	private String cancelBtnStr;// 取消按钮文字
	private boolean cancelable = false;// 是否可以取消,即:点击物理返回键或者Dialog外部,关闭Dialog
	private OnClickListener okBtnClickListener;// 确定按钮点击事件
	private OnClickListener cancelBtnClickListener;// 取消按钮点击事件

	public DialogParam() {
	}

	/**
	 * @param context
	 *            容器
	 * @param msg
	 *            提示消息
	 * @param cancelable
	 *            点击back键或者点击Dialog外部是否关闭Dialog
	 */
	public DialogParam(Context context, String msg, boolean cancelable) {
		super();
		this.context = context;
		this.msg = msg;
		this.cancelable = cancelable;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOkBtnStr() {
		return okBtnStr;
	}

	public void setOkBtnStr(String okBtnStr) {
		this.okBtnStr = okBtnStr;
	}

	public String getCancelBtnStr() {
		return cancelBtnStr;
	}

	public void setCancelBtnStr(String cancelBtnStr) {
		this.cancelBtnStr = cancelBtnStr;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public OnClickListener getOkBtnClickListener() {
		return okBtnClickListener;
	}

	public void setOkBtnClickListener(OnClickListener okBtnClickListener) {
		this.okBtnClickListener = okBtnClickListener;
	}

	public OnClickListener getCancelBtnClickListener() {
		return cancelBtnClickListener;
	}

	public void setCancelBtnClickListener(OnClickListener cancelBtnClickListener) {
		this.cancelBtnClickListener = cancelBtnClickListener;
	}

	public boolean isTextIsSelectable() {
		return textIsSelectable;
	}

	public void setTextIsSelectable(boolean textIsSelectable) {
		this.textIsSelectable = textIsSelectable;
	}
}
