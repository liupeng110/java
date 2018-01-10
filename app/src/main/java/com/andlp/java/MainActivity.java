package com.andlp.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.xutils.http.RequestParams;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    EditText uname,upass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          uname = findViewById(R.id.username);
          upass= findViewById(R.id.password);
              x.Ext.init(this.getApplication());


    }


    public void login(View view){
        x.task().run(new Runnable() {
            @Override public void run() {
                try {
                    RequestParams param = new RequestParams("http://m.sod.live/login/login_api");
                    param.addBodyParameter("username", uname.getText().toString());
                    param.addBodyParameter("password", upass.getText().toString());
                    String result = x.http().postSync(param, String.class);
                    Log.e("666","登陆结果:"+result);
                }catch (Throwable t){t.printStackTrace();}
            }
        });
    }



}
