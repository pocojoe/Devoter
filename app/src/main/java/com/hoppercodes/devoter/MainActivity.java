package com.hoppercodes.devoter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


//public class MainActivity extends AppCompatActivity {

 //   @Override
 //   protected void onCreate(Bundle savedInstanceState) {
 //       super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_main);
 //   }
//}


public class MainActivity extends Activity implements OnClickListener
{
    EditText editAuthorName, editAuthorEmailAddress, editAuthorEmailMessage;
    Button btnAdd,btnDelete,btnModify,btnView,btnViewAll,btnShowInfo;
    SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAuthorName =(EditText)findViewById(R.id.editAuthorName);
        editAuthorEmailAddress =(EditText)findViewById(R.id.editAuthorEmailAddress);
        editAuthorEmailMessage =(EditText)findViewById(R.id.editAuthorEmailMessage);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnShowInfo=(Button)findViewById(R.id.btnShowInfo);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        db=openOrCreateDatabase("DevoterDB", Context.MODE_PRIVATE, null);
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
        if(view==btnDelete)
        {
            if(editAuthorName.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter auName");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM delayEmail WHERE auName='"+ editAuthorName.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM delayEmail WHERE auName='"+ editAuthorName.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid auName");
            }
            clearText();
        }
        if(view==btnView)
        {
            if(editAuthorName.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter auName");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM delayEmail WHERE auName='"+ editAuthorName.getText()+"'", null);
            if(c.moveToFirst())
            {
                editAuthorEmailAddress.setText(c.getString(1));
                editAuthorEmailMessage.setText(c.getString(2));
            }
            else
            {
                showMessage("Error", "Invalid auName");
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM delayEmail", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("auName: "+c.getString(0)+"\n");
                buffer.append("auEmail: "+c.getString(1)+"\n");
                buffer.append("auMsg: "+c.getString(2)+"\n\n");
            }
            showMessage("delayEmail Details", buffer.toString());
        }
        if(view==btnShowInfo)
        {
            showMessage("Devoter Time Machine", "Thanks to Azim");
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