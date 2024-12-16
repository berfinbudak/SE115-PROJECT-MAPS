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

    
        for (int i = 0; i < map.getCityNumber(); i++) { //her şehir için döngü yapılır
            for (int j = 0; j < map.getCityNumber(); j++) {
                
                if (!visited[j] && map.getRoad()[i][j] != Integer.MAX_VALUE) { //Eğer bir şehir henüz ziyaret edilmediyse (!visited[j]) ve iki şehir arasında yol varsa (map.getRoad()[i][j] != Integer.MAX_VALUE), işlem yapılır.
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
            return "No path found."; //bitiş şehri hâlâ sonsuz mesafe olarak işaretlenmişse, yolun bulunamadığını belirtmek
        }
    
        String path = reconstructPath(previous, startIdx, endIdx); //previous dizisi kullanılarak en kısa yolun adım adım oluşturulması.
       // İşleyiş: Bu işlem reconstructPath adlı yardımcı metotta yapılır.
        return "Fastest Way: " + path + "\nTotal Time: " + distances[endIdx] + " min";
    } //En kısa yolu ve toplam süreyi bir metin formatında döndürmek.
    
    private String reconstructPath(int[] previous, int start, int end) { //int start: Başlangıç şehri indeksi.int end: Bitiş şehri indeksi.
        String path = ""; //int[] previous: En kısa yoldaki bir önceki şehirleri tutar.
        int current = end; //
    
        while (current != -1) { //Bitiş şehrinden başlayarak previous dizisini takip ederek yolu oluşturmak.
            path = map.getCity()[current].getName() + (path.isEmpty() ? "" : " -> ") + path;
            current = previous[current];
        } //current şehri, başlangıç şehrine ulaşılana kadar döngüyle işlenir.
        // Şehrin adı, yolun başına eklenir.
       // current, bir önceki şehre (previous[current]) ayarlanır.
    
        return path; // tam yolu döndürmek
    }
}