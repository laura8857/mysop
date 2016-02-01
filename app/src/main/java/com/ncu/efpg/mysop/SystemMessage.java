package com.ncu.efpg.mysop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class SystemMessage  extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        addNewTab(SystemMessageGet.class, "Message");
        addNewTab(SystemMessageMemo.class, "Memo");

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(intent.hasExtra("PAGE")){
            getTabHost().setCurrentTab(1);
        }else{
            getTabHost().setCurrentTab(0);
        }
        getTabHost().requestFocus();


    }
    public void addNewTab(Class<?> cls, String tabName){
        Intent intent = new Intent().setClass(this, cls);
        TabHost.TabSpec spec = getTabHost().newTabSpec(tabName)
                .setIndicator(tabName)
                .setContent(intent);
        getTabHost().addTab(spec);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_system_message, menu);
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
