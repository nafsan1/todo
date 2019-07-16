package com.example.todo.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.adapter.TodoAdapter;
import com.example.todo.api.BaseApiService;
import com.example.todo.api.UtilsAPI;
import com.example.todo.controller.TodoController;
import com.example.todo.model.MTodo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment implements TodoController.TodoView {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public static final String ADD_DATA = "Add_Data";
    public static final String EDIT_DATA = "Edit_Data";
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.image_notodo)
    ImageView imageNotodo;
    private TodoController controller;

    public TodoFragment() {
        // Required empty public constructor
    }

    private TodoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this, view);
        controller = new TodoController(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);

        controller.showData();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                BaseApiService mApiService = UtilsAPI.getApiService();

                mApiService.deleteTodo(adapter.getIdTodo(viewHolder.getAdapterPosition())).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {
                            try {
                                if (response.code() == 200) {
                                    Toast.makeText(getActivity(), "Delete Succesfull", Toast.LENGTH_SHORT).show();
                                    controller.showData();
                                }else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TodoFragment", t.getMessage());
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MTodo obj, int position) {

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    AddEditFragment currentFragment = new AddEditFragment();
                    currentFragment.setTypeData(EDIT_DATA);
                    currentFragment.setModel(obj);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_main, currentFragment, AddEditFragment.class.getSimpleName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }


    @Override
    public void getData(List<MTodo> list) {
        if (list.size() == 0){
            imageNotodo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            imageNotodo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter.setTodo(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void message(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
