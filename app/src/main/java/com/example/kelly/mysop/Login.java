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
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;

public class Login extends Activity {

    private ProgressDialog pDialog;

    private static String url_login = "http://140.115.82.211/front/mysop_login.jsp";
    private EditText et1;
    private EditText et2;
    String strHint1;
    String strHint2;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    static String TAG_ACCOUNT = "";
    String Password ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);

        strHint1 = getResources().getString(R.string.Login_email);
        strHint2 = getResources().getString(R.string.Login_passward);

        final EditText[] mArray = new EditText[] { et1,et2 };

        for (int i = 0; i < mArray.length; i++) {
            final int j = i;

            //EditText 獲得/失去焦點hint消失/出現
            mArray[j].setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if(j == 0){
                            ((TextView) v).setHint(strHint1);
                        }
                        if(j == 1){
                            ((TextView) v).setHint(strHint2);
                        }

                    } else {
                        ((TextView) v).setHint("");
                    }

                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void forgetpassword (View v){
        Intent it = new Intent(this,Forgetpwd.class);
        startActivity(it);
    }

    public void login_check(View view) {
        new CreateAccount().execute();
    }
    class CreateAccount extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Integer doInBackground(Integer... args) {
            TAG_ACCOUNT = Login.this.et1.getText().toString();
            Password = Login.this.et2.getText().toString();

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT));
            params.add(new BasicNameValuePair("Password", Password));

            JSONObject json = Login.this.jsonParser.makeHttpRequest(Login.url_login, "POST", params);
            Log.d("Create Response", json.toString());

            try {
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {
                    return 1;
                }else if(e == 6){
                    return 6;
                }else if(e == 7){
                    return 7;
                }else{
                    return 2;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Integer ans) {
            pDialog.dismiss();
            if(ans==1){
                DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(Login.this);
                member_accountDao mmember_accountDao = new member_accountDao();
                member_accountVo mmember_accountVo = new member_accountVo();
                mmember_accountVo.setAccount(TAG_ACCOUNT);
                mmember_accountVo.setPassword(Password);
                mmember_accountDao.insert(mDatabaseHelper,mmember_accountVo);

                Bundle bundle = new Bundle();
                bundle.putString("TAG_ACCOUNT",TAG_ACCOUNT);
                Intent it = new Intent(Login.this,SplashActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.putExtras(bundle);//將參數放入intent
                startActivity(it);
                finish();
            }else if(ans==6 || ans==7) {
                Intent i = new Intent(Login.this, Error.class);
                Login.this.startActivity(i);
                Login.this.finish();
            }else{
                Toast.makeText(Login.this, "系統錯誤", Toast.LENGTH_LONG).show();
            }

        }
    }

}
