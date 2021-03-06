package com.smuzdev.lab_02.units;

import android.util.Log;

import java.util.Comparator;

public class Student extends Person {
    Integer rate;
    Boolean isEmployee;

    public Student(String name, Integer age, Integer rate, Enum anEnum) {
        this.name = name;
        this.age = age;
        this.rate = rate;
        this.anEnum = anEnum;
    }

    @Override
    public String toString() {
        Log.i("Person", "Name: " + name + " " + "Age: " + age + " " + "Rate: " + rate);
        return "";
    }

    @Override
    public int compareTo(Object object) {
        int rate = ((Student)object).rate;
        return rate-this.rate;
    }

    public static Comparator<Person> StudentAgeComparator = new Comparator<Person>() {
        @Override
        public int compare(Person student1, Person student2) {
            Integer student1Age = student1.age;
            Integer student2Age = student2.age;
            return student1Age.compareTo(student2Age);
        }
    };

}
