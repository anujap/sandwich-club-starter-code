package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView lblAlsoKnownAs = (TextView) findViewById(R.id.also_known_label);
        TextView lblPlaceOfOrigin = (TextView) findViewById(R.id.place_of_origin_label);
        TextView lblDescription = (TextView) findViewById(R.id.description_label);
        TextView lblIngredients = (TextView) findViewById(R.id.ingredients_label);

        TextView tvAlsoKnowAs = (TextView) findViewById(R.id.also_known_tv);
        TextView tvOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView tvDescription = (TextView) findViewById(R.id.description_tv);
        TextView tvIngredients = (TextView) findViewById(R.id.ingredients_tv);

        // AlsoKnowAs
        if(sandwich.getAlsoKnownAs().isEmpty()) {
            lblAlsoKnownAs.setVisibility(View.GONE);
            tvAlsoKnowAs.setVisibility(View.GONE);
        }
        else {
            lblAlsoKnownAs.setVisibility(View.VISIBLE);
            tvAlsoKnowAs.setVisibility(View.VISIBLE);
            tvAlsoKnowAs.setText(TextUtils.join(System.getProperty("line.separator"), sandwich.getAlsoKnownAs()));
        }

        // PlaceOfOrigin
        if(sandwich.getPlaceOfOrigin().isEmpty()) {
            lblPlaceOfOrigin.setVisibility(View.GONE);
            tvOrigin.setVisibility(View.GONE);
        }
        else {
            lblPlaceOfOrigin.setVisibility(View.VISIBLE);
            tvOrigin.setVisibility(View.VISIBLE);
            tvOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        // Description
        if(sandwich.getDescription().isEmpty()) {
            lblDescription.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
        }
        else {
            lblDescription.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(sandwich.getDescription());
        }

        // Ingredients
        if(sandwich.getIngredients().isEmpty()) {
            lblIngredients.setVisibility(View.GONE);
            tvIngredients.setVisibility(View.GONE);
        }
        else {
            lblIngredients.setVisibility(View.VISIBLE);
            tvIngredients.setVisibility(View.VISIBLE);
            tvIngredients.setText(TextUtils.join(System.getProperty("line.separator"), sandwich.getIngredients()));
        }
    }

    // TextUtils.join - Reference - https://stackoverflow.com/questions/25727993/android-logging-strings-with-newline-character-or-br
}
