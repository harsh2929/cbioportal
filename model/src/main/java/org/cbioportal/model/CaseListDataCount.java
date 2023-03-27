package org.cbioportal.model;

public class CaseListDataCount {
    private final String label;
    private final String value;
    private int count;

    public CaseListDataCount(String label, String value, int count) {
        this.label = label;
        this.value = value;
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
}
