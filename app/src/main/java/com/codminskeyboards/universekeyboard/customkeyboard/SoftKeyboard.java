package com.codminskeyboards.universekeyboard.customkeyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.text.method.MetaKeyKeyListener;
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
    public static boolean capsLock;
    Context context;
    private InputMethodManager inputMethodManager;
    private CandidateView candidateView;
    private CompletionInfo[] completions;
    private StringBuilder composing = new StringBuilder();
    private boolean predictionOn;
    private boolean completionOn;
    private int lastDisplayWidth;
    private long lastShiftTime;
    private long metaState;
    private LatinKeyboardView inputView;
    private LatinKeyboard symbolsKeyboard;
    private LatinKeyboard symbolsShiftedKeyboard;
    private LatinKeyboard qwertyKeyboard;
    private LatinKeyboard qwertyShiftedKeyboard;
    private LatinKeyboard currentKeyboard;
    private String wordSeparators;
    private SpellCheckerSession spellCheckerSession;

    private ConstraintLayout emojiConstraintLayout;
    private ImageView emojiBackspaceImageView;
    private List<String> suggestions;
    private FillEmojiAdapter fillEmojiAdapter;
    private GridView emojiGridView;
    private ImageView smileImageView;
    private ImageView animalImageView;
    private ImageView lampImageView;
    private ImageView foodImageView;

    private AudioManager audioManager;
    private Vibrator vibrator;

    private int vibrationValue;
    private ImageView socialImageView;
    private String[] emojiArrayList;

    SoundPool soundPool;

    int ringerMode;

    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        context = SoftKeyboard.this;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        wordSeparators = getResources().getString(R.string.word_separators);

        TextServicesManager textServicesManager = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        if (textServicesManager != null)
            spellCheckerSession = textServicesManager.newSpellCheckerSession(null, Locale.ENGLISH, this, true);

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
        if (qwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == lastDisplayWidth)
                return;
            lastDisplayWidth = displayWidth;
        }
        qwertyKeyboard = new LatinKeyboard(context, R.xml.qwerty);
        qwertyShiftedKeyboard = new LatinKeyboard(context, R.xml.qwerty_shift);
        symbolsKeyboard = new LatinKeyboard(context, R.xml.symbols);
        symbolsShiftedKeyboard = new LatinKeyboard(context, R.xml.symbols_shift);
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
        GlobalClass globalClass = new GlobalClass(context);

        final View view = getLayoutInflater().inflate(R.layout.input, null);

        inputView = view.findViewById(R.id.keyboard);
        LinearLayout linKeyboard = view.findViewById(R.id.linKeyboard);
        emojiConstraintLayout = view.findViewById(R.id.emojiConstraintLayout);
        emojiBackspaceImageView = view.findViewById(R.id.emojiBackspaceImageView);
        emojiGridView = view.findViewById(R.id.emojiGridView);
        ImageView abcImageView = view.findViewById(R.id.abcImageView);
        smileImageView = view.findViewById(R.id.smileImageView);
        animalImageView = view.findViewById(R.id.animalImageView);
        lampImageView = view.findViewById(R.id.lampImageView);
        foodImageView = view.findViewById(R.id.foodImageView);
        socialImageView = view.findViewById(R.id.socialImageView);

        vibrationValue = GlobalClass.getPreferencesInt(context, GlobalClass.VIBRATION_VALUE, 0);

        emojiArrayList = getResources().getStringArray(R.array.smile);
        fillEmojiAdapter = new FillEmojiAdapter(context, emojiArrayList);
        emojiGridView.setAdapter(fillEmojiAdapter);

        int fontColor = getResources().getColor(GlobalClass.colorsArray[GlobalClass.fontColorPosition]);

        smileImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
        animalImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
        lampImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
        foodImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
        socialImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
        emojiBackspaceImageView.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);

        int keyColor = getResources().getColor(GlobalClass.colorsArray[GlobalClass.getPreferencesInt(context, GlobalClass.KEY_COLOR_POSITION, 1)]);
        for (int i = 0; i < emojiConstraintLayout.getChildCount(); i++) {
            final View child = emojiConstraintLayout.getChildAt(i);
            if (child instanceof ImageView) {
                GradientDrawable keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5, child.getRight() - 5, child.getBottom() - 5);
                keyBackground.setCornerRadius(GlobalClass.keyRadius);
                keyBackground.setAlpha(GlobalClass.keyOpacity);

                switch (GlobalClass.keyStroke) {
                    case 1:
                        keyBackground.setStroke(0, getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        keyBackground.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case 3:
                        keyBackground.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case 4:
                        keyBackground.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case 5:
                        keyBackground.setStroke(3, android.graphics.Color.GRAY);
                        break;
                }

                child.setBackground(keyBackground);
            }
        }

        smileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.smile);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                emojiGridView.setAdapter(fillEmojiAdapter);

                smileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_bold));
                animalImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                lampImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                foodImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                socialImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                smileImageView.setColorFilter(getResources().getColor(R.color.white));
                animalImageView.setColorFilter(getResources().getColor(R.color.silver));
                lampImageView.setColorFilter(getResources().getColor(R.color.silver));
                foodImageView.setColorFilter(getResources().getColor(R.color.silver));
                socialImageView.setColorFilter(getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(getResources().getColor(R.color.silver));
            }
        });

        animalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.animal);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                emojiGridView.setAdapter(fillEmojiAdapter);

                smileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                animalImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal_bold));
                lampImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                foodImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                socialImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                smileImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                animalImageView.setColorFilter(context.getResources().getColor(R.color.white));
                lampImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                foodImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                socialImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(context.getResources().getColor(R.color.silver));
            }
        });

        lampImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.lamp);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                emojiGridView.setAdapter(fillEmojiAdapter);

                smileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                animalImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                lampImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp_bold));
                foodImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                socialImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                smileImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                animalImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                lampImageView.setColorFilter(context.getResources().getColor(R.color.white));
                foodImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                socialImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(context.getResources().getColor(R.color.silver));
            }
        });

        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.food);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                emojiGridView.setAdapter(fillEmojiAdapter);

                smileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                animalImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                lampImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                foodImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food_bold));
                socialImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_building));

                smileImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                animalImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                lampImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                foodImageView.setColorFilter(context.getResources().getColor(R.color.white));
                socialImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                emojiBackspaceImageView.setColorFilter(context.getResources().getColor(R.color.silver));
            }
        });

        socialImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = getResources().getStringArray(R.array.social);
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
                emojiGridView.setAdapter(fillEmojiAdapter);

                smileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_smile_one));
                animalImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_animal));
                lampImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_lamp));
                foodImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_food));
                socialImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_building_bold));

                smileImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                animalImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                lampImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                foodImageView.setColorFilter(context.getResources().getColor(R.color.silver));
                socialImageView.setColorFilter(context.getResources().getColor(R.color.white));
                emojiBackspaceImageView.setColorFilter(context.getResources().getColor(R.color.silver));
            }
        });

        emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (predictionOn) {
                    composing.append(emojiArrayList[position]);
                    getCurrentInputConnection().setComposingText(composing, 1);
                    updateShiftKeyState(getCurrentInputEditorInfo());
                    updateCandidates();
                } else
                    getCurrentInputConnection().commitText(emojiArrayList[position], 1);
            }
        });

        abcImageView.setOnClickListener(new View.OnClickListener() {
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

        boolean backgroundIsDrawable = GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.BACKGROUND_IS_DRAWABLE, true);

        if (backgroundIsDrawable) {
            linKeyboard.setBackgroundResource((GlobalClass.backgroundArray[GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.BACKGROUND_POSITION, 0)]));
        } else {
            linKeyboard.setBackgroundResource((GlobalClass.colorsArray[GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.BACKGROUND_COLOR_POSITION, 0)]));
        }

        for (Keyboard.Key k : qwertyKeyboard.getKeys()) {
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
        inputView.setOnKeyboardActionListener(this);
        setLatinKeyboard(qwertyKeyboard);
        inputView.setPreviewEnabled(false);
        return view;
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        inputView.setKeyboard(nextKeyboard);
    }

    /**
     * Called by the framework when your view for showing candidates needs to
     * be generated, like {@link #onCreateInputView}.
     */
    @Override
    public View onCreateCandidatesView() {
        candidateView = new CandidateView(context);
        candidateView.setService(this);
        return candidateView;
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
        composing.setLength(0);
        updateCandidates();

        if (!restarting) {
            // Clear shift states.
            metaState = 0;
        }

        predictionOn = false;
        completionOn = false;
        completions = null;

        // We are now going to initialize our state based on the type of
        // text being edited.
        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
                // Numbers and dates default to the symbols keyboard, with
                // no extra features.
                currentKeyboard = symbolsKeyboard;
                break;

            case InputType.TYPE_CLASS_PHONE:
                // Phones will also default to the symbols keyboard, though
                // often you will want to have a dedicated phone keyboard.
                currentKeyboard = symbolsKeyboard;
                break;

            case InputType.TYPE_CLASS_TEXT:
                // This is general text editing.  We will default to the
                // normal alphabetic keyboard, and assume that we should
                // be doing predictive text (showing candidates as the
                // user types).
                currentKeyboard = qwertyKeyboard;
                predictionOn = false;

                // We now look for a few special variations of text that will
                // modify our behavior.
                int variation = attribute.inputType & InputType.TYPE_MASK_VARIATION;
                if (variation == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
                        variation == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Do not display predictions / what the user is typing
                    // when they are entering a password.
                    predictionOn = false;
                }

                if (variation == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        || variation == InputType.TYPE_TEXT_VARIATION_URI
                        || variation == InputType.TYPE_TEXT_VARIATION_FILTER) {
                    // Our predictions are not useful for e-mail addresses
                    // or URIs.
                    predictionOn = false;
                }

                if ((attribute.inputType & InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    // If this is an auto-complete text view, then our predictions
                    // will not be shown and instead we will allow the editor
                    // to supply their own.  We only show the editor's
                    // candidates when in fullscreen mode, otherwise relying
                    // own it displaying its own UI.
                    predictionOn = false;
                    completionOn = isFullscreenMode();
                }

                // We also want to look at the current state of the editor
                // to decide whether our alphabetic keyboard should start out
                // shifted.
                updateShiftKeyState(attribute);
                break;

            default:
                // For all unknown input types, default to the alphabetic
                // keyboard with no special features.
                currentKeyboard = qwertyKeyboard;
                updateShiftKeyState(attribute);
        }

        // Update the label on the enter key, depending on what the application
        // says it will do.
        currentKeyboard.setImeOptions(getResources(), attribute.imeOptions);
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
        composing.setLength(0);
        updateCandidates();

        // We only hide the candidates window when finishing input on
        // a particular editor, to avoid popping the underlying application
        // up and down if the user is entering text into the bottom of
        // its window.
        setCandidatesViewShown(false);

        currentKeyboard = qwertyKeyboard;
        if (inputView != null) {
            inputView.closing();
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        // Apply the selected keyboard to the input view.
        setLatinKeyboard(currentKeyboard);
        inputView.closing();
        final InputMethodSubtype subtype = inputMethodManager.getCurrentInputMethodSubtype();
        inputView.setSubtypeOnSpaceKey(subtype);
        setInputView(onCreateInputView());
    }

    @Override
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        if (inputView != null)
            inputView.setSubtypeOnSpaceKey(subtype);
    }

    /**
     * Deal with the editor reporting movement of its cursor.
     */
    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        // If the current selection in the text view changes, we should
        // clear whatever candidate text we have.
        if (composing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            composing.setLength(0);
            updateCandidates();
            InputConnection inputConnection = getCurrentInputConnection();
            if (inputConnection != null)
                inputConnection.finishComposingText();
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

        if (completionOn) {
            this.completions = completions;
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
        metaState = MetaKeyKeyListener.handleKeyDown(metaState, keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(metaState));
        metaState = MetaKeyKeyListener.adjustMetaAfterKeypress(metaState);
        InputConnection inputConnection = getCurrentInputConnection();
        if (c == 0 || inputConnection == null)
            return false;

        boolean dead = false;
        if ((c & KeyCharacterMap.COMBINING_ACCENT) != 0) {
            dead = true;
            c = c & KeyCharacterMap.COMBINING_ACCENT_MASK;
        }

        if (composing.length() > 0) {
            char accent = composing.charAt(composing.length() - 1);
            int composed = KeyEvent.getDeadChar(accent, c);
            if (composed != 0) {
                c = composed;
                composing.setLength(composing.length() - 1);
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
                if (event.getRepeatCount() == 0 && inputView != null)
                    if (inputView.handleBack())
                        return true;
                break;

            case KeyEvent.KEYCODE_DEL:

                // Special handling of the delete key: if we currently are
                // composing text for the user, we want to modify that instead
                // of let the application to the delete itself.
                if (composing.length() > 0) {
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
                    if (keyCode == KeyEvent.KEYCODE_SPACE && (event.getMetaState() & KeyEvent.META_ALT_ON) != 0) {
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
                    if (predictionOn && translateKeyDown(keyCode, event))
                        return true;
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
        if (PROCESS_HARD_KEYS)
            if (predictionOn)
                metaState = MetaKeyKeyListener.handleKeyUp(metaState, keyCode, event);

        return super.onKeyUp(keyCode, event);
    }

    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        GlobalClass.printLog("SoftKeyboard", "---------------commitTyped---------------");

        if (composing.length() > 0) {
            inputConnection.commitText(composing, composing.length());
            composing.setLength(0);
            updateCandidates();
        }
    }

    /**
     * Helper to update the shift state of our keyboard based on the initial
     * editor state.
     */
    private void updateShiftKeyState(EditorInfo attr) {
        GlobalClass.printLog("SoftKeyboard", "---------------updateShiftKeyState---------------");

        if (attr != null && inputView != null && qwertyKeyboard == inputView.getKeyboard()) {       // TODO: Check if qwertyShiftedKeyboard condition not needed
            int caps = 0;
            EditorInfo editorInfo = getCurrentInputEditorInfo();
            if (editorInfo != null && editorInfo.inputType != InputType.TYPE_NULL)
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            inputView.setShifted(capsLock || caps != 0);
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
            if (composing.length() > 0)
                commitTyped(getCurrentInputConnection());
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == Keyboard.KEYCODE_DELETE)
            handleBackspace();
        else if (primaryCode == Keyboard.KEYCODE_SHIFT)
            handleShift();
        else if (primaryCode == Keyboard.KEYCODE_CANCEL)
            handleClose();
        else if (primaryCode == LatinKeyboardView.KEYCODE_LANGUAGE_SWITCH)
            handleLanguageSwitch();
        else if (primaryCode == LatinKeyboardView.KEYCODE_OPTIONS) {
            // Show a menu or something
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE && inputView != null) {
            Keyboard current = inputView.getKeyboard();
            if (current == symbolsKeyboard || current == symbolsShiftedKeyboard)
                setLatinKeyboard(qwertyKeyboard);
            else {
                setLatinKeyboard(symbolsKeyboard);
                symbolsKeyboard.setShifted(false);
            }
        } else if (primaryCode == LatinKeyboardView.KEYCODE_EMOJI)
            handleEmoji();
        else
            handleCharacter(primaryCode, keyCodes);
    }

    public void onText(CharSequence text) {
        GlobalClass.printLog("SoftKeyboard", "---------------onText---------------");

        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection == null)
            return;
        inputConnection.beginBatchEdit();
        if (composing.length() > 0)
            commitTyped(inputConnection);
        inputConnection.commitText(text, 0);
        inputConnection.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    /**
     * Update the list of available candidates from the current composing
     * text.  This will need to be filled in by however you are determining
     * candidates.
     */
    private void updateCandidates() {
        GlobalClass.printLog("SoftKeyboard", "---------------updateCandidates---------------");

        if (!completionOn) {
            if (composing.length() > 0) {
                ArrayList<String> list = new ArrayList<>();
                //spellCheckerSession.getSentenceSuggestions(new TextInfo[]{new TextInfo(composing.toString())}, 5);  //TODO: Re-enable and make spellCheckerSession not null (currently always null)
                list.add(composing.toString());
                setSuggestions(list, true, true);
            } else
                setSuggestions(null, false, false);
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {

        if (suggestions != null && suggestions.size() > 0)
            setCandidatesViewShown(true);
        else if (isExtractViewShown())
            setCandidatesViewShown(true);
        this.suggestions = suggestions;
        if (candidateView != null)
            candidateView.setSuggestions(suggestions, completions, typedWordValid);
    }

    private void handleBackspace() {
        final int length = composing.length();
        if (length > 1) {
            composing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(composing, 1);
            updateCandidates();
        } else if (length > 0) {
            composing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else
            keyDownUp(KeyEvent.KEYCODE_DEL);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleShift() {

        if (inputView == null)
            return;

        Keyboard currentKeyboard = inputView.getKeyboard();
        if (qwertyKeyboard == currentKeyboard || qwertyShiftedKeyboard == currentKeyboard) {
            if (capsLock) {
                setLatinKeyboard(qwertyKeyboard);
                inputView.setShifted(false);
                capsLock = false;
            } else {
                if (qwertyKeyboard == currentKeyboard) {
                    checkToggleCapsLock();
                    setLatinKeyboard(qwertyShiftedKeyboard);
                    inputView.setShifted(true);
                } else {
                    checkToggleCapsLock();
                    inputView.setShifted(capsLock);
                    if (!inputView.isShifted())
                        setLatinKeyboard(qwertyKeyboard);
                }
            }
        } else if (currentKeyboard == symbolsKeyboard) {
            symbolsKeyboard.setShifted(true);
            setLatinKeyboard(symbolsShiftedKeyboard);
            symbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == symbolsShiftedKeyboard) {
            symbolsShiftedKeyboard.setShifted(false);
            setLatinKeyboard(symbolsKeyboard);
            symbolsKeyboard.setShifted(false);
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (inputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if (!capsLock && inputView.getKeyboard() != symbolsShiftedKeyboard) {
                    setLatinKeyboard(qwertyKeyboard);
                    inputView.setShifted(!inputView.isShifted());
                }
            }
        }
        if (predictionOn) {
            composing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(composing, 1);
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
        inputView.closing();
    }

    private IBinder getToken() {

        final Dialog dialog = getWindow();
        if (dialog == null)
            return null;
        final Window window = dialog.getWindow();
        if (window == null)
            return null;
        return window.getAttributes().token;
    }

    private void handleLanguageSwitch() {
        inputMethodManager.switchToNextInputMethod(getToken(), false /* onlyCurrentIme */);
    }

    private void handleEmoji() {
        if (inputView.getVisibility() == View.VISIBLE) {
            inputView.setVisibility(View.GONE);
            emojiConstraintLayout.setVisibility(View.VISIBLE);
        } else {
            inputView.setVisibility(View.VISIBLE);
            emojiConstraintLayout.setVisibility(View.GONE);
        }
    }

    private void checkToggleCapsLock() {
        GlobalClass.printLog("SoftKeyboard", "---------------checkToggleCapsLock---------------");

        long now = System.currentTimeMillis();
        if (lastShiftTime + 800 > now) {
            capsLock = !capsLock;
            lastShiftTime = 0;

        } else if (!inputView.isShifted())
            lastShiftTime = now;
    }

    private String getWordSeparators() {
        GlobalClass.printLog("SoftKeyboard", "---------------getWordSeparators---------------");

        return wordSeparators;
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

        if (completionOn && completions != null && index >= 0
                && index < completions.length) {
            CompletionInfo ci = completions[index];
            getCurrentInputConnection().commitCompletion(ci);
            if (candidateView != null)
                candidateView.clear();
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (composing.length() > 0) {
            if (predictionOn && suggestions != null && index >= 0)
                composing.replace(0, composing.length(), suggestions.get(index));
            commitTyped(getCurrentInputConnection());

        }
    }

    public void swipeRight() {
        GlobalClass.printLog("SoftKeyboard", "---------------swipeRight---------------");

        Log.d("SoftKeyboard", "Swipe right");
        if (completionOn)
            pickDefaultCandidate();
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

        GlobalClass.printLog("SoftKeyboard", "---------------onPress---------------" + String.valueOf(GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_ON, false)));

        Log.e("KEYBOARD", "hello" + String.valueOf(GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_ON, false)));

        if (GlobalClass.getPreferencesBool(getApplicationContext(), GlobalClass.SOUND_ON, false))
            performKeySound();

        if (vibrationValue != 0)
            performKeyVibration(vibrationValue);

        // Disable preview key on Shift, Delete, Symbol, Language Switch, Space and Enter.
        if (primaryCode == -1 || primaryCode == -5 || primaryCode == -2 || primaryCode == -101 || primaryCode == 32 || primaryCode == 10)
            inputView.setPreviewEnabled(false);
        else {
            //inputView.setPreviewEnabled(true); TODO: Fix Preview
        }

    }

    public void onRelease(int primaryCode) {
        GlobalClass.printLog("SoftKeyboard", "---------------onRelease---------------");
        inputView.setPreviewEnabled(false);
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

            for (int j = 0; j < len; ++j)
                sb.append(",").append(result.getSuggestionAt(j));

            sb.append(" (").append(len).append(")");
        }
        Log.d("SoftKeyboard", "SUGGESTIONS: " + sb.toString());
    }

    private void dumpSuggestionsInfoInternal(final List<String> sb, final SuggestionsInfo suggestionsInfo, final int length, final int offset) {
        // Returned suggestions are contained in SuggestionsInfo
        GlobalClass.printLog("SoftKeyboard", "---------------dumpSuggestionsInfoInternal---------------");

        final int len = suggestionsInfo.getSuggestionsCount();
        for (int j = 0; j < len; ++j)
            sb.add(suggestionsInfo.getSuggestionAt(j));
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        GlobalClass.printLog("SoftKeyboard", "---------------onGetSentenceSuggestions---------------");

        Log.d("SoftKeyboard", "onGetSentenceSuggestions");
        final List<String> sb = new ArrayList<>();
        for (final SentenceSuggestionsInfo sentenceSuggestionsInfo : results)
            for (int i = 0; i < sentenceSuggestionsInfo.getSuggestionsCount(); ++i)
                dumpSuggestionsInfoInternal(sb, sentenceSuggestionsInfo.getSuggestionsInfoAt(i), sentenceSuggestionsInfo.getOffsetAt(i), sentenceSuggestionsInfo.getLengthAt(i));
        Log.d("SoftKeyboard", "SUGGESTIONS: " + sb.toString());
        setSuggestions(sb, true, true);
    }

    private void performKeySound() {
        GlobalClass.printLog("SoftKeyboard", "---------------performKeySound---------------");

        if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {

            final int soundID = soundPool.load(context, GlobalClass.soundsArray[GlobalClass.soundPosition], 1);
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