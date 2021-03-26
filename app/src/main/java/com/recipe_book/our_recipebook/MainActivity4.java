package com.recipe_book.our_recipebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity4 extends AppCompatActivity {

    private EditText Title,Type,Prep,Serves,Time,Meal,Difficulty;
    String ImageUrl;Uri uri;
    ImageView iv;
    String key,oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String title,type,prep,serves,time,meal,difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toast.makeText(MainActivity4.this,"Please make sure you re-upload the picture",Toast.LENGTH_SHORT).show();

        iv = (ImageView)findViewById(R.id.addimage);
        Title = (EditText)findViewById(R.id.Title);
        Type = (EditText)findViewById(R.id.Recipe);
        Prep = (EditText)findViewById(R.id.Prep);
        Serves = (EditText)findViewById(R.id.Serves);
        Time = (EditText)findViewById(R.id.Time);
        Meal = (EditText)findViewById(R.id.Meal);
        Difficulty = (EditText)findViewById(R.id.Level);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            Glide.with(MainActivity4.this)
                    .load(bundle.getString("oldimageUrl"))
                    .into(iv);
            Title.setText(bundle.getString("titleKey"));
            Type.setText(bundle.getString("recipeKey"));
            Serves.setText(bundle.getString("servesKey"));
            Meal.setText(bundle.getString("mealKey"));
            Prep.setText(bundle.getString("prepKey"));
            Time.setText(bundle.getString("timeKey"));
            Difficulty.setText(bundle.getString("levelKey"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldimageUrl"); }
        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe").child(key); }


    public void btnSelectImage(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            iv.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();

    }



    public void btnUpdateRecipe(View view) {

        title = Title.getText().toString().trim();
        type = Type.getText().toString().trim();
        meal = Meal.getText().toString();
        serves = Serves.getText().toString().trim();
        prep = Prep.getText().toString().trim();
        difficulty = Difficulty.getText().toString();
        time = Time.getText().toString();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Recipe Updating....");
        pd.show();
        storageReference = FirebaseStorage.getInstance()
                .getReference().child("Recipe Image").child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                ImageUrl = urlImage.toString();
                uploadRecipe();
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
            }
        });


    }

    public void uploadRecipe(){
        recipe Recipe = new recipe(title, serves,difficulty,meal,prep,type,time,oldImageUrl);
        databaseReference.setValue(Recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference storageReferenceNew  = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                storageReferenceNew.delete();
                Toast.makeText(MainActivity4.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });




    }

}