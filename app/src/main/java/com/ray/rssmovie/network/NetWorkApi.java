package com.ray.rssmovie.network;

import com.ray.rssmovie.bean.MovieDetail;
import com.ray.rssmovie.bean.MovieList;
import com.ray.rssmovie.bean.MovieSubject;
import com.ray.rssmovie.bean.UsBoxMovieList;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
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
    Observable<UsBoxMovieList> getNabor();

    /**
     * 获取排名250
     */
    @GET("movie/top250")
    Observable<MovieList> getTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情{使用占位符替换}
     */
    @GET("movie/subject/{id}")
    Observable<MovieDetail> getSubjectDetail(@Path("id") String id);
}
