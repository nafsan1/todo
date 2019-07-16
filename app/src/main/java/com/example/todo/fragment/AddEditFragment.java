package com.example.todo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.todo.MainActivity;
import com.example.todo.R;
import com.example.todo.controller.AddEditController;
import com.example.todo.model.MTodo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.todo.fragment.TodoFragment.EDIT_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment implements AddEditController.AddEditView {


    @BindView(R.id.edtx_title)
    EditText edtxTitle;
    @BindView(R.id.edtx_body)
    EditText edtxBody;
    @BindView(R.id.btn_add_edit)
    Button btnAddEdit;
    @BindView(R.id.progress)
    ProgressBar progress;

    public AddEditFragment() {
        // Required empty public constructor
    }

    private AddEditController controller;
    private String typeData = "Add_Data";
    private MTodo model;

    public void setTypeData(String typeData) {
        this.typeData = typeData;
    }

    public void setModel(MTodo model) {
        this.model = model;
    }

    private String URL = "https://todo-backend-restify-redux.herokuapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        ButterKnife.bind(this, view);
        controller = new AddEditController(this);
        if (typeData.equals(EDIT_DATA)) {
            setEditData();
        }
        return view;
    }

    private void setEditData() {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Edit Todo");
        edtxTitle.setText(model.getTitle());
        edtxBody.setText(model.getOrder());
    }


    @OnClick(R.id.btn_add_edit)
    public void onViewClicked() {
        if (edtxTitle.getText().toString().isEmpty()) {
            edtxTitle.setError("Field Empty");
        } else if (edtxBody.getText().toString().isEmpty()) {
            edtxBody.setError("Field Empty");
        } else {
            if (typeData.equals(EDIT_DATA)) {
                controller.updateData(model.getId(), edtxTitle.getText().toString(),
                        edtxBody.getText().toString());
            } else {
                controller.inputData(edtxTitle.getText().toString(),
                        edtxBody.getText().toString());
            }
        }
    }

    @Override
    public void responSuccesInput() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
    }

    @Override
    public void responSuccesEdit() {
        getActivity().onBackPressed();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
