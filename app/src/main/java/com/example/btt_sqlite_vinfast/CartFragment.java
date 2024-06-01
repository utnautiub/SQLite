package com.example.btt_sqlite_vinfast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.btt_sqlite_vinfast.adapter.CartAdapter;
import com.example.btt_sqlite_vinfast.model.CartItem;
import com.example.btt_sqlite_vinfast.repository.CartItemRepository;
import com.example.btt_sqlite_vinfast.repository.CartRepository;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
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
            }

            @Override
            public void onItemRemove(CartItem cartItem) {
                cartItemRepository.deleteCartItem(cartItem.getCartItemId());
                cartItemList.remove(cartItem);
                cartAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
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
    }

    private int getCurrentUserId() {
        // Implement logic to get the current user ID
        return 1; // Example value
    }
}
