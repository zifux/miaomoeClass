package com.miaomoe.www.miaomoe;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public List<Map<String, Object>> dataList1;
    public List<Map<String, Object>> dataList2;
    public List<Map<String, Object>> dataList3;
    public List<Map<String, Object>> dataList4;
    public List<Map<String, Object>> dataList5;
    public List<Fragment> fList;
    private FragmentPagerAdapter fpa;

    private ClassTable classTable;
    public mSimpleAdapter classList;
    private ArrayList<GridView> gVList;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;
    private ImageButton btn5;
    private FrameLayout fragment_room;
    private FrameLayout fragment_class;
    private FrameLayout fragment_my;
    private FrameLayout main_setting_return;
    private ImageButton topbar_setting;
    private LinearLayout main_setting;
    private LinearLayout bottom_room;
    private LinearLayout bottom_class;
    private LinearLayout bottom_my;
    private LinearLayout main_up;
    private LinearLayout menu_tongji;
    private RelativeLayout menu_login;
    private LinearLayout menu_tomorrow;
    private LinearLayout menu_setting;
    public TextView my_yuanxi;
    public TextView my_zhuanye;
    public TextView my_banji;
    public TextView my_xuehao;
    public TextView my_xingming;
    private Boolean menuShowing = false;


    private ImageView backPic;
    public ViewPager viewPager;
    private boolean tomorrow;

    private FragmentManager fm;
    private FragmentTransaction ft;
    public List<Map<String, Object>> dataListEmpty;


    int z = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 9;
    int x = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    android.support.v7.app.ActionBar abr;
    private Button my_logout;
    private RelativeLayout my_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initSetting();
        new BackGround(this).execute(String.valueOf(Do.GetRoom), String.valueOf(z), String.valueOf(x));
        initEvent();
    }

    private void initSetting() {
        SharedPreferences setting = getSharedPreferences("setting", 0);
        String backpic = setting.getString("BackPic", null);
        String sid = setting.getString("user", null);
        String pw = setting.getString("pw", null);
        Boolean login = setting.getBoolean("login", false);
        if (backpic != null && !backpic.equals("无")) {
            backPic.setImageBitmap(BitmapFactory.decodeFile(backpic));
        }
        if (login) {
            new BackGround(this).execute(String.valueOf(Do.InitLogin), "15", "15");
            //new BackGround(this).execute(String.valueOf(Do.GetClassTable));
            //new BackGround(this).execute(String.valueOf(Do.getStudentScore), "14", "15");
            menu_login.setVisibility(View.GONE);
        }else{
            my_logout.setVisibility(View.GONE);
        }
        if (z <= 0) {
            z = 1;
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vper);
        btn1 = (ImageButton) findViewById(R.id.imageButton1);
        btn2 = (ImageButton) findViewById(R.id.imageButton2);
        btn3 = (ImageButton) findViewById(R.id.imageButton3);
        btn4 = (ImageButton) findViewById(R.id.imageButton4);
        btn5 = (ImageButton) findViewById(R.id.imageButton5);
        backPic = (ImageView) findViewById(R.id.back_pic);
        bottom_class = (LinearLayout) findViewById(R.id.bottombar_class);
        bottom_room = (LinearLayout) findViewById(R.id.bottombar_room);
        bottom_my = (LinearLayout) findViewById(R.id.bottombar_my);
        topbar_setting = (ImageButton) findViewById(R.id.topbar_setting);
        fragment_class = (FrameLayout) findViewById(R.id.frame_class);
        fragment_room = (FrameLayout) findViewById(R.id.frame_room);
        fragment_my = (FrameLayout) findViewById(R.id.frame_my);
        my_zhuanye = (TextView) findViewById(R.id.my_zhuanye);
        my_yuanxi = (TextView) findViewById(R.id.my_yuanxi);
        my_banji = (TextView) findViewById(R.id.my_banji);
        my_xuehao = (TextView) findViewById(R.id.my_xuehao);
        my_xingming = (TextView) findViewById(R.id.my_xingming);

        main_up = (LinearLayout) findViewById(R.id.main_up);
        main_setting_return = (FrameLayout) findViewById(R.id.main_setting_return);
        main_setting = (LinearLayout) findViewById(R.id.main_setting);
        main_up.setVisibility(View.GONE);
        main_setting.setScaleY(0F);
        main_setting.setTranslationY(-70F);

        menu_login = (RelativeLayout) findViewById(R.id.menu_login);
        my_setting = (RelativeLayout) findViewById(R.id.my_setting);
        my_logout = (Button) findViewById(R.id.my_logout);
        menu_setting = (LinearLayout) findViewById(R.id.menu_setting);
        menu_tomorrow = (LinearLayout) findViewById(R.id.menu_tomorrow);
        menu_tongji = (LinearLayout) findViewById(R.id.menu_tongji);
        /*fm=getSupportFragmentManager();
        ft = fm.beginTransaction();*/
    }

    private void initEvent() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        bottom_class.setOnClickListener(this);
        bottom_room.setOnClickListener(this);
        bottom_my.setOnClickListener(this);
        topbar_setting.setOnClickListener(this);
        main_setting_return.setOnClickListener(this);
        menu_tongji.setOnClickListener(this);
        menu_setting.setOnClickListener(this);
        menu_login.setOnClickListener(this);
        my_logout.setOnClickListener(this);
        menu_tomorrow.setOnClickListener(this);
        my_setting.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private LinearLayout k1;
            private LinearLayout k2;
            private LinearLayout k3;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int s = position * 2 + 1;
                int e = position * 2 + 2;
                if (findViewById(R.id.topTextView2) != null) {
                    ((TextView) findViewById(R.id.topTextView2)).setText("节次: " + s + "-" + e);
                }
                setbtn(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class menuItem {
        private MenuItem item = null;

        menuItem(MenuItem i) {
            item = i;
        }

        menuItem(View t) {
        }

        public void setText(String text) {
            if (item != null) {
                item.setTitle(text);
            } else {
                ((TextView) findViewById(R.id.textView12)).setText(text);
            }
        }

        public CharSequence getText() {
            if (item != null) {
                return item.getTitle().toString();
            } else {
                return ((TextView) findViewById(R.id.textView12)).getText();
            }
        }
    }

    private boolean menuSelect(int id, menuItem item) {
        if (id == R.id.action_settings || id == R.id.menu_setting || id == R.id.my_setting) {
            /*Intent text=new Intent(this,Setting.class);
            startActivityForResult(text, 100);*/
            Intent setting = new Intent(this, Settings.class);
            startActivityForResult(setting, 300);
            if (menuShowing) {
                menuShow(false);
            }
            return true;
        } else if (id == R.id.action_date || id == R.id.menu_tomorrow) {
            if (item.getText() == "今天") {
                new BackGround(this).execute(String.valueOf(Do.GetRoom), String.valueOf(z), String.valueOf(x));
                tomorrow = false;
                ((TextView) findViewById(R.id.loading)).setText("Loading...");
                item.setText("明天");
            } else {
                new BackGround(this).execute(String.valueOf(Do.GetRoom), String.valueOf(z), String.valueOf((x + 1 > 7) ? 1 : x + 1));
                tomorrow = true;
                ((TextView) findViewById(R.id.loading)).setText("Loading...");
                item.setText("今天");
            }
            if (menuShowing) {
                menuShow(false);
            }
            return true;
        } else if (id == R.id.action_FindEmpty || id == R.id.menu_tongji) {
            Intent text = new Intent(this, Statistics.class);
            startActivityForResult(text, 100);
            if (menuShowing) {
                menuShow(false);
            }
            return true;
        } else if (id == R.id.action_login || id == R.id.menu_login) {
            Intent text = new Intent(this, LoginActivity.class);
            if (getSharedPreferences("setting", 0).getBoolean("login", false)) {
                showInfo("已经登录");
                return true;
            }
            startActivityForResult(text, 400);
            if (menuShowing) {
                menuShow(false);
            }
            return true;
        }/*else if(id==R.id.action_myClass){
            Intent text=new Intent(this,FindEmpty.class);
            startActivityForResult(text,500);
            return true;
        }*/
        return false;
    }

    private void showInfo(String info) {
        Toast.makeText(getApplicationContext(), info,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (menuSelect(id, new menuItem(item))) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setx() {
        String[] xTypes = {"末日", "天", "一", "二", "三", "四", "五", "六"};
        ((TextView) findViewById(R.id.loading)).setText("第" + z + "周 星期" + (tomorrow ? (xTypes[(x + 1 > 7) ? 1 : x + 1] + " (明天)") : xTypes[x]));
    }

    public void setPage(int page) {
        switch (page) {
            case 1:
                viewPager.setCurrentItem(0);
                break;
            case 2:
                viewPager.setCurrentItem(1);
                break;
            case 3:
                viewPager.setCurrentItem(2);
                break;
            case 4:
                viewPager.setCurrentItem(3);
                break;
            case 5:
                viewPager.setCurrentItem(4);

        }
    }

    private void menuShow(Boolean show) {
        if (show) {
            ObjectAnimator one = ObjectAnimator.ofFloat(main_setting, "scaleY", 0F, 1F);
            ObjectAnimator two = ObjectAnimator.ofFloat(main_setting, "translationY", -70F, 0F);
            ObjectAnimator three = ObjectAnimator.ofFloat(main_setting_return, "alpha", 0F, 0.3F);
            ObjectAnimator four = ObjectAnimator.ofFloat(main_setting, "alpha", 0F, 1F);
            AnimatorSet i = new AnimatorSet();
            i.play(one).with(two).with(four).before(three);
            i.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    main_up.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    menuShowing = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            i.setDuration(200).start();
        } else {
            ObjectAnimator rone = ObjectAnimator.ofFloat(main_setting, "scaleY", 1F, 0F);
            ObjectAnimator rtwo = ObjectAnimator.ofFloat(main_setting, "translationY", 0F, -70F);
            ObjectAnimator rthree = ObjectAnimator.ofFloat(main_setting_return, "alpha", 0.3F, 0F);
            ObjectAnimator rfour = ObjectAnimator.ofFloat(main_setting, "alpha", 1F, 0F);
            AnimatorSet ri = new AnimatorSet();
            ri.play(rone).with(rtwo).with(rfour).after(rthree);
            ri.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    main_up.setVisibility(View.GONE);
                    menuShowing = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            ri.setDuration(200).start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_setting:
                if (menuShowing) {
                    menuShow(false);
                } else {
                    menuShow(true);
                }
                break;
            case R.id.main_setting_return:
                if (menuShowing) {
                    menuShow(false);
                }
                break;
            case R.id.imageButton1:
                setbtn(1);
                setPage(1);
                break;
            case R.id.imageButton2:
                setbtn(2);
                setPage(2);
                break;
            case R.id.imageButton3:
                setbtn(3);
                setPage(3);
                break;
            case R.id.imageButton4:
                setbtn(4);
                setPage(4);
                break;
            case R.id.imageButton5:
                setbtn(5);
                setPage(5);
            case R.id.bottombar_room:
                fragment_room.setVisibility(View.VISIBLE);
                fragment_class.setVisibility(View.GONE);
                fragment_my.setVisibility(View.GONE);
                break;
            case R.id.bottombar_class:
                fragment_room.setVisibility(View.GONE);
                fragment_class.setVisibility(View.VISIBLE);
                fragment_my.setVisibility(View.GONE);
                break;
            case R.id.bottombar_my:
                fragment_my.setVisibility(View.VISIBLE);
                fragment_room.setVisibility(View.GONE);
                fragment_class.setVisibility(View.GONE);
                break;
            case R.id.my_logout:
                getSharedPreferences("setting",0).edit().putString("user",null).putString("pw",null).putString("Cookie",null).putBoolean("login",false).putBoolean("classTableInDB",false).commit();
                my_logout.setVisibility(View.GONE);
                menu_login.setVisibility(View.VISIBLE);
                showInfo("已经注销");
                break;
        }
        menuSelect(v.getId(), new menuItem(v));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 333 && data != null) {
            backPic.setImageBitmap(BitmapFactory.decodeFile(data.getDataString()));
        } else if (resultCode == 334) {
            //showInfo("壁纸已更改");
        } else if (resultCode == 444) {
            new BackGround(this).execute(String.valueOf(Do.GetClassTable));
            new BackGround(this).execute(String.valueOf(Do.getStudentScore), "14", "15");
            my_logout.setVisibility(View.VISIBLE);
            menu_login.setVisibility(View.GONE);
        }
    }


    public void setbtn(int btn) {
        btn1.setBackgroundColor(Color.parseColor("#60000000"));
        btn2.setBackgroundColor(Color.parseColor("#60000000"));
        btn3.setBackgroundColor(Color.parseColor("#60000000"));
        btn4.setBackgroundColor(Color.parseColor("#60000000"));
        btn5.setBackgroundColor(Color.parseColor("#60000000"));
        switch (btn) {
            case 1:
                btn1.setBackgroundColor(Color.parseColor("#96dc5a00"));
                break;
            case 2:
                btn2.setBackgroundColor(Color.parseColor("#968dd7dc"));
                break;
            case 3:
                btn3.setBackgroundColor(Color.parseColor("#9694bfdc"));
                break;
            case 4:
                btn4.setBackgroundColor(Color.parseColor("#96c8dca3"));
                break;
            case 5:
                btn5.setBackgroundColor(Color.parseColor("#96ffe89d"));
                break;
        }
    }

    public void upClassRoomView() {
        fList = new ArrayList<>();
        fList.add(oneClassPage.createClassPage(this));
        fList.add(oneClassPage2.createClassPage(this));
        fList.add(oneClassPage3.createClassPage(this));
        fList.add(oneClassPage4.createClassPage(this));
        fList.add(oneClassPage5.createClassPage(this));
        fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fList.get(position);
            }

            @Override
            public int getCount() {
                return fList.size();
            }
        };
        viewPager.setAdapter(fpa);
        //activity.getSupportFragmentManager().beginTransaction().commit();
        setPage(1);
        setbtn(1);
        setx();

    }


/*    class mHandler extends Handler {
        public void handleMessage(Message msg) {
            if(msg.getData().getBoolean("LogResult",false)){
                Toast.makeText(getApplicationContext(), "登入成功",
                        Toast.LENGTH_SHORT).show();
            }else{
                MainActivity.this.toUpdate(msg.getData().getString("data"));
            }

        }
    }*/
}