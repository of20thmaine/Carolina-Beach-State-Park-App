package com.cbsp.carolinabeachtours;

import com.google.firebase.storage.StorageReference;

public class Location {

    public enum LocationType {
        ECOSYSTEM {
            @Override
            public String toString() {
              return "ECOSYSTEM";
            }
          }, ANIMAL {
            @Override
            public String toString() {
              return "ANIMAL";
            }
          }, PLANT {
            @Override
            public String toString() {
              return "PLANT";
            }
          }
    }

    private LocationType type;
    private String name, about, isIn, imageFile;
    private double latitude, longitude;
    private int popularity;
    private StorageReference image;

    public Location() { }

    public Location(LocationType type, String name, String about, double latitude,
                    double longitude, String isIn, String imageFile, int popularity) {
        this.type = type;
        this.name = name;
        this.about = about;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isIn = isIn;
        this.imageFile = imageFile;
        this.popularity = popularity;
    }

    public LocationType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getIsIn() {
        return isIn;
    }

    public String getImageFile() {
        return imageFile;
    }

    public int getPopularity() {
        return popularity;
    }

    public String typeAsString() {
        if (type == LocationType.ANIMAL) {
            return "Animal";
        } else if (type == LocationType.PLANT) {
            return "Plant";
        }
        return "Ecosystem";
    }
}
