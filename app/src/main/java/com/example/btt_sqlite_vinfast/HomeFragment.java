package com.example.btt_sqlite_vinfast;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btt_sqlite_vinfast.model.Product;
import com.example.btt_sqlite_vinfast.repository.ProductRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ImageView imgLogo;

    String[] items = {"Item 1", "Item 2", "Item 3"};

    Button btnModalThemSP;
    Button btnModalSuaSP;
    Button btnModalThemLoaiSP;

    AutoCompleteTextView autoCompleteTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize AutoCompleteTextView and Adapter
        autoCompleteTextView = view.findViewById(R.id.dropdown_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        autoCompleteTextView.setAdapter(adapter);

        // Set item click listener
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),"Đã chọn thể loại: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize and set onClickListeners for buttons
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
                logout();
                Toast.makeText(getActivity(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openModal(int gravity, int a) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (a == 0) {
            dialog.setContentView(R.layout.activity_add_product);
            setupAddProductDialog(dialog);
        } else if (a == 1) {
            dialog.setContentView(R.layout.activity_edit_product);
        }
        else
        {
            dialog.setContentView(R.layout.activity_add_category);
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void setupAddProductDialog(Dialog dialog) {
        EditText edtMaSP = dialog.findViewById(R.id.edtMaSP); // Assuming this is Product Code
        EditText edtTenSP = dialog.findViewById(R.id.edtTenSP);
        EditText edtGiaSP = dialog.findViewById(R.id.edtGiaSP);
        EditText edtLoaiSP = dialog.findViewById(R.id.edtLoaiSP); // Category ID
        Button btnThemSP = dialog.findViewById(R.id.btnThemSP);

        btnThemSP.setOnClickListener(v -> {
            String productCode = edtMaSP.getText().toString().trim();
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

            Product product = new Product(0, productCode, productName, "default_url", productPrice, categoryId); // Assuming 'default_url' for demonstration
            ProductRepository productRepository = new ProductRepository(getActivity());
            productRepository.addProduct(product);

            Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
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
        // Create an intent to start LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish(); // Close the current activity
    }

}
