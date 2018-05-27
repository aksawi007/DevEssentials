package developer.com.developeressential;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        TextView perName_view = (TextView) findViewById(R.id.detail_name);
        TextView perAge_view = (TextView) findViewById(R.id.detail_age);
        imageView = (ImageView) this.findViewById(R.id.detail_img);

        String perName = intent.getExtras().get("personName").toString();
        String perAge = intent.getExtras().get("personAge").toString();
        Bitmap photo = (Bitmap) intent.getExtras().get("personPics");

        perName_view.setText(perName);
        perAge_view.setText(perAge);
        imageView.setImageBitmap(photo);
    }

}
