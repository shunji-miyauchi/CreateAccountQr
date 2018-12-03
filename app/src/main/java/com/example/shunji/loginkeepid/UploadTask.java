package com.example.shunji.loginkeepid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//public class UploadTask extends AppCompatActivity {
//
  //  @Override
   // protected void onCreate(Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_upload_task);
   // }
//}

public class UploadTask extends AsyncTask<String, Void, String> {

    private Listener listener;

    // 非同期処理
    @Override
    protected String doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "https://hoge-hoge.com/token_check.php";

        HttpURLConnection httpConn = null;
        String result = null;
        String word = "word="+params[0];

        try {
            // URL設定
            URL url = new URL(urlSt);

            // HttpURLConnection
            httpConn = (HttpURLConnection) url.openConnection();

            // request POST
            httpConn.setRequestMethod("POST");

            // no Redirects
            httpConn.setInstanceFollowRedirects(false);

            // データを書き込む
            httpConn.setDoOutput(true);

            // 時間制限
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);

            // 接続
            httpConn.connect();

            // POSTデータ送信処理
            OutputStream outStream = null;

            try {
                outStream = httpConn.getOutputStream();
                outStream.write( word.getBytes("UTF-8"));
                outStream.flush();
                Log.d("debug","flush");
            } catch (IOException e) {
                // POST送信エラー
                e.printStackTrace();
                result="POST送信エラー";
            } finally {
                if (outStream != null) {
                    outStream.close();
                }
            }

            final int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // レスポンスを受け取る処理等
                result="HTTP_OK";
            }
            else{
                result="status="+String.valueOf(status);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return result;
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String result);
    }
}
