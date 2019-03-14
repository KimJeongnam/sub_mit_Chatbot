package com.example.kosmo_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosmo_project.enums.DayOfWeekType;
import com.example.kosmo_project.enums.MainType;
import com.example.kosmo_project.enums.SubType;
import com.example.kosmo_project.httpcommunication.MyRetrofit;
import com.example.kosmo_project.listviewadapter.ListViewItem;
import com.example.kosmo_project.recyclerview.MessageListAdapter;
import com.example.kosmo_project.service.ChatBotService;
import com.example.kosmo_project.vo.Lecture;
import com.example.kosmo_project.vo.LectureDatas;
import com.example.kosmo_project.vo.Message;
import com.example.kosmo_project.vo.RecvData;
import com.example.kosmo_project.vo.StdScore;
import com.example.kosmo_project.vo.User;
import com.example.kosmo_project.vo.weather.WeatherDataHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.MaskTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.speech.tts.TextToSpeech.ERROR;


public class ChatActivity extends AppCompatActivity {
    public static final int CHAT_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_LOCATION = 2;
    private static LocationManager locationManager;

    public static String userNumber;

    /*
    Butterknife
    각 View Bind
     */
    @BindView(R.id.userIamge)
    ImageView userImage;
    @BindView(R.id.tf_userName)
    TextView tf_userName;

    private RecyclerView mMessageRecycler;

    private EditText chatBox;
    @BindView(R.id.button_chatbox_send)
    Button sendButton;

    @BindView(R.id.chat_activity)
    RelativeLayout main_layout;

    @BindView(R.id.layout_chatbox)
    LinearLayout linearLayout;

    private TextToSpeech tts;


    private MessageListAdapter messageListAdapter;

    public static Transformation transformation;

    public android.text.format.DateFormat df = new android.text.format.DateFormat();

    private boolean isEnableGPS = false;
    // 날씨 api 에서 제공하는 위치값 변수
    public static double lat;
    public static double lon;
    public static String myLocation;


    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                lon = location.getLongitude();    //경도
                lat = location.getLatitude();         //위도
                float accuracy = location.getAccuracy();        //신뢰도

                Geocoder gCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try{
                    List<Address> addrs = gCoder.getFromLocation(lat, lon, 1);
                    Log.d("address", addrs.size()+"");
                    // 현재 주소
                    Address address = addrs.get(0);
                    Log.d("location:","////////////현재 내 위치값 : "+address.getAddressLine(0).toString());
                    myLocation = address.getAddressLine(0).toString();
                    Log.d("location:","////////////x : "+lat+",  y:"+lon);
                    isEnableGPS = true;
                }catch(IOException e){
                    e.printStackTrace();
                }
                Log.d("location","location lat: "+lat+" lon : "+lon);
            }
            else {
            //Network 위치제공자에 의한 위치변화

            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.SUCCESS) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                    int result = tts.setLanguage(Locale.KOREA);
                    //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else
                    tts.speak("자바대학교에 오신것을 환영합니다", TextToSpeech.QUEUE_FLUSH, null);
            }
        }); // TTS 변수 선언

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        chatBox = findViewById(R.id.edittext_chatbox);
        /*
        권한 설정
         */

        // Picasso 에 사용할 둥그런 이미지 transform
        transformation = new MaskTransformation(getApplicationContext(), R.drawable.rounded_image);
        // ButterKnife 바인드 선언
        ButterKnife.bind(this);

        // 채팅 입력창 에 텍스트가 있을시 send 버튼 활성화
        chatBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().trim();
                if (str.length() == 0)
                    sendButton.setEnabled(false);
                else
                    sendButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // RecyclerView 세팅
        // custom RecyclerAdapter 생성
        messageListAdapter = new MessageListAdapter(this, Message.messageList);

        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(messageListAdapter);

        mMessageRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
        {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
            {
                Log.d("sibal","onInterceptTouchEvent");
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                Log.d("sibal", "e.getX==>"+e.getX());

                Log.d("sibal", "e.getY==>"+e.getY());
                Log.d("sibal", "child==>"+child);
                hideKeyboard(rv);
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e)
            {
                Log.d("sibal","onTouchEvent");

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
            {
                Log.d("sibal","onRequestDisallowInterceptTouchEvent");
            }
        });


        getSupportActionBar().hide();

        Intent intent = getIntent();

        String toast_message = intent.getExtras().getString("message");
        User user = (User) intent.getExtras().getSerializable("user");
        userNumber = intent.getExtras().getString("userNumber");

        Toast.makeText(this, toast_message, Toast.LENGTH_LONG).show();

        tf_userName.setText(user.getUserName());
        setUserImage(user.getUserImage(), userImage);

        getMyLocation();

        botMessgae("자바대학교에 오신것을 환영합니다.!\n (keyword : 날씨, 과제 , 시간표, 학점 )", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Message.messageList = null;
        System.gc();
    }

    private void messageRecyclerUpdate(){
        messageListAdapter.notifyDataSetChanged();
        mMessageRecycler.scrollToPosition(messageListAdapter.getItemCount() - 1);
    }

    // 메세지 리스트에 메세지 추가 메소드
    public void addMessage(Message message){
        Message.messageList.add(message);
        chatBox.setText("");

        messageRecyclerUpdate();
    }

    // 키보드 내리는 메소드
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private synchronized void getMyLocation() {
        Location currentLocation = null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
            // 해당 권한이 없을시 설정
        }
        else {
            //사용자의 위치 수신을 위한 세팅
            locationManager = (LocationManager)this.getSystemService(getApplicationContext().LOCATION_SERVICE);
            // 위치 구하기

            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if(currentLocation==null){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, mLocationListener);
            }else{
                lat = currentLocation.getLatitude();
                lon = currentLocation.getLongitude();

                Geocoder gCoder = new Geocoder(this, Locale.getDefault());
                try{
                    List<Address> addrs = gCoder.getFromLocation(lat, lon, 1);
                    Log.d("address", addrs.size()+"");
                    Address address = addrs.get(0);
                    Log.d("location:","////////////현재 내 위치값 : "+address.getAddressLine(0).toString());
                    myLocation = address.getAddressLine(0).toString();
                    Log.d("location:","////////////x : "+lat+",  y:"+lon);
                    isEnableGPS = true;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    @OnClick(R.id.mic_button)
    public void openMicIntent(View view){
        Intent intent = new Intent(getApplicationContext(), MicActivity.class);
        startActivityForResult(intent, CHAT_REQUEST_CODE);
    }

    @OnClick(R.id.button_chatbox_send)
    public void sendMessageButtonClick(View view) {
        Date now = new Date();

        String str = chatBox.getText().toString();
        sendMessage(str, false);
        callChatBotService(str);
    }

    private void sendMessage(String msg, boolean voice){
        Date now = new Date();

        Message message = new Message();
        message.setMessage(msg);
        message.setTime(df.format("hh:mm", now).toString());
        message.setType(Message.VIEW_TYPE_MESSAGE_SENT);
        message.setVoice(voice);
        addMessage(message);
    }

    private void botMessgae(String msg, Object lists){
        Date now = new Date();
        Message message = new Message();
        message.setNickname("자바대학교");
        message.setMessage(msg);
        message.setTime(df.format("hh:mm", now).toString());
        message.setType(Message.VIEW_TYPE_MESSAGE_RECEIVED);
        message.setVoice(false);

        if(lists != null)
            if(lists instanceof List<?>){
                if(((List<?>)lists).size() != 0) {
                    Object i = ((List) lists).get(0);
                    if (i instanceof ListViewItem) {
                        if (((List<ListViewItem>) lists).size() != 0) {
                            message.setWeathers((List<ListViewItem>) lists);
                        }
                    }
                    if (i instanceof Lecture) {
                        if (((List<Lecture>) lists).size() != 0) {
                            message.setLectures((List<Lecture>) lists);
                        }
                    }
                    if (i instanceof StdScore) {
                        if (((List<StdScore>) lists).size() != 0) {
                            message.setStdScoreList((List<StdScore>) lists);
                        }
                    }
                }
            }

        addMessage(message);
    }

    public static void setUserImage(String path, ImageView imageView) {
        Picasso.get().load(MyRetrofit.BASE_URL + "project/resources" + path)
                .transform(transformation)
                .into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CHAT_REQUEST_CODE:
                activityResult(resultCode, data);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        locationManager.removeUpdates(mLocationListener);
    }

    private void  activityResult(int resultCode, Intent data){
        switch(resultCode){
            case MicActivity.RESULT_CODE_SPEECH:
                String str = data.getExtras().getString("result");
                sendMessage(str, true);
                callChatBotService(str);
        }
    }

    public void callChatBotService(String str){
        ChatBotService service = new ChatBotService(str, (map)->{
            MainType mainType = (MainType)map.get("mainType");
            SubType subType = (SubType)map.get("subType");
            String string = (String)map.get("str");

            /*
            0 REPORT(Arrays.asList("과제", "레포트")),
            1 WEATHER(Arrays.asList("날씨")),
            2 TIMETABLE(Arrays.asList("시간표")),
            3 SCORE(Arrays.asList("학점", "성적")),
            4 INTRODUCTION(Arrays.asList("소개")),
            5 START_PROJECT(Arrays.asList("프로젝트 시작하자", "발표 시작해", "발표 시작", "프로젝트 시작")),
            6 END_PROJECT(Arrays.asList("프로젝트 종료", "발표 종료")),
            7 RAPSTAR(Arrays.asList("랩 해 봐", "랩 해봐", "랩 해줘", "랩 해 줘", "랩 듣고 싶어", "너 랩좀하니?")),
            8 HELLO(Arrays.asList("안녕", "애나")),
            9 EMPTY(Collections.EMPTY_LIST);
             */

            Log.d("mainType : ",mainType.ordinal()+"");
            String msg = "";

            switch(mainType.ordinal()){
                case 1:
                    getWeather(subType);
                    break;
                case 0:
                    getReport(subType);
                    break;
                case 2:
                    getTimeTable(subType, string);
                    break;
                case 3:
                    getScore(subType);
                    break;
                case 4:
                    msg = "안녕하세요 저는 [애나]입니다. 무엇을 도와드릴까요?";
                    botMessgae(msg, null);
                    tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    break;
                case 5:
                    msg = "sub_MIT 팀 안드로이드 음성 지원 챗 발표를 시작 하겠습니다. 잘 부탁드립니다.";
                    botMessgae(msg, null);
                    tts.speak("SUBMIT 팀 안드로이드 음성 지원 챗 발표를 시작 하겠습니다. 잘 부탁드립니다.", TextToSpeech.QUEUE_FLUSH, null);
                    break;
                case 6:
                    msg = "sub_MIT 팀 발표를 종료합니다. 감사합니다.";
                    botMessgae(msg, null);
                    tts.speak("SUBMIT 팀 발표를 종료합니다. 감사합니다.", TextToSpeech.QUEUE_FLUSH, null);
                    break;
                case 7:
                    tts.setSpeechRate(2.8f);
                    msg = "북치기 박치기 북치기 박치기 북치기 박치기 북치기 박치기 북치기 박치기 북치기 박치기 북치기 박치기 ";
                    botMessgae(msg, null);
                    tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    break;
                case 8:
                    msg = "네 말씀하세요.";
                    botMessgae(msg, null);
                    tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    break;
                default:
                    msg = "무슨말인지 모르겠어요.";
                    botMessgae(msg, null);
                    tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    break;
            }
            tts.setSpeechRate(1.0f);
        });
    }

    private void getWeather(SubType subType){
        if(isEnableGPS)
            new WeatherDataHandler((map)->{
                String msg = (String)map.get("message");
                tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                List<ListViewItem> items = (List<ListViewItem>)map.get("listViewItems");

                botMessgae(msg, items);
            }, subType.ordinal());
        else
            Toast.makeText(this, "현재 GPS 정보가 불확실 합니다. 잠시후 다시 시도해 주세요.", Toast.LENGTH_LONG).show();
    }

    private void getReport(SubType subType){
        MainActivity.connectInterface.getStdReports(userNumber).enqueue(new Callback<RecvData<String>>() {
            @Override
            public void onResponse(Call<RecvData<String>> call, Response<RecvData<String>> response) {
                if(response.isSuccessful()) {
                    RecvData<String> data = response.body();

                    if (data.getStatus() == 0) return;

                    String msg = (String) data.getData();

                    botMessgae(msg, null);
                    tts.speak(tf_userName.getText().toString()+"님께서 해야할 과제 목록 입니다."
                            , TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onFailure(Call<RecvData<String>> call, Throwable t) {

            }
        });
    }

    private void getTimeTable(SubType subType, String str){
        Calendar cal = Calendar.getInstance();

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        DayOfWeekType dayOfWeekType = DayOfWeekType.EMPTY;

        Log.d("dayOfWeekType", str);

        dayOfWeekType = dayOfWeekType.findByType(str);

        Log.d("dayOfWeekType", "int : "+dayOfWeekType.getIntValue()+", string : "+dayOfWeekType.getStringValue());

        if(dayOfWeekType.getIntValue()==-100) {
            switch (subType.getValue()) {
                case "오늘":
                    dayOfWeek -= 2;
                    break;
                case "모레":
                    if(dayOfWeek == 6) dayOfWeek = -1;
                    if(dayOfWeek == 7) dayOfWeek = 0;
                    break;
                case "내일":
                    dayOfWeek -= 1;
                    if(dayOfWeek == 6) dayOfWeek = -1;
                    break;
                case "없음":
                    dayOfWeek -= 2;
                    break;
            }
        }else
            dayOfWeek = dayOfWeekType.getIntValue();

        Log.d("dayOfWeekType", "result dayOfWeek : "+dayOfWeek);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stdNumber", userNumber);
        map.put("day", dayOfWeek);

        MainActivity.connectInterface.getLectureTime(map).enqueue(new Callback<RecvData<LectureDatas>>() {
            @Override
            public void onResponse(Call<RecvData<LectureDatas>> call, Response<RecvData<LectureDatas>> response) {
                if(response.isSuccessful()){
                    RecvData<LectureDatas> data = response.body();
                    
                    if(data.getStatus()==1){
                        LectureDatas lectureDatas = data.getData();

                        String msg = "'"+lectureDatas.getDayofweek()+"' 요일 시간표";


                        botMessgae(msg, lectureDatas.getLectures());
                        tts.speak(msg + " 입니다.", TextToSpeech.QUEUE_FLUSH, null);
                    }else{
                        Toast.makeText(ChatActivity.this, "getLectureTime rest Fail!! server Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecvData<LectureDatas>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getScore(SubType subType){
        MainActivity.connectInterface.getTotalScore(userNumber).enqueue(new Callback<RecvData<List<StdScore>>>() {
            @Override
            public void onResponse(Call<RecvData<List<StdScore>>> call, Response<RecvData<List<StdScore>>> response) {
                if(response.isSuccessful()){
                    RecvData<List<StdScore>>  data = response.body();
                    if(data.getStatus()==0) {
                        Toast.makeText(ChatActivity.this, "getScore fail data is null", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<StdScore> stdScores = data.getData();

                    StdScore stdScore=null;
                    String msg = "";

                    if(stdScores.size()!=0){
                        stdScore = stdScores.get(0);
                        msg += tf_userName.getText().toString()+"님의 전체 학점 조회\n";
                    }else{
                        msg += "이전 학기 학점 기록이 없습니다.\n";
                    }
                    tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                    botMessgae(msg, stdScores);
                }
            }

            @Override
            public void onFailure(Call<RecvData<List<StdScore>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
