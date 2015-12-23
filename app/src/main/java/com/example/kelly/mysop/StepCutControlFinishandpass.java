package com.example.kelly.mysop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        case_recordDao mcase_recordDao = new case_recordDao();
        List<case_recordVo> list = null;
        list = mcase_recordDao.selectRaw(mDatabaseHelper, "Case_number ="+TAG_CASE_NUMBER+" and Step_order ="+TAG_STEP_ORDER);

        for(int i=0; i< list.size();i++) {

            DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
            step_recordDao mstep_recordDao1 = new step_recordDao();
            List<step_recordVo> list1 = null;
            list1 = mstep_recordDao1.selectRaw(mDatabaseHelper1, "Step_number ="+TAG_STEP_NUMBER+" and Record_order ="+list.get(i).getRecord_order());
            //Record_type: 1數字 2
            if (list1.get(i).getRecord_type().equals("1")) {

                Bundle bundle = new Bundle();
                bundle.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
                bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);

                if (Integer.valueOf(list1.get(i).getRecord_min()) < Integer.valueOf(list.get(i).getRecord_value()) && Integer.valueOf(list.get(i).getRecord_value()) < Integer.valueOf(list1.get(i).getRecord_max())) {

                    Intent it = new Intent(StepCutControlFinishandpass.this, StepNextControl.class);
                    it.putExtras(bundle);
                    startActivity(it);
                    finish();
                } else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(StepCutControlFinishandpass.this);
                    dialog.setTitle("");
                    dialog.setMessage("此步驟輸入的資料不符合完工之條件");
                    dialog.show();

                    Intent it1 = new Intent(StepCutControlFinishandpass.this, Steprecording.class);
                    it1.putExtras(bundle);
                    startActivity(it1);
                    finish();
                }
            }

            //非數字要怎麼通過@@?
        }

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
