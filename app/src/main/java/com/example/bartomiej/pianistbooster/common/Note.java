package com.example.bartomiej.pianistbooster.common;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.bartomiej.pianistbooster.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static com.example.bartomiej.pianistbooster.common.Constants.clef_ids.TREMBLE;

public class Note {

    final ImageView note;
    List<ImageView> lines;
    final Activity activity;
    private int clef;
    private int semitone;

    private int id;

    public static double getDPI(double size, DisplayMetrics metrics){

        return (size * metrics.densityDpi) / (double)DisplayMetrics.DENSITY_DEFAULT;
    }

    /**
     *
     * @return note id - position from lowest to highest (indexed starting at 0)
     */
    public int getNoteId() {
        return id;
    }

    public int getNoteClef() {
        return clef;
    }

    public int getNoteSemitone() {
        return semitone;
    }

    public Note(Activity activity, int semitone, int id, int clef) {
        int Y = PianoFrequencies.getPositionOnClef(id, semitone);
        this.activity = activity;

        this.semitone = semitone;
        this.clef = clef;

        note = new ImageView(activity);
        if(semitone == -1) {
            note.setImageResource(R.drawable.flat);
        } else if(semitone == 0) {
            note.setImageResource(R.drawable.note);
        } else if(semitone == 1) {
            note.setImageResource(R.drawable.sharp);
        }

        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        RelativeLayout layout = (RelativeLayout) activity.findViewById(R.id.relative_layout);
        layout.addView(note);

        LayoutParams lp = (LayoutParams) note.getLayoutParams();

        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        if(clef == TREMBLE) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lp.width = (int)(getDPI(22.0, metrics));
            lp.height = (int)(getDPI(28.0, metrics));
            lp.leftMargin = (int)(getDPI(150.0, metrics));
            lp.topMargin = (int)(getDPI(59.8, metrics) - (Y * getDPI(3.8, metrics)));
            Log.v("note", "All: topMargin = " + Integer.toString(lp.topMargin) + " Y = " + Integer.toString(Y));
        } else {
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.width = (int)(getDPI(22.0, metrics));
            lp.height = (int)(getDPI(28.0, metrics));
            lp.leftMargin = (int)(getDPI(150.0, metrics));
            lp.bottomMargin = (int)(getDPI(83.38 + (3.8 * Y), metrics));
        }
        note.setLayoutParams(lp);

        lines = new ArrayList<>();
        if(clef == TREMBLE) {
            for (int i = Math.min(10, Y); i <= Math.max(Y, -2); i++) {
                if(Math.abs(i % 2) == 1 || (-2 < i && i < 10)) continue;
                lines.add(new ImageView(activity));
                ImageView line = lines.get(lines.size() - 1);
                line.setImageResource(R.drawable.line);
                layout.addView(line);

                LayoutParams lineParams = (LayoutParams) line.getLayoutParams();
                lineParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lineParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                lineParams.leftMargin = (int)(getDPI(150.0 + 2, metrics));
                lineParams.topMargin = (int)(getDPI(59.8 + 21.3 - i * 3.8, metrics));
                line.setLayoutParams(lineParams);

                line.setVisibility(INVISIBLE);
            }
        } else {
            for (int i = Math.min(-2, Y); i <= Math.max(Y, -14); i++) {
                if(Math.abs(i % 2) == 1 || (-14 < i && i < -2)) continue;
                lines.add(new ImageView(activity));
                ImageView line = lines.get(lines.size() - 1);
                line.setImageResource(R.drawable.line);
                layout.addView(line);

                LayoutParams lineParams = (LayoutParams) line.getLayoutParams();
                lineParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lineParams.leftMargin = (int)(getDPI(150.0 + 2, metrics));
                lineParams.bottomMargin = (int)(getDPI(83.38 + 5.3 + i * 3.8, metrics));
                line.setLayoutParams(lineParams);

                line.setVisibility(INVISIBLE);
            }
        }

        setVisibility(INVISIBLE);
    }

    public void setVisibility(int visibility) {
        note.setVisibility(visibility);
        for(ImageView line: lines) {
            line.setVisibility(visibility);
        }
    }

    public void setX(int X) {
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LayoutParams lp = (LayoutParams) note.getLayoutParams();
        lp.leftMargin = (int)getDPI((double)X, metrics);
        note.setLayoutParams(lp);
    }

}
