package com.example.btt_sqlite_vinfast.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btt_sqlite_vinfast.R;
import com.example.btt_sqlite_vinfast.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnAddToCartClickListener addToCartClickListener;

    public interface OnAddToCartClickListener {
        void onAddToCartClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnAddToCartClickListener addToCartClickListener) {
        this.context = context;
        this.productList = productList;
        this.addToCartClickListener = addToCartClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvTitleProduct.setText(product.getName());
        holder.tvCategoryProduct.setText(String.valueOf(product.getCategoryId())); // Hiển thị ID thể loại
        holder.tvGiaProduct.setText(String.valueOf(product.getPrice()));

        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            if (product.getImageUrl().startsWith("default_url")) {
                holder.imageProduct.setImageResource(R.drawable.vinfast_logo); // Hình ảnh mặc định
            } else {
                int imageResource = context.getResources().getIdentifier(product.getImageUrl(), "drawable", context.getPackageName());
                if (imageResource != 0) {
                    holder.imageProduct.setImageResource(imageResource);
                } else {
                    holder.imageProduct.setImageResource(R.drawable.vinfast_logo); // Hình ảnh mặc định
                }
            }
        } else {
            holder.imageProduct.setImageResource(R.drawable.vinfast_logo); // Hình ảnh mặc định
        }

        holder.btnAddToCart.setOnClickListener(v -> addToCartClickListener.onAddToCartClick(product));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleProduct, tvCategoryProduct, tvGiaProduct;
        ImageView imageProduct;
        Button btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleProduct = itemView.findViewById(R.id.tvTitleProduct);
            tvCategoryProduct = itemView.findViewById(R.id.tvCategoryProduct);
            tvGiaProduct = itemView.findViewById(R.id.tvGiaProduct);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
