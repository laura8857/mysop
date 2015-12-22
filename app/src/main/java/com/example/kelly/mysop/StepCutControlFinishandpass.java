package com.example.kelly.mysop;

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


public class StepCutControlFinishandpass extends ActionBarActivity {

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

        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        step_recordDao mstep_recordDao1 = new step_recordDao();
        List<step_recordVo> list1 = null;
        list1 = mstep_recordDao1.selectRaw(mDatabaseHelper1, "Step_number ="+TAG_STEP_NUMBER);

        if(list1.get(0).getRecord_type().equals("3")){

        }
        if(Integer.valueOf(list.get(0).getRecord_value()) < Integer.valueOf(list1.get(0).getRecord_max())){

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
