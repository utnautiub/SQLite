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
import com.example.btt_sqlite_vinfast.model.CartItem;
import com.example.btt_sqlite_vinfast.model.Product;
import com.example.btt_sqlite_vinfast.repository.ProductRepository;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItemList;
    private ProductRepository productRepository;
    private OnCartItemChangeListener onCartItemChangeListener;

    public interface OnCartItemChangeListener {
        void onQuantityChange(CartItem cartItem, int newQuantity);
        void onItemRemove(CartItem cartItem);
    }

    public CartAdapter(Context context, List<CartItem> cartItemList, OnCartItemChangeListener onCartItemChangeListener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.productRepository = new ProductRepository(context);
        this.onCartItemChangeListener = onCartItemChangeListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        Product product = productRepository.getProductById(cartItem.getProductId());

        if (product != null) {
            holder.tvTitleProduct.setText(product.getName());
            holder.tvCategoryProduct.setText(String.valueOf(product.getCategoryId()));
            holder.tvGiaProduct.setText(String.valueOf(product.getPrice()));
            holder.tvTotalProduct.setText(String.valueOf(cartItem.getQuantity()));

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
        }

        holder.tvplus.setOnClickListener(v -> onCartItemChangeListener.onQuantityChange(cartItem, cartItem.getQuantity() + 1));
        holder.tvminus.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                onCartItemChangeListener.onQuantityChange(cartItem, cartItem.getQuantity() - 1);
            } else {
                onCartItemChangeListener.onItemRemove(cartItem);
            }
        });

        holder.btnAddToCart.setOnClickListener(v -> onCartItemChangeListener.onItemRemove(cartItem));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleProduct, tvCategoryProduct, tvGiaProduct, tvTotalProduct;
        ImageView imageProduct;
        TextView tvplus, tvminus;
        Button btnAddToCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleProduct = itemView.findViewById(R.id.tvTitleProduct);
            tvCategoryProduct = itemView.findViewById(R.id.tvCategoryProduct);
            tvGiaProduct = itemView.findViewById(R.id.tvGiaProduct);
            tvTotalProduct = itemView.findViewById(R.id.tvTotalProduct);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            tvplus = itemView.findViewById(R.id.tvplus);
            tvminus = itemView.findViewById(R.id.tvminus);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
