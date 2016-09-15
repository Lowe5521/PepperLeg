package com.jonkoester.pepperlegexample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonkoester.pepperleg.PepperLegDataModel;

public class FragmentMid extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        ((ExampleActivity) getActivity()).pepperQueue.add(new PepperLegDataModel(view.findViewById(R.id.first_name_tv), "First Name", "Is this thing on?", PepperLegDataModel.Direction.RIGHT, null, PepperLegDataModel.YAlignment.TOP));
        ((ExampleActivity) getActivity()).pepperQueue.add(new PepperLegDataModel(view.findViewById(R.id.button2), "Button Two", "Button 2 is actually the third button... weird", PepperLegDataModel.Direction.BOTTOM, PepperLegDataModel.XAlignment.LEFT, null));
        ((ExampleActivity) getActivity()).pepperQueue.add(new PepperLegDataModel(view.findViewById(R.id.tab_five_tv), "Table Middle!", "Dead center, baby!", PepperLegDataModel.Direction.TOP, PepperLegDataModel.XAlignment.CENTER, null));

        return view;
    }
}
