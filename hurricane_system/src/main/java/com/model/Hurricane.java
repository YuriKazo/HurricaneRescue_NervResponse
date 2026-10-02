package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Hurricane {
    private String hurricaneName;
    private int hurricaneCategory;
    private UUID hurricaneID;
    private ActiveLevel activeLevel;
    private ArrayList<String> currentZip;

    public Hurricane(String name, int category, ActiveLevel level, ArrayList<String> zip){

    }
    public ArrayList<String> getHurricaneZip(){
        return new ArrayList<String>();
    }
}
