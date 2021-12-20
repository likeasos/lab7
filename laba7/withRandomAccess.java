import java.io.*;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class withRandomAccess {
    private static final int MAX_STRING_LENGTH = 20;

    private static String ReadString(Scanner sc) throws IllegalArgumentException {
        String out = sc.nextLine();
        if (out.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        return out;
    }

    private static String ReadFromFileString(RandomAccessFile rf) throws IOException {
        String str = rf.readUTF();
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.readByte();
        return str;
    }
    private static void WriteToFileString(RandomAccessFile rf, String str) throws IOException, IllegalArgumentException {
        if (str.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        rf.seek(rf.length()); // поиск конца файла
        rf.writeUTF(str);
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.writeByte(0);
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("E:\\laba7_randomAccess_out1.txt");
        if (!file1.createNewFile()) {
            file1.delete();
            file1.createNewFile();
        }
        RandomAccessFile raf1 = new RandomAccessFile(file1, "rw"); // чтение и запись
        Scanner sc = new Scanner(System.in, "cp1251");
        System.out.println("Введите количество фильмов => ");
        int count = sc.nextInt();
        sc.nextLine();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println("Введите информацию о фильме №" + (i + 1) + ": ");
                System.out.print("Название фильма => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Год выпуска => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
                System.out.print("Страна => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Жанр => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Стоимость проката => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
            }
        } catch (IllegalArgumentException e) {
            // Строка слишком длинная
            raf1.close();
            return;
        }
        raf1.close();

        raf1 = new RandomAccessFile(file1, "r"); // только чтение
        File file2 = new File("E:\\laba7_randomAccess_out2.txt");
        if (!file2.createNewFile()) {
            file2.delete();
            file2.createNewFile();
        }
        RandomAccessFile raf2 = new RandomAccessFile(file2, "rw"); //  чтение и запись
        try {
            while (true) {
                String filmName = ReadFromFileString(raf1);
                int filmYear = raf1.readInt();
                String filmCountry = ReadFromFileString(raf1);
                String filmGenre = ReadFromFileString(raf1);
                int filmPrice = raf1.readInt();
                if (filmCountry.equals("RUS")) {
                    WriteToFileString(raf2, filmName);
                    raf2.writeInt(filmYear);
                    WriteToFileString(raf2, filmCountry);
                    WriteToFileString(raf2, filmGenre);
                    raf2.writeInt(filmPrice);
                }
            }
        } catch (EOFException e) {
            // файл закончился
        } catch (IllegalArgumentException e) {
            // Строка слишком длинная
            raf2.close();
            raf1.close();
            return;
        }
        raf2.close();
        raf1.close();
    }
}
