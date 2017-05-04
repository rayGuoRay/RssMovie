package com.ray.rssmovie.bean;

/**
 * Created by guolei on 17-4-6.
 */

public class MovieSubject {
    public class Rating {
        public float max;
        public float average;
        public float starts;
        public float min;
    }

    public class Images {
        public String small;
        public String medium;
        public String large;
    }

    public Rating rating;
    public Images images;
    public String id;
    public String title;
}
