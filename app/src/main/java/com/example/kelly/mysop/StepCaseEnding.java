package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;


public class StepCaseEnding extends Activity {

    private TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;
    private EditText edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10;
    private TextView unit1,unit2,unit3,unit4,unit5,unit6,unit7,unit8,unit9,unit10;
    private LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9,l10;
    private Button change;
    public int Count;
    public int Step=1;

    //先寫死帳號和 SOPnumber
    String TAG_ACCOUNT = "";
    ArrayList<HashMap<String, String>> productsList;
    ArrayList<HashMap<String, String>> valueList;
    JSONArray products = null;
    JSONArray products1 = null;

    //連線 case ending
    JSONParser jsonParser = new JSONParser();
    //第一個是傳入紀錄說明和記錄值  第二個是更改紀錄值  第三個是 結案（刪掉代辦）
    private static String url_all_products = "http://140.115.80.237/front/mysop_stepCaseclose.jsp";
    private static String url_record = "http://140.115.80.237/front/mysop_steprecording3.jsp";
    private static String url_all_products2 = "http://140.115.80.237/front/mysop_stepCaseclose2.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_TEXT = "text";
    private static final String TAG_UNIT = "unit";
    private static final String TAG_VALUE = "value";
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();
    EditText[] edit = new EditText[20];
    String RecordText[] = new String[20];
    String TAG_CASE_NUMBER="1";
    //紀錄step_order record_order
    String steporder[];
    String recordorder[];
    int ok=0;

    //orm
    private RuntimeExceptionDao<case_masterVo, Integer> case_masterRuntimeDao;
    private case_masterDao mcase_masterDao;
    private RuntimeExceptionDao<sop_detailVo, Integer> sop_detailRuntimeDao;
    private sop_detailDao msop_detailDao;
    private RuntimeExceptionDao<step_recordVo, Integer> step_recordRuntimeDao;
    private step_recordDao mstep_recordDao;
    private RuntimeExceptionDao<case_recordVo, Integer> case_recordRuntimeDao;
    private case_recordDao mcase_recordDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_case_ending);

        change=(Button)findViewById(R.id.change);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");	//輸出Bundle內容
//        TAG_ACCOUNT = bundle.getString("TAG_ACCOUNT");	//輸出Bundle內容
       // TAG_ACCOUNT="test@gmail.com";
        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        valueList = new ArrayList<HashMap<String, String>>();

        //orm account
        DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepCaseEnding.this);
        member_accountDao mmember_accountDao = new  member_accountDao();
        List<member_accountVo> memberlist = null;
        memberlist = mmember_accountDao.selectColumns(mDatabaseHelper4, "FIELD_Account");
        TAG_ACCOUNT = memberlist.get(0).getAccount();

            //orm
            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
            //取紀錄值
            mcase_recordDao = new case_recordDao();
            List<case_recordVo> caserecordlist = null;
            caserecordlist = mcase_recordDao.selectRaw(mDatabaseHelper, "Case_number=" + "'" + TAG_CASE_NUMBER + "'");


            recordorder = new String[caserecordlist.size()];
            steporder = new String[caserecordlist.size()];
            for (int i = 0; i < caserecordlist.size(); i++) {
                recordorder[i] = caserecordlist.get(i).getRecord_order();
                steporder[i] = caserecordlist.get(i).getStep_order();
            }

            LinearLayout ly = (LinearLayout) findViewById(R.id.endlayout);

            Count = caserecordlist.size();

            for (int i = 0; i < caserecordlist.size(); i++) {
                //用case_number 還有case_record的step 去抓資料庫的紀錄單位和敘述
                msop_detailDao = new sop_detailDao();
                List<sop_detailVo> sopdetaillist = null;
                sopdetaillist = msop_detailDao.selectRawByNest2(mDatabaseHelper, "Case_number", TAG_CASE_NUMBER, "Sop_number","Step_order",caserecordlist.get(i).getStep_order());

                //取出需要的sop_number 再找出step_number 還需要order
                mstep_recordDao = new step_recordDao();
                List<step_recordVo> steprecordlist = null;
                steprecordlist = mstep_recordDao.selectRaw2(mDatabaseHelper,"Step_number",sopdetaillist.get(0).getStep_order(),"Record_order",caserecordlist.get(i).getRecord_order());

                TextView text1 = new TextView(StepCaseEnding.this);
                text1.setTextSize(17);
                text1.setText(steporder[i]+"."+recordorder[i]+steprecordlist.get(0).getRecord_text() + "(" + steprecordlist.get(0).getRecord_unit() + ")");


                edit[i] = new EditText(StepCaseEnding.this.getApplicationContext());

                edit[i].setBackgroundColor(Color.parseColor("#FEFBE6"));
                edit[i].setTextColor(Color.rgb(0, 0, 0));
                edit[i].setSingleLine(true);
                //edit.linecolor
                edit[i].setText(caserecordlist.get(i).getRecord_value());
                ly.addView(text1);
                ly.addView(edit[i]);
            }

            // new LoadInput().execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_case_ending, menu);
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
    public void endChange (View v){
        //orm update
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(StepCaseEnding.this);
        case_recordDao mcase_recordDao = new case_recordDao();
        case_recordVo mcase_recordVo = new case_recordVo();


        for(int i =0;i< Count;i++){

            //UPDATE orm
            //mmember_accountDao2.update(mDatabaseHelper2,"account","test","username","678");
            mcase_recordDao.update_record(mDatabaseHelper, "Case_number", TAG_CASE_NUMBER, "Step_order", steporder[i], "Record_order", recordorder[i], "Record_value", StepCaseEnding.this.edit[i].getText().toString());
          //  List<case_recordVo> case_recordlist = null ;
         //   case_recordlist = mcase_recordDao.selectRaw(mDatabaseHelper,"Case_number="+"'"+TAG_CASE_NUMBER+"'");
        //   Log.d("update", case_recordlist.get(0).getRecord_value());


        }


//        SOPContent1[] SO= new SOPContent1[10];
//      for(Step=0;Step<Count;Step++){
//          SO[Step]=new SOPContent1();
//          SO[Step].execute(Step);
//      }

            AlertDialog.Builder dialog = new AlertDialog.Builder(StepCaseEnding.this);
            dialog.setTitle("");
            dialog.setMessage("更改成功");
            dialog.show();

    }

    //結案 和上傳
    public void close (View v){

        //無論連線或離線都要刪掉的orm
        DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepCaseEnding.this);
        case_masterDao mcase_masterDao4 = new case_masterDao();
        List<case_masterVo> caselist = null ;
        caselist = mcase_masterDao4.selectRaw(mDatabaseHelper4,"Case_number="+"'"+TAG_CASE_NUMBER+"'");
        //delete sop_master中的sop
        sop_masterDao msop_masterDao = new sop_masterDao();
        msop_masterDao.delete(mDatabaseHelper4,"Sop_number",caselist.get(0).getSop_number());
        //delete step_detail
        sop_detailDao msop_detailDao = new sop_detailDao();
        msop_detailDao.delete(mDatabaseHelper4,"Sop_number",caselist.get(0).getSop_number());
        //delete step_record
        step_recordDao mstep_record = new step_recordDao();
        //取紀錄值
        mcase_recordDao = new case_recordDao();
        List<case_recordVo> caserecordlist = null;
        caserecordlist = mcase_recordDao.selectRaw(mDatabaseHelper4, "Case_number=" + "'" + TAG_CASE_NUMBER + "'");
        for(int i =0;i<Count;i++){
            //用case_number 還有case_record的step 去抓資料庫的紀錄單位和敘述
            msop_detailDao = new sop_detailDao();
            List<sop_detailVo> sopdetaillist = null;
            sopdetaillist = msop_detailDao.selectRawByNest2(mDatabaseHelper4, "Case_number", TAG_CASE_NUMBER, "Sop_number","Step_order",caserecordlist.get(i).getStep_order());
            //取出需要的sop_number 再找出step_number 還需要order
            mstep_recordDao = new step_recordDao();
            mstep_recordDao.delete2(mDatabaseHelper4, "Step_number", sopdetaillist.get(0).getStep_order(), "Record_order", caserecordlist.get(i).getRecord_order());

        }


        //檢查是否有網路
        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        //null代表沒網路
        if(info==null) {
            Log.d("network","null");
            //Delete orm
            DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(StepCaseEnding.this);
            case_masterDao mcase_master2 = new case_masterDao();

            mcase_master2.update(mDatabaseHelper2,"Case_number",TAG_CASE_NUMBER,"Case_mark","1");
//             List<case_masterVo> caselist = null ;
//             caselist = mcase_masterDao.selectRaw(mDatabaseHelper2,"Case_number="+"'"+TAG_CASE_NUMBER+"'");
//             Log.d("casemark",caselist.get(0).getCase_mark());
//            //delete sop_master中的sop
//            sop_masterDao msop_masterDao = new sop_masterDao();
//            msop_masterDao.delete(mDatabaseHelper2,"Sop_number",caselist.get(0).getSop_number());

            Intent i = new Intent(this, Mysop.class);
            Bundle bundle = new Bundle();
            bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtras(bundle);	//將參數放入intent
            startActivity(i);
            finish();
        }else {
            Log.d("network","Qui");
            //上傳
            SOPContent1[] SO= new SOPContent1[Count];
            for(Step=0;Step<Count;Step++){
                SO[Step]=new SOPContent1();
                SO[Step].execute(Step);
            }
            //結案
            new SOPContent2().execute();

            //Delete orm
            DatabaseHelper mDatabaseHelper3 = DatabaseHelper.getHelper(StepCaseEnding.this);
            case_recordDao mcase_recordDao3 = new case_recordDao();
            mcase_recordDao3.delete(mDatabaseHelper3, "Case_number", TAG_CASE_NUMBER);
            case_masterDao mcase_master3 = new case_masterDao();
            mcase_master3.delete(mDatabaseHelper3, "Case_number", TAG_CASE_NUMBER);
            // List<case_masterVo> caselist = null ;
            // caselist = mcase_masterDao.selectRaw(mDatabaseHelper2,"Account="+"'"+"test@gmail.com"+"'");
            // Log.d("Here clear",caselist.get(0).getCase_number());
            finish();
        }



    }

    //更改紀錄
    class SOPContent1 extends AsyncTask<Integer, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepCaseEnding.this);
            pDialog.setMessage("Changing..Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
          //  pDialog.show();

        }


        protected String doInBackground(Integer... args) {

            //先寫死stepnumber
            //String Casenumber ="" ;

            int a=args[0];
            String RecordOrder=Integer.toString(a+1);
            RecordText[a] = StepCaseEnding.this.edit[a].getText().toString();

            ArrayList params = new ArrayList();
            System.out.println("jj"+RecordText[a]);

            params.add(new BasicNameValuePair("RecordText", RecordText[a]));
            params.add(new BasicNameValuePair("CaseNumber", TAG_CASE_NUMBER) );
            params.add(new BasicNameValuePair("RecordOrder", recordorder[a]) );
            params.add(new BasicNameValuePair("StepOrder", steporder[a]) );

            // 上傳紀錄的紀錄
            JSONObject json1 = StepCaseEnding.this.jsonParser.makeHttpRequest(StepCaseEnding.url_record, "POST", params);

            try {

                int e = json1.getInt(TAG_SUCCESS);
                if(e == 1) {
                ok=1;
                }else{
                ok=0;
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
          //  pDialog.dismiss();

        }
    }
    //結案 刪除sop
    class SOPContent2 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepCaseEnding.this);
            pDialog.setMessage("Being Close. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ArrayList params2 = new ArrayList();

            //params2.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            params2.add(new BasicNameValuePair("Casenumber", TAG_CASE_NUMBER) );

            JSONObject json2 = StepCaseEnding.this.jsonParser.makeHttpRequest(StepCaseEnding.url_all_products2,"POST",params2);
            try {

                int e3 = json2.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    Intent i = new Intent(StepCaseEnding.this, Mysop.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtras(bundle);	//將參數放入intent
                    startActivity(i);
                }else {

                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            pDialog.dismiss();
            return null;
        }

    }


//    //讀取紀錄 和紀錄說明
//    class LoadInput extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(StepCaseEnding.this);
//            pDialog.setMessage("Loading. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        /**
//         * getting All products from url
//         * */
//        protected String doInBackground(String... args) {
//            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//            params.add(new BasicNameValuePair("Casenumber", TAG_CASE_NUMBER) );
//
//            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
//
//
//
//            // Check your log cat for JSON reponse
//            Log.d("All Products: ", json.toString());
//
//            try {
//                // Checking for SUCCESS TAG
//                //step = json.getString("step");
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//
//                    products = json.getJSONArray(TAG_PRODUCTS);
//
//                    for (int i = 0; i < products.length(); i++) {
//                        JSONObject c = products.getJSONObject(i);
//
//
//                        // Storing each json item in variable
//                        String text = c.getString(TAG_TEXT);
//                        String unit = c.getString(TAG_UNIT);
//
//                        // creating new HashMap
//                        HashMap<String, String> map = new HashMap<String, String>();
//
//                        // adding each child node to HashMap key => value
//                        map.put(TAG_TEXT, text);
//                        map.put(TAG_UNIT, unit);
//
//                        // adding HashList to ArrayList
//                        productsList.add(map);
//
//                    }
//                    //抓記錄值
//                    products1=json.getJSONArray("values");
//                    for(int i=0 ;i<products1.length();i++){
//                        JSONObject c =products1.getJSONObject(i);
//                        String value=c.getString(TAG_VALUE);
//                        String step = c.getString("step");
//                        String order = c.getString("order");
//                        HashMap<String,String> vmap = new HashMap<String, String>();
//                        vmap.put(TAG_VALUE,value);
//                        vmap.put("step",step);
//                        vmap.put("order",order);
//
//                        valueList.add(vmap);
//                    }
//                } else {
//
//
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog
//         * **/
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog after getting all products
//            pDialog.dismiss();
//            // updating UI from Background Thread
///*            runOnUiThread(new Runnable() {
//                public void run() {
//
//                }
//            }); */
//
//            recordorder=new String[products1.length()];
//            steporder=new  String[products1.length()];
//
//            for(int i =0;i<products1.length();i++){
//                recordorder[i]=valueList.get(i).get("order");
//                steporder[i]=valueList.get(i).get("step");
//                System.out.println("order:"+recordorder[i]+"  step:"+steporder[i]);
//            }
//
//
//            LinearLayout ly = (LinearLayout)findViewById(R.id.endlayout);
//
//            Count=products.length();
//
//            for(int i=0; i<products.length();i++) {
//
//                TextView text1 = new TextView(StepCaseEnding.this);
//                text1.setTextSize(17);
//                text1.setText(productsList.get(i).get(TAG_TEXT)+"("+productsList.get(i).get(TAG_UNIT)+")");
//
//
//                edit[i] = new EditText(StepCaseEnding.this.getApplicationContext());
//
//                edit[i].setBackgroundColor(Color.parseColor("#FEFBE6"));
//                edit[i].setTextColor(Color.rgb(0, 0, 0));
//                edit[i].setSingleLine(true);
//                //edit.linecolor
//                edit[i].setText(valueList.get(i).get(TAG_VALUE));
//                ly.addView(text1);
//                ly.addView(edit[i]);
//            }
//
//
//
//        }
//
//    }
    }

