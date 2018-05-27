package developer.com.developeressential;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Summary extends Activity {
    ImageView imageView;
    String perName;
    String perAge;
    Bitmap photo;
    Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();
        TextView perName_view = (TextView) findViewById(R.id.per_name);
        TextView perAge_view = (TextView) findViewById(R.id.per_age);
        imageView = (ImageView) this.findViewById(R.id.per_img);

        perName = intent.getExtras().get("personName").toString();
        perAge = intent.getExtras().get("personAge").toString();
        photo = (Bitmap) intent.getExtras().get("personPics");

        perName_view.setText(perName);
        perAge_view.setText(perAge);
        imageView.setImageBitmap(photo);

       /* Bitmap photo1 = (Bitmap) intent.getExtras().get("data");
        imageView = (ImageView) this.findViewById(R.id.imageView);*/
    }

    public void upload(View view) {
        Intent intent = new Intent(Summary.this, UploadToDB.class);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        intent.putExtra("personName", perName);
        intent.putExtra("personAge", perAge);
        intent.putExtra("personPics", photo);
        connection = connectionclass("admin", "devadmin", "devEssentialsDB", "devessentialsdb.cfohl81ibqp7.us-east-1.rds.amazonaws.com");
        PreparedStatement statement = null;
        String sql = "INSERT INTO TestDB3 (PersonAge, PersonName, PersonPic) VALUES (?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(perAge));
            statement.setString(2, perName);
            statement.setBinaryStream(3, new ByteArrayInputStream( bArray),bArray.length);
            int m =  connection.createStatement().executeUpdate("INSERT INTO TestDB3 (PersonAge, PersonName, PersonPic) VALUES("+Integer.parseInt(perAge)+" ,'"+perName+"' ,null )");
           //int m1 =  connection.createStatement().executeUpdate("INSERT INTO TestDB3 (PersonAge, PersonName, PersonPic) VALUES("+Integer.parseInt(perAge)+" ,'"+perName+"' ,"+bArray.toString()+" )");
            int n = statement.executeUpdate();
        } catch (SQLException e) {
            String error = "error";
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }


        startActivity(intent);
    }

    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String ConnectionURL = null;
        try {
            String className = "com.mysql.jdbc.Driver";
            className = className;
            Class.forName(className);
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + database + ";user=" + user + ";password=" + password + ";";
            String jdbcUrl = "jdbc:mysql://" + server + ":" +
                    3306 + "/" + database + "?user=" + user + "&password=" + password;
            jdbcUrl = jdbcUrl;
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}

