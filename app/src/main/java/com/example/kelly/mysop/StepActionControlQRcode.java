package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepActionControlQRcode extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //讀取 qrcode 圖片
    private static String url_create_product = "http://140.115.80.237/front/mysop_ACqrcode.jsp";
    private static final String TAG_SUCCESS = "success";

    TextView textView1;

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;
    String TAG_ACCOUNT;

    String QRcode = "";

    private RuntimeExceptionDao<sop_detailVo, Integer> sop_detailRuntimeDao;
    private sop_detailDao msop_detailDao;

    int TAG_START_REMIND = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_action_control_qrcode);
        textView1 = (TextView) findViewById(R.id.qrcode);

        TextView ss = (TextView)findViewById(R.id.textView2);
        TextView StartMessage = (TextView)findViewById(R.id.textView17);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");
        ss.setText(Integer.toString(TAG_STEP_ORDER));

        Log.d("QRcode",TAG_STEP_NUMBER);

        //orm 用stepnumber去抓資料庫的東西
        msop_detailDao = new sop_detailDao();
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        List<sop_detailVo> list = null;
        list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number =" + TAG_STEP_NUMBER);
        Log.d("抓", list.get(0).getStart_value1());
        QRcode = list.get(0).getStart_value1();
        TAG_START_REMIND = Integer.valueOf(list.get(0).getStart_remind());
        StartMessage.setText(list.get(0).getStart_message());
        //Log.d("TAG_START_REMIND",list.get(0).getStart_remind());

        //1語音2手錶3響鈴
        if(TAG_START_REMIND == 1){
            mp = new MediaPlayer();
            try {
                mp.setDataSource("/sdcard/MYSOPTEST/start"+TAG_STEP_NUMBER+".mp3");
                mp.prepare();
            } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
            mp.start();
        }else if(TAG_START_REMIND == 2){
            Notification notification = new NotificationCompat.Builder(getApplication())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Step"+Integer.toString(TAG_STEP_ORDER))
                    .setContentText("啟動方式：QRcode啟動")
                    .extend(
                            new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                    .build();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
            Random random = new Random();
            int notificationId = random.nextInt(9999 - 1000) + 1000;
            notificationManager.notify(notificationId, notification);
        }else if(TAG_START_REMIND == 3){

            Log.d("TAG_START_REMIND","震動響鈴");
            //震動
            Vibrator mVibrator;
            mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            mVibrator.vibrate(1000);
            //響鈴
            NotificationManager mgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification nt = new Notification();
            nt.defaults = Notification.DEFAULT_SOUND;
            int soundId = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
            mgr.notify(soundId, nt);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_action_control_qrcode, menu);
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

    public void qrcheck (View v){
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        if(getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size()==0)
        {
            // 未安裝
            Toast.makeText(this, "請至 Play 商店安裝 ZXing 條碼掃描器", Toast.LENGTH_LONG).show();
        }
        else
        {
            // SCAN_MODE, 可判別所有支援的條碼
            // QR_CODE_MODE, 只判別 QRCode
            // PRODUCT_MODE, UPC and EAN 碼
            // ONE_D_MODE, 1 維條碼
            intent.putExtra("SCAN_MODE", "SCAN_MODE");

            // 呼叫ZXing Scanner，完成動作後回傳 1 給 onActivityResult 的 requestCode 參數
            startActivityForResult(intent, 1);
        }
    }


    // 接收 ZXing 掃描後回傳來的結果
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                // ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                textView1.setText(contents.toString());
                //new CheckQR().execute();

                //判斷qrcode一樣否
                String QRnumber=StepActionControlQRcode.this.textView1.getText().toString();
                if( QRnumber.equals(QRcode)){
                   Intent intent1 = new Intent(StepActionControlQRcode.this,Stepdescription.class);
                   Bundle bundle = new Bundle();
                   bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
                   bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                   bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                   intent1.putExtras(bundle);//將參數放入intent
                   startActivity(intent1);
                    finish();
                }else {
                   AlertDialog.Builder dialog = new AlertDialog.Builder(StepActionControlQRcode.this);
                   dialog.setTitle("");
                   dialog.setMessage("目標錯誤，請尋找正確QR code");
                   dialog.show();
                }

            }
            else
            if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this, "取消掃描", Toast.LENGTH_LONG).show();
            }
        }
    }

//    class CheckQR extends AsyncTask<String, String, Integer> {
//
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(StepActionControlQRcode.this);
//            pDialog.setMessage("QR code checking.... Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        protected Integer doInBackground(String... args) {
//
//            //String Stepnumber="1";
//            String Stepnumber = TAG_STEP_NUMBER;
//            String QRnumber=StepActionControlQRcode.this.textView1.getText().toString();
//            int valoreOnPostExecute = 0;
//
//
//
//            ArrayList params = new ArrayList();
//            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));
//            params.add(new BasicNameValuePair("QRnumber", QRnumber));
//
//            JSONObject json = StepActionControlQRcode.this.jsonParser.makeHttpRequest(StepActionControlQRcode.url_create_product, "POST", params);
//
//
//            try {
//                int e = json.getInt(TAG_SUCCESS);
//                if(e == 1) {
//                    Intent intent = new Intent(StepActionControlQRcode.this,Stepdescription.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
//                    bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
//                    bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
//                    intent.putExtras(bundle);//將參數放入intent
//                    startActivity(intent);
//
//                }else if(e == 6){
//
//                    valoreOnPostExecute=1;
//
//                }
//            } catch (JSONException var9) {
//                var9.printStackTrace();
//            }
//
//            return valoreOnPostExecute;
//        }
//
//        protected void onPostExecute(Integer valoreOnPostExecute) {
//
//            pDialog.dismiss();
//            if(valoreOnPostExecute==1){
//                AlertDialog.Builder dialog = new AlertDialog.Builder(StepActionControlQRcode.this);
//                dialog.setTitle("");
//                dialog.setMessage("目標錯誤，請尋找正確QR code");
//                dialog.show();
//
//            }
//        }
//    }
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
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepActionControlQRcode.this);
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
