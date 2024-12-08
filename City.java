public class City {
    private String label;
    private int index;

    City(String label, int index) {
        this.label = label;
        this.index = index;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}