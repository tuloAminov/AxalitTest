import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class imgShow {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread("src/img.png"); // загружаем картинку
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY); // превращаем картинку в черно-белую

        Mat binaryImage = new Mat();
        Imgproc.threshold(grayImage, binaryImage, 154, 255, Imgproc.THRESH_BINARY); // бинарируем

        // Находим кол-во точек
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binaryImage, contours, hierarchy, 1, 2);

        System.out.println("Количество точек: " + contours.size());

        // Находим площадь
        double area = 0;
        for (MatOfPoint contour : contours) {
            double contourArea = Imgproc.contourArea(contour);
            area += contourArea;
        }
        System.out.println("Площадь: " + area);

        // Нарисуем контур
        Imgproc.drawContours(image, contours, -1, new Scalar(80, 255, 20), 1);
        Imgcodecs.imwrite("contour.jpg", image);
    }
}
