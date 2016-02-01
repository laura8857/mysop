package com.ncu.efpg.mysop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.case_recordDao;
import Ormlite.case_recordVo;
import Ormlite.member_accountDao;
import Ormlite.member_accountVo;
import Ormlite.step_recordDao;
import Ormlite.step_recordVo;


public class StepCutControlFinishandpass extends Activity {

    String TAG_CASE_NUMBER = "";
    String TAG_STEP_NUMBER = "";
    int TAG_STEP_ORDER = 0;
    boolean Pass =true;
    String TAG_ACCOUNT;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("檢核結果");
        setContentView(R.layout.activity_step_cut_control_finishandpass);
        setFinishOnTouchOutside(false);



        TextView ss = (TextView)findViewById(R.id.finishandpass_textView1);
        ss.setText("此步驟輸入的資料符合完工之條件");

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

        detector = new GestureDetector(new MySimpleOnGestureListener());
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.finishandpass_relativelayout);
        //rl.setOnTouchListener(new MyOnTouchListener());



/*        AlertDialog.Builder dialog = new AlertDialog.Builder(StepCutControlFinishandpass.this);
        dialog.setTitle("");
        dialog.setMessage("此步驟輸入的資料不符合完工之條件");*/

        for(int i=0; i< list.size();i++) {

            Log.d("Finishandpass", "forloop");
            DatabaseHelper mDatabaseHelper1 = DatabaseHelper.getHelper(this);
            step_recordDao mstep_recordDao1 = new step_recordDao();
            List<step_recordVo> list1 = null;
            list1 = mstep_recordDao1.selectRaw(mDatabaseHelper1, "Step_number ="+TAG_STEP_NUMBER+" AND Record_order ="+list.get(i).getRecord_order());

            Log.d("cutcontrolfinish2",String.valueOf(list1.size()));
            Log.d("抓4-1",list.get(i).getRecord_value());

            //Record_type: 1數字 2
            if (list1.get(0).getRecord_type().equals("1")) {

                if (Integer.valueOf(list1.get(0).getRecord_min()) <= Integer.valueOf(list.get(i).getRecord_value()) && Integer.valueOf(list.get(i).getRecord_value()) <= Integer.valueOf(list1.get(0).getRecord_max())) {

                    //ss.setText("此步驟輸入的資料符合完工之條件");
                    //Pass = true;
                    //dialog.setMessage("此步驟輸入的資料符合完工之條件");

                }else{
                    ss.setText("此步驟輸入的資料不符合完工之條件");
                    Pass = false;
                }
            }
            //非數字要怎麼通過@@?
        }
        //dialog.show();

/*        dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent it = new Intent(StepCutControlFinishandpass.this, StepNextControl.class);
                it.putExtras(bundle1);
                startActivity(it);
                finish();
            }
        });*/


        //View v = findViewById(android.R.id.content);
       // v.setOnTouchListener(new MyOnTouchListener());

    }

    class MyOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return detector.onTouchEvent(event);
        }
    }
    class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // TODO Auto-generated method stub
            if ((e1.getX() - e2.getX()) > 50) {//左滑

                Bundle bundle1 = new Bundle();
                bundle1.putString("TAG_CASE_NUMBER", TAG_CASE_NUMBER);
                bundle1.putString("TAG_STEP_NUMBER", TAG_STEP_NUMBER);
                bundle1.putInt("TAG_STEP_ORDER", TAG_STEP_ORDER);

                if(Pass){
                    Intent it = new Intent(StepCutControlFinishandpass.this, StepNextControl.class);
                    it.putExtras(bundle1);
                    startActivity(it);
                    //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_right);
                    finish();
                }else{
                    //bundle1.putBoolean("TAG_BACK_TO_RECORDING",true);
                    Intent it1 = new Intent(StepCutControlFinishandpass.this, Steprecording.class);
                    it1.putExtras(bundle1);
                    startActivity(it1);
                    //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_right);
                    finish();
                }



                return true;
            }

            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
/*        if(dialogBounds.contains((int)ev.getX(),(int)ev.getY())){
            return detector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);*/
        return detector.onTouchEvent(ev);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //orm account
            DatabaseHelper mDatabaseHelper4 = DatabaseHelper.getHelper(StepCutControlFinishandpass.this);
            member_accountDao mmember_accountDao = new  member_accountDao();
            List<member_accountVo> memberlist = null;
            memberlist = mmember_accountDao.selectColumns(mDatabaseHelper4, "FIELD_Account");
            TAG_ACCOUNT = memberlist.get(0).getAccount();

            Bundle bundle = new Bundle();
            bundle.putString("TAG_ACCOUNT",TAG_ACCOUNT);
            Intent it = new Intent(this,Mysop.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            it.putExtras(bundle);//將參數放入intent
            startActivity(it);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
