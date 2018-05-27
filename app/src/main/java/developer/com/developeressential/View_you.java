package developer.com.developeressential;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.common.util.IOUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View_you extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_you);
        final ArrayList<String> list = new ArrayList<String>();
        Connection connection = getConnection("admin", "devadmin", "devEssentialsDB", "devessentialsdb.cfohl81ibqp7.us-east-1.rds.amazonaws.com");
        String query = "SELECT personName AS personName FROM TestDB3;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString("personName"));
            }
        } catch (SQLException e) {

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

        final ListView listview = (ListView) findViewById(R.id.listview);

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id)  {
                final String item = (String) parent.getItemAtPosition(position);
                Connection connection = getConnection("admin", "devadmin", "devEssentialsDB", "devessentialsdb.cfohl81ibqp7.us-east-1.rds.amazonaws.com");
                String query = "SELECT PersonAge AS PersonAge, personName AS personName, PersonPic AS PersonPic FROM TestDB3 WHERE personName = '" + item + "';";
                try {
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    int age=0;
                    String name="";
                    byte[] image=null;
                    while (rs.next()) {
                        age = rs.getInt("PersonAge");
                        name = rs.getString("personName");
                        image = rs.getBytes("PersonPic");
                    }
                    Bitmap bmp = null;
                    if(image.length!=0) {
                        bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    }
                    Intent intent = new Intent(View_you.this, Details.class);
                    intent.putExtra("personName", name);
                    intent.putExtra("personAge", age);
                    intent.putExtra("personPics", bmp);
                    startActivity(intent);
                } catch (SQLException e) {

                }

            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


    public Connection getConnection(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
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


