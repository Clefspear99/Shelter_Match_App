package lsj.softwareeng.sheltermatch;


import android.graphics.Bitmap;

import java.util.ArrayList;

import lsj.softwareeng.sheltermatch.ui.fav.FavFragment;

//Concrete PetObject Builder class
class PetObjectBuilder{
    int idBuilder=-1;
    String nameBuilder="Unnamed";
    String typeBuilder="unknown";
    int sexBuilder=0;
    String ageBuilder="unknown";
    String breedBuilder="unknown";
    ArrayList<String> imgURLsBuilder;
    String cityBuilder="unknown";
    String stateBuilder="unknown";
    String countryBuilder="unknown";
    int zipCodeBuilder=00000;
    double locationLatBuilder=0;
    double locationLongBuilder=0;
    String timeStampBuilder="";
    boolean favBuilder=false;

    static int genID=-2;


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


    public PetObjectBuilder Sex(int sex) {
        this.sexBuilder = sex;
        return this;
    }


    public PetObjectBuilder Age(String age) {
        this.ageBuilder = age;
        return this;
    }

    public PetObjectBuilder Breed(String breed) {
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

    public PetObjectBuilder ZipCode(int zipCode) {
        this.zipCodeBuilder = zipCode;
        return this;
    }

    public PetObjectBuilder LocationLat(double locationLat) {
        this.locationLatBuilder = locationLat;
        return this;
    }

    public PetObjectBuilder LocationLong(double locationLong) {
        this.locationLongBuilder = locationLong;
        return this;
    }

    public PetObjectBuilder TimeStamp(String timeStamp) {
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
        return new PetObject(idBuilder, nameBuilder, typeBuilder, sexBuilder, ageBuilder, breedBuilder, imgURLsBuilder, cityBuilder, stateBuilder, countryBuilder, zipCodeBuilder, locationLatBuilder, locationLongBuilder, timeStampBuilder, favBuilder);
    }
}



public class PetObject {
int id;
String name;
String type;
int sex;
String age;
String breed;
ArrayList<String> imgURLs;
ArrayList<Bitmap> images;
String city;
String state;
String country;
int zipCode;
double locationLat;
double locationLounge;
String timeStamp;
Boolean fav;
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


    public PetObject(int id, String name, String type, int sex, String age, String breed, ArrayList<String> imgURLs, String city, String state, String country, int zipCode, double locationLat, double locationLounge, String timeStamp, boolean fav) {
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
        this.zipCode = zipCode;
        this.locationLat = locationLat;
        this.locationLounge = locationLounge;
        this.timeStamp=timeStamp;
        this.fav=fav;
        this.images=new ArrayList<>();
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLounge() {
        return locationLounge;
    }

    public void setLocationLounge(double locationLounge) {
        this.locationLounge = locationLounge;
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
            tempPet=new PetObjectBuilder().Name("Charlie").Type("Dog").Sex(1).Age("Young")
                    .Breed("HotDog").City("Whitewater").State("WI").Country("US").ZipCode(53190).TimeStamp("2021-03-31 18:56:12").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/4/?bust=1611963715").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/1/?bust=1611233423").addImageURL("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/3/?bust=1611233547").build();
            pets.add(tempPet);
        }

        return pets;
    }

}


/*
0: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/4/?bust=1611963715"
1: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/1/?bust=1611233423"
2: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/2/?bust=1611233437"
3: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/3/?bust=1611233547"
4: "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50329280/5/?bust=1611963716"
 */