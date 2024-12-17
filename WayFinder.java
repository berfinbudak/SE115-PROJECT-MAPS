public class WayFinder {
    private CountryMap map;

    public WayFinder(CountryMap map) {
        this.map = map;
    }

    // setters and getters
    public void setCountryMap(CountryMap map ){
        this.map = map;
    }

    public CountryMap getCountryMap(){
        return map;
    }

    
    public String findShortestPath(String start, String end) { //Belirtilen başlangıç ve bitiş şehirleri arasında en kısa yolu bulur.
        int startIdx = map.getCityIndex(start); //String start: Başlangıç şehri adı.
        int endIdx = map.getCityIndex(end); //String end: Bitiş şehri adı

    
        if (startIdx == -1 || endIdx == -1) {   
            return "Invalid start or end city.";
        }
        //getCityIndex ile başlangıç (start) ve bitiş (end) şehirlerinin indekslerini alır.
        //eğer bir şehir bulunamazsa (-1), geçersiz şehir mesajı döner.
    
    
        
        int[] distances = new int[map.getCityNumber()]; //Her şehir için başlangıçtan olan en kısa mesafeyi tutar
        int[] previous = new int[map.getCityNumber()];//Her şehrin en kısa yolda hangi şehirden geldiğini tutar
        boolean[] visited = new boolean[map.getCityNumber()]; //Şehirlerin ziyaret edilip edilmediğini tutar
    
        for (int i = 0; i < map.getCityNumber(); i++) {
            distances[i] = Integer.MAX_VALUE; // Set all distances to "infinity"
            previous[i] = -1;                 // Initialize previous to -1 (no path)
        }
    
        distances[startIdx] = 0; // Start city has 0 distance

    
        for (int i = 0; i < map.getCityNumber(); i++) { //her şehir için döngü yapılır,checks the shortest path for each city
            for (int j = 0; j < map.getCityNumber(); j++) { // this loop checks the unvisited cities that have a road connection. 
                
                if (!visited[j] && map.getRoad()[i][j] != Integer.MAX_VALUE) { //Eğer bir şehir henüz ziyaret edilmediyse (!visited[j]) ve iki şehir arasında yol varsa işlem yapılır.
                    int newDist = distances[i] + map.getRoad()[i][j]; //Mevcut şehirden (i) diğer şehre (j) olan toplam mesafe hesaplanır.

                    if (newDist < distances[j]) { //Eğer yeni mesafe, mevcut mesafeden küçükse
                        distances[j] = newDist;  //distances[j]: Güncellenir
                        previous[j] = i; //previous[j]: Bu şehrin geldiği şehir (i) olarak ayarlanır.
                    }
                } 
            }
            visited[i] = true; //şehir visited olarak işaretlenir (visited[i] = true).

        }
    
        if (distances[endIdx] == Integer.MAX_VALUE) {
            return "No path found."; //bitiş şehri hâlâ sonsuz mesafe olarak işaretlenmişse, yolun bulunamadığını belirtirir
        }
    
        String path = reconstructPath(previous, startIdx, endIdx); //reconstructPath metodu, previous[] dizisini kullanarak yolu geri izler ve oluşturur.

        return "Fastest Way: " + path + "\nTotal Time: " + distances[endIdx] + " min";
    } //En kısa yolu ve toplam süreyi bir metin formatında döndürür.
    
    //int start başlangıç şehrinin indeksi,int end bitiş şehrinin indeksi.

    private String reconstructPath(int[] previous, int start, int end) { //Bu dizi, her bir şehir için bir önceki şehri tutar.
        String path = ""; //Güzergâhı oluşturacak string değişkeni. Başlangıçta boş bir string olarak başlatılır.
        int current = end; //Şu anki şehir indeksini tutar. Bitiş şehrinin indeksi ile başlatılır
    
        while (current != -1) { //Döngü, current değeri -1 olana kadar devam eder.-1 değeri, başlangıç şehrine ulaşıldığını belirtir.
            path = map.getCity()[current].getName() + (path.isEmpty() ? "" : " -> ") + path;//current indeksindeki şehrin adını döner.path boşsa sadece şehir adı eklenir.
                                                                                            // Eğer path boş değilse, " -> " eklenerek şehir adı birleştirilir.
         current = previous[current];//previous dizisini kullanarak bir önceki şehre geçilir.
        } 
    
        return path; //ath stringi, şehir adlarının bitişten başlangıca doğru birleştirilmiş halini döner.
    }
}