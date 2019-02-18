package view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by admin on 2019/2/16.
 */

public class CustomSurfaceView extends SurfaceView {

    private MediaPlayer mediaPlayer ;

    public CustomSurfaceView(Context context) {
        super(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mediaPlayer = new MediaPlayer();
        SurfaceHolder surfaceHolder = getHolder();
        mediaPlayer.setDisplay(surfaceHolder);

    }


}
