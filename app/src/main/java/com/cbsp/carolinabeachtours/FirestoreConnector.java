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

    private final FirebaseFirestore db;

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
            .orderBy("popularity", Query.Direction.DESCENDING).limit(6)
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
     * Retrieves locations of a type within a given location.
     * @param typeIn: ID (String) of location looking in.
     * @param typeFind: Type of location being looked for.
     * @param ctx: Context of calling list activity.
     */
    void getTypeIn(String typeIn, Location.LocationType typeFind, final LocationListActivity ctx) {
         db.collection("locations")
                .whereEqualTo("type", typeFind.toString())
                .whereEqualTo("isIn", typeIn)
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
                "img/l1.png",
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
                "img/l2.png",
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
                "img/l3.png",
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
                "img/l1_a1.png",
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
                "img/l1_a2.png",
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
                "img/l1_a3.png",
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
                "img/l1_p1.png",
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
                "img/l1_p2.png",
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
                "img/l1_p3.png",
                3
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Cricket Frog",
                "Southern Cricket Frogs vary in color quite a bit so it's not easy to identify" +
                        " them by coloration. They can be anywhere from green, brown, to red, yellow " +
                        "or grayish . The best way to know that it's a Southern Cricket frog is first" +
                        " from its size. They are very small! About the size of a dime. Adults only" +
                        " grow to about ¾-1” long. They often have a dark triangle present between" +
                        " the eyes and a Y-shaped stripe on their back regardless of their color pattern ",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_a1.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "American Alligator",
                "Let’s face it; alligators get a bad rap.  They are certainly not as cute and" +
                        " cuddly as puppies; in fact, they just might swallow one whole. Yet, these" +
                        " powerful, ferocious, pre-historic creatures contribute to the survival and" +
                        " maintenance of our wetland ecosystems.  They are so important, that many" +
                        " scientists consider alligators a \"keystone species.\"" +
                        "Alligators are native to the southeastern coastal areas of the United States;" +
                        " that means they are right here in our own backyard!",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_a2.png",
                3
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Painted Turtle",
                "The painted turtle is the most widespread native turtle of North America. It" +
                        " lives in slow-moving fresh waters; eating aquatic vegetation, algae, and small" +
                        " water creatures including insects, crustaceans, and fish. Although they are" +
                        " frequently consumed as eggs or hatchlings by rodents, canines, and snakes, " +
                        "the adult turtles' hard shells protect them from most predators. Reliant on" +
                        " warmth from its surroundings, the painted turtle is active only during the" +
                        " day when it basks for hours on logs or rocks.",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_a3.png",
                2
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Pink Sundew",
                "The pink sundew is a member of the sundew family (family Droseraceae). It is" +
                        " a small carnivorous herb, with flowers in a raceme* or openly branched clusters," +
                        " usually ranging from 2 to 4 cm in diameter, but in wet habitats, can grow " +
                        "up to 7 cm. In the sunlight, the plant appears red with round, spoon-shaped" +
                        " leaf blades with numerous tentacles. In lower light, the leaves are lime-green" +
                        " with red tentacles. The leaves are arranged in a rosette pattern, and generally" +
                        " lie flat on the ground. The end of each tentacle has a mucilaginous secretory" +
                        " gland, which gives the plant its dewy appearance.",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_p1.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Bladderwort",
                "In the world of carnivorous plants, the bladderwort is often overlooked because" +
                        " of the more well-known Venus flytraps and pitcher plants. Many people may have" +
                        " seen a pond covered in tiny yellow flowers and not realized what an amazing plant" +
                        " they were viewing. The bladderwort, Utricularia inflata, is an aquatic carnivorous" +
                        " plant with yellow flowers that look like snapdragons. It is aptly named because" +
                        " the word “bladder” means trap and the word “wort” is the old English word " +
                        "for plant. These plants contain bladders about one to three millimeters long" +
                        " that vacuum up small invertebrates that brush the trigger hairs. Once the meal" +
                        " is captured, the prey is digested by enzymes in a process that takes about three days. ",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_p2.png",
                2
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "American Water Lily",
                "The American Water Lily is a beautiful aquatic plant that floats either along" +
                        " the surfaces of freshwater environments or just beneath it. It is commonly known" +
                        " as the American White Water-lily, Fragrant White Water-lily, Sweet-scented" +
                        " White Water-lily, and Beaver root. The flowers produced may be either white" +
                        " or pink with yellow centers and are only one flower to one stem/lily. Each " +
                        "flower is very fragrant, opens in the morning, closes by noon, is 2-6 inches" +
                        " across, and has about 25 petals. The pad itself is nearly circular, has a " +
                        "thick waxy coating, grows to about 10 inches and has a large slit down" +
                        " one-third of its center.",
                34.042128, -77.913268,
                locations.get(1).getName(),
                "img/l2_p3.png",
                2
        ));

         locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Gray Squirrel",
                "The Gray Squirrel is a very common and abundant species in America. Their fur" +
                        " tends to be yellowish brown during the summer and then becomes thick and " +
                        "gray during the winter months. The backs of the ears are tan to cinnamon in " +
                        "color and have white tips in the winter. Their tail is made up of long, wavy" +
                        " hairs, each banded with brown and black at the base and have a broad, white " +
                        "tip. The average length of the Gray Squirrel ranges from 18 to 20 inches and" +
                        " their tail is about half of their total body length (7 to 10 in).",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_a1.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Orchard Spider",
                "The Orchard Spider, also known as the \"Orchard Orb-weaver,\" and the" +
                        " \"Venusta Orchard Spider,\" is the only spider species that received" +
                        " its nomenclature directly from Charles Darwin himself. Now, imagine the" +
                        "inherent horror in being the host of a strange parasite that, slowly and" +
                        " steadily, sucks the life fluids out of you until all that remains is your" +
                        " skeleton. Even Darwin couldn't foresee that nightmare awaiting the Orchard Spider in the forest.",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_a2.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.ANIMAL,
                "Broad-headed Skink",
                "Broadhead Skinks are the largest skinks in our region of North Carolina, ranging" +
                        " from 5.6 to 12.8 inches long. Male skinks are distinguished by their " +
                        "orange-red heads and olive-brown bodies, though the reddish color is only " +
                        "prominent during mating season in the springtime. Females are smaller in " +
                        "size and often display 5 to 7 stripes along their body, which can fade with age.",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_a3.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Turkey Oak",
                "The turkey oak is a member of the red oak group of oaks and is native to the" +
                        " southeastern United States. The name turkey oak derives from the resemblance" +
                        " of the leaves to a turkey's foot.",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_p1.png",
                1
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Southern Live Oak",
                "The Southern live oak, also known as the Virginia live oak, is a native tree" +
                        " perfectly adapted for coastal areas because it is very salt tolerant. Southern" +
                        " live oaks are a distinctive, iconic tree whose twisted and hunched trunks are" +
                        " a common sight on barrier islands.  Their tough wood and extensive root systems" +
                        " help them survive harsh nor'easters and hurricanes, and many of these trees" +
                        " can live to be hundreds of years old.",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_p2.png",
                2
        ));

        locations.add(new Location(
                Location.LocationType.PLANT,
                "Orange Milkwort",
                "The Orange Milkwort is biennial and a member of the polygalacea (milkwort)" +
                        " family. The genus name Polygala means poly = many and gala = milk, not " +
                        "for the milky sap but it was believed animals that consumed the flowers " +
                        "would produce more milk. It is a small native flowering herb with orange " +
                        "cylindrical flowers similar to the shape of a clover  with small smooth " +
                        "leafy stems. They are found in pocosin type habitats similar to those" +
                        " at Carolina Beach State Park, and can grow up to 12 inches in height.",
                34.041385, -77.918871,
                locations.get(2).getName(),
                "img/l3_p3.png",
                2
        ));

        for (Location loc : locations) {
            db.collection("locations").document(loc.getName()).set(loc);
        }
    }


}
