package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepActionControlNFC extends Activity {

    private TextView mTextView;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;
     String TAG_ACCOUNT;


    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //讀取 NFC
    private static String url_NFC = "http://140.115.80.237/front/mysop_ACnfc.jsp";
    private static final String TAG_SUCCESS = "success";

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    private sop_detailDao msop_detailDao;
    private DatabaseHelper mDatabaseHelper;

    int TAG_START_REMIND = 0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_action_control_nfc);

        mDatabaseHelper = DatabaseHelper.getHelper(this);

        TextView ss = (TextView)findViewById(R.id.AC_NFC_textView2);
        TextView StartMessage = (TextView)findViewById(R.id.AC_NFC_textView6);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");
//        TAG_CASE_NUMBER = "2014";
//        TAG_STEP_NUMBER = "4";
//        TAG_STEP_ORDER = 4;
        ss.setText(Integer.toString(TAG_STEP_ORDER));

        sop_detailDao msop_detailDao0 = new sop_detailDao();
        DatabaseHelper mDatabaseHelper0 = DatabaseHelper.getHelper(this);
        List<sop_detailVo> list0 = null;
        list0 = msop_detailDao0.selectRaw(mDatabaseHelper0, "Step_number =" + TAG_STEP_NUMBER);
        TAG_START_REMIND = Integer.valueOf(list0.get(0).getStart_remind());
        StartMessage.setText(list0.get(0).getStart_message());
        //Log.d("TAG_START_REMIND",list0.get(0).getStart_remind());

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
                    .setContentText("啟動方式：NFC啟動")
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


        mTextView = (TextView)findViewById(R.id.AC_NFC_textView5);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            mTextView.setText("支持讀取NFC!");
        } else {
            mTextView.setText("不支持NFC!");
        }
        if (!mNfcAdapter.isEnabled()) {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setTitle("注意");
            alertbox.setMessage("請開啟NFC!");
            alertbox.setPositiveButton("開啟", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivity(intent);
                    }
                }
            });
            alertbox.setNegativeButton("關閉", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertbox.show();

        }

        mPendingIntent = PendingIntent.getActivity(this, 0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndefIntent.addDataType("*/*");
            mIntentFilters = new IntentFilter[] { ndefIntent };
        }catch (Exception e) {
            Log.e("TagDispatch", e.toString());
        }
        mNFCTechLists = new String[][] { new String[] { NfcF.class.getName() } };

    }


    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String s = action + "\n\n" + tag.toString();
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        //NFC Tag中第一項Text要放UUID
        String[] NFC_UUID = new String[20];

        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord [] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {

                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";

                            int langCodeLen = payload[0] & 0077;
                            s += ("\n\nNdefMessage[" + i + "], NdefRecord[" + j + "]:\n\"" +
                                    new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding) + "\"");
                            NFC_UUID[j] = new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }
        }
        //mTextView.setText(s);
        //new Check_NFC().execute(NFC_UUID[0]);
        msop_detailDao = new sop_detailDao();
        List<sop_detailVo> list = null;
        list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number ="+TAG_STEP_NUMBER);
        Log.d("抓", list.get(0).getStart_value1());
        if(list.get(0).getStart_value1().equals(NFC_UUID[0])){

            Intent Intent = new Intent();
            Intent.setClass(StepActionControlNFC.this, Stepdescription.class);
            Bundle bundle = new Bundle();
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
            bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
            bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
            Intent.putExtras(bundle);//將參數放入intent
            startActivity(Intent);
            //切換畫面，右近左出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            finish();
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(StepActionControlNFC.this);
            dialog.setTitle("");
            dialog.setMessage("比對結果錯誤，請使用正確的NFC Tag比對!");
            dialog.show();
        }


    }


    @Override
    public void onResume() {
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
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
    }
    @Override
    public void onPause() {
        if(mp != null){
            mp.pause();
            //mp.release();
        }
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

/*    class Check_NFC extends AsyncTask<String, String, Integer> {


        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepActionControlNFC.this);
            pDialog.setMessage("Matching... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Integer doInBackground(String... args) {

            int returnvalue = 0;

            //String Stepnumber="4";
            String Stepnumber = TAG_STEP_NUMBER;
            String NFC_UUID = args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));
            params.add(new BasicNameValuePair("NFC_UUID", NFC_UUID));
            JSONObject json = StepActionControlNFC.this.jsonParser.makeHttpRequest(StepActionControlNFC.url_NFC, "GET", params);


            try {
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    returnvalue = 1;
                }else if(e == 6){
                    returnvalue = 6;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return returnvalue;
        }

        protected void onPostExecute(Integer returnvalue) {

            pDialog.dismiss();
            if (returnvalue == 1){
                Intent intent = new Intent();
                intent.setClass(StepActionControlNFC.this, Stepdescription.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
                bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                intent.putExtras(bundle);//將參數放入intent
                startActivity(intent);
                //切換畫面，右近左出
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }else if(returnvalue == 6){
                AlertDialog.Builder dialog = new AlertDialog.Builder(StepActionControlNFC.this);
                dialog.setTitle("");
                dialog.setMessage("比對結果錯誤，請使用正確的NFC Tag比對!");
                dialog.show();
            }

        }
    }*/

    @Override
    protected void onDestroy() {
        if(mp != null) {
            mp.release();
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //orm account
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepActionControlNFC.this);
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
