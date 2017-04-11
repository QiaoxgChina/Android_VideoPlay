package com.ywkd.admin.videoplayproject.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.data.VideoFileData;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;

import java.util.List;

public class PlayMultipleVideoByMPActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private String FilePath;

    private SurfaceView surfaceView2;
    private MediaPlayer mediaPlayer2;
    private String FilePath2;

    private List<VideoFile> mVideoFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UIUtils.hideSystemTitleBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_multiple_video_by_mp);
        initVideoFiles();
        initData1();
        initData2();
    }

    private void initVideoFiles() {
        mVideoFiles = VideoFileData.scanFolderVideoFiles(FileUtils.getLocalVideoDir());
    }

    private void initData1() {
        FilePath = mVideoFiles.get(0).getLocalPath();
        surfaceView = (SurfaceView) findViewById(R.id.sv);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置视频流类型

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                Log.i("sno", "start mediaplayer1----------------");
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    mediaPlayer.setDisplay(surfaceView.getHolder());
                    mediaPlayer.setDataSource(FilePath);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {   ///在这里增加播放失败.
                    mediaPlayer.release();
                    if (mediaPlayer != null)
                        Log.i("sno", "eeeeeeeeeeeeerrormediaPlayer!=null");
                    e.printStackTrace();
                }
            }
        }, 200);
    }

    private void initData2() {
        FilePath2 = mVideoFiles.get(1).getLocalPath();
        surfaceView2 = (SurfaceView) findViewById(R.id.sv2);
        mediaPlayer2 = new MediaPlayer();
        mediaPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置视频流类型

        mediaPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer2.start();
                mediaPlayer2.setLooping(true);
                Log.i("sno", "start mediaPlayer2----------------");
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    mediaPlayer2.setDisplay(surfaceView2.getHolder());
                    mediaPlayer2.setDataSource(FilePath2);
                    mediaPlayer2.prepareAsync();
                } catch (Exception e) {   ///在这里增加播放失败.
                    mediaPlayer2.release();
                    if (mediaPlayer2 != null)
                        Log.i("sno", "eeeeeeeeeeeeerrormediaPlayer!=null");
                    e.printStackTrace();
                }
            }
        }, 200);
    }
}
