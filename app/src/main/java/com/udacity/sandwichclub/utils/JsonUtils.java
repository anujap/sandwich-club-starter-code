package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwichObj = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);

            // sandwich name
            JSONObject jsonObjectName = jsonObject.getJSONObject(SANDWICH_NAME);
            String strMainName = jsonObjectName.getString(SANDWICH_MAIN_NAME);

            JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray(SANDWICH_ALSO_KNOWN_AS);
            List<String> alsoKnownAsList = convertArrayToList(jsonArrayAlsoKnownAs);

            // place of origin
            String strPlaceOfOrigin = jsonObject.getString(SANDWICH_PLACE_OF_ORIGIN);

            // description
            String strDescription = jsonObject.getString(SANDWICH_DESCRIPTION);

            // image
            String strImage = jsonObject.getString(SANDWICH_IMAGE);

            // ingredients
            JSONArray jsonArrayIngredients = jsonObject.getJSONArray(SANDWICH_INGREDIENTS);
            List<String> ingredientsList = convertArrayToList(jsonArrayIngredients);

            sandwichObj.setMainName(strMainName);
            sandwichObj.setAlsoKnownAs(alsoKnownAsList);
            sandwichObj.setPlaceOfOrigin(strPlaceOfOrigin);
            sandwichObj.setDescription(strDescription);
            sandwichObj.setImage(strImage);
            sandwichObj.setIngredients(ingredientsList);


        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return sandwichObj;
    }

    // function called to convert JsonArray to arraylist<String>
    private static List<String> convertArrayToList(JSONArray jsonArray) {

        List<String> arrayList = new ArrayList<String>();

        if(jsonArray != null) {
            int length = jsonArray.length();

            if(length > 0) {
                for (int i=0; i<length; i++) {
                    try {
                        arrayList.add(jsonArray.getString(i));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        return arrayList;
    }
}
