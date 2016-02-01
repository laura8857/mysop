package com.ncu.efpg.mysop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Forget extends Activity {

    String TAG_ACCOUNT = "";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static String url = "http://140.115.82.211/mysop/mysop_forgetpwd.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_ACCOUNT = bundle.getString("TAG_ACCOUNT");	//輸出Bundle內容
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget, menu);
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
    public void confirm (View v){
        Intent it = new Intent(this,Login.class);
        startActivity(it);
        finish();
    }

    public void resend_password (View v){
        new resendpassword().execute();
    }

    //重寄密碼
    class resendpassword extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Forget.this);
            pDialog.setMessage("Sending...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Integer doInBackground(Integer... args1) {

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            JSONObject json2 = Forget.this.jsonParser.makeHttpRequest(Forget.url, "POST", params);
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
            pDialog.dismiss();
            if(ans1==1){
                Toast.makeText(Forget.this, "重寄成功!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Forget.this,"重寄失敗!",Toast.LENGTH_LONG).show();
            }
        }
    }



}
