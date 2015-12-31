package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Emailverify extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url = "http://140.115.80.237/front/mysop_captcha.jsp";
    private static String url1 = "http://140.115.80.237/mysop/mysop_register1.jsp";
    private static final String TAG_SUCCESS = "success";
    private EditText InputEmailVerify;
    String TAG_ACCOUNT = "";
    String TAG_Key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailverify);
        InputEmailVerify = (EditText) findViewById(R.id.editText3);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_ACCOUNT = bundle.getString("TAG_ACCOUNT");	//輸出Bundle內容


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emailverify, menu);
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

    public void emailverify_check(View view) {


        new CreateAccount().execute();
    }
    public void emailverify_resend(View view) {

        new resend().execute();
    }

    class CreateAccount extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            Emailverify.this.pDialog = new ProgressDialog(Emailverify.this);
            Emailverify.this.pDialog.setMessage("verifying...");
            Emailverify.this.pDialog.setIndeterminate(false);
            Emailverify.this.pDialog.setCancelable(true);
            Emailverify.this.pDialog.show();
        }

        protected Integer doInBackground(Integer... args) {
            String InputEmailVerify =  Emailverify.this.InputEmailVerify.getText().toString();

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Emailverify", InputEmailVerify));
            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT));

            JSONObject json = Emailverify.this.jsonParser.makeHttpRequest(Emailverify.url, "POST", params);
            Log.d("Create Response", json.toString());

            try {
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    return 1;
                }else if(e == 6){
                    return 6;
                }else{
                    return 2;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Integer ans) {
            Emailverify.this.pDialog.dismiss();
            if(ans==1){
                Toast.makeText(Emailverify.this,"註冊成功!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Emailverify.this.getApplicationContext(), Home.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAG_Key", TAG_Key);
                i.putExtras(bundle);
                Emailverify.this.startActivity(i);
                Emailverify.this.finish();
      /*          AlertDialog.Builder dialog = new AlertDialog.Builder(Emailverify.this);
                dialog.setTitle("");
                dialog.setMessage("註冊成功！");
                dialog.show();*/
            }else if(ans==6){
                Intent i = new Intent(Emailverify.this.getApplicationContext(), EmailVertifyError.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
                i.putExtras(bundle);
                Emailverify.this.startActivity(i);
                Emailverify.this.finish();
            }else {
                Toast.makeText(Emailverify.this,"系統錯誤",Toast.LENGTH_LONG).show();
            }
        }
    }

    //重寄
    class resend extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            Emailverify.this.pDialog = new ProgressDialog(Emailverify.this);
            Emailverify.this.pDialog.setMessage("Sending...");
            Emailverify.this.pDialog.setIndeterminate(false);
            Emailverify.this.pDialog.setCancelable(true);
            Emailverify.this.pDialog.show();
        }

        protected Integer doInBackground(Integer... args1) {

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            JSONObject json2 = Emailverify.this.jsonParser.makeHttpRequest(Emailverify.url1, "POST", params);
            Log.d("Create Response", json2.toString());

            try {
                int e = json2.getInt(TAG_SUCCESS);
                if(e == 1) {
                    return 1;
                }else{
                    return 2;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Integer ans1) {
            Emailverify.this.pDialog.dismiss();
            if(ans1==1){
                Toast.makeText(Emailverify.this,"驗證信重寄成功!",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Emailverify.this,"驗證信重寄失敗!",Toast.LENGTH_LONG).show();
            }
        }
    }


}

