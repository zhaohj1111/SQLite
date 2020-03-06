package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etname;
    private EditText etemail;
    private EditText etphone;
    private Button btnadd;
    private Button btnsel;
    private  Button btnupd;
    private Button btndel;
    private TextView tvshow;
    MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname=(EditText)findViewById(R.id.et_name);
        etemail=(EditText)findViewById(R.id.et_mail);
        etphone=(EditText)findViewById(R.id.et_phone);


        btnadd=(Button)findViewById(R.id.btn_add);
        btnsel=(Button)findViewById(R.id.btn_query);
        btnupd=(Button)findViewById(R.id.btn_Update);
        btndel=(Button)findViewById(R.id.btn_delete);
        tvshow=(TextView)findViewById(R.id.txt_show);
        myHelper= new MyHelper(this);

        btnadd.setOnClickListener(this);
        btnsel.setOnClickListener(this);
        btnupd.setOnClickListener(this);
        btndel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String name;
        String email;
        String phone;
        SQLiteDatabase db;
        ContentValues values;


        switch (v.getId()){
            case R.id.btn_add:
                name=etname.getText().toString();
                email=etemail.getText().toString();
                phone=etphone.getText().toString();
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",name);
                values.put("Email",email);
                values.put("phone",phone);
                db.insert("info",null,values);
                Toast.makeText(this,"add successfully",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_delete:
                db=myHelper.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"delete successfully",Toast.LENGTH_SHORT).show();
                db.close();
                tvshow.setText("");
                break;
            case R.id.btn_Update:
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("phone",etphone.getText().toString());
                db.update("info",values,"name=?",new String[]{etname.getText().toString()});
                Toast.makeText(this,"update successfully",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_query:
                db=myHelper.getWritableDatabase();
                Cursor cursor=db.query("info",null,null,null,
                        null,null,null,null);
                if(cursor.getCount()==0){
                    Toast.makeText(this,"no query.",Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToFirst();
                    tvshow.setText("name:"+cursor.getString(1)+"   phone:"+cursor.getString(2)+"email:"+cursor.getString(3));
                }
                while(cursor.moveToNext()){
                    tvshow.append("\n"+"name:"+cursor.getString(1)+"   phone:"+cursor.getString(2)+"   email:"+cursor.getString(3));
                }
                cursor.close();
                db.close();
                //Toast.makeText(this,"query successfully",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
