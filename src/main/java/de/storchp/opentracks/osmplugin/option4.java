package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.widget.TextView;

public class option4 extends MapsActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option4);

        // Find the TextView in the layout
        TextView textViewHelloWorld = findViewById(R.id.option4);

        // Set the text to "Hello World!"
        textViewHelloWorld.setText("Hello World!");
    }

}
