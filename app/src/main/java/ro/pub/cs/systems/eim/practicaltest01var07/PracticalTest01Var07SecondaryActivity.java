package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var07SecondaryActivity extends Activity {

    private double[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_secondary);

        Intent intent = getIntent();
        values = intent == null ? null : intent.getDoubleArrayExtra(Constants.EXTRA_VALUES);
        if (values == null || values.length != 4) {
            // B.2:
            setResult(RESULT_CANCELED);
            finish();
            return;
        }

        int[] ids = {R.id.value1_text_view, R.id.value2_text_view, R.id.value3_text_view, R.id.value4_text_view};
        for (int i = 0; i < ids.length; i++) {
            ((TextView) findViewById(ids[i])).setText(String.valueOf(values[i]));
        }

        findViewById(R.id.sum_button).setOnClickListener(v -> {
            double sum = 0;
            for (double value : values) {
                sum += value;
            }
            returnResult("sum", sum);
        });

        findViewById(R.id.product_button).setOnClickListener(v -> {
            double product = 1;
            for (double value : values) {
                product *= value;
            }
            returnResult("product", product);
        });
    }

    // C.1:
    private void returnResult(String operation, double result) {
        Toast.makeText(this, operation + " = " + result, Toast.LENGTH_SHORT).show();
        Intent data = new Intent();
        data.putExtra(Constants.EXTRA_RESULT, result);
        data.putExtra(Constants.EXTRA_OPERATION, operation);
        setResult(RESULT_OK, data);
        finish();
    }
}
