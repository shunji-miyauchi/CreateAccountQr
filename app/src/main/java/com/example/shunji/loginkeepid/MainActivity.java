package com.example.shunji.loginkeepid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;


        public class MainActivity extends AppCompatActivity {

            private UploadTask task;
            private TextView textView;
            // wordを入れる
            private EditText editText;

            // phpがPOSTで受け取ったwordを入れて作成するHTMLページ(適宜合わせてください)
            String url = "https://10.0.2.2/HTTP.html";


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                editText = findViewById(R.id.uriname);

                Button post = findViewById(R.id.post);

                // ボタンをタップして非同期処理を開始
                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String param0 = editText.getText().toString();

                        if(param0.length() != 0){
                            task = new UploadTask();
                            task.setListener(createListener());
                            task.execute(param0);
                        }

                    }
                });

                // ブラウザを起動する
                Button browser = findViewById(R.id.browser);
                browser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // phpで作成されたhtmlファイルへアクセス
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);

                        // text clear
                        textView.setText("");
                    }
                });

                textView = findViewById(R.id.text_view);
            }


            @Override
            protected void onDestroy() {
                task.setListener(null);
                super.onDestroy();
            }

            private UploadTask.Listener createListener() {
                return new UploadTask.Listener() {
                    @Override
                    public void onSuccess(String result) {
                        textView.setText(result);
                    }
                };
            }
        }

