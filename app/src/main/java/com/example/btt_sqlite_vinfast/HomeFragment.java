package com.example.btt_sqlite_vinfast;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.btt_sqlite_vinfast.adapter.ProductAdapter;
import com.example.btt_sqlite_vinfast.model.Product;
import com.example.btt_sqlite_vinfast.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ImageView imgLogo;

    List<String> categoryList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();

    Button btnModalThemSP;
    Button btnModalSuaSP;
    Button btnModalThemLoaiSP;

    AutoCompleteTextView autoCompleteTextView;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    private boolean isLogoutConfirmed = false;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        autoCompleteTextView = view.findViewById(R.id.dropdown_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Đã chọn thể loại: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        btnModalThemSP = view.findViewById(R.id.btnModalThemSP);
        btnModalSuaSP = view.findViewById(R.id.btnModalSuaSP);
        btnModalThemLoaiSP = view.findViewById(R.id.btnModalThemLoaiSP);

        btnModalThemSP.setOnClickListener(v -> openModal(Gravity.CENTER, 0));
        btnModalSuaSP.setOnClickListener(v -> openModal(Gravity.CENTER, 1));
        btnModalThemLoaiSP.setOnClickListener(v -> openModal(Gravity.CENTER, 2));

        // Initialize ImageView and set onClickListener
        imgLogo = view.findViewById(R.id.imgLogo);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogoutConfirmed) {
                    logout();
                    Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                } else {
                    isLogoutConfirmed = true;
                    Toast.makeText(getActivity(), "Bấm lần nữa để đăng xuất", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isLogoutConfirmed = false;
                        }
                    }, 2000);
                }
            }
        });

        recyclerView = view.findViewById(R.id.rvproduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productAdapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(productAdapter);

        addSampleData();

        populateProductList();
    }

    private void addSampleData() {
        ProductRepository productRepository = new ProductRepository(getActivity());
        productRepository.addProduct(new Product(001, "VinFast Car", "vinfast_logo", 1000000, 1));
        productRepository.addProduct(new Product(002, "VinFast Bike", "default_url", 500000, 2));
        productRepository.addProduct(new Product(003, "VinFast Electric Scooter", "default_url", 300000, 3));
        productRepository.addProduct(new Product(004, "VinFast SUV", "default_url", 1500000, 1));
        productRepository.addProduct(new Product(005, "VinFast Sedan", "default_url", 1200000, 1));
    }

    private void populateProductList() {
        ProductRepository productRepository = new ProductRepository(getActivity());
        productList.clear();
        productList.addAll(productRepository.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }

    private void openModal(int gravity, int a) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (a == 0) {
            dialog.setContentView(R.layout.activity_add_product);
            setupAddProductDialog(dialog);
        } else if (a == 1) {
            dialog.setContentView(R.layout.activity_edit_product);
        } else {
            dialog.setContentView(R.layout.activity_add_category);
        }
        configureDialogWindow(dialog, gravity);
        dialog.show();
    }

    private void setupAddProductDialog(Dialog dialog) {
        EditText edtMaSP = dialog.findViewById(R.id.edtMaSP); // Assuming this is Product Code
        EditText edtTenSP = dialog.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = dialog.findViewById(R.id.edtGiaSP);
        EditText edtLoaiSP = dialog.findViewById(R.id.edtLoaiSP); // Category ID
        Button btnThemSP = dialog.findViewById(R.id.btnThemSP);

        btnThemSP.setOnClickListener(v -> {
            int productCode = Integer.parseInt(edtMaSP.getText().toString().trim());
            String productName = edtTenSP.getText().toString().trim();
            double productPrice = 0;
            int categoryId = 0;

            try {
                productPrice = Double.parseDouble(edtGiaSP.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Please enter a valid price", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                categoryId = Integer.parseInt(edtLoaiSP.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Please enter a valid category ID", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = new Product(productCode, productName, "default_url", productPrice, categoryId); // Assuming 'default_url' for demonstration
            ProductRepository productRepository = new ProductRepository(getActivity());
            productRepository.addProduct(product);

            Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            populateProductList();
        });
    }

    private void configureDialogWindow(Dialog dialog, int gravity) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = gravity;
            window.setAttributes(windowAttributes);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
