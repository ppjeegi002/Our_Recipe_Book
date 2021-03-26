package com.recipe_book.our_recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity3 extends AppCompatActivity {
    TextView Title, Meal, Level, Serves, Time, Recipe, Prep;
    ImageView Rpic;
    String key = "";
    String imgUrl = "";
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity3.this, Recipelist.class);
                startActivity(in);
            }
        });
        Title = findViewById(R.id.Heading);
        Meal = findViewById(R.id.mMeal);
        Level = findViewById(R.id.mDifficult);
        Serves = findViewById(R.id.mServes);
        Time = findViewById(R.id.mTime);
        Recipe = findViewById(R.id.mRecipe);
        Prep = findViewById(R.id.mIngredients);
        Rpic = findViewById(R.id.imageView);


        Prep.setMovementMethod(new ScrollingMovementMethod());
        Recipe.setMovementMethod(new ScrollingMovementMethod());
        Bundle mBundle = getIntent().getExtras();


        if (mBundle != null) {
            Title.setText(mBundle.getString("Title"));
            Glide.with(this).load(mBundle.getString("Image")).into(Rpic);
            Meal.setText(mBundle.getString("Meal"));
            Level.setText(mBundle.getString("Level"));
            Serves.setText(mBundle.getString("Serves"));
            Time.setText(mBundle.getString("Time"));
            Prep.setText(mBundle.getString("Ingredients"));
            Recipe.setText(mBundle.getString("Description"));
            key = mBundle.getString("keyValue");
            imgUrl = mBundle.getString("Image");
        }
    }

    public void btnDelete(View view) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipe");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(imgUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child(key).removeValue();
                Toast.makeText(MainActivity3.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Recipelist.class));
                finish();
            }
        });
    }

    public void btnUpdate(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity4.class).putExtra("titleKey", Title.getText().toString())
                .putExtra("mealKey", Meal.getText().toString()).putExtra("levelKey", Level.getText().toString())
                .putExtra("servesKey", Serves.getText().toString()).putExtra("timeKey", Time.getText().toString())
                .putExtra("prepKey", Prep.getText().toString()).putExtra("recipeKey", Recipe.getText().toString())
                .putExtra("oldImageUrl", imgUrl).putExtra("key", key));
    }

}