package com.MaksimKavalenka.PositionalNotationConverter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.MaksimKavalenka.PositionalNotationConverter.Fragments.FragmentAuxiliary;
import com.MaksimKavalenka.PositionalNotationConverter.Fragments.FragmentCommon;
import com.MaksimKavalenka.PositionalNotationConverter.Fragments.TitleAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements Data, OnClickListener {

    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_LANGUAGE = "language";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private Handler handler;
    private TitleAdapter titleAdapter;
    private ViewPager mViewPager;

    private Parts integ;
    private Intent intent;

    private String[] names;
    private String[] modes;
    private String[] systems;
    private String[] languages;
    private String[] strings;

    private String text = "";

    private TextView sys;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    private Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF, buttonSaM, buttonPoint, buttonBackspace, buttonClear;
    private Integer st = DEC;
    private Byte system = drop[DEC-2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializationLanguage();

        intent = new Intent(this, AboutActivity.class);

        names = getResources().getStringArray(R.array.names);
        modes = getResources().getStringArray(R.array.modes);
        systems = getResources().getStringArray(R.array.systems);
        languages = getResources().getStringArray(R.array.languages);
        strings = getResources().getStringArray(R.array.about);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(this);
        button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(this);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonA.setOnClickListener(this);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonB.setOnClickListener(this);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonC.setOnClickListener(this);
        buttonD = (Button) findViewById(R.id.buttonD);
        buttonD.setOnClickListener(this);
        buttonE = (Button) findViewById(R.id.buttonE);
        buttonE.setOnClickListener(this);
        buttonF = (Button) findViewById(R.id.buttonF);
        buttonF.setOnClickListener(this);
        buttonSaM = (Button) findViewById(R.id.buttonSaM);
        buttonSaM.setOnClickListener(this);
        buttonPoint = (Button) findViewById(R.id.buttonPoint);
        buttonPoint.setOnClickListener(this);
        buttonBackspace = (Button) findViewById(R.id.buttonBackspace);
        buttonBackspace.setOnClickListener(this);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
        sys = (TextView) findViewById(R.id.textSystem);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                integ.setAdapter();
                return false;
            }
        });

        initialization();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        getSupportFragmentManager().putFragment(savedInstanceState, FragmentCommon.class.getName(), titleAdapter.getItem(0));
        getSupportFragmentManager().putFragment(savedInstanceState, FragmentAuxiliary.class.getName(), titleAdapter.getItem(1));
        savedInstanceState.putByte("system", system);
        savedInstanceState.putInt("st", st);
        savedInstanceState.putString("text", text);
        savedInstanceState.putStringArray("names", names);
        savedInstanceState.putStringArray("systems", systems);
        savedInstanceState.putStringArray("modes", modes);
        savedInstanceState.putStringArray("languages", languages);
        savedInstanceState.putStringArray("strings", strings);
        savedInstanceState.putStringArray("meta", integ.getMeta());
        savedInstanceState.putInt("prot", integ.getProt());
        savedInstanceState.putString("dec", integ.getDec());
        savedInstanceState.putString("save", integ.getSave());
        savedInstanceState.putString("frac", integ.getFracS());
        savedInstanceState.putInt("multi", integ.getMulti());
        savedInstanceState.putBoolean("mode", integ.getMode());
        savedInstanceState.putBoolean("flagFrac", integ.getFlagFrac());
        savedInstanceState.putBoolean("flagSaM", integ.getFlagSaM());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle restoredInstanceState) {
        super.onRestoreInstanceState(restoredInstanceState);
        ArrayList<Fragment> array = new ArrayList<>();
        array.add(getSupportFragmentManager().getFragment(restoredInstanceState, FragmentCommon.class.getName()));
        array.add(getSupportFragmentManager().getFragment(restoredInstanceState, FragmentAuxiliary.class.getName()));
        titleAdapter.setFrags(array);
        system = restoredInstanceState.getByte("system");
        st = restoredInstanceState.getInt("st");
        text = restoredInstanceState.getString("text");
        names = restoredInstanceState.getStringArray("names");
        systems = restoredInstanceState.getStringArray("systems");
        modes = restoredInstanceState.getStringArray("modes");
        languages = restoredInstanceState.getStringArray("languages");
        strings = restoredInstanceState.getStringArray("strings");
        sys.setText(systems[st - 2]);
        integ = new Parts(this, titleAdapter, handler, restoredInstanceState.getInt("prot"), restoredInstanceState.getString("dec"), restoredInstanceState.getString("save"),
                restoredInstanceState.getString("frac"), restoredInstanceState.getStringArray("meta"), restoredInstanceState.getInt("multi"), restoredInstanceState.getBoolean("mode"),
                restoredInstanceState.getBoolean("flagFrac"), restoredInstanceState.getBoolean("flagSaM"));
        registerForContextMenu(sys);
        enabled(st);
        system = drop[st-2];
        titleAdapter.setText(text);
        integ.setValue();
        enabledPoint(restoredInstanceState.getBoolean("flagFrac"));
        enabledSaM(true);
        if ((restoredInstanceState.getString("dec").equals("0")) && !(restoredInstanceState.getBoolean("flagFrac"))) {
            integ.protection(0);
            enabledSaM(false);
        }//end if
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        SubMenu subMenuSystems = menu.addSubMenu(names[0]);
        subMenuSystems.add(0, BIN, 2, systems[0]);
        subMenuSystems.add(0, TER, 3, systems[1]);
        subMenuSystems.add(0, QUA, 4, systems[2]);
        subMenuSystems.add(0, QUI, 5, systems[3]);
        subMenuSystems.add(0, SEN, 6, systems[4]);
        subMenuSystems.add(0, SEP, 7, systems[5]);
        subMenuSystems.add(0, OCT, 8, systems[6]);
        subMenuSystems.add(0, NON, 9, systems[7]);
        subMenuSystems.add(0, DEC, 10, systems[8]);
        subMenuSystems.add(0, UND, 11, systems[9]);
        subMenuSystems.add(0, DUO, 12, systems[10]);
        subMenuSystems.add(0, TRE, 13, systems[11]);
        subMenuSystems.add(0, TET, 14, systems[12]);
        subMenuSystems.add(0, PEN, 15, systems[13]);
        subMenuSystems.add(0, HEX, 16, systems[14]);
        SubMenu subMenuModes = menu.addSubMenu(names[1]);
        subMenuModes.add(1, STANDARD, 0, modes[0]);
        subMenuModes.add(1, ADVANCED, 1, modes[1]);
        SubMenu subMenuLanguages = menu.addSubMenu(names[2]);
        subMenuLanguages.add(2, BELARUSIAN, 0, languages[0]);
        subMenuLanguages.add(2, GERMAN, 1, languages[1]);
        subMenuLanguages.add(2, ENGLISH, 2, languages[2]);
        subMenuLanguages.add(2, ITALIAN, 3, languages[3]);
        subMenuLanguages.add(2, LITHUANIAN, 4, languages[4]);
        subMenuLanguages.add(2, LATVIAN, 5, languages[5]);
        subMenuLanguages.add(2, POLISH, 6, languages[6]);
        subMenuLanguages.add(2, RUSSIAN, 7, languages[7]);
        subMenuLanguages.add(2, UKRAINIAN, 8, languages[8]);
        menu.add(Menu.NONE, ABOUT, Menu.NONE, names[3]);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case STANDARD:
                item.setChecked(!item.isChecked());
                integ.setMode(false);
                integ.recalc();
                integ.setValue();
                break;
            case ADVANCED:
                item.setChecked(!item.isChecked());
                integ.setMode(true);
                integ.recalc();
                integ.setValue();
                break;
            case BIN:
            case TER:
            case QUA:
            case QUI:
            case SEN:
            case SEP:
            case OCT:
            case NON:
            case DEC:
            case UND:
            case DUO:
            case TRE:
            case TET:
            case PEN:
            case HEX:
                item.setChecked(!item.isChecked());
                setSystem(item.getItemId());
                integ.protection(0);
                enabledPoint(false);
                enabledSaM(false);
                text = "";
                titleAdapter.setText(text);
                break;
            case BELARUSIAN:
            case GERMAN:
            case ENGLISH:
            case ITALIAN:
            case LITHUANIAN:
            case LATVIAN:
            case POLISH:
            case RUSSIAN:
            case UKRAINIAN:
                editor = settings.edit();
                editor.putInt(APP_PREFERENCES_LANGUAGE, item.getItemId());
                editor.apply();
                setLanguage(new Locale(lang[item.getItemId()-200]));
                setLocale();
                break;
            case ABOUT:
                intent.putExtra("strings", strings);
                startActivity(intent);
                break;
            default:
                return false;
        }//end switch
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.textSystem:
                menu.add(0, BIN, 2, systems[0]);
                menu.add(0, TER, 3, systems[1]);
                menu.add(0, QUA, 4, systems[2]);
                menu.add(0, QUI, 5, systems[3]);
                menu.add(0, SEN, 6, systems[4]);
                menu.add(0, SEP, 7, systems[5]);
                menu.add(0, OCT, 8, systems[6]);
                menu.add(0, NON, 9, systems[7]);
                menu.add(0, DEC, 10, systems[8]);
                menu.add(0, UND, 11, systems[9]);
                menu.add(0, DUO, 12, systems[10]);
                menu.add(0, TRE, 13, systems[11]);
                menu.add(0, TET, 14, systems[12]);
                menu.add(0, PEN, 15, systems[13]);
                menu.add(0, HEX, 16, systems[14]);
                break;
         }//end switch
    }

    /*~ Меню ~*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case BIN:
            case TER:
            case QUA:
            case QUI:
            case SEN:
            case SEP:
            case OCT:
            case NON:
            case DEC:
            case UND:
            case DUO:
            case TRE:
            case TET:
            case PEN:
            case HEX:
                setSystem(item.getItemId());
                integ.protection(0);
                enabledPoint(false);
                enabledSaM(false);
                text = "";
                titleAdapter.setText(text);
                break;
        }//end switch
        return super.onContextItemSelected(item);
    }

    /*~ Обработка нажатий на кнопки ~*/
    @Override
    public void onClick(View v) {
        Integer var = 0;
        Character symb = null;
        switch (v.getId()) {
            case R.id.button0:
                var = 0;
                symb = '0';
                break;
            case R.id.button1:
                var = 1;
                symb = '1';
                break;
            case R.id.button2:
                var = 2;
                symb = '2';
                break;
            case R.id.button3:
                var = 3;
                symb = '3';
                break;
            case R.id.button4:
                var = 4;
                symb = '4';
                break;
            case R.id.button5:
                var = 5;
                symb = '5';
                break;
            case R.id.button6:
                var = 6;
                symb = '6';
                break;
            case R.id.button7:
                var = 7;
                symb = '7';
                break;
            case R.id.button8:
                var = 8;
                symb = '8';
                break;
            case R.id.button9:
                var = 9;
                symb = '9';
                break;
            case R.id.buttonA:
                var = 10;
                symb = 'A';
                break;
            case R.id.buttonB:
                var = 11;
                symb = 'B';
                break;
            case R.id.buttonC:
                var = 12;
                symb = 'C';
                break;
            case R.id.buttonD:
                var = 13;
                symb = 'D';
                break;
            case R.id.buttonE:
                var = 14;
                symb = 'E';
                break;
            case R.id.buttonF:
                var = 15;
                symb = 'F';
                break;
        }//end switch

        switch (v.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.buttonA:
            case R.id.buttonB:
            case R.id.buttonC:
            case R.id.buttonD:
            case R.id.buttonE:
            case R.id.buttonF:
                enabledSaM(true);
                if (integ.getProtect()) {
                    integ.protection(1);
                    text += symb;
                    if ((integ.getProt() % system) == 0)
                        text += ' ';
                    titleAdapter.setText(text);
                    integ.stepUp(var);
                    integ.setValue();
                }//end if
                break;
            case R.id.buttonBackspace:
                if (text.equals("") || text.length() != 0 || text.charAt(text.length()-1) != '.') //text != null
                    integ.stepDown();
                text = removeLastChar(text);
                if (text.equals("- "))
                    text = "";
                if (text.length() == 0)
                    enabledSaM(false);
                integ.setValue();
                integ.protection(-1);
                titleAdapter.setText(text);
                break;
            case R.id.buttonClear:
                text = "";
                titleAdapter.setText(text);
                integ.protection(0);
                enabledPoint(false);
                enabledSaM(false);
                break;
            case R.id.buttonPoint:
                if (integ.getProtect()) {
                    enabledPoint(true);
                    integ.setFrac(true);
                    if (text == null || text.length() == 0) {
                        text += "0.";
                        integ.recalc();
                        //integ.protection(1);
                    }//end if
                    else
                        text += '.';
                    integ.protection(1);
                    integ.setValue();
                    titleAdapter.setText(text);
                }//end if
                break;
            case R.id.buttonSaM:
                integ.setFlagSaM();
                if (text.charAt(0) == '-')
                    text = text.substring(2, text.length());
                else {
                    String str = text.substring(0, text.length());
                    text = "- " + str;
                }//end else
                titleAdapter.setText(text);
                integ.setSaM();
                integ.recalc();
                integ.setValue();
                break;
            default:
                break;
        }//end switch
    }

    /*~ Инициализация языка ~*/
    private void initializationLanguage() {
        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (settings.contains(APP_PREFERENCES_LANGUAGE))
            setLanguage(new Locale(lang[settings.getInt(APP_PREFERENCES_LANGUAGE, -1) - 200]));
    }

    /*~ Общая инициализация ~*/
    private void initialization() {
        if (titleAdapter == null)
            titleAdapter = new TitleAdapter(getSupportFragmentManager());
        if (integ == null)
            integ = new Parts(this, titleAdapter, handler);
        mViewPager.setAdapter(titleAdapter);
        mViewPager.setCurrentItem(0);
        sys.setText(systems[8]);
        registerForContextMenu(sys);
        enabled(DEC);
        enabledSaM(false);
    }

    /*~ Смена языка ~*/
    private void setLanguage(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void setLocale() {
        Context context = getBaseContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*~ Блокировка кнопок ~*/
    private void enabled (final Integer s) {
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
        buttonE.setEnabled(true);
        buttonF.setEnabled(true);
        button0.setTextColor(Color.WHITE);
        button1.setTextColor(Color.WHITE);
        button2.setTextColor(Color.WHITE);
        button3.setTextColor(Color.WHITE);
        button4.setTextColor(Color.WHITE);
        button5.setTextColor(Color.WHITE);
        button6.setTextColor(Color.WHITE);
        button7.setTextColor(Color.WHITE);
        button8.setTextColor(Color.WHITE);
        button9.setTextColor(Color.WHITE);
        buttonA.setTextColor(Color.WHITE);
        buttonB.setTextColor(Color.WHITE);
        buttonC.setTextColor(Color.WHITE);
        buttonD.setTextColor(Color.WHITE);
        buttonE.setTextColor(Color.WHITE);
        buttonF.setTextColor(Color.WHITE);
        switch (s) {
            case 2:
                button2.setEnabled(false);
                button2.setTextColor(Color.parseColor("#4E4E4E"));
            case 3:
                button3.setEnabled(false);
                button3.setTextColor(Color.parseColor("#4E4E4E"));
            case 4:
                button4.setEnabled(false);
                button4.setTextColor(Color.parseColor("#4E4E4E"));
            case 5:
                button5.setEnabled(false);
                button5.setTextColor(Color.parseColor("#4E4E4E"));
            case 6:
                button6.setEnabled(false);
                button6.setTextColor(Color.parseColor("#4E4E4E"));
            case 7:
                button7.setEnabled(false);
                button7.setTextColor(Color.parseColor("#4E4E4E"));
            case 8:
                button8.setEnabled(false);
                button8.setTextColor(Color.parseColor("#4E4E4E"));
            case 9:
                button9.setEnabled(false);
                button9.setTextColor(Color.parseColor("#4E4E4E"));
            case 10:
                buttonA.setEnabled(false);
                buttonA.setTextColor(Color.parseColor("#4E4E4E"));
            case 11:
                buttonB.setEnabled(false);
                buttonB.setTextColor(Color.parseColor("#4E4E4E"));
            case 12:
                buttonC.setEnabled(false);
                buttonC.setTextColor(Color.parseColor("#4E4E4E"));
            case 13:
                buttonD.setEnabled(false);
                buttonD.setTextColor(Color.parseColor("#4E4E4E"));
            case 14:
                buttonE.setEnabled(false);
                buttonE.setTextColor(Color.parseColor("#4E4E4E"));
            case 15:
                buttonF.setEnabled(false);
                buttonF.setTextColor(Color.parseColor("#4E4E4E"));
                break;
            default:
                break;
        }//end switch
    }

    private void enabledPoint (final Boolean flag) {
        if (!flag) {
            buttonPoint.setEnabled(true);
            buttonPoint.setTextColor(Color.WHITE);
        }//end if
        else {
            buttonPoint.setEnabled(false);
            buttonPoint.setTextColor(Color.parseColor("#4E4E4E"));
        }//end else
    }

    private void enabledSaM (final Boolean flag) {
        if (flag) {
            buttonSaM.setEnabled(true);
            buttonSaM.setTextColor(Color.WHITE);
        }//end if
        else {
            buttonSaM.setEnabled(false);
            buttonSaM.setTextColor(Color.parseColor("#4E4E4E"));
        }//end else
    }

    /*~ Установка системы счисления ~*/
    private void setSystem(final int s) {
        st = s;
        system = drop[s-2];
        integ.setMulti(s);
        sys.setText(systems[s-2]);
        enabled(s);
    }

    /*~ Удаление последнего символа строки ~*/
    private String removeLastChar(String s) {
        if (s == null || s.length() == 0)
            return s;
        if (s.charAt(s.length()-1) == '.') {
            buttonPoint.setEnabled(true);
            buttonPoint.setTextColor(Color.WHITE);
            integ.setFrac(false);
        }//end if
        if (s.charAt(s.length()-1) == ' ')
            return s.substring(0, s.length()-2);
        return s.substring(0, s.length()-1);
    }
}