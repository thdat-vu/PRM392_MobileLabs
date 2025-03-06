package com.davt.lab12.ui.dashboard;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davt.lab12.R;
import com.davt.lab12.databinding.FragmentDashBoardBinding;
import com.davt.lab12.viewmodel.SharedViewModel;

public class DashBoardFragment extends Fragment {

    private FragmentDashBoardBinding binding;

    // Sử dụng sharedViewModel để có thể sử dụng data một vòng đời của Fragment
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Gửi dữ liệu vào ShareViewModel
        binding.btnUppNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.incrementSharedData();
            }
        });

        // Set Observer để hiện thị giá trị trước tiên trên toàn bộ các Fragment giá trị của sharedViewModel
        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                binding.textDashboard.setText(String.valueOf(data));
            }
        });

        return root;
    }
}