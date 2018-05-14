package com.example.bartomiej.pianistbooster.sound_analysis;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.bartomiej.pianistbooster.common.Constants;
import com.example.bartomiej.pianistbooster.common.PianoFrequencies;

import java.util.ArrayList;
import java.util.List;

import static com.example.bartomiej.pianistbooster.common.Constants.thread_ids.LISTENER_THREAD;

public class ListenerThread implements Runnable {


    final private Handler handler;
    final private Activity activity;
    public ListenerThread(Activity activity, Handler handler) {
            this.activity = activity;
            this.handler = handler;
    }

    private boolean validateMicAvailability(){
        Boolean available = true;
        AudioRecord recorder =
                new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);
        try{
            if(recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED ){
                available = false;

            }

            recorder.startRecording();
            if(recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING){
                recorder.stop();
                available = false;

            }
            recorder.stop();
        } catch(Exception e) {
            available = false;
        } finally{
            recorder.release();
            recorder = null;
        }

        return available;
    }

    private void getpitch(){
        /*
        while(!validateMicAvailability()) {
            try {
                Thread.sleep(10);
            } catch(Exception e) {

            }
        }*/

        double frequencies[] = PianoFrequencies.getFrequencies();


        int channel_config = AudioFormat.CHANNEL_IN_MONO;
        int format = AudioFormat.ENCODING_PCM_16BIT;
        int sampleSize = 44100;
        int bufferSize = AudioRecord.getMinBufferSize(sampleSize, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioInput = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleSize, channel_config, format, bufferSize);

        short[] audioBuffer = new short[bufferSize / 2];

        audioInput.startRecording();

        int SAMPLE_TO_FFT = 2048;

        int FIND_AMOUNT = 3;

        List <Short> oldShorts = new ArrayList<>();

        List <Double> results = new ArrayList<>();


        while(!Thread.currentThread().isInterrupted()) {
            int shortsRead = audioInput.read(audioBuffer, 0, audioBuffer.length);
            for(int i = 0; i < shortsRead; i++) {
                oldShorts.add(audioBuffer[i]);
                if(oldShorts.size() == SAMPLE_TO_FFT) {
                    short[] x = new short[SAMPLE_TO_FFT];
                    for(int j = 0; j < SAMPLE_TO_FFT; j++) {
                        x[j] = oldShorts.get(j);
                    }
                    AnalyzeSound as = new AnalyzeSound();
                    Double frequency = as.analyze(x, frequencies);

                    oldShorts = new ArrayList<>();

                    results.add(frequency);

                    if(results.size() == FIND_AMOUNT) {
                        double lider = results.get(FIND_AMOUNT / 2);
                        int count = 0;
                        for(double f: results) {
                            if(f == lider) count++;
                        }
                        if(count >= (FIND_AMOUNT + 1) / 2) {
                            Message msg = new Message();
                            msg.obj = Double.toString(lider);
                            msg.what = LISTENER_THREAD;
                            handler.sendMessage(msg);
                        }
                        results.remove(0);
                    }


                }
            }
        }

        audioInput.stop();
        audioInput.release();
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        getpitch();
    }
}
