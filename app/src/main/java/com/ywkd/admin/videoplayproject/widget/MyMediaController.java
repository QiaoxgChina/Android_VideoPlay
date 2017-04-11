package com.ywkd.admin.videoplayproject.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.SeekBar;

import com.ywkd.admin.videoplayproject.R;

import java.lang.reflect.Field;

import static android.view.LayoutInflater.from;

/**
 * Created by admin on 2017/3/21.
 */

public class MyMediaController extends MediaController {

    private Activity mActivity;

    private View mView;

    public MyMediaController(Context context) {
        super(context);
        mActivity = (Activity) context;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
//        mView = from(getContext()).inflate(
//                R.layout.video_button, null);
        try {
//            SeekBar sb = (SeekBar) from(getContext()).inflate(
//                    R.layout.video_seekbar, null);
            Field mRoot = android.widget.MediaController.class
                    .getDeclaredField("mRoot");
            mRoot.setAccessible(true);
            ViewGroup mRootVg = (ViewGroup) mRoot.get(this);
            ViewGroup vg = findSeekBarParent(mRootVg);
            int index = 1;
            for (int i = 0; i < vg.getChildCount(); i++) {
                if (vg.getChildAt(i) instanceof SeekBar) {
                    index = i;
                    break;
                }
            }
            vg.removeViewAt(index);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            params.weight = 1;
//            vg.addView(sb, index, params);
//            Field mProgress = android.widget.MediaController.class
//                    .getDeclaredField("mProgress");
//            mProgress.setAccessible(true);
//            mProgress.set(this, sb);
//            Field mSeekListener = android.widget.MediaController.class
//                    .getDeclaredField("mSeekListener");
//            mSeekListener.setAccessible(true);
//            sb.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) mSeekListener
//                    .get(this));
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ViewGroup findSeekBarParent(ViewGroup vg) {
        ViewGroup viewGroup = null;
        for (int i = 0; i < vg.getChildCount(); i++) {
            View view = vg.getChildAt(i);
            if (view instanceof SeekBar) {
                viewGroup = (ViewGroup) view.getParent();
                break;
            } else if (view instanceof ViewGroup) {
                viewGroup = findSeekBarParent((ViewGroup) view);
            } else {
                continue;
            }
        }
        return viewGroup;
    }

    @Override
    public void show(int timeout) {
        super.show(timeout);
        ((ViewGroup) mActivity.findViewById(android.R.id.content))
                .removeView(mView);
        ((ViewGroup) mActivity.findViewById(android.R.id.content))
                .addView(mView);
    }

    @Override
    public void hide() {
        super.hide();
        ((ViewGroup) mActivity.findViewById(android.R.id.content))
                .removeView(mView);
    }

}
