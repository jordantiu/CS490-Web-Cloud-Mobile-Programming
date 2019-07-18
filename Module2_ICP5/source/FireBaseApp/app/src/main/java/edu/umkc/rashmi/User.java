package edu.umkc.rashmi;

/**
 * Created by Rashmi on 4/16/2018.
 */

public class User {

        public String name;
        public String phone;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
}

