package cn.edu.ujn.eqnoxx.eqnocal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends KeyboardController {
    // on_create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        start = true;
    }
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        scientificMode = false;
        screen = findViewById(R.id.screenNormal);
        screen.setText(screenText);
    }

    // click_to_switch
    public void switchMode(View view) {
        switchActivity();
    }
    private void switchActivity() {
        screenText = screen.getText().toString();
        startActivity(new Intent(this, ScientificActivity.class));
    }
}