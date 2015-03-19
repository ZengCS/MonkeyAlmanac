package com.zcs.app.monkeyalmanac;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcs.app.monkeyalmanac.base.BaseActivity;
import com.zcs.app.monkeyalmanac.entity.DataItem;
import com.zcs.app.monkeyalmanac.entity.ResponseDataEntity;
import com.zcs.app.monkeyalmanac.utils.Constants;
import com.zcs.app.monkeyalmanac.utils.DialogParam;
import com.zcs.app.monkeyalmanac.utils.DialogUtil;

/**
 * @author ZengCS
 * @since 2015年3月18日15:45:56
 */
@SuppressLint("SimpleDateFormat")
public class MainActivity extends BaseActivity {
	/** Constants */
	private static final String CURR_TITLE = "程序猿黄历";

	/** Variables */
	private Date today = new Date();
	private int iday;
	private List<DataItem> dataItems;
	private ResponseDataEntity responseData;
	private LayoutInflater inflater;

	/** Views */
	private TextView tvToday, tvGoddess, tvDrinks, tvDirection;
	private ViewGroup goodLayout, badLayout;
	private Dialog msgDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inflater = getLayoutInflater();

		super.init();
		initToday();

		initData();

		// 设置信息
		tvToday.setText(getTodayString());
		tvGoddess.setText(getStar(random(iday, 6) % 5 + 1));
		tvDrinks.setText(getDrinks());
		String direction = Constants.DIRECTIONS[random(iday, 2) % Constants.DIRECTIONS.length];
		tvDirection.setText(direction);
		pickTodaysLuck();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("data.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
			is.close();
			responseData = gson.fromJson(sb.toString(), ResponseDataEntity.class);
			dataItems = responseData.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebtn_left_act:// 返回
			finish();
			break;
		case R.id.titletxt_right_act:// 关于
			if (msgDialog == null) {
				initMsgDialog();
			}
			msgDialog.show();
			break;
		default:
			break;
		}
	}

	private void initMsgDialog() {
		StringBuffer sb = new StringBuffer();
		sb.append("E-Mail：zengcs@vip.qq.com");
		sb.append("\nAndroid版源码GitHub地址");
		sb.append("\nhttps://github.com/zcs417327734/MonkeyAlmanac");
		sb.append("\n\n数据参考：");
		sb.append("\nhttp://bejson.com/tools/huangli/");
		sb.append("\nhttp://runjs.cn/detail/ydp3it7b");

		DialogParam param = new DialogParam(this, sb.toString(), false);
		StringBuffer sb_title = new StringBuffer();
		sb_title.append(getString(R.string.app_name) + " Ver" + getVersionName());
		sb_title.append("\n作者：曾成顺");
		param.setTitle(sb_title.toString());
		param.setOkBtnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				msgDialog.dismiss();
			}
		});
		String[] highStrs = { "https://github.com/zcs417327734/MonkeyAlmanac", "http://bejson.com/tools/huangli/", "http://runjs.cn/detail/ydp3it7b" };
		int highColor = getResources().getColor(R.color.C4);
		msgDialog = DialogUtil.createMessageDialog(param, highStrs, highColor);
	}

	@Override
	protected void initTitlebar() {
		// TODO 初始化标题栏
		super.titlebarView = findViewById(R.id.common_titlebar);

		// 左侧返回按钮
		super.titleBtnLeft = (ImageView) titlebarView.findViewById(R.id.titlebtn_left_act);
		// super.titleBtnLeft.setOnClickListener(this);
		super.titleBtnLeft.setVisibility(View.INVISIBLE);

		// 中间文字
		super.titleTxtCenter = (TextView) titlebarView.findViewById(R.id.titletxt_center_act);
		super.titleTxtCenter.setText(CURR_TITLE);

		// 右侧文字
		super.titleTxtRight = (TextView) titlebarView.findViewById(R.id.titletxt_right_act);
		super.titleTxtRight.setOnClickListener(this);
	}

	@Override
	protected void initComponent() {
		tvToday = (TextView) findViewById(R.id.tv_today);
		tvGoddess = (TextView) findViewById(R.id.tv_goddess);
		tvDrinks = (TextView) findViewById(R.id.tv_drinks);
		tvDirection = (TextView) findViewById(R.id.tv_direction);

		goodLayout = (ViewGroup) findViewById(R.id.layout_good);
		badLayout = (ViewGroup) findViewById(R.id.layout_bad);
	}

	private String getDrinks() {
		List<String> drinks = new ArrayList<String>();
		for (String s : Constants.DRINKS) {
			drinks.add(s);
		}
		List<String> result = pickRandomDrink(drinks, 2);
		return result.get(0) + "，" + result.get(1);
	}

	private List<String> pickRandomDrink(List<String> array, int size) {
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < array.size(); i++) {
			result.add(array.get(i));
		}

		for (int j = 0; j < array.size() - size; j++) {
			int index = random(iday, j) % result.size();
			result.remove(index);
		}

		return result;
	}

	private void initToday() {
		System.out.println("today:" + today);
		String str = new SimpleDateFormat("yyyyMMdd").format(today);
		// iday = Long.parseLong(str);
		iday = Integer.parseInt(str);
		System.out.println("iday:" + iday);
	}

	@SuppressWarnings("deprecation")
	private String getTodayString() {
		String todayString = new SimpleDateFormat("yyyy年MM月dd日").format(today);
		return "今天是" + todayString + " 星期" + Constants.WEEKS[today.getDay()];
	}

	private String getStar(int num) {
		String result = "";
		int i = 0;
		while (i < num) {
			result += "★";
			i++;
		}
		while (i < 5) {
			result += "☆";
			i++;
		}
		return result;
	}

	private int random(int dayseed, int indexseed) {
		int n = dayseed % 11117;
		for (int i = 0; i < 100 + indexseed; i++) {
			n = n * n;
			n = n % 11117; // 11117 是个质数
		}
		return n;
	}

	// 生成今日运势
	private void pickTodaysLuck() {
		List<DataItem> _activities = filter(dataItems);

		int numGood = random(iday, 98) % 3 + 2;
		int numBad = random(iday, 87) % 3 + 2;
		List<DataItem> eventArr = pickRandomActivity(_activities, numGood + numBad);

		for (int i = 0; i < numGood; i++) {
			addToGood(eventArr.get(i));
		}

		for (int i = 0; i < numBad; i++) {
			addToBad(eventArr.get(numGood + i));
		}
	}

	// 添加到“宜”
	private void addToGood(DataItem event) {
		System.out.println("good event:" + event.getName());
		View v = inflater.inflate(R.layout.item_event, null);
		TextView name = (TextView) v.findViewById(R.id.tv_event_name);
		TextView desc = (TextView) v.findViewById(R.id.tv_event_desc);
		name.setText(event.getName());
		if (TextUtils.isEmpty(event.getGood())) {
			desc.setVisibility(View.GONE);
		} else {
			desc.setText(event.getGood());
		}
		goodLayout.addView(v);
	}

	// 添加到“不宜”
	private void addToBad(DataItem event) {
		System.out.println("bad event:" + event.getName());
		View v = inflater.inflate(R.layout.item_event, null);
		TextView name = (TextView) v.findViewById(R.id.tv_event_name);
		TextView desc = (TextView) v.findViewById(R.id.tv_event_desc);
		name.setText(event.getName());
		if (TextUtils.isEmpty(event.getBad())) {
			desc.setVisibility(View.GONE);
		} else {
			desc.setText(event.getBad());
		}
		badLayout.addView(v);
	}

	// 去掉一些不合今日的事件
	private List<DataItem> filter(List<DataItem> activities) {
		List<DataItem> result = new ArrayList<DataItem>(0);

		// 周末的话，只留下 weekend = true 的事件
		if (isWeekend()) {
			for (int i = 0; i < activities.size(); i++) {
				if (activities.get(i).isWeekend()) {
					result.add(activities.get(i));
				}
			}
			return result;
		}
		return activities;
	}

	@SuppressWarnings("deprecation")
	private boolean isWeekend() {
		return today.getDay() == 0 || today.getDay() == 6;
	}

	// 从 activities 中随机挑选 size 个
	private List<DataItem> pickRandomActivity(List<DataItem> activities, int size) {
		List<DataItem> picked_events = pickRandom(activities, size);

		for (int i = 0; i < picked_events.size(); i++) {
			// picked_events[i] = parse(picked_events[i]);
			parse(picked_events.get(i));
		}

		return pickRandom(activities, size);
	}

	// 从数组中随机挑选 size 个
	private List<DataItem> pickRandom(List<DataItem> array, int size) {
		List<DataItem> result = new ArrayList<DataItem>();

		for (int i = 0; i < array.size(); i++) {
			result.add(array.get(i));
		}

		for (int j = 0; j < array.size() - size; j++) {
			int index = random(iday, j) % result.size();
			result.remove(index);
		}

		return result;
	}

	// 解析占位符并替换成随机内容
	private DataItem parse(DataItem result) {
		// DataItem result = new DataItem();
		// // clone
		// result.setName(event.getName());
		// result.setGood(event.getGood());
		// result.setBad(event.getBad());

		if (result.getName().indexOf("%v") != -1) {
			result.setName(result.getName().replace("%v", Constants.VAR_NAMES[random(iday, 12) % Constants.VAR_NAMES.length]));
		}

		if (result.getName().indexOf("%t") != -1) {
			result.setName(result.getName().replace("%t", Constants.TOOLS[random(iday, 11) % Constants.TOOLS.length]));
		}

		if (result.getName().indexOf("%l") != -1) {
			result.setName(result.getName().replace("%l", String.valueOf((random(iday, 12) % 247 + 30))));
		}

		return result;
	}
}
