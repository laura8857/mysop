package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Ormlite.DatabaseHelper;
import Ormlite.case_recordDao;
import Ormlite.case_recordVo;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;

//p303
public class Steprecording extends Activity {


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;

    private static String url_all_products = "http://140.115.80.237/front/mysop_steprecording2.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_RECODE = "recode";
    String TAG_ACCOUNT;


    private static String url_record = "http://140.115.80.237/front/mysop_steprecording3.jsp";

    private GestureDetector detector;
    EditText[] edit1 = new EditText[20];

    private Intent recognizerIntent = null;
    private GridView gridView;
    private List<String> messageList;
    private ArrayAdapter<String> adapter;
    private Context context;
    EditText edtext = null;

    private int count=0;
    //private String step="";
    String RecordText[] = new String[20];

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;
    boolean TAG_BACK_TO_RECORDING = false;

    private step_recordDao mstep_recordDao;
    private case_recordDao mcase_recordDao;
    private case_recordVo mcase_recordVo;


    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private String wearcontent[];
    boolean TAG_FROM_WEAR = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steprecording);

        //手錶
        String fromwear = getIntent().getStringExtra("reply");
        if(fromwear==null){
            Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
            if(remoteInput!=null){
                fromwear = remoteInput.getCharSequence(Steprecording.EXTRA_VOICE_REPLY).toString();
                Log.d("fromwear",fromwear);
                wearcontent = fromwear.split(" ");
                TAG_FROM_WEAR = true;
            }
        }


        TextView ss = (TextView)findViewById(R.id.textView6);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle

        if(intent.hasExtra("TAG_BACK_TO_RECORDING")){
            TAG_BACK_TO_RECORDING = true;
            Log.d("抓3","backtorecording");
        }

        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");

        Log.d("StepRecording",TAG_STEP_NUMBER);
//        TAG_CASE_NUMBER = "15";
//        TAG_STEP_NUMBER = "10";
//        TAG_STEP_ORDER = 1;
        ss.setText(Integer.toString(TAG_STEP_ORDER));

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        // Loading products in Background Thread
        //new LoadInput().execute();

        //SELECT record_order,record_text FROM step_record WHERE step_number='"+Stepnumber+"'"
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        mstep_recordDao = new step_recordDao();
        List<step_recordVo> list = null;
        list = mstep_recordDao.selectRaw(mDatabaseHelper, "Step_number="+TAG_STEP_NUMBER);

      //  Log.d("抓", list.get(0).getRecord_order());
      //  Log.d("抓2", list.get(0).getRecord_text());

        count = list.size();
        LinearLayout ly = (LinearLayout)findViewById(R.id.linearlayoutinput);
        for(int i=0; i<count;i++) {
            TextView text1 = new TextView(Steprecording.this);
            text1.setText(list.get(i).getRecord_text());

            edit1[i] = new EditText(Steprecording.this.getApplicationContext());
            edit1[i].setTextColor(Color.rgb(0, 0, 0));
            edit1[i].setOnFocusChangeListener(new MyOnFocusChangeListener());
            edit1[i].setSingleLine(true);
            edit1[i].setBackgroundColor(Color.parseColor("#FEFBE6"));
            if(TAG_FROM_WEAR){
               if(i<wearcontent.length) {
                   edit1[i].setText(wearcontent[i]);
               }
            }
            //edit1[0].setText("eee");
            ly.addView(text1);
            ly.addView(edit1[i]);
        }


        detector = new GestureDetector(new MySimpleOnGestureListener());

        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);
        sv.setOnTouchListener(new MyOnTouchListener());
        //LinearLayout llb = (LinearLayout)findViewById(R.id.linearLayoutbackground);
        //llb.setOnTouchListener(new MyOnTouchListener());

        messageList = new ArrayList<>();

        //手錶
        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();
        // Create an intent for the reply action
        Intent replyIntent = new Intent(this, Steprecording.class);
        Bundle wb = new Bundle();
        wb.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
        wb.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
        wb.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
        replyIntent.putExtras(wb);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.cast_ic_notification_0,
                        getString(R.string.reply_label), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        // Create builder for the main notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Step"+Integer.toString(TAG_STEP_ORDER))
                        .setContentText("紀錄項(請往左滑)：");

/*        // Create a big text style for the second page
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("Page 2")
                .bigText("A lot of text...");

        // Create second page notification
        Notification secondPageNotification =
                new NotificationCompat.Builder(this)
                        .setStyle(secondPageStyle)
                        .build();
*/
        List extras = new ArrayList();
        for (int i = 0; i < count; i++) {

            NotificationCompat.BigTextStyle extraPageStyle = new NotificationCompat.BigTextStyle();
            extraPageStyle.setBigContentTitle("第"+String.valueOf(i)+"項")
                    .bigText(list.get(i).getRecord_text());
            Notification extraPageNotification = new NotificationCompat.Builder(this)
                    .setStyle(extraPageStyle)
                    .build();
            extras.add(extraPageNotification);

        }
/*      //如果要跳頁
        Intent mainIntent = new Intent(this, MyDisplayActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0,
                mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);
*/

        // Extend the notification builder with the second page
        Notification notification = notificationBuilder
                .extend(new NotificationCompat.WearableExtender()
                        .addPages(extras).addAction(action))
                        //.addPage(secondPageNotification))
                //.addAction(android.R.drawable.ic_media_play, "Speak", mainPendingIntent)
                .build();

        // Issue the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplication());
        Random random = new Random();
        int notificationId = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(notificationId, notification);

/*        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
        sendNotification();*/
    }


    public void speak_onclick(View view) {
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說...");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        try {
            startActivityForResult(recognizerIntent, 1);
        }catch(ActivityNotFoundException a){
            Toast.makeText(getApplicationContext(),"抱歉您的系統不支持語音識別輸入。",Toast.LENGTH_LONG).show();
        }
    }

    int voice=0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent it) {
        super.onActivityResult(requestCode, resultCode, it);
        messageList.clear();
        if (requestCode != 1) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        List<String> list = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        for (String s : list) {
            messageList.add(s);
        }
        Log.d("howmany",String.valueOf(count));
        if(edtext != null){
            edtext.setText(messageList.get(0));
        }else {
            if (voice < count) {
                edit1[voice].setText(messageList.get(0));
                voice++;
                Log.d("vvv", String.valueOf(voice));
            } else {
                voice = count - 1;
                edit1[voice].setText(messageList.get(0));
                Log.d("nnn", String.valueOf(voice));
            }
        }
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_steprecording, menu);
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
    }*/


/*    class LoadInput extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Steprecording.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String Stepnumber = TAG_STEP_NUMBER;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                //step = json.getString("step");
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {

                    products = json.getJSONArray(TAG_PRODUCTS);

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);


                        // Storing each json item in variable
                        int r = i+1;
                        String id = c.getString(TAG_RECODE+r);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_RECODE+r, id);

                        // adding HashList to ArrayList
                        productsList.add(map);

                    }
                } else {



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

            LinearLayout ly = (LinearLayout)findViewById(R.id.linearlayoutinput);

            count=products.length();

            for(int i=0; i<products.length();i++) {

                int r = i+1;
                TextView text1 = new TextView(Steprecording.this);
                text1.setText(productsList.get(i).get(TAG_RECODE+r));


                edit1[i] = new EditText(Steprecording.this.getApplicationContext());
                //edit1[i].setId(400+i);

                edit1[i].setTextColor(Color.rgb(0, 0, 0));
                edit1[i].setOnFocusChangeListener(new MyOnFocusChangeListener());
                edit1[i].setSingleLine(true);
                edit1[i].setBackgroundColor(Color.parseColor("#FEFBE6"));
                //edit1[0].setText("eee");
                ly.addView(text1);
                ly.addView(edit1[i]);
            }

            //if(edit1.getTag().equals(1)){edit1.setText("qqq");}

            //ly.setOnTouchListener(new MyOnTouchListener());


        }

    }*/


    class MyOnFocusChangeListener implements View.OnFocusChangeListener{
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Log.i("test", "获得焦点");
                Log.i("test", getCurrentFocus().toString());
                edtext = (EditText)getCurrentFocus();
            } else {
                Log.i("test", "失去焦点");
            }
        }
    }

    //抓滑動
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
                //Log.d("ohitwork",edit1[1].getText().toString());

                boolean didinput = true;

                for(int j=0;j<count;j++){
                    if(edit1[j].getText().toString().equals("")){
                        didinput = false;
                    }
                }

                if(didinput) {

                    if(!TAG_BACK_TO_RECORDING) {
                        //new Recording().execute();
                        DatabaseHelper[] mDatabaseHelper2 = new DatabaseHelper[20];
                        for (int postcount = 0; postcount < count; postcount++) {
                            //RC[postcount] = new Recording();
                            //RC[postcount].execute(postcount);

                            //"INSERT INTO case_record(case_number,step_order,record_order,record_value) VALUES('"+CaseNumber+"',"+StepOrder+","+RecordOrder+",'"+RecordText+"')";
                            RecordText[postcount] = Steprecording.this.edit1[postcount].getText().toString();

                            mDatabaseHelper2[postcount] = new DatabaseHelper(getApplicationContext());
                            mcase_recordDao = new case_recordDao();
                            mcase_recordVo = new case_recordVo();
                            mcase_recordVo.setCase_number(TAG_CASE_NUMBER);
                            mcase_recordVo.setStep_order(String.valueOf(TAG_STEP_ORDER));
                            mcase_recordVo.setRecord_order(String.valueOf(postcount + 1));
                            Log.d("RecordOrder", String.valueOf(postcount + 1));
                            mcase_recordVo.setRecord_value(RecordText[postcount]);
                            mcase_recordDao.insert(mDatabaseHelper2[postcount], mcase_recordVo);
                        }
                    //TAG_BACK_TO_RECORDING=true
                    }else{
                        Log.d("抓4","進入update");
                        DatabaseHelper[] mDatabaseHelper2 = new DatabaseHelper[20];
                            for (int postcount = 0; postcount < count; postcount++) {
                                RecordText[postcount] = Steprecording.this.edit1[postcount].getText().toString();
                                mDatabaseHelper2[postcount] = new DatabaseHelper(getApplicationContext());
                                mcase_recordDao = new case_recordDao();
                                mcase_recordDao.update_record(mDatabaseHelper2[postcount], "Case_number", TAG_CASE_NUMBER, "Step_order", TAG_STEP_NUMBER, "Record_order", String.valueOf(postcount + 1),"Record_value",RecordText[postcount]);
                            }
                    }

                    Intent intent = new Intent();
                    intent.setClass(Steprecording.this, StepCutControl.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
                    bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                    bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                    intent.putExtras(bundle);//將參數放入intent
                    startActivity(intent);
                    //切換畫面，右近左出
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Steprecording.this);
                    dialog.setTitle("");
                    dialog.setMessage("請紀錄完成再前往下一頁!");
                    dialog.show();
                }
                return true;



            } else if((e1.getX() - e2.getX()) < -50){ //11/29新加右滑
                Intent intent2 = new Intent();
                intent2.setClass(Steprecording.this, Stepdescription.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
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

    //回傳
/*    class Recording extends AsyncTask<Integer, String, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            Steprecording.this.pDialog = new ProgressDialog(Steprecording.this);
            Steprecording.this.pDialog.setMessage("Recording...");
            Steprecording.this.pDialog.setIndeterminate(false);
            Steprecording.this.pDialog.setCancelable(true);
            //Steprecording.this.pDialog.show();
        }

        protected Integer doInBackground(Integer... args) {

            //EditText et1 = (EditText)edit1.findViewById(20);
            //String Account = Steprecording.this.et1.getText().toString();
            int upload = 0;
            int a = args[0];
            RecordText[a] = Steprecording.this.edit1[a].getText().toString();
            ArrayList params = new ArrayList();

            //和使用者有關的-->按號
            params.add(new BasicNameValuePair("CaseNumber", TAG_CASE_NUMBER));
            //同一step的每一次/個record
            params.add(new BasicNameValuePair("StepOrder", String.valueOf(TAG_STEP_ORDER)));
            params.add(new BasicNameValuePair("RecordOrder", String.valueOf(a+1)));
            Log.d("RecordOrder", String.valueOf(a+1));
            params.add(new BasicNameValuePair("RecordText", RecordText[a]));

            JSONObject json = Steprecording.this.jParser.makeHttpRequest(Steprecording.url_record, "POST", params);
            Log.d("Create Response", json.toString());

            try {
                int e = json.getInt(TAG_SUCCESS);
                if (e == 1) {
                    Log.d("YES","SSS");
                    upload = 1;
                } else if (e == 2) {
                    Log.d("WRONG","SSS");
                    upload = 2;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();

            }
        return upload;
        }

        protected void onPostExecute(Integer upload) {
            //Steprecording.this.pDialog.dismiss();
            if(upload == 2){
                Toast.makeText(getApplicationContext(),"上傳未成功!", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //orm account
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(Steprecording.this);
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
