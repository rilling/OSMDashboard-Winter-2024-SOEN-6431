package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.widget.TextView;

public class option3 extends MapsActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option3);

        // Find the TextView in the layout
        TextView textViewHelloWorld = findViewById(R.id.option3);

        // Set the text to "Hello World!"
        textViewHelloWorld.setText("Hello World!");
    }

}
