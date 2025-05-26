package com.example.depremuygulamasi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class DepremAdaptoru extends ArrayAdapter<Deprem> {

    public DepremAdaptoru(Context context, List<Deprem> depremler) {
        super(context, 0, depremler);
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

        TextView siddetView = listItemView.findViewById(R.id.magnitude);
        siddetView.setText(guncelDeprem.getSiddet());

        TextView konumView = listItemView.findViewById(R.id.location);
        konumView.setText(guncelDeprem.getKonum());

        TextView tarihView = listItemView.findViewById(R.id.date);
        tarihView.setText(guncelDeprem.getTarih());

        ImageView flagImage = listItemView.findViewById(R.id.flagImage);
        String konum = guncelDeprem.getKonum().toLowerCase();
        int flagResId = R.drawable.ic_flag_placeholder;

        if (konum.contains("turkey") || konum.contains("türkiye") || konum.contains("istanbul")) {
            flagResId = R.drawable.flag_tr;
        }
        else if (konum.contains("france")) {
            flagResId = R.drawable.flag_fr;
        }
        else if (konum.contains("germany")) {
            flagResId = R.drawable.flag_de;
        }
        else if (konum.contains("greece") || konum.contains("athens")) {
            flagResId = R.drawable.flag_gr;
        }
        else if (konum.contains("england")) {
            flagResId = R.drawable.flag_uk;
        }
        else if (konum.contains("azerbaijan")) {
            flagResId = R.drawable.flag_az;
        }
        else if (konum.contains("japan")) {
            flagResId = R.drawable.flag_jp;
        }
        else if (konum.contains("afghanistan")) {
            flagResId = R.drawable.flag_af;
        }
        else if (konum.contains("america")) {
            flagResId = R.drawable.flag_us;
        }
        else if (konum.contains("mexico")) {
            flagResId = R.drawable.flag_mx;
        }
        else if (konum.contains("norway")) {
            flagResId = R.drawable.flag_no;
        }
        else if (konum.contains("argentina")) {
            flagResId = R.drawable.flag_ar;
        }
        else if (konum.contains("china")) {
            flagResId = R.drawable.flag_cn;
        }
        else if (konum.contains("chile")) {
            flagResId = R.drawable.flag_cl;
        }
        else if (konum.contains("peru")) {
            flagResId = R.drawable.flag_pe;
        }
        else if (konum.contains("indonesia") || konum.contains("ternate") ||
                konum.contains("blangpidie")) {
            flagResId = R.drawable.flag_id;
        }
        else if (konum.contains("vietnam")) {
            flagResId = R.drawable.flag_vn;
        }
        else if (konum.contains("romania")) {
            flagResId = R.drawable.flag_ro;
        }
        else if (konum.contains("pakistan")) {
            flagResId = R.drawable.flag_pk;
        }
        else if (konum.contains("iceland")) {
            flagResId = R.drawable.flag_is;
        }
        else if (konum.contains("Costa")) {
            flagResId = R.drawable.flag_cr;
        }
        else if (konum.contains("newzealand")) {
            flagResId = R.drawable.flag_nz;
        }
        else if (konum.contains("tanzania")) {
            flagResId = R.drawable.flag_tz;
        }
        else if (konum.contains("philippines")) {
            flagResId = R.drawable.flag_ph;
        }
        else if (konum.contains("tonga")) {
            flagResId = R.drawable.flag_to;
        }

        flagImage.setImageResource(flagResId);

        return listItemView;
    }
}
