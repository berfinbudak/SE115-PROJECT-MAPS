import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String inputFileName = scanner.nextLine();

        try {
            File inputFile = new File(inputFileName);
            Scanner fileScanner = new Scanner(inputFile);

            int lineNumber = 0; 
            boolean validFile = true;

            
            lineNumber++;
            int cityNumber = 0;
            if (fileScanner.hasNextLine()) {
                String cityNumberLine = fileScanner.nextLine().trim();
                try {
                    cityNumber = Integer.parseInt(cityNumberLine);
                } catch (NumberFormatException e) {
                    System.out.println("Error Line " + lineNumber + ": Invalid city count.");
                    validFile = false;
                }
            } else {
                System.out.println("Error Line " + lineNumber + ": Missing city count.");
                validFile = false;
            }

            
            lineNumber++;
            String[] cityNames = new String[0];
            if (fileScanner.hasNextLine()) {
                String cityNamesLine = fileScanner.nextLine().trim();
                cityNames = cityNamesLine.split(" ");
                if (cityNames.length != cityNumber) {
                    System.out.println("Error Line " + lineNumber + ": Mismatch in city count and city labels.");
                    validFile = false;
                }
            } else {
                System.out.println("Error Line " + lineNumber + ": Missing city names.");
                validFile = false;
            }

            
            lineNumber++;
            int roadCount = 0;
            if (fileScanner.hasNextLine()) {
                String roadCountLine = fileScanner.nextLine().trim();
                try {
                    roadCount = Integer.parseInt(roadCountLine);
                } catch (NumberFormatException e) {
                    System.out.println("error on line " + lineNumber + ": route count format is invalid");
                    validFile = false;
                }
            } else {
                System.out.println("Error Line " + lineNumber + ": Missing route count.");
                validFile = false;
            }

            
            CountryMap map = new CountryMap(cityNumber);
            for (String city : cityNames) {
                map.addCity(city);
            }

            for (int i = 0; i < roadCount; i++) {
                lineNumber++;
                if (fileScanner.hasNextLine()) {
                    String[] roadLine = fileScanner.nextLine().trim().split(" ");
                    if (roadLine.length != 3) {
                        System.out.println("Error Line " + lineNumber + ": Invalid route format.");
                        validFile = false;
                        continue;
                    }
                    try {
                        map.addRoad(roadLine[0], roadLine[1], Integer.parseInt(roadLine[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("Error Line " + lineNumber + ": Invalid route time value.");
                        validFile = false;
                    }
                } else {
                    System.out.println("Error Line " + lineNumber + ": Missing route information.");
                    validFile = false;
                }
            }

            lineNumber++;
            String start = "", end = "";
            if (fileScanner.hasNextLine()) {
                String[] startEndLine = fileScanner.nextLine().trim().split(" ");
                if (startEndLine.length != 2) {
                    System.out.println("Error Line " + lineNumber + ": Invalid start and end cities format.");
                    validFile = false;
                } else {
                    start = startEndLine[0];
                    end = startEndLine[1];
                }
            } else {
                System.out.println("Error Line " + lineNumber + ": Missing start and end cities.");
                validFile = false;
            }

            fileScanner.close();

            if (validFile) {
                System.out.println("File read is successful!");

                WayFinder wayFinder = new WayFinder(map);
                String result = wayFinder.findShortestPath(start, end);

                try (FileWriter writer = new FileWriter("output.txt")) {
                    writer.write(result);
                } catch (IOException e) {
                    System.err.println("Error writing to file: " + e.getMessage());
                }

                System.out.println("Solution written to output.txt");
            } else {
                System.out.println("Input file has errors. Fix them and try again.");
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        scanner.close();
    }
}