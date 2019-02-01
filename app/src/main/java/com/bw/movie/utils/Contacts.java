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
    //电影关注
    public static final String ATTENTION_YES = "movie/v1/verify/followMovie";
    //电影取消关注
    public static final String ATTENTION_NO = "movie/v1/verify/cancelFollowMovie";
    //电影查询详情
    public static final String FILMQUERY = "movie/v1/findMoviesDetail";
    //微信登录
    public static final String WX_LOGIN = "user/v1/weChatBindingLogin";
    //绑定微信账号
    public static final String WX_BIND = "user/v1/verify/bindWeChat";
    //是否绑定微信账号
    public static final String WX_TOBIND = "user/v1/verify/whetherToBindWeChat";
    //反馈
    public static final String FANKUI = "tool/v1/verify/recordFeedBack";
    //修改密码
    public static final String XIUGAI_MIMA = "user/v1/verify/modifyUserPwd";
    //我的信息
    public static final String MY_XINXI = "user/v1/verify/getUserInfoByUserId";
    //查询关注电影
    public static final String MY_DAINYING = "movie/v1/verify/findMoviePageList";
    //查询影片评论
    public static final String QUERY_REVIEW = "movie/v1/findAllMovieComment";
    //电影院详情
    public static final String CINEMA_XIANGQING = "cinema/v1/findCinemaInfo";
    //根据电影ID和影院ID查询电影排期列表
    public static final String MOVIEW_LIEBIAO = "movie/v1/findMovieScheduleList";
    //影院评论列表
    public static final String CINEMA_PINGLUN = "cinema/v1/findAllCinemaComment";
    //影院点赞
    public static final String CINEMA_DAINZHI = "cinema/v1/verify/cinemaCommentGreat";
    //关注影院
    public static final String CINEMA_GUANZHU = "cinema/v1/verify/followCinema";
    //查询关注影院
    public static final String CHAXUN_YINGYUAN = "cinema/v1/verify/findCinemaPageList";
    //影院评论
    public static final String CINEMA_XIEGUANZHU = "cinema/v1/verify/cinemaComment";
    //影院取消关注
    public static final String CINEMA_QUANXIAOGUANZHU = "cinema/v1/verify/cancelFollowCinema";
    //模糊搜索影院
    public static final String CINEMA_MOHU = "cinema/v1/findAllCinemas";

}
