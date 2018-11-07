package com.codminskeyboards.universekeyboard.customkeyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.os.Vibrator;
import android.text.InputType;
import android.text.method.MetaKeyKeyListener;
import android.util.Base64;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextServicesManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.FillEmojiAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Example of writing an input method for a soft keyboard.  This code is
 * focused on simplicity over completeness, so it should in no way be considered
 * to be a complete soft keyboard implementation.  Its purpose is to provide
 * a basic example for how you would get started writing an input method, to
 * be fleshed out as appropriate.
 */

public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener,
        SpellCheckerSession.SpellCheckerSessionListener {

    /**
     * This boolean indicates the optional example code for performing
     * processing of hard keys in addition to regular text generation
     * from on-screen interaction.  It would be used for input methods that
     * perform language translations (such as converting text entered on
     * a QWERTY keyboard to Chinese), but may not be used for input methods
     * that are primarily intended to be used for on-screen text entry.
     */
    static final boolean PROCESS_HARD_KEYS = true;
    private InputMethodManager mInputMethodManager;
    private CandidateView mCandidateView;
    private CompletionInfo[] mCompletions;
    private StringBuilder mComposing = new StringBuilder();
    private boolean mPredictionOn;
    private boolean mCompletionOn;
    private int mLastDisplayWidth;
    public static boolean mCapsLock;
    private long mLastShiftTime;
    private long mMetaState;
    private LatinKeyboardView mInputView;
    private LatinKeyboard mSymbolsKeyboard;
    private LatinKeyboard mSymbolsShiftedKeyboard;
    private LatinKeyboard mQwertyKeyboard;
    private LatinKeyboard mQwertyKeyboardShift;
    private LatinKeyboard mCurKeyboard;
    private String mWordSeparators;

    private SpellCheckerSession mScs;
    private List<String> mSuggestions;

    private RelativeLayout linEmoji;
    private ImageView emojiBackspaceImageView;
    private GridView gvEmoji;
    private FillEmojiAdapter fillEmojiAdapter;
    private ImageView ivAbc;
    private ImageView ivSmile;
    private ImageView ivAnimal;
    private ImageView ivLamp;
    private ImageView ivFood;
    private ImageView ivSocial;

    private AudioManager audioManager;
    private Vibrator vibrator;

    private int vibrationStrength;

    Context mContext;
    private String[] emojiArrayList;

    SoundPool soundPool;

    int ringerMode;
    int soundID;

    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = SoftKeyboard.this;
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);

        TextServicesManager tsm = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        if (tsm != null) {
            mScs = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ringerMode = audioManager.getRingerMode();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);

        if (soundPool == null)
            soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * This is the point where you can do all of your UI initialization.  It
     * is called after creation and any configuration change.
     */
    @Override
    public void onInitializeInterface() {
        if (mQwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth)
                return;
            mLastDisplayWidth = displayWidth;
        }
        mQwertyKeyboard = new LatinKeyboard(this, R.xml.qwerty);
        mQwertyKeyboardShift = new LatinKeyboard(this, R.xml.qwerty_shift);
        mSymbolsKeyboard = new LatinKeyboard(this, R.xml.symbols);
        mSymbolsShiftedKeyboard = new LatinKeyboard(this, R.xml.symbols_shift);
    }

    /**
     * Called by the framework when your view for creating input needs to
     * be generated.  This will be called the first time your input method
     * is displayed, and every time it needs to be re-created such as due to
     * a configuration change.
     */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        GlobalClass globalClass = new GlobalClass(SoftKeyboard.this.getApplicationContext());

        final View view = getLayoutInflater().inflate(R.layout.input, null);
        mInputView = view.findViewById(R.id.keyboard);
        LinearLayout linKeyboard = view.findViewById(R.id.linKeyboard);
        linEmoji = view.findViewById(R.id.linEmoji);
        emojiBackspaceImageView = view.findViewById(R.id.emojiBackspaceImageView);
        gvEmoji = view.findViewById(R.id.gvEmoji);
        ivAbc = view.findViewById(R.id.ivAbc);
        ivSmile = view.findViewById(R.id.ivSmile);
        ivAnimal = view.findViewById(R.id.ivAnimal);
        ivLamp = view.findViewById(R.id.ivLamp);
        ivFood = view.findViewById(R.id.ivFood);
        ivSocial = view.findViewById(R.id.ivSocial);
        LinearLayout linCategory = view.findViewById(R.id.linCategory);

        vibrationStrength = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.vibrationStrength, 0);

        GlobalClass.soundId = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SOUND_NAME, R.raw.balloon_snap);

        emojiArrayList = getResources().getStringArray(R.array.smile);
        fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
        gvEmoji.setAdapter(fillEmojiAdapter);

        ivSmile.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivAnimal.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivLamp.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivFood.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivSocial.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        emojiBackspaceImageView.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);

        for (int i = 0; i < linCategory.getChildCount(); i++) {
            final View mChild = linCategory.getChildAt(i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {

                GradientDrawable npd1;
                if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, mContext.getResources().getColor(R.color.eight)) == R.color.white) {
                    npd1 = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{mContext.getResources().getColor(R.color.eight),
                                    mContext.getResources().getColor(R.color.eight)});

                } else {
                    npd1 = new GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            new int[]{GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, mContext.getResources().getColor(R.color.eight)),
                                    GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, mContext.getResources().getColor(R.color.eight))});

                }

                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);

                npd1.setCornerRadius(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_RADIUS, 18));
                npd1.setAlpha(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_OPACITY, 255));

                switch (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_STROKE, 2)) {
                    case 1:
                        npd1.setStroke(0, getApplicationContext().getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case 3:
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case 4:
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case 5:
                        npd1.setStroke(3, android.graphics.Color.GRAY);
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")));
                    if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "").length() != 0 && GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "") != null
                            && !GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "").isEmpty()) {
                        try {

                            Typeface font = Typeface.createFromAsset(this.getAssets(), GlobalClass.tempFontName);
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        ivSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.smile);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                gvEmoji.setAdapter(fillEmojiAdapter);

                ivSmile.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_bold));
                ivAnimal.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                ivLamp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                ivSocial.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                ivSmile.setColorFilter(mContext.getResources().getColor(R.color.white));
                ivAnimal.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivLamp.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivFood.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivSocial.setColorFilter(mContext.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.animal);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                gvEmoji.setAdapter(fillEmojiAdapter);

                ivSmile.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                ivAnimal.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal_bold));
                ivLamp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                ivSocial.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                ivSmile.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivAnimal.setColorFilter(mContext.getResources().getColor(R.color.white));
                ivLamp.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivFood.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivSocial.setColorFilter(mContext.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.lamp);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                gvEmoji.setAdapter(fillEmojiAdapter);

                ivSmile.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                ivAnimal.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                ivLamp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_bold));
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                ivSocial.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                ivSmile.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivAnimal.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivLamp.setColorFilter(mContext.getResources().getColor(R.color.white));
                ivFood.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivSocial.setColorFilter(mContext.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.food);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                gvEmoji.setAdapter(fillEmojiAdapter);

                ivSmile.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                ivAnimal.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                ivLamp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_food_bold));
                ivSocial.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                ivSmile.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivAnimal.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivLamp.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivFood.setColorFilter(mContext.getResources().getColor(R.color.white));
                ivSocial.setColorFilter(mContext.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.social);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                gvEmoji.setAdapter(fillEmojiAdapter);

                ivSmile.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                ivAnimal.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                ivLamp.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                ivSocial.setImageDrawable(getResources().getDrawable(R.drawable.ic_building_bold));

                ivSmile.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivAnimal.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivLamp.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivFood.setColorFilter(mContext.getResources().getColor(R.color.silver));
                ivSocial.setColorFilter(mContext.getResources().getColor(R.color.white));
                emojiBackspaceImageView.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        gvEmoji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPredictionOn) {
                    mComposing.append(emojiArrayList[position]);
                    getCurrentInputConnection().setComposingText(mComposing, 1);
                    updateShiftKeyState(getCurrentInputEditorInfo());
                    updateCandidates();
                } else {
                    getCurrentInputConnection().commitText(emojiArrayList[position], 1);
                }
            }
        });

        ivAbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmoji();
            }
        });

        emojiBackspaceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackspace();
                keyDownUp(KeyEvent.KEYCODE_DEL);
            }
        });

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null) != null) {
            byte[] decodedString = Base64.decode(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            BitmapDrawable background = new BitmapDrawable(mContext.getResources(), decodedByte);
            linKeyboard.setBackground(background);
        } else {
            linKeyboard.setBackgroundResource(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_BG_IMAGE, R.drawable.background_1));
        }

        for (Keyboard.Key k : mQwertyKeyboard.getKeys()) {
            switch (k.codes[0]) {
                case Keyboard.KEYCODE_SHIFT /*-978903*/:
                    k.icon = null;
                    break;
                case 10 /*-978903*/:
                    k.icon = null;
                    break;
                case Keyboard.KEYCODE_DELETE /*-978903*/:
                    k.icon = null;
                    break;

                default:
                    break;
            }
        }
        mInputView.setOnKeyboardActionListener(this);
        setLatinKeyboard(mQwertyKeyboard);
        mInputView.setPreviewEnabled(false);
        return view;
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        mInputView.setKeyboard(nextKeyboard);
    }

    /**
     * Called by the framework when your view for showing candidates needs to
     * be generated, like {@link #onCreateInputView}.
     */
    @Override
    public View onCreateCandidatesView() {
        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        return mCandidateView;
    }

    /**
     * This is the main point where we do our initialization of the input method
     * to begin operating on an application.  At this point we have been
     * bound to the client, and are now receiving all of the detailed information
     * about the target of our edits.
     */
    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);


        // Reset our state.  We want to do this even if restarting, because
        // the underlying state of the text editor could have changed in any way.
        mComposing.setLength(0);
        updateCandidates();

        if (!restarting) {
            // Clear shift states.
            mMetaState = 0;
        }

        mPredictionOn = false;
        mCompletionOn = false;
        mCompletions = null;

        // We are now going to initialize our state based on the type of
        // text being edited.
        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
                // Numbers and dates default to the symbols keyboard, with
                // no extra features.
                mCurKeyboard = mSymbolsKeyboard;
                break;

            case InputType.TYPE_CLASS_PHONE:
                // Phones will also default to the symbols keyboard, though
                // often you will want to have a dedicated phone keyboard.
                mCurKeyboard = mSymbolsKeyboard;
                break;

            case InputType.TYPE_CLASS_TEXT:
                // This is general text editing.  We will default to the
                // normal alphabetic keyboard, and assume that we should
                // be doing predictive text (showing candidates as the
                // user types).
                mCurKeyboard = mQwertyKeyboard;
                mPredictionOn = false;

                // We now look for a few special variations of text that will
                // modify our behavior.
                int variation = attribute.inputType & InputType.TYPE_MASK_VARIATION;
                if (variation == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
                        variation == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Do not display predictions / what the user is typing
                    // when they are entering a password.
                    mPredictionOn = false;
                }

                if (variation == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        || variation == InputType.TYPE_TEXT_VARIATION_URI
                        || variation == InputType.TYPE_TEXT_VARIATION_FILTER) {
                    // Our predictions are not useful for e-mail addresses
                    // or URIs.
                    mPredictionOn = false;
                }

                if ((attribute.inputType & InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    // If this is an auto-complete text view, then our predictions
                    // will not be shown and instead we will allow the editor
                    // to supply their own.  We only show the editor's
                    // candidates when in fullscreen mode, otherwise relying
                    // own it displaying its own UI.
                    mPredictionOn = false;
                    mCompletionOn = isFullscreenMode();
                }

                // We also want to look at the current state of the editor
                // to decide whether our alphabetic keyboard should start out
                // shifted.
                updateShiftKeyState(attribute);
                break;

            default:
                // For all unknown input types, default to the alphabetic
                // keyboard with no special features.
                mCurKeyboard = mQwertyKeyboard;
                updateShiftKeyState(attribute);
        }

        // Update the label on the enter key, depending on what the application
        // says it will do.
        mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }

    /**
     * This is called when the user is done editing a field.  We can use
     * this to reset our state.
     */
    @Override
    public void onFinishInput() {

        super.onFinishInput();
        GlobalClass.printLog("SoftKeyboard", "---------------onFinishInput---------------");

        // Clear current composing text and candidates.
        mComposing.setLength(0);
        updateCandidates();

        // We only hide the candidates window when finishing input on
        // a particular editor, to avoid popping the underlying application
        // up and down if the user is entering text into the bottom of
        // its window.
        setCandidatesViewShown(false);

        mCurKeyboard = mQwertyKeyboard;
        if (mInputView != null) {
            mInputView.closing();
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        // Apply the selected keyboard to the input view.
        setLatinKeyboard(mCurKeyboard);
        mInputView.closing();
        final InputMethodSubtype subtype = mInputMethodManager.getCurrentInputMethodSubtype();
        mInputView.setSubtypeOnSpaceKey(subtype);
        setInputView(onCreateInputView());
    }

    @Override
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        if (mInputView != null)
            mInputView.setSubtypeOnSpaceKey(subtype);
    }

    /**
     * Deal with the editor reporting movement of its cursor.
     */
    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
                                  int newSelStart, int newSelEnd,
                                  int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
                candidatesStart, candidatesEnd);

        // If the current selection in the text view changes, we should
        // clear whatever candidate text we have.
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            updateCandidates();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    /**
     * This tells us about completions that the editor has determined based
     * on the current text in it.  We want to use this in fullscreen mode
     * to show the completions ourself, since the editor can not be seen
     * in that situation.
     */
    @Override
    public void onDisplayCompletions(CompletionInfo[] completions) {
        GlobalClass.printLog("SoftKeyboard", "---------------onDisplayCompletions---------------");

        if (mCompletionOn) {
            mCompletions = completions;
            if (completions == null) {
                setSuggestions(null, false, false);
                return;
            }

            List<String> stringList = new ArrayList<>();
            for (CompletionInfo ci : completions) {
                if (ci != null) stringList.add(ci.getText().toString());
            }
            setSuggestions(stringList, true, true);
        }
    }

    /**
     * This translates incoming hard key events in to edit operations on an
     * InputConnection.  It is only needed when using the
     * PROCESS_HARD_KEYS option.
     */
    private boolean translateKeyDown(int keyCode, KeyEvent event) {
        mMetaState = MetaKeyKeyListener.handleKeyDown(mMetaState,
                keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(mMetaState));
        mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(mMetaState);
        InputConnection ic = getCurrentInputConnection();
        if (c == 0 || ic == null) {
            return false;
        }

        boolean dead = false;
        if ((c & KeyCharacterMap.COMBINING_ACCENT) != 0) {
            dead = true;
            c = c & KeyCharacterMap.COMBINING_ACCENT_MASK;
        }

        if (mComposing.length() > 0) {
            char accent = mComposing.charAt(mComposing.length() - 1);
            int composed = KeyEvent.getDeadChar(accent, c);
            if (composed != 0) {
                c = composed;
                mComposing.setLength(mComposing.length() - 1);
            }
        }

        onKey(c, null);

        return true;
    }

    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        GlobalClass.printLog("SoftKeyboard", "---------------onKeyDown---------------");

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                // The InputMethodService already takes care of the back
                // key for us, to dismiss the input method if it is shown.
                // However, our keyboard could be showing a pop-up window
                // that back should dismiss, so we first allow it to do that.
                if (event.getRepeatCount() == 0 && mInputView != null) {
                    if (mInputView.handleBack()) {
                        return true;
                    }
                }
                break;

            case KeyEvent.KEYCODE_DEL:

                // Special handling of the delete key: if we currently are
                // composing text for the user, we want to modify that instead
                // of let the application to the delete itself.
                if (mComposing.length() > 0) {
                    onKey(Keyboard.KEYCODE_DELETE, null);
                    return true;
                }
                break;

            case KeyEvent.KEYCODE_ENTER:
                // Let the underlying text editor always handle these.
                return false;

            default:
                // For all other keys, if we want to do transformations on
                // text being entered with a hard keyboard, we need to process
                // it and do the appropriate action.
                if (PROCESS_HARD_KEYS) {
                    if (keyCode == KeyEvent.KEYCODE_SPACE
                            && (event.getMetaState() & KeyEvent.META_ALT_ON) != 0) {
                        // A silly example: in our input method, Alt+Space
                        // is a shortcut for 'android' in lower case.
                        InputConnection ic = getCurrentInputConnection();
                        if (ic != null) {
                            // First, tell the editor that it is no longer in the
                            // shift state, since we are consuming this.
                            ic.clearMetaKeyStates(KeyEvent.META_ALT_ON);
                            keyDownUp(KeyEvent.KEYCODE_A);
                            keyDownUp(KeyEvent.KEYCODE_N);
                            keyDownUp(KeyEvent.KEYCODE_D);
                            keyDownUp(KeyEvent.KEYCODE_R);
                            keyDownUp(KeyEvent.KEYCODE_O);
                            keyDownUp(KeyEvent.KEYCODE_I);
                            keyDownUp(KeyEvent.KEYCODE_D);
                            // And we consume this event.
                            return true;
                        }
                    }
                    if (mPredictionOn && translateKeyDown(keyCode, event)) {
                        return true;
                    }
                }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Use this to monitor key events being delivered to the application.
     * We get first crack at them, and can either resume them or let them
     * continue to the app.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        GlobalClass.printLog("SoftKeyboard", "---------------onKeyUp---------------");

        // If we want to do transformations on text being entered with a hard
        // keyboard, we need to process the up events to update the meta key
        // state we are tracking.
        if (PROCESS_HARD_KEYS) {
            if (mPredictionOn) {
                mMetaState = MetaKeyKeyListener.handleKeyUp(mMetaState, keyCode, event);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        GlobalClass.printLog("SoftKeyboard", "---------------commitTyped---------------");

        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            updateCandidates();
        }
    }

    /**
     * Helper to update the shift state of our keyboard based on the initial
     * editor state.
     */
    private void updateShiftKeyState(EditorInfo attr) {
        GlobalClass.printLog("SoftKeyboard", "---------------updateShiftKeyState---------------");

        if (attr != null && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {// TODO: Check if mQwertyKeyboardShift condition not needed
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }

    }

    /**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        return Character.isLetter(code);
    }

    /**
     * Helper to send a key down / key up pair to the current editor.
     */
    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }

    /**
     * Helper to send a character to the editor as raw key events.
     */
    private void sendKey(int keyCode) {

        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
    }

    // Implementation of KeyboardViewListener

    public void onKey(int primaryCode, int[] keyCodes) {

        if (isWordSeparator(primaryCode)) {
            // Handle separator
            if (mComposing.length() > 0) {
                commitTyped(getCurrentInputConnection());
            }
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            handleBackspace();
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            handleShift();
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            handleClose();
        } else if (primaryCode == LatinKeyboardView.KEYCODE_LANGUAGE_SWITCH) {
            handleLanguageSwitch();
        } else if (primaryCode == LatinKeyboardView.KEYCODE_OPTIONS) {
            // Show a menu or something
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE && mInputView != null) {
            Keyboard current = mInputView.getKeyboard();
            if (current == mSymbolsKeyboard || current == mSymbolsShiftedKeyboard) {
                setLatinKeyboard(mQwertyKeyboard);
            } else {
                setLatinKeyboard(mSymbolsKeyboard);
                mSymbolsKeyboard.setShifted(false);
            }
        } else if (primaryCode == LatinKeyboardView.KEYCODE_EMOJI) {
            handleEmoji();
        } else {
            handleCharacter(primaryCode, keyCodes);
        }
    }

    public void onText(CharSequence text) {
        GlobalClass.printLog("SoftKeyboard", "---------------onText---------------");

        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) {
            commitTyped(ic);
        }
        ic.commitText(text, 0);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    /**
     * Update the list of available candidates from the current composing
     * text.  This will need to be filled in by however you are determining
     * candidates.
     */
    private void updateCandidates() {
        GlobalClass.printLog("SoftKeyboard", "---------------updateCandidates---------------");

        if (!mCompletionOn) {
            if (mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<>();
                //mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(mComposing.toString())}, 5);  TODO: Re-enable and make mScs not null (currently always null)
                list.add(mComposing.toString());
                setSuggestions(list, true, true);
            } else {
                setSuggestions(null, false, false);
            }
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {

        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(true);
        } else if (isExtractViewShown()) {
            setCandidatesViewShown(true);

        }
        mSuggestions = suggestions;
        if (mCandidateView != null) {
            mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }

    private void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleShift() {

        if (mInputView == null) {
            return;
        }

        Keyboard currentKeyboard = mInputView.getKeyboard();
        if (mQwertyKeyboard == currentKeyboard || mQwertyKeyboardShift == currentKeyboard) {
            if (mCapsLock) {
                setLatinKeyboard(mQwertyKeyboard);
                mInputView.setShifted(false);
                mCapsLock = false;
            } else {
                if (mQwertyKeyboard == currentKeyboard) {
                    checkToggleCapsLock();
                    setLatinKeyboard(mQwertyKeyboardShift);
                    mInputView.setShifted(true);
                } else {
                    checkToggleCapsLock();
                    mInputView.setShifted(mCapsLock);
                    if (!mInputView.isShifted())
                        setLatinKeyboard(mQwertyKeyboard);
                }
            }
        } else if (currentKeyboard == mSymbolsKeyboard) {
            mSymbolsKeyboard.setShifted(true);
            setLatinKeyboard(mSymbolsShiftedKeyboard);
            mSymbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == mSymbolsShiftedKeyboard) {
            mSymbolsShiftedKeyboard.setShifted(false);
            setLatinKeyboard(mSymbolsKeyboard);
            mSymbolsKeyboard.setShifted(false);
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if (!mCapsLock && mInputView.getKeyboard() != mSymbolsShiftedKeyboard) {
                    setLatinKeyboard(mQwertyKeyboard);
                    mInputView.setShifted(!mInputView.isShifted());
                }
            }
        }
        if (mPredictionOn) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        } else {
            getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        mInputView.closing();
    }

    private IBinder getToken() {

        final Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }

    private void handleLanguageSwitch() {
        mInputMethodManager.switchToNextInputMethod(getToken(), false /* onlyCurrentIme */);
    }

    private void handleEmoji() {
        if (mInputView.getVisibility() == View.VISIBLE) {
            mInputView.setVisibility(View.GONE);
            linEmoji.setVisibility(View.VISIBLE);
        } else {
            mInputView.setVisibility(View.VISIBLE);
            linEmoji.setVisibility(View.GONE);
        }
    }

    private void checkToggleCapsLock() {
        GlobalClass.printLog("SoftKeyboard", "---------------checkToggleCapsLock---------------");

        long now = System.currentTimeMillis();
        if (mLastShiftTime + 800 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;

        } else if (!mInputView.isShifted()) {
            mLastShiftTime = now;
        }
    }

    private String getWordSeparators() {
        GlobalClass.printLog("SoftKeyboard", "---------------getWordSeparators---------------");

        return mWordSeparators;
    }

    public boolean isWordSeparator(int code) {
        GlobalClass.printLog("SoftKeyboard", "---------------isWordSeparator---------------");

        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char) code));
    }

    public void pickDefaultCandidate() {
        GlobalClass.printLog("SoftKeyboard", "---------------pickDefaultCandidate---------------");

        pickSuggestionManually(0);
    }

    public void pickSuggestionManually(int index) {
        GlobalClass.printLog("SoftKeyboard", "---------------pickSuggestionManually---------------");

        if (mCompletionOn && mCompletions != null && index >= 0
                && index < mCompletions.length) {
            CompletionInfo ci = mCompletions[index];
            getCurrentInputConnection().commitCompletion(ci);
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (mComposing.length() > 0) {

            if (mPredictionOn && mSuggestions != null && index >= 0) {
                mComposing.replace(0, mComposing.length(), mSuggestions.get(index));
            }
            commitTyped(getCurrentInputConnection());

        }
    }

    public void swipeRight() {
        GlobalClass.printLog("SoftKeyboard", "---------------swipeRight---------------");

        Log.d("SoftKeyboard", "Swipe right");
        if (mCompletionOn) {
            pickDefaultCandidate();
        }
    }

    public void swipeLeft() {
        GlobalClass.printLog("SoftKeyboard", "---------------swipeLeft---------------");

        Log.d("SoftKeyboard", "Swipe left");
        handleBackspace();
    }

    public void swipeDown() {
        GlobalClass.printLog("SoftKeyboard", "---------------swipeDown---------------");

        handleClose();
    }

    public void swipeUp() {
        GlobalClass.printLog("SoftKeyboard", "---------------swipeUp---------------");
    }

    public void onPress(int primaryCode) {

        GlobalClass.printLog("SoftKeyboard", "---------------onPress---------------" + String.valueOf(GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_STATUS, false)));

        Log.e("KEYBOARD", "hello" + String.valueOf(GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_STATUS, false)));

        if (GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_STATUS, false))
            performKeySound();

        if (vibrationStrength != 0)
            performKeyVibration(vibrationStrength);

        // Disable preview key on Shift, Delete, Symbol, Language Switch, Space and Enter.
        if (primaryCode == -1 || primaryCode == -5 || primaryCode == -2 || primaryCode == -101 || primaryCode == 32 || primaryCode == 10)
            mInputView.setPreviewEnabled(false);
        else {
            //mInputView.setPreviewEnabled(true); TODO: Fix Preview
        }

    }

    public void onRelease(int primaryCode) {
        GlobalClass.printLog("SoftKeyboard", "---------------onRelease---------------");
        mInputView.setPreviewEnabled(false);
    }

    /**
     * http://www.tutorialspoint.com/android/android_spelling_checker.htm
     *
     * @param results results
     */
    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        GlobalClass.printLog("SoftKeyboard", "---------------onGetSuggestions---------------");

        final StringBuilder sb = new StringBuilder();

        for (SuggestionsInfo result : results) {
            // Returned suggestions are contained in SuggestionsInfo
            final int len = result.getSuggestionsCount();
            sb.append('\n');

            for (int j = 0; j < len; ++j) {
                sb.append(",").append(result.getSuggestionAt(j));
            }

            sb.append(" (").append(len).append(")");
        }
        Log.d("SoftKeyboard", "SUGGESTIONS: " + sb.toString());
    }

    private void dumpSuggestionsInfoInternal(
            final List<String> sb, final SuggestionsInfo si, final int length, final int offset) {
        // Returned suggestions are contained in SuggestionsInfo
        GlobalClass.printLog("SoftKeyboard", "---------------dumpSuggestionsInfoInternal---------------");

        final int len = si.getSuggestionsCount();
        for (int j = 0; j < len; ++j) {
            sb.add(si.getSuggestionAt(j));
        }
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        GlobalClass.printLog("SoftKeyboard", "---------------onGetSentenceSuggestions---------------");

        Log.d("SoftKeyboard", "onGetSentenceSuggestions");
        final List<String> sb = new ArrayList<>();
        for (final SentenceSuggestionsInfo ssi : results) {
            for (int i = 0; i < ssi.getSuggestionsCount(); ++i) {
                dumpSuggestionsInfoInternal(
                        sb, ssi.getSuggestionsInfoAt(i), ssi.getOffsetAt(i), ssi.getLengthAt(i));
            }
        }
        Log.d("SoftKeyboard", "SUGGESTIONS: " + sb.toString());
        setSuggestions(sb, true, true);
    }

    private void performKeySound() {
        GlobalClass.printLog("SoftKeyboard", "---------------performKeySound---------------");

        if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {

            soundID = soundPool.load(this, GlobalClass.soundId, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(soundID, 1, 1, 0, 0, 1);
                }
            });
        }
    }

    private void performKeyVibration(int duration) {
        int ringerMode = audioManager.getRingerMode();
        if (ringerMode == AudioManager.RINGER_MODE_NORMAL || ringerMode == AudioManager.RINGER_MODE_VIBRATE)
            vibrator.vibrate(duration);
    }

}