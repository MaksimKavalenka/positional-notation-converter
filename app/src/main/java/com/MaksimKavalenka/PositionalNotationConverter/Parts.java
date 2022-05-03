package com.MaksimKavalenka.PositionalNotationConverter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.MaksimKavalenka.PositionalNotationConverter.Fragments.TitleAdapter;

import java.math.BigInteger;

public class Parts extends Activity implements Data {

    private TitleAdapter titleAdapter;
    private Thread thread;
    private Handler handler;

    private Toast message_bound;

    private ArrayAdapter<String> adapterS;
    private ArrayAdapter<String> adapterA;

    private String[] res = new String[4];
    private String[] result = new String[14];
    private String[] meta = new String[14];
    private String[] protection = null;

    private Integer prot;
    private BigInteger dec, save;
    private String fracS;
    private Integer multi;
    private Double frac;
    private Boolean mode, flagFrac, flagSaM, changeMode = false;

    public Parts(Context context, TitleAdapter titleAdapter, Handler handler) {
        this.titleAdapter = titleAdapter;
        this.handler = handler;
        adapterS = new ArrayAdapter<>(context, R.layout.design, res);
        adapterA = new ArrayAdapter<>(context, R.layout.design, result);
        prot = 0;
        dec = BigInteger.valueOf(0);
        save = BigInteger.valueOf(0);
        fracS = "0.";
        frac = 0.0;
        multi = DEC;
        mode = false;
        flagFrac = false;
        flagSaM = true;
        protection = context.getResources().getStringArray(R.array.protection);
        message_bound = Toast.makeText(context, protection[1] + ' ' + LIMIT + ' ' + protection[0], Toast.LENGTH_LONG);
    }

    public Parts(Context context, TitleAdapter titleAdapter, Handler handler, Integer prot, String dec, String save, String fracS, String[] meta, Integer multi, Boolean mode, Boolean flagFrac, Boolean flagSaM) {
        this.titleAdapter = titleAdapter;
        this.handler = handler;
        adapterS = new ArrayAdapter<>(context, R.layout.design, res);
        adapterA = new ArrayAdapter<>(context, R.layout.design, result);
        this.prot = prot;
        this.dec = new BigInteger(dec);
        this.save = new BigInteger(save);
        this.fracS = fracS;
        this.meta = meta;
        this.frac = Double.valueOf(fracS);
        this.multi = multi;
        this.mode = mode;
        this.flagFrac = flagFrac;
        this.flagSaM = flagSaM;
        protection = context.getResources().getStringArray(R.array.protection);
        message_bound = Toast.makeText(context, protection[1] + ' ' + LIMIT + ' ' + protection[0], Toast.LENGTH_LONG);
    }

    public void setMode(final Boolean mode) {
        this.mode = mode;
    }

    public void setFrac(final Boolean flagFrac) {
        this.flagFrac = flagFrac;
    }

    public void setFlagSaM() {
        flagSaM ^= true;
    }

    public void setMulti(final Integer multi) {
        this.multi = multi;
    }

    public void setSaM() {
        if (save.equals(BigInteger.valueOf(0))) {
            save = dec;
            switch (dec.intValue()) {
                case 0:
                    dec = BigInteger.valueOf(0);
                    break;
                case 1:
                    dec = BigInteger.valueOf(7);
                    break;
                case 2:
                    dec = BigInteger.valueOf(6);
                    break;
                case 3:
                    dec = BigInteger.valueOf(5);
                    break;
                default:
                    BigInteger z = getNegative(dec);
                    dec = (z.multiply(BigInteger.valueOf(2))).subtract(dec);
                    break;
            }//end switch
        }//end if
        else {
            dec = save;
            save = BigInteger.valueOf(0);
        }//end else
    }

    private BigInteger getNegative(final BigInteger a) {
        for (BigInteger[] neg : negative)
            if (a.compareTo(neg[29]) < 0)
                for (int i = 0; i < 30; i++)
                    if (a.compareTo(neg[i]) < 0)
                        return neg[i-1];
        return BigInteger.valueOf(0);
    }

    public void stepUp(final Integer var) {
        if (!flagFrac) {
            if (flagSaM) {
                dec = dec.multiply(BigInteger.valueOf(multi));
                dec = dec.add(BigInteger.valueOf(var));
            }//end if
            else {
                save = save.multiply(BigInteger.valueOf(multi));
                save = save.add(BigInteger.valueOf(var));
                BigInteger z = getNegative(save);
                dec = (z.multiply(BigInteger.valueOf(2))).subtract(save);
            }//end else
        }//end if
        else {
            fracS += String.valueOf(var);
            frac = Double.valueOf(fracS);
        }//end else
    }

    public void stepDown() {
        if (!flagFrac)
            if (flagSaM)
                dec = dec.divide(BigInteger.valueOf(multi));
            else {
                save = save.divide(BigInteger.valueOf(multi));
                if (save.compareTo(BigInteger.valueOf(1)) < 0) {
                    clear();
                    return;
                }//end if
                switch (save.intValue()) {
                    case 0:
                        dec = BigInteger.valueOf(0);
                        break;
                    case 1:
                        dec = BigInteger.valueOf(7);
                        break;
                    case 2:
                        dec = BigInteger.valueOf(6);
                        break;
                    case 3:
                        dec = BigInteger.valueOf(5);
                        break;
                    default:
                        BigInteger z = getNegative(save);
                        dec = (z.multiply(BigInteger.valueOf(2))).subtract(save);
                        break;
                }//end switch
            }//end else
        else {
            if (fracS.charAt(fracS.length()-1) != '.') {
                fracS = fracS.substring(0, fracS.length() - 1);
                frac = Double.valueOf(fracS);
            }//end if
        }//end else
    }

    /*~ Расчёт целой части числа ~*/
    public String calc(BigInteger x, final Integer s) {
        String text = "";
        BigInteger a;
        int count = 0;
        do {
            ++count;
            a = x.mod(BigInteger.valueOf(s));
            x = x.divide(BigInteger.valueOf(s));
            switch (a.intValue()) {
                case 0:
                    text += '0';
                    break;
                case 1:
                    text += '1';
                    break;
                case 2:
                    text += '2';
                    break;
                case 3:
                    text += '3';
                    break;
                case 4:
                    text += '4';
                    break;
                case 5:
                    text += '5';
                    break;
                case 6:
                    text += '6';
                    break;
                case 7:
                    text += '7';
                    break;
                case 8:
                    text += '8';
                    break;
                case 9:
                    text += '9';
                    break;
                case 10:
                    text += 'A';
                    break;
                case 11:
                    text += 'B';
                    break;
                case 12:
                    text += 'C';
                    break;
                case 13:
                    text += 'D';
                    break;
                case 14:
                    text += 'E';
                    break;
                case 15:
                    text += 'F';
                    break;
                default:
                    break;
            }//end switch
            if ((count % drop[s-2]) == 0)
                text += ' ';
        } while (x.compareTo(BigInteger.valueOf(0)) > 0);
        return new StringBuffer(text).reverse().toString();
    }

    /*~ Расчёт дробной части числа ~*/
    private String calcFrac(Double x, final Integer s) {
        String text = "";
        int a, count = 0;
        do {
            ++count;
            a = (int)(x * s);
            x = (x * s) % 1;
            switch (a) {
                case 0:
                    text += '0';
                    break;
                case 1:
                    text += '1';
                    break;
                case 2:
                    text += '2';
                    break;
                case 3:
                    text += '3';
                    break;
                case 4:
                    text += '4';
                    break;
                case 5:
                    text += '5';
                    break;
                case 6:
                    text += '6';
                    break;
                case 7:
                    text += '7';
                    break;
                case 8:
                    text += '8';
                    break;
                case 9:
                    text += '9';
                    break;
                case 10:
                    text += 'A';
                    break;
                case 11:
                    text += 'B';
                    break;
                case 12:
                    text += 'C';
                    break;
                case 13:
                    text += 'D';
                    break;
                case 14:
                    text += 'E';
                    break;
                case 15:
                    text += 'F';
                    break;
                default:
                    break;
            }//end switch
            if ((count % drop[s-2]) == 0)
                text += ' ';
        } while (((x % 1) != 0) && (count < 55));
        return text;
    }

    public void recalc() {
        if (flagFrac) {
            flagFrac = false;
            changeMode = true;
        }//end if
    }

    /*~ Установка значения ~*/
    public void setValue() {
        if (thread != null)
            thread.interrupt();
        thread = new Thread(runnable);
        thread.start();
    }

    /*~ Ограничение ввода ~*/
    public void protection(final Integer i) {
        if ((i == 1) && getProtect())
            ++prot;
        if ((i == -1) && (prot > 0))
            --prot;
        if (i == 0)
            clear();
    }

    public boolean getProtect() {
        if (prot == LIMIT) {
            message_bound.show();
            return false;
        }//end if
        else
            return true;
    }

    private void clear() {
        prot = 0;
        dec = BigInteger.valueOf(0);
        save = BigInteger.valueOf(0);
        fracS = "0.";
        frac = 0.0;
        titleAdapter.setVisibility(View.INVISIBLE);
        flagFrac = false;
        flagSaM = true;
    }

    public Integer getProt() {
        return prot;
    }

    public String getDec() {
        return dec.toString();
    }

    public String getSave() {
        return save.toString();
    }

    public String getFracS() {
        return fracS;
    }

    public String[] getMeta() {
        return meta;
    }

    public Integer getMulti() {
        return multi;
    }

    public Boolean getMode() {
        return mode;
    }

    public Boolean getFlagFrac() {
        return flagFrac;
    }

    public Boolean getFlagSaM() {
        return flagSaM;
    }


    Runnable runnable = new Runnable() {
        public void run() {
            if (!mode) {
                int[] a = {2, 8, 10, 16};
                int pos = 0;
                for (int i : a) {
                    if (i == multi) {
                        res[3] = "";
                        continue;
                    }//end if
                    if (!flagFrac) {
                        res[pos++] = iso[i - 2] + calc(dec, i);
                        System.arraycopy(res, 0, meta, 0, res.length);
                    }//end if
                    else
                        res[pos] = meta[pos++] + '.' + calcFrac(frac, i);
                }//end for
            }//end if
            else {
                int[] a = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
                int save = 2;
                for (int i : a) {
                    if (i == multi) {
                        save = 3;
                        continue;
                    }//end if
                    if (!flagFrac) {
                        result[i - save] = iso[i - 2] + calc(dec, i);
                        System.arraycopy(result, 0, meta, 0, result.length);
                    }//end if
                    else
                        result[i - save] = meta[i - save] + "." + calcFrac(frac, i);
                }//end for
            }//end else
            handler.sendMessage(handler.obtainMessage());
        }
    };

    public void setAdapter() {
        if (!mode)
            titleAdapter.setAdapter(adapterS);
        else
            titleAdapter.setAdapter(adapterA);
        if (changeMode) {
            changeMode = false;
            flagFrac = true;
            setValue();
        }//end if
        titleAdapter.setVisibility(View.VISIBLE);
        if (prot == 0)
            clear();
    }
}