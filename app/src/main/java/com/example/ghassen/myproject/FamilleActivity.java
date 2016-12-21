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

public class FamilleActivity extends AppCompatActivity {
    private static final  int Delete=0;
    List<Family> families = new ArrayList<>();
    ListView FamilyListView;
    DatabaseHandler2 dbHandler;
    ListView lv;
    ArrayAdapter<Family> FamilyAdapter;
    int longClickedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famille);
        Button button=(Button)findViewById(R.id.addFamille);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FamilleActivity.this, AddFamilyActivity.class);
                startActivity(intent1);
            }
        });
        dbHandler = new DatabaseHandler2(this);
        dbHandler.open();
        FamilyListView = (ListView)findViewById(R.id.list);

        families.addAll(dbHandler.getAllFamily());

        populateList();
        registerForContextMenu(FamilyListView);
        FamilyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });
    }
    private boolean familyExists(Family family){

        String name = family.getName();
        int familyCount=families.size();
        for (int i=0;i<familyCount;i++){
            if(name.compareToIgnoreCase(families.get(i).getName())==0)
                return true;
        }
        return false;
    }

    private class FamilyListAdapter extends ArrayAdapter<Family>{
        public FamilyListAdapter(){
            super(FamilleActivity.this,R.layout.row_item1,families);
        }
        @Override
        public View getView(int position,View view, ViewGroup patent){

            view=getLayoutInflater().inflate(R.layout.row_item1,patent,false);


            Family currentFamily = families.get(position);

            TextView name = (TextView)view.findViewById(R.id.name);
            name.setText(currentFamily.getName());

            return view;
        }


    }

    private void populateList(){
        FamilyAdapter = new FamilyListAdapter();
        FamilyListView.setAdapter(FamilyAdapter);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);


        menu.add(Menu.NONE, Delete, menu.NONE, "Delete Family Event");

    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){

            case Delete:
                dbHandler.deleteFamily(families.get(longClickedItemIndex));
                families.remove(longClickedItemIndex);
                FamilyAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
