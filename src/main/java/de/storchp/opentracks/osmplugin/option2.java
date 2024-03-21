package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.widget.TextView;


public class option2 extends MapsActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option2);

        // Find the TextView in the layout
        TextView textViewHelloWorld = findViewById(R.id.option2);

        // Set the text to "Hello World!"
        textViewHelloWorld.setText("Hello World!");
    }

}
