package com.example.christopher.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "EmployeesAdapter";
    private List<Employee> employeeList;
    private MainActivity mainAct;

    EmployeesAdapter(List<Employee> empList, MainActivity ma) {
        this.employeeList = empList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);

        Employee employee = employeeList.get(position);

        holder.name.setText(employee.getName());
        holder.empId.setText(String.format(Locale.getDefault(), "%d", employee.getEmpId()));
        holder.department.setText(employee.getDepartment());
        holder.dateTime.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

}