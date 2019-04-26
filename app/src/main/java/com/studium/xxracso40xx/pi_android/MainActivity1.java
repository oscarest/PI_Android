package com.studium.xxracso40xx.pi_android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.studium.xxracso40xx.pi_android.model.Usuarios;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {

    private String APIserver = "http://8music.ddns.net/modelo/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void clicBuscar(View view) {
        EditText txtIdProducto = findViewById(R.id.txtIdProducto);
        if (txtIdProducto.length() > 0) {
            Long id = Long.parseLong(txtIdProducto.getText().toString());
            new DB_Apache().execute("get-product.php?id=" + id);
        } else {
            new DB_Apache().execute("get-list-products.php");
        }
    }

    private class DB_Apache extends AsyncTask<String, Void, Boolean> {

        private String json;

        protected Boolean doInBackground(String... urls) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(APIserver + urls[0]));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean isOk) {
            try {
                if (isOk) {
                    JSONArray response = new JSONArray(json);

                    List<Usuarios> lst = new ArrayList<Usuarios>();
                    /*for (int i = 0; i < response.length(); i++) {
                        lst.add(new Usuarios(
                                response.getJSONObject(i).getLong("idUsuario")
                                , response.getJSONObject(i).getString("claveUsuario")
                                , response.getJSONObject(i).getInt("tipoUsuario")
                                , response.getJSONObject(i).getLong("algunaSuscripcionUsuario")
                        ));
                    }
                    */


                    StringBuilder result = new StringBuilder();
                    for (Usuarios p : lst) {
                        result.append(p.toString() + "\n");
                    }

                    TextView txtResult = findViewById(R.id.txtResult);
                    txtResult.setText(result);
                }

            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
            }
        }

    }
}