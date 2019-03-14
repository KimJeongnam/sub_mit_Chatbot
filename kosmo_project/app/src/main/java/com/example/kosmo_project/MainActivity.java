package com.example.kosmo_project;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kosmo_project.httpcommunication.ConnectInterface;
import com.example.kosmo_project.httpcommunication.MyRetrofit;
import com.example.kosmo_project.httpcommunication.WeatherAPIInterface;
import com.example.kosmo_project.httpcommunication.WeatherRetrofit;
import com.example.kosmo_project.vo.Message;
import com.example.kosmo_project.vo.RecvData;
import com.example.kosmo_project.vo.User;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static ConnectInterface connectInterface;
    public static WeatherAPIInterface weatherAPIInterface;

    @BindView(R.id.edit_text_id) EditText editText_id;
    @BindView(R.id.edit_text_pw) EditText editText_pw;

    public android.text.format.DateFormat df = new android.text.format.DateFormat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.title_bar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_bar);

        ButterKnife.bind(this);

        connectInterface  = MyRetrofit.getRetrofit().create(ConnectInterface.class);
        weatherAPIInterface = WeatherRetrofit.getRetrofit().create(WeatherAPIInterface.class);

        connectInterface.connect().enqueue(new Callback<RecvData>() {
            @Override
            public void onResponse(Call<RecvData> call, Response<RecvData> response) {
                if (response.isSuccessful()) {
                    RecvData data = response.body();

                    Toast.makeText(getApplicationContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RecvData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버 연결 오류!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick(R.id.login_button)
    void requestLogin(View view){
        String userNumber = editText_id.getText().toString();
        String userPassword = editText_pw.getText().toString();

        connectInterface.requestLogin(userNumber, userPassword).enqueue(new Callback<RecvData<User>>() {
            @Override
            public void onResponse(Call<RecvData<User>> call, Response<RecvData<User>> response) {
                if (response.isSuccessful()) {
                    RecvData data = response.body();

                    Toast.makeText(getApplicationContext(), data.getMessage(), Toast.LENGTH_LONG).show();

                    if(data.getStatus()==1){
                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                        intent.putExtra("message", data.getMessage());
                        intent.putExtra("user", (User)data.getData());
                        intent.putExtra("userNumber", userNumber);

                        // 메세지 리스트 초기화
                        if(Message.messageList==null)
                            Message.messageList = new ArrayList<Message>();

                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RecvData<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "requestLogin() Error!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
