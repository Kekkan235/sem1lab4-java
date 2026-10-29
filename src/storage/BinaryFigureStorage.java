package storage;

import geometry2d.Figure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryFigureStorage implements FigureStorage {

    @Override
    public List<Figure> read(Path path)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream input =
                     new ObjectInputStream(Files.newInputStream(path))) {

            Object object = input.readObject();

            if (!(object instanceof List<?> list)) {
                throw new IOException("Файл не содержит список фигур");
            }

            List<Figure> figures = new ArrayList<>();

            for (Object item : list) {

                if (!(item instanceof Figure figure)) {
                    throw new IOException(
                            "В списке найден объект, который не является фигурой"
                    );
                }

                figures.add(figure);
            }

            return figures;
        }
    }

    @Override
    public void write(Path path, List<Figure> figures)
            throws IOException {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(Files.newOutputStream(path))) {

            output.writeObject(new ArrayList<>(figures));
        }
    }
}