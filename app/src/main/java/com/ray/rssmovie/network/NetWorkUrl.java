package com.ray.rssmovie.network;

/**
 * Created by guolei on 17-4-6.
 */

public class NetWorkUrl {

    private static final String BASE_URL = "https://api.douban.com/";

    private static final String API_VERSION = "v2/";

    private static final String MOVIE_SUBJECT = "movie/";

    public static final String TOP250 = "top250";

    public static final String NABOR = "us_box";

    public static String getBaseUrl() {
        return BASE_URL + API_VERSION;
    }
}
