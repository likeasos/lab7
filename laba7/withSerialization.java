import java.io.*;
import java.util.Scanner;

class Film implements Serializable {
    String name; // название фильма
    int year; // год выпуска
    String country; // страна
    String genre; // жанр
    int price; // стоимость проката
}

public class withSerialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in,"cp1251");
        // создается файл на диске
        File f1 = new File("E:\\laba7_out1.txt");
        f1.createNewFile();
        File f2 = new File("E:\\laba7_out2.txt");
        f2.createNewFile();
        // -------------ЗАПИСЬ ОБЪЕКТА В ФАЙЛ-------------
        // Создается поток для записи объекта
        FileOutputStream fos = new FileOutputStream(f1);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println("Введите количество фильмов => ");
        int count = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < count; i++) {
            // Вводится информация об объекте (стране)
            Film film = new Film();
            System.out.println("Введите информацию о фильме №" + (i + 1) + ": ");
            System.out.print("Название фильма => ");
            film.name = sc.nextLine();
            System.out.print("Год выпуска => ");
            film.year = sc.nextInt();
            sc.nextLine();
            System.out.print("Страна => ");
            film.country = sc.nextLine();
            System.out.print("Жанр => ");
            film.genre = sc.nextLine();
            System.out.print("Стоимость проката => ");
            film.price = sc.nextInt();
            sc.nextLine();
            // Объект записывается в файл
            oos.writeObject(film);
        }
        // Дописывается информация и закрывается файловый поток
        oos.flush();
        oos.close();

        // -------------ЧТЕНИЕ ОБЪЕКТА ИЗ ФАЙЛА-------------
        // Создается поток для чтения объекта из файла
        FileInputStream fis = new FileInputStream(f1);
        ObjectInputStream oin = new ObjectInputStream(fis);
        fos = new FileOutputStream(f2);
        oos = new ObjectOutputStream(fos);
        try {
            while (true) {
                // Объект считывается
                Film film = (Film) oin.readObject();
                if (film.country.equals("RUS")) {
                    oos.writeObject(film); // Записываем во второй файл, если он из России
                }
            }
        }
        catch (EOFException e) {
            // файл закончился
        }
        // Поток закрывается
        oin.close();
        oos.flush();
        oos.close();
    }
}
