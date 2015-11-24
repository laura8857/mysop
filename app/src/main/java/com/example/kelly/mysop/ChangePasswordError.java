package com.example.kelly.mysop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;


public class ChangePasswordError extends Activity {

    private RuntimeExceptionDao<member_accountVo, Integer> menber_accountRuntimeDao;
    private member_accountDao mmember_accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_error);


        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        mmember_accountDao = new member_accountDao();
        //menber_accountRuntimeDao = mDatabaseHelper.getMember_accountDao();
        member_accountVo mmember_accountVo = new member_accountVo();
        List<member_accountVo> list = null;
        list = mmember_accountDao.selectRaw(mDatabaseHelper, "account='test'");

        Log.d("抓", list.get(0).getUsername());
        Log.d("抓1", String.valueOf(list.size()));
        Toast.makeText(this, list.get(0).getUsername(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password_error, menu);
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
    public void backTochangepassword (View v){
        Intent it = new Intent(this,Changepassword.class);
        startActivity(it);
    }

}
