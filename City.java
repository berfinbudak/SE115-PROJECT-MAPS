public class City {
    private String name;
    private int ind;

    City(String name, int ind) {
        this.name = name;
        this.ind = ind;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public String getName() {
        return name;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public int getInd() {
        return ind;
    }
}