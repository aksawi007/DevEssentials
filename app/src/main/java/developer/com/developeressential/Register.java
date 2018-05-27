package developer.com.developeressential;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Register extends Activity {
    ImageView imageView;
    private EditText per_name, per_age;
    private static final int CAMERA_REQUEST = 1;
    Intent intent;
    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = getIntent();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) intent.getExtras().get("data");
            imageView = (ImageView) this.findViewById(R.id.imageView_reg);
            imageView.setImageBitmap(photo);
            intent.putExtra("personPics", photo);
            //setContentView(R.layout.activity_register);
        }
    }
    public void openRegCamera(View view) {
        String c = intent.getComponent().getPackageName();
        setContentView(R.layout.activity_register);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void submit(View view) {
        Intent intent = new Intent(Register.this, Summary.class);
        per_name = (EditText) findViewById(R.id.per_name_reg);
        per_age = (EditText) findViewById(R.id.per_age_reg);
        intent.putExtra("personName", per_name.getText());
        intent.putExtra("personAge", per_age.getText());
        intent.putExtra("personPics", photo);
        startActivity(intent);
    }
}
