package storage;

import geometry2d.Figure;
import geometry2d.Circle;
import geometry2d.Rectangle;
import exceptions.InvalidFigureRecordException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextfigureStorage implements FigureStorage {

    @Override
    public List<Figure> read(Path path)
            throws IOException, InvalidFigureRecordException {

        List<Figure> figures = new ArrayList<>();

        List<String> lines = Files.readAllLines(
                path, StandardCharsets.UTF_8
        );

        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i).trim();

            if (line.isEmpty()) {
                continue;
            }

            try {
                String[] parts = line.split(";", -1);

                if (parts[0].equals("C") && parts.length == 2) {

                    double radius = Double.parseDouble(parts[1]);

                    figures.add(new Circle(radius));

                } else if (parts[0].equals("R") && parts.length == 3) {

                    double width = Double.parseDouble(parts[1]);
                    double height = Double.parseDouble(parts[2]);

                    figures.add(new Rectangle(width, height));

                } else {
                    throw new IllegalArgumentException(
                            "Неизвестный тип фигуры или неверное количество параметров"
                    );
                }

            } catch (RuntimeException e) {

                throw new InvalidFigureRecordException(
                        "Ошибка в строке " + (i + 1) + ": " + e.getMessage(),
                        e
                );
            }
        }

        return figures;
    }

    @Override
    public void write(Path path, List<Figure> figures)
            throws IOException {

        List<String> lines = new ArrayList<>();

        for (Figure figure : figures) {

            if (figure instanceof Circle circle) {

                lines.add("C;" + circle.getRadius());

            } else if (figure instanceof Rectangle rectangle) {

                lines.add(
                        "R;" + rectangle.getWidth() +
                                ";" + rectangle.getHeight()
                );
            } else {
                throw new IOException("Неизвестный тип фигуры");
            }
        }

        Files.write(path, lines, StandardCharsets.UTF_8);
    }
}