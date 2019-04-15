package com.cbsp.carolinabeachtours;

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
    private String name, about;
    private double latitude, longitude;
    private Location isIn;
    private int imageId;

    public Location() { }

    public Location(LocationType type, String name, String about, double latitude,
                    double longitude, Location isIn, int imageId) {
        this.type = type;
        this.name = name;
        this.about = about;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isIn = isIn;
        this.imageId = imageId;
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

    public Location getIsIn() {
        return isIn;
    }

    public int getImageId() {
        return imageId;
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
