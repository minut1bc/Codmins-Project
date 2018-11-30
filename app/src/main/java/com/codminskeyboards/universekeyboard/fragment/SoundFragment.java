package com.codminskeyboards.universekeyboard.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.SoundAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

public class SoundFragment extends Fragment {

    Context context;

    SoundAdapter soundAdapter;
    RecyclerView soundRecyclerView;
    //    ArrayList<NewSoundData> newSoundDataArrayList = new ArrayList<>();
    SeekBar seekBarVibration;
    String vibrationStrengthText;
    TextView vibrationTextView;
    SoundPool soundPool;
    private Vibrator vibrator;
    private AudioManager audioManager;
    CreateKeyboardActivity createKeyboardActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View soundFragmentView = inflater.inflate(R.layout.sound_fragment, container, false);

        soundRecyclerView = soundFragmentView.findViewById(R.id.soundRecyclerView);
        seekBarVibration = soundFragmentView.findViewById(R.id.seekBarVibration);
        vibrationTextView = soundFragmentView.findViewById(R.id.vibrationTextView);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        setSeekBarVibration();

        getSoundFromDatabase();

        return soundFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createKeyboardActivity = (CreateKeyboardActivity) context;
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createKeyboardActivity = null;
        context = null;
    }

    private void getSoundFromDatabase() {
        soundAdapter = new SoundAdapter(context /*, newSoundDataArrayList*/);
//        for (int aFreeSoundArray : GlobalClass.soundsArray) {
//            newSoundDataArrayList.add(new NewSoundData(aFreeSoundArray, false));
//        }

        soundRecyclerView.setHasFixedSize(true);
        soundRecyclerView.setLayoutManager(new GridLayoutManager(context, GlobalClass.calculateNoOfColumns(context)));
        soundRecyclerView.setAdapter(soundAdapter);

        soundRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CreateKeyboardActivity.keyboardData.setSoundPosition(position);
                if (position != 0) {
                    performKeySound();
                    CreateKeyboardActivity.keyboardData.setSoundOn(true);
                } else
                    CreateKeyboardActivity.keyboardData.setSoundOn(false);
                soundAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        }));
    }

    void setSeekBarVibration() {
        seekBarVibration.setProgress(CreateKeyboardActivity.keyboardData.getVibrationValue());
        vibrationStrengthText = String.valueOf(seekBarVibration.getProgress()) + " ms";
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
                if (progress > 0)
                    vibrator.vibrate(progress);
                CreateKeyboardActivity.keyboardData.setVibrationValue(progress);
            }
        });
    }

    private void performKeySound() {
        int ringerMode = audioManager.getRingerMode();

        Log.e("soundStatus", CreateKeyboardActivity.keyboardData.getSoundOn() + "");
        Log.e("soundIndex", CreateKeyboardActivity.keyboardData.getSoundPosition() + "");

        if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            if (soundPool == null)
                soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

            final int soundId = soundPool.load(context, GlobalClass.soundsArray[CreateKeyboardActivity.keyboardData.getSoundPosition()], 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(soundId, 1, 1, 0, 0, 1);
                }
            });
        }
    }
}