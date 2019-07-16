package com.example.todo.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.MainActivity;
import com.example.todo.api.BaseApiService;
import com.example.todo.api.UtilsAPI;
import com.example.todo.model.MTodo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditController {
    private AddEditView view;



    public AddEditController(AddEditView view) {
        this.view = view;
    }

    public void updateData(String id, String title, String description) {
        view.showProgress();
        Map<String, String> fields = new HashMap<>();
        fields.put("title", title);
        fields.put("order", description);
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.patchTodo(id, fields).enqueue(new Callback<MTodo>() {
            @Override
            public void onResponse(Call<MTodo> call, Response<MTodo> response) {
                if (response.isSuccessful()) {
                    try {
                        view.responSuccesEdit();
                        view.message("Data save succesfully");
                        view.hideProgress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MTodo> call, Throwable t) {
                Log.e("EditData", t.getMessage());
                view.message("Error");
            }
        });

    }

    public void inputData(String title, String description) {
        view.showProgress();
        Map<String, String> fields = new HashMap<>();
        fields.put("title", title);
        fields.put("order", description);

        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.createTodo(fields).enqueue(new Callback<MTodo>() {
            @Override
            public void onResponse(Call<MTodo> call, Response<MTodo> response) {
                if (response.isSuccessful()) {
                    try {
                        view.responSuccesInput();
                        view.message("Data save succesfully");
                        view.hideProgress();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                   view.message("error");
                    view.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<MTodo> call, Throwable t) {
                view.message("Error");
                view.hideProgress();
            }
        });
    }


    public interface AddEditView {
        void responSuccesInput();
        void responSuccesEdit();

        void showProgress();

        void hideProgress();

        void message(String message);
    }
}
