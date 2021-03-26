package com.recipe_book.our_recipebook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Recipelist extends AppCompatActivity {
    RecyclerView mRv;
    List<recipe> myrecipeList;
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    EditText searchBar;
    public ValueEventListener eventListener;
    RecipeAdapter recipeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        searchBar = (EditText) findViewById(R.id.Search);
        mRv = (RecyclerView) findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Recipelist.this, 1);
        mRv.setLayoutManager(gridLayoutManager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data..");

        myrecipeList = new ArrayList<>();


       recipeAdapter = new RecipeAdapter(Recipelist.this, myrecipeList);
        mRv.setAdapter(recipeAdapter);


        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myrecipeList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    recipe Recipe = itemSnapshot.getValue(recipe.class);
                    Recipe.setKey(itemSnapshot.getKey());
                    myrecipeList.add(Recipe);
                }
                recipeAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                find(s.toString());
            }
        });
    }

    private void find(String text) {
        ArrayList<recipe> findList = new ArrayList<>();
        for (recipe item : myrecipeList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                findList.add(item);
            }
        }
        recipeAdapter.findList(findList);
    }
}