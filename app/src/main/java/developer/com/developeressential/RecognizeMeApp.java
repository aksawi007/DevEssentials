package developer.com.developeressential;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class RecognizeMeApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize_me_app);
    }

    public void register(View view) {
        Intent intent = new Intent(RecognizeMeApp.this, Register.class);
        startActivity(intent);
    }

    public void view_u(View view) {
        Intent intent = new Intent(RecognizeMeApp.this, View_you.class);
        startActivity(intent);
    }
}
