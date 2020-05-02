package com.example.shaadi.utilities;

public class ItemNumberIndexForRecyclerList {
    private final int count;

    public ItemNumberIndexForRecyclerList(int count) {
        this.count = count;

    }

    public int getCursorPosition(){
        return count;
    }
}
