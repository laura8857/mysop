package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;
import Ormlite.sop_masterDao;
import Ormlite.sop_masterVo;


public class Start extends Activity {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    private RuntimeExceptionDao<sop_masterVo, Integer> sop_masterRuntimeDao;
    private sop_masterDao msop_masterDao;
    private sop_detailDao msop_detailDao;

    private static String url_all_products = "http://140.115.82.211/front/mysop_start.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SOPNAME = "sop_name";
    private static String SOPNAME = "";
    private static final String TAG_SOPDETAIL = "sop_detail";
    private static String SOPDETAIL = "";

    String TAG_CASE_NUMBER = "";
//    private Dao<sop_masterVo, Integer> msop_masterDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");

        Log.d("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        msop_masterDao = new sop_masterDao();
        //menber_accountRuntimeDao = mDatabaseHelper.getMember_accountDao();
        List<sop_masterVo> list = null;
        list = msop_masterDao.selectRawByNest(mDatabaseHelper,"Case_number",TAG_CASE_NUMBER,"Sop_number");
        //list = msop_masterDao.selectRaw(mDatabaseHelper, "Sop_number='20151111'");

        //共有幾步驟
        msop_detailDao = new sop_detailDao();
        List<sop_detailVo> listforcount = null;
        listforcount = msop_detailDao.selectRawByNest(mDatabaseHelper,"Case_number",TAG_CASE_NUMBER,"Sop_number");
        //listforcount = msop_detailDao.selectRaw(mDatabaseHelper, "Sop_number='20151111'");
        int Count = listforcount.size();

        TextView start_title = (TextView)findViewById(R.id.start_title);
        start_title.setText(list.get(0).getSop_name());

        TextView start_detail = (TextView)findViewById(R.id.start_detail);
        start_detail.setText(list.get(0).getSop_detail());

        TextView start_right = (TextView)findViewById(R.id.start_right);
        start_right.setText(String.valueOf(Count));

        Log.d("抓", list.get(0).getSop_number());
        //Toast.makeText(this, list.get(0).getSop_number(), Toast.LENGTH_SHORT).show();

        // Loading products in Background Thread
        // new LoadAllProducts().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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

    public void startload(View view){

            //(Changepassword.this.new CreateAccount()).execute(new String[0]);

/*            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
            msop_masterDao = new sop_masterDao();
            sop_masterVo msop_masterVo = new  sop_masterVo();

            msop_masterVo.setSop_name("SOP名字");
            msop_masterVo.setSop_detail("SOP的詳細介紹");
            msop_masterVo.setSop_number("201511111");
            msop_masterVo.setAccount("abcc");
            msop_masterVo.setSop_version("1");*/

            Bundle bundle = new Bundle();
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
            Intent it = new Intent(Start.this,StepActionControl.class);
            it.putExtras(bundle);
            startActivity(it);
            finish();
    }
}



//
//    /**
//     * Background Async Task to Load all product by making HTTP Request
//     * */
//    class LoadAllProducts extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(Start.this);
//            pDialog.setMessage("Loading products. Please wait...");
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
//            // getting JSON string from URL
//            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
//
//            // Check your log cat for JSON reponse
//            Log.d("All Products: ", json.toString());
//
//            try {
//                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//
//                    SOPNAME = json.getString(TAG_SOPNAME);
//                    SOPDETAIL = json.getString(TAG_SOPDETAIL);
//
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
//            TextView start_title = (TextView)findViewById(R.id.start_title);
//            start_title.setText(SOPNAME);
//
//            TextView start_detail = (TextView)findViewById(R.id.start_detail);
//            start_detail.setText(SOPDETAIL);
//
//            TextView start_right = (TextView)findViewById(R.id.start_right);
//            start_right.setText("9");
//
//            AlertDialog.Builder dialog = new AlertDialog.Builder(Start.this);
//            dialog.setTitle("");
//            dialog.setMessage(SOPNAME);
//            dialog.show();
//
//
//        }
//
//    }


