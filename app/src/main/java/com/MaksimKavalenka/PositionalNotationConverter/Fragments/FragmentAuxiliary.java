package com.MaksimKavalenka.PositionalNotationConverter.Fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MaksimKavalenka.PositionalNotationConverter.R;

public class FragmentAuxiliary extends Communicate {

    private TextView num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.auxiliary, container, false);
        num = (TextView) v.findViewById(R.id.textNumberAuxiliary);
        num.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }

    @Override
    public void setText(String str) {
        num.setText(str);
    }
}