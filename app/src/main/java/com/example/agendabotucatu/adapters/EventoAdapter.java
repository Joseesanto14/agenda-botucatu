package com.example.agendabotucatu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agendabotucatu.activities.DetalhesEventoActivity;
import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Evento;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private Context context;
    private List<Evento> lista;

    public EventoAdapter(Context context, List<Evento> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = lista.get(position);

        carregarAutor(evento.getIdUsuarioCriador(), holder.txtNomeUsuario);
        holder.txtTitulo.setText(evento.getTitulo());
        holder.txtLocal.setText("Local: " + evento.getLocal());
        holder.txtDataHora.setText("Data: " + evento.getData());
        holder.txtCategoria.setText("Categoria: " + evento.getCategoria());

        // Carregar imagem com Glide
//        if (evento.getImagemUrl() != null && !evento.getImagemUrl().isEmpty()) {
//            Glide.with(context)
//                    .load(evento.getImagemUrl())
//                    .into(holder.imgEvento);
//        }

        // Clique no "Ver mais detalhes"
        holder.txtVerMais.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalhesEventoActivity.class);
            intent.putExtra("eventoId", evento.getIdEvento());
            context.startActivity(intent);
        });

        // Clique no botão de favoritar
        holder.btFavoritar.setOnClickListener(v -> {
            // todo: Aqui você vai colocar a lógica real depois
            // Por enquanto, apenas troca o ícone

            holder.btFavoritar.setImageResource(R.drawable.ic_favoritos);

        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUsuario, imgEvento;
        TextView txtNomeUsuario, txtTitulo, txtLocal, txtDataHora, txtCategoria, txtVerMais;
        ImageButton btFavoritar;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUsuario = itemView.findViewById(R.id.imgUsuario);
            imgEvento = itemView.findViewById(R.id.imgEvento);
            txtNomeUsuario = itemView.findViewById(R.id.txtNomeUsuario);
            txtTitulo = itemView.findViewById(R.id.txtTituloEvento);
            txtLocal = itemView.findViewById(R.id.txtData);
            txtDataHora = itemView.findViewById(R.id.txtDataHora);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtVerMais = itemView.findViewById(R.id.txtVerMais);
            btFavoritar = itemView.findViewById(R.id.btFavoritar);
        }
    }
    private void carregarAutor(String autorUid, TextView textView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios")
                .document(autorUid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists() && doc.getString("nome") != null) {
                        textView.setText("@" + doc.getString("nome"));
                    } else {
                        textView.setText("@" + autorUid);
                    }
                });
    }
}
