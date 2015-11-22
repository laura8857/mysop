package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import Ormlite.DatabaseHelper;
import Ormlite.sop_masterDao;
import Ormlite.sop_masterVo;


public class Start extends Activity {

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();


    private static String url_all_products = "http://140.115.80.237/front/mysop_start.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SOPNAME = "sop_name";
    private static String SOPNAME = "";
    private static final String TAG_SOPDETAIL = "sop_detail";
    private static String SOPDETAIL = "";

//    private Dao<sop_masterVo, Integer> msop_masterDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        DatabaseHelper mDatabaseHelper = DatabaseHelper
                .getHelper(this);
//
//        msop_masterDao = mDatabaseHelper.getSop_MasterDao();
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

    private RuntimeExceptionDao<sop_masterVo, Integer> sop_masterRuntimeDao;
    private sop_masterDao msop_masterDao;

    public void startload(View view){

            //(Changepassword.this.new CreateAccount()).execute(new String[0]);

            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
            msop_masterDao = new sop_masterDao();
            //menber_accountRuntimeDao = mDatabaseHelper.getMember_accountDao();
//            member_accountVo mmember_accountVo = new member_accountVo();
            sop_masterVo msop_masterVo = new  sop_masterVo();

            msop_masterVo.setSop_name("SOP名字");
            msop_masterVo.setSop_detail("SOP的詳細介紹");
            msop_masterVo.setSop_number("201511111");
            msop_masterVo.setAccount("abcc");
            msop_masterVo.setSop_version("1");

            //menber_accountRuntimeDao.createOrUpdate(mmember_accountVo);
            msop_masterDao.insert(mDatabaseHelper,msop_masterVo);

            /*mmember_accountVo = mmember_accountDao.queryForId(1);
            mmember_accountVo.setPassword(NewPassword);
            mmember_accountDao.update(mmember_accountVo);*/
            //Toast.makeText(this, mmember_accountVo.getPassword(), Toast.LENGTH_SHORT).show();
            Log.d("TEST", "TEST");

            Intent it = new Intent(Start.this,ChangePasswordError.class);
            startActivity(it);



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


