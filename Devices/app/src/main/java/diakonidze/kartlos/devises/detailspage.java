package diakonidze.kartlos.devises;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class detailspage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspage);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Devises devicedetails = (Devises) getIntent().getExtras().getSerializable("obj");

        TextView text_dev = (TextView) findViewById(R.id.text_device_2);
        TextView textView = (TextView) findViewById(R.id.text_info);
        textView.setText(devicedetails.getInfo());
        text_dev.setText(devicedetails.getModel());
        ImageView image = (ImageView) findViewById(R.id.imageView_dedails);

        try{
            Picasso.with(this)
                    .load(devicedetails.getImig())
                    .resize(1000,1111)
                    .centerInside()
                    .into(image);
        }catch (Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailspage, menu);
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
            onBackPressed();
            return true;
        }

        if (id == R.id.cancel_action) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
