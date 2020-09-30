package com.example.christopher.recycler;

import androidx.annotation.NonNull;

public class Employee {

    private String name;
    private long empId;
    private String department;

    private static int ctr = 1;

    Employee() {
        this.name = "Employee Name " + ctr;
        this.empId = System.currentTimeMillis();
        this.department = "Department " + ctr;
        ctr++;
    }

    public String getName() {
        return name;
    }

    long getEmpId() {
        return empId;
    }

    String getDepartment() {
        return department;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + empId+ "), " + department;
    }
}