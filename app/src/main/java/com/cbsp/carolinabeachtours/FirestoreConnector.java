package com.cbsp.carolinabeachtours;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

class FirestoreConnector {

    private FirebaseFirestore db;
    private boolean available;

    FirestoreConnector() {
        db = FirebaseFirestore.getInstance();
        available = false;
    }

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
                ctx.getResources().getIdentifier("l" + 1, "drawable", ctx.getPackageName())
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
                ctx.getResources().getIdentifier("l" + 2, "drawable", ctx.getPackageName())
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
                ctx.getResources().getIdentifier("l" + 3, "drawable", ctx.getPackageName())
        ));

        for (Location loc : locations) {
            db.collection("locations").document(loc.getName()).set(loc);
        }
    }


}
