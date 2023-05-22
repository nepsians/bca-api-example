package com.example.apiexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView idTv;
    TextView nameTv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        ArrayList<MyDataModel> tasks = new ArrayList<MyDataModel>();
//        tasks.add(new MyDataModel("Wake up at 8am", "", ""));
//        tasks.add(new MyDataModel("Learn java programming for 2 hour", "", ""));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tasks);
        recyclerView.setAdapter(adapter);


        String apiUrl = "https://dummyjson.com/products";
//        String apiUrl = "http://192.168.56.1/api/test_api.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonData = response;
                Log.d("JSON DATA: ", jsonData);

//                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();

                jsonArrayDecode(jsonData, adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_number", "1000");
                params.put("second_number", "2000  ");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void jsonArrayDecode(String jsonData, RecyclerViewAdapter adapter) {
        ArrayList<MyDataModel> data = new ArrayList<MyDataModel>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            JSONArray posts = jsonObject.getJSONArray("products");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i);

                String title = post.getString("title");
                String description = post.getString("description");
                String thumbnail = post.getString("thumbnail");

                data.add(new MyDataModel(title, description, thumbnail));
            }

            adapter.setPosts(data);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


//    public void jsonDecode(String jsonData) {
//        try {
//            JSONArray jsonArray = new JSONArray(jsonData);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                int id = jsonObject.getInt("id");
//                String name = jsonObject.getString("title");
//
//
//                idTv.setText("ID: " + id);
//                nameTv.setText("Name: " + name);
//            }
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }
}