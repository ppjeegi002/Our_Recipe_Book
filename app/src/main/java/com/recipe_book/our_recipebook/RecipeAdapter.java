package com.recipe_book.our_recipebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

     private Context mContext;
     private List<recipe> myrecipeList;
     private int lp = -1;

        public RecipeAdapter(Context mContext, List<recipe> myrecipeList) {
                this.mContext = mContext;
                this.myrecipeList = myrecipeList;
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
               View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);

                return new RecipeViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
            Glide.with(mContext)
                    .load(myrecipeList.get(i).getImage()).into(recipeViewHolder.imageView);
                recipeViewHolder.mTitle.setText(myrecipeList.get(i).getName());
                recipeViewHolder.mMeal.setText(myrecipeList.get(i).getMeal());
                recipeViewHolder.mDifficulty.setText(myrecipeList.get(i).getDifficulty());
                recipeViewHolder.mServes.setText(myrecipeList.get(i).getServes());
                recipeViewHolder.mTime.setText(myrecipeList.get(i).getTime());
                recipeViewHolder.mPrep.setText(myrecipeList.get(i).getIngredients());
                recipeViewHolder.mType.setText(myrecipeList.get(i).getRecipe());
                recipeViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(mContext,MainActivity3.class);
                        intent.putExtra("Image",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getImage());
                        intent.putExtra("Title",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getName());
                        intent.putExtra("Meal",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getMeal());
                        intent.putExtra("Level",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getDifficulty());
                        intent.putExtra("Serves",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getServes());
                        intent.putExtra("Time",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getTime());
                        intent.putExtra("Ingredients",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getIngredients());
                        intent.putExtra("Description",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getRecipe());
                        intent.putExtra("keyValue",myrecipeList.get(recipeViewHolder.getAdapterPosition()).getKey());
                        mContext.startActivity(intent);
                    }
                });
                Animation(recipeViewHolder.itemView,i);
        }
        public void Animation(View viewToAnimate,int position){
            if(position>lp){
                ScaleAnimation animation=new  ScaleAnimation(0.0f,1.0f,0.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
           animation.setDuration(1500);
            viewToAnimate.startAnimation(animation);
            lp = position;}

        }
        @Override
        public int getItemCount() {
                return myrecipeList.size();
        }

    public void findList(ArrayList<recipe> findList) {
            myrecipeList= findList;
            notifyDataSetChanged();
    }
}
class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView mTitle, mPrep, mServes, mType, mTime,mMeal,mDifficulty;

        CardView mCardView;

        public RecipeViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.idIVCourseImage);
                mTitle = itemView.findViewById(R.id.idTVCourseName);
                mMeal = itemView.findViewById(R.id.idTVCourseMeal);
                mDifficulty = itemView.findViewById(R.id.idTVCourseDifficulty);
                mPrep = itemView.findViewById(R.id.idTVCourseIngr);
                mServes = itemView.findViewById(R.id.idTVCourseServes);
                mType = itemView.findViewById(R.id.idTVCourseRecipe);
                mTime = itemView.findViewById(R.id.idTVCourseTime);
                mCardView = itemView.findViewById(R.id.cv);


        }






}