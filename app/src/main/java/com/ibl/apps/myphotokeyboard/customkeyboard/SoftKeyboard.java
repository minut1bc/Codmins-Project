package com.ibl.apps.myphotokeyboard.customkeyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.text.method.MetaKeyKeyListener;
import android.util.Base64;
import android.util.Log;
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
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ibl.apps.myphotokeyboard.Interface.OnItemClickListener;
import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.adapter.FillArtAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillEmojiAdapter;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of writing an input method for a soft keyboard.  This code is
 * focused on simplicity over completeness, so it should in no way be considered
 * to be a complete soft keyboard implementation.  Its purpose is to provide
 * a basic example for how you would get started writing an input method, to
 * be fleshed out as appropriate.
 */

public class SoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener,
        SpellCheckerSession.SpellCheckerSessionListener {

    static final boolean PROCESS_HARD_KEYS = true;
    private InputMethodManager mInputMethodManager;
    private CandidateView mCandidateView;
    private CompletionInfo[] mCompletions;
    private StringBuilder mComposing = new StringBuilder();
    private boolean mPredictionOn;
    private boolean mCompletionOn;
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

    private ImageView ivEmoji;
    private ImageView ivArt;
    private RelativeLayout linEmoji;
    private ImageView ivClose;
    private GridView gvEmoji;
    private RecyclerView rv_art_list;
    private FillEmojiAdapter fillEmojiAdapter;
    private ImageView ivAbc;
    private ImageView ivSmile;
    private ImageView ivAnimal;
    private ImageView ivLamp;
    private ImageView ivFood;
    private ImageView ivSocial;
    private ImageView ivGoogleSearch;

    Context mContext;
    private String[] emojiArrayList;

    String[] smilyArrayList =
            {
                    "\uD83D\uDE03",
                    "\uD83D\uDE00",
                    "\uD83D\uDE0A",
                    "☺",
                    "\uD83D\uDE09",
                    "\uD83D\uDE0D",
                    "\uD83D\uDE18",
                    "\uD83D\uDE1A",
                    "\uD83D\uDE17",
                    "\uD83D\uDE19",
                    "\uD83D\uDE1C",
                    "\uD83D\uDE1D",
                    "\uD83D\uDE1B",
                    "\uD83D\uDE33",
                    "\uD83D\uDE01",
                    "\uD83D\uDE14",
                    "\uD83D\uDE0C",
                    "\uD83D\uDE0C",
                    "\uD83D\uDE1E",
                    "\uD83D\uDE23",
                    "\uD83D\uDE22",
                    "\uD83D\uDE02",
                    "\uD83D\uDE2D",
                    "\uD83D\uDE2A",
                    "\uD83D\uDE25",
                    "\uD83D\uDE30",
                    "\uD83D\uDE05",
                    "\uD83D\uDE13",
                    "\uD83D\uDE29",
                    "\uD83D\uDE2B",
                    "\uD83D\uDE28",
                    "\uD83D\uDE31",
                    "\uD83D\uDE20",
                    "\uD83D\uDE21",
                    "\uD83D\uDE24",
                    "\uD83D\uDE16",
                    "\uD83D\uDE06",
                    "\uD83D\uDE0B",
                    "\uD83D\uDE37",
                    "\uD83D\uDE0E",
                    "\uD83D\uDE34",
                    "\uD83D\uDE35",
                    "\uD83D\uDE32",
                    "\uD83D\uDE1F",
                    "\uD83D\uDE26",
                    "\uD83D\uDE27",
                    "\uD83D\uDE08",
                    "\uD83D\uDC7F",
                    "\uD83D\uDE2E",
                    "\uD83D\uDE2C",
                    "\uD83D\uDE10",
                    "\uD83D\uDE15",
                    "\uD83D\uDE2F",
                    "\uD83D\uDE36",
                    "\uD83D\uDE07",
                    "\uD83D\uDE0F",
                    "\uD83D\uDE11",
                    "\uD83D\uDC72",
                    "\uD83D\uDC73",
                    "\uD83D\uDC6E",
                    "\uD83D\uDC77",
                    "\uD83D\uDC82",
                    "\uD83D\uDC76",
                    "\uD83D\uDC66",
                    "\uD83D\uDC67",
                    "\uD83D\uDC68",
                    "\uD83D\uDC69",
                    "\uD83D\uDC74",
                    "\uD83D\uDC75",
                    "\uD83D\uDC71",
                    "\uD83D\uDC7C",
                    "\uD83D\uDC78",
                    "\uD83D\uDE3A",
                    "\uD83D\uDE38",
                    "\uD83D\uDE3B",
                    "\uD83D\uDE3D",
                    "\uD83D\uDE3C",
                    "\uD83D\uDE40",
                    "\uD83D\uDE3F",
                    "\uD83D\uDE39",
                    "\uD83D\uDE3E",
                    "\uD83D\uDC79",
                    "\uD83D\uDC7A",
                    "\uD83D\uDE48",
                    "\uD83D\uDE49",
                    "\uD83D\uDE4A",
                    "\uD83D\uDC80",
                    "\uD83D\uDC7D",
                    "\uD83D\uDCA9",
                    "\uD83D\uDD25",
                    "✨",
                    "\uD83C\uDF1F",
                    "\uD83D\uDCAB",
                    "\uD83D\uDCA5",
                    "\uD83D\uDCA2",
                    "\uD83D\uDCA6",
                    "\uD83D\uDCA7",
                    "\uD83D\uDCA4",
                    "\uD83D\uDCA8",
                    "\uD83D\uDC42",
                    "\uD83D\uDC40",
                    "\uD83D\uDC43",
                    "\uD83D\uDC45",
                    "\uD83D\uDC44",
                    "\uD83D\uDC4D",
                    "\uD83D\uDC4E",
                    "\uD83D\uDC4C",
                    "\uD83D\uDC4A",
                    "✊",
                    "✌",
                    "\uD83D\uDC4B",
                    "✋",
                    "\uD83D\uDC50",
                    "\uD83D\uDC46",
                    "\uD83D\uDC47",
                    "\uD83D\uDC49",
                    "\uD83D\uDC48",
                    "\uD83D\uDE4C",
                    "\uD83D\uDE4F",
                    "☝",
                    "\uD83D\uDC4F",
                    "\uD83D\uDCAA",
                    "\uD83D\uDEB6",
                    "\uD83C\uDFC3",
                    "\uD83D\uDC83",
                    "\uD83D\uDC6B",
                    "\uD83D\uDC6A",
                    "\uD83D\uDC6C",
                    "\uD83D\uDC6D",
                    "\uD83D\uDC8F",
                    "\uD83D\uDC91",
                    "\uD83D\uDC6F",
                    "\uD83D\uDE46",
                    "\uD83D\uDE45",
                    "\uD83D\uDC81",
                    "\uD83D\uDE4B",
                    "\uD83D\uDC86",
                    "\uD83D\uDC87",
                    "\uD83D\uDC85",
                    "\uD83D\uDC70",
                    "\uD83D\uDE4E",
                    "\uD83D\uDE4D",
                    "\uD83D\uDE47",
                    "\uD83C\uDFA9",
                    "\uD83D\uDC51",
                    "\uD83D\uDC52",
                    "\uD83D\uDC5F",
                    "\uD83D\uDC5E",
                    "\uD83D\uDC61",
                    "\uD83D\uDC60",
                    "\uD83D\uDC62",
                    "\uD83D\uDC55",
                    "\uD83D\uDC54",
                    "\uD83D\uDC5A",
                    "\uD83D\uDC57",
                    "\uD83C\uDFBD",
                    "\uD83D\uDC56",
                    "\uD83D\uDC58",
                    "\uD83D\uDC59",
                    "\uD83D\uDCBC",
                    "\uD83D\uDC5C",
                    "\uD83D\uDC5D",
                    "\uD83D\uDC5B",
                    "\uD83D\uDC53",
                    "\uD83C\uDF80",
                    "\uD83C\uDF02",
                    "\uD83D\uDC84",
                    "\uD83D\uDC9B",
                    "\uD83D\uDC99",
                    "\uD83D\uDC9C",
                    "\uD83D\uDC9A",
                    "❤",
                    "\uD83D\uDC94",
                    "\uD83D\uDC97",
                    "\uD83D\uDC93",
                    "\uD83D\uDC95",
                    "\uD83D\uDC96",
                    "\uD83D\uDC9E",
                    "\uD83D\uDC98",
                    "\uD83D\uDC8C",
                    "\uD83D\uDC8B",
                    "\uD83D\uDC8D",
                    "\uD83D\uDC8E",
                    "\uD83D\uDC64",
                    "\uD83D\uDC65",
                    "\uD83D\uDCAC",
                    "\uD83D\uDC63",
                    "\uD83D\uDCAD",
            };

    String[] animalArrayList =
            {
                    "\uD83D\uDC3E",
                    "\uD83D\uDC90",
                    "\uD83C\uDF38",
                    "\uD83C\uDF37",
                    "\uD83C\uDF40",
                    "\uD83C\uDF39",
                    "\uD83C\uDF3B",
                    "\uD83C\uDF3A",
                    "\uD83C\uDF41",
                    "\uD83C\uDF43",
                    "\uD83C\uDF42",
                    "\uD83C\uDF3F",
                    "\uD83C\uDF3E",
                    "\uD83C\uDF44",
                    "\uD83C\uDF35",
                    "\uD83C\uDF34",
                    "\uD83C\uDF32",
                    "\uD83C\uDF33",
                    "\uD83C\uDF30",
                    "\uD83C\uDF31",
                    "\uD83C\uDF3C",
                    "\uD83C\uDF10",
                    "\uD83C\uDF1E",
                    "\uD83C\uDF1D",
                    "\uD83C\uDF1A",
                    "\uD83C\uDF11",
                    "\uD83C\uDF12",
                    "\uD83C\uDF13",
                    "\uD83C\uDF14",
                    "\uD83C\uDF15",
                    "\uD83C\uDF16",
                    "\uD83C\uDF17",
                    "\uD83C\uDF18",
                    "\uD83C\uDF1C",
                    "\uD83C\uDF1B",
                    "\uD83C\uDF19",
                    "\uD83C\uDF0D",
                    "\uD83C\uDF0E",
                    "\uD83C\uDF0F",
                    "\uD83C\uDF0B",
                    "\uD83C\uDF0C",
                    "\uD83C\uDF20",
                    "⭐",
                    "☀",
                    "⛅",
                    "☁",
                    "⚡",
                    "☔",
                    "❄",
                    "⛄",
                    "\uD83C\uDF00",
                    "\uD83C\uDF01",
                    "\uD83C\uDF08",
                    "\uD83C\uDF0A",
                    "\uD83C\uDF8D",
                    "\uD83D\uDC9D",
                    "\uD83C\uDF8E",
                    "\uD83C\uDF92",
                    "\uD83C\uDF93",
                    "\uD83C\uDF8F",
                    "\uD83C\uDF86",
                    "\uD83C\uDF87",
                    "\uD83C\uDF90",
                    "\uD83C\uDF91",
                    "\uD83C\uDF83",
                    "\uD83D\uDC7B",
                    "\uD83C\uDF85",
                    "\uD83C\uDF84",
                    "\uD83C\uDF81",
                    "\uD83C\uDF8B",
                    "\uD83C\uDF89",
                    "\uD83C\uDF8A",
                    "\uD83C\uDF88",
                    "\uD83C\uDF8C",
            };

    String[] lampArrayList =
            {
                    "\uD83D\uDD2E",
                    "\uD83C\uDFA5",
                    "\uD83D\uDCF7",
                    "\uD83D\uDCF9",
                    "\uD83D\uDCFC",
                    "\uD83D\uDCBF",
                    "\uD83D\uDCC0",
                    "\uD83D\uDCBD",
                    "\uD83D\uDCBE",
                    "\uD83D\uDCBB",
                    "\uD83D\uDCF1",
                    "☎",
                    "\uD83D\uDCDE",
                    "\uD83D\uDCDF",
                    "\uD83D\uDCE0",
                    "\uD83D\uDCE1",
                    "\uD83D\uDCFA",
                    "\uD83D\uDCFB",
                    "\uD83D\uDD0A",
                    "\uD83D\uDD09",
                    "\uD83D\uDD08",
                    "\uD83D\uDD07",
                    "\uD83D\uDD14",
                    "\uD83D\uDD15",
                    "\uD83D\uDCE2",
                    "\uD83D\uDCE3",
                    "⏳",
                    "⌛",
                    "⏰",
                    "⌚",
                    "\uD83D\uDD13",
                    "\uD83D\uDD12",
                    "\uD83D\uDD0F",
                    "\uD83D\uDD10",
                    "\uD83D\uDD11",
                    "\uD83D\uDD0E",
                    "\uD83D\uDCA1",
                    "\uD83D\uDD26",
                    "\uD83D\uDD06",
                    "\uD83D\uDD05",
                    "\uD83D\uDD0C",
                    "\uD83D\uDD0B",
                    "\uD83D\uDD0D",
                    "\uD83D\uDEC1",
                    "\uD83D\uDEC0",
                    "\uD83D\uDEBF",
                    "\uD83D\uDEBD",
                    "\uD83D\uDD27",
                    "\uD83D\uDD29",
                    "\uD83D\uDD28",
                    "\uD83D\uDEAA",
                    "\uD83D\uDEAC",
                    "\uD83D\uDCA3",
                    "\uD83D\uDD2B",
                    "\uD83D\uDD2A",
                    "\uD83D\uDC8A",
                    "\uD83D\uDC89",
                    "\uD83D\uDCB0",
                    "\uD83D\uDCB4",
                    "\uD83D\uDCB5",
                    "\uD83D\uDCB7",
                    "\uD83D\uDCB6",
                    "\uD83D\uDCB3",
                    "\uD83D\uDCB8",
                    "\uD83D\uDCF2",
                    "\uD83D\uDCE7",
                    "\uD83D\uDCE5",
                    "\uD83D\uDCE4",
                    "\uD83D\uDCE9✉",
                    "\uD83D\uDCE8",
                    "\uD83D\uDCEF",
                    "\uD83D\uDCEB",
                    "\uD83D\uDCEA",
                    "\uD83D\uDCEC",
                    "\uD83D\uDCED",
                    "\uD83D\uDCEE",
                    "\uD83D\uDCE6",
                    "\uD83D\uDCDD",
                    "\uD83D\uDCC4",
                    "\uD83D\uDCC3",
                    "\uD83D\uDCC3",
                    "\uD83D\uDCCA",
                    "\uD83D\uDCC8",
                    "\uD83D\uDCC9",
                    "\uD83D\uDCDC",
                    "\uD83D\uDCCB",
                    "\uD83D\uDCC5",
                    "\uD83D\uDCC6",
                    "\uD83D\uDCC7",
                    "\uD83D\uDCC1",
                    "\uD83D\uDCC2",
                    "✂",
                    "\uD83D\uDCCC",
                    "\uD83D\uDCCE",
                    "✒",
                    "✏",
                    "\uD83D\uDCCF",
                    "",
                    "\uD83D\uDCD0",
                    "\uD83D\uDCD5",
                    "\uD83D\uDCD7",
                    "\uD83D\uDCD8",
                    "\uD83D\uDCD9",
                    "\uD83D\uDCD3",
                    "\uD83D\uDCD4",
                    "\uD83D\uDCD2",
                    "\uD83D\uDCDA",
                    "\uD83D\uDCD6",
                    "\uD83D\uDD16",
                    "\uD83D\uDCDB",
                    "\uD83D\uDD2C",
                    "\uD83D\uDD2D",
                    "\uD83D\uDCF0",
                    "\uD83C\uDFA8",
                    "\uD83C\uDFAC",
                    "\uD83C\uDFA4",
                    "\uD83C\uDFA7",
                    "\uD83C\uDFBC",
                    "\uD83C\uDFB5",
                    "\uD83C\uDFB6",
                    "\uD83C\uDFB9",
                    "\uD83C\uDFBB",
                    "\uD83C\uDFBA",
                    "\uD83C\uDFB7",
                    "\uD83C\uDFB8",
                    "\uD83D\uDC7E",
                    "\uD83C\uDFAE",
                    "\uD83C\uDCCF",
                    "\uD83C\uDFB4",
                    "\uD83C\uDC04",
                    "\uD83C\uDFB2",
                    "\uD83C\uDFAF",
                    "\uD83C\uDFC8",
                    "\uD83C\uDFC0",
                    "⚽",
                    "⚾",
                    "\uD83C\uDFBE",
                    "\uD83C\uDFB1",
                    "\uD83C\uDFC9",
                    "\uD83C\uDFB3",
                    "⛳",
                    "\uD83D\uDEB5",
                    "\uD83D\uDEB4",
                    "\uD83C\uDFC1",
                    "\uD83C\uDFC7",
                    "\uD83C\uDFC6",
                    "\uD83C\uDFBF",
                    "\uD83C\uDFC2",
                    "\uD83C\uDFCA",
                    "\uD83C\uDFC4",
                    "\uD83C\uDFA3",
                    "☕",
            };
    String[] foodArrayList =
            {
                    "\uD83C\uDF75",
                    "\uD83C\uDF76",
                    "\uD83C\uDF7C",
                    "\uD83C\uDF7A",
                    "\uD83C\uDF7B",
                    "\uD83C\uDF78",
                    "\uD83C\uDF79",
                    "\uD83C\uDF77",
                    "\uD83C\uDF74",
                    "\uD83C\uDF55",
                    "\uD83C\uDF54",
                    "\uD83C\uDF5F",
                    "\uD83C\uDF57",
                    "\uD83C\uDF56",
                    "\uD83C\uDF5D",
                    "\uD83C\uDF5B",
                    "\uD83C\uDF64",
                    "\uD83C\uDF71",
                    "\uD83C\uDF63",
                    "\uD83C\uDF65",
                    "\uD83C\uDF59",
                    "\uD83C\uDF58",
                    "\uD83C\uDF5A",
                    "\uD83C\uDF5C",
                    "\uD83C\uDF72",
                    "\uD83C\uDF62",
                    "\uD83C\uDF61",
                    "\uD83C\uDF73",
                    "\uD83C\uDF5E",
                    "\uD83C\uDF69",
                    "\uD83C\uDF6E",
                    "\uD83C\uDF66",
                    "\uD83C\uDF68",
                    "\uD83C\uDF67",
                    "\uD83C\uDF82",
                    "\uD83C\uDF70",
                    "\uD83C\uDF6A",
                    "\uD83C\uDF6B",
                    "\uD83C\uDF6C",
                    "\uD83C\uDF6D",
                    "\uD83C\uDF6F",
                    "\uD83C\uDF4E",
                    "\uD83C\uDF4F",
                    "\uD83C\uDF4A",
                    "\uD83C\uDF4B",
                    "\uD83C\uDF52",
                    "\uD83C\uDF47",
                    "\uD83C\uDF49",
                    "\uD83C\uDF53",
                    "\uD83C\uDF51",
                    "\uD83C\uDF48",
                    "\uD83C\uDF4C",
                    "\uD83C\uDF50",
                    "\uD83C\uDF4D",
                    "\uD83C\uDF60",
                    "\uD83C\uDF46",
                    "\uD83C\uDF45",
                    "\uD83C\uDF3D",
            };
    String[] socialArrayList =
            {
                    "\uD83C\uDFE0",
                    "\uD83C\uDFE1",
                    "\uD83C\uDFEB",
                    "\uD83C\uDFE2",
                    "\uD83C\uDFE3",
                    "\uD83C\uDFE5",
                    "\uD83C\uDFE6",
                    "\uD83C\uDFEA",
                    "\uD83C\uDFE9",
                    "\uD83C\uDFE8",
                    "\uD83D\uDC92",
                    "⛪",
                    "\uD83C\uDFEC",
                    "\uD83C\uDFE4",
                    "\uD83C\uDF07",
                    "\uD83C\uDF06",
                    "\uD83C\uDFEF",
                    "\uD83C\uDFF0",
                    "⛺",
                    "\uD83C\uDFED",
                    "\uD83D\uDDFC",
                    "\uD83D\uDDFE",
                    "\uD83D\uDDFB",
                    "\uD83C\uDF04",
                    "\uD83C\uDF05",
                    "\uD83C\uDF03",
                    "\uD83D\uDDFD",
                    "\uD83C\uDF09",
                    "\uD83C\uDFA0",
                    "\uD83C\uDFA1",
                    "⛲",
                    "\uD83C\uDFA2",
                    "\uD83D\uDEA2",
                    "⛵",
                    "\uD83D\uDEA4",
                    "\uD83D\uDEA3",
                    "⚓",
                    "\uD83D\uDE80",
                    "✈",
                    "\uD83D\uDCBA",
                    "\uD83D\uDE81",
                    "\uD83D\uDE82",
                    "\uD83D\uDE8A",
                    "\uD83D\uDE89",
                    "\uD83D\uDE9E",
                    "\uD83D\uDE86",
                    "\uD83D\uDE84",
                    "\uD83D\uDE85",
                    "\uD83D\uDE88",
                    "\uD83D\uDE87",
                    "\uD83D\uDE9D",
                    "\uD83D\uDE8B",
                    "\uD83D\uDE83",
                    "\uD83D\uDE8E",
                    "\uD83D\uDE8C",
                    "\uD83D\uDE8D",
                    "\uD83D\uDE99",
                    "\uD83D\uDE98",
                    "\uD83D\uDE97",
                    "\uD83D\uDE95",
                    "\uD83D\uDE96",
                    "\uD83D\uDE9B",
                    "\uD83D\uDE9A",
                    "\uD83D\uDEA8",
                    "\uD83D\uDE93",
                    "\uD83D\uDE94",
                    "\uD83D\uDE92",
                    "\uD83D\uDE91",
                    "\uD83D\uDE90",
                    "\uD83D\uDEB2",
                    "\uD83D\uDEA1",
                    "\uD83D\uDE9F",
                    "\uD83D\uDEA0",
                    "\uD83D\uDE9C",
                    "\uD83D\uDC88",
                    "\uD83D\uDE8F",
                    "\uD83C\uDFAB",
                    "\uD83D\uDEA6",
                    "\uD83D\uDEA5",
                    "⚠",
                    "\uD83D\uDEA7",
                    "\uD83D\uDD30",
                    "⛽",
                    "\uD83C\uDFEE",
                    "\uD83C\uDFB0",
                    "♨",
                    "\uD83D\uDDFF",
                    "\uD83C\uDFAA",
                    "\uD83C\uDFAD",
                    "\uD83D\uDCCD",
                    "\uD83D\uDEA9",
                    "\uD83C\uDDEF\uD83C\uDDF5",
                    "\uD83C\uDDF0\uD83C\uDDF7",
                    "\uD83C\uDDE9\uD83C\uDDEA",
                    "\uD83C\uDDE8\uD83C\uDDF3",
                    "\uD83C\uDDFA\uD83C\uDDF8",
                    "\uD83C\uDDEB\uD83C\uDDF7",
                    "\uD83C\uDDEA\uD83C\uDDF8",
                    "\uD83C\uDDEE\uD83C\uDDF9",
                    "\uD83C\uDDF7\uD83C\uDDFA",
                    "1⃣",
                    "2⃣",
                    "3⃣",
                    "4⃣",
                    "5⃣",
                    "6⃣",
                    "7⃣",
                    "8⃣",
                    "9⃣",
                    "0⃣",
                    "\uD83D\uDD1F",
                    "\uD83D\uDD22",
                    "#⃣",
                    "\uD83D\uDD23",
                    "⬆",
                    "⬇",
                    "⬅",
                    "➡",
                    "\uD83D\uDD20",
                    "\uD83D\uDD21",
                    "\uD83D\uDD24",
                    "↗",
                    "↖",
                    "↘",
                    "↙",
                    "↔",
                    "↕",
                    "?",
                    "◀",
                    "\uD83D\uDD3C",
                    "\uD83D\uDD3D",
                    "↩",
                    "↪",
                    "ℹ",
                    "⏪",
                    "⏩",
                    "⏫",
                    "⏬",
                    "⤵",
                    "⤴",
                    "\uD83C\uDD97",
                    "\uD83D\uDD00",
                    "\uD83D\uDD01",
                    "\uD83D\uDD02",
                    "\uD83C\uDD95",
                    "\uD83C\uDD99",
                    "\uD83C\uDD92",
                    "\uD83C\uDD93",
                    "\uD83C\uDD96",
                    "\uD83D\uDCF6",
                    "\uD83C\uDFA6",
                    "\uD83C\uDE01",
                    "🈯",
                    "🈳",
                    "🈵",
                    "🈴",
                    "🈲",
                    "🉐",
                    "🈹",
                    "🈺",
                    "🈶",
                    "🈚",
                    "\uD83D\uDEBB",
                    "\uD83D\uDEB9",
                    "\uD83D\uDEBA",
                    "\uD83D\uDEBC",
                    "\uD83D\uDEBE",
                    "\uD83D\uDEB0",
                    "\uD83D\uDEAE",
                    "\uD83C\uDD7F",
                    "♿",
                    "\uD83D\uDEAD",
                    "\uD83C\uDE37",
                    "\uD83C\uDE38",
                    "\uD83C\uDE02",
                    "Ⓜ",
                    "\uD83D\uDEC2",
                    "\uD83D\uDEC4",
                    "\uD83D\uDEC5",
                    "\uD83D\uDEC3",
                    "\uD83C\uDE51",
                    "㊙",
                    "㊗",
                    "\uD83C\uDD91",
                    "\uD83C\uDD98",
                    "\uD83C\uDD94",
                    "\uD83D\uDEAB",
                    "\uD83D\uDD1E",
                    "\uD83D\uDCF5",
                    "\uD83D\uDEAF",
                    "\uD83D\uDEB1",
                    "\uD83D\uDEB3",
                    "\uD83D\uDEB7",
                    "\uD83D\uDEB8",
                    "⛔",
                    "✳",
                    "❇",
                    "❎",
                    "✅",
                    "✴",
                    "\uD83D\uDC9F",
                    "\uD83C\uDD9A",
                    "\uD83D\uDCF3",
                    "\uD83D\uDCF4",
                    "\uD83C\uDD70",
                    "\uD83C\uDD71",
                    "\uD83C\uDD8E",
                    "\uD83C\uDD7E",
                    "\uD83D\uDCA0",
                    "➿",
                    "♻",
                    "♈",
                    "♉",
                    "♊",
                    "♋",
                    "♌",
                    "♍",
                    "♎",
                    "♏",
                    "♐",
                    "♑",
                    "♒",
                    "♓",
                    "⛎",
                    "\uD83D\uDD2F",
                    "\uD83C\uDFE7",
                    "\uD83D\uDCB9",
                    "\uD83D\uDCB2",
                    "\uD83D\uDCB1",
                    "©",
                    "®",
                    "™",
                    "❌",
                    "‼",
                    "⁉",
                    "❗",
                    "❓",
                    "❕",
                    "❔",
                    "⭕",
                    "\uD83D\uDD1D",
                    "\uD83D\uDD1A",
                    "\uD83D\uDD19",
                    "\uD83D\uDD1B",
                    "\uD83D\uDD1C",
                    "\uD83D\uDD03",
                    "\uD83D\uDD5B",
                    "\uD83D\uDD67",
                    "\uD83D\uDD50",
                    "\uD83D\uDD5C",
                    "\uD83D\uDD51",
                    "\uD83D\uDD5D",
                    "\uD83D\uDD52",
                    "\uD83D\uDD5E",
                    "\uD83D\uDD53",
                    "\uD83D\uDD5F",
                    "\uD83D\uDD54",
                    "\uD83D\uDD60",
                    "\uD83D\uDD55",
                    "\uD83D\uDD56",
                    "\uD83D\uDD57",
                    "\uD83D\uDD58",
                    "\uD83D\uDD59",
                    "\uD83D\uDD5A",
                    "\uD83D\uDD61",
                    "\uD83D\uDD62",
                    "\uD83D\uDD63",
                    "\uD83D\uDD64",
                    "\uD83D\uDD65",
                    "\uD83D\uDD66",
                    "✖",
                    "➕",
                    "➖",
                    "➗",
                    "♠",
                    "♥",
                    "♣",
                    "♦",
                    "\uD83D\uDCAE",
                    "\uD83D\uDCAF",
                    "✔",
                    "☑",
                    "\uD83D\uDD18",
                    "\uD83D\uDD17",
                    "➰",
                    "〰",
                    "〽",
                    "\uD83D\uDD31",
                    "◼",
                    "◻",
                    "◾",
                    "◽",
                    "▪",
                    "▫",
                    "\uD83D\uDD3A",
                    "\uD83D\uDD32",
                    "\uD83D\uDD33",
                    "⚫",
                    "⚪",
                    "\uD83D\uDD34",
                    "\uD83D\uDD35",
                    "\uD83D\uDD3B",
                    "⬜",
                    "⬛",
                    "\uD83D\uDD36",
                    "\uD83D\uDD37",
                    "\uD83D\uDD38",
                    "\uD83D\uDD39",
            };


    String[] artArrayList =
            {
                    "────────▀██▀─▀██▀─▄▄▄─▄▄▄──▄▄▄──▄──▄─\n" +
                            "─────────██▄▄▄██─█──█─█──█─█──█─█──█─\n" +
                            "▀██▀▀▄───██───██─▀▄▀█─█▄▄▀─█▄▄▀─▀▄▀█─\n" +
                            "─██▄▀▄──▄██▄─▄██▄─────█────█───▀▄▄▄▀─\n" +
                            "─██───█─▄──────▄──█───────█──────▄──▄\n" +
                            "─██───█─▄─▄─▄─▄█▄─█─▄───▄▄█─▄▀▀█─█──█\n" +
                            "▄██▄▄▀──█─█▀───█──█▀─█─█──█─█─▄█─▀▄▀█\n" +
                            "────────█─█────█──█──█─▀▄▀█──▀─▀─▄▄▄▀ ",
                    "˜˜”*°•.¸☆ ★ ☆¸.•°*”˜˜”*°•.¸☆\n" +
                            "╔╗╔╦══╦═╦═╦╗╔╗ ★ ★ ★\n" +
                            "║╚╝║══║═║═║╚╝║ ☆¸.•°*”˜˜”*°•.¸☆\n" +
                            "║╔╗║╔╗║╔╣╔╩╗╔╝ ★  Birthday to you ☆\n" +
                            "╚╝╚╩╝╚╩╝╚╝═╚╝ ♥￥☆★☆★☆￥♥ ★☆ ",
                    "╮╭╭╮┏╮┏╮╮╭┊┊┊┊┊┊┊\n" +
                            "┣┫┣┫┣╯┣╯╰┫┊┊☆☆┊┊┊\n" +
                            "╯╯╯╯╯┊╯┊╰╯╭━┻┻━╮┊\n" +
                            "┏╮┊┏╮╭╮╮╭╭┻━━━━┻╮\n" +
                            "┣┫★┃┃┣┫╰┫┣╮╭╮╭╮╭┫\n" +
                            "┗╯┊┗╯╯╯╰╯┃╰╯╰╯╰╯┃\n" +
                            "━━━━━━━━━╯╳╳╳╳╳╳╰",
                    "___________'O'\n" +
                            "___________/o\\\n" +
                            "__________/.-o-\\\n" +
                            "_________/''--o--\\\n" +
                            "________█▓▒▒▓█\n" +
                            "________█▓▒▒▓█\n" +
                            "____...//{´°´(_)´°´}\\\\.\n" +
                            "___oOOo`—''*—´---oOOo\n" +
                            "'█▄█ █▀█ █▀█ █▀█ █▄█\n" +
                            "'█▀█ █▀█ █▀▀ █▀▀ ░█'░\n" +
                            "̲̅̅B̲̅][̲̅̅I̲̅][̲̅̅R̲̅][̲̅̅T̲̅ ][̲̅̅H̲̅][̲̅̅D̲̅][̲̅̅A̲̅][̅̅Y̅\n" +
                            "ღ♥ღ♡ღ♥ღ♡ღ♥ღ♡ღ♥ღ♡♥ ♥\n" +
                            "┊　　 ┊　　 ┊　　 ♥\n" +
                            "┊　　 ┊　　 ♥\n" +
                            "┊　　 ♥\n" +
                            "♥\n" +
                            "♥\n" +
                            "♥\n" +
                            "\n",
                    "✫\n" +
                            "‵⁀) ✫ ✫ ✫.\n" +
                            "`⋎´✫¸.•°*”˜˜”*°•✫\n" +
                            "..✫¸.•°*”˜˜”*°•.✫\n" +
                            "☻/ღ˚ •。* ♥♥ ˚ ˚✰˚ ˛★* 。 ღ˛° 。* °♥ ˚ • ★ *˚ ♥.ღ 。\n" +
                            "/▌*˛˚ ⓗⓐⓟⓟⓨ ⓑⓘⓡⓣⓗⓓⓐⓨ ˚ ✰* ★\n" +
                            "/ \\ ˚. ♥T♥a♥n♥y♥a ♥x♥x♥x♥x♥x\n" +
                            "★ *˛ ˚♥♥* ♥✰。˚ ˚ღ。* ♥˛˚ ♥♥ 。✰˚* ˚♥ ★ღ ˚ ♥✰ •* ˚ ♥♥\" ✰", "┈┈┈┈┈┈ⒽⒺⓁⓁⓄ┈┈┈┈┈ \n" +
                    "╭━━╮┈┈┈╭━━╮┈┈┈┈┈ \n" +
                    "┃╭╮┣━━━┫╭╮┃┈╭┳┳╮ \n" +
                    "╰━┳╯▆┈▆╰┳━╯┈┃┃┃┃ \n" +
                    "┈┈┃┓┈◯┈┏┃┈┈╭┫┗┗┃ \n" +
                    "┈┈┃╰┳┳┳╯┃┈┈┃┃╭━┃ \n" +
                    "╭━┻╮┗┻┛╭┻━╮╰┳━┳╯ \n" +
                    "┃┈┈╰━━━╯┈┈╰━┛┈┃┈ ",
                    "   (\"-^-/\")\n" +
                            "      `o__o' ]\n" +
                            "      (_Y_) _/\n" +
                            "    _..`--'-.`,\n" +
                            "   (__)_,--(__)\n" +
                            "       7:   ; 1\n" +
                            "     _/,`-.-' :\n" +
                            "    (_,)-~~(_,)",
                    "║║╔═╦╦╦═╗\n" +
                            "║╚╣║║║║╩╣\n" +
                            "╚═╩═╩═╩═╩\n" +
                            "(¯`♥´¯)\n" +
                            "`*.¸.*´\n" +
                            "¸.•´¸.•*¨) ¸.•*¨)\n" +
                            "(¸.•´ (¸.•´ .•´ ¸¸.•¨¯`• ♥",
                    "oooO \n" +
                            "(....).... Oooo.... \n" +
                            ".\\..(.....(.....)... \n" +
                            ". .\\_)..... )../.... \n" +
                            ". ......... (_/..... ",
                    "☆彡 \n" +
                            "\n" +
                            "ℒℴѵℯ \n" +
                            "O/ \n" +
                            "/▌♥ \n" +
                            "/.\\ \n" +
                            "████ \n" +
                            "╬╬ \n" +
                            "╬╬ \n" +
                            "╬╬\\O \n" +
                            "╬╬/▌ \n" +
                            "╬╬// \n" +
                            "╬╬ \n" +
                            "╬╬ \n" +
                            "╬╬ \n" +
                            "╬╬\\O \n" +
                            "╬╬/▌ \n" +
                            "╬╬/.\\ \n" +
                            "█████ ● ● ● . . . ",
                    "   ,--.\n" +
                            "     /   \\\n" +
                            "    /      \\\n" +
                            " _/_____\\_\n" +
                            "(_________)\n" +
                            "(/  @  @  \\)\n" +
                            "(  `._,()._,' )\n" +
                            " (    `-'`-'   )\n" +
                            "   \\        /\n" +
                            "     \\,,,,,/\n",
                    "         *\n" +
                            "         /.\\\n" +
                            "        /..'\\\n" +
                            "       / '.' \\\n" +
                            "     /  .''.'  \\\n" +
                            "    /   .'.'.   \\\n" +
                            "   /   '.''.'.   \\\n" +
                            "    ^^^[_]^^^\n" +
                            "----abcdef-----\n" +
                            "Hitting you with a: \n" +
                            ".。☆。*。☆。 \n" +
                            "★。＼｜／。★ \n" +
                            "LOVE BOMB! \n" +
                            "★。／｜＼。★ \n" +
                            "。☆。*。☆。 ",
                    "      ...-\"-...\n" +
                            "     /          \\\n" +
                            "     \\ @ @/\n" +
                            "      (_ =_)\n" +
                            "      _)(`\n" +
                            "  ,_(`_))\\\n" +
                            "   \"-\\)__/\n" +
                            "    __|||__\n" +
                            "   ((__|__))",
                    ".     ,==.                              |~~~\n" +
                            "     /  66\\                            |\n" +
                            "     \\c  -_)                    |~~~\n" +
                            "      `) (                        |\n" +
                            "      /   \\               |~~~\n" +
                            "     /   \\ \\             |\n" +
                            "    ((   /\\ \\_  |~~~ \n" +
                            "     \\\\  \\ `--`  |\n" +
                            "     / / /  |~~~\n" +
                            "___ (_(___)_| ",
                    "….*?.¸.?*’\n" +
                            "….*?.@@ ?*’\n" +
                            ".*?.@@@@?*’\n" +
                            "….@@@@@@\n" +
                            "…?*@@@@`*?.¸¸\n" +
                            "…….\\\\\\||///.\n" +
                            "……..\\\\||//.\n" +
                            "………???.\n" +
                            "……….\\|/..?\n" +
                            "………..V….\n" +
                            "A Bunch Of Flower For U My Sweet friends",
                    "/\\””\\\n" +
                            "| |G |\n" +
                            "/\\”'”””\\\n" +
                            "| |*o*|\n" +
                            "/\\'””””””\\\n" +
                            "| |,;*o*;,|\n" +
                            "/\\”””””””””\\\n" +
                            "| |,;*”D”*;,|\n" +
                            "\n" +
                            "GOOD NIGHT.\n" +
                            "Sweet Dreams….",
                    "   _     _\n" +
                            "        (q'-\"-'p)\n" +
                            "        |    'Y'   |\n" +
                            "         \\_ ^ _/\n" +
                            "       .'`(>O<)`'.\n" +
                            "      /  / .-. \\  \\\n" +
                            "     (_.'|| . ||'._)\n" +
                            "       _/'.___.'\\_\n" +
                            "      /\"\\ /   \\ /\"\\\n" +
                            "      \\__/     \\__/",
                    "彡 \n" +
                            "♫ \n" +
                            "¸.¤*¨¨*¤.¸¸...¸.¤*¨¨*¤ \n" +
                            "\\¸ ّۣۜ I Love You   ×°× ᶫᵒᵛᵉ 彡 \n" +
                            ".\\¸.¤*¨¨*¤.¸¸.¸.¤*¨¨*¤ \n" +
                            "..\\ \n" +
                            "☻/ \n" +
                            "/▌ ♫♪ \n" +
                            "/ \\ (¯`•.•´¯) (¯`•.•´¯) \n" +
                            "`•.¸(¯`•.•´¯)¸ .• \n" +
                            "×°× ` •.¸.•´ ×°× ",
                    "இ ۣڰڿ - ۣڰ—...இ ۣڰڿ - —ۣڰ \n" +
                            ".*♥*...║║║║╔╗╔╗*♥* \n" +
                            "...*♥*.╠╣║║║╗╚╗. ....*❤* \n" +
                            "*♥*....║║╚╝╚╝╚╝.*♥* \n" +
                            "A Hug is worth more then thousand words ",
                    "ღ \n" +
                            "\n" +
                            "(¯`v´¯)　　 \n" +
                            "`*.¸.*´ \n" +
                            "~* ♥ *~* ♥ *~* ♥ *~ \n" +
                            "(¯`•´¯).........ღ..........。☆。*。☆。 \n" +
                            "`•.,(¯`•´¯).......ღ......★。＼｜／。★ \n" +
                            "(¯`•´¯).•´(¯`•´¯) . ღ ~ *♥ ℒℴѵℯ ♥* ~ \n" +
                            "☆☆` •.•´(¯`•´¯)..ღ..★。／｜＼。★ ",
                    "ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ \n" +
                            "♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ \n" +
                            ">>♥<<╔╗╔═╦╦╦═╗ >>♥<< \n" +
                            ">>♥<<║╚╣║║║║╩╣ >>♥<< \n" +
                            ">>♥<<╚═╩═╩═╩═╝ >>♥<< \n" +
                            "♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ \n" +
                            "ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ",
                    "Rᴇᴅɪsᴄᴏᴠᴇʀɪɴɢ Lᴏᴠᴇ . . . ღ ℒℴѵℯ ☆彡 \n" +
                            "(¯`v´¯)　　 \n" +
                            "`*.¸.*´ \n" +
                            "☻/ \n" +
                            "/▌ \n" +
                            "/ | \n" +
                            "╬═♥╬ \n" +
                            "╬♥═╬ \n" +
                            "╬♥═╬ \n" +
                            "╬═♥╬ \n" +
                            "╬═♥╬ \n" +
                            "╬♥═╬ \n" +
                            "╬♥═╬ \n" +
                            "╬♥═╬ \n" +
                            "╬♥═╬ ♥ℒℴѵℯ ",
                    " o,,,o     /),/) \n" +
                            "( ‘ ; ‘ )   ( ‘ ; ‘ ) \n" +
                            "(,,)–(,,)(,,)–(,,) \n" +
                            "U’n me forever.. \n" +
                            "and ever and ever!",
                    "ℒƠѵℯ✫* ” ♥ ” *✫ℒƠѵℯ \n" +
                            "*ℒƠѵℯ✫* ” ♥ ” *✫ℒƠѵℯ✫ \n" +
                            "•°*˜”*°• •°*”˜•°*˜”*°• .••°*”˜ ",
                    "In love, holding hands. \n" +
                            "../(,\")\\♥ ♥(\".) \n" +
                            ".../♥\\.  =   ./█\\. \n" +
                            ".._| |_ .         ._| |_ ",
                    "❀ My ✿. . . ♡. . . ♡. . . ♡ \n" +
                            "(¯`v´¯ ). ✿ Heart ❀.Is .♡ \n" +
                            ". `•.¸.•´ ♡. .❀ Forever ✿ \n" +
                            ". ¸.•´¸.•´¨) ¸.•*¨). ✿Yours ❀ \n" +
                            "(¸.•´ (¸.•´ .•´ ¸.. . ♡. . . .♡ \n" +
                            ". . .♡. . . . . .♡. . . . . ♡. . . . .♡ ",
                    "╲╲╭━━━━╮╲╲ \n" +
                            "╭╮┃▆┈┈▆┃╭╮ \n" +
                            "┃╰┫▽▽▽┣╯┃ \n" +
                            "╰━┫△△△┣━╯ \n" +
                            "╲╲┃┈┈┈┈┃╲╲ \n" +
                            "╲╲┃┈┏┓┈┃╲╲ \n" +
                            "▔▔╰━╯╰━╯▔▔",
                    "╲╲╭━━━━━━━╮╱╱ \n" +
                            "╲╭╯╭━╮┈╭━╮╰╮╱ \n" +
                            "╲┃┈┃┈▊┈┃┈▊┈┃╱ \n" +
                            "╲┃┈┗━┛┈┗━┛┈┃╱ \n" +
                            "╱┃┈┏━━━━━┓┈┃╲ \n" +
                            "╱┃┈┃┈┈╭━╮┃┈┃╲ \n" +
                            "╱╰╮╰━━┻━┻╯╭╯╲ \n" +
                            "╱╱╰━━━━━━━╯╲╲\n" +
                            "----abcdef-----\n" +
                            "┏┓┏┓┊┊┊┊┊┊┊┊┊┊┊┊ \n" +
                            "┃┗┛┣━━┳━━┳━━┳┓┏┓ \n" +
                            "┃┛┗┃╭╮┃┛┛┃┗┗┃╰┛┃ \n" +
                            "┃╰╯┃┗┛┃╰╯┃╰╯┣━╮┃ \n" +
                            "┃┏┓┃┏┓┃┏━┫┏┳┻━╯┃ \n" +
                            "┗┛┗┻┛┗┻┛┊┗┛┗━━━╯",
                    "♫ \n" +
                            "¸.¤*¨¨*¤.¸¸...¸.¤*¨* \n" +
                            "\\¸ ❤ ×°× ᶫᵒᵛᵉ ᵧₒᵤ 彡 \n" +
                            ".\\¸.¤*¨¨*¤.¸¸.¸.¤¨*¤ \n" +
                            "..\\ \n" +
                            "☻/ \n" +
                            "/▌ ♫♪ \n" +
                            "/ \\ (¯`•.•´¯) (¯`•.•´¯) \n" +
                            "`•.¸(¯`•.•´¯)¸ .• \n" +
                            "×°× ` •.¸.•´ ×°×°\n" +
                            "ᶫᵒᵛᵉᵧₒᵤ ",
                    "♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ \n" +
                            "♥ ╔══╗ ╔╗╔═╦╦╦═╗ ╔╗╔╗ \n" +
                            "♥ ╚╗╔╝ ║╚╣║║║║╩╣ ║╚╝║ \n" +
                            "♥ ╔╝╚╗ ╚═╩═╩═╩═╝ ╚══╝ \n" +
                            "♥ ╚══╝ …… ღ ♥ kiss ღ ♥ …… \n" +
                            "♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ღ ♥ ",
                    "♡ ➹ ᶫᵒᵛᵉ 彡 \n" +
                            "★ \n" +
                            "(¯`•.•´¯) (¯`•.•´¯) \n" +
                            "~`•.¸(¯`•.•´¯)¸ .•~ \n" +
                            "×°× ` •.¸.•´ ×°× \n" +
                            "ღ ~ ☸*´`'*°☆ \n" +
                            "`☆`-`☆.¸¸.♥`ᶫᵒᵛᵉ ✰彡ღ \n" +
                            "----abcdef-----\n" +
                            "… (_( o)>(¯`•´¯)<(o )_/) \n" +
                            "… (___)....`•.¸•´… (___) \n" +
                            "..… ¥¥……………………¥¥ \n" +
                            "----abcdef-----\n" +
                            "©©___©©_ ©©__©©©©___©©©© \n" +
                            "©©__©©__ ©©_©©______©© \n" +
                            "©©©©____ ©©__©©©©___©©©© \n" +
                            "©©__©©__ ©©______©©______©© \n" +
                            "©©___©©_ ©©__©©©©__©©©© ",
                    "░×°×░×°×░ \n" +
                            "☸*´`'*°☆ \n" +
                            "`☆`-`☆.¸¸.♥`ℒℴ×°×ѵℯ \n" +
                            "----abcdef-----\n" +
                            "┼┼ ♥ ♥ \n" +
                            "┼┼ ♥ ♥ \n" +
                            "┼┼ ♥ ♥ .(Y).........(Y) \n" +
                            "┼┼┏┓.(^.^)......(^.^) ¸.•*¨✿ \n" +
                            "┼┼┃┃.((')(')♥(')(')) ♥ ♥ ♥ ♥ ♥ \n" +
                            "┼┼┃┃┏━━┳┓┏┳━━┓ \n" +
                            "┼┼┃┃┃┏┓┃┗┛┃┃━┫ Hugs \n" +
                            "┼┼┃┗┫┗┛┣┓┏┫┃━┫ ¸.•*¨✿ \n" +
                            "┼┼┗━┻━━┛┗┛┗━━┛ ¸.•*¨✿ ¸.•*¨✿Kiss ",
                    "╰დ╮ ღ♥ღ ╭დ╯ \n" +
                            "ღ♥♥ ԼƠƔЄ ♥♥ღ ",
                    "╭╮╭╮╮╭╮╮╭╮╮╭╮╮ \n" +
                            "┃┃╰╮╯╰╮╯╰╮╯╰╮╯ \n" +
                            "┃┃╭┳━━┳━╮╭━┳━━╮ \n" +
                            "┃┃┃┃╭╮┣╮┃┃╭┫╭╮┃ \n" +
                            "┃╰╯┃╰╯┃┃╰╯┃┃╰┻┻╮ \n" +
                            "╰━━┻━━╯╰━━╯╰━━━╯ ",
                    "╔══╗ .. -```- \n" +
                            "╚╗╔╝ .. C ' -') \n" +
                            "╔╝╚╗..O( ¯`-´¯)o \n" +
                            "╚══╝...(\")`• . •´(\") \n" +
                            "╔╗╔═╦╦╦═╗ ╔╗╔╗ \n" +
                            "║╚╣║║║║╩╣ ║╚╝║ \n" +
                            "╚═╩═╩═╩═╝ ╚══╝ ",
                    "╔═════════ ೋღ❤ღೋ ═════════╗ \n" +
                            "ೋ ❤❤❤~~I LOVE YOU SO MUCH~~❤❤❤ ೋ \n" +
                            "╚═════════ ೋღ❤ღೋ ═════════╝ ",
                    "╭━ ♥━ ♥━ ♥━ ♥━ ♥━ ♥━ ♥━ ♥━ ♥ ♥ ♥ \n" +
                            "╰╮┏┳┳┳┓┏┳┳┳┳┓┏┳┳┳┳┓ \n" +
                            "┏┻╋╋┻┻┫┣┻╋╋┻┫┣┻╋╋┻┫ \n" +
                            "┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻ⓞ┻┻ⓞ┻┻ⓞ╯▄ ▄ ▄ ▄ ▄ ",
                    "┈┈┈┈╭╮╭╮ \n" +
                            "┈┈┈┈┃┃┃┃ \n" +
                            "┈┈┈┈┃┃┃┃ \n" +
                            "┈┈┈┈┃┗┛┣┳╮ \n" +
                            "┈┈┈╭┻━━┓┃┃ \n" +
                            "┈┈┈┃╲┏━╯┻┫ \n" +
                            "┈┈┈╰╮╯┊┊╭╯ ᶫᵒᵛᵉ 彡 \n" +
                            "★ \n" +
                            "(¯`•.•´¯) (¯`•.•´¯) \n" +
                            "~`•.¸(¯`•.•´¯)¸ .•~ \n" +
                            "×°× ` •.¸.•´ ×°× \n" +
                            "ღ ~ ☸*´`'*°☆ \n" +
                            "`☆`-`☆.¸¸.♥`ᶫᵒᵛᵉ ✰彡ღ ",
                    "_██_ \n" +
                            "‹(•¿•)› \n" +
                            "..(█) \n" +
                            "…/ | \n" +
                            "⊰♥⊱.. \n" +
                            "…⊰♥⊱… \n" +
                            "…..⊰♥⊱… \n" +
                            "…….⊰♥⊱… \n" +
                            "………⊰♥⊱… \n" +
                            "…….⊰♥⊱… \n" +
                            "…..⊰♥⊱… \n" +
                            "…⊰♥⊱… \n" +
                            ".⊰♥⊱.. ",
                    "________/)_☼_____./¯\"\"\"/') \n" +
                            "¯¯¯¯¯¯¯¯¯\\)¯☼¯¯¯¯'\\_„„„„\\) \n" +
                            "◯╔╗═◯╔═╗◯╔╦╗◯╔═╗◯ \n" +
                            "◯║╚╗◯║║║◯║║║◯║╩╣◯ \n" +
                            "◯╚═╝◯╚═╝◯╚═╝◯╚═╝◯ ",
                    "....♥ ♥....♥♥...... \n" +
                            "..♥......♥.....♥.... \n" +
                            "... ♥..........♥..... \n" +
                            ".......♥....♥......... \n" +
                            "..........♥........... \n" +
                            "I LOVE YOU! ",
                    "..... (¯`v´¯)♥ \n" +
                            ".......•.¸.•´ \n" +
                            "....¸.•´ \n" +
                            "... ( \n" +
                            "☻/ \n" +
                            "/▌♥♥ \n" +
                            "/ \\ ♥♥ \n" +
                            "╭ ♥ ╯ƒღʀ γღµ , ωɨтɦ ℓღνє ╭ ♥ ╯ ",
                    ". .+''\"+.+''\"+. \n" +
                            "+. . .Love. . .+ \n" +
                            ". '+. .You. .+' \n" +
                            ". . . .\"+,+'' \n" +
                            ". . . . / \n" +
                            ". . . . | ()\"\"() \n" +
                            ". . . .(\"( 'o' ) \n" +
                            ". . . .,-)___)l'--. \n" +
                            ". . .-\"=(o)===(o)='",
                    "(”)….(”) \n" +
                            "( ‘ o ‘ ) \n" +
                            "(”)–(”) \n" +
                            "(””’)-(””’) \n" +
                            "I Love You ",
                    "(¯`v´¯)　　 \n" +
                            "`*.¸.*´ \n" +
                            "(¯`♥´¯)ⓛⓞⓥⓔ(¯`♥´¯) \n" +
                            "┃. ┏┃┃┃┏┛⋱ ⋮ ⋰ \n" +
                            "┃. ━┛━┛┏┛⋯ ღ ⋯ \n" +
                            "━┛. . ღ .. ..━┛⋰ ⋮ ⋱ \n" +
                            "╰დ╮♥♥♥❤♥♥♥╭დ╯☆彡",
                    "(¯`L´¯)✿ \n" +
                            ".`•.¸.•´(¯`O´¯)✿ \n" +
                            "******.`•.¸.•´(¯`V´¯)✿ \n" +
                            "...***********`•.¸.•´(¯`E´¯) \n" +
                            ".........**************•.¸.•´ℒℴνℯ ",
                    "(¯`•.•´¯) (¯`•.•´¯) \n" +
                            "*`•.¸(¯`•.•´¯)¸.•´ \n" +
                            "♡ º° ¤`•.¸.•´ ¤ º° ♡",
                    "•✤─█▄◯╲╱☰─✤•",
                    "((¯`♥´¯)) ✰ \n" +
                            ".`*.¸.*´.✿¸.•* ԼƠƔЄ *•.¸♥ ✰ ԼƠƔЄ ",
                    "(\\__/) \n" +
                            "(◕‿◕) \n" +
                            "(“)_(“) \n" +
                            "(¯`v´¯)　　 \n" +
                            "`*.¸.*´ \n" +
                            "(¯` ♥ ´¯)ⓛⓞⓥⓔ(¯` ♥ ´¯) \n" +
                            "┃. ┏┃┃┃┏┛⋱ ⋮ ⋰ \n" +
                            "┃. ━┛━┛┏┛⋯ ღ ⋯ \n" +
                            "━┛. . ღ .. ..━┛⋰ ⋮ ⋱ \n" +
                            "╰დ╮ ♥ ♥ ♥ ♥ ♥ ♥ ♥ ╭დ╯",
                    "(¯`•´¯).........ღ..........。☆。*。☆。 \n" +
                            "`•.,(¯`•´¯).......ღ......★。＼｜／。★ \n" +
                            "(¯`•´¯).•´(¯`•´¯).ღ ☆ ★ кìʂʂεʂ ☆ ★ \n" +
                            "☆☆` •.•´(¯`•´¯)..ღ..★。／｜＼。★ \n" +
                            "\n" +
                            "(¯`♥´¯) MAY YOU FEEL LOVED (¯`♥´¯) \n" +
                            "`*.¸.*´................................... `*.¸.*´",
                    ". .`҉҉´- . -* \n" +
                            "-`҉҉´-¸.*-`҉҉ \n" +
                            "@══════@ \n" +
                            ".║ Happy      ║ \n" +
                            ".║ Birthday...║ \n" +
                            "@══════@ \n" +
                            "*.-`҉҉´-*´ ¸*-`҉҉ \n" +
                            "¸.*.-`҉҉ ",
                    "╔•═•-⊰❉⊱•═•⊰❉⊱•═•⊰❉⊱ •═•╗ \n" +
                            "║  ＧＯＯＤ　ＭＯＲＮＩＮＧ !!!║ \n" +
                            "╚•═•-⊰❉⊱•═•⊰❉⊱•═•⊰❉⊱ •═•╝",
                    "╭╯╭╯╭╯ \n" +
                            "█▓▓▓▓▓█═╮ \n" +
                            "█▓▓▓▓▓█▏︱ \n" +
                            "█▓▓▓▓▓█═╯ \n" +
                            "◥█████◤ \n" +
                            "┏━━━┓╋╋╋╋╋╋╋┏┓ \n" +
                            "┃┏━┓┃╋╋╋╋╋╋╋┃┃ \n" +
                            "┃┃╋┗╋━━┳━━┳━┛┃ \n" +
                            "┃┃┏━┫┏┓┃┏┓┃┏┓┃ \n" +
                            "┃┗┻━┃┗┛┃┗┛┃┗┛┃ \n" +
                            "┗━━━┻━━┻━━┻━━┛ \n" +
                            "┏━┓┏━┓ \n" +
                            "┃┃┗┛┃┃ \n" +
                            "┃┏┓┏┓┣━━┳━┳━┓┏┳━┓┏━━┓ \n" +
                            "┃┃┃┃┃┃┏┓┃┏┫┏┓╋┫┏┓┫┏┓┃ \n" +
                            "┃┃┃┃┃┃┗┛┃┃┃┃┃┃┃┃┃┃┗┛┃ \n" +
                            "┗┛┗┛┗┻━━┻┛┗┛┗┻┻┛┗┻━┓┃ \n" +
                            "╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏━┛┃ \n" +
                            "╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗━━",
                    "-----__∗_]¯¯¯[________∗ \n" +
                            "∗--/____________________\\ \n" +
                            "---/___∗_____ /\\__________\\ \n" +
                            "--/__________/▒ \\____∗____\\ \n" +
                            "-/__________/▒▒▒\\_________\\ \n" +
                            "/∗_________/▒:▓:▒ \\____∗___\\ \n" +
                            "═══════▒II≡≡≡≡II▒══════ \n" +
                            "--▒▒:▓:▓:▒║██:║▒:▓:▓:▒▒▒ \n" +
                            "--▒▒:▓:▓:▒║██:║▒:▓:▓:▒▒▒ \n" +
                            "--▒▒:▓:▓:▒║██:║▒:▓:▓:▒▒▒ \n" +
                            "---̶̶̿̿ ̶̿ ̶̶̶̶̿̿̿̿ ̶̶̿̿ ̶̿ ̶̶̿̿ ̶̶̶̿̿̿ ̶̿ ̶̶̿̿ ̶̿ ̶̶̶̶̿̿̿̿ ̶̶̿̿ ̶̶̶̶̿̿̿̿ ̶̿ ̶̶̿̿ ̶̿ ̶̶̿̿ ̶▃▅▅▃̶̶̿̿ ̶̿ ̶̶̶̶̿̿̿̿ ̶̶̿̿ ̶̿ ̶̶̿̿ ̶̶̶̿̿̿ ̶̿ ̶̶̿̿ ̶̿ ̶̶̶̶̿̿̿̿ ̶̶̿̿ ̶̶̶̶̿̿̿̿ ̶̿ ̶̶̿̿ ̶̿ ̶̶̶̶̿̿̿̿",
                    "●★●―★―●★● \n" +
                            "▬▬▬.◙.▬▬▬ \n" +
                            "═▂▄▄▓▄▄▂ \n" +
                            "◢◤ █▀▀████▄▄▄▄◢◤ \n" +
                            "█▄ █ー ███▀▀▀▀▀▀▀╬ \n" +
                            "◥█████◤ \n" +
                            "══╩══╩══ \n" +
                            "●●―★―●●",
                    "……….٩(-̮̮̃-̃)۶٩(̾●̮̮̃̾•̃ ̾)۶٩(-̮̮̃-̃)۶٩(̾●̮̮̃̾•̃̾)۶ \n" +
                            "………..|”\"”\"”\"”\"”\"”\"”\"”\"”\"‘ ””’”\"”\" “” |\\^___ \n" +
                            "………. | ..ૐ…Hippie-Bus..ૐ.. |||”|”\"\\___ \n" +
                            "………..|___________________|||_|____ |) \n" +
                            "………..!(@)’(@)”\"” ”’”\"**!(@)(@)***!(@) \n" +
                            "”””””””””””””””””””” ””””””””’ ”””””””””””””””””””” ””””””’",
                    "⁀) ✫ ✫ ✫. \n" +
                            "`⋎´✫¸.•°*”˜˜”*°•✫ \n" +
                            "..✫¸.•°*”˜˜”*°•.✫ \n" +
                            "☻/ღ˚ •。* ♥♥ ˚ ˚✰˚ ˛★* 。 ღ˛° 。* °♥ ˚ • ★ *˚ .ღ 。 \n" +
                            "/▌*˛˚ ░M░A░K░E░ - ░W░I░S░H░ ˚ ✰* ★ \n" +
                            "/ \\ ˚. ★ *˛ ˚♥♥* ✰。˚ ˚ღ。* ˛˚ ♥♥ 。✰˚* ˚ ★ღ ˚ 。✰ •* ˚ ♥♥\"",
                    "╔══════════════════╗ \n" +
                            "║.(¯`♥´¯)´´¯`•°*”˜˜”*°•. ƸӜƷ.☆ \n" +
                            "║.`*.¸.*.•°*”˜˜”*°•.ƸӜƷ \n" +
                            "║.•°*”˜˜”*°•.ƸӜƷ ✶* ¸ .✫ ♥ \n" +
                            "▒╔╗╔╗╔╦╗ \n" +
                            "▒╠╣║║╠╣╠╦═╗ \n" +
                            "▒║║║╚╣║═╣╩╣ \n" +
                            "▒╚╝╚═╩╩╩╩═╝ \n" +
                            "╚══════════════════╝",
                    "┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊ \n" +
                            "┊┊┊┏╮┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊ \n" +
                            "┊┊┊┃┃┊┊┊┊┏┓┊┏┓┏┓╭━┓┏━┓┊┊ \n" +
                            "━▅━╯┗━╮┊┊┃┃┊┃┃┃┗╯╭┛┃━┫┊┊ \n" +
                            "┈▇┈┈┈┈┃┊┊┃┗┓┃┃┃┏╮╰┓┃━┫┊┊ \n" +
                            "┈▇━╮┈┈┃┊┊┗━┛┗┛┗┛╰━┛┗━┛┊┊ \n" +
                            "▔▔┊╰━━╯┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊ \n" +
                            "┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊┊",
                    "╲╲╲╲╲┏━━━┓╱╱╱╱╱ \n" +
                            "╲┏━━━┻━━━┻━━━┓╱ \n" +
                            "╲┃╭━╮┏━━━┓╭━╮┃╱ \n" +
                            "╱┃┃╳┃┣◯━◯┫┃╳┃┃╲ \n" +
                            "╱┃╰━╯┣━━━┫╰━╯┃╲ \n" +
                            "╱┃┈▊▊▊▊┈▂▃▅▇┈┃╲ \n" +
                            "╱┗━━━━━━━━━━━┛╲",
                    "... \n" +
                            "......./ 7................♪ \n" +
                            "....../_( \n" +
                            "......|_|......♫ \n" +
                            "......|_|..........................♫ \n" +
                            "......|_|....... ♪ \n" +
                            "......|_|...............♫ \n" +
                            "..((¯¯¯¯)) ...... ♪ \n" +
                            "...)).O.((.......... ♪ \n" +
                            "..//..=..\\\\........... ♪ \n" +
                            ".((.____.))......... ♪",
                    "╔╦╦ \n" +
                            "╠╬╬╬╣ \n" +
                            "╠╬╬╬╣ I ♥ \n" +
                            "╠╬╬╬╣ Chocolate \n" +
                            "╚╩╩╩╝ \n" +
                            "Chocolate Bar",
                    "/╲ ︵ ╱\\ \n" +
                            "l (◉) (◉) l \n" +
                            "\\ ︶ V︶ / \n" +
                            "/↺↺↺↺\\ \n" +
                            "↺↺↺↺↺ \n" +
                            "\\↺↺↺↺/ \n" +
                            "¯¯/\\¯/\\¯¯",
                    ".°ʅ︵ʃ° \n" +
                            "..(°‿°) \n" +
                            "〇(▬)〇 \n" +
                            "✿*(▬) \n" +
                            "Ɓҽҽ◥\n" +
                            "\n" +
                            "(¯`v´¯) \n" +
                            "`•.¸.•´ \n" +
                            "☻/ \n" +
                            "/▌\n" +
                            "\n" +
                            "Me and My Heart, Flying in the sky ",
                    "‧ ‧ ⋱ ⋱ ⋮ ⋰ ⋰ \n" +
                            "‧ ‧ ‧ ‧ ⋱ ◯⋰ \n" +
                            "~⁀~ ⁀~⁀~⁀~ \n" +
                            "....(),() .♥. (\\.∕) \n" +
                            "... (__) . . .(__) \n" +
                            ".. (_o_ )...(_o_) \n" +
                            "´”``”`´”`´”`` \n" +
                            "Two Cute Friends",
                    "  . ¨ ¨ ¨••♥•• \n" +
                            "  .•´*•.( - .- ).•*`•. \n" +
                            "  `•--•´►♥◄`•--•´ \n" +
                            "  ¨ ¨ ¨ (\"') (\"')\n" +
                            "----abcdef-----\n" +
                            "☆.´ `. ☽¸.☆ \n" +
                            "(͡๏̯͡๏)(͡๏̯͡๏) \n" +
                            "( , ,)( ,,). \n" +
                            "¯**´¯**´¯` \n" +
                            "G(๏̯͡๏̯͡)(๏̯͡๏̯͡) |) NIGHT",
                    "╔═╗══════╔╗══╔╗═════════ \n" +
                            "║═╬╦╦╦═╦═╣╚╗╔╝╠╦╦═╦═╦══╗ \n" +
                            "╠═║║║║╩╣╩╣╔╣║╬║╔╣╩╣╬║║║║ \n" +
                            "╚═╩══╩═╩═╩═╝╚═╩╝╚═╩╩╩╩╩╝♥",
                    "╔══════════════════╗ \n" +
                            "║.(¯`♥´¯)´´¯`•°*”˜˜”*°•. ★ \n" +
                            "║.`*.¸.*.•°*”˜˜”*°•.★ \n" +
                            "║.•°*”˜˜”*°•.★✶* ¸ .✫ ♥ \n" +
                            "║✿ ~~~ GOOD NIGHT ~~~ ✿ \n" +
                            "╚══════════════════╝ ",
                    "｡♥｡･ﾟ♡ﾟ･｡♥｡･｡･｡･｡･｡･｡･｡･｡･｡･｡･｡♥｡･ﾟ♡ﾟ･｡♥｡ \n" +
                            "╱╱╱╱╱╱╱╭╮╱╱╱╭╮╱╭╮╭╮ \n" +
                            "╭━┳━┳━┳╯┃╭━┳╋╋━┫╰┫╰╮+ﾟ★* \n" +
                            "┃╋┃╋┃╋┃╋┃┃┃┃┃┃╋┃┃┃╭┫☆ \n" +
                            "┣╮┣━┻━┻━╯╰┻━┻╋╮┣┻┻━╯＊+*･　 \n" +
                            "╰━╯╱╱╱╱╱╱╱╱╱╱╰━╯+ﾟ★*☆ \n" +
                            "｡♥｡･ﾟ♡ﾟ･｡♥｡ Exclusive ♥｡･ﾟ♡ﾟ･｡♥｡\n" +
                            "\n" +
                            ".      ✫                ˏ ⌢ ˎ ⌢ ˎ \n" +
                            ".              *        (˒  ° ʾ)⌣⁀ \n" +
                            ".             ⌣⁀ˏ ⌢  ` ⌣ ´ \n" +
                            ". ✫                                   ✫ \n" +
                            ".ƓƠƠƊƝƖƓĤƬ ŞƤƦƝƘɭƐŞ",
                    ".,-,*´`'*°☆ \n" +
                            "/.( \n" +
                            "\\ {     ۰۪۫G۪۫۰۰۪۫O۪۫۰۰۪۫O۪۫۰۰۪۫D۪۫۰ ۰۪۫N۪۫۰۰۪۫I۪۫۰۰۪۫G۪۫۰۰۪۫H۪۫۰۰۪۫T۪۫۰ \n" +
                            "`-`☆.¸¸.♥\n" +
                            "----abcdef-----\n" +
                            ", ‘ , ‘ , ‘ , ‘ , ‘ , ‘ , ‘ , \n" +
                            ", ‘ , ‘ , ‘ , ‘ , ‘, ‘ , ‘ , \n" +
                            ",__,____, \n" +
                            "/____,_/ \\ .;’;';. \n" +
                            "l__[]__l_ l ,,)(,,\n" +
                            "\n" +
                            "Enjoy the cool climate. \n" +
                            "Good Evening",
                    "—— – -•*”*• \n" +
                            "  —-..(¯*( -.- )*¯) Night Time \n" +
                            "  —— (. /(“)(“)\\ .) sprinkles \n" +
                            "  ¨︵¸︵( ░░ ) .︵. ︵ \n" +
                            "  (´░░░░░░´░░ ) \n" +
                            "  ¨`´︶´¯`︶´`︶´`︶",
                    "ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ \n" +
                            "•*´¨`*•.¸¸.•*´¨`*•.¸¸.•*´¨`*•.¸¸.•*´¨`*•.¸¸.• \n" +
                            "::: (\\_(\\ ...*...*...*...*...*...*...*...*...*...*...*...* \n" +
                            "*: (=' :') :::::::: GOOD MORNING :::::::::: \n" +
                            "•.. (,('')('')¤...*...*...*...*...*...*...*...*...*...*...* \n" +
                            "¸.•*´¨`*•.¸¸.•*´¨`*•.¸¸.•*´¨`*•.¸¸.•*´¨`*•.¸ \n" +
                            "ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ♥ஜ۩۞۩ஜ",

                    "☆¸.•´¯`•.¸☽☆                  \n" +
                            "\n" +
                            "       нανє α                 \n" +
                            "\n" +
                            "     вєαυтιfυℓ             \n" +
                            "\n" +
                            "    вℓєѕѕє∂ ∂αу..            \n" +
                            "\n" +
                            "☆☾¸.•´¯`•.¸☆",
                    "                           _(\\_/) \n" +
                            "                         ,((((^`\\ \n" +
                            "                        ((((  (6 \\ \n" +
                            "                      ,((((( ,      \\ \n" +
                            "  ,,,_              ,(((((  /\"._  ,`, \n" +
                            "((((\\\\ ,...       ,((((   /    `-.-' \n" +
                            ")))  ;'    `\"'\"'\"\"((((   (      \n" +
                            "(((  /            (((      \\ \n" +
                            ")) |                      | \n" +
                            "((  |        .       '     | \n" +
                            "))  \\     _ '      `t   ,.') \n" +
                            "(   |   y;- -,-\"\"'\"-.\\   \\/  \n" +
                            ")   / ./  ) /         `\\  \\ \n" +
                            "   |./   ( (           / /' \n" +
                            "   ||     \\\\          //'| \n" +
                            "   ||      \\\\       _//'|| \n" +
                            "   ||       ))     |_/  || \n" +
                            "   \\_\\     |_/          || \n" +
                            "   `'\"                  \\_\\ \n" +
                            "                        `'\""
            };

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
        final TextServicesManager tsm = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        if (tsm != null) {
            mScs = tsm.newSpellCheckerSession(null, null, this, true);
        }

    }

    @Override
    public void onInitializeInterface() {
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
        View view = getLayoutInflater().inflate(R.layout.input, null);
        ivEmoji = view.findViewById(R.id.ivEmoji);
        ivArt = view.findViewById(R.id.ivArt);
        mInputView = view.findViewById(R.id.keyboard);
        RelativeLayout linKeyboard = view.findViewById(R.id.linKeyboard);
        linEmoji = view.findViewById(R.id.linEmoji);
        ivClose = view.findViewById(R.id.ivCancel);
        gvEmoji = view.findViewById(R.id.gvEmoji);
        rv_art_list = view.findViewById(R.id.rv_art_list);
        ivAbc = view.findViewById(R.id.ivAbc);
        ivSmile = view.findViewById(R.id.ivSmile);
        ivAnimal = view.findViewById(R.id.ivAnimal);
        ivLamp = view.findViewById(R.id.ivLamp);
        ivFood = view.findViewById(R.id.ivFood);
        ivSocial = view.findViewById(R.id.ivSocial);
        LinearLayout linCategory = view.findViewById(R.id.linCategory);
        ivGoogleSearch = view.findViewById(R.id.ivgooglesearch);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rv_art_list.setLayoutManager(gaggeredGridLayoutManager);
        FillArtAdapter fillArtAdapter = new FillArtAdapter(getApplicationContext(), artArrayList);
        rv_art_list.setAdapter(fillArtAdapter);

        emojiArrayList = smilyArrayList;
        fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), emojiArrayList);
        gvEmoji.setAdapter(fillEmojiAdapter);

        ivSmile.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivAnimal.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivLamp.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivFood.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivSocial.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
        ivClose.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);

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

                npd1.setCornerRadius(Float.parseFloat(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_RADIUS, "18")));
                npd1.setAlpha(Integer.parseInt(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_OPACITY, "255")));

                switch (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_STROKE, "2")) {
                    case "1":
                        npd1.setStroke(0, getApplicationContext().getResources().getColor(R.color.colorPrimary));
                        break;
                    case "2":
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case "3":
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case "4":
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case "5":
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
                emojiArrayList = smilyArrayList;
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), smilyArrayList);
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
                ivClose.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = animalArrayList;
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), animalArrayList);
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
                ivClose.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = lampArrayList;
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), lampArrayList);
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
                ivClose.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = foodArrayList;
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), foodArrayList);
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
                ivClose.setColorFilter(mContext.getResources().getColor(R.color.silver));
            }
        });

        ivSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiArrayList = socialArrayList;
                fillEmojiAdapter = new FillEmojiAdapter(getApplicationContext(), socialArrayList);
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
                ivClose.setColorFilter(mContext.getResources().getColor(R.color.silver));
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

        fillArtAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mPredictionOn) {
                    mComposing.append(artArrayList[position]);
                    getCurrentInputConnection().setComposingText(mComposing, 1);
                    updateShiftKeyState(getCurrentInputEditorInfo());
                    updateCandidates();
                } else {
                    getCurrentInputConnection().commitText(artArrayList[position], 1);
                }
            }
        });

        ivAbc.setColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.MULTIPLY);

        ivAbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInputView.setVisibility(View.VISIBLE);
                linEmoji.setVisibility(View.GONE);
                rv_art_list.setVisibility(View.GONE);

                ivAbc.setColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.MULTIPLY);
                ivEmoji.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivArt.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivGoogleSearch.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
            }
        });

        ivEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInputView.setVisibility(View.GONE);
                linEmoji.setVisibility(View.VISIBLE);
                rv_art_list.setVisibility(View.GONE);

                ivAbc.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivEmoji.setColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.MULTIPLY);
                ivArt.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivGoogleSearch.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);

            }
        });

        ivArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInputView.setVisibility(View.GONE);
                linEmoji.setVisibility(View.GONE);
                rv_art_list.setVisibility(View.VISIBLE);

                ivAbc.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivEmoji.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivArt.setColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.MULTIPLY);
                ivGoogleSearch.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
            }
        });

        ivGoogleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Search = null;
                try {
                    Search = URLEncoder.encode("", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + Search);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                ivAbc.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivEmoji.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivArt.setColorFilter(ContextCompat.getColor(mContext, R.color.silver), PorterDuff.Mode.MULTIPLY);
                ivGoogleSearch.setColorFilter(ContextCompat.getColor(mContext, R.color.white), PorterDuff.Mode.MULTIPLY);
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
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
            linKeyboard.setBackgroundResource(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_BG_IMAGE, R.drawable.theme_color1));
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
        setLatinKeyboard(mQwertyKeyboardShift);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(false);
        mInputView.setShifted(true);
        return view;
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        mInputView.setKeyboard(nextKeyboard);
    }

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
        mComposing.setLength(0);
        updateCandidates();

        if (!restarting) {
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
                mPredictionOn = true;

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
        mCurKeyboard.setImeOptions(getApplicationContext().getResources(), attribute.imeOptions);
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
        setLatinKeyboard(mCurKeyboard);
        mInputView.closing();
        final InputMethodSubtype subtype = mInputMethodManager.getCurrentInputMethodSubtype();
        mInputView.setSubtypeOnSpaceKey(subtype);
        setInputView(onCreateInputView());
    }

    @Override
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        mInputView.setSubtypeOnSpaceKey(subtype);
    }

    /**
     * Deal with the editor reporting movement of its cursor.
     */
    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
                                  int newSelStart, int newSelEnd,
                                  int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

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

        if (attr != null && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {
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
            if (mComposing.length() > 0 && null != mScs) {
                ArrayList<String> list = new ArrayList<>();
                mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(mComposing.toString())}, 5);
                setSuggestions(list, true, true);
            } else {
                setSuggestions(null, false, false);
            }
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {

        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(false);
        } else if (isExtractViewShown()) {
            setCandidatesViewShown(false);

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
            setLatinKeyboard(mQwertyKeyboardShift);
            mInputView.setShifted(true);
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
                checkToggleCapsLock();
                setLatinKeyboard(mQwertyKeyboard);
                mInputView.setShifted(false);
                mCapsLock = false;
            } else {
                if (mQwertyKeyboard == currentKeyboard) {
                    // Alphabet keyboard
                    checkToggleCapsLock();
                    setLatinKeyboard(mQwertyKeyboardShift);
                    mInputView.setShifted(true);
                } else if (mQwertyKeyboardShift == currentKeyboard) { // Alphabet keyboard
                    checkToggleCapsLock();
                    if (mCapsLock) {
                        setLatinKeyboard(mQwertyKeyboardShift);
                        mInputView.setShifted(true);
                    } else {
                        setLatinKeyboard(mQwertyKeyboard);
                        mInputView.setShifted(false);
                    }
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
        if (mInputView.isShifted()) {
            primaryCode = Character.toUpperCase(primaryCode);
            if (!mCapsLock) {
                setLatinKeyboard(mQwertyKeyboard);
                mInputView.setShifted(mCapsLock || !mInputView.isShifted());
            }
        }
        if (mPredictionOn) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            getCurrentInputConnection().finishComposingText();
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        } else {
            updateShiftKeyState(getCurrentInputEditorInfo());
            getCurrentInputConnection().commitText(String.valueOf((char) primaryCode), 1);
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

    private void checkToggleCapsLock() {
        GlobalClass.printLog("SoftKeyboard", "---------------checkToggleCapsLock---------------");

        long now = System.currentTimeMillis();
        if (mLastShiftTime + 800 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;

        } else {
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
        if (mCompletionOn || mPredictionOn) {
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

        GlobalClass.printLog("SoftKeyboard", "---------------onPress---------------" + GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "off"));

        Log.e("KEYBOARD", "hello" + GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "off"));

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "off").equals("on") && isNormalMode()) {

            //remove this comment for the play key tone
            beep(10);
        }
    }

    public void onRelease(int primaryCode) {
        GlobalClass.printLog("SoftKeyboard", "---------------onRelease---------------");

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

    private static final int NOT_A_LENGTH = -1;

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
            for (int j = 0; j < ssi.getSuggestionsCount(); ++j) {
                dumpSuggestionsInfoInternal(
                        sb, ssi.getSuggestionsInfoAt(j), ssi.getOffsetAt(j), ssi.getLengthAt(j));
            }
        }
        Log.d("SoftKeyboard", "SUGGESTIONS: " + sb.toString());
        setSuggestions(sb, true, true);
    }

    private void beep(int volume) {
        GlobalClass.printLog("SoftKeyboard", "---------------beep---------------");

        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        MediaPlayer player;

        try {
            player = MediaPlayer.create(this, GlobalClass.tempSoundName);
            if (manager != null) {
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            }
            player.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

        } catch (Exception ignored) {
        }
    }

    public boolean isNormalMode() {
        GlobalClass.printLog("SoftKeyboard", "---------------isNormalMode---------------");

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (am != null) {
            switch (am.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT:
                    return false;
                case AudioManager.RINGER_MODE_VIBRATE:
                    return false;
                case AudioManager.RINGER_MODE_NORMAL:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

}
