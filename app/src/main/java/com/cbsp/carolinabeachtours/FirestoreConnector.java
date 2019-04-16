package com.cbsp.carolinabeachtours;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;

class FirestoreConnector {

    private FirebaseFirestore db;

    FirestoreConnector() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Retrieves all location data of certain type.
     * @param type: Type needed.
     * @param ctx: Context of calling list activity.
     */
    void getAllLocationsOfType(Location.LocationType type, final LocationListActivity ctx) {
        db.collection("locations")
            .whereEqualTo("type", type.toString())
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Location> locations = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            locations.add(document.toObject(Location.class));
                        }
                        ctx.drawData(locations);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        ctx.dataLoadFailed();
                    }
                }
            });
    }

    /**
     * Retrieves five most popular park locations.
     * @param ctx: Context of calling main activity.
     */
     void getPopularLocations(final MainActivity ctx) {
        db.collection("locations")
            .orderBy("popularity", Query.Direction.DESCENDING).limit(5)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Location> locations = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            locations.add(document.toObject(Location.class));
                        }
                        ctx.drawData(locations);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        ctx.dataLoadFailed();
                    }
                }
            });
    }

    /**
     * Hard-coded for demonstration, populates firestore with the initial data sample.
     * ctx: Lets us get resource identifiers for drawables (db stores ids).
     */
    void populateFirestore(Context ctx) {
        List<Location> locations = new ArrayList<>();

        locations.add(new Location(
                Location.LocationType.ECOSYSTEM,
                "Pine Savanna",
                "The Longleaf Pine Savanna is an ecosystem that surrounds the Wilmington, " +
                    "North Carolina region. These forests have a rich history of use by humans" +
                    " and vast diversity of flora and fauna that call this ecosystem home. These" +
                    " communities are generally characterized by flat, wet areas that are saturated" +
                    " for part of the year; great plant diversity, up to 52 species per square" +
                    " meter; some of the rarest plants in all of North Carolina; and frequent" +
                    " fire, which helps promote diversity and carnivorous plant health. Carolina" +
                    " Beach State Park has a thriving Pine Savanna that surrounds the Flytrap Trail" +
                    " in the center of the Park. When on this trail, there are many signs that" +
                    " let visitors know they are in this important and unique ecosystem!",
                34.044724, -77.917396,
                null,
                ctx.getResources().getIdentifier("l" + 1, "drawable", ctx.getPackageName()),
                0
        ));

        locations.add(new Location(
                Location.LocationType.ECOSYSTEM,
                "Vernal Ponds",
                "Vernal Ponds are small sites that flood seasonally and occur throughout the" +
                        " Coastal Plain and Sandhills. They are dominated by a dense to sparse herb" +
                        " layer and when dry are subject to fires spreading from adjacent uplands. " +
                        "These Vernal Pools are almost always key amphibian breeding sites because " +
                        "they contain no fish. Small Depression Ponds are on sites with permanently" +
                        " flooded (at least in the center) sinkholes. Most occur in the lower" +
                        " coastal plain, over limestone formations. Scattered trees (pond cypress " +
                        "and swamp blackgum) may be present in both deep and shallow water zones " +
                        "and most ponds are surrounded by a dense shrub layer. These shrubby zones " +
                        "provide breeding habitat for shrub‐scrub‐nesting and these sites are used " +
                        "by wading birds for foraging/nesting. The main value of these sites, " +
                        "however, is that they provide critical habitat for reptiles and breeding" +
                        " amphibians. ",
                34.042128, -77.913268,
                null,
                ctx.getResources().getIdentifier("l" + 2, "drawable", ctx.getPackageName()),
                2
        ));

        locations.add(new Location(
                Location.LocationType.ECOSYSTEM,
                "Xeric Sandhill",
                "The Xeric Sandhill Scrub is the driest and most arid region of the state" +
                        " park. The Xeric (meaning dry) Sandhill Scrub is located at higher " +
                        "elevations or on steeper slopes of the coastal plain. The area is very dry" +
                        " due to the porous soil which mainly consists of deep, wind-blown sand and" +
                        " in some places a layer of clay and organic material. It is in these areas" +
                        " that Longleaf Pine, Turkey Oaks, and Wire Grass dominate the landscape. " +
                        "One of the state park's most popular attractions, Sugarloaf Dune, lies " +
                        "within the Xeric Sandhill Community.",
                34.041385, -77.918871,
                null,
                ctx.getResources().getIdentifier("l" + 3, "drawable", ctx.getPackageName()),
                2
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Six-lined Racerunner",
                "The Six-lined Racerunner is a species of lizard common to the United States." +
                        " This lizard is typically dark green, brown, or black, with six yellow or" +
                        " light green stripes that extend down the body from head to tail. Their" +
                        " underside is usually white in females, and a pale blue in males. Males " +
                        "may sometimes have a pale green throat. Six-lined Racerunners are slender" +
                        " and streamlined, with a tail nearly twice the body length. Their average " +
                        "length is about 6 to 9.5 inches. Like other species of whiptail lizards, " +
                        "the Six-lined Racerunner is diurnal and insectivorous; Racerunners eat a " +
                        "wide variety of insects, spiders, foraging under vegetation for termites " +
                        "and other invertebrates.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_a1" , "drawable", ctx.getPackageName()),
                1
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Eastern Fox Squirrel",
                "Eastern fox squirrels are most abundant in open forest stands with little" +
                        "understory vegetation; they are not found in stands with dense" +
                        "undergrowth.  Ideal habitat is small stands of large trees interspersed" +
                        "with agricultural land. They are often observed foraging on the ground " +
                        "several hundred meters from the nearest woodlot.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_a2", "drawable", ctx.getPackageName()),
                2
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Whitetail Deer",
                "The Whitetail Deer, commonly known as Whitetails, are not necessarily an " +
                        "animal that people first consider when they think of island ecology. " +
                        "However, they are both native and thriving on Pleasure Island and " +
                        "surrounding areas. Whitetails are the smallest species of the deer " +
                        "family and get their name from the underside of their tails. Their bodies" +
                        " are mainly a reddish or grayish brown, depending on the season, except " +
                        "for the white undersides of their tails. They show the white side of " +
                        "their tails and wag it when they sense danger.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_a3", "drawable", ctx.getPackageName()),
                0
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Longleaf Pine",
                "The longleaf pine is an evergreen conifer that obtained its name by having" +
                        " the longest needles of any eastern pine species. The needles can grow " +
                        "to be eighteen inches long and grow in bundles of three. Old mature trees" +
                        " grow to between eighty and one hundred feet tall. The longleaf pine is a" +
                        " very slow growing tree and it can take up to half of their 300 year " +
                        "lifespan to reach maturity.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_p1", "drawable", ctx.getPackageName()),
                0
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Wiregrass",
                "Wiregrass plays an important role in many habitats. Wiregrass grows from " +
                        "North Carolina down to Florida and is found in many habitats including " +
                        "North Carolina's sandhill scrub and pine savannas where it is one of the " +
                        "dominant understory plants. Wiregrass is extremely adapted to natural " +
                        "fires, usually due to dry conditions and lightning.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_p2", "drawable", ctx.getPackageName()),
                0
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Venus Flytrap",
                "The Venus flytrap is a small photosynthetic plant, meaning it gets its " +
                        "energy from the sun. it is a carnivorous plant that eats insects. The " +
                        "Venus flytrap lives in poor acidic soil, made up of little phosphorous or" +
                        " nitrogen. The plant makes up for the poor nutrients in the soil by eating" +
                        " insects. The leaves of Venus flytraps look like “jaws,” which stay wide " +
                        "open, and are only 3-10 cm across. The trap entices insects to it by " +
                        "giving off a sweet nectar smell, then snaps shut over the prey when two " +
                        "or more of the trigger hairs are disturbed within 20 seconds. When the " +
                        "trap is closed over an insect, cilia, or finger-like projections, keep " +
                        "the insects inside. Once the trap is fully shut, digestive fluids take " +
                        "over and dissolve the soft parts of the insect. Digestion can take " +
                        "anywhere between 5-12 days, in which the insect is than absorbed into " +
                        "the leaves. Once digestion is done, the trap will reopen and the " +
                        "exoskeleton or indigestible parts of the insect will fall out.",
                34.044724, -77.917396,
                locations.get(0).getName(),
                ctx.getResources().getIdentifier("l1_p3", "drawable", ctx.getPackageName()),
                3
        ));

        for (Location loc : locations) {
            db.collection("locations").document(loc.getName()).set(loc);
        }
    }


}
