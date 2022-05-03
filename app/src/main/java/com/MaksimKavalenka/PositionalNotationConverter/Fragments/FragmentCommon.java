package com.MaksimKavalenka.PositionalNotationConverter.Fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.MaksimKavalenka.PositionalNotationConverter.R;

public class FragmentCommon extends Communicate {

    private TextView num;
    private ListView listSys;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.common, container, false);
        num = (TextView) v.findViewById(R.id.textNumberCommon);
        listSys = (ListView) v.findViewById(R.id.listSystems);
        num.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }

    @Override
    public void setText(String str) {
        num.setText(str);
    }

    @Override
    public void setVisibility(int i) {
        listSys.setVisibility(i);
    }

    @Override
    public void setAdapter(ArrayAdapter<String> array) {
        listSys.setAdapter(array);
    }
}