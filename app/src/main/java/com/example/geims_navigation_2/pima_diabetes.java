package com.example.geims_navigation_2;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class pima_diabetes extends AppCompatActivity {

    private Interpreter tflite;
    private EditText[] inputs;
    private TextView tvResult;

    // means & stds from your Python StandardScaler
    private static final float[] MEANS = {
            3.8f, 120.9f, 69.1f, 20.5f,
            79.8f, 32.0f, 0.47f, 33.2f
    };
    private static final float[] STDS = {
            3.4f, 31.9f, 19.4f, 15.9f,
            115.2f, 7.9f, 0.33f, 11.8f
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pima_diabetes);

        // 1) Load TFLite model
        try {
            tflite = new Interpreter(loadModelFile("pima_diabetes.tflite"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2) Hook up views
        inputs = new EditText[] {
                findViewById(R.id.etPregnancies),
                findViewById(R.id.etGlucose),
                findViewById(R.id.etBloodPressure),
                findViewById(R.id.etSkinThickness),
                findViewById(R.id.etInsulin),
                findViewById(R.id.etBMI),
                findViewById(R.id.etDiabetesPedigree),
                findViewById(R.id.etAge)
        };
        Button btn = findViewById(R.id.btnPredict);
        tvResult = findViewById(R.id.tvResult);

        // 3) On‑click: gather inputs, run model, display result
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1) Read raw inputs
                float[] inputVals = new float[8];
                for (int i = 0; i < 8; i++) {
                    String s = inputs[i].getText().toString().trim();
                    inputVals[i] = s.isEmpty() ? 0f : Float.parseFloat(s);
                }

                // 2) Normalize exactly as in Python
                for (int i = 0; i < 8; i++) {
                    inputVals[i] = (inputVals[i] - MEANS[i]) / STDS[i];
                }

                // 3) Prepare 2D input
                float[][] modelInput = new float[1][8];
                modelInput[0] = inputVals;

                // 4) Run inference
                float[][] output = new float[1][1];
                tflite.run(modelInput, output);

                // 5) Display
                float prob = output[0][0];
                String result = prob > 0.5
                        ? "Positive (Risk = " + String.format("%.2f", prob) + ")"
                        : "Negative (Risk = " + String.format("%.2f", prob) + ")";
                tvResult.setText(result);
            }
        });

    }

    // Helper to load model from assets
    private MappedByteBuffer loadModelFile(String modelFilename) throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd(modelFilename);
        FileInputStream fis = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fis.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }

}