package com.trivia.trivia.activities.Events.multitype;

import com.trivia.trivia.util.Event;

import java.util.ArrayList;

public class SectionDataModel {
    private String headerTitle;
    private int type;
    private ArrayList<Event> allEventInSection;
    private ArrayList<SingleItemModel> singleItemModels;

    public SectionDataModel() {
    }

    public SectionDataModel(String headerTitle, ArrayList<Event> allItemInSection, int type) {
        this.type = type;
        this.headerTitle = headerTitle;
        this.allEventInSection = allItemInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Event> getAllEventInSection() {
        return allEventInSection;
    }

    public void setAllEventInSection(ArrayList<Event> allEventInSection) {
        this.allEventInSection = allEventInSection;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<SingleItemModel> getSingleItemModels() {
        return singleItemModels;
    }

    public void setSingleItemModels(ArrayList<SingleItemModel> singleItemModels) {
        this.singleItemModels = singleItemModels;
    }
}
