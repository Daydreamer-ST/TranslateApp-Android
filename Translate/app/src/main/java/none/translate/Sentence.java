package none.translate;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sentence extends Activity {
    private String sj;
    private String content;
    private String note;
    private String translation;
    private String string;
    private ArrayList all;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.second);
        SharedPreferences read = getSharedPreferences("like", MODE_PRIVATE);
        int id = read.getInt("id", 0);
        all=new ArrayList();
        for(int ids=0;ids<id;ids++){
            String text = read.getString(String.valueOf(ids), "");
            all.add(text);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Sentence.this,android.R.layout.simple_list_item_1,all);
        ListView listview=(ListView)findViewById(R.id.likeall);
        listview.setAdapter(adapter);
    }
    public void translate (View v){
        Intent intent =new Intent(Sentence.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
