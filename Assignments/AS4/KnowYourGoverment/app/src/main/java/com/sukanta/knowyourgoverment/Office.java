package com.sukanta.knowyourgoverment;

import java.io.Serializable;
import java.util.HashMap;

public class Office implements Serializable {
    HashMap<Integer,String> officesHashMap = new HashMap<>();
    String city;
    String state;
    String zip;
}
