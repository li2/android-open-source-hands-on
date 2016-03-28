package me.li2.android.androidtextsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button mSpannableApiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpannableApiBtn = (Button) findViewById(R.id.mainSpannableApiBtn);
        mSpannableApiBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mainSpannableApiBtn:
                startActivity(new Intent(MainActivity.this, SpannableStringApiActivity.class));
                break;
        }
    }
}
