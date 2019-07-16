package com.example.todo.api;


import com.example.todo.model.MTodo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface BaseApiService {


   /* @FormUrlEncoded
    @POST("unbk_android_admin/get_member_credit_unbk_2019")
    Call<MPilihVoucherResponse> getMemberCredit(@Field("member_id") String member_id);

    @FormUrlEncoded
    @POST("unbk_android_store/get_pilih_paket_simulasi")
    Call<MPilihPaketResponse> getPaket(@Field("cube_code") String cube_code);

    @FormUrlEncoded
    @POST("unbk_android_store/get_pilih_unbk")
    Call<MPilihUnbkResponse> getPilihUnbk(@Field("cube_code") String cube_code,
                                          @Field("paket_id") String paket_id);

    @FormUrlEncoded
    @POST("unbk_android_store/get_soal_unbk")
    Call<MSoalResponse> getPilihSoal(@Field("level_id") String level_id,
                                     @Field("kelompok_id") String kelompok_id,
                                     @Field("subject_id") String subject_id,
                                     @Field("paket_id") String paket_id,
                                     @Field("penulis_id") String penulis_id);


    @FormUrlEncoded
    @POST("unbk_android_admin/get_loginUser_baru")
    Call<MLoginSucces> getLoginUser(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("device_token") String device_token,
                                    @Field("device_name") String device_name);

    @GET("unbk_android_store/get_spinner_sekolah_baru")
    Call<MJenjangResponse> getJenjang();*/

    @GET
    Call<List<MTodo>> getTodo(@Url String url);

    /* @FormUrlEncoded
     @POST
     Call<MTodo> createTodo(
             @Field("title") String title,
             @Field("order") String order);*/
    @Headers("Content-Type: application/json")
    @POST("/")
    Call<MTodo> createTodo(
            @Body Map<String, String> fields);

    @Headers("Content-Type: application/json")
    @PATCH("/{id}")
    Call<MTodo> patchTodo(
                         @Path("id") String id,
                         @Body Map<String, String> fields);
    @Headers("Content-Type: application/json")
    @DELETE("/{id}")
    Call<Void> deleteTodo(
            @Path("id") String id);
}
