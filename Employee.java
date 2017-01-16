package fec;

public class Employee implements Comparable<Employee>, java.io.Serializable {

    // Employee information
    String firstName;
    String middleName = null;
    String lastName;
    int age;
    // 1. male 2. female 3. other
    int gender;
    double wage;
    String position;
    String notes;

    long lastUpdated;

    Schedule workschedule;

    String email;

    public Employee(String fn, String mn, String ln, int a, int g, double s,
            String n, String p, long lu, String e) {
        firstName = fn;
        middleName = mn;
        lastName = ln;
        age = a;
        gender = g;
        wage = s;
        notes = n;
        position = p;
        lastUpdated = lu;
        email = e;
    }

    @Override
    public int compareTo(Employee x) {
        return Long.valueOf(this.lastUpdated).compareTo(
                Long.valueOf(x.lastUpdated));
    }
    
//    public Object[] forTable() {
//        return new Object[] { lastName, firstName, middleName, age, gender,
//                position, wage, email, workschedule, notes, lastUpdated };
//    }

}
