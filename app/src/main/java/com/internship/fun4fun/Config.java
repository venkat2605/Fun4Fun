package com.internship.fun4fun;

public final class Config {

    private Config() {
    }

    public static final String YOUTUBE_API_KEY = "YOUR_API_KEY";
    public static final String FINAL_URL = "https://www.googleapis.com/youtube/v3/search?&maxResults=50&part=snippet,id&channelId=";

    public static final String PARAM_CHANNEL_ID_YOUTUBE = "channelId=";
    public static final String PARAM_MAX_RESULT_YOUTUBE = "maxResults=";

    public static final String PARAM_PAGE_TOKEN_YOUTUBE = "pageToken=";

    public static final String PARAM_PLAYLIST_ID_YOUTUBE = "playlistId=";
}
