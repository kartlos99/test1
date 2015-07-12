package diakonidze.kartlos.devises;  // V 1.4

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import diakonidze.kartlos.devises.database.DBmanager;
import diakonidze.kartlos.devises.database.DBscheme;


public class MainActivity extends ActionBarActivity {

    private List<Devises> mydataSRV;
    int previousGroup = 999;
    private ArrayList<Devises> mydata;
    int rrr =0;

    private ArrayList<Devises> mainInfoList, title_list, explist;
    HashMap<String, List<Devises>> childlist;
    Myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setIcon(R.mipmap.ic_launcher_devices);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);//Title("jeolabi");
        actionbar.setTitle("ჯეოლაბის ტექნიკა");
//        actionbar.setBackgroundDrawable(new ColorDrawable(Color.RED));


        mainInfoList = new ArrayList<Devises>();
        childlist = new HashMap<String, List<Devises>>();
        title_list = new ArrayList<Devises>();


            DBmanager.initialaize(this);
            DBmanager.openReadable();
            mainInfoList = DBmanager.getDevicesList("");
            DBmanager.close();

        if(mainInfoList.size() == 0){
            getDataFromServer();
        }

        dataSorting(mainInfoList);

        ExpandableListView mylistview = (ExpandableListView) findViewById(R.id.expandableListView);


        myadapter = new Myadapter(getApplication(), title_list, childlist);

        mylistview.setAdapter(myadapter);


        mylistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Devises detailed = (Devises) myadapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), detailspage.class);
                intent.putExtra("obj", detailed);

                startActivity(intent);
                return false;
            }
        });

        mylistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    if (groupPosition != previousGroup) {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);


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
//            TextView textView = (TextView) findViewById(R.id.textView);
//            textView.setText(item.getTitle());
            return true;
        }
        if (id == R.id.action_search) {

            return true;
        }
        if (id == R.id.update){
            getDataFromServer();
            myadapter.notifyDataSetChanged();
//            Toast.makeText(this, "vitom ganaaxla",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Devises> getmydata() {

        List<Devises> mydata = new ArrayList<>();

        mydata.add(new Devises("IaMac", "A33333  3 333 33pple", "hr45 45 45 4 5454 499", "დედატური ინფო", "img url", 1));
        mydata.add(new Devises("IaMac", "Apple", "hsw499", "დედატური ინფო", "img url", 2));
        mydata.add(new Devises("IaMac", "Apple", "hr229", "დედატური ინფო , moklg", "url", 3));
        mydata.add(new Devises("ფდგფ დფ", "Apple", "hr400", "დედატური ინფო", "img url", 2));
        mydata.add(new Devises("Xleptop", "dell", "hra42", "დედატური ინფო", "img url", 4));
        mydata.add(new Devises("Xleptop", "Apple", "macair2", "დედატური ინფო", "img url", 3));
        mydata.add(new Devises("Xleptop", "asus", "km55", "დედატური ინფო", "img url", 5));
        mydata.add(new Devises("Z_tablet", "samsung", "ns43", "დედატური ინფო", "img url", 3));
        mydata.add(new Devises("Z_tablet", "samsung", "ns43A", "დედატური ინფო", "img url", 2));

        return mydata;
    }

    private void getDataFromServer() {

        String url = "http://176.28.22.210/tech/index.php/TechList/API";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        ArrayList<Devises> newData = new ArrayList<>();

                        if(jsonArray.length()>0){
                            for(int i=0; i<jsonArray.length(); i++){
                                try {
                                    newData.add(new Devises(
                                            jsonArray.getJSONObject(i).getString("name"),
                                            jsonArray.getJSONObject(i).getString("manufactor"),
                                            jsonArray.getJSONObject(i).getString("model"),
                                            jsonArray.getJSONObject(i).getString("info"),
                                            jsonArray.getJSONObject(i).getString("photos"),
                                            jsonArray.getJSONObject(i).getInt("quantity")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //  Toast.makeText(getApplicationContext(),"N " + mainInfoList.size(),Toast.LENGTH_LONG).show();
                        // ******  daxarisxeba
                        if (newData.size() > 0) {           // tu erti mowyobiloba mainc aris mashin vaxarisxebt lists

                            writeInDatabase(newData);

                            dataSorting(newData);
                        }
//                        myadapter.notifyDataSetChanged();
                        myadapter = new Myadapter(getApplication(), title_list, childlist);
                        ExpandableListView mylistview = (ExpandableListView) findViewById(R.id.expandableListView);
                        mylistview.setAdapter(myadapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(),volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        queue.add(request);

    }

    private void dataSorting(ArrayList<Devises> unSortData) {

        DBmanager.initialaize(getApplicationContext());
        DBmanager.openReadable();

        title_list = DBmanager.getUnicTitles();

        for (int i = 0; i < title_list.size(); i++){
            explist = new ArrayList<Devises>();
            explist = DBmanager.getDevicesList(DBscheme.DEVICES_CATEGORY + " = '" + title_list.get(i).getDeviceCategory()+"'");

            title_list.get(i).setImig(explist.get(0).getImig());
            childlist.put(title_list.get(i).getDeviceCategory(), explist);
        }
        DBmanager.close();

/////////////////////////////  Array listebit daxarisxeba (bazamde amas viyenebdi)
        /*
        if(unSortData.size() > 0) {
            // gavigot ramdeni unikaluri mowyobiloba gvaqvs
            title_list.add(new Devises(unSortData.get(0).getDeviceCategory(), "", "", "", unSortData.get(0).getImig(), 0));
            int temtcout = 0;
            for (Devises temp : unSortData) {
                if (!temp.getDeviceCategory().equals(title_list.get(temtcout).getDeviceCategory())) {
                    title_list.add(new Devises(temp.getDeviceCategory(), "", "", "", temp.getImig(), 0));
                    temtcout++;
                }
            }

            // titoeuli mowyobilobistvis calke vaquchebt modelebs
            for (int i = 0; i < title_list.size(); i++) {

                explist = new ArrayList<Devises>();
                int device_sumcount = 0;

                for (int j = 0; j < unSortData.size(); j++) {
                    if (unSortData.get(j).getDeviceCategory().equals(title_list.get(i).getDeviceCategory())) {
                        explist.add(unSortData.get(j));
                        device_sumcount += unSortData.get(j).getCount();
                    }
                }
                title_list.get(i).setCount(device_sumcount);
                childlist.put(title_list.get(i).getDeviceCategory(), explist);
            }
        }
        */
    }

    public void writeInDatabase(ArrayList<Devises> saveit){
        DBmanager.initialaize(getApplicationContext());
        DBmanager.openWritable();

        DBmanager.clearTable();

        for (int i=0; i<saveit.size(); i++){
            DBmanager.insertIntoDevices(saveit.get(i));
        }

        DBmanager.close();
    }
}
