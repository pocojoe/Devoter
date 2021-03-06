package com.hoppercodes.devoter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActivityBooth extends Activity implements OnClickListener
{
    EditText editAuthorName, editAuthorEmailAddress, editAuthorEmailMessage;
    Button btnAdd, btnClear;
    SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booth);
        editAuthorName =(EditText)findViewById(R.id.editAuthorName);
        editAuthorEmailAddress =(EditText)findViewById(R.id.editAuthorEmailAddress);
        editAuthorEmailMessage =(EditText)findViewById(R.id.editAuthorEmailMessage);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnClear=(Button)findViewById(R.id.btnClear);
        btnAdd.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        String DB_PATH = Environment.getExternalStorageDirectory().getPath().toString();
        String DB_NAME = "DevoterDB";
        String DB_FQN = DB_PATH+"/"+DB_NAME;
        // String DB_FQN="DevoterDB";
        // showMessage("devoterDB Fully Qualified Name", DB_FQN);
        db=openOrCreateDatabase(DB_FQN, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS delayEmail(auName VARCHAR,auEmail VARCHAR,auMessage VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            if(editAuthorName.getText().toString().trim().length()==0||
                    editAuthorEmailAddress.getText().toString().trim().length()==0||
                    editAuthorEmailMessage.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO delayEmail VALUES('"+ editAuthorName.getText()+"','"+ editAuthorEmailAddress.getText()+
                    "','"+ editAuthorEmailMessage.getText()+"');");
            showMessage("Success", "Record added");
            clearText();
        }

        if(view==btnClear)
        {
            clearText();
        }
    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editAuthorName.setText("");
        editAuthorEmailAddress.setText("");
        editAuthorEmailMessage.setText("");
        editAuthorName.requestFocus();
    }
}