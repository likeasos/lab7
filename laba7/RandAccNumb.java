import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class RandAccNumb {
    public static void main(String[] args) {
        try {
            File folder = new File("E:\\My");
            if (!folder.exists())
                folder.mkdirs();
            File f1 = new File("E:\\My\\num1Mart.txt");
            f1.createNewFile();
            Scanner sc = new Scanner(System.in, "cp1251");
            System.out.println("Сколько чисел надо записать в ФАЙЛ?\n => ");
            int count = sc.nextInt();

            RandomAccessFile rf = new RandomAccessFile(f1, "rw");
            System.out.println("Исходный размер файла в байтах = " + rf.length() + ", указать стоит на " + rf.getFilePointer() + "-м байте");
            System.out.println("Введите числа: ");
            for (int i = 0; i < count; i++) {
                rf.writeShort(sc.nextInt());
            }
            System.out.println("Новый размер файла в байтах = " + rf.length() + ",указатель стоит на " + rf.getFilePointer() + "-м байте");
            System.out.println("Количество байт на 1 число = " + rf.length() / count);
            int numSize = (int)(rf.length() / count);
            rf.close();
            rf = new RandomAccessFile(f1, "r");
            System.out.println("\n Числа в файле: ");
            for (int i = 0; i < count; i++) {
                rf.seek(i * numSize);
                System.out.println("Число " + i + ": " + rf.readShort());
            }
            System.out.println("Числа в обратном порядке: ");
            for (int i = count - 1; i >= 0; i--) {
                rf.seek(i * numSize);
                System.out.println("Число " + i + ": " + rf.readShort());
            }
            rf.seek(rf.getFilePointer() - numSize);
            System.out.println("Кооличество чисел в файле = " + rf.length() / numSize + ", последнее число = " + rf.readShort());
            System.out.print("\n Введите число, которое нужно найти в файле => ");
            int x = sc.nextInt();
            int kol = 0;
            for (int i = 0; i < count; i++) {
                rf.seek(i * numSize);
                int number = rf.readShort();
                if (number == x) {
                    kol++;
                    System.out.print("номер " + i + ", ");
                }
            }
            System.out.println(" количество искомых чисел = " + kol);
            rf.close();
            rf = new RandomAccessFile(f1, "rw");
            for (int k = 0; k < count; k++) {
                for (int i = 0; i < count - k - 1; i++) {
                    rf.seek(i * numSize);
                    int number1 = rf.readShort();
                    int number2 = rf.readShort();
                    if (number1 > number2) {
                        rf.seek(i * numSize);
                        rf.writeShort(number2);
                        rf.writeShort(number1);
                    }
                }
            }
            System.out.println("\n Числа, остортированные в файле: ");
            for (int i = 0; i < count; i++) {
                rf.seek(i * numSize);
                System.out.print(" " + rf.readShort());
            }
            rf.close();
        } catch (IOException e) {
            System.out.println("End of file " + e);
        }
    }
}
