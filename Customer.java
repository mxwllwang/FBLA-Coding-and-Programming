package fec;

public class Customer implements Comparable<Customer>, java.io.Serializable {

    // Customer Information
    String firstName;
    String middleName = null;
    String lastName;
    int age;

    // 1. male 2. female 3. other
    int gender;
    String notes;
    String email;

    Schedule attendance;
    long lastUpdated;

    Customer(String fn, String mn, String ln, int a, int g, String n, String e,
            long lu) {
        firstName = fn;
        middleName = mn;
        lastName = ln;
        age = a;
        gender = g;
        notes = n;
        email = e;
        lastUpdated = lu;
    }

    @Override
    public int compareTo(Customer x) {
        return Long.valueOf(this.lastUpdated).compareTo(
                Long.valueOf(x.lastUpdated));
    }

//    public Object[] forTable() {
//        return new Object[] { lastName, firstName, middleName, age, gender,
//                attendance, email, notes, lastUpdated };
//    }

}
