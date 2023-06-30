package com.app.wetravel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class CityPickerDialogFragment extends DialogFragment {
    private String selectedCity;
    private OnCitySelectedListener listener;




    public interface OnCitySelectedListener {
        void onCitySelected(String city);
    }

    public void setOnCitySelectedListener(OnCitySelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_city_picker, null);
        builder.setView(view);

        NumberPicker numberPicker = view.findViewById(R.id.numberPicker);
        String[] cities = getResources().getStringArray(R.array.china_cities);
        numberPicker.setDisplayedValues(cities);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(cities.length - 1);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedCityIndex = numberPicker.getValue();
                String selectedCity = cities[selectedCityIndex];
                if (listener != null) {
                    listener.onCitySelected(selectedCity);
                }
            }
        });

        return builder.create();
    }


}
