package com.example.kosmo_project.httpcommunication;

import com.example.kosmo_project.vo.LectureDatas;
import com.example.kosmo_project.vo.RecvData;
import com.example.kosmo_project.vo.StdScore;
import com.example.kosmo_project.vo.User;
import com.example.kosmo_project.vo.WeatherLocation;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ConnectInterface {
    @GET("project/rest/api/v1.0/connect_check")
    Call<RecvData> connect();

    @FormUrlEncoded
    @POST("project/rest/api/v1.0/login/student")
    Call<RecvData<User>> requestLogin(@Field("userNumber") String userNumber,
                                      @Field("userPassword") String userPassword);

    @GET("project/rest/api/v1.0/getLocation")
    Call<RecvData<WeatherLocation>> getLocation(@QueryMap Map<String, String> map);


    @GET("project/rest/api/v1.0/getLectureTime")
    Call<RecvData<LectureDatas>> getLectureTime(@QueryMap Map<String, Object> map);

    @POST("project/rest/api/v1.0/getStdReports")
    Call<RecvData<String>> getStdReports(@Query("stdNumber") String stdNumber);

    @POST("project/rest/api/v1.0/getTotalScore")
    Call<RecvData<List<StdScore>>> getTotalScore(@Query("stdNumber") String stdNumber);
}
