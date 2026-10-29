package app;

import geometry2d.Figure;
import geometry3d.Cylinder;
import storage.FigureStorage;
import storage.TextfigureStorage;
import storage.BinaryFigureStorage;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Программа запущена!");

        if (args.length != 2) {
            System.out.println(
                    "Использование: Main <входной файл> <выходной файл>"
            );
            System.out.println(
                    "Например: data/figures.txt data/figures.bin"
            );
            return;
        }

        Path inputPath = Path.of(args[0]);
        Path outputPath = Path.of(args[1]);

        FigureStorage textStorage = new TextfigureStorage();
        FigureStorage binaryStorage = new BinaryFigureStorage();

        try {

            // Читаем фигуры из текстового файла
            List<Figure> figures = textStorage.read(inputPath);

            System.out.println("Прочитанные фигуры:");

            for (Figure figure : figures) {

                System.out.println(figure);
                System.out.println("Площадь: " + figure.area());
                System.out.println("Периметр: " + figure.perimeter());
                System.out.println("--------------------");
            }

            // Создаём цилиндр
            if (!figures.isEmpty()) {

                Random random = new Random();

                Figure base = figures.get(
                        random.nextInt(figures.size())
                );

                try (Scanner scanner = new Scanner(System.in)) {

                    System.out.print("Введите высоту цилиндра: ");

                    double height = scanner.nextDouble();

                    Cylinder cylinder = new Cylinder(base, height);

                    System.out.println(cylinder);
                    System.out.println(
                            "Объём цилиндра: " + cylinder.volume()
                    );
                }

            } else {
                System.out.println(
                        "Список пуст. Цилиндр не создан."
                );
            }

            // Сохраняем фигуры в бинарный файл
            binaryStorage.write(outputPath, figures);

            System.out.println("Бинарный файл успешно сохранён!");

        } catch (Exception e) {

            System.out.println("Ошибка: " + e.getMessage());

            if (e.getCause() != null) {
                System.out.println(
                        "Причина: " + e.getCause().getMessage()
                );
            }
        }
    }
}