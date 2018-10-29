package com.dev.eivs.testscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dev.eivs.testscan.activity.CameraActivity;
import com.dev.eivs.testscan.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int SELECT_PICTURE = 1;
    private static final int SCAN_PICTURE = 2;
    public String resutScan;
    private RecyclerView recyclerView;
    List<ProductModel> result;
    ProductManager productManager = new ProductManager();
    private ProductAdapter adapter;
    TextView emptyText;

    private int requestedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emptyText = (TextView) findViewById(R.id.text_no_data);
        recyclerView = (RecyclerView) findViewById(R.id.product_list);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        result = new ArrayList<>();
        result = productManager.pm;
        adapter = new ProductAdapter(result);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
                checkIfEmty();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                removeProduct(item.getGroupId());
                checkIfEmty();
                break;
            case 1:
                updateItemText(item.getGroupId());
                break;
            case 2:
                updateItemImage(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void removeProduct(int position) {
        result.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, result.size());

    }

    private void addProduct() {
        result.add(new ProductModel("https://www.wallpaperwolf.com/images/stormy-sea-0535-25.jpg"));
        adapter.notifyItemChanged(result.size());
        adapter.notifyItemRangeChanged(0, result.size());
    }

    private void updateItemText(int index) {
        requestedIndex = index;
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, SCAN_PICTURE);

    }

    private void updateItemImage(int index) {
        requestedIndex = index;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    private void checkIfEmty(){
        if(result.size() == 0){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
              String selectedImageUri = String.valueOf(data.getData());
                result.get(requestedIndex).setUrl(selectedImageUri);
                adapter.notifyItemChanged(requestedIndex);
            } else if (requestCode == SCAN_PICTURE) {
                resutScan = data.getStringExtra("name");
                result.get(requestedIndex).setDescription(resutScan);
                adapter.notifyItemChanged(requestedIndex);

            }
        }
    }
}
