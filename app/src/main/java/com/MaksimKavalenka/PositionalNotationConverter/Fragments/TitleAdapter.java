package com.MaksimKavalenka.PositionalNotationConverter.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class TitleAdapter extends FragmentPagerAdapter {

    private final Communicate frags[] = new Communicate[2];

    public TitleAdapter(FragmentManager fm) {
        super(fm);
        frags[0] = new FragmentCommon();
        frags[1] = new FragmentAuxiliary();
    }

    @Override
    public Communicate getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }

    public void setFrags(ArrayList<Fragment> array) {
        frags[0] = (Communicate) array.get(0);
        frags[1] = (Communicate) array.get(1);
    }

    public void setText(String str) {
        frags[0].setText(str);
        frags[1].setText(str);
    }

    public void setVisibility(int i) {
        frags[0].setVisibility(i);
    }

    public void setAdapter(ArrayAdapter<String> array) {
        frags[0].setAdapter(array);
    }
}