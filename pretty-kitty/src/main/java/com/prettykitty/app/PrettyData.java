package com.prettykitty.app;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by brianrogers on 6/3/14.
 */
public class PrettyData {

    private PrettyData(){}

    public static final Map<String, String> SPA_LOCATIONS = new LinkedHashMap<String, String>(){{
        put("Austin, TX", "pkaustin");
        put("Houston, TX", "pkhouston");
        put("Dallas, TX", "pkdallas");
        put("Dallas Uptown, TX", "pkdallas");
        put("Houston (Wash Hts), TX", "pkhouston");
        put("Montclair, NJ", "pkmontclair");
        put("Hoboken, NJ", "pkwhoboken");
        put("Watchung, NJ", "pkwatchung");
    }};

}
