package com.szgs.bbs.answer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.szgs.bbs.BaseActivity;
import com.szgs.bbs.R;
import com.szgs.bbs.common.Constans;
import com.szgs.bbs.common.util.CacheUtils;
import com.szgs.bbs.common.util.LggsUtils;

/**
 * 我的任务
 * 
 * @author db
 * 
 */
public class MineTaskActivity extends BaseActivity implements OnClickListener {

	private TextView tv_title;
	private TextView top_left_tv;
	private TextView minetask_everydaylogin;
	private ImageView minetask_everydaylogin_more;
	private TextView logintask_complete;
	private TextView logintask_recommend;
	private TextView minetask_onequestion;
	private ImageView minetask_onequestion_more;
	private TextView minetask_onequestion_recommend;
	private TextView minetask_onequestion_complete;
	private TextView minetask_givemezan;
	private ImageView minetask_givemezan_more;
	private TextView minetask_givemezan_recommend;
	private TextView minetask_givemezan_complete;
	private ProgressDialog progressdialog;
	private List<MineTaskResponse> TaskList;
	private LinearLayout ll_login;
	private LinearLayout ll_answer_question;
	private LinearLayout ll_answer_zan;
	private LinearLayout ll_gym_top;
	private TextView tv_gym_top_complete;
	private TextView tv_gym_top;
	private ImageView iv_gym_top;
	private LinearLayout ll_answer_adopt;
	private TextView tv_answer_adopt_complete;
	private TextView tv_answer_adopt;
	private ImageView iv_answer_adopt;
	private TextView tv_gymtop;
	private TextView tv_answer_adopt_show;
	private TextView minetask_onequestion_hascomplete;
	private int limit1;
	private int doneCount1;
	private int operationCount1;
	private TextView minetask_givemezan_hascomplete;
	private int limit2;
	private int doneCount2;
	private int operationCount2;
	private TextView task_ljcns;
	private TextView task_ljcns_complete;
	private TextView tv_ljcns10_show;
	private ImageView iv_ljcn;
	private TextView tv_njcn_notic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_task);
		TaskList = new ArrayList<MineTaskResponse>();
		initHeaderView();
		initView();
		sendTaskRequest();
	}

	public void initView() {
		/** 每日登录 */
		ll_login = (LinearLayout) findViewById(R.id.ll_login);
		ll_login.setOnClickListener(this);
		minetask_everydaylogin = (TextView) findViewById(R.id.minetask_everydaylogin);
		minetask_everydaylogin.setOnClickListener(this);
		minetask_everydaylogin_more = (ImageView) findViewById(R.id.minetask_everydaylogin_more);
		logintask_complete = (TextView) findViewById(R.id.logintask_complete);
		logintask_recommend = (TextView) findViewById(R.id.logintask_recommend);
		logintask_complete.setOnClickListener(this);
		/** 每日 回答一个问题 */
		minetask_onequestion_hascomplete = (TextView) findViewById(R.id.minetask_onequestion_hascomplete);
		ll_answer_question = (LinearLayout) findViewById(R.id.ll_answer_question);
		ll_answer_question.setOnClickListener(this);
		minetask_onequestion = (TextView) findViewById(R.id.minetask_onequestion);
		minetask_onequestion.setOnClickListener(this);
		minetask_onequestion_more = (ImageView) findViewById(R.id.minetask_onequestion_more);
		minetask_onequestion_recommend = (TextView) findViewById(R.id.minetask_onequestion_recommend);
		minetask_onequestion_complete = (TextView) findViewById(R.id.minetask_onequestion_complete);
		minetask_onequestion_complete.setOnClickListener(this);
		/** 每日 回答并被点赞 */
		minetask_givemezan_hascomplete = (TextView) findViewById(R.id.minetask_givemezan_hascomplete);
		ll_answer_zan = (LinearLayout) findViewById(R.id.ll_answer_zan);
		ll_answer_zan.setOnClickListener(this);
		minetask_givemezan = (TextView) findViewById(R.id.minetask_givemezan);
		minetask_givemezan.setOnClickListener(this);
		minetask_givemezan_more = (ImageView) findViewById(R.id.minetask_givemezan_more);
		minetask_givemezan_recommend = (TextView) findViewById(R.id.minetask_givemezan_recommend);
		minetask_givemezan_complete = (TextView) findViewById(R.id.minetask_givemezan_complete);
		minetask_givemezan_complete.setOnClickListener(this);
		/** 被管理员置顶 */
		tv_gymtop = (TextView) findViewById(R.id.tv_gymtop);
		tv_gymtop.setOnClickListener(this);
		ll_gym_top = (LinearLayout) findViewById(R.id.ll_gym_top);
		ll_gym_top.setOnClickListener(this);
		tv_gym_top_complete = (TextView) findViewById(R.id.tv_gym_top_complete);
		tv_gym_top = (TextView) findViewById(R.id.tv_gym_top);
		tv_gym_top_complete.setOnClickListener(this);
		iv_gym_top = (ImageView) findViewById(R.id.iv_gym_top);
		/** 回答被采纳 */
		tv_answer_adopt_show = (TextView) findViewById(R.id.tv_answer_adopt_show);
		tv_answer_adopt_show.setOnClickListener(this);
		ll_answer_adopt = (LinearLayout) findViewById(R.id.ll_answer_adopt);
		ll_answer_adopt.setOnClickListener(this);
		tv_answer_adopt_complete = (TextView) findViewById(R.id.tv_answer_adopt_complete);
		tv_answer_adopt = (TextView) findViewById(R.id.tv_answer_adopt);
		tv_answer_adopt_complete.setOnClickListener(this);
		iv_answer_adopt = (ImageView) findViewById(R.id.iv_answer_adopt);
		/** 累计采纳数10次 */
		task_ljcns = (TextView) findViewById(R.id.task_ljcns);
		task_ljcns_complete = (TextView) findViewById(R.id.task_ljcns_complete);
		task_ljcns_complete.setOnClickListener(this);
		tv_ljcns10_show = (TextView) findViewById(R.id.tv_ljcns10_show);
		tv_ljcns10_show.setOnClickListener(this);
		iv_ljcn = (ImageView) findViewById(R.id.iv_ljcn);
		tv_njcn_notic = (TextView) findViewById(R.id.tv_njcn_notic);
	}

	public void initHeaderView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		top_left_tv = (TextView) findViewById(R.id.top_left_tv);
		top_left_tv.setBackgroundResource(R.drawable.navbar_back_selector);
		top_left_tv.setOnClickListener(this);
		tv_title.setText("我的任务");
	}
	
	
	public void sendTaskRequest() {
		progressdialog = new ProgressDialog(MineTaskActivity.this);
		progressdialog.setMessage("正在加载。。。");
		progressdialog.setCancelable(true);
		progressdialog.show();
		AsyncHttpClient client = getClient();
		String url = Constans.URL + "tasks";
		RequestParams params = new RequestParams();
		params.put("userId", CacheUtils.getUserId(MineTaskActivity.this));
		client.setTimeout(5000);
		client.get(url, params, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				progressdialog.dismiss();
				Gson gson = new Gson();
				if (statusCode == 200) {
					for (int i = 0; i < response.length(); i++) {
						try {
							MineTaskResponse taskResponse = gson.fromJson(
									response.get(i).toString(),
									MineTaskResponse.class);
							TaskList.add(taskResponse);
						} catch (JsonSyntaxException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
					setData(TaskList);
				}
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {

				if (statusCode == 401) {
					sendAutoLoginRequest();
				}
				LggsUtils.ShowToast(MineTaskActivity.this,
						LggsUtils.replaceAll(responseString));
				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFinish() {
				progressdialog.dismiss();
				super.onFinish();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				LggsUtils.ShowToast(MineTaskActivity.this, getResources()
						.getString(R.string.network_connection_error));
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_left_tv:
			finish();
			break;
		case R.id.minetask_everydaylogin:
			if (logintask_recommend.getVisibility() == View.GONE) {
				logintask_recommend.setVisibility(View.VISIBLE);
				minetask_everydaylogin_more
						.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				logintask_recommend.setVisibility(View.GONE);
				minetask_everydaylogin_more
						.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.minetask_onequestion:
			if (minetask_onequestion_recommend.getVisibility() == View.GONE) {
				minetask_onequestion_recommend.setVisibility(View.VISIBLE);
				minetask_onequestion_more
						.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				minetask_onequestion_recommend.setVisibility(View.GONE);
				minetask_onequestion_more
						.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.tv_gymtop:
			if (tv_gym_top.getVisibility() == View.GONE) {
				tv_gym_top.setVisibility(View.VISIBLE);
				iv_gym_top
						.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				tv_gym_top.setVisibility(View.GONE);
				iv_gym_top.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.minetask_givemezan:
			if (minetask_givemezan_recommend.getVisibility() == View.GONE) {
				minetask_givemezan_recommend.setVisibility(View.VISIBLE);
				minetask_givemezan_more
						.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				minetask_givemezan_recommend.setVisibility(View.GONE);
				minetask_givemezan_more
						.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.tv_answer_adopt_show:
			if (tv_answer_adopt.getVisibility() == View.GONE) {
				tv_answer_adopt.setVisibility(View.VISIBLE);
				iv_answer_adopt
						.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				tv_answer_adopt.setVisibility(View.GONE);
				iv_answer_adopt.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.tv_ljcns10_show:
			if (tv_njcn_notic.getVisibility() == View.GONE) {
				tv_njcn_notic.setVisibility(View.VISIBLE);
				iv_ljcn.setBackgroundResource(R.drawable.list_task_more_pre_img);
			} else {
				tv_njcn_notic.setVisibility(View.GONE);
				iv_ljcn.setBackgroundResource(R.drawable.list_more_img);
			}
			break;
		case R.id.logintask_complete:
			sendCompleteTask(TaskList.get(0).task.id);
			break;
		case R.id.minetask_onequestion_complete:
			sendCompleteTask(TaskList.get(1).task.id);
			break;
		case R.id.minetask_givemezan_complete:
			sendCompleteTask(TaskList.get(2).task.id);
			break;
		case R.id.tv_gym_top_complete:
			sendCompleteTask(TaskList.get(3).task.id);
			break;
		case R.id.tv_answer_adopt_complete:
			sendCompleteTask(TaskList.get(4).task.id);
			break;
		case R.id.task_ljcns_complete:
			sendCompleteTask(TaskList.get(5).task.id);
			break;

		default:
			break;
		}
	}

	public void setData(List<MineTaskResponse> TaskList) {
		if (TaskList.get(0).operationCount > 0) {
			logintask_complete.setVisibility(View.VISIBLE);
			if (TaskList.get(0).doneRatio.equals("1/1")) {
				logintask_complete.setClickable(false);
				logintask_complete
						.setBackgroundResource(R.drawable.gray_button_nopadding);

			}
		} else {
			logintask_complete.setVisibility(View.GONE);
		}
		if (TaskList.get(1).operationCount > 0) {
			limit1 = TaskList.get(1).task.limitTimes;
			doneCount1 = TaskList.get(1).doneCount;
			operationCount1 = TaskList.get(1).operationCount;
			if (limit1 == doneCount1) {
				minetask_onequestion_complete.setClickable(false);
				minetask_onequestion_complete
						.setBackgroundResource(R.drawable.gray_button_nopadding);
			} else if (doneCount1 != operationCount1) {
				minetask_onequestion_complete.setVisibility(View.VISIBLE);
			} else {
				minetask_onequestion_hascomplete
						.setText(TaskList.get(1).doneRatio);
				minetask_onequestion_hascomplete.setVisibility(View.VISIBLE);
				minetask_onequestion_complete.setVisibility(View.GONE);
			}
		} else {
			minetask_onequestion_complete.setVisibility(View.GONE);
		}
		if (TaskList.get(2).operationCount > 0) {
			limit2 = TaskList.get(2).task.limitTimes;
			doneCount2 = TaskList.get(2).doneCount;
			operationCount2 = TaskList.get(2).operationCount;
			if (limit2 == doneCount2) {
				minetask_givemezan_complete.setClickable(false);
				minetask_givemezan_complete
						.setBackgroundResource(R.drawable.gray_button_nopadding);
			} else if (doneCount2 != operationCount2) {
				minetask_givemezan_complete.setVisibility(View.VISIBLE);
			} else {
				minetask_givemezan_hascomplete
						.setText(TaskList.get(2).doneRatio);
				minetask_givemezan_hascomplete.setVisibility(View.VISIBLE);
				minetask_givemezan_complete.setVisibility(View.GONE);
			}
		} else {
			minetask_givemezan_complete.setVisibility(View.GONE);
		}
		if (TaskList.get(3).operationCount > 0) {
			tv_gym_top_complete.setVisibility(View.VISIBLE);
			if (TaskList.get(3).doneRatio.equals("1/1")) {
				tv_gym_top_complete.setClickable(false);
				tv_gym_top_complete
						.setBackgroundResource(R.drawable.gray_button_nopadding);

			}
		} else {
			tv_gym_top_complete.setVisibility(View.GONE);
		}
		if (TaskList.get(4).operationCount > 0) {
			tv_answer_adopt_complete.setVisibility(View.VISIBLE);
			if (TaskList.get(4).doneRatio.equals("1/1")) {
				tv_answer_adopt_complete.setClickable(false);
				tv_answer_adopt_complete
						.setBackgroundResource(R.drawable.gray_button_nopadding);

			}
		} else {
			tv_answer_adopt_complete.setVisibility(View.GONE);
		}
		if (!TaskList.get(5).doneRatio.equals("1/1")) {
			if (TaskList.get(5).operationCount >= 10) {
				task_ljcns_complete.setVisibility(View.VISIBLE);
				task_ljcns.setVisibility(View.GONE);
			} else {
				task_ljcns.setText(TaskList.get(5).operationRatio);
			}
		} else {
			task_ljcns.setText(TaskList.get(5).operationRatio);
		}
	}

	public void sendCompleteTask(final int taskId) {

		progressdialog = new ProgressDialog(MineTaskActivity.this);
		progressdialog.setMessage("正在加载。。。");
		progressdialog.setCancelable(true);
		progressdialog.show();
		AsyncHttpClient client = getClient();
		String url = Constans.URL + "task/finish";
		RequestParams params = new RequestParams();
		params.put("userId", CacheUtils.getUserId(MineTaskActivity.this));
		params.put("taskId", taskId);
		client.setTimeout(5000);
		client.post(url, params, new JsonHttpResponseHandler() {

			private Toast toast;

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				progressdialog.dismiss();
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {

				if (statusCode == 401) {
					sendAutoLoginRequest();
				} else if (statusCode == 200) {
					LggsUtils.showPicToast(MineTaskActivity.this);
					if (taskId == TaskList.get(0).task.id) {
						logintask_complete.setClickable(false);
						logintask_complete
								.setBackgroundResource(R.drawable.gray_button_nopadding);
					} else if (taskId == TaskList.get(1).task.id) {
						if (operationCount1 == 10) {
							minetask_onequestion_complete.setClickable(false);
							minetask_onequestion_complete
									.setBackgroundResource(R.drawable.gray_button_nopadding);
						} else {
							minetask_onequestion_complete
									.setVisibility(View.GONE);
							minetask_onequestion_hascomplete
									.setText(operationCount1 + "/" + limit1);
							minetask_onequestion_hascomplete
									.setVisibility(View.VISIBLE);
						}

					} else if (taskId == TaskList.get(2).task.id) {
						if (operationCount2 == 10) {
							minetask_givemezan_complete.setClickable(false);
							minetask_givemezan_complete
									.setBackgroundResource(R.drawable.gray_button_nopadding);
						} else {
							minetask_givemezan_complete
									.setVisibility(View.GONE);
							minetask_givemezan_hascomplete
									.setText(operationCount1 + "/" + limit1);
							minetask_givemezan_hascomplete
									.setVisibility(View.VISIBLE);
						}
					} else if (taskId == TaskList.get(3).task.id) {
						tv_gym_top_complete.setClickable(false);
						tv_gym_top_complete
								.setBackgroundResource(R.drawable.gray_button_nopadding);
					} else if (taskId == TaskList.get(4).task.id) {
						tv_answer_adopt_complete.setClickable(false);
						tv_answer_adopt_complete
								.setBackgroundResource(R.drawable.gray_button_nopadding);
					} else if (taskId == TaskList.get(5).task.id) {
						task_ljcns_complete.setClickable(false);
						task_ljcns_complete
								.setBackgroundResource(R.drawable.gray_button_nopadding);
					}
				} else {
					LggsUtils.ShowToast(MineTaskActivity.this,
							LggsUtils.replaceAll(responseString));
				}

				super.onFailure(statusCode, headers, responseString, throwable);
			}

			@Override
			public void onFinish() {
				progressdialog.dismiss();
				super.onFinish();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				LggsUtils.ShowToast(MineTaskActivity.this, getResources()
						.getString(R.string.network_connection_error));
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}
		});

	}
}
