package com.studium.xxracso40xx.pi_android.model;


import java.util.ArrayList;

public class SectionDataModel {



    private String headerTitle;
    private ArrayList<CancionObject> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<CancionObject> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<CancionObject> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<CancionObject> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}

