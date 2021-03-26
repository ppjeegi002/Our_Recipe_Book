package com.recipe_book.our_recipebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Calendar;


public class MainActivity2 extends AppCompatActivity {
    private EditText Title;
    private EditText Type;
    private EditText Prep;
    private EditText Serves;
    private EditText Time;
    private ImageView back;
    private Button buttonadd;
    private EditText Meal;
    private EditText Difficulty;
    String ImageUrl;
    final int REQUEST_CODE_GALLERY = 111;

    Uri uri;
    ImageView iv;
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        buttonadd = findViewById(R.id.buttonadd);
       buttonadd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ActivityCompat.requestPermissions(MainActivity2.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
           }
       });

       back = findViewById(R.id.back);

        iv = (ImageView)findViewById(R.id.addimage); Title = findViewById(R.id.Title); Type= findViewById(R.id.Recipe); Prep= findViewById(R.id.Prep);Serves= findViewById(R.id.Serves);Time= findViewById(R.id.Time);Meal= findViewById(R.id.Meal);Difficulty= findViewById(R.id.Level);

       Title = findViewById(R.id.Title);
       Title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus)
                   Title.setHint("");
               else
                   Title.setHint("Recipe name:");
           }
       });


        back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent in = new Intent(MainActivity2.this,MainActivity.class);
              startActivity(in);
          }
      });


       }


    public  void uploadImage(){
    StorageReference  storageReference = FirebaseStorage.getInstance().getReference().child("Recipe Image").child(uri.getLastPathSegment());
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Recipe Uploading...");
        pd.show();
    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete());
            Uri urlImage = uriTask.getResult();
            ImageUrl= urlImage.toString();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"You have not given permission",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null){ uri = data.getData();
            iv.setImageURI(uri); }
        else{
            Toast.makeText(this,"You have not selected an image",Toast.LENGTH_SHORT).show();
        }

    }


    public void btnUploadRecipe(View view) {
    uploadImage();


    }


@RequiresApi(api = Build.VERSION_CODES.N)
public void uploadRecipe(){
    String CurrentDateTime= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    recipe RecipeData = new recipe(Title.getText().toString(),Serves.getText().toString(),Difficulty.getText().toString(),Meal.getText().toString(),Prep.getText().toString(),
            Type.getText().toString(),Time.getText().toString(),ImageUrl);



    FirebaseDatabase.getInstance().getReference("Recipe").child(CurrentDateTime).setValue(RecipeData).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Toast.makeText(MainActivity2.this,"Recipe Uploaded",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(MainActivity2.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
    });
}}
