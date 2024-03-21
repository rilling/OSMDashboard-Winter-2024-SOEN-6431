package de.storchp.opentracks.osmplugin;

import android.os.Bundle;
import android.widget.TextView;

public class option1 extends MapsActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option1);

        // Find the TextView in the layout
        TextView textViewHelloWorld = findViewById(R.id.option1);

        // Set the text to "Hello World!"
        textViewHelloWorld.setText("Hello World!");
}

}
