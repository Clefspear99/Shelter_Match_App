package lsj.softwareeng.sheltermatch;


import android.graphics.Bitmap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;

import lsj.softwareeng.sheltermatch.ui.fav.FavFragment;

//Concrete PetObject Builder class
class PetObjectBuilder{
    int idBuilder=-1;
    String nameBuilder="Unnamed";
    String typeBuilder="unknown";
    String sexBuilder="unknown";
    String ageBuilder="unknown";
    String[] breedBuilder=new String[]{"unknown"};
    ArrayList<String> imgURLsBuilder;
    String cityBuilder="unknown";
    String stateBuilder="unknown";
    String countryBuilder="unknown";
    int postalCodeBuilder=00000;
    double locationLatBuilder=0;
    double locationLonBuilder=0;
    String timeStampBuilder="";
    boolean favBuilder=false;

    static int genID=-2;

    public int getIdBuilder() {
        return idBuilder;
    }

    public void setIdBuilder(int idBuilder) {
        this.idBuilder = idBuilder;
    }

    public String getNameBuilder() {
        return nameBuilder;
    }

    public void setNameBuilder(String nameBuilder) {
        this.nameBuilder = nameBuilder;
    }

    public String getTypeBuilder() {
        return typeBuilder;
    }

    public void setTypeBuilder(String typeBuilder) {
        this.typeBuilder = typeBuilder;
    }

    public String getSexBuilder() {
        return sexBuilder;
    }

    public void setSexBuilder(String sexBuilder) {
        this.sexBuilder = sexBuilder;
    }

    public String getAgeBuilder() {
        return ageBuilder;
    }

    public void setAgeBuilder(String ageBuilder) {
        this.ageBuilder = ageBuilder;
    }

    public String[] getBreedBuilder() {
        return breedBuilder;
    }

    public void setBreedBuilder(String[] breedBuilder) {
        this.breedBuilder = breedBuilder;
    }

    public ArrayList<String> getImgURLsBuilder() {
        return imgURLsBuilder;
    }

    public void setImgURLsBuilder(ArrayList<String> imgURLsBuilder) {
        this.imgURLsBuilder = imgURLsBuilder;
    }

    public String getCityBuilder() {
        return cityBuilder;
    }

    public void setCityBuilder(String cityBuilder) {
        this.cityBuilder = cityBuilder;
    }

    public String getStateBuilder() {
        return stateBuilder;
    }

    public void setStateBuilder(String stateBuilder) {
        this.stateBuilder = stateBuilder;
    }

    public String getCountryBuilder() {
        return countryBuilder;
    }

    public void setCountryBuilder(String countryBuilder) {
        this.countryBuilder = countryBuilder;
    }

    public int getPostalCodeBuilder() {
        return postalCodeBuilder;
    }

    public void setPostalCodeBuilder(int postalCodeBuilder) {
        this.postalCodeBuilder = postalCodeBuilder;
    }

    public double getLocationLatBuilder() {
        return locationLatBuilder;
    }

    public void setLocationLatBuilder(double locationLatBuilder) {
        this.locationLatBuilder = locationLatBuilder;
    }

    public double getLocationLonBuilder() {
        return locationLonBuilder;
    }

    public void setLocationLonBuilder(double locationLonBuilder) {
        this.locationLonBuilder = locationLonBuilder;
    }

    public String getTimeStampBuilder() {
        return timeStampBuilder;
    }

    public void setTimeStampBuilder(String timeStampBuilder) {
        this.timeStampBuilder = timeStampBuilder;
    }

    public boolean isFavBuilder() {
        return favBuilder;
    }

    public void setFavBuilder(boolean favBuilder) {
        this.favBuilder = favBuilder;
    }

    public static int getGenID() {
        return genID;
    }

    public static void setGenID(int genID) {
        PetObjectBuilder.genID = genID;
    }

    public PetObjectBuilder Id(int id) {
        this.idBuilder = id;
        return this;
    }

    public PetObjectBuilder Name(String name) {
        this.nameBuilder = name;
        return this;
    }

    public PetObjectBuilder Type(String type) {
        this.typeBuilder = type;
        return this;
    }


    public PetObjectBuilder Sex(String sex) {
        this.sexBuilder = sex;
        return this;
    }


    public PetObjectBuilder Age(String age) {
        this.ageBuilder = age;
        return this;
    }

    public PetObjectBuilder Breed(String[] breed) {
        this.breedBuilder = breed;
        return this;
    }

    public PetObjectBuilder ImgURLs(ArrayList<String> imgURLs) {
        this.imgURLsBuilder = imgURLs;
        return this;
    }

    public PetObjectBuilder City(String city) {
        this.cityBuilder = city;
        return this;
    }

    public PetObjectBuilder State(String state) {
        this.stateBuilder = state;
        return this;
    }

    public PetObjectBuilder Country(String country) {
        this.countryBuilder = country;
        return this;
    }

    public PetObjectBuilder postalCode(int postalCode) {
        this.postalCodeBuilder = postalCode;
        return this;
    }

    public PetObjectBuilder LocationLat(double locationLat) {
        this.locationLatBuilder = locationLat;
        return this;
    }

    public PetObjectBuilder locationLon(double locationLon) {
        this.locationLonBuilder = locationLon;
        return this;
    }

    public PetObjectBuilder timeStamp(String timeStamp) {
        this.timeStampBuilder = timeStamp;
        return this;
    }

    public PetObjectBuilder Fav(boolean fav) {
        this.favBuilder = fav;
        return this;
    }

    public PetObjectBuilder addImageURL(String in){
        if(imgURLsBuilder==null)
            imgURLsBuilder=new ArrayList<>();
        imgURLsBuilder.add(in);
        return this;

    }

    public PetObject build() {
        if(imgURLsBuilder==null)
            imgURLsBuilder=new ArrayList<>();
        if(idBuilder==-1){
            idBuilder=genID;
            genID--;
        }
        return new PetObject(idBuilder, nameBuilder, typeBuilder, sexBuilder, ageBuilder, breedBuilder, imgURLsBuilder, cityBuilder, stateBuilder, countryBuilder, postalCodeBuilder, locationLatBuilder, locationLonBuilder, timeStampBuilder, favBuilder);
    }
}


//TODO Assign pet ID when not given
public class PetObject {

int id;
String name;
String type;
String sex;
String age;
String[] breed;
String city;
String state;
String country;
int postalCode;
double locationLat;
double locationLon;
String timeStamp;


ArrayList<Bitmap> images;
ArrayList<String> imgURLs;
Boolean fav=false;
boolean allImagesLoaded=false;

PetCardFrag petCardFragBrowse;
PetCardFrag petCardFragFav;




    public void addRemoveFromFav(MainActivity ma){

        FavFragment favFragment = ma.getFavFrag();
        petCardFragBrowse.changeFavColor();
        if(this.fav){
            if(petCardFragFav==null) {
                petCardFragFav = new PetCardFrag(this, ma);
                favFragment.addToFavs(petCardFragFav);
            }
            else{
                favFragment.addToFavsExisting(petCardFragFav);

            }
            //I don't need the following command. If I call it then it tires to change the the color
            //of a button that doesn't exist yet. Instead the color will be set when the button does exist
            //petCardFragFav.changeFavColor();


        }
        else{
            favFragment.removeFromFavs(petCardFragFav);
        }
    }


    public PetObject(int id, String name, String type, String sex, String age, String[] breed, ArrayList<String> imgURLs, String city, String state, String country, int postalCode, double locationLat, double locationLon, String timeStamp, boolean fav) {
        this.id = id;

        this.name = name;
        this.type = type;
        this.sex = sex;
        this.age = age;
        this.breed = breed;
        this.imgURLs = imgURLs;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.timeStamp = timeStamp;
        this.fav = fav;
        this.images = new ArrayList<>();
    }

    public PetObject(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String[] getBreed() {
        return breed;
    }

    public void setBreed(String[] breed) {
        this.breed = breed;
    }


    public ArrayList<String> getImgURLs() {
        return this.imgURLs;
    }

    public void setImgURLs(ArrayList<String> imgURLs) {
        this.imgURLs = imgURLs;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getpostalCode() {
        return postalCode;
    }

    public void setpostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double locationLon) {
        this.locationLon = locationLon;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    public PetCardFrag getPetCardFragBrowse() {
        return petCardFragBrowse;
    }

    public void setPetCardFragBrowse(PetCardFrag petCardFragBrowse) {
        this.petCardFragBrowse = petCardFragBrowse;
    }

    public PetCardFrag getPetCardFragFav() {
        return petCardFragFav;
    }

    public void setPetCardFragFav(PetCardFrag petCardFragFav) {
        this.petCardFragFav = petCardFragFav;
    }



    public static ArrayList<PetObject> genPets(int count){
        ArrayList<PetObject> pets = new ArrayList<>();
        PetObject tempPet;

        for(int i =0; i<count; i++){
            tempPet=new PetObjectBuilder().Name("Charlie").Type("Dog").Sex("Male").Age("Young")
                    .Breed(new String[] {"HotDog"}).City("Whitewater").State("WI").Country("US").postalCode(53190).timeStamp("2021-03-31 18:56:12").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/4/?bust=1611963715").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/1/?bust=1611233423").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/3/?bust=1611233547").build();
            pets.add(tempPet);
        }

        return pets;
    }

    public static PetObject getFromJson(JsonObject in){
        PetObjectBuilder out = new PetObjectBuilder();


        if(in.has("id"))
            out.setIdBuilder(in.get("id").getAsInt());


        if(in.has("name"))
            out.setNameBuilder(in.get("name").getAsString());

        if(in.has("type"))
            out.setTypeBuilder(in.get("type").getAsString());

        if(in.has("age"))
            out.setAgeBuilder(in.get("age").getAsString());
        if(in.has("sex"))
            out.setSexBuilder(in.get("sex").getAsString());
        if(in.has("age"))
            out.setAgeBuilder(in.get("age").getAsString());
        if(in.has("city"))
            out.setCityBuilder(in.get("city").getAsString());
        if(in.has("state"))
            out.setStateBuilder(in.get("state").getAsString());
        if(in.has("country"))
            out.setCountryBuilder(in.get("country").getAsString());
        if(in.has("timeStamp"))
            out.setTimeStampBuilder(in.get("timeStamp").getAsString());

        if(in.has("locationLat"))
            out.setLocationLatBuilder(in.get("locationLat").getAsDouble());

        if(in.has("locationLon"))
            out.setLocationLonBuilder(in.get("locationLon").getAsDouble());



        if(in.has("breed")){
            JsonArray breedJsonArray = in.get("breed").getAsJsonArray();
            String[] breedArr = new String[breedJsonArray.size()];

            for(int i =0; i<breedJsonArray.size(); i++){
                breedArr[i] = breedJsonArray.get(i).getAsString();
            }
            out.setBreedBuilder(breedArr);
        }

        if(in.has("photoFileLocation")){
            JsonArray fileJsonArray = in.get("photoFileLocation").getAsJsonArray();
            ArrayList<String> urlList = new ArrayList<>();
            for(int i =0; i<fileJsonArray.size(); i++){
                urlList.add(fileJsonArray.get(i).getAsString());
            }
            out.setImgURLsBuilder(urlList);
        }



        return  out.build();
    }

}


/*
0: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/4/?bust=1611963715"
1: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/1/?bust=1611233423"
2: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/2/?bust=1611233437"
3: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/3/?bust=1611233547"
4: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/5/?bust=1611963716"
 */