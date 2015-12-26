package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepActionControlIbeacon extends Activity implements BeaconConsumer{

    public static final String TAG = "BeaconsEverywhere";
    private BeaconManager beaconManager;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //讀取 qrcode 圖片
    private static String url_uuid = "http://140.115.80.237/front/mysop_ACibeacon.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_UUID = "UUID";
    String UUID = "00000000-0000-0000-0000-000000000000";

    int connectfinish = 0;

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();//獲得當前的藍芽

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    private sop_detailDao msop_detailDao;
    private DatabaseHelper mDatabaseHelper;

    int TAG_START_REMIND = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_action_control_ibeacon);
        Log.d("oncreateee", Integer.toString(connectfinish));

        mDatabaseHelper = DatabaseHelper.getHelper(this);

        TextView ss = (TextView)findViewById(R.id.AC_ibeacon_textView2);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");
        //TAG_STEP_NUMBER = "2";
        //TAG_STEP_ORDER = 2;
        ss.setText(Integer.toString(TAG_STEP_ORDER));

        sop_detailDao msop_detailDao0 = new sop_detailDao();
        DatabaseHelper mDatabaseHelper0 = DatabaseHelper.getHelper(this);
        List<sop_detailVo> list0 = null;
        list0 = msop_detailDao0.selectRaw(mDatabaseHelper0, "Step_number =" + TAG_STEP_NUMBER);
        TAG_START_REMIND = Integer.valueOf(list0.get(0).getStart_remind());
        Log.d("TAG_START_REMIND",list0.get(0).getStart_remind());
        if(TAG_START_REMIND == 1){

        }else if(TAG_START_REMIND == 2){

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


    public void refresh(){

        Intent intent = new Intent();
        intent.setClass(StepActionControlIbeacon.this, StepCutControlIbeacon.class);
        //Intent intent = getIntent();
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        if(connectfinish==0) {

        }
        Log.d("connectfinish?",Integer.toString(connectfinish));

        if(connectfinish == 0){}

        Region region = new Region("myBeaons", Identifier.parse(UUID), null, null);

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                    Intent intent = new Intent();
                    intent.setClass(StepActionControlIbeacon.this, Stepdescription.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
                    bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                    bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                    intent.putExtras(bundle);//將參數放入intent
                    startActivity(intent);
                    // 设置切换动画，从右边进入，左边退出
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    finish();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void didExitRegion(Region region) {
                try {
                    Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(Beacon oneBeacon : beacons) {
                    Log.d(TAG, "distance: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());
                }
            }
        });

        try {
            Log.d("uuid",region.getIdentifier(0).toString());
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void start_beacon(View view){
        if (adapter == null){
            Toast.makeText(this, "藍芽藍芽?不支持藍芽!", Toast.LENGTH_LONG).show();
        }else if(adapter.isEnabled()!=true){//如果藍芽未開啟
            //打開藍芽(會問使用者)
            Intent enabler=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enabler);
            Toast.makeText(this, "藍芽藍芽?沒打開喔!", Toast.LENGTH_LONG).show();
        }else {
            //new Check_beacon().execute();

            msop_detailDao = new sop_detailDao();
            List<sop_detailVo> list = null;
            list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number ="+TAG_STEP_NUMBER);
            Log.d("抓", list.get(0).getStart_value1());
            UUID = list.get(0).getStart_value1();
            connectfinish=1;
            beaconManager = BeaconManager.getInstanceForApplication(StepActionControlIbeacon.this);
            beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
            beaconManager.bind(StepActionControlIbeacon.this);

            Button ibeacon_button = (Button) findViewById(R.id.AC_ibeacon_button);
            ibeacon_button.setEnabled(false);


            Log.d("isithere", Integer.toString(connectfinish));

        }
    }


/*    class Check_beacon extends AsyncTask<String, String, Integer> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepActionControlIbeacon.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected Integer doInBackground(String... args) {

            int returnvalue = 0;

            String StepNumber = TAG_STEP_NUMBER;
            //String StepNumber = "2";

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("StepNumber", StepNumber));

            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(url_uuid, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    UUID = json.getString(TAG_UUID);
                    returnvalue = 1;
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return returnvalue;
        }


        protected void onPostExecute(Integer returnvalue) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            connectfinish=1;
            if (returnvalue == 1){
                beaconManager = BeaconManager.getInstanceForApplication(StepActionControlIbeacon.this);

                beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

                beaconManager.bind(StepActionControlIbeacon.this);

                Button ibeacon_button = (Button)findViewById(R.id.AC_ibeacon_button);
                ibeacon_button.setEnabled(false);
            }

        }
    }*/



}
