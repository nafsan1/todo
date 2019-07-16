package com.example.todo.api;

public class UtilsAPI {



    public static final String BASE_ROOT_URL = "https://todo-backend-restify-redux.herokuapp.com";

    /*public static final String BASE_ROOT_URL = "https://jsonplaceholder.typicode.com/";*/


    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_ROOT_URL).create(BaseApiService.class);
    }

}
