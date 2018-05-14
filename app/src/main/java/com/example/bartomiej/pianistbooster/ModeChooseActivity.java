package com.example.bartomiej.pianistbooster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bartomiej.pianistbooster.game_modes.freestyle_mode.FreestyleActivity;
import com.example.bartomiej.pianistbooster.game_modes.learn_notes_mode.LearnNotesActivity;
import com.example.bartomiej.pianistbooster.old.NotesShowActivity;
import com.example.bartomiej.pianistbooster.game_modes.staff_mode.old.StaffModeActivity;

public class ModeChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_choose);

    }

    public void clickFreestyleButton(View view) {
        Intent myIntent = new Intent(ModeChooseActivity.this, FreestyleActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        ModeChooseActivity.this.startActivity(myIntent);
    }

    public void clickStaffModeButton(View view) {
        Intent myIntent = new Intent(ModeChooseActivity.this, LearnNotesActivity.class);
        ModeChooseActivity.this.startActivity(myIntent);
    }

    public void clickBackButton(View view) {
        finish();
    }
}
