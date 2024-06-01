package com.example.btt_sqlite_vinfast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btt_sqlite_vinfast.adapter.CartAdapter;
import com.example.btt_sqlite_vinfast.model.CartItem;
import com.example.btt_sqlite_vinfast.model.Product;
import com.example.btt_sqlite_vinfast.repository.CartItemRepository;
import com.example.btt_sqlite_vinfast.repository.CartRepository;
import com.example.btt_sqlite_vinfast.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>();
    private CartItemRepository cartItemRepository;

    public CartFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvCart;
    private TextView tvTotalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);

        cartItemRepository = new CartItemRepository(getActivity());
        int userId = getUserIdFromSession();
        cartItemList = cartItemRepository.getCartItemsByUserId(userId);

        cartAdapter = new CartAdapter(getActivity(), cartItemList, new CartAdapter.OnCartItemChangeListener() {
            @Override
            public void onQuantityChange(CartItem cartItem, int newQuantity) {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.updateCartItem(cartItem);
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }

            @Override
            public void onItemRemove(CartItem cartItem) {
                cartItemRepository.deleteCartItem(cartItem.getCartItemId());
                cartItemList.remove(cartItem);
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCart.setAdapter(cartAdapter);

        updateTotalPrice();

        return view;
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItemList) {
            Product product = cartAdapter.getProductRepository().getProductById(cartItem.getProductId());
            if (product != null) {
                totalPrice += product.getPrice() * cartItem.getQuantity();
            }
        }
        tvTotalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartItemRepository = new CartItemRepository(getActivity());

        cartAdapter = new CartAdapter(getActivity(), cartItemList, new CartAdapter.OnCartItemChangeListener() {
            @Override
            public void onQuantityChange(CartItem cartItem, int newQuantity) {
                cartItem.setQuantity(newQuantity);
                cartItemRepository.updateCartItem(cartItem);
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            }

            @Override
            public void onItemRemove(CartItem cartItem) {
                cartItemRepository.deleteCartItem(cartItem.getCartItemId());
                cartItemList.remove(cartItem);
                cartAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                updateTotalPrice();
            }
        });

        recyclerView.setAdapter(cartAdapter);

        loadCartItems();
    }

    private void loadCartItems() {
        int userId = getCurrentUserId(); // Implement method to get the current user ID
        cartItemList.clear();
        cartItemList.addAll(cartItemRepository.getCartItemsByUserId(userId));
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice(); // Ensure total price is updated when cart items are loaded
    }

    private int getCurrentUserId() {
        // Implement logic to get the current user ID
        return 1; // Example value
    }

    private int getUserIdFromSession() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

}
