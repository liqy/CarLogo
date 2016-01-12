package com.bolooo.carlogo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.carlogo.ItemCar;
import com.bolooo.carlogo.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by liqin on 2016/1/12.
 */
public class CarLogoAdapter extends RecyclerView.Adapter<CarLogoAdapter.ViewHolder> {

    ArrayList<ItemCar> cars;
    Activity activity;

    public CarLogoAdapter(Activity activity, ArrayList<ItemCar> cars) {
        this.activity = activity;
        this.cars = cars;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_logo, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCar car = this.cars.get(position);
        holder.brandName.setText(car.attrquery);
        Glide.with(activity).load(car.normalpic).into(holder.carLogo);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView carLogo;
        public TextView brandName;

        public ViewHolder(View view) {
            super(view);
            carLogo = (ImageView) view.findViewById(R.id.carLogo);
            brandName = (TextView) view.findViewById(R.id.brandName);
        }
    }
}
