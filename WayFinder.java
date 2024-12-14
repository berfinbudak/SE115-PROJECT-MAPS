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

    
    public String findShortestPath(String start, String end) {
        int startIdx = map.getCityIndex(start); 
        int endIdx = map.getCityIndex(end);
    
        if (startIdx == -1 || endIdx == -1) {
            return "Invalid start or end city.";
        }
    
        
        int[] distances = new int[map.getCityNumber()];
        int[] previous = new int[map.getCityNumber()];
        boolean[] visited = new boolean[map.getCityNumber()];
    
        for (int i = 0; i < map.getCityNumber(); i++) {
            distances[i] = Integer.MAX_VALUE; 
            previous[i] = -1;                 
        }
    
        distances[startIdx] = 0; 
    
        for (int i = 0; i < map.getCityNumber(); i++) {
            for (int j = 0; j < map.getCityNumber(); j++) {
                
                if (!visited[j] && map.getRoad()[i][j] != Integer.MAX_VALUE) {
                    int newDist = distances[i] + map.getRoad()[i][j];
                    if (newDist < distances[j]) {
                        distances[j] = newDist;  
                        previous[j] = i;        
                    }
                } 
            }
            visited[i] = true; 
        }
    
        if (distances[endIdx] == Integer.MAX_VALUE) {
            return "No path found."; 
        }
    
        String path = reconstructPath(previous, startIdx, endIdx);
        return "Fastest Way: " + path + "\nTotal Time: " + distances[endIdx] + " min";
    }
    
    private String reconstructPath(int[] previous, int start, int end) {
        String path = "";
        int current = end;
    
        while (current != -1) {
            path = map.getCity()[current].getName() + (path.isEmpty() ? "" : " -> ") + path;
            current = previous[current];
        }
    
        return path;
    }
}