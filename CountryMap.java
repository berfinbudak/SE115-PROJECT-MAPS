public class CountryMap {
    private City[] cities;
    private int[][] road; 
    private int cityNumber = 0;

    public CountryMap(int size) {
        cities = new City[size];
        road = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                road[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    public void setCity (City[]cities){
        this.cities = cities;
    }
    public City[] getCity(){
        return cities;
    }
    public void setRoad(int[][]road){
        this.road = road;
    }
    public int [][] getRoad (){
        return road;
    }
    public void setCityNumber (int cityNumber){
        this.cityNumber = cityNumber;
    }
    
    public int getCityNumber(){
        return cityNumber;
    }

    public void addCity(String name) {
        cities[cityNumber++] = new City(name, cityNumber - 1);
    }
    
    int getCityIndex(String name) {
        for (int i = 0; i < cityNumber; i++) {
            if (cities[i].getName().equals(name)) {
                return i;
            }
        }
        return -1; 
    }

    public void addRoad(String city1, String city2, int time) {
        int ind1 = getCityIndex(city1);
        int ind2 = getCityIndex(city2);
        if (ind1 != -1 && ind2 != -1) {
            road[ind1][ind2] = time;
            road[ind2][ind1] = time; 
        }
    }
} 