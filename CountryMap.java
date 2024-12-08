public class CountryMap {
    private City[] cities;
    private int[][] roads; 
    private int cityCount = 0;

    public CountryMap(int size) {
        cities = new City[size];
        roads = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                roads[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    public void setCity (City[]cities){
        this.cities = cities;
    }
    public City[] getCity(){
        return cities;
    }
    public void setRoads(int[][]roads){
        this.roads = roads;
    }
    public int [][] getRoads (){
        return roads;
    }
    public void setCityCount (int cityCount){
        this.cityCount = cityCount;
    }
    public int getCityCount(){
        return cityCount;
    }

    public void addCity(String label) {
        cities[cityCount++] = new City(label, cityCount - 1);
    }
    int getCityIndex(String label) {
        for (int i = 0; i < cityCount; i++) {
            if (cities[i].getLabel().equals(label)) {
                return i;
            }
        }
        return -1; 
    }

    public void addRoute(String city1, String city2, int time) {
        int index1 = getCityIndex(city1);
        int index2 = getCityIndex(city2);
        if (index1 != -1 && index2 != -1) {
            roads[index1][index2] = time;
            roads[index2][index1] = time; 
        }
    }
}
