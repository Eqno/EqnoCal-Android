package cn.edu.ujn.eqnoxx.eqnocal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScientificActivity extends KeyboardController {
    // on_create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        init();
    }
    private void init() {
        scientificMode = true;
        screen = (TextView) findViewById(R.id.screenScientific);
        screen.setText(screenText);
    }

    // click_to_switch
    public void switchMode(View view) {
        screenText = screen.getText().toString();
        ScientificActivity.this.finish();
    }
}
