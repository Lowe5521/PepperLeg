package com.jonkoester.pepperlegexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.jonkoester.pepperleg.PepperLegDataModel;
import com.jonkoester.pepperleg.PepperLegOverlay;

import java.util.LinkedList;
import java.util.Queue;

public class ExampleActivity extends AppCompatActivity {

    public Queue<PepperLegDataModel> pepperQueue = new LinkedList<>();
    public PepperLegOverlay pepperLegOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        getFragmentManager().beginTransaction()
                .add(R.id.act_example_container1, new FragmentTop(), FragmentTop.class.getSimpleName())
                .add(R.id.act_example_container2, new FragmentMid(), FragmentMid.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pepperLegOverlay == null) {
            pepperLegOverlay = new PepperLegOverlay(this, pepperQueue);
            addContentView(pepperLegOverlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
}
