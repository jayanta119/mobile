package ratingbar.example.com.inbox;
 
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
 
import java.util.ArrayList;
 
public class MainActivity extends AppCompatActivity {
 
    Toolbar toolbar;
    ListView lv_sms;
    ArrayList<SmsModel> smsModelArrayList;
    SmsAdapter smsAdapter;
    ProgressDialog progressDialog;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //method for initialisation
        init();
 
        //Method to read sms and load into listview
        readSms();
    }
 
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading sms");
 
        smsModelArrayList = new ArrayList<>();
        lv_sms = (ListView) findViewById(R.id.lv_sms);
        smsAdapter = new SmsAdapter(MainActivity.this,smsModelArrayList);
        lv_sms.setAdapter(smsAdapter);
 
    }
 
    public void readSms(){
 
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null ,null,null);
        startManagingCursor(c);
 
        progressDialog.show();
        // Read the sms data
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
 
                String mobile = c.getString(c.getColumnIndexOrThrow("address")).toString();
                String message = c.getString(c.getColumnIndexOrThrow("body")).toString();
 
                //adding item to array list
                smsModelArrayList.add(new SmsModel(mobile, message));
                c.moveToNext();
            }
 
        }
        c.close();
 
        progressDialog.dismiss();
        // notifying listview adapter
 
        smsAdapter.notifyDataSetChanged();
 
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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