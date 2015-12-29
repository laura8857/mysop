package com.example.kelly.mysop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.system_messageDao;
import Ormlite.system_messageVo;

/**
 * Created by laura on 15/12/28.
 */
public class SystemMessageMemo extends Activity {
    private ListView listView;
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
        setContentView(R.layout.activity_system_message_memo);
        listView = (ListView)findViewById(R.id.memolist);

//        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);


        //orm
        List<system_messageVo> systemmessagelist = null ;
        systemmessagelist = msystem_messageDao.selectRaw(mDatabaseHelper, "Message_mark=" + "'" + "Mark" + "'");
        messagesize=systemmessagelist.size();
        if(messagesize>0) {
            messagelist = new String[systemmessagelist.size()];
            checklist = new Boolean[systemmessagelist.size()];

            for (int i = 0; i < systemmessagelist.size(); i++) {
                messagelist[i] = systemmessagelist.get(i).getSystem_message();
                checklist[i] = false;
            }
            adapter = new MyAdapter(SystemMessageMemo.this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);
        }



    }

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
    public void deleteMemo(View v){

        for(int i =0;i<messagesize;i++){
            if(checklist[i]==true){
                DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SystemMessageMemo.this);
                system_messageDao msystem_messageDao = new system_messageDao();
                msystem_messageDao.update(mDatabaseHelper2, "System_message", messagelist[i], "Message_mark", "noMark");

            }
        }

        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SystemMessageMemo.this);
        List<system_messageVo> systemmessagelist = null ;
        systemmessagelist = msystem_messageDao.selectRaw(mDatabaseHelper, "Message_mark="+"'"+"Mark"+"'");
        messagelist = new String[systemmessagelist.size()];
        checklist = new Boolean[systemmessagelist.size()];
        messagesize=systemmessagelist.size();
        for(int i =0;i<systemmessagelist.size();i++){
            messagelist[i]=systemmessagelist.get(i).getSystem_message();
            checklist[i]=false;
        }
        listView.invalidateViews();
    }

    //重新整理
    public void refreshMemo(View v){
        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SystemMessageMemo.this);
        List<system_messageVo> systemmessagelist = null ;
        systemmessagelist = msystem_messageDao.selectRaw(mDatabaseHelper, "Message_mark="+"'"+"Mark"+"'");
        messagelist = new String[systemmessagelist.size()];
        checklist = new Boolean[systemmessagelist.size()];
        messagesize=systemmessagelist.size();
        for(int i =0;i<systemmessagelist.size();i++){
            messagelist[i]=systemmessagelist.get(i).getSystem_message();
            checklist[i]=false;
        }
        adapter = new MyAdapter(SystemMessageMemo.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
    }


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
