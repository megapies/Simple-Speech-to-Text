package com.example.megapiespt.speechtotextdemo2;


import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Button btnOk;
    private Camera cam;
    private SpellChecker spellChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstance();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cam != null){
            cam.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initInstance() {
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnOk = (Button) findViewById(R.id.ok_btn);
        spellChecker = new SpellChecker();

        // hide the action bar
        getSupportActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performOk();
            }
        });
    }

    private void performOk() {
//        String uri = "content://media/internal/images/media";
//        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        Uri number = Uri.parse("tel:5551234");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(intent);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(TextUtils.join(" ", result) + " len " + result.size());
                    castSpell(result);
                }
                break;
            }

        }
    }

    private void castSpell(ArrayList<String> spells){
        String result = spellChecker.castSpell(spells);
        switch (result){
            case SpellChecker.FLASH_LIGHT :
                openFlashLight();
                break;
            case SpellChecker.CLOSE :
                closeFlashLight();
                break;
            case SpellChecker.CAMERA :
                openCamera();
                break;
            case SpellChecker.FACEBOOK :
                openFacebook();
                break;
            case SpellChecker.GALLERY :
                openGallery();
                break;
        }
    }

    private void closeFlashLight() {
        if(cam != null){
            cam.stopPreview();
            cam.release();
        }
    }

    private void openFlashLight() {
        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
    }

    private void openCall(){
        String number = "08000000";
        Uri uri = Uri.parse("tel:" + number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(callIntent);
    }

    private void openCamera(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void openFacebook(){
        String uri = "facebook://facebook.com/inbox";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void openGallery(){
        String uri = "content://media/internal/images/media";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            // TODO handle select item event
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
