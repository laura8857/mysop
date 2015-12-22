package com.example.kelly.mysop;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_recordDao;
import Ormlite.case_recordVo;
import Ormlite.sop_detailDao;
import Ormlite.sop_detailVo;


public class StepNextControlData extends ActionBarActivity {

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;

    private DatabaseHelper mDatabaseHelper;
    private sop_detailDao msop_detailDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_next_control_data);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();	//取得Bundle
        TAG_CASE_NUMBER = bundle.getString("TAG_CASE_NUMBER");
        TAG_STEP_NUMBER = bundle.getString("TAG_STEP_NUMBER");
        TAG_STEP_ORDER = bundle.getInt("TAG_STEP_ORDER");

        mDatabaseHelper = DatabaseHelper.getHelper(this);
        msop_detailDao = new sop_detailDao();
        List<sop_detailVo> list = null;
        list = msop_detailDao.selectRaw(mDatabaseHelper, "Step_number ="+TAG_STEP_NUMBER);
        Log.d("Data1", list.get(0).getNext_step_number());
        String Next = list.get(0).getNext_step_number();
        String[] NextStep = Next.split("。");
        String[] NextStepOption = NextStep[0].split("，");
        String[] NextStepNumber = NextStep[1].split("，");
        UseNextStepOption(NextStepOption,NextStepNumber);

    }


    public void UseNextStepOption(String[] NextStepOption,String[] NextStepNumber) {

        Bundle bundle = new Bundle();
        bundle.putString("TAG_CASE_NUMBER",TAG_CASE_NUMBER);

        DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
        case_recordDao mcase_recordDao1 = new case_recordDao();
        List<case_recordVo> list1 = null;
        list1 = mcase_recordDao1.selectRaw(mDatabaseHelper1, "Case_number ="+TAG_CASE_NUMBER+" and Step_order ="+TAG_STEP_ORDER);

        //資料格式"xxx，xxx。1，2"，只要這一步驟中使用者輸入的資料其中一個符合，就可以跳頁
        for(int i=0;i<NextStepOption.length;i++){
            for(int j=0;j<list1.size();j++){
                if(list1.get(j).getRecord_value().equals(NextStepOption[i])){
                    Intent it = new Intent(StepNextControlData.this, StepActionControl.class);
                    bundle.putString("TAG_NEXT_STEP_NUMBER", NextStepNumber[j]);
                    it.putExtras(bundle);
                    startActivity(it);
                    finish();
                    break;
                }
            }
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(StepNextControlData.this);
        dialog.setTitle("");
        dialog.setMessage("此步驟輸入的資料不符合資料選擇下一步驟之條件");
        dialog.show();

        Intent it1 = new Intent(StepNextControlData.this, Steprecording.class);
        bundle.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
        bundle.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);
        it1.putExtras(bundle);
        startActivity(it1);
        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_step_next_control_data, menu);
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
