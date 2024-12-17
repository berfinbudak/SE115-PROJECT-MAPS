import java.io.File;//dosya nesnesi oluşturmak için
import java.io.FileWriter;//çıktıyı bir dosyaya yazmak için kullanılır
import java.io.IOException;//dosya işlemi sırasında oluşabilecek hataları yönetmek için
import java.util.Scanner;// kullanıcı girişi alıp okumak için

public class Main {
    public static void main(String[] args) {//Kullanıcıdan bir dosya adı girme bloku
        Scanner scanner = new Scanner(System.in);//kullanıcıdan giriş almak için Scanner nesnesi oluşturulur
        System.out.print("Enter input file name: ");
        String inputFileName = scanner.nextLine();//kullanıcının girdiği dosya adı String değişkenine kaydedilir

        try {//Dosya Okuma Bloku
            
            
            File inputFile = new File(inputFileName);//kullanıcının verdiği dosya adıyla File nesnesi oluşturulur
            Scanner fileScanner = new Scanner(inputFile);//dosyayı okumak için Scanner nesnesi oluşturulur

            int lineNumber = 0; //Hangi satırda olduğumuzu takip eder. Hata mesajları verirken kullanılacaktır.
            boolean validFile = true; //Dosyanın geçerli olup olmadığını izler eğer bir hata bulunursa bu değişken false yapılır

            //Şehir Sayısını Okuma bloku
            lineNumber++;//1 olur
            int cityNumber = 0;//Dosyadan okunan şehir sayısını saklamak için kullanılır.

            if (fileScanner.hasNextLine()) {//Dosyada bir sonraki satır varsa işlemi başlatır.
                String cityNumberLine = fileScanner.nextLine().trim();//Okunan satırdaki baştaki ve sondaki boşluklar kaldırılır (trim()).
                try { //try: Metni (int) çevirmeye çalışır.

                    cityNumber = Integer.parseInt(cityNumberLine);
                } catch (NumberFormatException e) {//Eğer satırda int yoksa NumberFormatException fırlatılır validFile false yapılır ve hata mesajı yazdırılır.
                    
                    System.out.println("Error Line " + lineNumber + ": Invalid city count.");
                    validFile = false; //hata mesajı yazdırılır
                }
            } else {//Eğer dosyada bir sonraki satır yoksa bu blok çalışır.
                System.out.println("Error Line " + lineNumber + ": Missing city count.");
                validFile = false;//validFile değişkeni false yapılır
            }

            //Şehir Adlarını Okuma Bloku
            lineNumber++;//2 olur
            String[] cityNames = new String[0];//cityNames adlı String dizisi tanımlanır boş dizi ayarlanır
            if (fileScanner.hasNextLine()) {//dosyada 2.satır varsa işem başlatılır
                String cityNamesLine = fileScanner.nextLine().trim();//Okunan satırdaki baştaki ve sondaki boşluklar temizlenir 
                cityNames = cityNamesLine.split(" ");// metni boşluk karakterine göre böler ve bir String dizisi döndürür.
                if (cityNames.length != cityNumber) {//Eğer şehir sayısı ile verilen adların sayısı uyuşmazsa hata mesajı yazdırılır
                    System.out.println("Error Line " + lineNumber + ": Mismatch in city count and city labels.");
                    validFile = false;//validFile false yapılır.
                }
            } else {
                System.out.println("Error Line " + lineNumber + ": Missing city names.");
                validFile = false;//Hata mesajı yazdırılır.validFile değişkeni false yapılır,
            }

            //Yol Sayısını Okuma Bloku
            lineNumber++;//satır sayısı 3 olur
            int roadCount = 0;//roadCount adında bir tam sayı değişkeni oluşturulur ve başlangıç değeri 0 olarak atanır.
           //dosyanın üçüncü satırında belirtilen yol sayısını saklamak için kullanılır.
            if (fileScanner.hasNextLine()) {//Eğer dosyada üçüncü satır varsa, işlem devam eder. 
                String roadCountLine = fileScanner.nextLine().trim();//Dosyanın mevcut satırı okunur(filescanner ile) 
               //Okunan satırdaki baştaki ve sondaki boşluklar temizlenir (trim()).
             try {//dosyadan alınan yol sayısını metinden inte çevirmektir.
                    roadCount = Integer.parseInt(roadCountLine);//Integer.parseInt() fonksiyonu, bir metni (String) tam sayıya (int) dönüştürür.
                } catch (NumberFormatException e) {//hata oluşursa bu blok devreye girer.
                    System.out.println("error on line " + lineNumber +  "the format of the route count is invalid");
                    validFile = false;
                }
            } else {//Eğer dosyada üçüncü satır yoksa bu blok çalışır.

                System.out.println("Error Line " + lineNumber + ": Missing route count.");
                validFile = false;
            }

            //Harita Oluşturma ve Şehir Eklemek Bloku
            CountryMap map = new CountryMap(cityNumber);//Harita nesnesi, şehir sayısına göre oluşturulur.
            for (String city : cityNames) {
                map.addCity(city);// Şehir adları haritaya eklenir.
            }
         //Yol Bilgilerini Okuma Bloku
            for (int i = 0; i < roadCount; i++) {//belirtilen yol sayısı kadar
                lineNumber++;//satır numarasını artırır
                if (fileScanner.hasNextLine()) {
                    String[] roadLine = fileScanner.nextLine().trim().split(" ");// Satırı oku ve parçala.
                    if (roadLine.length != 3) {// Eğer satırda 3 parça yoksa (şehir1, şehir2, süre).
                        System.out.println("Error Line " + lineNumber + ": Invalid route format.");
                        validFile = false;
                        continue;// Bu satırı atla ve bir sonraki satıra geç
                    }
                    try {
                        map.addRoad(roadLine[0], roadLine[1], Integer.parseInt(roadLine[2]));// Yol bilgilerini haritaya ekle
                    } catch (NumberFormatException e) { // Eğer süre bir tam sayı değilse
                        System.out.println("Error Line " + lineNumber + ": Invalid route time value.");
                        validFile = false;
                    }
                } else {// Dosyada yol bilgisi eksikse
                    System.out.println("Error Line " + lineNumber + ": Missing route information.");
                    validFile = false;
                }
            }
        // Başlangıç ve Bitiş Şehirlerini Okuma Bloku
            lineNumber++; // Satır numarasını artır.
            String start = "", end = ""; // Başlangıç ve bitiş şehirlerini tutacak değişkenler.
            if (fileScanner.hasNextLine()) {
                String[] startEndLine = fileScanner.nextLine().trim().split(" "); // Satırı oku ve parçala
                if (startEndLine.length != 2) { // Eğer iki şehir adı yoksa
                    System.out.println("Error Line " + lineNumber + ": Invalid start and end cities format.");
                    validFile = false;
                } else { // Başlangıç ve bitiş şehirlerini değişkenlere ata
                    start = startEndLine[0];
                    end = startEndLine[1];
                }
            } else { // Eğer dosyada başlangıç ve bitiş şehirleri eksikse
                System.out.println("Error Line " + lineNumber + ": Missing start and end cities.");
                validFile = false;
            }

            fileScanner.close(); //dosyayı kapama
 
        //En Kısa Yol Hesaplama Bloku
            if (validFile) { // Eğer dosya geçerliyse
                System.out.println("File read is successful!");

                WayFinder wayFinder = new WayFinder(map); // WayFinder nesnesi oluştur
                String result = wayFinder.findShortestPath(start, end); // En kısa yolu bul

                try (FileWriter writer = new FileWriter("output.txt")) { // Çıktıyı dosyaya yaz
                    writer.write(result);
                } catch (IOException e) { // Dosyaya yazma hatalarını yakala
                    System.err.println("Error writing to file: " + e.getMessage());
                }

                System.out.println("Solution written to output.txt"); // Başarı mesajı yazdır
            } else { // Eğer dosya geçerli değilse
                System.out.println("Input file has errors. Fix them and try again.");
            }

        } catch (IOException e) {  // Eğer dosya okunurken hata oluşursa
            System.err.println("Error reading file: " + e.getMessage());
        }

        scanner.close(); 
    }
}