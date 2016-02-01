package com.ncu.efpg.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;


public class Changepassword extends Activity {


    //R檔又不見了
    //帳號先寫死
    private ProgressDialog pDialog;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    String strHint1;
    String strHint2;
    String strHint3;

    JSONParser jsonParser = new JSONParser();
    private static String url_changepassword = "http://140.115.82.211/front/mysop_changePassword.jsp";
    private static final String TAG_SUCCESS = "success";
    String TAG_ACCOUNT = "";
    String NewPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        TAG_ACCOUNT = bundle.getString("TAG_ACCOUNT");

        et1 = (EditText) findViewById(R.id.Originalpassword);
        et2 = (EditText) findViewById(R.id.NewPassword);
        et3 = (EditText) findViewById(R.id.Confirmnewpassward);

        strHint1 = getResources().getString(R.string.Changepassward_Originalpassword);
        strHint2 = getResources().getString(R.string.Changepassward_NewPassword);
        strHint3 = getResources().getString(R.string.Changepassward_Confirmnewpassward);

        final EditText[] mArray = new EditText[] { et1,et2,et3 };

        for (int i = 0; i < mArray.length; i++) {
            final int j = i;

            //EditText 获得焦点时hint消失，失去焦点时hint显示
            mArray[j].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        if(j == 0){
                            ((TextView) v).setHint(strHint1);
                        }
                        if(j == 1){
                            ((TextView) v).setHint(strHint2);
                        }
                        if(j == 2){
                            ((TextView) v).setHint(strHint3);
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
        getMenuInflater().inflate(R.menu.menu_changepassword, menu);
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

    private RuntimeExceptionDao<member_accountVo, Integer> menber_accountRuntimeDao;
    private member_accountDao mmember_accountDao;

    public void changepassword_check(View view){
        NewPassword = Changepassword.this.et2.getText().toString();
        String ConfirmNewPassword = Changepassword.this.et3.getText().toString();

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        //null代表沒網路
        if(info!=null) {

            if (ConfirmNewPassword.equals(NewPassword)) {

                new CreateAccount().execute();


/*
            DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
            mmember_accountDao = new member_accountDao();
            //menber_accountRuntimeDao = mDatabaseHelper.getMember_accountDao();
            member_accountVo mmember_accountVo = new member_accountVo();

            mmember_accountVo.setAccount("test");
            mmember_accountVo.setUsername("12345");
            mmember_accountVo.setPassword(NewPassword);

            //menber_accountRuntimeDao.createOrUpdate(mmember_accountVo);
            mmember_accountDao.insert(mDatabaseHelper,mmember_accountVo);

            //UPDATE
            DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(this);
            member_accountDao mmember_accountDao2 = new member_accountDao();
            member_accountVo mmember_accountVo2 = new member_accountVo();

            mmember_accountDao2.update(mDatabaseHelper2,"account","test","username","678");

            //測試一次抓兩筆
            DatabaseHelper mDatabaseHelper3 = DatabaseHelper.getHelper(this);
            member_accountDao mmember_accountDao3 = new member_accountDao();
            member_accountVo mmember_accountVo3 = new member_accountVo();
            mmember_accountVo3.setAccount("test1");
            mmember_accountVo3.setUsername("678");
            mmember_accountVo3.setPassword("testing");
            mmember_accountDao3.insert(mDatabaseHelper3,mmember_accountVo3);


            //測account可不可以一樣
            DatabaseHelper mDatabaseHelper5 = DatabaseHelper.getHelper(this);
            member_accountDao mmember_accountDao5 = new member_accountDao();
            member_accountVo mmember_accountVo5 = new member_accountVo();
            mmember_accountVo5.setUsername("678");
            mmember_accountVo5.setAccount("test2");
            mmember_accountVo5.setPassword("testing2");
            mmember_accountDao5.insert(mDatabaseHelper5,mmember_accountVo5);

*/
            /*mmember_accountVo = mmember_accountDao.queryForId(1);
            mmember_accountVo.setPassword(NewPassword);
            mmember_accountDao.update(mmember_accountVo);*/
                //Toast.makeText(this, mmember_accountVo.getPassword(), Toast.LENGTH_SHORT).show();
                //Log.d("TEST", "TEST");

/*
            //測試巢狀是否可用
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(this);
            case_masterDao mcase_masterDao4 = new case_masterDao();
            case_masterVo mcase_masterVo4 = new case_masterVo();
            mcase_masterVo4.setAccount("test1");
            mcase_masterVo4.setCase_number("111");
            mcase_masterVo4.setRule_number("222");
            mcase_masterDao4.insert(mDatabaseHelper4,mcase_masterVo4);




            Intent it = new Intent(Changepassword.this,ChangePasswordError.class);
            startActivity(it);
*/

            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Changepassword.this);
                dialog.setTitle("咦！");
                dialog.setMessage("請確認新密碼一致");
                dialog.show();
            }
        }else{
            Toast.makeText(this,"請開啟網路",Toast.LENGTH_LONG).show();
        }
    }




    class CreateAccount extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Changepassword.this);
            pDialog.setMessage("Changing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Integer doInBackground(Integer... args) {
            String Originalpassword = Changepassword.this.et1.getText().toString();
            //String NewPassword = Changepassword.this.et2.getText().toString();

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("Password", Originalpassword));
            params.add(new BasicNameValuePair("NewPassword", NewPassword));
            params.add(new BasicNameValuePair("Account", TAG_ACCOUNT));

            JSONObject json = Changepassword.this.jsonParser.makeHttpRequest(Changepassword.url_changepassword, "POST", params);

            try {
                int e = json.getInt(TAG_SUCCESS);
                if(e == 1) {

                    return 1;

                }else if(e == 6){

                    return 6;
                }
            } catch (JSONException var9) {
                var9.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Integer ans) {
            pDialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putString("TAG_ACCOUNT", TAG_ACCOUNT);
            if(ans==1){
                Toast.makeText(Changepassword.this,"變更密碼成功!",Toast.LENGTH_LONG).show();
                DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(Changepassword.this);
                member_accountDao mmember_accountDao = new member_accountDao();
                mmember_accountDao.update(mDatabaseHelper,"account",TAG_ACCOUNT,"password",NewPassword);

                Intent it = new Intent(Changepassword.this,Mysop.class).putExtras(bundle);
                startActivity(it);
                finish();
            }else if(ans==6){
                Intent it = new Intent(Changepassword.this,ChangePasswordError.class).putExtras(bundle);
                startActivity(it);
                finish();
            }

        }
    }



}
