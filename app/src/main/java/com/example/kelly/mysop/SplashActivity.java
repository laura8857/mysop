package com.example.kelly.mysop;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_masterDao;
import Ormlite.case_masterVo;
import Ormlite.case_recordDao;
import Ormlite.case_recordVo;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;
import Ormlite.sop_masterDao;
import Ormlite.sop_masterVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;


public class SplashActivity extends Activity {


    String TAG_ACCOUNT;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    ArrayList<HashMap<String, String>> productsList1;
    ArrayList<HashMap<String, String>> productsList2;
    ArrayList<HashMap<String, String>> productsList3;
    private static String url_all_products = "http://140.115.80.237/front/mysop_step_detail.jsp";
    private static String url_all_products1 = "http://140.115.80.237/front/mysop_sop_master.jsp";
    private static String url_all_products2 = "http://140.115.80.237/front/mysop_case_master.jsp";
    private static String url_all_products3 = "http://140.115.80.237/front/mysop_step_record.jsp";
    private static String url_upload1 = "http://140.115.80.237/front/.jsp";
    private static String url_upload2 = "http://140.115.80.237/front/.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_CASENUMBER = "casenumber";
    private static final String TAG_SOPNAME = "sopname";
    private static final String TAG_STARTRULE = "startrule";
    private static final String TAG_STARTVALUE = "startvalue";
    private static final String TAG_PICTURE = "picture";
    private static final String TAG_ORDER = "order";
    private static final String TAG_TATOL = "total";
    private static final String TAG_SOPNUMBER = "sopnumber";
    JSONArray products = null;
    JSONArray products1 = null;
    JSONArray products2 = null;
    JSONArray products3 = null;

    Context mContext;
    DownloadManager manager ;
    DownloadCompleteReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        productsList = new ArrayList<HashMap<String, String>>();
        productsList1 = new ArrayList<HashMap<String, String>>();
        productsList2 = new ArrayList<HashMap<String, String>>();
        productsList3 = new ArrayList<HashMap<String, String>>();

        //寫死 insert，之後把他刪掉
        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao1;
        mmember_accountDao1 = new member_accountDao();
        member_accountVo mmember_accountVo = new member_accountVo();
        mmember_accountVo.setAccount("test@gmail.com");
        mmember_accountVo.setUsername("user1");
        mmember_accountVo.setPassword("test");
        mmember_accountDao1.insert(mDatabaseHelper1, mmember_accountVo);

        getAccount();


        //下載
        mContext = this;
        manager =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        //download();

    }

    public void download() {

        DatabaseHelper DatabaseHelperDL = DatabaseHelper.getHelper(this);
        sop_detailDao sopdetailDaoDL = new sop_detailDao();
        List<sop_detailVo> sop_detailDL;
        sop_detailDL = sopdetailDaoDL.selectRaw(DatabaseHelperDL, "Start_remind=1");
        DownloadManager.Request[] down = new DownloadManager.Request[50];

        for(int i=0;i<sop_detailDL.size();i++){

            File file = new File(URI.create("file:///mnt/sdcard/MYSOPTEST/start" + sop_detailDL.get(i).getStep_number() + ".mp3").getPath());
            if (file.exists()) {
                break;
            }
            down[i]=new DownloadManager.Request (Uri.parse("http://140.115.80.237/front/download/start"+sop_detailDL.get(i).getStep_number()+".mp3"));
            //允許網路類型
            down[i].setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
            //禁止發通知
            down[i].setShowRunningNotification(false);
            //不顯示下載頁面
            down[i].setVisibleInDownloadsUi(false);
            //下載後存放位置
            //down.setDestinationInExternalFilesDir(mContext, null, "testhtml.html");
            //在sdcard裡面的MYSOPTEST資料夾
            down[i].setDestinationInExternalPublicDir("MYSOPTEST", "start"+sop_detailDL.get(i).getStep_number()+".mp3");
            //將請求加入
            manager.enqueue(down[i]);

        }

        DatabaseHelper DatabaseHelperDL1 = DatabaseHelper.getHelper(this);
        sop_detailDao sopdetailDaoDL1 = new sop_detailDao();
        List<sop_detailVo> sop_detailDL1;
        sop_detailDL1 = sopdetailDaoDL1.selectRaw(DatabaseHelperDL1, "Step_remind=1");
        DownloadManager.Request[] down1 = new DownloadManager.Request[50];

        for(int i=0;i<sop_detailDL1.size();i++) {

            File file = new File(URI.create("file:///mnt/sdcard/MYSOPTEST/step" + sop_detailDL1.get(i).getStep_number() + ".mp3").getPath());
            if (file.exists()) {
                break;
            }
            down1[i] = new DownloadManager.Request(Uri.parse("http://140.115.80.237/front/download/step" + sop_detailDL1.get(i).getStep_number() + ".mp3"));
            //允許網路類型
            down1[i].setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //禁止發通知
            down1[i].setShowRunningNotification(false);
            //不顯示下載頁面
            down1[i].setVisibleInDownloadsUi(false);
            //下載後存放位置
            //down.setDestinationInExternalFilesDir(mContext, null, "testhtml.html");
            //在sdcard裡面的MYSOPTEST資料夾
            down1[i].setDestinationInExternalPublicDir("MYSOPTEST", "step" + sop_detailDL1.get(i).getStep_number() + ".mp3");
            //將請求加入
            manager.enqueue(down1[i]);
        }


/*        DownloadManager.Request down=new DownloadManager.Request (Uri.parse("http://140.115.80.237/front/download/testhtml.html"));
        //允許網路類型
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
        //禁止發通知
        down.setShowRunningNotification(false);
        //不顯示下載頁面
        down.setVisibleInDownloadsUi(false);
        //下載後存放位置
        //down.setDestinationInExternalFilesDir(mContext, null, "testhtml.html");
        //在sdcard裡面的MYSOPTEST資料夾
        down.setDestinationInExternalPublicDir("MYSOPTEST", "testhtml.html");
        //將請求加入
        manager.enqueue(down);*/
    }
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.v("SplashActivity"," download complete! id : "+downId);
                Toast.makeText(context, intent.getAction()+"id : "+downId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(receiver != null)unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void getAccount() {

        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao = new member_accountDao();
        List<member_accountVo> account;
        account = mmember_accountDao.selectColumns(mDatabaseHelper, "FIELD_Account");

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        //null代表沒網路
        if(info==null && account.isEmpty()) {
            Toast.makeText(SplashActivity.this,"請開啟網路",Toast.LENGTH_LONG);
        }else if(info==null && !account.isEmpty()) {
            startActivity(new Intent().setClass(SplashActivity.this, Mysop.class));
        }else if(info!=null && account.isEmpty()) {
            startActivity(new Intent().setClass(SplashActivity.this, Login.class));
        }else if(info!=null && !account.isEmpty()){

            DatabaseHelper DatabaseHelperupload = DatabaseHelper.getHelper(SplashActivity.this);
            case_masterDao case_masteruploadDao = new case_masterDao();
            List<case_masterVo> uploadlist;
            uploadlist = case_masteruploadDao.selectRaw(DatabaseHelperupload,"Case_mark=1");
            //此人有無網路結案
            if(uploadlist!=null){

                DatabaseHelper DatabaseHelperupload1 = DatabaseHelper.getHelper(SplashActivity.this);
                case_recordDao case_recorduploadDao1 = new case_recordDao();
                List<case_recordVo> uploadlist1;
                for(int i=0;i<uploadlist.size();i++) {
                    uploadlist1 = case_recorduploadDao1.selectRaw(DatabaseHelperupload1,"Case_number="+uploadlist.get(i).getCase_number());
                    //上傳記錄值case_record
                    for(int j=0;j<uploadlist1.size();j++) {
                        UpLoad1[] upload1 = new UpLoad1[uploadlist1.size() - 1];
                        upload1[i] = new UpLoad1();
                        upload1[i].execute(uploadlist1.get(i).getCase_number(), uploadlist1.get(i).getStep_order(), uploadlist1.get(i).getRecord_order(), uploadlist1.get(i).getRecord_value());
                    }
                    //上傳刪除case_master
                    UpLoad2[] upload2 = new UpLoad2[uploadlist.size() - 1];
                    upload2[i] = new UpLoad2();
                    upload2[i].execute(uploadlist.get(i).getCase_number());
                    //手機刪case_record、case_master
                    DatabaseHelper DatabaseHelperupload2 = DatabaseHelper.getHelper(SplashActivity.this);
                    case_recordDao case_recordupload2 = new case_recordDao();
                    case_recordupload2.delete(DatabaseHelperupload2,"Case_number",uploadlist.get(i).getCase_number());
                    DatabaseHelper DatabaseHelperupload3 = DatabaseHelper.getHelper(SplashActivity.this);
                    case_masterDao case_masteruploadDao3 = new case_masterDao();
                    case_masteruploadDao3.delete(DatabaseHelperupload3,"Case_number",uploadlist.get(i).getCase_number());
                }
            }
            TAG_ACCOUNT = account.get(0).getAccount();
            new LoadAllProducts().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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

    //更改紀錄
    class UpLoad1 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setMessage("Changing..Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        protected String doInBackground(String... args) {

            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("CaseNumber", args[0]));
            params.add(new BasicNameValuePair("StepOrder", args[1]));
            params.add(new BasicNameValuePair("RecordOrder", args[2]));
            params.add(new BasicNameValuePair("RecordText", args[3]));
            // 上傳紀錄的紀錄
            JSONObject jsonupload1 = SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_upload1, "POST", params);
            try {
                int e = jsonupload1.getInt(TAG_SUCCESS);
                if(e == 1) {
                    Log.d("updoad1","成功");
                }else{
                    Log.d("updoad1","失敗");
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }

    //結案
    class UpLoad2 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setMessage("Being Close. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ArrayList params2 = new ArrayList();
            params2.add(new BasicNameValuePair("Casenumber", args[0]) );
            JSONObject jsonupload2 = SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_upload2,"POST",params2);
            try {
                int e3 = jsonupload2.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    Log.d("updoad2","成功");
                }else {
                    Log.d("updoad2","失敗");
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

        }
    }





//下載

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SplashActivity.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT) );
            // getting JSON string from URL
            JSONObject json = SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_all_products,"GET", params);
            JSONObject json1 =SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_all_products1,"GET", params);
            JSONObject json2 =SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_all_products2,"GET", params);
            JSONObject json3 =SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_all_products3,"GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());
            Log.d("All Products: ", json1.toString());
            Log.d("All Products: ", json2.toString());
            Log.d("All Products: ", json3.toString());
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);


                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    Log.d("test",String.valueOf(products.length()));

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        //for (int i = 0; i < 2; i++) {

                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable

                        Log.d("create",c.toString());
                        /*if(i==3){
                            continue;
                        }*/
                        String sopnumber = c.getString("sop_number");
                        String steporder = c.getString("step_order");
                        String stepnumber= c.getString("step_number");
                        String stepname = c.getString("step_name");
                        String steppurpose = c.getString("step_purpose");
                        String stepintro = c.getString("step_intro");
                        Log.d("finish",c.getString("step_intro"));
                        Log.d("finish",c.getString("step_intro").replace("\\",""));
                        String startrule = c.getString("start_rule");
                        String startvalue1 = c.getString("start_value1");
                        String startvalue2 = c.getString("start_value2");
                        String finishrule = c.getString("finish_rule");
                        Log.d("finish",c.getString("finish_value1"));
                        String finishvalue1 = c.getString("finish_value1");
                        String finishvalue2 = c.getString("finish_value2");
                        String nextsteprule = c.getString("next_step_rule");
                        String next_step_number = c.getString("next_step_number");
                        String step_remind = c.getString("step_remind");
                        String start_remind = c.getString("start_remind");
                        String start_message = c.getString("start_message");




                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value


                        map.put("sop_number",sopnumber);
                        map.put("step_order",steporder);
                        map.put("step_number",stepnumber);
                        map.put("step_name",stepname);
                        map.put("step_purpose",steppurpose);
                        map.put("step_intro",stepintro);
                        map.put("start_rule",startrule);
                        map.put("start_value1",startvalue1);
                        map.put("start_value2",startvalue2);
                        map.put("finish_rule",finishrule);
                        map.put("finish_value1",finishvalue1);
                        map.put("finish_value2",finishvalue2);
                        map.put("next_step_rule",nextsteprule);
                        map.put("next_step_number", next_step_number);
                        map.put("step_remind",step_remind);
                        map.put("start_remind", start_remind);
                        map.put("start_message", start_message);

                        Log.d("test12",String.valueOf(i));

                        // adding HashList to ArrayList
                        productsList.add(map);

                    }
                    Log.d("test11",String.valueOf(productsList.size()));
                    Log.d("productlist","success");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try{
                // Checking for SUCCESS TAG
                int success1 = json1.getInt(TAG_SUCCESS);

                if (success1 == 1) {
                    // products found
                    // Getting Array of Products
                    products1 = json1.getJSONArray(TAG_PRODUCTS);
                    Log.d("ptest1",String.valueOf(products1.length()));
                    // looping through All Products
                    for (int i = 0; i < products1.length(); i++) {
                        JSONObject c = products1.getJSONObject(i);

                        // Storing each json item in variable
                        String sopnumber =c.getString("sop_number");
                        String sopname = c.getString("sop_name");
                        String sopgraphsrc = c.getString("sop_graph_src");
                        String sopintro = c.getString("sop_intro");
                        String sopdetail = c.getString("sop_detail");
                        String account = c.getString("account");
                        String startrule = c.getString("startrule");

                        // creating new HashMap
                        HashMap<String, String> map1 = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map1.put("sop_number",sopnumber);
                        map1.put("sop_name", sopname);
                        map1.put("sop_graph_src",sopgraphsrc);
                        map1.put("sop_intro",sopintro);
                        map1.put("sop_detail",sopdetail);
                        map1.put("account",account);
                        map1.put("startrule",startrule);

                        Log.d("ptestcount",String.valueOf(i));
                        // adding HashList to ArrayList
                        productsList1.add(map1);
                    }
                    Log.d("productlist1","success");
                } else {


                }


                // Checking for SUCCESS TAG
                int success2 = json2.getInt(TAG_SUCCESS);

                if (success2 == 1) {
                    // products found
                    // Getting Array of Products
                    products2 = json2.getJSONArray(TAG_PRODUCTS);
                    Log.d("ptest2",String.valueOf(products2.length()));
                    // looping through All Products
                    for (int i = 0; i < products2.length(); i++) {
                        JSONObject c = products2.getJSONObject(i);

                        // Storing each json item in variable

                        String sopnumber = c.getString("sop_number");
                        String casenumber = c.getString("case_number");
                        String account = c.getString("account");
                        String stepnumber = c.getString("step_number");

                        // creating new HashMap
                        HashMap<String, String> map2 = new HashMap<String, String>();

                        // adding each child node to HashMap key => value


                        map2.put("sop_number",sopnumber);
                        map2.put("case_number",casenumber);
                        map2.put("account",account);
                        map2.put("step_number",stepnumber);

                        Log.d("ptestcount2",String.valueOf(i));
                        // adding HashList to ArrayList
                        productsList2.add(map2);
                    }
                    Log.d("productlist2","success");
                } else {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                int success3 = json3.getInt(TAG_SUCCESS);
                if (success3 == 1) {
                    // products found
                    // Getting Array of Products
                    products3 = json3.getJSONArray(TAG_PRODUCTS);

                    Log.d("test",String.valueOf(products3.length()));

                    // looping through All Products
                    for (int i = 0; i < products3.length(); i++) {
                        //for (int i = 0; i < 2; i++) {

                        JSONObject c = products3.getJSONObject(i);

                        // Storing each json item in variable

                        Log.d("create",c.toString());

                        String id = c.getString("id");
                        String stepnumber = c.getString("step_number");
                        String recordorder = c.getString("record_order");
                        String recordtext= c.getString("record_text");
                        String recordtype = c.getString("record_type");
                        String recordunit = c.getString("record_unit");
                        String recordmax = c.getString("record_max");
                        String recordmin = c.getString("record_min");
                        String recordstandard = c.getString("record_standard");

                        // creating new HashMap
                        HashMap<String, String> map3 = new HashMap<String, String>();
                        // adding each child node to HashMap key => value

                        map3.put("id",id);
                        map3.put("step_number",stepnumber);
                        map3.put("record_order",recordorder);
                        map3.put("record_text",recordtext);
                        map3.put("record_type",recordtype);
                        map3.put("record_unit",recordunit);
                        map3.put("record_max",recordmax);
                        map3.put("record_min",recordmin);
                        map3.put("record_standard",recordstandard);

                        Log.d("record",String.valueOf(i));

                        // adding HashList to ArrayList
                        productsList3.add(map3);

                    }
                    Log.d("record",String.valueOf(productsList3.size()));
                    Log.d("productlist3","success");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {


            pDialog.dismiss();
            //Log.d("test1",productsList.get(10).get("finish_value1"));
            //Log.d("test1",String.valueOf(productsList.size()));

            DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_detailDao msop_detailDao2 = new sop_detailDao();
            sop_detailVo msop_detailVo2 = new sop_detailVo();

/*            DatabaseHelper[] mDatabaseHelper2 = new DatabaseHelper[20];
            sop_detailDao[] msop_detailDao2 = new sop_detailDao[20];
            sop_detailVo[] msop_detailVo2 = new sop_detailVo[20];
            for (int i = 0; i <  4; i++){
                mDatabaseHelper2[i] = DatabaseHelper.getHelper(SplashActivity.this);
                msop_detailVo2[i].setSop_number("www");
                msop_detailVo2[i].setStep_order("123"+i);
                msop_detailDao2[i].insert(mDatabaseHelper2[i], msop_detailVo2[i]);
                Log.d("products",String.valueOf(i));
            }*/
            //msop_detailDao2.insert(mDatabaseHelper2, msop_detailVo2);
            Log.d("length",String.valueOf(products.length()));
            Log.d("size",String.valueOf(productsList.size()));

            // dismiss the dialog after getting all products
            for (int i = 0; i <  productsList.size(); i++){


                msop_detailVo2.setSop_number(productsList.get(i).get("sop_number"));
                msop_detailVo2.setStep_order(productsList.get(i).get("step_order"));
                msop_detailVo2.setStep_number(productsList.get(i).get("step_number"));
                Log.d("countproducts"+String.valueOf(i),productsList.get(i).get("step_number"));
                msop_detailVo2.setStep_name(productsList.get(i).get("step_name"));
                msop_detailVo2.setStep_purpose(productsList.get(i).get("step_purpose"));
                msop_detailVo2.setStep_intro(productsList.get(i).get("step_intro"));
                Log.d("countproducts"+String.valueOf(i),productsList.get(i).get("step_intro"));
                msop_detailVo2.setStart_rule(productsList.get(i).get("start_rule"));
                msop_detailVo2.setStart_value1(productsList.get(i).get("start_value1"));
                msop_detailVo2.setStart_value2(productsList.get(i).get("start_value2"));
                msop_detailVo2.setFinish_rule(productsList.get(i).get("finish_rule"));
                msop_detailVo2.setFinish_value1(productsList.get(i).get("finish_value1"));
                msop_detailVo2.setFinish_value2(productsList.get(i).get("finish_value2"));
                msop_detailVo2.setNext_step_number(productsList.get(i).get("next_step_number"));
                msop_detailVo2.setNext_step_rule(productsList.get(i).get("next_step_rule"));
                msop_detailVo2.setStep_remind(productsList.get(i).get("step_remind"));
                msop_detailVo2.setStart_remind(productsList.get(i).get("start_remind"));
                msop_detailVo2.setStart_message(productsList.get(i).get("start_message"));


                msop_detailDao2.insert(mDatabaseHelper2, msop_detailVo2);
            }


            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(SplashActivity.this);
            case_masterDao mcase_masterDao4 = new case_masterDao();
            case_masterVo mcase_masterVo4 = new case_masterVo();

            Log.d("eee",String.valueOf(products2.length()));
            // dismiss the dialog after getting all products
            for (int i = 0; i <  productsList2.size(); i++){

                mcase_masterVo4.setSop_number(productsList2.get(i).get("sop_number"));
                mcase_masterVo4.setStep_number(productsList2.get(i).get("step_number"));
                mcase_masterVo4.setAccount(productsList2.get(i).get("account"));
                mcase_masterVo4.setCase_number(productsList2.get(i).get("case_number"));
                mcase_masterVo4.setCase_mark("0");
                mcase_masterDao4.insert(mDatabaseHelper4, mcase_masterVo4);

            }

            DatabaseHelper mDatabaseHelper5 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_masterDao msop_masterDao5 = new sop_masterDao();
            sop_masterVo msop_masterVo5 = new sop_masterVo();

            // dismiss the dialog after getting all products
            for (int i = 0; i <  products1.length(); i++){

                msop_masterVo5.setSop_number(productsList1.get(i).get("sop_number"));
                msop_masterVo5.setSop_name(productsList1.get(i).get("sop_name"));
                msop_masterVo5.setSop_graph_src(productsList1.get(i).get("sop_graph_src"));
                msop_masterVo5.setSop_intro(productsList1.get(i).get("sop_intro"));
                msop_masterVo5.setSop_detail(productsList1.get(i).get("sop_detail"));
                msop_masterVo5.setAccount(productsList1.get(i).get("account"));
                msop_masterVo5.setStart_rule(productsList1.get(i).get("start_rule"));
                msop_masterDao5.insert(mDatabaseHelper5, msop_masterVo5);

            }
            DatabaseHelper mDatabaseHelper6 = DatabaseHelper.getHelper(SplashActivity.this);
            step_recordDao mstep_recordDao6 = new step_recordDao();
            step_recordVo mstep_recordVo6 = new step_recordVo();
            Log.d("length3",String.valueOf(products3.length()));
            Log.d("size3",String.valueOf(productsList3.size()));
            // dismiss the dialog after getting all products
            for (int i = 0; i <  products3.length(); i++){

                mstep_recordVo6.setId(Integer.valueOf(productsList3.get(i).get("id")));
                mstep_recordVo6.setStep_number(productsList3.get(i).get("step_number"));
                mstep_recordVo6.setRecord_order(productsList3.get(i).get("record_order"));
                mstep_recordVo6.setRecord_text(productsList3.get(i).get("record_text"));
                Log.d("countproducts3"+i,productsList3.get(i).get("record_text"));
                mstep_recordVo6.setRecord_type(productsList3.get(i).get("record_type"));
                mstep_recordVo6.setRecord_unit(productsList3.get(i).get("record_unit"));
                mstep_recordVo6.setRecord_max(productsList3.get(i).get("record_max"));
                mstep_recordVo6.setRecord_min(productsList3.get(i).get("record_min"));
                mstep_recordVo6.setRecord_standard(productsList3.get(i).get("record_standard"));
                mstep_recordDao6.insert(mDatabaseHelper6, mstep_recordVo6);

            }

            download();
/*
            //測試
            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SplashActivity.this);
            step_recordDao mstep_recordDao11 = new step_recordDao();
            List<step_recordVo>step_recordlist11 = null;
            //sopdetaillist = msop_detailDao.selectRaw(mDatabaseHelper,"Step_number IN(SELECT Last_do_order FROM case_masterVo WHERE Account='"+TAG_ACCOUNT+"')");
            step_recordlist11 = mstep_recordDao11.selectRawByNest(mDatabaseHelper,"Sop_number","20150803","Step_number");
            Log.d("onpost_test",String.valueOf(step_recordlist11.size()));
            Log.d("00",step_recordlist11.get(0).getStep_number());
            Log.d("1",step_recordlist11.get(0).getRecord_text());
            Log.d("2",step_recordlist11.get(1).getRecord_text());
            Log.d("3",step_recordlist11.get(2).getRecord_text());
            Log.d("4",step_recordlist11.get(3).getRecord_text());
            Log.d("5",step_recordlist11.get(4).getRecord_text());
            Log.d("6",step_recordlist11.get(5).getRecord_text());
            Log.d("7",step_recordlist11.get(6).getRecord_text());
            DatabaseHelper mDatabaseHelper15 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_detailDao msop_detailDao15 = new sop_detailDao();
            List<sop_detailVo> list15 = null;
            list15 = msop_detailDao15.selectRaw(mDatabaseHelper15, "Step_number ='1'");
            Log.d("User1", list15.get(0).getSop_number());
            //測試


            //測試
            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SplashActivity.this);
            sop_detailDao msop_detailDao9 = new sop_detailDao();
            List<sop_detailVo>sopdetaillist9 = null;
            //sopdetaillist = msop_detailDao.selectRaw(mDatabaseHelper,"Step_number IN(SELECT Last_do_order FROM case_masterVo WHERE Account='"+TAG_ACCOUNT+"')");
            sopdetaillist9 = msop_detailDao9.selectRawByNest(mDatabaseHelper,"Account",TAG_ACCOUNT,"Step_number");
            Log.d("onpost_test",String.valueOf(sopdetaillist9.size()));
            Log.d("1",sopdetaillist9.get(0).getStep_number());
            Log.d("2",sopdetaillist9.get(1).getStep_number());
            Log.d("3",sopdetaillist9.get(2).getStep_number());
            Log.d("4",sopdetaillist9.get(3).getStep_number());
            Log.d("5",sopdetaillist9.get(4).getStep_number());
            //Log.d("6",sopdetaillist9.get(5).getStep_number());
            //測試

            //測試
            DatabaseHelper mDatabaseHelper10 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_detailDao msop_detailDao10 = new sop_detailDao();
            List<sop_detailVo>sopdetaillist10 = null;
            sopdetaillist10 = msop_detailDao10.selectRaw(mDatabaseHelper10,"Step_number=6");
            Log.d("onpost_test2",sopdetaillist10.get(0).getStep_number());
            Log.d("onpost_test2",sopdetaillist10.get(0).getStep_intro());
            //測試
*/

            //startActivity(new Intent().setClass(SplashActivity.this, Login.class));
            Bundle bundle = new Bundle();
            bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
            startActivity(new Intent().setClass(SplashActivity.this, Mysop.class).putExtras(bundle));
        }
    }
}
