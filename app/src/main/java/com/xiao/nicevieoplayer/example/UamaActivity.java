package com.xiao.nicevieoplayer.example;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevieoplayer.R;

/**
 * Created by Jin on 2017/12/13.
 * Description
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class UamaActivity extends AppCompatActivity {
	
	private NiceVideoPlayer mNiceVideoPlayer;
	private ScrollView mScrollView;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uama_activity);
		
		init();
		trigger();
	}
	
	private void init() {
		mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
		mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
		String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
		mNiceVideoPlayer.setUp(videoUrl, null);
		TxVideoPlayerController controller = new TxVideoPlayerController(this);
		controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
		controller.setLenght(98000);
		Glide.with(this)
				.load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
				.placeholder(R.drawable.img_default)
				.crossFade()
				.into(controller.imageView());
		mNiceVideoPlayer.setController(controller);
	}
	
	private void trigger() {
		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override
			public void onScrollChange(View view, int i, int i1, int i2, int i3) {
				int[] location = new int[2];
				mNiceVideoPlayer.getLocationInWindow(location); //获取在当前窗口内的绝对坐标

//				int[] location2 = new int[2];
//				mNiceVideoPlayer.getLocationOnScreen(location2);//获取在整个屏幕内的绝对坐标
//
//				Log.i("msg", "获取在整个屏幕内的绝对坐标" + location2[0] + "####----####" + location2[1]);
				
				if (location[1] < 0) {
					if (!mNiceVideoPlayer.isIdle()) {
						if (!mNiceVideoPlayer.isTinyWindow()) {
							Log.i("msg", "获取在当前窗口内的绝对坐标" + location[0] + "####----####" + location[1]);
							mNiceVideoPlayer.enterTinyWindow();
						}
					}
				} else {
					if (mNiceVideoPlayer.isTinyWindow()) {
						Log.i("msg", "获取在当前窗口内的绝对坐标" + location[0] + "####----####" + location[1]);
						mNiceVideoPlayer.exitTinyWindow();
					}
				}
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
	}
	
	@Override
	public void onBackPressed() {
		if (NiceVideoPlayerManager.instance().onBackPressd()) return;
		super.onBackPressed();
	}
}
