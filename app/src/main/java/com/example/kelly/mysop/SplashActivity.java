package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
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
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;
import Ormlite.sop_masterDao;
import Ormlite.sop_masterVo;


public class SplashActivity extends Activity {

    private member_accountDao mmember_accountDao;
    String TAG_ACCOUNT;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    ArrayList<HashMap<String, String>> productsList1;
    private static String url_all_products = "http://140.115.80.237/front/mysop_step_detail.jsp";
    private static String url_all_products1 = "http://140.115.80.237/front/mysop_mysop1.jsp";
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //寫死 insert
        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao1;
        mmember_accountDao1 = new member_accountDao();
        member_accountVo mmember_accountVo = new member_accountVo();

        mmember_accountVo.setAccount("test@gmail.com");
        mmember_accountVo.setUsername("user1");
        mmember_accountVo.setPassword("test");

        mmember_accountDao1.insert(mDatabaseHelper1, mmember_accountVo);


        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        List<member_accountVo> list = null;

        mmember_accountDao = new member_accountDao();
        //劉昱呈這邊需要再修一下
        list = mmember_accountDao.selectRaw(mDatabaseHelper, "account");

//        if (list.isEmpty()) {
//            startActivity(new Intent().setClass(SplashActivity.this, Login.class));
//        } else {
//            //JSP要塞ORM有點難ＱＡＱ
//            startActivity(new Intent().setClass(SplashActivity.this, Mysop.class));
//        }

        TAG_ACCOUNT=list.get(0).getAccount();
        new LoadAllProducts().execute();

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

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable



                        String sopnumber = c.getString("sop_number");
                        String sopnumber1 =c.getString("sop_number");
                        String steporder = c.getString("step_order");
                        String stepnumber= c.getString("step_number");
                        String stepname = c.getString("step_name");
                        String sopname = c.getString("sop_name");
                        String steppurpose = c.getString("step_purpose");
                        String sopgraphsrc = c.getString("sop_graph_src");
                        String sopintro1 = c.getString("sop_intro");
                        String stepintro = c.getString("step_intro");
                        String sop_detail = c.getString("sop_detail");
                        String account = c.getString("account");
                        String startrule1 = c.getString("startrule");
                        String startrule = c.getString("start_rule");
                        String startvalue1 = c.getString("start_value1");
                        String startvalue2 = c.getString("start_value2");
                        String finishrule = c.getString("finish_rule");
                        String finishvalue1 = c.getString("finish_value1");
                        String finishvalue2 = c.getString("finish_value2");
                        String nextsteprule = c.getString("next_step_rule");
                        String next_step_number = c.getString("next_step_number");


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_SOPNAME, sopname);
                        map.put(TAG_CASENUMBER, sopnumber);
                        map.put(TAG_STARTRULE, startrule);
                        map.put(TAG_STARTVALUE,startvalue);
                        map.put(TAG_PICTURE,picture);
                        map.put(TAG_ORDER,order);

                        map.put("sop_number",sopnumber);
                        map.put("step_order");
                        String stepnumber= c.getString("step_number");
                        String stepname = c.getString("step_name");
                        String steppurpose = c.getString("step_purpose");
                        String stepintro = c.getString("step_intro");
                        String startrule = c.getString("start_rule");
                        String startvalue1 = c.getString("start_value1");
                        String startvalue2 = c.getString("start_value2");
                        String finishrule = c.getString("finish_rule");
                        String finishvalue1 = c.getString("finish_value1");
                        String finishvalue2 = c.getString("finish_value2");
                        String nextsteprule = c.getString("next_step_rule");
                        String next_step_number = c.getString("next_step_number");


                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } else {


                }
                // Checking for SUCCESS TAG
                int success1 = json1.getInt(TAG_SUCCESS);

                if (success1 == 1) {
                    // products found
                    // Getting Array of Products
                    products1 = json1.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products1.length(); i++) {
                        JSONObject c = products1.getJSONObject(i);

                        // Storing each json item in variable
                        String sopnumber = c.getString("sop_number");
                        String total = c.getString(TAG_TATOL);
                        String number = c.getString(TAG_SOPNUMBER);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_TATOL, total);
                        map.put(TAG_SOPNUMBER, number);

                        // adding HashList to ArrayList
                        productsList1.add(map);
                    }
                } else {


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

            DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_detailDao msop_detailDao2 = new sop_detailDao();
            sop_detailVo[] msop_detailVo2 = new sop_detailVo[500];

            // dismiss the dialog after getting all products
            for (int i = 0; i <  products.length(); i++){

                msop_detailVo2[i].setSop_number(productsList.get(i).get("sop_number"));
                msop_detailVo2[i].setStep_order(productsList.get(i).get("step_order"));
                msop_detailVo2[i].setStep_number(productsList.get(i).get("step_number"));
                msop_detailVo2[i].setStep_purpose(productsList.get(i).get("step_purpose"));
                msop_detailVo2[i].setStep_intro(productsList.get(i).get("step_intro"));
                msop_detailVo2[i].setStart_rule(productsList.get(i).get("start_rule"));
                msop_detailVo2[i].setStart_value1(productsList.get(i).get("start_value1"));
                msop_detailVo2[i].setFinish_rule(productsList.get(i).get("finish_rule"));
                msop_detailVo2[i].setFinish_value1(productsList.get(i).get("finish_value1"));
                msop_detailVo2[i].setFinish_value2(productsList.get(i).get("finish_value2"));
                msop_detailVo2[i].setNext_step_number(productsList.get(i).get("next_step_number"));
                msop_detailVo2[i].setNext_step_rule(productsList.get(i).get("next_step_rule"));
                msop_detailDao2.insert(mDatabaseHelper2, msop_detailVo2[i]);

            }


            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(SplashActivity.this);
            case_masterDao mcase_masterDao4 = new case_masterDao();
            case_masterVo[] mcase_masterVo4 = new case_masterVo[500];

            // dismiss the dialog after getting all products
            for (int i = 0; i <  products.length(); i++){

                mcase_masterVo4[i].setSop_number(productsList.get(i).get("sop_number"));
                mcase_masterVo4[i].setStep_number(productsList.get(i).get("step_number"));
                mcase_masterVo4[i].setAccount(productsList.get(i).get("account"));
                mcase_masterVo4[i].setCase_number(productsList.get(i).get("case_number"));
                mcase_masterDao4.insert(mDatabaseHelper4, mcase_masterVo4[i]);

            }

            DatabaseHelper mDatabaseHelper5 = DatabaseHelper.getHelper(SplashActivity.this);
            sop_masterDao msop_masterDao5 = new sop_masterDao();
            sop_masterVo[] msop_masterVo5 = new sop_masterVo[500];

            // dismiss the dialog after getting all products
            for (int i = 0; i <  products.length(); i++){

                msop_masterVo5[i].setSop_number(productsList.get(i).get("sop_number"));
                msop_masterVo5[i].setSop_name(productsList.get(i).get("sop_name"));
                msop_masterVo5[i].setSop_graph_src(productsList.get(i).get("sop_graph_src"));
                msop_masterVo5[i].setSop_intro(productsList.get(i).get("sop_intro"));
                msop_masterVo5[i].setSop_detail(productsList.get(i).get("sop_detail"));
                msop_masterVo5[i].setAccount(productsList.get(i).get("account"));
                msop_masterVo5[i].setStart_rule(productsList.get(i).get("start_rule"));
                msop_masterDao5.insert(mDatabaseHelper5, msop_masterVo5[i]);

            }


        }
   }
}
