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
import Ormlite.case_masterDao;
import Ormlite.case_masterVo;
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
        list = mmember_accountDao.selectRaw(mDatabaseHelper, "username='678'");

        Log.d("抓", list.get(0).getAccount());
        Log.d("抓1", list.get(1).getAccount());
        Log.d("抓2", list.get(0).getUsername());
        Log.d("抓3", list.get(1).getUsername());
        Log.d("有幾筆", String.valueOf(list.size()));
        Toast.makeText(this, list.get(1).getUsername(), Toast.LENGTH_SHORT).show();

        //測巢狀
        DatabaseHelper mDatabaseHelper3 = DatabaseHelper.getHelper(this);
        case_masterDao mcase_masterDao3 = new case_masterDao();
        List<case_masterVo> list3 = null;
        list3 = mcase_masterDao3.selectRaw(mDatabaseHelper3, "Case_number=111");
        Log.d("抓5", list3.get(0).getAccount());


        DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao2 = new member_accountDao();
        List<member_accountVo> list2 = null;
        list2 = mmember_accountDao2.selectRawByNest(mDatabaseHelper2, "Case_number","111","account");

        Log.d("抓44", list2.get(0).getUsername());

/*原本的巢狀不能用
        DatabaseHelper mDatabaseHelper6 = DatabaseHelper.getHelper(this);
        member_accountDao mmember_accountDao6 = new member_accountDao();
        List<member_accountVo> list6 = null;
        list6 = mmember_accountDao6.selectRaw(mDatabaseHelper6, "account IN(SELECT Account FROM case_masterVo WHERE Case_number=111");
        Log.d("抓6", list6.get(0).getAccount());
*/

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
