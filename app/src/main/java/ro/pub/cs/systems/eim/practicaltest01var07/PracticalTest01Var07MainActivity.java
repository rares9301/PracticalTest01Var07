package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var07MainActivity extends Activity {

    private EditText[] fields;
    private final Random random = new Random();

    // C.2
    private boolean hasResult = false;
    private double result = 0.0;

    // D.2
    private final BroadcastReceiver randomValuesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Constants.ACTION_RANDOM_VALUES.equals(intent.getAction())) {
                return;
            }
            int[] values = intent.getIntArrayExtra(Constants.EXTRA_RANDOM_INTS);
            if (values == null || values.length != fields.length) {
                return;
            }
            for (int i = 0; i < fields.length; i++) {
                fields[i].setText(String.valueOf(values[i]));
            }
            Log.d(Constants.TAG, "Broadcast received, fields overwritten: "
                    + values[0] + " " + values[1] + " " + values[2] + " " + values[3]);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_main);

        fields = new EditText[]{
                findViewById(R.id.field1_edit_text),
                findViewById(R.id.field2_edit_text),
                findViewById(R.id.field3_edit_text),
                findViewById(R.id.field4_edit_text)
        };

        Button randomButton = findViewById(R.id.random_button);
        randomButton.setOnClickListener(v -> fillNonNumericFields());

        Button setButton = findViewById(R.id.set_button);
        setButton.setOnClickListener(v -> openSecondaryActivity());

        // C.2
        if (savedInstanceState != null) {
            restoreResult(savedInstanceState);
        }

        // D.1
        startService(new Intent(this, PracticalTest01Var07Service.class));
    }

    // B.1:
    private void fillNonNumericFields() {
        for (EditText field : fields) {
            if (parseNumber(field.getText().toString()) == null) {
                field.setText(String.valueOf((double) random.nextInt(100)));
            }
        }
    }

    private static Double parseNumber(String text) {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            double value = Double.parseDouble(trimmed);
            return (Double.isNaN(value) || Double.isInfinite(value)) ? null : value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // B.2:
    private void openSecondaryActivity() {
        double[] values = new double[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Double value = parseNumber(fields[i].getText().toString());
            if (value == null) {
                Toast.makeText(this, R.string.fields_not_numeric, Toast.LENGTH_SHORT).show();
                return;
            }
            values[i] = value;
        }
        Intent intent = new Intent(this, PracticalTest01Var07SecondaryActivity.class);
        intent.putExtra(Constants.EXTRA_VALUES, values);
        startActivityForResult(intent, Constants.SECONDARY_REQUEST_CODE);
    }

    // C.2 a)Logcat
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constants.SECONDARY_REQUEST_CODE || resultCode != RESULT_OK || data == null
                || !data.hasExtra(Constants.EXTRA_RESULT)) {
            return;
        }
        result = data.getDoubleExtra(Constants.EXTRA_RESULT, 0.0);
        hasResult = true;
        String operation = data.getStringExtra(Constants.EXTRA_OPERATION);
        String message = "Result (" + operation + ") = " + result;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.d(Constants.TAG, "Received " + message);
    }

    // C.2 b)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.STATE_HAS_RESULT, hasResult);
        outState.putDouble(Constants.STATE_RESULT, result);
        Log.d(Constants.TAG, "onSaveInstanceState: hasResult=" + hasResult + " result=" + result);
    }

    private void restoreResult(Bundle state) {
        hasResult = state.getBoolean(Constants.STATE_HAS_RESULT, false);
        result = state.getDouble(Constants.STATE_RESULT, 0.0);
        Log.d(Constants.TAG, "Restored state: hasResult=" + hasResult + " result=" + result);
        if (hasResult) {
            Toast.makeText(this, "Restored result = " + result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.ACTION_RANDOM_VALUES);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(randomValuesReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(randomValuesReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(randomValuesReceiver);
        super.onPause();
    }

    // D.1 b)
    @Override
    protected void onDestroy() {
        Log.d(Constants.TAG, "Main activity onDestroy -> stopping service");
        stopService(new Intent(this, PracticalTest01Var07Service.class));
        super.onDestroy();
    }
}
