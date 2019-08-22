package com.bilal.karademir.rehberuygulamasi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class KisilerAdapter extends RecyclerView.Adapter<KisilerAdapter.CardTasarimtutucu> {

    private Context mContext;
    private List<Kisiler> kisilerListe;
    Veritabani vt;

    public KisilerAdapter(Context mContext, List<Kisiler> kisilerListe, Veritabani vt) {
        this.mContext = mContext;
        this.kisilerListe = kisilerListe;
        this.vt = vt;
    }


    @NonNull
    @Override
    public CardTasarimtutucu onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kisi_card_tasarim,viewGroup,false);

        return new CardTasarimtutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardTasarimtutucu cardTasarimtutucu, int i) {
        final Kisiler kisi = kisilerListe.get(i);
        cardTasarimtutucu.textViewKisiBilgi.setText(kisi.getKisi_adi()+" - "+kisi.getKisi_tel());
        cardTasarimtutucu.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,cardTasarimtutucu.imageView);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());



                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.action_sil:

                                Snackbar.make(cardTasarimtutucu.imageView,"Silmek İstiyor musunuz?",Snackbar.LENGTH_LONG)

                                        .setAction("Evet", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                vt = new Veritabani(mContext);
                                                new KisilerDao().kisiSil(vt,kisi.getKisi_id());

                                                kisilerListe = new KisilerDao().tumKisler(vt);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .show();
                                return true;
                            case R.id.action_guncelle:

                                alertGoster(kisi);

                                //Snackbar.make(cardTasarimtutucu.imageView,"Güncellemek İstiyor musunuz?",Snackbar.LENGTH_LONG).show();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return kisilerListe.size();
    }

    public class CardTasarimtutucu extends RecyclerView.ViewHolder{

        private TextView textViewKisiBilgi;
        private ImageView imageView;

        public CardTasarimtutucu(@NonNull View itemView) {
            super(itemView);

            textViewKisiBilgi = itemView.findViewById(R.id.textViewKisiBilgi);
            imageView = itemView.findViewById(R.id.imageView);





        }
    }



    public void alertGoster(final Kisiler kisi){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View tasarim = layoutInflater.inflate(R.layout.alert_tasarim,null);
        final EditText editTextAd = tasarim.findViewById(R.id.editTextAd);
        final EditText editTextTel = tasarim.findViewById(R.id.editText2Tel);

        editTextAd.setText(kisi.getKisi_adi());
        editTextTel.setText(kisi.getKisi_tel());

        AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
        ad.setTitle("Kişi Güncelle");
        ad.setView(tasarim);
        ad.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String ad = editTextAd.getText().toString().trim();
                String tel = editTextTel.getText().toString().trim();
                int kisi_id = kisi.getKisi_id();

                vt = new Veritabani(mContext);

                new KisilerDao().kisiGuncelle(vt,kisi_id,ad,tel);
                kisilerListe = new KisilerDao().tumKisler(vt);
                notifyDataSetChanged();

               // Toast.makeText(mContext, "Adı: "+ad+" Tel: "+tel, Toast.LENGTH_SHORT).show();

            }
        });

        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();


    }
}
