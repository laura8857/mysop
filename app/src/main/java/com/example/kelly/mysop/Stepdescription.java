package com.example.kelly.mysop;

import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;


//p302
public class Stepdescription extends Activity {

    private GestureDetector detector;

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;
    String TAG_ACCOUNT;


    private static String url_des = "http://140.115.82.211/front/mysop_stepdescription1.jsp";
    private ProgressDialog pDialog1;
    JSONParser jParser1 = new JSONParser();
    ArrayList<HashMap<String, String>> productsList1;
    JSONArray products1 = null;

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;

    private static String url_next = "http://140.115.82.211/front/mysop_stepdescription.jsp";
    private static final String TAG_SUCCESS = "success";
    private int TAG_Next=0;
    String sHtml = "";

    WebView ww;
    int TAG_STEP_REMIND = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepdescription);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");

        Log.d("StepDescription",TAG_STEP_NUMBER);

        TextView des_tw2 = (TextView)findViewById(R.id.des_textView2);
        des_tw2.setText(Integer.toString(TAG_STEP_ORDER));


        //TAG_CASE_NUMBER = "J1234";
        //TAG_STEP_NUMBER = "10";
        //TAG_STEP_ORDER = 1;

        //原執行
        //new LoadDes().execute();
        //new CheckNextActivity().execute();
        //RelativeLayout des_rl = (RelativeLayout)findViewById(R.id.des_relative);
        //detector = new GestureDetector(new MySimpleOnGestureListener());
        //des_rl.setOnTouchListener(new MyOnTouchListener());

        //WebView wv = (WebView)findViewById(R.id.webView);
        //wv.loadUrl("https://www.google.com.tw");

        //這一步驟有沒有要記錄(steprecording)
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        step_recordDao mstep_recordDao = new step_recordDao();
        List<step_recordVo> list = null;
        list = mstep_recordDao.selectRaw(mDatabaseHelper, "Step_number="+TAG_STEP_NUMBER);
        if(list.size()!=0){
            TAG_Next = 1;
        }else{
            TAG_Next = 2;
        }
        Log.d("TAG_Next",String.valueOf(TAG_Next));

        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        sop_detailDao msop_detailDao1 = new sop_detailDao();
        List<sop_detailVo> list1 = null;
        list1 = msop_detailDao1.selectRaw(mDatabaseHelper1, "Step_number="+TAG_STEP_NUMBER);
        sHtml = list1.get(0).getStep_intro();
        TAG_STEP_REMIND = Integer.valueOf(list1.get(0).getStep_remind());

        //1語音2手錶
        if(TAG_STEP_REMIND == 1){

            mp = new MediaPlayer();
            try {
                mp.setDataSource("/sdcard/MYSOPTEST/step"+TAG_STEP_NUMBER+".mp3");
                mp.prepare();
            } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
            mp.start();

        }else if(TAG_STEP_REMIND == 2){
            Log.d("DELETEHTMLTAG",sHtml.replaceAll("\\<.*?>",""));
            Notification notification = new NotificationCompat.Builder(getApplication())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Step"+Integer.toString(TAG_STEP_ORDER))
                    .setContentText(sHtml.replaceAll("\\<.*?>",""))
                    .extend(
                            new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                    .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
            Random random = new Random();
            int notificationId = random.nextInt(9999 - 1000) + 1000;
            notificationManager.notify(notificationId, notification);
        }


        detector = new GestureDetector(new MySimpleOnGestureListener());
        ww = (WebView)findViewById(R.id.webView);
        WebSettings settings = ww.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        //settings.setSupportZoom(true);
        //settings.setBuiltInZoomControls(true);
        ww.getSettings().setUseWideViewPort(true);
        ww.getSettings().setLoadWithOverviewMode(true);
        //ww.setInitialScale(100);
        ww.setOnTouchListener(new MyOnTouchListener());

        ww.loadDataWithBaseURL(null, sHtml, "text/html", "utf-8", null);//1225(html用資料庫之資料)

        //ww.loadUrl("file:///android_asset/webview/MySOP.html");//1224(直接檔案下載)
        // ww.loadUrl("file:///sdcard/MYSOPTEST/testhtml.html");
        //ww.loadData("中文", "text/html; charset=utf-8", "UTF-8");

    }

/*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        return detector.onTouchEvent(ev);
    }

/*    String StepName="";
    String StepPurpose="";
    String StepIntro="";

    class LoadDes extends AsyncTask<String, String, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(Stepdescription.this);
            pDialog1.setMessage("Loading..... Please wait...");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();
        }
        protected Integer doInBackground(String... args) {

            int loadsuccess=0;
            String Stepnumber = TAG_STEP_NUMBER;

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("Stepnumber", Stepnumber));

            JSONObject json1 = jParser1.makeHttpRequest(url_des, "GET", params1);

            Log.d("All Products: ", json1.toString());

            try {

                int success = json1.getInt(TAG_SUCCESS);
                if (success == 1) {
                    loadsuccess=1;
                    StepName = json1.getString("stepname");
                    StepPurpose = json1.getString("steppurpose");
                    StepIntro = json1.getString("stepintro");
                } else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return loadsuccess;
        }
        protected void onPostExecute(Integer loadsuccess) {

            pDialog1.dismiss();
            if(loadsuccess==1){
                TextView des_tw4 = (TextView)findViewById(R.id.des_textView4);
                des_tw4.setText(StepName);
                TextView des_tw6 = (TextView)findViewById(R.id.des_textView6);
                des_tw6.setText(StepPurpose);
                TextView des_tw8 = (TextView)findViewById(R.id.des_textView8);
                des_tw8.setText(StepIntro);
            }

        }

    }*/

/*    class CheckNextActivity extends AsyncTask<String, String, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(StepActionControl.this);
            //pDialog.setMessage("Loading..... Please wait...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(false);
            //pDialog.show();
        }
        protected Integer doInBackground(String... args) {

            int NextActivity=0;
            String Stepnumber = TAG_STEP_NUMBER;

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));

            JSONObject json = jParser.makeHttpRequest(url_next, "GET", params);

            Log.d("All Products: ", json.toString());

            try {

                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    NextActivity=1;
                    TAG_Next = NextActivity;
                } else if(success == 6) {
                    NextActivity=2;
                    TAG_Next = NextActivity;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return NextActivity;
        }
        protected void onPostExecute(Integer NextActivity) {
            if(NextActivity==0){
               Log.d("下一步","讀取失敗");
            }
        }

    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stepdescription, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MyOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return detector.onTouchEvent(event);
        }
    }
    class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // TODO Auto-generated method stub
            if ((e1.getX() - e2.getX()) > 50) {//说明是左滑
                Intent intent = new Intent();
                if(TAG_Next==1){
                    intent.setClass(Stepdescription.this, Steprecording.class);
                }else{
                    intent.setClass(Stepdescription.this, StepCutControl.class);
                }
                Bundle bundle = new Bundle();
                bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
                bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                intent.putExtras(bundle);//將參數放入intent
                startActivity(intent);
                // 设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                //finish();
                return true;
            } else if((e1.getX() - e2.getX()) < -50) { //11/29新加右滑
                Intent intent2 = new Intent();
                intent2.setClass(Stepdescription.this, StepActionControl.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
                bundle2.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                bundle2.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                intent2.putExtras(bundle2);//將參數放入intent
                startActivity(intent2);
                //切換畫面，右近左出
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                finish();
                return true;
            }
                return false;
        }

    }
    @Override
    public void onPause(){
        if(mp != null){
            mp.pause();
            //mp.release();
        }
            super.onPause();
    }
    @Override
    protected void onDestroy() {
        if(mp != null) {
            mp.release();
        }
        super.onDestroy();
    }
    @Override
    public void onResume(){
        if(mp != null) {
            try {
                mp.prepare();
            } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
            mp.start();
        }
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //orm account
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(Stepdescription.this);
            member_accountDao mmember_accountDao = new  member_accountDao();
            List<member_accountVo> memberlist = null;
            memberlist = mmember_accountDao.selectColumns(mDatabaseHelper4, "FIELD_Account");
            TAG_ACCOUNT = memberlist.get(0).getAccount();

            Bundle bundle = new Bundle();
            bundle.putString("TAG_ACCOUNT",TAG_ACCOUNT);
            Intent it = new Intent(this,Mysop.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            it.putExtras(bundle);//將參數放入intent
            startActivity(it);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
