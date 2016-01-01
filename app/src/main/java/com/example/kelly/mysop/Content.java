package com.example.kelly.mysop;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_masterDao;
import Ormlite.case_masterVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;
import Ormlite.sop_masterDao;
import Ormlite.sop_masterVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;

//134行 帳號暫時註解
//帳號暫時寫死  sopnumber也是

public class Content extends Activity {

    private EditText inputText;
    private ListView listInput;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    private TextView title;
    private TextView master;
    private TextView download;
    private TextView star;
    private TextView cagetory;
    private TextView subtitle;
    private TextView Ctext;
    private ImageView graph1,graph2,graph3,likepitcure;
    private HorizontalScrollView horizontalScrollView;

    JSONParser jsonParser = new JSONParser();
    //讀取 sop內容 圖片
    private static String url_create_product = "http://140.115.80.237/front/mysop_content.jsp";
    //讀取 評論
    private static String url_create_product1 = "http://140.115.80.237/front/mysop_content1.jsp";
    //寫入評論
    private static String url_create_product2 = "http://140.115.80.237/front/mysop_content3.jsp";
    //數like數
    private static String url_create_product4 = "http://140.115.80.237/front/mysop_content5.jsp";
    //加入清單
    private static String url_create_product5 = "http://140.115.80.237/front/mysop_content6.jsp";
    //讀取 sop的 步驟一的stepnumber for 加入清單用
    private static String url_create_product6 = "http://140.115.80.237/front/mysop_content2.jsp";
    //按讚
    private static String url_create_product7 = "http://140.115.80.237/front/mysop_content7.jsp";
    //下載sop
    private static String url_all_products = "http://140.115.80.237/front/mysop_step_detail.jsp";
    private static String url_all_products1 = "http://140.115.80.237/front/mysop_sop_master.jsp";
    private static String url_all_products2 = "http://140.115.80.237/front/mysop_case_master.jsp";
    private static String url_all_products3 = "http://140.115.80.237/front/mysop_step_record.jsp";

    TextView sopnumber;
    String TAG_ACCOUNT = "";
    String TAG_SOP_NUMBER = "";
    ArrayList<HashMap<String, String>> productsList;
    ArrayList<HashMap<String, String>> likeproductsList;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "sop";
    private static final String TAG_LIKEPRODUCTS = "likenumber";
    private static final String TAG_PID = "account";
    private static final String TAG_NAME = "sop_comment";
    private static final String TAG_NUMBER = "sopnumber";
    private static final String TAG_DETAIL = "detail";
    private static final String TAG_SOPNAME = "sopname";
    private static final String TAG_INTRO = "intro";
    private static final String TAG_USERNAME="username";
    private static final String TAG_SOPGRAPH="sopgraph";
    private static final String TAG_GRAPH1="graph1";
    private static final String TAG_GRAPH2="graph2";
    private static final String TAG_GRAPH3="graph3";
    private static final String TAG_STARTRULE="start_rule";

    private static String STEPNUMBER ="";
    private static String NUMBER ="";
    private  static String DETAIL="";
    private static String SOPNAME="";
    private static String INTRO="";
    private static String USERNAME="";
    private  static String ACCOUNT="";

    private static String STARTRULE="";
    private static String SOPGRAPH="";
    private static String GRAPH1="";
    private static String GRAPH2="";
    private static String GRAPH3="";

    private int likecount=0;


    JSONArray products = null;
    JSONArray likeproducts = null;

    //for 下載
    JSONArray products4 = null;
    JSONArray products1 = null;
    JSONArray products2 = null;
    JSONArray products3 = null;
    private static final String TAG_PRODUCTS1 = "products";
    ArrayList<HashMap<String, String>> productsList4;
    ArrayList<HashMap<String, String>> productsList1;
    ArrayList<HashMap<String, String>> productsList2;
    ArrayList<HashMap<String, String>> productsList3;
    Context mContext;
    DownloadManager manager ;
    DownloadCompleteReceiver receiver;

    int DetectDownload=0;
    int DetectDownloadReceive=0;


    //紀錄like時間
    String str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        inputText = (EditText)findViewById(R.id.inputText);
        listInput = (ListView)findViewById(R.id.listInputText);
        items = new ArrayList<String>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        listInput.setAdapter(adapter);

        title=(TextView)findViewById(R.id.content_title);
        master=(TextView)findViewById(R.id.content_master);
        download=(TextView)findViewById(R.id.download);
        star=(TextView)findViewById(R.id.star);
        cagetory=(TextView)findViewById(R.id.category);
        subtitle=(TextView)findViewById(R.id.content_subtitle);
         Ctext=(TextView)findViewById(R.id.content_text);
        sopnumber=(TextView)findViewById(R.id.content_sopnumber);

        graph1=(ImageView)findViewById(R.id.graph1);
        graph2=(ImageView)findViewById(R.id.graph2);
        graph3=(ImageView)findViewById(R.id.graph3);

        likepitcure=(ImageView)findViewById(R.id.like);

        horizontalScrollView=( HorizontalScrollView)findViewById(R.id.horizontalScrollView);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
       // TAG_ACCOUNT = bundle.getString("TAG_ACCOUNT");	//輸出Bundle內容
        TAG_ACCOUNT = "e@gmail.com";
        TAG_SOP_NUMBER = "20150813";
       // TAG_SOP_NUMBER = bundle.getString("TAG_SOP_NUMBER");


        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();
        likeproductsList = new ArrayList<HashMap<String, String>>();

        //for download
        productsList4 = new ArrayList<HashMap<String, String>>();
        productsList1 = new ArrayList<HashMap<String, String>>();
        productsList2 = new ArrayList<HashMap<String, String>>();
        productsList3 = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
         new SOPContent().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //新增評論
    public void writeCommon (View v){
        if(!inputText.getText().toString().equals("")){
            new SOPContent1().execute();
        }

    }

    //加入清單 和到Mysop頁面
    public void addtolist (View v){
        new SOPContent2().execute();

    }

    //新增按讚
    public void onlike (View v){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        str = formatter.format(curDate);
        Toast.makeText(this, "Like"+str, Toast.LENGTH_LONG).show();
        new ContentLike().execute();

    }
   //圖片網址
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    class SOPContent extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Content.this);
            pDialog.setMessage("Loading Content. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {

            ArrayList params = new ArrayList();
            ArrayList params1 = new ArrayList();
            ArrayList params3 = new ArrayList();
            ArrayList params2 = new ArrayList();

            params1.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER) );
            params.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER) );
            params3.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER) );
            params2.add(new BasicNameValuePair("Sopnumber",TAG_SOP_NUMBER) );

            // json抓sop內容  json1抓評論  json3抓like數
            JSONObject json = Content.this.jsonParser.makeHttpRequest(Content.url_create_product, "GET", params);
            JSONObject json1 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product1, "GET", params1);
            JSONObject json3 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product4, "GET", params3);
            JSONObject json2 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product6, "GET", params2);


            try {

                int e3 = json2.getInt(TAG_SUCCESS);
                if(e3==1){
                    STEPNUMBER=json2.getString("stepnumber");
                    System.out.println("OH"+STEPNUMBER);
                }else{

                }

                //讀取評論
                int e = json1.getInt(TAG_SUCCESS);
                if(e == 1) {

                    products = json1.getJSONArray(TAG_PRODUCTS);

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_PID, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                }else if(e == 2) {
                }
                //算like
                int e1 = json3.getInt(TAG_SUCCESS);

                if(e1 == 1) {
                    likeproducts = json3.getJSONArray(TAG_LIKEPRODUCTS);
                    for(int i=0;i<likeproducts.length();i++){
                        likecount++;
                    }
                }else{

                }

                //讀取sop內容 sop圖片
                int e2 = json.getInt(TAG_SUCCESS);

                if(e2==1){
                    SOPGRAPH = json.getString(TAG_SOPGRAPH);
                    SOPNAME = json.getString(TAG_SOPNAME);
                    NUMBER  = json.getString(TAG_NUMBER);
                    DETAIL = json.getString(TAG_DETAIL);
                    INTRO  = json.getString(TAG_INTRO);
                    ACCOUNT= json.getString(TAG_PID);
                    STARTRULE=json.getString(TAG_STARTRULE);
                    //判斷有沒有介紹的圖片
                    if(!json.getString(TAG_GRAPH1).equals("")){
                        GRAPH1 = json.getString(TAG_GRAPH1);
                    }
                    if(!json.getString(TAG_GRAPH2).equals("")){
                        GRAPH2 = json.getString(TAG_GRAPH2);
                    }
                    if(!json.getString(TAG_GRAPH3).equals("")){
                        GRAPH3 = json.getString(TAG_GRAPH3);
                    }

                }else{
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
            pDialog.dismiss();

            //自動新增評論
            for (int i = 0; i < products.length(); i++) {
                items.add(productsList.get(i).get(TAG_PID)+"\n"+productsList.get(i).get(TAG_NAME));
                listInput.setAdapter(adapter);
                inputText.setText("");
            }
            //放入sop內容
            title.setText(SOPNAME);
            subtitle.setText(INTRO);
            sopnumber.setText(NUMBER);
            Ctext.setText(DETAIL);
            master.setText(ACCOUNT);

            //放入sop圖片們
            new DownloadImageTask((ImageView)findViewById(R.id.content_picture))
                    .execute(SOPGRAPH);
            if(GRAPH1.equals("")){
                graph1.setVisibility(8);
            }else{
            new DownloadImageTask((ImageView)findViewById(R.id.graph1))
                    .execute(GRAPH1);
                horizontalScrollView.setVisibility(0);
                horizontalScrollView.getLayoutParams().height=300;
                graph1.setVisibility(0);
                graph1.getLayoutParams().width=300;
                graph1.getLayoutParams().height=300;
            }
            if(GRAPH2.equals("")){
                graph2.setVisibility(8);
            }else{
                new DownloadImageTask((ImageView)findViewById(R.id.graph2))
                        .execute(GRAPH2);
                horizontalScrollView.setVisibility(0);
                horizontalScrollView.getLayoutParams().height=300;
                graph2.setVisibility(0);
                graph2.getLayoutParams().width=300;
                graph2.getLayoutParams().height=300;
            }
            if(GRAPH3.equals("")){
                graph3.setVisibility(8);
            }else{
                new DownloadImageTask((ImageView)findViewById(R.id.graph3))
                        .execute(GRAPH3);
                horizontalScrollView.setVisibility(0);
                horizontalScrollView.getLayoutParams().height=300;
                graph3.setVisibility(0);
                graph3.getLayoutParams().width=300;
                graph3.getLayoutParams().height=300;
            }


            //放入收藏數
             download.setText(String.valueOf(likecount));

            //放入啟動規則
            switch (STARTRULE){
                case "1":
                    cagetory.setText("人工啟動");
                    cagetory.setTextSize(20);
                    break;
                case "2":
                    cagetory.setText("前一步驟\n完工");
                    cagetory.setTextSize(16);
                    break;
                case "3":
                    cagetory.setText("Beacon");
                    cagetory.setTextSize(20);
                    break;
                case "4":
                    cagetory.setText("QR code");
                    cagetory.setTextSize(20);
                    break;
                case "5":
                    cagetory.setText("NFC");
                    cagetory.setTextSize(25);
                    break;
                case "6":
                    cagetory.setText("定位");
                    cagetory.setTextSize(25);
                    break;
                case "7":
                    cagetory.setText("時間到期");
                    cagetory.setTextSize(16);
                    break;
            }
        }
    }

    //寫入評論
    class SOPContent1 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Content.this);
            pDialog.setMessage("Loading Content. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String Inputtext = Content.this.inputText.getText().toString();


            //for get
            ArrayList params2 = new ArrayList();

            params2.add(new BasicNameValuePair("Inputtext", Inputtext) );
            params2.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            params2.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER) );

            JSONObject json2 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product2,"POST",params2);


            try {
               //寫入評論
                int e3 = json2.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    USERNAME = json2.getString(TAG_USERNAME);
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            pDialog.dismiss();

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();

            items.add(USERNAME+"\n"+inputText.getText().toString());
            listInput.setAdapter(adapter);
            inputText.setText("");

        }

    }

    //加入清單
    class SOPContent2 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Content.this);
            pDialog.setMessage("Adding to List. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {


            //for get
            ArrayList params2 = new ArrayList();

            params2.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            params2.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER));
            params2.add(new BasicNameValuePair("Stepnumber", STEPNUMBER) );

            JSONObject json2 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product5,"POST",params2);

            try {
                //加入清單
                int e3 = json2.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    new LoadAllProducts().execute();
                    Intent it = new Intent(Content.this,Mysop.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
                    it.putExtras(bundle);
                    startActivity(it);
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            pDialog.dismiss();
            return null;
        }

    }

    //加入按讚
    class ContentLike extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Content.this);
            pDialog.setMessage("Like... Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String Liketime=str;
            ArrayList params7 = new ArrayList();

            params7.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            params7.add(new BasicNameValuePair("Sopnumber", TAG_SOP_NUMBER));
            params7.add(new BasicNameValuePair("Liketime", Liketime));
            System.out.println("account " + TAG_ACCOUNT + "sopnumber " + TAG_SOP_NUMBER + "Like " + str);

            JSONObject json7 = Content.this.jsonParser.makeHttpRequest(Content.url_create_product7,"POST",params7);

            try {
                //按攥
                int e3 = json7.getInt(TAG_SUCCESS);
                if(e3 == 1) {
                    Log.d("like","sucess");
                }else {
                    Log.d("like","nonsucess");
                    System.out.println("sucess=" + e3);
                }

            } catch (JSONException var9) {
                var9.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            likepitcure.setImageResource(R.drawable.like);

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
            pDialog = new ProgressDialog(Content.this);
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
            JSONObject json = Content.this.jsonParser.makeHttpRequest(Content.url_all_products,"GET", params);
            JSONObject json1 =Content.this.jsonParser.makeHttpRequest(Content.url_all_products1,"GET", params);
            JSONObject json2 =Content.this.jsonParser.makeHttpRequest(Content.url_all_products2,"GET", params);
            JSONObject json3 =Content.this.jsonParser.makeHttpRequest(Content.url_all_products3,"GET", params);
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
                    products4 = json.getJSONArray(TAG_PRODUCTS1);

                    Log.d("test",String.valueOf(products4.length()));

                    // looping through All Products
                    for (int i = 0; i < products4.length(); i++) {
                        //for (int i = 0; i < 2; i++) {

                        JSONObject c = products4.getJSONObject(i);

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
                        productsList4.add(map);

                    }
                    Log.d("test11",String.valueOf(productsList4.size()));
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
                    products1 = json1.getJSONArray(TAG_PRODUCTS1);
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
                    products2 = json2.getJSONArray(TAG_PRODUCTS1);
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
                    products3 = json3.getJSONArray(TAG_PRODUCTS1);

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
                    Log.d("productlist3", "success");
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

            DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(Content.this);
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
            Log.d("length",String.valueOf(products4.length()));
            Log.d("size",String.valueOf(productsList4.size()));

            // dismiss the dialog after getting all products
            for (int i = 0; i <  productsList4.size(); i++){


                msop_detailVo2.setSop_number(productsList4.get(i).get("sop_number"));
                msop_detailVo2.setStep_order(productsList4.get(i).get("step_order"));
                msop_detailVo2.setStep_number(productsList4.get(i).get("step_number"));
                Log.d("countproducts"+String.valueOf(i),productsList4.get(i).get("step_number"));
                msop_detailVo2.setStep_name(productsList4.get(i).get("step_name"));
                msop_detailVo2.setStep_purpose(productsList4.get(i).get("step_purpose"));
                msop_detailVo2.setStep_intro(productsList4.get(i).get("step_intro"));
                Log.d("countproducts"+String.valueOf(i),productsList4.get(i).get("step_intro"));
                msop_detailVo2.setStart_rule(productsList4.get(i).get("start_rule"));
                msop_detailVo2.setStart_value1(productsList4.get(i).get("start_value1"));
                msop_detailVo2.setStart_value2(productsList4.get(i).get("start_value2"));
                msop_detailVo2.setFinish_rule(productsList4.get(i).get("finish_rule"));
                msop_detailVo2.setFinish_value1(productsList4.get(i).get("finish_value1"));
                msop_detailVo2.setFinish_value2(productsList4.get(i).get("finish_value2"));
                msop_detailVo2.setNext_step_number(productsList4.get(i).get("next_step_number"));
                msop_detailVo2.setNext_step_rule(productsList4.get(i).get("next_step_rule"));
                msop_detailVo2.setStep_remind(productsList4.get(i).get("step_remind"));
                msop_detailVo2.setStart_remind(productsList4.get(i).get("start_remind"));
                msop_detailVo2.setStart_message(productsList4.get(i).get("start_message"));


                msop_detailDao2.insert(mDatabaseHelper2, msop_detailVo2);
            }


            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(Content.this);
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

            DatabaseHelper mDatabaseHelper5 = DatabaseHelper.getHelper(Content.this);
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
            DatabaseHelper mDatabaseHelper6 = DatabaseHelper.getHelper(Content.this);
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
            if(DetectDownload==0) {
                Bundle bundle = new Bundle();
                bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
                startActivity(new Intent().setClass(Content.this, Mysop.class).putExtras(bundle));
            }
        }


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
            down[i]=new DownloadManager.Request (Uri.parse("http://140.115.80.237/front/download/start" + sop_detailDL.get(i).getStep_number() + ".mp3"));
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
            DetectDownload++;
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
            DetectDownload++;
        }

        DatabaseHelper DatabaseHelperDL2 = DatabaseHelper.getHelper(this);
        sop_masterDao sopmasterDaoDL2 = new sop_masterDao();
        List<sop_masterVo> sop_masterDL2;
        sop_masterDL2 = sopmasterDaoDL2.selectAll(DatabaseHelperDL2);
        DownloadManager.Request[] down2 = new DownloadManager.Request[50];

        for(int i=0;i<sop_masterDL2.size();i++) {

            String[] graph = sop_masterDL2.get(i).getSop_graph_src().split("/");

            File file = new File(URI.create("file:///mnt/sdcard/MYSOPTEST/" + graph[graph.length-1] ).getPath());
            if (file.exists()) {
                break;
            }
            down2[i] = new DownloadManager.Request(Uri.parse("http://140.115.80.237/front/picture/" + graph[graph.length-1]));
            //允許網路類型
            down2[i].setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //禁止發通知
            down2[i].setShowRunningNotification(false);
            //不顯示下載頁面
            down2[i].setVisibleInDownloadsUi(false);
            //下載後存放位置
            //down.setDestinationInExternalFilesDir(mContext, null, "testhtml.html");
            //在sdcard裡面的MYSOPTEST資料夾
            down2[i].setDestinationInExternalPublicDir("MYSOPTEST", graph[graph.length-1]);
            //將請求加入
            manager.enqueue(down2[i]);
            DetectDownload++;
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
                //Toast.makeText(context, intent.getAction()+"id : "+downId, Toast.LENGTH_SHORT).show();
                DetectDownloadReceive++;
                if(DetectDownload==DetectDownloadReceive){
                    Bundle bundle = new Bundle();
                    bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
                    startActivity(new Intent().setClass(Content.this, Mysop.class).putExtras(bundle));
                }
            }
        }
    }

}
