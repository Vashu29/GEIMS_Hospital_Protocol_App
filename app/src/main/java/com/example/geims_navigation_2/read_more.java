package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class read_more extends AppCompatActivity {
    ImageView im1;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);

        im1 = findViewById(R.id.im1);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);

        im1.setImageResource(R.drawable.ghanshalasir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(read_more.this, Login_2.class);
                startActivity(i);
            }
        });
        /*
        tv.setText("President\n" +
                "Prof. (Dr.) Kamal Ghanshala\n" +
                "Ph.D. Computer Science\n" +
                "Founder – Graphic Era Group\n\n\n“A Visionary who dreamt and realized his dreams”\n\n" +
                "Message from the Desk of President, Prof. (Dr) Kamal Ghanshala\n\n" +
                "Today colleges and universities are facing changing student demographics, issues of quality and value, and increased competition. Their success requires transformative change to enable new teaching and learning approaches. As higher education is rapidly evolving, Graphic Era is at the cutting edge of the movement. We are competing boldly and taking new paths—yet staying grounded in our mission of delivering quality education, building teaching excellence, introducing innovative study programs, customizing and updating our course content to match the industry requirements, setting records in placement, research, and consultancy.\n" +
                "\n" +
                "A creative and inventive culture, nurtured by our faculty, students, and staff, is transforming how we train our students and help them to strengthen their future prospects. In developing Graphic Era University, we have partnered with leaders in the corporate sector and research organizations of the country and international universities, for training and placements of our students. We are leveraging these creative partnerships to stretch our boundaries of excellence collaboration, innovation, and the creation of skills, across numerous disciplinary fronts. The deep integration of technology in learning has made learning mobile and self-directed. The ultimate goal of learning is critical thinking, generating work skills, communication, creativity, collaboration, character, education, and citizenship, and technology is assisting in a big way in accelerating these ideals and making learning irresistibly engaging. With recognition from prestigious institutions, Graphic Era University is setting new benchmarks in education.");
*/
    }
}