public class City {//şehir adını ve indeksini tutuyor
    private String name;
    private int ind;

//constructor
    City(String name, int ind) {
        this.name = name;
        this.ind = ind;
    }
//get and setters
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