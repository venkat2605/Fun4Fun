package com.internship.fun4fun;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;


import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends YouTubeBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,YouTubePlayer.OnInitializedListener  {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    YouTubePlayer player;
    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    int mVideoType;;
    String videoid,videoid1,videotitle,videourl,videoTime;
    String url;
    private String mNextPageToken = "";




    ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList;
    DrawerLayout drawer;
    Toolbar toolbar;
    YoutubeVideoAdapter adapter;

    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    String niceDateStr;


    ActionBarDrawerToggle mDrawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        toolbar.setTitle(getResources().getString(R.string.boy_formula));

        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.action_settings)
                {
                    //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();


                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Fun4Fun");
                        String shareMessage= "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "Choose One"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                }

                return false;
            }
        });

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        youtubeVideoModelArrayList = new ArrayList<>();

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        String channelId =getResources().getString(R.string.url_boy_formula);

        populateRecyclerView(channelId);










    }














    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Quit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure to quit?")
                .setCancelable(false)
                .setPositiveButton("Quit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.boy_formula){
            String channelId =getResources().getString(R.string.url_boy_formula);
            toolbar.setTitle(getResources().getString(R.string.boy_formula));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);

        }
        if (id == R.id.bumchick_babloo){
            String channelId=getResources().getString(R.string.url_bumchick_babloo);
            toolbar.setTitle(getResources().getString(R.string.bumchick_babloo));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.capdt){
            String channelId=getResources().getString(R.string.url_capdt);
            toolbar.setTitle(getResources().getString(R.string.capdt));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);




        }
        if (id == R.id.chai_bisket){
            String channelId=getResources().getString(R.string.url_chai_bisket);
            toolbar.setTitle(getResources().getString(R.string.chai_bisket));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.dhethadi){
            String channelId=getResources().getString(R.string.url_dhethadi);
            toolbar.setTitle(getResources().getString(R.string.dhethadi));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);



        }
        if (id == R.id.funpataka){
            String channelId=getResources().getString(R.string.url_funpataka);
            toolbar.setTitle(getResources().getString(R.string.funpataka));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.girl_formula){
            String channelId=getResources().getString(R.string.url_girl_formula);
            toolbar.setTitle(getResources().getString(R.string.girl_formula));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);



        }
        if (id == R.id.jalsaa_raayudu){
            String channelId=getResources().getString(R.string.url_jalsaa_raayudu);
            toolbar.setTitle(getResources().getString(R.string.jalsaa_raayudu));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.mahathalli){


            String channelId=getResources().getString(R.string.url_mahathalli);
            toolbar.setTitle(getResources().getString(R.string.mahathalli));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.my_village_show){


            String channelId=getResources().getString(R.string.url_my_village_show);
            toolbar.setTitle(getResources().getString(R.string.my_village_show));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);

        }
        if (id == R.id.pakkinti_kurradu){
            String channelId=getResources().getString(R.string.url_pakkinti_kurradu);
            toolbar.setTitle(getResources().getString(R.string.pakkinti_kurradu));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);

        }
        if (id == R.id.teluguone){
            String channelId=getResources().getString(R.string.url_teluguone);
            toolbar.setTitle(getResources().getString(R.string.teluguone));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.viva){
            String channelId=getResources().getString(R.string.url_viva);
            toolbar.setTitle(getResources().getString(R.string.viva));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.wirally){
            String channelId=getResources().getString(R.string.url_wirally);
            toolbar.setTitle(getResources().getString(R.string.wirally));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.yodhatv){
            String channelId=getResources().getString(R.string.url_yodha_tv);
            toolbar.setTitle(getResources().getString(R.string.yodha_tv));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.zflicks){
            String channelId=getResources().getString(R.string.url_z_flicks);
            toolbar.setTitle(getResources().getString(R.string.z_flicks));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }
        if (id == R.id.krazy_khanna){
            String channelId=getResources().getString(R.string.url_krazy_khanna);
            toolbar.setTitle(getResources().getString(R.string.krazy_khanna));
            youtubeVideoModelArrayList.clear();
            youtubeVideoModelArrayList = new ArrayList<>();
            populateRecyclerView(channelId);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }



    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
            //showMessage("Playing");
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
            // showMessage("Paused");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            //showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }



    /**
     * populate the recyclerview and implement the click event here
     */
    private void populateRecyclerView(final String channelId) {

        if (this.mVideoType == 2) {
            url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,id&fields=nextPageToken,pageInfo(totalResults),items(snippet(title,thumbnails,publishedAt,resourceId(videoId)))&key=" +Config.YOUTUBE_API_KEY + "&" + Config.PARAM_PLAYLIST_ID_YOUTUBE + channelId + "&" + Config.PARAM_PAGE_TOKEN_YOUTUBE + this.mNextPageToken + "&" + Config.PARAM_MAX_RESULT_YOUTUBE + 50;
        } else {
            url = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&type=video&fields=nextPageToken,pageInfo(totalResults),items(id(videoId),snippet(title,thumbnails,publishedAt))&key=" +Config.YOUTUBE_API_KEY + "&" + Config.PARAM_CHANNEL_ID_YOUTUBE + channelId + "&" + Config.PARAM_PAGE_TOKEN_YOUTUBE + this.mNextPageToken + "&" + Config.PARAM_MAX_RESULT_YOUTUBE + 50;
        }
        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Url",""+Config.FINAL_URL + channelId + "&key=" + Config.YOUTUBE_API_KEY);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectthumbnail = jsonsnippet.getJSONObject("thumbnails");

                        JSONObject jsonObjectdefault = jsonObjectthumbnail.getJSONObject("default");
                        videoid = jsonVideoId.getString("videoId");
                        videotitle = jsonsnippet.getString("title");
                        videourl = jsonObjectdefault.getString("url");
                        videoTime = jsonsnippet.getString("publishedAt");
                        try {
                            Date date = inputFormat.parse(videoTime);
                            niceDateStr = String.valueOf(DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS));


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        youtubeVideoModelArrayList.add(new YoutubeVideoModel(videoid, videotitle, videourl, niceDateStr));



                    }
                    adapter = new YoutubeVideoAdapter(MainActivity.this, youtubeVideoModelArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    videoid1 = "" + youtubeVideoModelArrayList.get(0).getVideoId();
                    Log.e("VideoId",videoid1);
                    player.cueVideo(videoid1);
                    pDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


        //set click event
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                player.loadVideo(youtubeVideoModelArrayList.get(position).getVideoId());
            }
        }));
    }



}

