package com.example.ghassen.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends AppCompatActivity {
    private static final  int Delete=0;
    List<Business> businesses = new ArrayList<>();
    ListView businessListView;
    DatabaseHandler dbHandler;
    ListView lv;
    ArrayAdapter<Business> BusinessAdapter;
    int longClickedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Button button=(Button)findViewById(R.id.addBusiness);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BusinessActivity.this, AddBusinessActivity.class);
                startActivity(intent1);
            }
        });
        dbHandler = new DatabaseHandler(this);
        dbHandler.open();
        businessListView  = (ListView)findViewById(R.id.list);

      businesses.addAll(dbHandler.getAllBusinesses());

        populateList();
        registerForContextMenu(businessListView);
       businessListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });
    }
    private boolean businessExists(Business business){

        String name = business.getName();
        int BusinessCount=businesses.size();
        for (int i=0;i<BusinessCount;i++){
            if(name.compareToIgnoreCase(businesses.get(i).getName())==0)
                return true;
        }
        return false;
    }

    private class BusinessListAdapter extends ArrayAdapter<Business>{
        public BusinessListAdapter(){
            super(BusinessActivity.this,R.layout.row_item,businesses);
        }
        @Override
        public View getView(int position,View view, ViewGroup patent){

            view=getLayoutInflater().inflate(R.layout.row_item,patent,false);


            Business currentBusiness = businesses.get(position);

            TextView name = (TextView)view.findViewById(R.id.name);
            name.setText(currentBusiness.getName());
            TextView description = (TextView)view.findViewById(R.id.decription);
            description.setText(currentBusiness.getDescription());

            return view;
        }


    }

    private void populateList(){
        BusinessAdapter = new BusinessListAdapter();
        businessListView.setAdapter(BusinessAdapter);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);


        menu.add(Menu.NONE, Delete, menu.NONE, "Delete Business");

    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){

            case Delete:
                dbHandler.deleteBusiness(businesses.get(longClickedItemIndex));
                businesses.remove(longClickedItemIndex);
                BusinessAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
