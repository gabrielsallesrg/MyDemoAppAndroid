package br.com.rg.gabrielsalles.mydemoapp2017.randomuser;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.rg.gabrielsalles.mydemoapp2017.R;


public final class RandomUserImageDataBinder {

    private RandomUserImageDataBinder() {

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
                .placeholder(R.drawable.ic_account_grey600_48dp)
                .error(R.drawable.ic_account_grey600_48dp)
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

//    @BindingAdapter({"imageUrlPicasso"})
//    public static void setImageUrlPicasso(final ImageView imageView, String url) {
//        Picasso.with(imageView.getContext())
//                .load(url)
//                .placeholder(R.drawable.ic_account_grey600_48dp)
//                .fit()
//                .error(R.drawable.ic_account_grey600_48dp)
//                .into(imageView);
//    }
//
//    @BindingAdapter({"imageUrlDetailPicasso"})
//    public static void setImageUrlDetailPicasso(final ImageView imageView, String url) {
//        Picasso.with(imageView.getContext())
//                .load(url)
//                .fit()
//                .error(R.drawable.ic_account_grey600_48dp)
//                .into(imageView);
//    }


}
