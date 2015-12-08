package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    ArrayList<HashMap<String, String>> productsList2;
    private static String url_all_products = "http://140.115.80.237/front/mysop_step_detail.jsp";
    private static String url_all_products1 = "http://140.115.80.237/front/mysop_sop_master.jsp";
    private static String url_all_products2 = "http://140.115.80.237/front/mysop_case_master.jsp";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        productsList = new ArrayList<HashMap<String, String>>();
        productsList1 = new ArrayList<HashMap<String, String>>();
        productsList2 = new ArrayList<HashMap<String, String>>();

        //寫死 insert
        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao1;
        mmember_accountDao1 = new member_accountDao();
        member_accountVo mmember_accountVo = new member_accountVo();

        mmember_accountVo.setAccount("test@gmail.com");
        mmember_accountVo.setUsername("user1");
        mmember_accountVo.setPassword("test");

        mmember_accountDao1.insert(mDatabaseHelper1, mmember_accountVo);


        //DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        List<member_accountVo> list;
        List<member_accountVo> account;

        //劉昱呈這邊需要再修一下
        //list = mmember_accountDao1.selectRaw(mDatabaseHelper1, "account=test@gmail.com");
        //account = mmember_accountDao1.selectColumns(mDatabaseHelper1, "FIELD_Account");
        //if (account.isEmpty()) {
       //     startActivity(new Intent().setClass(SplashActivity.this, Login.class));
       // } else {
            //JSP要塞ORM有點難ＱＡＱ
        //TAG_ACCOUNT=account.get(0).getAccount();
        new LoadAllProducts().execute();
        //    startActivity(new Intent().setClass(SplashActivity.this, Mysop.class));
       // }




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
            JSONObject json2 =SplashActivity.this.jsonParser.makeHttpRequest(SplashActivity.url_all_products2,"GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());
            Log.d("All Products: ", json1.toString());
            Log.d("All Products: ", json2.toString());
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
                        if(i==3){
                            continue;
                        }
                        String sopnumber = c.getString("sop_number");
                        String steporder = c.getString("step_order");
                        String stepnumber= c.getString("step_number");
                        String stepname = c.getString("step_name");
                        String steppurpose = c.getString("step_purpose");
                        String stepintro = c.getString("step_intro");
                        String startrule = c.getString("start_rule");
                        String startvalue1 = c.getString("start_value1");
                        String startvalue2 = c.getString("start_value2");
                        String finishrule = c.getString("finish_rule");
                        Log.d("finish",c.getString("finish_value1"));
                        String finishvalue1 = c.getString("finish_value1");

                        String finishvalue2 = c.getString("finish_value2");
                        String nextsteprule = c.getString("next_step_rule");
                        String next_step_number = c.getString("next_step_number");



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
                        map1.put("sop-detail",sopdetail);
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
            Log.d("countproducts345",String.valueOf(products.length()));

            // dismiss the dialog after getting all products
            for (int i = 0; i <  productsList.size(); i++){
                Log.d("countproducts","123");

                msop_detailVo2.setSop_number(productsList.get(i).get("sop_number"));
                msop_detailVo2.setStep_order(productsList.get(i).get("step_order"));
                msop_detailVo2.setStep_number(productsList.get(i).get("step_number"));
                msop_detailVo2.setStep_purpose(productsList.get(i).get("step_purpose"));
                msop_detailVo2.setStep_intro(productsList.get(i).get("step_intro"));
                msop_detailVo2.setStart_rule(productsList.get(i).get("start_rule"));
                msop_detailVo2.setStart_value1(productsList.get(i).get("start_value1"));
                msop_detailVo2.setStart_value2(productsList.get(i).get("start_value2"));
                msop_detailVo2.setFinish_rule(productsList.get(i).get("finish_rule"));
                msop_detailVo2.setFinish_value1(productsList.get(i).get("finish_value1"));
                msop_detailVo2.setFinish_value2(productsList.get(i).get("finish_value2"));
                msop_detailVo2.setNext_step_number(productsList.get(i).get("next_step_number"));
                msop_detailVo2.setNext_step_rule(productsList.get(i).get("next_step_rule"));

                msop_detailDao2.insert(mDatabaseHelper2, msop_detailVo2);
                Log.d("countproducts",String.valueOf(i));
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

            //startActivity(new Intent().setClass(SplashActivity.this, Login.class));
            startActivity(new Intent().setClass(SplashActivity.this, Mysop.class));
        }
    }
}
