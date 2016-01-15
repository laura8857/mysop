package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_masterDao;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepActionControl extends Activity {
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_update = "http://140.115.80.237/front/mysop_AC1.jsp";
    private static String url_checkStartrule = "http://140.115.80.237/front/mysop_AC.jsp";
    private static final String TAG_SUCCESS = "success";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    private static String url_checkall = "http://140.115.80.237/front/mysop_AC2.jsp";
    String TAG_CASE_NUMBER = "";
    int StartRule;

    private sop_detailDao msop_detailDao;
    private case_masterDao mcase_masterDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_action_control);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle

        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        List<sop_detailVo> list = null;

        //從P305來的話
        if(intent.hasExtra("TAG_NEXT_STEP_NUMBER")){
            TAG_STEP_NUMBER = bundle.getString("TAG_NEXT_STEP_NUMBER");
            TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
            //new Update().execute();


            //"UPDATE case_master SET last_do_order='"+Stepnumber+"' WHERE case_number='"+Casenumber+"'"
            mcase_masterDao = new case_masterDao();
            mcase_masterDao.update(mDatabaseHelper,"Case_number",TAG_CASE_NUMBER,"Step_number",TAG_STEP_NUMBER);
            //mcase_masterDao.update(mDatabaseHelper,mcase_masterVo);

            //list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number IN(SELECT last_do_order FROM case_master WHERE case_number='"+TAG_CASE_NUMBER+"')");
            msop_detailDao = new sop_detailDao();
            list = msop_detailDao.selectRawByNest(mDatabaseHelper,"Case_number",TAG_CASE_NUMBER,"Step_number");
            StartRule = Integer.valueOf(list.get(0).getStart_rule());
            TAG_STEP_ORDER = Integer.valueOf(list.get(0).getStep_order());
            UseStartRule(StartRule);


        }else{
            //沒從P305來的話
            //TAG_CASE_NUMBER = "3";//nfc
            TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
            //new Checkall().execute();
            Log.d("抓",TAG_CASE_NUMBER);
            msop_detailDao = new sop_detailDao();
            //"Step_number IN(SELECT last_do_order FROM case_master WHERE case_number='"++"')"
            list = msop_detailDao.selectRawByNest(mDatabaseHelper,"Case_number",TAG_CASE_NUMBER,"Step_number");
            Log.d("抓1", list.get(0).getSop_number());

            TAG_STEP_NUMBER = list.get(0).getStep_number();
            Log.d("StepActionControl",TAG_STEP_NUMBER);
            StartRule = Integer.valueOf(list.get(0).getStart_rule());
            TAG_STEP_ORDER = Integer.valueOf(list.get(0).getStep_order());

            //到startrule選擇
            UseStartRule(StartRule);

        }

    }


    public void UseStartRule(int startrule){

        //設定傳送參數
        Bundle bundle = new Bundle();
        bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
        bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
        bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

        Log.d("StepActionControl","finish");

        switch (startrule){
            case 1:
                // cagetory.setText("人工啟動");
                Intent it1 = new Intent(StepActionControl.this,StepActionControlArtificial.class);
                it1.putExtras(bundle);//將參數放入intent
                startActivity(it1);
                finish();
                break;
            case 2:
                // cagetory.setText("前一步驟\n完工");
                Intent it = new Intent(StepActionControl.this,Stepdescription.class);
                it.putExtras(bundle);//將參數放入intent
                startActivity(it);
                finish();
                break;
            case 3:
                //cagetory.setText("Beacon");
                Intent it3 = new Intent(StepActionControl.this,StepActionControlIbeacon.class);
                it3.putExtras(bundle);//將參數放入intent
                startActivity(it3);
                finish();
                break;
            case 4:
                //cagetory.setText("QR code");
                Intent it4 = new Intent(StepActionControl.this,StepActionControlQRcode.class);
                it4.putExtras(bundle);//將參數放入intent
                startActivity(it4);
                finish();
                break;
            case 5:
                // cagetory.setText("NFC");
                Intent it5 = new Intent(StepActionControl.this,StepActionControlNFC.class);
                it5.putExtras(bundle);//將參數放入intent
                startActivity(it5);
                finish();
                break;
            case 6:
                // cagetory.setText("定位");
                Intent it6 = new Intent(StepActionControl.this,StepActionControlGPS.class);
                it6.putExtras(bundle);//將參數放入intent
                startActivity(it6);
                finish();
                break;
            case 7:
                //  cagetory.setText("時間到期");
                Intent it7 = new Intent(StepActionControl.this,StepActionControlTime.class);
                it7.putExtras(bundle);//將參數放入intent
                startActivity(it7);
                finish();
                break;
            default:
                Log.d("WRONG", "wrong");
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_action_control, menu);
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

/*    //不從p305來
    class Checkall extends AsyncTask<String, String, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(StepActionControl.this);
            //pDialog.setMessage("Loading..... Please wait...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(false);
            //pDialog.show();
        }

        protected Integer doInBackground(String... args) {

            int startrule=0;
            String Casenumber = TAG_CASE_NUMBER;

            //for get
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Casenumber", Casenumber));
            JSONObject json = StepActionControl.this.jsonParser.makeHttpRequest(StepActionControl.url_checkall,"GET",params);

            try {
                //加入清單
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    startrule = json.getInt("startrule");
                    TAG_STEP_NUMBER = json.getString("stepnumber");
                    TAG_STEP_ORDER = json.getInt("steporder");
                }else{

                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return startrule;
        }

        protected void onPostExecute(Integer startrule) {
            //pDialog.dismiss();

            //設定傳送參數
            Bundle bundle = new Bundle();
            bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
            bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

            switch (startrule){
                case 1:
                    // cagetory.setText("人工啟動");
                    Intent it1 = new Intent(StepActionControl.this,StepActionControlArtificial.class);
                    it1.putExtras(bundle);//將參數放入intent
                    startActivity(it1);

                    break;
                case 2:
                    // cagetory.setText("前一步驟\n完工");
                    Intent it = new Intent(StepActionControl.this,Stepdescription.class);
                    it.putExtras(bundle);//將參數放入intent
                    startActivity(it);

                    break;
                case 3:
                    //cagetory.setText("Beacon");
                    Intent it3 = new Intent(StepActionControl.this,StepActionControlIbeacon.class);
                    it3.putExtras(bundle);//將參數放入intent
                    startActivity(it3);
                    break;
                case 4:
                    //cagetory.setText("QR code");
                    Intent it4 = new Intent(StepActionControl.this,StepActionControlQRcode.class);
                    it4.putExtras(bundle);//將參數放入intent
                    startActivity(it4);
                    finish();
                    break;
                case 5:
                    // cagetory.setText("NFC");
                    Intent it5 = new Intent(StepActionControl.this,StepActionControlNFC.class);
                    it5.putExtras(bundle);//將參數放入intent
                    startActivity(it5);
                    finish();
                    break;
                case 6:
                    // cagetory.setText("定位");
                    Intent it6 = new Intent(StepActionControl.this,StepActionControlGPS.class);
                    it6.putExtras(bundle);//將參數放入intent
                    startActivity(it6);
                    finish();
                    break;
                case 7:
                    //  cagetory.setText("時間到期");
                    Intent it7 = new Intent(StepActionControl.this,StepActionControlTime.class);
                    it7.putExtras(bundle);//將參數放入intent
                    startActivity(it7);
                    finish();
                    break;
                default:
                    Log.d("WRONG", "wrong");
                    break;
            }
        }

    }*/

    //從p305來，Update last_do_order
/*    class Update extends AsyncTask<String, String, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepActionControl.this);
            pDialog.setMessage("Loading..... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Integer doInBackground(String... args) {

            int updatecheck=0;
            String Stepnumber = TAG_STEP_NUMBER;
            String Casenumber = TAG_CASE_NUMBER;
            //for get
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));
            params.add(new BasicNameValuePair("Casenumber", Casenumber));
            JSONObject json = StepActionControl.this.jsonParser.makeHttpRequest(StepActionControl.url_update,"POST",params);

            try {
                //加入清單
                int e3 = json.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    updatecheck=1;
                }else{
                    updatecheck=2;
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return updatecheck;
        }

        protected void onPostExecute(Integer updatecheck) {
            pDialog.dismiss();
            if(updatecheck==1) {
                new CheckStartrule().execute();
            }else{
                Toast.makeText(getApplicationContext(), "更新失敗!請重新啟動...", Toast.LENGTH_LONG).show();
            }
        }

    }*/

    //判斷啟動規則 1人工啟動 2前一步驟完工 3beacon 4QRcode 5NFC 6定位 7時間
/*    class CheckStartrule extends AsyncTask<String, String, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepActionControl.this);
            pDialog.setMessage("Loading..... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Integer doInBackground(String... args) {


            String Stepnumber = TAG_STEP_NUMBER;

            int startrule = 0;

            //for get
            ArrayList params = new ArrayList();

            params.add(new BasicNameValuePair("Stepnumber", Stepnumber));

            JSONObject json = StepActionControl.this.jsonParser.makeHttpRequest(StepActionControl.url_checkStartrule,"GET",params);

            try {
                //加入清單
                int e3 = json.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    startrule = json.getInt("startrule");
                    TAG_STEP_ORDER = json.getInt("steporder");
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return startrule;
        }

        protected void onPostExecute(Integer startrule) {

            pDialog.dismiss();

            //設定傳送參數
            Bundle bundle = new Bundle();
            bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
            bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

            switch (startrule){
                case 1:
                   // cagetory.setText("人工啟動");
                    Intent it1 = new Intent(StepActionControl.this,StepActionControlArtificial.class);
                    it1.putExtras(bundle);//將參數放入intent
                    startActivity(it1);

                    break;
                case 2:
                   // cagetory.setText("前一步驟\n完工");
                    Intent it = new Intent(StepActionControl.this,Stepdescription.class);
                    it.putExtras(bundle);//將參數放入intent
                    startActivity(it);

                    break;
                case 3:
                    //cagetory.setText("Beacon");
                    Intent it3 = new Intent(StepActionControl.this,StepActionControlIbeacon.class);
                    it3.putExtras(bundle);//將參數放入intent
                    startActivity(it3);
                    break;
                case 4:
                    //cagetory.setText("QR code");
                    Intent it4 = new Intent(StepActionControl.this,StepActionControlQRcode.class);
                    it4.putExtras(bundle);//將參數放入intent
                    startActivity(it4);
                    finish();
                    break;
                case 5:
                   // cagetory.setText("NFC");
                    Intent it5 = new Intent(StepActionControl.this,StepActionControlNFC.class);
                    it5.putExtras(bundle);//將參數放入intent
                    startActivity(it5);
                    finish();
                    break;
                case 6:
                   // cagetory.setText("定位");
                    Intent it6 = new Intent(StepActionControl.this,StepActionControlGPS.class);
                    it6.putExtras(bundle);//將參數放入intent
                    startActivity(it6);
                    finish();
                    break;
                case 7:
                  //  cagetory.setText("時間到期");
                    Intent it7 = new Intent(StepActionControl.this,StepActionControlTime.class);
                    it7.putExtras(bundle);//將參數放入intent
                    startActivity(it7);
                    finish();
                    break;
                default:
                    System.out.println("WRONG");
                    break;
            }


        }

    }*/

}
