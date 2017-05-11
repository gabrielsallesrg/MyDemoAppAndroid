package br.com.rg.gabrielsalles.mydemoapp2017.randomuser;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.Target;
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
                .thumbnail(Glide
                        .with(imageView.getContext())
                        .load(url)
                        .centerCrop()
                        .override(1024, 1024)
                        .skipMemoryCache(true)
                        .sizeMultiplier(.1f)
                        .crossFade(0) // dontAnimate doesn't work here, see GRB.buildRequestRecursive
                )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"imageUrlDetailGlide"})
    public static void setImageUrlDetailGlide(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .dontAnimate()
                .skipMemoryCache(true)
                .thumbnail(Glide
                        .with(imageView.getContext())
                        .load(url)
                        .centerCrop()
                        .override(1024, 1024)
                        .skipMemoryCache(true)
                        .sizeMultiplier(.1f)
                        .crossFade(0) // dontAnimate doesn't work here, see GRB.buildRequestRecursive
                )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
