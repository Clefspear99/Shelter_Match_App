package lsj.softwareeng.sheltermatch;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import lsj.softwareeng.sheltermatch.ui.browse.BrowseFragment;
import lsj.softwareeng.sheltermatch.ui.fav.FavFragment;


public class apiAccess extends AsyncTask<JsonObject, Void, JsonObject> {

    String base = "https://cs.uww.edu/~strickerja11/shelterAPI/shelterMatching/animal/";
    String searchAddress = base + "currentAnimal/search.php";
    String favsAddress = base + "currentAnimal/searchFavorites.php";
    String response = "";

    private ArrayList<PetObject> petArray;
    private boolean isSearch;
    private ArrayList<Integer> favsIn;

    private FavFragment favFragment;
    private BrowseFragment browseFragment;

    private JsonObject inputJson;
    private int pageNum=0;

    static Hashtable<Integer, PetObject> masterHashTable = new Hashtable<>();



    public apiAccess search(BrowseFragment browseFragment, JsonObject inputJson) {
        this.browseFragment = browseFragment;
        this.inputJson = inputJson;
        isSearch = true;

        return this;
    }

    public apiAccess getFavs(FavFragment favFragment, ArrayList<Integer> favsIn, int pageNum) {
        this.favFragment = favFragment;
        this.favsIn = favsIn;
        this.pageNum=pageNum;
        isSearch = false;
        inputJson = new JsonObject();
        inputJson.add("pageNumber", new JsonPrimitive(Integer.toString(pageNum)));

        JsonArray favArray= new JsonArray();
        for (Integer curr : favsIn)
            favArray.add(curr);
        inputJson.add("id", favArray);

        return this;
    }


    protected JsonObject doInBackground(JsonObject... params) {

        URL url;
        HttpURLConnection conn;

        BufferedOutputStream out;
        InputStream in;

        JsonObject outJSON = new JsonObject();
        String message;

        String response;

        GsonBuilder Gbuild = new GsonBuilder();
        Gbuild.excludeFieldsWithoutExposeAnnotation();
        Gson g = Gbuild.create();

        String addr;
        if (isSearch)
            addr = searchAddress;
        else
            addr = favsAddress;


        try {


            message=inputJson.toString();


            url = new URL(addr);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);


            conn.connect();

            out = new BufferedOutputStream(conn.getOutputStream());
            out.write(message.getBytes());
            out.flush();
            out.close();


            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            response = streamToString(in);

            outJSON = JsonParser.parseString(response).getAsJsonObject();

            return outJSON;
        } catch (Exception except) {
            except.printStackTrace();
        }

        return outJSON;
    }


    protected void onPostExecute(JsonObject result) {
        JsonArray animals;
        if(!isSearch)
            animals = result.get("currentAnimal").getAsJsonArray();
        else
            animals = result.get("records").getAsJsonArray();
        int length = animals.size();
        ArrayList<PetObject> tempPetArr = new ArrayList<>();
        Gson gson = new Gson();
        JsonElement pulledFromArray;
        PetObject petObject;

        if(isSearch)
            gson = new Gson();

        for (int i = 0; i < length; i++) {
            pulledFromArray = animals.get(i);
            petObject = masterHashTable.get(pulledFromArray.getAsJsonObject().get("id").getAsInt());
            if (petObject == null) {
                petObject = PetObject.getFromJson(pulledFromArray.getAsJsonObject());
            }
            if (!isSearch) {
                petObject.setFav(true);
                favFragment.petsToShow.add(petObject);
                //favFragment.addPets(1);
            }
            else{
                browseFragment.petsToShow.add(petObject);
                //browseFragment.addPets(1);
            }
        }


    }



    private String streamToString(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();

    }

}

