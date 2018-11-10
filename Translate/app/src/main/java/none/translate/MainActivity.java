package none.translate;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends Activity {

    private String path_baidu;
    private String path_youdao;
    private String path_google;
    private String path_bing;
    private String search_text;
    private int times;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        times=0;
        SharedPreferences spf = getSharedPreferences("like", MODE_PRIVATE);
        id = spf.getInt("id", 0);
        //设置默认id为0
    }
    public void search(View v) {
        times = times + 1;
        Toast.makeText(this, String.valueOf(times), Toast.LENGTH_LONG).show();
        if (times == 1) {
            EditText text_Get = (EditText) findViewById(R.id.text);
            String text_before = text_Get.getText().toString();
            StringBuffer text_sb = new StringBuffer(text_before);
            String text = text_sb.toString().replaceAll(" ", "%20");//空格转换
            search_text = text_before;
            if (text.matches(".*[a-zA-z].*"))//判断中文/英文
            {
                path_baidu = "https://fanyi.baidu.com/transapi?from=auto&to=zh&query=" + text;
                path_youdao = "http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=" + text;
                path_google = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh&q=" + text;
                path_bing = "http://api.microsofttranslator.com/v2/Http.svc/Translate?appId=AFC76A66CF4F434ED080D245C30CF1E71C22959C&from=&to=zh&text=" + text;
                //英文转中文
            } else {
                path_baidu = "https://fanyi.baidu.com/transapi?from=auto&to=en&query=" + text;
                path_youdao = "http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=" + text;
                path_google = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=en&q=" + text;
                path_bing = "http://api.microsofttranslator.com/v2/Http.svc/Translate?appId=AFC76A66CF4F434ED080D245C30CF1E71C22959C&from=&to=en&text=" + text;
                //中文转英文
            }
            DownloadManager.Request request_baidu = new DownloadManager.Request(Uri.parse(path_baidu));
            request_baidu.setAllowedOverRoaming(false);
            //发起下载请求
            request_baidu.setDestinationInExternalFilesDir(this, "/storage/emulated/0/MyTranslate", "BaiDu.txt");
            //设置下载的名称和路径
            DownloadManager Manager_BaiDu = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Manager_BaiDu.enqueue(request_baidu);
            //完成下载
            //BaiDu Download
            DownloadManager.Request request_youdao = new DownloadManager.Request(Uri.parse(path_youdao));
            request_youdao.setAllowedOverRoaming(false);
            //发起下载请求
            request_youdao.setDestinationInExternalFilesDir(this, "/storage/emulated/0/MyTranslate", "YouDao.txt");
            //设置下载的名称和路径
            DownloadManager Manager_YouDao = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Manager_YouDao.enqueue(request_youdao);
            //完成下载
            //YouDao Download
            DownloadManager.Request request_google = new DownloadManager.Request(Uri.parse(path_google));
            request_google.setAllowedOverRoaming(false);
            //发起下载请求
            request_google.setDestinationInExternalFilesDir(this, "/storage/emulated/0/MyTranslate", "Google.txt");
            //设置下载的名称和路径
            DownloadManager Manager_Google = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Manager_Google.enqueue(request_google);
            //完成下载
            //Google Download
            DownloadManager.Request request_bing = new DownloadManager.Request(Uri.parse(path_bing));
            request_bing.setAllowedOverRoaming(false);
            //发起下载请求
            request_bing.setDestinationInExternalFilesDir(this, "/storage/emulated/0/MyTranslate", "Bing.txt");
            //设置下载的名称和路径
            DownloadManager Manager_Bing = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Manager_Bing.enqueue(request_bing);
            //完成下载
            //Bing Download
        }
        if (times == 2) {
            try {
                FileInputStream fin = new FileInputStream("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/BaiDu.txt");
                //创建字节数组，准备将文件流中的数据传给字节数组
                byte[] b = new byte[1024];
                fin.read(b);
                String baidu_translate = new String(b);
                JSONObject objectfirst = new JSONObject(baidu_translate);
                String sentences = objectfirst.getString("data");
                JSONArray jsonArray = new JSONArray(sentences);
                JSONObject result_Get = jsonArray.getJSONObject(0);
                String result_baidu = result_Get.getString("dst");
                TextView baidu = (TextView) findViewById(R.id.baidu);
                baidu.setText(result_baidu);
                //解析JSON
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Show BaiDu Translate
            try {
                FileInputStream fin = new FileInputStream("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/YouDao.txt");
                //创建字节数组，准备将文件流中的数据传给字节数组
                byte[] b = new byte[1024];
                fin.read(b);
                String youdao_translate = new String(b).trim();
                JSONObject objectfirst = new JSONObject(youdao_translate);
                String translateResult = objectfirst.getString("translateResult");
                String result_youdao_1 = translateResult.replace("[[{\"src\":\"", "").toString();
                String result_youdao_2 = result_youdao_1.replace(search_text, "").toString();
                String result_youdao_3 = result_youdao_2.replace("\",\"tgt\":\"", "").toString();
                String result_youdao_4 = result_youdao_3.replace("\"}]]", "").toString();
                TextView youdao = (TextView) findViewById(R.id.youdao);
                youdao.setText(result_youdao_4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Show YouDao Translate
            try {
                FileInputStream fin = new FileInputStream("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/Google.txt");
                //创建字节数组，准备将文件流中的数据传给字节数组
                byte[] b = new byte[1024];
                fin.read(b);
                String googole_translate = new String(b);
                JSONObject objectfirst = new JSONObject(googole_translate);
                String sentences = objectfirst.getString("sentences");
                JSONArray jsonArray = new JSONArray(sentences);
                JSONObject result_Get = jsonArray.getJSONObject(0);
                String result_google = result_Get.getString("trans");
                TextView google = (TextView) findViewById(R.id.google);
                google.setText(result_google);
                //解析JSON
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Show Google Translate
            try {
                FileInputStream fin = new FileInputStream("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/Bing.txt");
                //创建字节数组，准备将文件流中的数据传给字节数组
                byte[] b = new byte[1024];
                fin.read(b);
                String bing_translate = new String(b);
                String result_bing_1 = bing_translate.replace("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">", "");
                String result_bing = result_bing_1.replace("</string>", "");
                TextView bing = (TextView) findViewById(R.id.bing);
                bing.setText(result_bing);
            } catch (Exception e) {
                e.printStackTrace();
            }
            times = 0;
            //次数重置为0
            File file_path_baidu = new File("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/BaiDu.txt");
            file_path_baidu.delete();
            File file_path_youdao = new File("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/YouDao.txt");
            file_path_youdao.delete();
            File file_path_google = new File("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/Google.txt");
            file_path_google.delete();
            File file_path_bing = new File("/storage/emulated/0/Android/data/none.translate/files/storage/emulated/0/MyTranslate/Bing.txt");
            file_path_bing.delete();
        }
        //翻译实现
    }
    public void star (View v){
        Intent intent =new Intent(MainActivity.this,Sentence.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
    public void like (View v){
        SharedPreferences.Editor editor = getSharedPreferences("like", MODE_PRIVATE).edit();
        editor.putString(String.valueOf(id), search_text);
        id=id+1;
        editor.putInt("id", id);
        editor.commit();
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        //收藏功能
    }
}
