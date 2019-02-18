package splite;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 分离音视频通过MediaCodec 硬编码
 * Created by admin on 2019/2/18.
 */

public class SpliteVideo {

    private final String ROOT_DIRCOTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/";

    private final String xiaoYa_mvColor = "Camerajkn0sWajqx8E1sC.mp4";
    private final String outputMp4Path = "noAudioXiaoYa.mp4";

    private MediaCodec mediaCodec;
    private MediaMuxer mediaMuxer;

    public void getBuffer() {
        MediaExtractor mediaExtractor = new MediaExtractor();
        //int maxInputSize = 0;
        int framerate =0 ;
        int videoTrackIndex = 0 ;
        try {
            mediaExtractor.setDataSource(ROOT_DIRCOTORY + xiaoYa_mvColor);
            //       mediaExtractor.
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                if (mime.contains("video/")) {
                    mediaExtractor.selectTrack(i);
                    mediaMuxer = new MediaMuxer(ROOT_DIRCOTORY + outputMp4Path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    videoTrackIndex = mediaMuxer.addTrack(mediaFormat);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mediaMuxer != null) {
            mediaMuxer.start();
        }
        /*if (mediaCodec == null) {
            Log.i("MDL", "当前文件没有相应轨道");
            return;
        }*/
        if(mediaMuxer == null){
            Log.i("MDL","mediaMuxer初始化失败");
            return;
        }
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        info.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(8192 * 1024);
        int sampleSize = 0;
        while (true) {
            //这里如果报了illegalException异常，可能原因是buffer长度不够
            sampleSize = mediaExtractor.readSampleData(buffer,0);
            if(sampleSize < 0 ){
                break;
            }
            info.offset = 0;
            info.size = sampleSize;
            info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
            info.presentationTimeUs = mediaExtractor.getSampleTime();
            mediaMuxer.writeSampleData(videoTrackIndex, buffer, info);
            mediaExtractor.advance();
        }
        mediaExtractor.release();
        mediaMuxer.stop();
        mediaMuxer.release();
    }


    /**
     * 获取当前手机可以支持得编辑码格式
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void checkMediaDecoder() {
        MediaCodecList mediaCodecList = new MediaCodecList(MediaCodecList.ALL_CODECS);

        MediaCodecInfo[] codecInfos = mediaCodecList.getCodecInfos();
        for (MediaCodecInfo codecInfo : codecInfos) {
            Log.i("TAG", "codecInfo =" + codecInfo.getName());
        }
    }
}
