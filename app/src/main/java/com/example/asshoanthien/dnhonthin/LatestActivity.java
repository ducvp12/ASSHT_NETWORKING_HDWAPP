package com.example.asshoanthien.dnhonthin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asshoanthien.dnhonthin.adapter.AdapterLatest;
import com.example.asshoanthien.dnhonthin.adapter.LatestAdapter;
import com.example.asshoanthien.dnhonthin.model.latest;
import com.example.asshoanthien.dnhonthin.model.modelex.Photo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LatestActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
int page=1;
int per_page=10;
    private RecyclerView mRvCate;
     List<Photo> modelLatests;
    private AdapterLatest adapterLatest;
    SwipeRefreshLayout f5;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<latest> latestList;
    LatestAdapter latestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest);


        getData();
//        f5.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //lay du lieu
//                page=1;
//                modelLatests.clear();
//                getData(page,per_page);
//            }
//        });
//
//        mRvCate.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                page=page+1;
//                getData(page,per_page);
//            }
//        });


//
//        for (int i = 0; i < 40; i++) {
//            modelLatestList.add(new ModelLatest( "", "", i + "", i + ""));
//        }
//
//        Log.e("size", load.size() + "");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getData() {



        RequestQueue requestQueue = Volley.newRequestQueue(LatestActivity.this);
        String url = "https://www.flickr.com/services/rest/?method=flickr.favorites.getList&api_key=24bf810575bc5bfbe2aef1ed6cd4517b&user_id=63356846%40N04&format=json&nojsoncallback=1&extras=views,%20media,%20path_alias,%20url_sq,%20url_t,%20url_s,%20url_q,%20url_m,%20url_n,%20url_z,%20url_c,%20url_l,%20url_o&per_page=10&page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua", response);
                        try {

                            ArrayList<latest> latests = new ArrayList<>();

                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectP = jsonObject.getJSONObject("photos");
                            Log.d("photo", jsonObjectP.toString());


                            JSONArray jsonArrayS = jsonObjectP.getJSONArray("photo");
                            Log.d("photo2", jsonArrayS.toString());

                            for(int i=0 ; i<jsonArrayS.length();i++) {
                                JSONObject jsonObjectQ = jsonArrayS.getJSONObject(i);
                                Log.d("ddddd", jsonObjectQ.toString());

//                                    JSONObject jsonObjectA = jsonObjectQ.getJSONObject("url_sq");
//                                    Log.d("mmmmmm", jsonObjectQ.toString());

                                String url = jsonObjectQ.getString("url_m");
                                String a = jsonObjectQ.getString("title");
                                Log.d("kkkkk", url);
                                latests.add(new latest(url,a));




                            }
//
//
//
//


//                                    }
                            mAdapter = new LatestAdapter(LatestActivity.this,latests);
                            mRvCate.setHasFixedSize(true);
                            //mGridViewLatest.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            layoutManager = new GridLayoutManager(LatestActivity.this,2);
                            mRvCate.setLayoutManager(layoutManager);
                            mRvCate.setAdapter(mAdapter);


//
//                                }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);




    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.category, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_latest) {

        } else if (id == R.id.nav_category) {
            Intent intent=new Intent(LatestActivity.this,CategoryActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_heart) {
            Intent intent=new Intent(LatestActivity.this,MyFavoryitesActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_about_us) {
            Intent intent=new Intent(LatestActivity.this,AboutUsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_more) {
            Intent intent=new Intent(LatestActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void getData(int embed){
//        Hdwallpaper_Retrofit.getInstance().getCate(embed)
//                .enqueue(new Callback<List<Latestt>>() {
//                    @Override
//                    public void onResponse(Call<List<Latestt>> call, Response<List<Latestt>> response) {
//                        Toast.makeText(LatestActivity.this, response.body().size() + "", Toast.LENGTH_SHORT).show();
//
//                        modelLatests.addAll(response.body());
//                        adapterLatest.notifyDataSetChanged();
//                        Log.e("hahahaha",modelLatests+"");
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Latestt>> call, Throwable t) {
//
//                    }
//                });
//
//    }
}