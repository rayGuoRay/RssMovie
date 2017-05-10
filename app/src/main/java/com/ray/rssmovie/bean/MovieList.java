package com.ray.rssmovie.bean;

import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by guolei on 17-4-6.
 */

public class MovieList {
    public int start;
    public int count;
    public int total;
    public List<MovieSubject> subjects;
}
