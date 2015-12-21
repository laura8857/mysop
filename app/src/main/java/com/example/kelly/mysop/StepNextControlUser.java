package com.example.kelly.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


//p305
public class StepNextControlUser extends Activity {


    private ListView listView;
    //private String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
    private String[] ListOptionName;
    private String[] ListOptionNumber;
    private ArrayAdapter<String> listAdapter;


    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productsList;
    JSONArray products = null;

    private static String url_all_products = "http://140.115.80.237/front/mysop_steprecording2.jsp";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_RECODE = "recode";

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";

    private DatabaseHelper mDatabaseHelper;
    private sop_detailDao msop_detailDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_next_control_user);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");

        //list = new String[2];

        listView = (ListView)findViewById(R.id.next_listView);
/*        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listener);*/

        // Hashmap for ListView
       // productsList = new ArrayList<HashMap<String, String>>();
        // Loading products in Background Thread
        //new LoadInput().execute();
        mDatabaseHelper = DatabaseHelper.getHelper(this);
        msop_detailDao = new sop_detailDao();
        List<sop_detailVo> list = null;
        list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number ="+TAG_STEP_NUMBER);
        Log.d("抓", list.get(0).getSop_number());

        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        sop_detailDao msop_detailDao1 = new sop_detailDao();
        List<sop_detailVo> list1 = null;
        list1 = msop_detailDao1.selectRaw(mDatabaseHelper1, "Sop_number ="+list.get(0).getSop_number());
        Log.d("抓", list1.get(0).getStep_number());

        ListOptionName = new String[list1.size()];
        ListOptionNumber = new String[list1.size()];
        int r=0;
        for(int i=0; i<list1.size();i++) {
            if(list1.get(i).getStep_number().equals(TAG_STEP_NUMBER)){
                continue;
            }
            ListOptionName[r] = list1.get(i).getStep_name();
            ListOptionNumber[r] = list1.get(i).getStep_number();
            r++;
        }
        listAdapter = new ArrayAdapter(StepNextControlUser.this,android.R.layout.simple_list_item_1,ListOptionName);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(listener);


    }

    private ListView.OnItemClickListener listener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Toast.makeText(getApplicationContext(),"你選擇的是"+ListOptionName[position], Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);
            bundle.putString("TAG_NEXT_STEP_NUMBER", ListOptionNumber[position]);
            Intent it1 = new Intent(StepNextControlUser.this, StepActionControl.class);
            it1.putExtras(bundle);//將參數放入intent
            startActivity(it1);
            finish();
        }

    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_next_control_user, menu);
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

/*
    class LoadInput extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StepNextControlUser.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
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

            ListOption = new String[products.length()];

            for(int i=0; i<products.length();i++) {

                int r = i+1;
                ListOption[i] = productsList.get(i).get(TAG_RECODE+r);

            }

            listAdapter = new ArrayAdapter(StepNextControlUser.this,android.R.layout.simple_list_item_1,ListOption);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(listener);


        }

    }

*/


}
