package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_recordDao;
import Ormlite.case_recordVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;


public class StepCutControlFinishandpass extends Activity {

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_cut_control_finishandpass);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");

        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        case_recordDao mcase_recordDao = new case_recordDao();
        List<case_recordVo> list = null;
        list = mcase_recordDao.selectRaw(mDatabaseHelper, "Case_number ="+TAG_CASE_NUMBER+" AND Step_order ="+TAG_STEP_ORDER);
        //list = mcase_recordDao.selectRaw(mDatabaseHelper, "Case_number ="+TAG_CASE_NUMBER);

        Log.d("cutcontrolfinish",String.valueOf(list.size()));


        final Bundle bundle1 = new Bundle();
        bundle1.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
        bundle1.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
        bundle1.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
        AlertDialog.Builder dialog = new AlertDialog.Builder(StepCutControlFinishandpass.this);
        dialog.setTitle("");
        dialog.setMessage("此步驟輸入的資料不符合完工之條件");

        for(int i=0; i< list.size();i++) {

            Log.d("Finishandpass", "forloop");
            DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
            step_recordDao mstep_recordDao1 = new step_recordDao();
            List<step_recordVo> list1 = null;
            list1 = mstep_recordDao1.selectRaw(mDatabaseHelper1, "Step_number ="+TAG_STEP_NUMBER+" AND Record_order ="+list.get(i).getRecord_order());

            Log.d("cutcontrolfinish2",String.valueOf(list1.size()));

            //Record_type: 1數字 2
            if (list1.get(0).getRecord_type().equals("1")) {

                if (Integer.valueOf(list1.get(0).getRecord_min()) < Integer.valueOf(list.get(i).getRecord_value()) && Integer.valueOf(list.get(i).getRecord_value()) < Integer.valueOf(list1.get(0).getRecord_max())) {

                    dialog.setMessage("此步驟輸入的資料符合完工之條件");

                }
            }
            //非數字要怎麼通過@@?
        }
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent it = new Intent(StepCutControlFinishandpass.this, StepNextControl.class);
                it.putExtras(bundle1);
                startActivity(it);
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_cut_control_finishandpass, menu);
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
}
