package com.example.kelly.mysop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.memoDao;
import Ormlite.memoVo;
import Ormlite.system_messageDao;
import Ormlite.system_messageVo;

/**
 * Created by laura on 15/12/28.
 */
public class SystemMessageGet extends  Activity {
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    MyAdapter adapter = null;
    private String[] testlist = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
    private String[] messagelist;
    private Boolean[] checklist;
    private int messagesize;


    //orm
    private RuntimeExceptionDao<system_messageVo, Integer> system_messageVoRuntimeDao;
    private system_messageDao msystem_messageDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message_get);
        listView = (ListView)findViewById(R.id.getlist);


        //orm insert測試
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);
        for(int i=0;i<10;i++) {
            system_messageVo msystem_messageVo = new system_messageVo();
            msystem_messageVo.setSystem_message("測試" + i);
            //msystem_messageVo.setMessage_mark("noMark");
            msystem_messageDao.insert(mDatabaseHelper, msystem_messageVo);
        }

        //orm
        List<system_messageVo> systemmessagelist = null ;
        systemmessagelist = msystem_messageDao.selectRaw2(mDatabaseHelper);
        messagelist = new String[systemmessagelist.size()];
        checklist = new Boolean[systemmessagelist.size()];
        messagesize=systemmessagelist.size();
        for(int i =0;i<systemmessagelist.size();i++){
        messagelist[i]=systemmessagelist.get(i).getSystem_message();
            checklist[i]=false;
        }


        adapter  = new MyAdapter(SystemMessageGet.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mysop, menu);
        return true;
    }

    //listview listener
    private ListView.OnItemClickListener listener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            CheckBox cbx = (CheckBox)view.findViewById(R.id.check);
            if(!cbx.isChecked()) {
                checklist[position] = true;
                cbx.setChecked(checklist[position]);
                Toast.makeText(getApplicationContext(), "你選擇的是" + messagelist[position], Toast.LENGTH_SHORT).show();
            }else {
                checklist[position]=false;
                cbx.setChecked(checklist[position]);
            }

        }

    };

    //刪除訊息
    public void deletemessage (View v){

        for(int i =0;i<messagesize;i++){
            if(checklist[i]==true){
                DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SystemMessageGet.this);
                system_messageDao msystem_messageDao = new system_messageDao();
                msystem_messageDao.delete(mDatabaseHelper2,"System_message",messagelist[i]);

            }
        }

        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SystemMessageGet.this);
        List<system_messageVo> systemmessagelist = null ;
        systemmessagelist = msystem_messageDao.selectRaw2(mDatabaseHelper);
        messagelist = new String[systemmessagelist.size()];
        checklist = new Boolean[systemmessagelist.size()];
        messagesize=systemmessagelist.size();
        for(int i =0;i<systemmessagelist.size();i++){
            messagelist[i]=systemmessagelist.get(i).getSystem_message();
            checklist[i]=false;
        }
        listView.invalidateViews();
    }

    //加入到備忘錄
    public void insertMemo(View v){
        for(int i =0;i<messagesize;i++){
            if(checklist[i]==true){
                DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SystemMessageGet.this);
                system_messageDao msystem_messageDao = new system_messageDao();
                List<system_messageVo> systemmessagelist2 = null ;
                systemmessagelist2= msystem_messageDao.selectRaw(mDatabaseHelper2,"System_message="+"'"+messagelist[i]+"'");

                //加入memo表格
                memoVo mmemoVo = new memoVo();
                memoDao mmemoDao = new memoDao();
                mmemoVo.setSystem_message(systemmessagelist2.get(0).getSystem_message());
                mmemoVo.setId(systemmessagelist2.get(0).getId());
                mmemoDao.insert(mDatabaseHelper2,mmemoVo);


            }
        }

    }



    //自定listview
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;


        public MyAdapter(Context c) {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return messagelist.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return messagelist[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.checklistview, null);


            TextView Name = (TextView) convertView.findViewById(R.id.name);
            CheckBox check = (CheckBox)convertView.findViewById(R.id.check);

            Name.setText(messagelist[position]);
            check.setChecked(checklist[position]);

            return convertView;
        }

    }

    }








