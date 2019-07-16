package com.example.todo.controller;

import android.util.Log;
import android.widget.Toast;

import com.example.todo.api.BaseApiService;
import com.example.todo.api.UtilsAPI;
import com.example.todo.model.MTodo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoController {
    private TodoView view;
    public static final String URL = "https://todo-backend-restify-redux.herokuapp.com";
    private List<MTodo> list = new ArrayList<>();

    public TodoController(TodoView view) {
        this.view = view;
    }

    public void showData() {
        view.showProgress();
        BaseApiService mApiService = UtilsAPI.getApiService();
        mApiService.getTodo(URL).enqueue(new Callback<List<MTodo>>() {
            @Override
            public void onResponse(Call<List<MTodo>> call, Response<List<MTodo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    view.getData(list);
                    view.hideProgress();
                } else {
                    view.message("Error");
                    view.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<List<MTodo>> call, Throwable t) {
                Log.d("TodoFragment", t.getMessage());
                view.message("Error");
                view.hideProgress();
            }
        });
    }


    public interface TodoView {
        void getData(List<MTodo> list);
        void showProgress();
        void hideProgress();
        void message(String message);
    }
}
