package com.wordpress.elektroniknu.elektroniknu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


class productsAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Product[] products;
    private Context context;
    private Product p;

    //CONSTRUCTOR
    public productsAdapter(Context context, Product[] products) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.products = products;
    }

    public int getCount(){
        return products.length;
    }

    public Product getItem(int position){
        return products[position];
    }

    public long getItemId(int position){
        return position;
    }

    //ADAPT PROPERTIES OF EVERY VIEW OBJECT
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        p = products[position];
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.product_row, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.produktWebView);
            holder.productName = (TextView) convertView.findViewById(R.id.produktNameTextView);
            holder.storeText = (TextView) convertView.findViewById(R.id.storeTextView);
            holder.price = (Button) convertView.findViewById(R.id.pricebotton);
            holder.description1 = (TextView) convertView.findViewById(R.id.description1TextView);
            holder.description2 = (TextView) convertView.findViewById(R.id.description2TextView);
            holder.description3 = (TextView) convertView.findViewById(R.id.description3TextView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.productName.setText(p.getProductName());
        holder.storeText.setText(p.getStoreName());
        holder.price.setText(p.getProductPrice());
        try {
            holder.description1.setText(p.getProductDescription()[0]);
            try{
                holder.description2.setText(p.getProductDescription()[1]);
                try{
                    holder.description3.setText(p.getProductDescription()[2]);
                }catch (NullPointerException e){}
            }catch(NullPointerException e){
            }
        }catch(NullPointerException e){
            holder.description1.setText(" ");
            holder.description2.setText(" ");
            holder.description3.setText(" ");
        }
        if(p.getImage() != null){
            holder.image.setImageBitmap(p.getImage());
        }else{
            holder.image.setImageResource(R.drawable.ic_action_about);
        }

       holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlForOne =  p.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlForOne));
                context.startActivity(intent);
            }
        });

        holder.productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlForOne = p.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlForOne));
                context.startActivity(intent);
            }
        });



        return convertView;
    }

    static class ViewHolder{
        ImageView image;
        TextView productName;
        TextView storeText;
        Button price;
        TextView description1;
        TextView description2;
        TextView description3;
    }
}
