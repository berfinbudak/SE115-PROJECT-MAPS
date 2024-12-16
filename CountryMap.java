public class CountryMap {//şehir arası yolları ve şehir listesini yönetir
    private City[] cities;//şehir listesini tutar
    private int[][] road; //yolların zaman bilgisi
    private int cityNumber = 0;//eklenen şehir sayısı

    public CountryMap(int size) { //haritayı ve yolları tanımlayan constructor 
        cities = new City[size];
        road = new int[size][size];

        for (int i = 0; i < size; i++) { //yollar başta ınt.max ile doldurulur (ulaşılamaz yol mantığında)
            for (int j = 0; j < size; j++) {
                road[i][j] = Integer.MAX_VALUE;
            }
        }
    }//getters ve setters
    public void setCity (City[]cities){//şehir listesini ayarlar
        this.cities = cities;
    }
    public City[] getCity(){
        return cities;
    }
    public void setRoad(int[][]road){
        this.road = road;
    }
    public int [][] getRoad (){// yol matrisini döner
        return road;
    }
    public void setCityNumber (int cityNumber){
        this.cityNumber = cityNumber;
    }
    
    public int getCityNumber(){//şehir sayısını döner
        return cityNumber;
    }

    public void addCity(String name) {//yenir bir şehir oluşturulur listeye eklenir
        cities[cityNumber++] = new City(name, cityNumber - 1);
    }//citynumber bir artırılır
    
    int getCityIndex(String name) {//verilen şehir adı bulunur indeksini döner bulamazsa -1 döner
        for (int i = 0; i < cityNumber; i++) {
            if (cities[i].getName().equals(name)) {
                return i;
            }
        }
        return -1; 
    }

    public void addRoad(String city1, String city2, int time) {//iki şehirin arasındaki yolu ekler
        int ind1 = getCityIndex(city1);
        int ind2 = getCityIndex(city2);
        if (ind1 != -1 && ind2 != -1) {
            road[ind1][ind2] = time;
            road[ind2][ind1] = time; 
        }
    }
} 