package com.atfalna.test_reg_log_mysql;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {

        EditText name , email, pass;
        Button btn_reg;
        String sName , sEmail, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.ed_name);
        email=(EditText)findViewById(R.id.ed_email);
        pass=(EditText)findViewById(R.id.ed_password);

        btn_reg=(Button)findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sName = name.getText().toString();
                sEmail = email.getText().toString();
                sPassword = pass.getText().toString();
                Backgrund b = new Backgrund();
                b.execute(sName,sEmail,sPassword);
            }
        });
    }

    class Backgrund extends AsyncTask<String , String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String name=params[0];
            String email=params[1];
            String password=params[2];
            String data="";
            int tmp;

            try {
                URL url=new URL("http://localhost/register.php");
               String urlparams="name="+name+"&email="+email+"&password="+password;

                HttpURLConnection hurl=(HttpURLConnection)url.openConnection();
                hurl.setDoOutput(true);
                OutputStream os=hurl.getOutputStream();
                os.write(urlparams.getBytes());
                os.flush();
                os.close();

                InputStream is=hurl.getInputStream();
                while ((tmp=is.read())!=-1)
                {
                    data+=(char)tmp;
                }
                is.close();
                hurl.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals(""))
                s="data not saved";
            // Toast.makeText(Register.this, s+"", Toast.LENGTH_SHORT).show();
            Toast.makeText(Register.this, "save ya man", Toast.LENGTH_SHORT).show();

        }
    }


}
