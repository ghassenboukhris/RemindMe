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

public class AchatActivity extends AppCompatActivity {
    private static final  int Delete=0;
    List<Achat> achats = new ArrayList<>();
    ListView achatListView;
    DatabaseHandler1 dbHandler;
    ListView lv;
    ArrayAdapter<Achat> AchatAdapter;
    int longClickedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achat);
        Button button=(Button)findViewById(R.id.addAchat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AchatActivity.this, AddAchatActivity.class);
                startActivity(intent1);
            }
        });
        dbHandler = new DatabaseHandler1(this);
        dbHandler.open();
        achatListView  = (ListView)findViewById(R.id.list);

        achats.addAll(dbHandler.getAllAchat());

        populateList();
        registerForContextMenu(achatListView);
        achatListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });
    }
    private boolean achatExists(Achat achat){

        String name = achat.getName();
        int AchatCount= achats.size();
        for (int i=0;i<AchatCount;i++){
            if(name.compareToIgnoreCase(achats.get(i).getName())==0)
                return true;
        }
        return false;
    }

    private class AchatListAdapter extends ArrayAdapter<Achat>{
        public AchatListAdapter(){
            super(AchatActivity.this,R.layout.row_item1,achats);
        }
        @Override
        public View getView(int position,View view, ViewGroup patent){

            view=getLayoutInflater().inflate(R.layout.row_item1,patent,false);


            Achat currentAchat = achats.get(position);

            TextView name = (TextView)view.findViewById(R.id.name);
            name.setText(currentAchat.getName());

            return view;
        }


    }

    private void populateList(){
        AchatAdapter = new AchatListAdapter();
        achatListView.setAdapter(AchatAdapter);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,view,menuInfo);


        menu.add(Menu.NONE, Delete, menu.NONE, "Delete Purchase");

    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){

            case Delete:
                dbHandler.deleteAchat(achats.get(longClickedItemIndex));
                achats.remove(longClickedItemIndex);
                AchatAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
