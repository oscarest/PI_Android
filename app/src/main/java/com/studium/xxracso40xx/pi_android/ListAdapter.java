package com.studium.xxracso40xx.pi_android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.studium.xxracso40xx.pi_android.model.CancionObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter
{
    Activity context;
    ArrayList<CancionObject> canciones;
    private static LayoutInflater inflater = null;
    public ListAdapter(Activity context, ArrayList<CancionObject> canciones)
    {
    this.context = context;
    this.canciones = canciones;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return canciones.size();
    }

    @Override
    public Object getItem(int position) {
        return canciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    View itemView = convertView;
    itemView = (itemView==null) ? inflater.inflate(R.layout.activity_list_canciones, null):itemView;
        TextView nombreCancion = itemView.findViewById(R.id.listCancionNombre);
        ImageView imagenCancion = itemView.findViewById(R.id.listCancionImagen);
        CancionObject cancionSeleccionada = canciones.get(position);
        nombreCancion.setText(cancionSeleccionada.getNombreCancion());
        new DownLoadImageTask(imagenCancion).execute(cancionSeleccionada.getUrlImagenCancion());
        return itemView;
    }
    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
