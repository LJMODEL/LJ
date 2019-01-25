package com.bw.movie.utils;

public class Contacts {
    //前缀
    public static final String BASE_URL = "http://mobile.bwstudent.com/movieApi/";
    //登录请求头
    public static final String LOGIN = "user/v1/login";
    //注册请求头
    public static final String REGISTER = "user/v1/registerUser";
    //推荐影院
    public static final String RECOMMENDED = "cinema/v1/findRecommendCinemas";
    //首页recycler列表热门
    public static final String FILM_RECYCLER_HOT = "movie/v1/findHotMovieList";
    //首页recycler列表正在上映
    public static final String FILM_RECYCLER_ACTION = "movie/v1/findReleaseMovieList";
    //首页recycler列表即将上映
    public static final String FILM_RECYCLER_COMMINGSOON = "movie/v1/findComingSoonMovieList";
    //附近影院
    public static final String NEAR = "cinema/v1/findNearbyCinemas";

}
