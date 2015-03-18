package com.zcs.app.monkeyalmanac.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.zcs.app.monkeyalmanac.R;

/**
 * 有关文本的一些工具类
 * 
 * @author ZengCS 2014年5月19日17:16:04
 */
public class TextUtils {
	public static final int STRING = 0;
	public static final int E_MAIL = 1;
	public static final int WEBSITE = 2;
	public static final int TELEPHONE = 3;

	/**
	 * 对指定位置的字符进行高亮显示
	 * 
	 * @param src
	 *            源字符串
	 * @param start
	 *            高亮起点
	 * @param end
	 *            高亮终点
	 * @param color
	 *            颜色
	 * 
	 * @return
	 */
	public static SpannableString highlight(CharSequence src, int start, int end, int color) {
		SpannableString spannable = new SpannableString(src);
		CharacterStyle span = new ForegroundColorSpan(color);
		spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spannable;
	}

	public static SpannableString highlight(CharSequence src, String[] strs, int starts[], int ends[], int types[], int color) {
		SpannableString spannable = new SpannableString(src);

		for (int i = 0; i < starts.length; i++) {
			CharacterStyle span = null;
			switch (types[i]) {
			case E_MAIL:
				span = new URLSpan("mailto:" + strs[i]);
				break;
			case WEBSITE:
				span = new URLSpan(strs[i]);
				break;
			case TELEPHONE:
				span = new URLSpan("tel:" + strs[i]);
				break;
			default:
				span = new ForegroundColorSpan(color);
				break;
			}

			spannable.setSpan(span, starts[i], ends[i], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		return spannable;
	}

	public static TextView test(Context context) {
		TextView txtInfo = new TextView(context);
		SpannableString ss = new SpannableString("红色打电话斜体删除线绿色下划线图片:.");
		// 前景色
		ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 超链接
		ss.setSpan(new URLSpan("tel:1008611"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new URLSpan("http://www.maiziedu.com"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new URLSpan("mailto:zengcs@vip.qq.com"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 字体
		ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// 删除线
		ss.setSpan(new StrikethroughSpan(), 7, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 下划线
		ss.setSpan(new UnderlineSpan(), 10, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 绿色
		ss.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// 图片
		Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		ss.setSpan(span, 18, 19, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		txtInfo.setText(ss);
		txtInfo.setMovementMethod(LinkMovementMethod.getInstance());
		return txtInfo;
	}

	/**
	 * 高亮指定字符串
	 * 
	 * @param src
	 *            源字符串
	 * @param target
	 *            需要高亮的字符串
	 * @param color
	 *            高亮颜色
	 * @return
	 */
	public static SpannableString highlight(CharSequence src, String target, int color) {
		int idx = src.toString().indexOf(target);
		int start = 0;
		int end = 0;
		if (idx > -1) {
			start = idx;
			end = idx + target.length();
		}
		return highlight(src, start, end, color);
	}

	/**
	 * @param src
	 * @param target
	 * @param color
	 * @return
	 */
	public static SpannableString highlight(CharSequence src, String[] target, int color) {
		int[] starts = new int[target.length];
		int[] ends = new int[target.length];
		int[] types = new int[target.length];
		String[] strs = new String[target.length];
		int c = 0;
		for (String temp : target) {
			strs[c] = temp;
			int idx = src.toString().indexOf(temp);
			if (idx > -1) {
				starts[c] = idx;
				ends[c] = idx + temp.length();
				if (isEmail(temp)) {
					types[c] = E_MAIL;
				} else if (isWebsite(temp)) {
					types[c] = WEBSITE;
				} else if (isPhoneNumber(temp)) {
					types[c] = TELEPHONE;
				} else {
					types[c] = STRING;
				}
			}
			c++;
		}
		return highlight(src, strs, starts, ends, types, color);
	}

	/**
	 * @param email
	 * @return isEmail
	 */
	public static boolean isEmail(String email) {
		String regex = "[a-zA-Z_0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,3}){1,3}";
		return email.matches(regex);
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String str) {
		String strRegex = "[1][34578]\\d{9}";
		boolean result = str.matches(strRegex);
		return result;
	}

	/**
	 * @param url
	 * @return
	 */
	public static boolean isWebsite(String url) {
		if (isEmpty(url)) {
			return false;
		} else if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("www.")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param str
	 * @return isEmpty
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("") || str.trim().length() == 0 || str.trim().equals("null")) {
			return true;
		}
		return false;
	}
}
