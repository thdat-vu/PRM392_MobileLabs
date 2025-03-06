package com.davt.lab12.ui.notifications;

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
import com.davt.lab12.databinding.FragmentNotificationsBinding;
import com.davt.lab12.viewmodel.SharedViewModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    // Sử dụng sharedViewModel để có thể sử dụng data một vòng đời của Fragment
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container,  false);
        View root = binding.getRoot();

        // Khai báo viewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Nhận dữ liệu và hiện thị nó
        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                binding.textNotifications.setText(String.valueOf(data));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}