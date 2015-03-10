package com.miaomoe.www.miaomoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Fragment class1;
    private Fragment class3;
    private Fragment class5;
    private Fragment class7;
    private Fragment class9;
    public mSimpleAdapter classList;
    private ArrayList<GridView> gVList;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;
    private ImageButton btn5;
    private ImageView backPic;
    private ViewPager viewPager;
    private FragmentPagerAdapter fpa;
    private List<Fragment> fList;
    private boolean tomorrow;

    private FragmentManager fm;
    private FragmentTransaction ft;
    public List<Map<String,Object>> dataList1;
    public List<Map<String,Object>> dataList2;
    public List<Map<String,Object>> dataList3;
    public List<Map<String,Object>> dataList4;
    public List<Map<String,Object>> dataList5;
    public List<Map<String,Object>> dataListEmpty;


    private mHandler mainHandler=new mHandler();
    private JSONArray data;
    int z=9-Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    int x=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    android.support.v7.app.ActionBar abr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        abr=getSupportActionBar();
        abr.setSubtitle("\t 努力学习吧\\(≧ω≦)");
        Resources res=getResources();
        abr.setBackgroundDrawable(res.getDrawable(R.drawable.bar2));
        setContentView(R.layout.activity_main);
        initView();
        setPage(1);
        initSetting();

        NetPost getClass=new NetPost(mainHandler,this);
        getClass.execute(z,x);

        initEvent();

    }

    private void initSetting() {
        String backpic=getSharedPreferences("setting",0).getString("BackPic",null);
        String cookie=getSharedPreferences("setting",0).getString("Cookie",null);
        String user=getSharedPreferences("setting",0).getString("user",null);
        String pw=getSharedPreferences("setting",0).getString("pw",null);
        if(backpic!=null&&!backpic.equals("无")){
            backPic.setImageBitmap(BitmapFactory.decodeFile(backpic));
        }
        if(cookie!=null&&user!=null&&pw!=null){
            new NetLogin(mainHandler,this,getSharedPreferences("setting",0).edit()).execute("reLog",cookie);
        }

        if(z<=0){
            z=1;
        }
    }

    private void initEvent() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private LinearLayout k1;
            private LinearLayout k2;
            private LinearLayout k3;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*if(k1==null){
                    k1= (LinearLayout) findViewById(R.id.bottom_item1);
                    k2= (LinearLayout) findViewById(R.id.bottom_item2);
                    k3= (LinearLayout) findViewById(R.id.bottom_item3);
                }
                if(positionOffsetPixels>0){
                    k1.setLeft(-positionOffsetPixels/2);
                    k2.setLeft(-positionOffsetPixels/2);
                    k3.setLeft(-positionOffsetPixels/2);
                }else{
                    k1.setLeft(positionOffsetPixels/2);
                    k2.setLeft(positionOffsetPixels/2);
                    k3.setLeft(positionOffsetPixels/2);
                }*/
            }

            @Override
            public void onPageSelected(int position) {
                int s=position*2+1;
                int e=position*2+2;
                if(findViewById(R.id.topTextView2)!=null){
                    ((TextView)findViewById(R.id.topTextView2)).setText("节次: "+s+"-"+e);
                }
                setbtn(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.vper);

        btn1= (ImageButton) findViewById(R.id.imageButton1);
        btn2= (ImageButton) findViewById(R.id.imageButton2);
        btn3= (ImageButton) findViewById(R.id.imageButton3);
        btn4= (ImageButton) findViewById(R.id.imageButton4);
        btn5= (ImageButton) findViewById(R.id.imageButton5);
        backPic= (ImageView) findViewById(R.id.back_pic);
        /*fm=getSupportFragmentManager();
        ft = fm.beginTransaction();*/

        class1=new class1(this);
        class3=new class2(this);
        class5=new class3(this);
        class7=new class4(this);
        class9=new class5(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /*Intent text=new Intent(this,Setting.class);
            startActivityForResult(text, 100);*/
            Intent setting=new Intent(this,Settings.class);
            startActivityForResult(setting,300);
            return true;
        }else if(id == R.id.action_date){
            if(item.getTitle()=="今天"){
                NetPost getClass=new NetPost(mainHandler,this);
                tomorrow=false;
                getClass.execute(z,x);
                ((TextView)findViewById(R.id.loading)).setText("Loading...");
                item.setTitle("明天");
            }else {
                NetPost getClass=new NetPost(mainHandler,this);
                tomorrow=true;
                getClass.execute(z,(x+1>7)?1:x+1);
                ((TextView)findViewById(R.id.loading)).setText("Loading...");
                item.setTitle("今天");
            }
            return true;
        }else if(id==R.id.action_FindEmpty){
            Intent text=new Intent(this,FindEmpty.class);
            startActivityForResult(text, 100);
            return true;
        }else if(id==R.id.action_login){
            Intent text=new Intent(this,LoginActivity.class);
            startActivityForResult(text,400);
            return true;
        }else if(id==R.id.action_myClass){
            Intent text=new Intent(this,FindEmpty.class);
            startActivityForResult(text,500);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void toUpdate(String json){
        try {
            data=(JSONArray) new JSONTokener(json).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateView();
    }

    public void updateView(){
        Log.i("a", "完成,已经回调");
        try {
            setClassData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        fList=new ArrayList<>();
        fList.add(class1);
        fList.add(class3);
        fList.add(class5);
        fList.add(class7);
        fList.add(class9);

        fpa=new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        setbtn(1);
        setx();

    }
    private void setx(){
        String[] xTypes={"末日","天","一","二","三","四","五","六"};
        ((TextView)findViewById(R.id.loading)).setText("第"+z+"周 星期"+(tomorrow?(xTypes[(x+1>7)?1:x+1]+" (明天)"):xTypes[x]));
    }
    private void initGrid(GridView grid) throws JSONException {
        SimpleAdapter classList=new SimpleAdapter(this,dataList1,R.layout.item_classroom,new String[]{"class","name","cls"},new int[]{R.id.class_room,R.id.class_name,R.id.class_cls});
        grid.setAdapter(classList);
        grid.setOnItemClickListener(new ClassRoomInfo(MainActivity.this));
    }

    private int setDataList(List<Map<String,Object>> dataList, int i,int last,int s,int e) throws JSONException {
        int classRoom,next;
        String[] classTypes={"专业限选","专业任选","专业必修","通识必修","学科基础","其他"};
        String[] testTypes={"考查","考试"};
        /*String[] learnTypes={"理论课"};*/
        String name,classRoomS;
        Map<String,Object> map=new HashMap<>();
        name=data.getJSONObject(i).getString("name");
        classRoomS=data.getJSONObject(i).getString("where");
        if(classRoomS.equals("null")){
            classRoomS="未知";
            next=10;
        }else{
            classRoom= Integer.parseInt(classRoomS);
            next=classRoom%100;
            for(;last<next;++last){
                String emptyRoom=String.valueOf(classRoom-(classRoom%100)+last).replaceFirst("^1","A").replaceFirst("^2","B").replaceFirst("^3","C");
                Map<String,Object> inMap= new HashMap<>();
                inMap.put("class", emptyRoom);
                inMap.put("name","空");
                inMap.put("cls",s+"-"+e);
                dataList.add(inMap);
            }
        }
        String classroom=classRoomS.replaceFirst("^1","A").replaceFirst("^2","B").replaceFirst("^3","C");
        String toClasses="";
        JSONArray temp=data.getJSONObject(i).getJSONArray("toClass");
        int tempL=temp.length();
        for (int m=0;m<tempL;m++){
            toClasses+="\n\t"+(m+1)+"."+temp.get(m);
        }

        map.put("id",data.getJSONObject(i).getString("id"));
        map.put("class", classroom);
        map.put("name", name);
        map.put("cls",s+"-"+e);
        map.put("teacher",data.getJSONObject(i).getString("teacher"));
        map.put("toClass",toClasses);
        map.put("learnTime",data.getJSONObject(i).getString("learnTime"));
        map.put("testType",testTypes[data.getJSONObject(i).getInt("testType")]);
        map.put("classType",classTypes[data.getJSONObject(i).getInt("classType")]);
        dataList.add(map);
        return next;
    }

    private void setClassData() throws JSONException {
        int s;
        int e;
        int next,last=10,lat,k;
        String name;
        int classRoom;
        dataList1=new ArrayList<>();
        dataList2=new ArrayList<>();
        dataList3=new ArrayList<>();
        dataList4=new ArrayList<>();
        dataList5=new ArrayList<>();

        for(int i=0;i<data.length();i++){
            try {
                s=data.getJSONObject(i).getJSONObject("cla").getInt("start");
            } catch (JSONException e1) {
                e1.printStackTrace();
                s=0;
            }
            try {
                e=data.getJSONObject(i).getJSONObject("cla").getInt("end");
            } catch (JSONException e1) {
                e1.printStackTrace();
                e=0;
            }

            if(1<=s&&e<=2){
                last=setDataList(dataList1,i,last,s,e)+1;
            }else if(3<=s&&e<=4){
                last=setDataList(dataList2,i,last,s,e)+1;
            }else if(5<=s&&e<=6){
                last=setDataList(dataList3,i,last,s,e)+1;
            }else if(7<=s&&e<=8){
                last=setDataList(dataList4,i,last,s,e)+1;
            }else if(9<=s&&e<=10){
                last=setDataList(dataList5,i,last,s,e)+1;
            }

        }
    }

    public void setPage(int page) {
        switch (page){
            case 1:
                if(class1!=null){
                    viewPager.setCurrentItem(0);
                }break;
            case 2:
                if(class3!=null){
                    viewPager.setCurrentItem(1);
                }break;
            case 3:
                if(class5!=null){
                    viewPager.setCurrentItem(2);
                }break;
            case 4:
                if(class7!=null){
                    viewPager.setCurrentItem(3);
                }break;
            case 5:
                if(class9!=null){
                    viewPager.setCurrentItem(4);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==333&&data!=null){
            backPic.setImageBitmap(BitmapFactory.decodeFile(data.getDataString()));
        }
        else if(resultCode==334){
            backPic.setImageResource(R.drawable.background);
        }
    }


    public void setbtn(int btn) {
        btn1.setBackgroundColor(Color.parseColor("#60000000"));
        btn2.setBackgroundColor(Color.parseColor("#60000000"));
        btn3.setBackgroundColor(Color.parseColor("#60000000"));
        btn4.setBackgroundColor(Color.parseColor("#60000000"));
        btn5.setBackgroundColor(Color.parseColor("#60000000"));
        switch (btn){
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

    class mHandler extends Handler {
        public void handleMessage(Message msg) {
            if(msg.getData().getBoolean("LogResult",false)){
                Toast.makeText(getApplicationContext(), "登入成功",
                        Toast.LENGTH_SHORT).show();
            }else{
                MainActivity.this.toUpdate(msg.getData().getString("data"));
            }

        }
    }
}