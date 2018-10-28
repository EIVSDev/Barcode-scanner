package com.dev.eivs.testscan;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final int SELECT_PICTURE = 1;
    private static final int SCAN_PICTURE = 2;
    public String selectedImageUri;
    public String resutScan;
    private RecyclerView recyclerView;
    List<ProductModel> result;
    private ProductAdapter adapter;

    TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        new ItemTask().execute();
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
        recyclerView.setLayoutManager(glm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                removeProduct(item.getGroupId());
                break;
            case 1:
                addProduct();
                break;
            case 2:
                updateItemText(item.getGroupId(), resutScan);
                break;
            case 3:
                updateItemImage(item.getGroupId(), selectedImageUri);
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

    private class ItemTask extends AsyncTask<Void, Void, ProductManager> {

        @Override
        protected ProductManager doInBackground(Void... voids) {
            return new ProductManager();
        }

        @Override
        protected void onPostExecute(ProductManager models) {
            result = models.pm;
            adapter = new ProductAdapter(result);
            recyclerView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
        }
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

    private void updateItemText(int position, String text) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, SCAN_PICTURE);
        result.get(position).setDescription(text);
        adapter.notifyItemChanged(position);
    }

    private void updateItemImage(int index, String url) { // выбор изображения

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        result.get(index).setUrl(url);
        adapter.notifyItemChanged(index);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = String.valueOf(data.getData());
            } else if (requestCode == SCAN_PICTURE) {
                resutScan = data.getStringExtra("name");

                Log.e("____>", resutScan);
            }
        }
    }
}
