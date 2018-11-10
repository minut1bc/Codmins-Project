package com.codminskeyboards.universekeyboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.SoundAdapter;
import com.codminskeyboards.universekeyboard.model.NewSoundData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;

public class SoundFragment extends Fragment {

    Context context;

    SoundAdapter soundAdapter;
    GridView soundGridView;
    ArrayList<NewSoundData> newSoundDataArrayList = new ArrayList<>();
    SeekBar seekBarVibration;
    String vibrationStrengthText;
    int vibrationValue;
    TextView vibrationTextView;
    SoundPool soundPool;
    private Vibrator vibrator;
    private AudioManager audioManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View soundFragmentView = inflater.inflate(R.layout.sound_fragment, container, false);

        GlobalClass globalClass = new GlobalClass(context);

        soundGridView = soundFragmentView.findViewById(R.id.soundGridView);
        seekBarVibration = soundFragmentView.findViewById(R.id.seekBarVibration);
        vibrationTextView = soundFragmentView.findViewById(R.id.vibrationTextView);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        setSeekBarVibration();

        getSoundFromDatabase();

        return soundFragmentView;
    }

    @SuppressWarnings("deprecation")  // TODO: Check if it's okay
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        soundAdapter = new SoundAdapter(activity, newSoundDataArrayList);
        context = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    private void getSoundFromDatabase() {
        for (int aFreeSoundArray : GlobalClass.soundsArray)
            newSoundDataArrayList.add(new NewSoundData(aFreeSoundArray, false));

        soundGridView.setAdapter(soundAdapter);

        soundGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlobalClass.soundPosition = position;
                GlobalClass.soundId = newSoundDataArrayList.get(position).getResourceId();
                if (position != 0) {
                    performKeySound();
                    GlobalClass.soundStatus = true;
                } else {
                    GlobalClass.soundStatus = false;
                }
                soundAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    void setSeekBarVibration() {
        vibrationValue = GlobalClass.getPreferencesInt(context, GlobalClass.vibrationStrength, 0);

        seekBarVibration.setProgress(vibrationValue);
        vibrationStrengthText = String.valueOf(vibrationValue) + " ms";
        vibrationTextView.setText(vibrationStrengthText);

        seekBarVibration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vibrationStrengthText = String.valueOf(progress) + " ms";
                vibrationTextView.setText(vibrationStrengthText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                vibrator.vibrate(progress);
                GlobalClass.setPreferencesInt(context, GlobalClass.vibrationStrength, progress);
            }
        });
    }

    private void performKeySound() {

        int ringerMode = audioManager.getRingerMode();

        if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            try {
                if (soundPool == null)
                    soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

                // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                final int soundId = soundPool.load(context, GlobalClass.soundId, 1);
                Log.e("baaa", String.valueOf(GlobalClass.soundId));
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(soundId, 1, 1, 0, 0, 1);
                    }
                });
            } catch (Exception ignored) {
            }
        }
    }
}
