package com.example.depremuygulamasi;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class DepremEtkinligi extends Activity {

    private ArrayList<Deprem> depremListesi;
    private DepremAdaptoru adaptorum;
    private SearchView aramaKutusu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deprem);

        aramaKutusu = findViewById(R.id.aramaKutusu);
        Button geriDon = findViewById(R.id.geriDonButonu);
        Button btnBuyukten = findViewById(R.id.btnBuyukten);
        Button btnKucukten = findViewById(R.id.btnKucukten);

        geriDon.setOnClickListener(view -> finish());

        depremListesi = new ArrayList<>();
        ListView liste = findViewById(R.id.liste);
        adaptorum = new DepremAdaptoru(this, depremListesi);
        liste.setAdapter(adaptorum);

        btnBuyukten.setOnClickListener(v -> {
            Collections.sort(depremListesi, (d1, d2) -> Double.compare(Double.parseDouble(d2.getSiddet()), Double.parseDouble(d1.getSiddet())));
            adaptorum.updateData(depremListesi);
        });

        btnKucukten.setOnClickListener(v -> {
            Collections.sort(depremListesi, (d1, d2) -> Double.compare(Double.parseDouble(d1.getSiddet()), Double.parseDouble(d2.getSiddet())));
            adaptorum.updateData(depremListesi);
        });

        aramaKutusu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptorum.getFilter().filter(newText);
                return false;
            }
        });

        depremVerileriniCek();
    }

    private void depremVerileriniCek() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&limit=300&minmag=1";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> depremVerisiniIsle(response),
                error -> Toast.makeText(DepremEtkinligi.this, "Veri çekilemedi: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(request);
    }

    private void depremVerisiniIsle(JSONObject json) {
        try {
            JSONArray features = json.getJSONArray("features");
            depremListesi.clear();

            for (int i = 0; i < features.length(); i++) {
                JSONObject deprem = features.getJSONObject(i);
                JSONObject properties = deprem.getJSONObject("properties");

                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");

                Date date = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("tr"));
                String dateStr = sdf.format(date);

                depremListesi.add(new Deprem(String.valueOf(mag), place, dateStr, time));
            }

            adaptorum.updateData(depremListesi);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Veri işlenirken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    public static String hesaplaKacOnce(long zamanMillis) {
        long simdi = System.currentTimeMillis();
        long fark = simdi - zamanMillis;

        long saniye = fark / 1000;
        long dakika = saniye / 60;
        long saat = dakika / 60;
        long gun = saat / 24;

        if (gun > 0) return gun + " gün önce";
        else if (saat > 0) return saat + " saat önce";
        else if (dakika > 0) return dakika + " dakika önce";
        else return "şimdi";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
