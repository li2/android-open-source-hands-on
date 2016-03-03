/* Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package me.li2.customview;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.li2.customview.charting.PieChart;


public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();

        setContentView(R.layout.activity_main);
        final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
        pie.addItem("Agamemnon", 2, res.getColor(R.color.seafoam));
        pie.addItem("Bocephus", 3.5f, res.getColor(R.color.chartreuse));
        pie.addItem("Calliope", 2.5f, res.getColor(R.color.emerald));
        pie.addItem("Daedalus", 3, res.getColor(R.color.bluegrass));
        pie.addItem("Euripides", 1, res.getColor(R.color.turquoise));
        pie.addItem("Ganymede", 3, res.getColor(R.color.slate));

        ((Button) findViewById(R.id.Reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pie.setCurrentItem(0);
            }
        });
    }
}

