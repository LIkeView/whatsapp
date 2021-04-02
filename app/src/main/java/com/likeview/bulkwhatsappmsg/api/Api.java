package com.likeview.bulkwhatsappmsg.api;

//import com.aswdc.archdaily.api.models.DefaultResponse;
//import net.simplifiedcoding.retrofitandroidtutorial.models.LoginResponse;
//import net.simplifiedcoding.retrofitandroidtutorial.models.UsersResponse;


import com.likeview.bulkwhatsappmsg.models.ApiResponse;
import com.likeview.bulkwhatsappmsg.models.ApiResponseWhitoutResData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> login(@Field("email_or_phones") String email, @Field("password") String password);



}
