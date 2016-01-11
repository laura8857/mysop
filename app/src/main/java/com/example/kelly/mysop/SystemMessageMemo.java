package com.example.kelly.mysop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Ormlite.DatabaseHelper;
import Ormlite.memoDao;
import Ormlite.memoVo;

/**
 * Created by laura on 15/12/28.
 */
public class SystemMessageMemo extends Activity {
    private ListView listView;
    MyAdapter adapter = null;
    private String[] testlist = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
    private String[] messagelist;
    private int[] checklist;
    private int messagesize;

    //orm
    private RuntimeExceptionDao<memoVo, Integer> memoVoRuntimeDao;
    private memoDao mmemoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message_memo);
        listView = (ListView)findViewById(R.id.memolist);

//        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(this);


        //orm
        List<memoVo> systemmessagelist = null ;
        systemmessagelist = mmemoDao.selectRaw2(mDatabaseHelper);
        messagesize=systemmessagelist.size();
        if(messagesize>0) {
            messagelist = new String[systemmessagelist.size()];
            checklist = new int[systemmessagelist.size()];

            for (int i = 0; i < systemmessagelist.size(); i++) {
                messagelist[i] = systemmessagelist.get(i).getSystem_message();
                checklist[i] = R.drawable.uncheck;
            }
            adapter = new MyAdapter(SystemMessageMemo.this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(listener);
        }



    }

    private ListView.OnItemClickListener listener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //CheckBox cbx = (CheckBox)view.findViewById(R.id.check);
            if(checklist[position]==R.drawable.uncheck) {
                checklist[position] = R.drawable.check;
                //cbx.setChecked(checklist[position]);
                Toast.makeText(getApplicationContext(), "你選擇的是" + messagelist[position], Toast.LENGTH_SHORT).show();
            }else {
                checklist[position]=R.drawable.uncheck;
               // cbx.setChecked(checklist[position]);
                Toast.makeText(getApplicationContext(), "你取消的是" + messagelist[position], Toast.LENGTH_SHORT).show();
            }
            listView.invalidateViews();

        }

    };

    //刪除訊息
    public void deleteMemo(View v){

        for(int i =0;i<messagesize;i++){
            if(checklist[i]==R.drawable.check){
                DatabaseHelper mDatabaseHelper2 = DatabaseHelper.getHelper(SystemMessageMemo.this);
                memoDao mmemoDao = new memoDao();
                mmemoDao.delete(mDatabaseHelper2,"System_message",messagelist[i]);

            }
        }

        //orm
        DatabaseHelper mDatabaseHelper = DatabaseHelper.getHelper(SystemMessageMemo.this);
        List<memoVo> systemmessagelist = null ;
        systemmessagelist = mmemoDao.selectRaw2(mDatabaseHelper);
        messagelist = new String[systemmessagelist.size()];
        checklist = new int[systemmessagelist.size()];
        messagesize=systemmessagelist.size();
        for(int i =0;i<systemmessagelist.size();i++){
            messagelist[i]=systemmessagelist.get(i).getSystem_message();
            checklist[i]=R.drawable.uncheck;
        }
        listView.invalidateViews();
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
            //CheckBox check = (CheckBox)convertView.findViewById(R.id.check);
            ImageView checkbutton = (ImageView)convertView.findViewById(R.id.checkbutton);

            Name.setText(messagelist[position]);
            checkbutton.setImageResource(checklist[position]);
           // check.setChecked(checklist[position]);

            return convertView;
        }

    }
}
