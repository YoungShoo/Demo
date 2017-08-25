package com.shoo.demo.event.touch;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shoo.demo.R;

/**
 * Created by Shoo on 17-8-25.
 */

public class TOverlapTouchEvent {

    public static void test(Activity activity) {
        /*
        // from top to bottom
        ViewGroup.dispatchTouchEvent() {
            ...
            for (int i = childrenCount - 1; i >= 0; i--)
            ...
        }
         */

        // Result: Button Above Clicked
        activity.setContentView(R.layout.overlap_view);
        Button btnBelow = (Button) activity.findViewById(R.id.btn_below);
        Button btnAbove = (Button) activity.findViewById(R.id.btn_above);
        btnBelow.setOnClickListener(createOnclickListener());
        btnAbove.setOnClickListener(createOnclickListener());
    }

    public static View.OnClickListener createOnclickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.btn_below:
                        Toast.makeText(v.getContext(), "Button Below Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_above:
                        Toast.makeText(v.getContext(), "Button Above Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }
}
