package br.com.rg.gabrielsalles.mydemoapp2017.randomuser;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import br.com.rg.gabrielsalles.mydemoapp2017.R;

/**
 * Created by gabriel on 18/04/17.
 */

public final class RandomUserDataBinder {

    private RandomUserDataBinder() {

    }

    @BindingAdapter({"imageUrlGlide"})
    public static void setImageUrlGlide(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"imageUrlDetailGlide"})
    public static void setImageUrlDetailGlide(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .into(imageView);
    }

    @BindingAdapter({"imageUrlPicasso"})
    public static void setImageUrlPicasso(final ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"imageUrlDetailPicasso"})
    public static void setImageUrlDetailPicasso(final ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .fit()
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }


}
