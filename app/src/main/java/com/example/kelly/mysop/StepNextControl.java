package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepNextControl extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_next_control = "http://140.115.80.237/front/mysop_StepNextControl.jsp";
    private static final String TAG_SUCCESS = "success";

    String TAG_NEXT_STEP_NUMBER = "";

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    int NextStepRule;
    private sop_detailDao msop_detailDao;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_next_control);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");

        //new CheckNextControlRule().execute();
        mDatabaseHelper = DatabaseHelper.getHelper(this);
        msop_detailDao = new sop_detailDao();
        List<sop_detailVo> list = null;
        list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number ="+TAG_STEP_NUMBER);
        Log.d("抓", list.get(0).getNext_step_rule()+list.get(0).getNext_step_number());
        NextStepRule = Integer.valueOf(list.get(0).getNext_step_rule());
        TAG_NEXT_STEP_NUMBER = list.get(0).getNext_step_number();

        UseNextStepRule(NextStepRule);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_next_control, menu);
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

    //判斷下一步驟規則 0最後一步 1依順序決定 2依使用者決定 3依資料決定
    public void UseNextStepRule(int NextStepRule){

        Bundle bundle = new Bundle();
        bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

        //最後一步
        if(NextStepRule == 0){
            Intent it = new Intent(StepNextControl.this, StepCaseEnding.class);
            it.putExtras(bundle);
            startActivity(it);
            finish();

        }else {

            switch (NextStepRule) {
                case 1:
                    // 依順序決定
                    bundle.putString("TAG_NEXT_STEP_NUMBER", TAG_NEXT_STEP_NUMBER);
                    Intent it1 = new Intent(StepNextControl.this, StepActionControl.class);
                    it1.putExtras(bundle);//將參數放入intent
                    startActivity(it1);
                    finish();
                    break;
                case 2:
                    // 依使用者決定
                    Log.d("StepNextCrol",String.valueOf(NextStepRule));
                    bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                    bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                    Intent it2 = new Intent(StepNextControl.this, StepNextControlUser.class);
                    it2.putExtras(bundle);//將參數放入intent
                    startActivity(it2);
                    finish();
                    break;
                case 3:
                    // 依資料決定
                    bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                    bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
                    Intent it3 = new Intent(StepNextControl.this, StepNextControlData.class);
                    it3.putExtras(bundle);
                    startActivity(it3);
                    finish();
                    break;
                default:
                    System.out.println("WRONG");
                    break;

            }
        }
    }

/*
    //判斷完工規則 1依順序決定 2依使用者決定 3依資料決定
    class CheckNextControlRule extends AsyncTask<String, String, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepNextControl.this);
            pDialog.setMessage("Loading..... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Integer doInBackground(String... args) {

            //String Stepnumber = "1";
            String Stepnumber = TAG_STEP_NUMBER;
            int nextsteprule = 0;

            //for get
            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));

            JSONObject json = StepNextControl.this.jsonParser.makeHttpRequest(StepNextControl.url_next_control,"GET",params);

            try {
                //加入清單
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    nextsteprule = json.getInt("nextsteprule");
                    TAG_NEXT_STEP_NUMBER = json.getString("nextstepnumber");

                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return nextsteprule;
        }

        protected void onPostExecute(Integer nextsteprule) {

            pDialog.dismiss();

            //設定傳送參數
            Bundle bundle = new Bundle();
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

            //依順序決定but又是最後一步
            if(nextsteprule == null){
                Intent it = new Intent(StepNextControl.this, StepCaseEnding.class);
                it.putExtras(bundle);
                startActivity(it);
                finish();

            }else {

                bundle.putString("TAG_NEXT_STEP_NUMBER", TAG_NEXT_STEP_NUMBER);

                switch (nextsteprule) {
                    case 1:
                        // 依順序決定
                        Intent it1 = new Intent(StepNextControl.this, StepActionControl.class);
                        it1.putExtras(bundle);//將參數放入intent
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        // 依使用者決定
                        Intent it2 = new Intent(StepNextControl.this, StepNextControlUser.class);
                        it2.putExtras(bundle);//將參數放入intent
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        // 依資料決定
                        Intent it3 = new Intent(StepNextControl.this, StepNextControl.class);
                        it3.putExtras(bundle);//將參數放入intent
                        startActivity(it3);
                        finish();
                        break;
                    case 0:
                        // 依資料決定
                        Intent it4 = new Intent(StepNextControl.this, StepCaseEnding.class);
                        it4.putExtras(bundle);//將參數放入intent
                        startActivity(it4);
                        finish();
                        break;

                    default:
                        System.out.println("WRONG");
                        break;

                }
            }

        }

    }*/
}
