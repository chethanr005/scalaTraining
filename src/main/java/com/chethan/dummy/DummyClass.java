package com.chethan.dummy;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Chethan on Nov 04, 2022.
 */

public class DummyClass {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "z");
        map.put("3", "c");
        map.put("2", "b");
        System.out.println(map + " -> HashMap");

        //getOrDefault()
        String val = map.getOrDefault("4", "Nah!");
        System.out.println(val);

        //replaceAll()
        map.replaceAll((a, b) -> {
            Integer i1 = Integer.parseInt(a) * 3;
            return i1.toString();
        });
        System.out.println(map);

        //putIfAbsent()
        map.putIfAbsent("4", "four");
        System.out.println(map);

        //compute()
        map.compute("4", (k, v) -> "this is a second positive integer");
        map.computeIfAbsent("5", (k) -> "five");
        map.computeIfPresent("7", (k, v) -> "seven");
        System.out.println(map);


        //merge()
        map.merge("1", "10", (v1, v2) -> v1 + " *** " + v2);
        System.out.println(map);


        //A map entry (key-value pair). The Map.entrySet method returns a collection-view of the map, whose
        // elements are of this class. The only way to obtain a reference to a map entry is from the iterator of this collection-view. These
        // Map.Entry objects are valid only for the duration of the iteration; more formally, the behavior of a map entry is undefined if
        // the backing map has been modified after the entry was returned by the iterator, except through the setValue operation on the map entry.
        // Using entrySet() to get the entry's of the map

        Set<Map.Entry<String, String>> entry = map.entrySet();
        System.out.println(entry);

        LinkedHashMap<String, Integer> m =
                new LinkedHashMap<String, Integer>();

        m.put("1 - Bedroom", 25000);
        m.put("2 - Bedroom", 50000);
        m.put("3 - Bedroom", 75000);
        m.put("1 - Bedroom - hall", 65000);
        m.put("2 - Bedroom - hall", 85000);
        m.put("3 - Bedroom - hall", 105000);
        Set<Map.Entry<String, Integer>> s = m.entrySet();


        for (Map.Entry<String, Integer> it : s) {
            // Using the getKey to get key of the it element
            // Using the getValue to get value of the it element
            System.out.println("Before change of value = " +
                    it.getKey() + "   " + it.getValue());

            // Changing the value of 1 - Bedroom.
            double getRandom   = Math.random() * 100000;
            int    getRoundoff = (int) Math.round(getRandom);

            // Using setValue to change the value of the
            // map element
            it.setValue(getRoundoff);


            System.out.println("After change of value = " +
                    it.getKey() + "   " + it.getValue());
        }

        Optional<Map.Entry<String, Integer>> singleEntry = s.stream().filter(a -> a.getKey().equals("1 - Bedroom - hall")).findFirst();
        System.out.println(singleEntry);

        Map.Entry<String, Integer> actualEntry = singleEntry.get();
        System.out.println(actualEntry);


        Map<Integer, Integer> numMap = new HashMap<>();
        numMap.put(1, 5);
        numMap.put(2, 9);
        numMap.put(3, 5);
        numMap.put(4, 2);
        numMap.put(5, 9);
        numMap.put(6, 4);
        numMap.put(7, 1);


        //performing max and min condition on Map
        System.out.println(numMap.entrySet().stream().max((k, v) -> k.getValue() < v.getValue() ? 1 : -1));

        System.out.println(Collections.max(numMap.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey());

        System.out.println(Collections.max(numMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey());

        System.out.println(numMap.entrySet()
                                 .stream()
                                 .filter(entry1 -> entry1.getValue() == Collections.max(numMap.values()))
                                 //.findFirst()
                                 .collect(Collectors.toList()));


        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(new Employee(1, "","",1000,"" ));
        employeeList.add(new Employee(2, "","",2000,"" ));
        employeeList.add(new Employee(3, "","",1000,"" ));
        employeeList.add(new Employee(4, "","",1000,"" ));
        employeeList.add(new Employee(5, "","",3000,"" ));
        employeeList.add(new Employee(6, "","",1000,"" ));
        employeeList.add(new Employee(7, "","",1000,"" ));
        employeeList.add(new Employee(8, "","",9000,"" ));



        System.out.println(employeeList.stream().collect(Collectors.groupingBy(Employee::getSalary)));

    }


}


class Employee {
    private int    id;
    private String name;
    private String department;
    private double salary;
    private String gender;


    protected Employee(int id, String name, String department, double salary, String gender) {

        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.gender = gender;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    //Getters
    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + department + " " + salary + " " + gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Double.compare(employee.salary, salary) == 0 && Objects.equals(name, employee.name) && Objects.equals(department, employee.department) && Objects.equals(gender, employee.gender);
    }
}

