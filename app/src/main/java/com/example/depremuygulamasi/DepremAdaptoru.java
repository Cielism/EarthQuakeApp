package com.example.depremuygulamasi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DepremAdaptoru extends ArrayAdapter<Deprem> implements Filterable {
    private List<Deprem> tumDepremler;
    private List<Deprem> gosterilenDepremler;

    public DepremAdaptoru(Context context, List<Deprem> depremler) {
        super(context, 0, depremler);
        this.tumDepremler = new ArrayList<>(depremler); // tüm depremler
        this.gosterilenDepremler = new ArrayList<>(depremler); // filtrelenen liste
    }


    public void updateData(List<Deprem> yeniListe) {
        tumDepremler.clear();
        tumDepremler.addAll(yeniListe);

        gosterilenDepremler.clear();
        gosterilenDepremler.addAll(yeniListe);

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return gosterilenDepremler.size();
    }

    @Override
    public Deprem getItem(int position) {
        return gosterilenDepremler.get(position);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Deprem> filtrelenmisListe = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filtrelenmisListe.addAll(tumDepremler);
                } else {
                    String filtreMetni = constraint.toString().toLowerCase().trim();
                    for (Deprem deprem : tumDepremler) {
                        if (deprem.getKonum().toLowerCase().contains(filtreMetni)) {
                            filtrelenmisListe.add(deprem);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtrelenmisListe;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                gosterilenDepremler.clear();
                gosterilenDepremler.addAll((List<Deprem>) results.values);
                notifyDataSetChanged();
            }
        };

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.deprem_liste_elemanlari, parent, false);
        }

        Deprem guncelDeprem = getItem(position);

        // Mag arka plan rengini burdan veriyoz
        TextView siddetView = listItemView.findViewById(R.id.magnitude);
        String siddetStr = guncelDeprem.getSiddet();
        if (siddetStr == null || siddetStr.isEmpty()) siddetStr = "-";
        siddetView.setText(siddetStr);

        try {
            double siddetDouble = Double.parseDouble(siddetStr);
            GradientDrawable arkaPlanCizimi = (GradientDrawable) siddetView.getBackground();
            int renk = getRenkKodunaGoreArkaPlan(siddetDouble);
            arkaPlanCizimi.setColor(renk);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Lokasyon
        TextView konumView = listItemView.findViewById(R.id.location);
        String konum = guncelDeprem.getKonum();
        if (konum == null || konum.isEmpty()) konum = "-";
        konumView.setText(konum);

        long zaman = guncelDeprem.getZamanMillis();
        String kacOnce = DepremEtkinligi.hesaplaKacOnce(zaman);
        // Tarih
        TextView tarihView = listItemView.findViewById(R.id.date);
        String tarih = guncelDeprem.getTarih();
        if (tarih == null || tarih.isEmpty()) tarih = "-"; tarihView.setText(tarih + " • " + kacOnce);

        // Bayraklar
        ImageView flagImage = listItemView.findViewById(R.id.flagImage);
        konum = konum.toLowerCase();
        int flagResId = R.drawable.ic_flag_placeholder;

        if (konum.contains("turkey") || konum.contains("türkiye") || konum.contains("istanbul")) {
            flagResId = R.drawable.flag_tr;
        } else if (konum.contains("france")) {
            flagResId = R.drawable.flag_fr;
        } else if (konum.contains("germany")) {
            flagResId = R.drawable.flag_de;
        } else if (konum.contains("greece") || konum.contains("athens")) {
            flagResId = R.drawable.flag_gr;
        } else if (konum.contains("england")) {
            flagResId = R.drawable.flag_uk;
        } else if (konum.contains("azerbaijan")) {
            flagResId = R.drawable.flag_az;
        } else if (konum.contains("japan")) {
            flagResId = R.drawable.flag_jp;
        } else if (konum.contains("afghanistan")) {
            flagResId = R.drawable.flag_af;
        } else if (konum.contains("america") || konum.contains("alaska") || konum.contains("california")) {
            flagResId = R.drawable.flag_us;
        } else if (konum.contains("mexico")) {
            flagResId = R.drawable.flag_mx;
        } else if (konum.contains("norway")) {
            flagResId = R.drawable.flag_no;
        } else if (konum.contains("argentina")) {
            flagResId = R.drawable.flag_ar;
        } else if (konum.contains("china")) {
            flagResId = R.drawable.flag_cn;
        } else if (konum.contains("chile")) {
            flagResId = R.drawable.flag_cl;
        } else if (konum.contains("peru")) {
            flagResId = R.drawable.flag_pe;
        } else if (konum.contains("indonesia") || konum.contains("waingapu") || konum.contains("padangsidempuan")
                || konum.contains("blangpidie") || konum.contains("tobelo") || konum.contains("ternate")) {
            flagResId = R.drawable.flag_id;
        } else if (konum.contains("vietnam")) {
            flagResId = R.drawable.flag_vn;
        } else if (konum.contains("romania")) {
            flagResId = R.drawable.flag_ro;
        } else if (konum.contains("pakistan")) {
            flagResId = R.drawable.flag_pk;
        } else if (konum.contains("iceland") || konum.contains("keflav") || konum.contains("reykjanes") || konum.contains("sandger")) {
            flagResId = R.drawable.flag_is;
        } else if (konum.contains("costa")) {
            flagResId = R.drawable.flag_cr;
        } else if (konum.contains("zealand")) {
            flagResId = R.drawable.flag_nz;
        } else if (konum.contains("tanzania")) {
            flagResId = R.drawable.flag_tz;
        } else if (konum.contains("philippines")) {
            flagResId = R.drawable.flag_ph;
        } else if (konum.contains("tonga")) {
            flagResId = R.drawable.flag_to;
        } else if (konum.contains("russia")) {
            flagResId = R.drawable.flag_ru;
        } else if (konum.contains("fiji")) {
            flagResId = R.drawable.flag_fj;
        } else if (konum.contains("kazakhstan")) {
            flagResId = R.drawable.flag_kz;
        } else if (konum.contains("papua")) {
            flagResId = R.drawable.flag_pg;
        } else if (konum.contains("vanuatu")) {
            flagResId = R.drawable.flag_vu;
        } else if (konum.contains("venezuela")) {
            flagResId = R.drawable.flag_ve;
        } else if (konum.contains("georgia")) {
            flagResId = R.drawable.flag_ge;
        } else if (konum.contains("colombia")) {
            flagResId = R.drawable.flag_co;
        }

        flagImage.setImageResource(flagResId);

        return listItemView;
    }

    private int getRenkKodunaGoreArkaPlan(double siddet) {
        if (siddet < 2.5) {
            return Color.parseColor("#2196F3"); // Mavi
        } else if (siddet < 3.0) {
            return Color.parseColor("#4CAF50"); // Yeşil
        } else if (siddet < 6.0) {
            return Color.parseColor("#FFC107"); // Sarı/Turuncu
        } else {
            return Color.parseColor("#F44336"); // Kırmızı
        }
    }

}
