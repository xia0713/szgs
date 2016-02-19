package com.szgs.bbs.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.szgs.bbs.LggsApplication;
import com.szgs.bbs.R;
import com.szgs.bbs.common.Constans;

public class LggsUtils {

	public static void ainim1(Activity activity) {
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);
		// //此为自定义的动画效果
	}

	/**
	 * 不带参数之间的activity跳转
	 * 
	 * @param packageContext
	 * @param cls
	 */
	public static void StartIntent(Context packageContext, Class cls) {
		Intent myIntent = new Intent(packageContext, cls);
		packageContext.startActivity(myIntent);
		// ainim1((Activity) packageContext);
	}

	public static void StartIntent(Context context, Class cls, Bundle bundle) {
		Intent myIntent = new Intent(context, cls);
		myIntent.putExtras(bundle);
		context.startActivity(myIntent);
	}

	public static void ShowToast(Context context, String text) {
		text = replaceAll(text);
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示普通提示信息对话框
	 * 
	 * @param context
	 * @param title
	 *            对话框标题
	 * @param msg
	 *            提示语句
	 */
	public static void showCustomeDialog(Context context, String title,
			String msg) {

		showConfirmOrCancelDialog(context, title, msg, null, null, false, true);
	}

	/***
	 * 确认/取消 提示对话框
	 */
	public static void showConfirmOrCancelDialog(Context context, String title,
			String msg, final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean showCancelBtn,
			boolean canceledOnTouchOutside) {

		
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_cancel_dialog);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText(title);

		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText(msg);

		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		if (!showCancelBtn) {
			return;
		} else {
			Button cancelBtn = (Button) view
					.findViewById(R.id.confirm_cancel_dialog_cancel);
			cancelBtn.setVisibility(View.VISIBLE);
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (cancelListener != null) {

						cancelListener.onClick(v);
					}
					dialog.dismiss();
				}
			});
		}
	}

	/***
	 * 确认退出
	 */
	public static void showConfirmExitDialog(Context context, String title,
			String msg, final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean showCancelBtn,
			boolean canceledOnTouchOutside) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_exit_dialog);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText(title);

		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText(msg);

		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		if (!showCancelBtn) {
			return;
		} else {
			Button cancelBtn = (Button) view
					.findViewById(R.id.confirm_cancel_dialog_cancel);
			cancelBtn.setVisibility(View.VISIBLE);
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (cancelListener != null) {

						cancelListener.onClick(v);
					}
					dialog.dismiss();
				}
			});
		}
	}

	/***
	 * 举报对话框
	 */
	public static void showDelationDialog(Context context,
			final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean canceledOnTouchOutside) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_cancel_dialog1);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText("温馨提示");
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText("请确认您所举报的内容");
		tv_message.setVisibility(View.VISIBLE);
		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText("违反了用户协议或者含有不良信息");

		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setText("举报");
		confirmBtn.setTextColor(context.getResources().getColor(R.color.red));
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		Button cancelBtn = (Button) view
				.findViewById(R.id.confirm_cancel_dialog_cancel);
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setTextColor(context.getResources().getColor(R.color.black));
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (cancelListener != null) {

					cancelListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
	}

	/***
	 * 举报对话框
	 */
	public static void showDelationSelfDialog(Context context,
			final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean canceledOnTouchOutside) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_cancel_dialog1);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText("温馨提示");
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setVisibility(View.GONE);
		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText("请确认您要举报自己的回答");
		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setText("举报");
		confirmBtn.setTextColor(context.getResources().getColor(R.color.red));
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		Button cancelBtn = (Button) view
				.findViewById(R.id.confirm_cancel_dialog_cancel);
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setTextColor(context.getResources().getColor(R.color.black));
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (cancelListener != null) {

					cancelListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
	}

	/***
	 * 确认收货地址
	 */
	public static void showExchangeDialog(Context context,
			final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean canceledOnTouchOutside) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_cancel_dialog1);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText("兑换成功");
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText("您的奖品将以快递上门");
		tv_message.setVisibility(View.VISIBLE);
		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText("请确保您填写的信息无误");

		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setText("确认兑换");
		confirmBtn.setTextColor(context.getResources().getColor(
				R.color.header_bg));
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		Button cancelBtn = (Button) view
				.findViewById(R.id.confirm_cancel_dialog_cancel);
		cancelBtn.setVisibility(View.VISIBLE);
		cancelBtn.setText("返回修改");
		cancelBtn.setTextColor(context.getResources().getColor(
				R.color.header_bg));
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (cancelListener != null) {

					cancelListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
	}

	/***
	 * 举报对话框
	 */
	public static void showFailDialog(Context context,
			final OnClickListener confirmListener,
			final OnClickListener cancelListener, boolean canceledOnTouchOutside) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.confirm_cancel_dialog2);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		View view = dialog.getWindow().getDecorView();
		TextView titleTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_title);
		titleTv.setText("提交失败");
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText("您输入的内容中含有不良信息");
		tv_message.setVisibility(View.VISIBLE);
		TextView messageTv = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_message);
		messageTv.setText("请修改后再提交");
		TextView confirmBtn = (TextView) view
				.findViewById(R.id.confirm_cancel_dialog_ok);
		confirmBtn.setText("确认");
		confirmBtn.setTextColor(context.getResources().getColor(R.color.red));
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (confirmListener != null) {

					confirmListener.onClick(v);
				}
				dialog.dismiss();
			}
		});
	}

	/**
	 * 手机格式化加空格 123 2245 445
	 */
	public static String formatPhone(String phone) {
		if (phone != null && phone.length() == 11) {
			StringBuilder stringBuilder = new StringBuilder(phone.substring(0,
					3));
			stringBuilder.append("\t").append(phone.substring(3, 7))
					.append("\t").append(phone.substring(7, phone.length()));
			return stringBuilder.toString();
		}
		return phone;
	}

	/** 获取固定格式的日期字符串（2015年1月28日） */
	@SuppressLint("SimpleDateFormat")
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date(System.currentTimeMillis());
		return sdf.format(date);
	}

	/** 检测指定程序是否已安装 */
	public static boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {

			String name = pinfo.get(i).packageName;
			// Log.v(TAG, name);

			if (name.equalsIgnoreCase(packageName)) {

				return true;
			}
		}
		return false;
	}

	/**
	 * 判断手机是否已经开启上网功能
	 * 
	 * @return boolean 是否开启上网功能
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		boolean netIsConnection = false;
		// 获取当前的网络连接是否可用
		if (null == networkInfo) {
			// Toast.makeText(context, "网络连接不可用，请重新设置。",
			// Toast.LENGTH_SHORT).show();
			netIsConnection = false;
		} else {
			boolean available = networkInfo.isAvailable();
			if (available) {
				// Toast.makeText(context, "当前网络已连接...",
				// Toast.LENGTH_SHORT).show();
				netIsConnection = true;
			} else {
				// Toast.makeText(context, "当前网络未连接...",
				// Toast.LENGTH_SHORT).show();
				netIsConnection = false;
			}
		}
		return netIsConnection;
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 格式化手机号（18888888888 -> 188 8888 8888）
	 * 
	 * @param telephoneNumber
	 *            待格式的手机号
	 * @return
	 */
	public static String formateTelephoneNumber(String telephoneNumber) {

		telephoneNumber = replaceBlank(telephoneNumber);

		if (telephoneNumber.length() < 10) {
			return telephoneNumber;
		}
		return telephoneNumber;
	}

	/** 去除字符串中所有空格 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/** 存储路径 */
	public static String getDownloadFileDir() {

		return FileUtils.getDefaultPath();
	}

	/** 获取附件保存路径 */
	public static String getErpDownloadDir() {

		return FileUtils.getDefaultPath() + "erp/";
	}

	/**
	 * 判断日期是否为3天以内
	 * 
	 * @param date
	 *            日期格式2015-03-17
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean isDateNew(String dateString) {
		boolean flag = false;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate;
		try {
			oldDate = simpleDateFormat.parse(dateString);

			Calendar oldCalendar = Calendar.getInstance();
			oldCalendar.setTime(oldDate);
			int oldYear = oldCalendar.get(Calendar.YEAR);
			int oldDayOfYear = oldCalendar.get(Calendar.DAY_OF_YEAR);

			Calendar newCalendar = Calendar.getInstance();
			int newYear = newCalendar.get(Calendar.YEAR);
			int newDayOfYear = newCalendar.get(Calendar.DAY_OF_YEAR);

			if (oldYear == newYear && newDayOfYear - oldDayOfYear < 3) {

				flag = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 输入一个日期，得到合适的描述（昨天、前天之类）
	 * 
	 * @param dateString
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDayDescreption(String dateString) {

		String result = dateString;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate;
		try {
			oldDate = simpleDateFormat.parse(dateString);

			Calendar oldCalendar = Calendar.getInstance();
			oldCalendar.setTime(oldDate);
			int oldYear = oldCalendar.get(Calendar.YEAR);
			int oldDayOfYear = oldCalendar.get(Calendar.DAY_OF_YEAR);

			Calendar newCalendar = Calendar.getInstance();
			int newYear = newCalendar.get(Calendar.YEAR);
			int newDayOfYear = newCalendar.get(Calendar.DAY_OF_YEAR);

			if (oldYear == newYear) {

				int days = newDayOfYear - oldDayOfYear;
				switch (days) {
				case 0:

					result = "今天";
					break;
				case 1:

					result = "昨天";
					break;
				case 2:

					result = "前天";
					break;

				default:
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 在非Activity中显示dialog
	 * 
	 * @param context
	 * @param title
	 * @param content
	 */
	public static void showAlertInBroadcast(Context context, String title,
			String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(content).setCancelable(true)
				.setPositiveButton("确定", null);
		AlertDialog alert = builder.create();
		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alert.show();
	}

	/** 获取版本号(内部识别号) */
	public static int getVersionCode(Context context)//
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/** 获取版本号 */
	public static String getVersionName(Context context) {

//		return context.getResources().getString(R.string.version_name);
		PackageInfo pi = null;
		 
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(context.getPackageName(),
	                PackageManager.GET_CONFIGURATIONS);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(pi!=null){
	    	return pi.versionName;
	    }else{
	    	return context.getResources().getString(R.string.version_name);
	    }
	    	
	}

	/** 上一次按下返回键所记录的时间 */
	private static Long mLastTimePressedBack = 0L;
	/** 两次按下返回键决定是否退出程序的间隔 */
	private static final Long INTERVAL_TIME = 2000L;

	/** 监听返回键事件（按两次退出程序） */
	public static void onBackPressed(Context context) {

		if (System.currentTimeMillis() - mLastTimePressedBack <= INTERVAL_TIME) {

			LggsApplication.getInstance().exit();

		} else {

			mLastTimePressedBack = System.currentTimeMillis();
			Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();

		}
	}

	/** 毫秒转日期 */
	@SuppressLint("SimpleDateFormat")
	public static String milliseconds2Date(long milliseconds) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 计算时间差
	 * 
	 * @param startTime
	 * @param endTime
	 *            系统当前时间
	 * @param format
	 *            格式，例如yyyy-MM-dd HH:mm:ss，可以为null。
	 * @return
	 */
	public static String caculateTime(String startTime, String endTime,
			String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd;
		if (format != null) {
			sd = new SimpleDateFormat(format);
		} else {
			sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		if (startTime == null) {
			return "";
		}
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (day >= 1) {
			// if (day < 2) {
			// return day + " 天";
			// } else {
			String[] strs = startTime.split(" ")[0].split("-");
			return strs[0] + "-" + strs[1] + "-" + strs[2];
			// }
		} else if (hour >= 1) {
			return hour + " 小时前";
		} else if (min >= 1) {
			return min + " 分钟前";
		} else {
			return "刚刚";
		}

	}

	/**
	 * 获取当前时间，24小时制
	 * 
	 * @return
	 */
	public static String getCurrentTime() {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return sDateFormat.format(new java.util.Date());
	}

	public static DisplayImageOptions inImageLoaderOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.me_avatar_default) // resource
																	// or
																	// drawable
				.showImageForEmptyUri(R.drawable.me_avatar_default) // resource
																	// or
																	// drawable
				.showImageOnFail(R.drawable.me_avatar_default) // resource or
																// drawable
				.delayBeforeLoading(1000).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(false) // default
				.build();
		return options;
	}

	/**
	 * 判断电话号码 ^((13[0-9])|(147)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(147)|(15[^4,\\D])|(18[0-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	/**
	 * 将字符串转为字节并返回字节的长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getStringLength(String str) {
		byte[] byBuffer = new byte[200];
		int count = 0;
		try {
			byBuffer = str.getBytes("gb2312");
			count = byBuffer.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 将字符串中所有的"替换成空格
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceAll(String str) {
		String endstr = "";
		endstr = str.replaceAll("\"", "");

		return endstr;
	}

	/**
	 * 调整Activity整个窗口的alpha，制作变暗效果
	 * 
	 * @param alpha
	 */
	public static void setWindowAlpha(Context context, float alpha) {
		WindowManager.LayoutParams lp = ((Activity) context).getWindow()
				.getAttributes();
		lp.alpha = alpha;
		((Activity) context).getWindow().setAttributes(lp);
	}

	/**
	 * 计算点赞数目，>=1000以k显示，等等
	 * 
	 * @param num
	 */
	public static String caculateLikeNum(int num) {
		float result;
		if ((result = num / 1000) > 1) {
			return result + "k";
		} else {
			return num + "";
		}
	}

	public static String tolow(String str) {

		char[] str1 = str.toCharArray();
		StringBuffer newStr = new StringBuffer();
		for (int i = str.length() - 1; i >= 0; i--) {
			// System.out.println(str1[i]);
			if ('A' <= str1[i] && str1[i] <= 'Z') {
				newStr.append(String.valueOf(str1[i]).toLowerCase());
			} else {
				newStr.append(String.valueOf(str1[i]));
			}
		}
		return newStr.toString();
	}

	public static SpannableStringBuilder SetTextColor(Context context,
			String content, String keyword) {

		// int fstart = content.indexOf(keyword);
		// int fstart = tolow(content).indexOf(tolow(keyword));
		int fstart = content.toLowerCase().indexOf(keyword.toLowerCase());
		if (fstart == -1) {
			return null;
		}
		int fend = fstart + keyword.length();
		SpannableStringBuilder style = new SpannableStringBuilder(content);
		style.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						R.color.mine_blue)), fstart, fend,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}

	public static SpannableStringBuilder SetTextColorOrange(Context context,
			String content, String keyword) {
		int fstart = content.indexOf(keyword);
		if (fstart == -1) {
			return null;
		}
		int fend = fstart + keyword.length();
		SpannableStringBuilder style = new SpannableStringBuilder(content);
		style.setSpan(
				new ForegroundColorSpan(context.getResources().getColor(
						R.color.orange)), fstart, fend,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}

	public static void showMyToast(Context context, String title,
			String content1, String content2) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.mytoast_layout, null);
		TextView toast_name = (TextView) view.findViewById(R.id.toast_name);
		TextView toast_content1 = (TextView) view
				.findViewById(R.id.toast_content1);
		TextView toast_content2 = (TextView) view
				.findViewById(R.id.toast_content2);
		toast_content1.setText(content1);
		if (title == null) {
			toast_name.setVisibility(View.GONE);
		} else {
			toast_name.setVisibility(View.VISIBLE);
			toast_name.setText(title);
		}
		if (content2 != null) {
			toast_content2.setText(content2);
			toast_content2.setVisibility(View.VISIBLE);
		}
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

	public static void showPicToast(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.pic_toast, null);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

	public static void showAdoptaccessToast(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.pic_toast1, null);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

	/**
	 * 设置listview的高度，必须在setadapter之后
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除
		listView.setLayoutParams(params);
	}

	// 保存文件内容
	public static void writeFiles(Context context, String content) {
		try {
			// 打开文件获取输出流，文件不存在则自动创建
			FileOutputStream fos = context.openFileOutput(Constans.FILENAME,
					context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读取文件内容
	public static String readFiles(Context context) {
		String content = null;
		try {
			FileInputStream fis = context.openFileInput(Constans.FILENAME);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			content = baos.toString();
			fis.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static void deleteFiles(Context context) {
		try {
			context.deleteFile(Constans.FILENAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
