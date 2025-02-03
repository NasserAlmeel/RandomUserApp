package com.example.randomuserapp;

public class UserModel {
    private Name name;
    private Picture picture;

    public Name getName() {
        return name;
    }

    public Picture getPicture() {
        return picture;
    }

    public class Name {
        private String first;
        private String last;

        public String getFullName() {
            return first + " " + last;
        }
    }

    public class Picture {
        private String large;

        public String getLarge() {
            return large;
        }
    }
}
