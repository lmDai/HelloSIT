package com.example.luhongcheng;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luhongcheng.UI.JellyInterpolator;
import com.example.luhongcheng.util.EventUtil;
import com.example.luhongcheng.util.IntentUtil;
import com.example.luhongcheng.util.SpUtil;

/**
 * Created by alex233 on 2018/4/21.
 */


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password;
    private TextView btnLogin, btnVisitor;
    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;
    private LinearLayout mName, mPsw;
    private String usernameId;
    private String passwordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_startflash);
        btnLogin = (TextView) findViewById(R.id.main_btn_login);
        btnVisitor = (TextView) findViewById(R.id.main_btn_nologin);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        //initView();

        restoreInfo();
        test();

        //为login设置点击事件
        //这里需要改进，login导致动画没了
        btnLogin.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(LoginActivity.this, JellyInterpolator.class);
                usernameId = username.getText().toString();
                passwordId = password.getText().toString();
                if (TextUtils.isEmpty(usernameId) || TextUtils.isEmpty(passwordId)) {
                    //判断账号密码长度
                    EventUtil.showToast(LoginActivity.this, "请输入学号和密码");
                } else if (usernameId.length() == 10 & passwordId.length() >= 4) {
                    memInfo(usernameId, passwordId);
                    IntentUtil.getInstance().goActivity(LoginActivity.this, MainFragmentActivity.class);
                    finish();
                } else {
                    EventUtil.showToast(LoginActivity.this, "格式错误，请重试");
                }
            }
        });
        //无密码登录
        btnVisitor.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.getInstance().goActivity(LoginActivity.this, MainFragmentActivity.class);
                finish();
            }
        });
        bindView();
    }

    private void bindView() {
        ImageView share = (ImageView) findViewById(R.id.shareapp);
        share.setOnClickListener(new ShareText());
    }

    //分享文字至所有第三方软件
    public class ShareText implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "SITschool上应大学生助手集成OA系统部分查询及资讯功能，可在Android端实现查询成绩，查询电费，查询第二课堂，查询考试安排等等一系列功能，目前在酷安已发布，快来下载吧：https://www.coolapk.com/apk/187672");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }

    private void test() {
        SharedPreferences sp = getSharedPreferences("userid", 0);
        username.setText(sp.getString("username", ""));
        password.setText(sp.getString("password", ""));

        if (username.length() == 10 && password.length() >= 4) {
            Intent intent3 = new Intent(LoginActivity.this, MainFragmentActivity.class);
            startActivity(intent3);
        }
    }


    /*保存密码-嘻嘻*/
    private void memInfo(String usernameId, String passwordId) {
        SpUtil.putString(LoginActivity.this, Constants.USERNAME, usernameId);
        SpUtil.putString(LoginActivity.this, Constants.PASSWORD, passwordId);

    }

    private void restoreInfo() {
        username.setText(SpUtil.getString(LoginActivity.this, Constants.USERNAME));
        password.setText(SpUtil.getString(LoginActivity.this, Constants.PASSWORD));
    }

    /**
     * 保存密码就到这里了
     */

    @Override
    public void onClick(View view) {
        mWidth = btnLogin.getMeasuredWidth();
        mHeight = btnLogin.getMeasuredHeight();
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);
        inputAnimator(mInputLayout, mWidth, mHeight);
    }


    private void inputAnimator(final View view, float w, float h) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recovery();
                    }
                }, 2000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }


    /*如果登录失败就恢复原来的样子*/
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }


}