package com.example.depremuygulamasi;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ListView;
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
import java.util.Date;
import java.util.Locale;

public class DepremEtkinligi extends Activity {

    private ArrayList<Deprem> depremListesi;
    private DepremAdaptoru adaptorum;
    SearchView aramaKutusu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deprem);

        aramaKutusu = findViewById(R.id.aramaKutusu);
        Button geriDon = findViewById(R.id.geriDonButonu);
        geriDon.setOnClickListener(view -> finish());

        depremListesi = new ArrayList<>();
        ListView liste = findViewById(R.id.liste);
        adaptorum = new DepremAdaptoru(this, depremListesi);
        liste.setAdapter(adaptorum);

        aramaKutusu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (adaptorum != null) {
                    adaptorum.getFilter().filter(newText);
                }
                return false;
            }
        });

        // VERİ ÇEKMEYİ BAŞLAT
        depremVerileriniCek();
    }

    private void depremVerileriniCek() {
        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?" +
                "format=geojson&orderby=time&limit=300&minmag=1";

        RequestQueue istekSirasi = Volley.newRequestQueue(this);

        JsonObjectRequest jsonIstek = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject cevap) {
                        depremVerisiniIsle(cevap);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError hata) {
                        Toast.makeText(DepremEtkinligi.this,
                                "Veri çekilemedi: " + hata.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );

        istekSirasi.add(jsonIstek);
    }

    private void depremVerisiniIsle(JSONObject json) {
        try {
            JSONArray depremler = json.getJSONArray("features");

            depremListesi.clear();

            for (int i = 0; i < depremler.length(); i++) {
                JSONObject deprem = depremler.getJSONObject(i);
                JSONObject ozellikler = deprem.getJSONObject("properties");

                double siddet = ozellikler.getDouble("mag");
                String konum = ozellikler.getString("place");
                long zaman = ozellikler.getLong("time");

                Date tarih = new Date(zaman);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("tr"));
                String tarihStr = sdf.format(tarih);

                // Burada yeni "zaman" parametresini de Deprem nesnesine ekleyeceğiz (Deprem sınıfını da buna göre güncelle)
                depremListesi.add(new Deprem(String.valueOf(siddet), konum, tarihStr, zaman));
            }

            adaptorum.updateData(depremListesi);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Veri işlenirken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    // Yeni metod: zaman farkını hesaplayıp Türkçe string döndürüyor
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
