package com.ray.rssmovie.network;

import com.ray.rssmovie.bean.MovieList;
import com.ray.rssmovie.bean.MovieSubject;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by guolei on 17-4-6.
 */

public interface NetWorkApi {
    /**
     * 搜索电影
     */
    @GET("movie/search")
    Observable<MovieList> searchMovie(@QueryMap Map<String, String> paramMap);

    /**
     * 获取北美票房排行榜
     */
    @GET("movie/us_box")
    Observable<MovieList> getNabor();

    /**
     * 获取排名250
     */
    @GET("movie/top250")
    Observable<MovieList> getTop250(@Query("start") int start, @Query("count") int count);
}
