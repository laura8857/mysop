package com.example.kelly.mysop;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

import java.io.IOException;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepActionControlArtificial extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://140.115.80.237/front/mysop_ACArtificial.jsp";
    private static final String TAG_SUCCESS = "success";

    //private static String Step="";

    private static TextView steporder;
    String TAG_ACCOUNT;

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    int TAG_START_REMIND = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_action_control_artificial);

        TextView ss = (TextView)findViewById(R.id.textView2);
        TextView StartMessage = (TextView)findViewById(R.id.textView17);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        ss.setText(Integer.toString(TAG_STEP_ORDER));

        sop_detailDao msop_detailDao0 = new sop_detailDao();
        DatabaseHelper mDatabaseHelper0 = DatabaseHelper.getHelper(this);
        List<sop_detailVo> list0 = null;
        list0 = msop_detailDao0.selectRaw(mDatabaseHelper0, "Step_number =" + TAG_STEP_NUMBER);
        TAG_START_REMIND = Integer.valueOf(list0.get(0).getStart_remind());
        StartMessage.setText(list0.get(0).getStart_message());
        //Log.d("TAG_START_REMIND", list0.get(0).getStart_remind());

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
                    .setSmallIcon(R.drawable.ftc3x)
                    .setContentTitle("Step"+Integer.toString(TAG_STEP_ORDER))
                    .setContentText("啟動方式：人工啟動")
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

        //new CheckStep().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_action_control_artificial, menu);
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

    public void artfiOpen(View v){
        Intent intent = new Intent(StepActionControlArtificial.this,Stepdescription.class);
        Bundle bundle = new Bundle();
        bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
        bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
        bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
        intent.putExtras(bundle);//將參數放入intent
        startActivity(intent);
        finish();
    }

    //讀取第幾步驟
/*    class CheckStep extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepActionControlArtificial.this);
            pDialog.setMessage("QR code checking.... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            //寫死 Stepnumber
            String Stepnumber="3";

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));

            JSONObject json = StepActionControlArtificial.this.jsonParser.makeHttpRequest(StepActionControlArtificial.url_create_product, "GET", params);


            try {
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    Step=json.getString("step_order");
                    System.out.println("Here"+Step);

                }else if(e == 6){


                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String valoreOnPostExecute) {

            pDialog.dismiss();
            steporder.setText(Step);

        }
    }*/
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
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepActionControlArtificial.this);
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
