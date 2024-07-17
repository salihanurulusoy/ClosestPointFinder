import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;

public class QuickSort {

    /**
     * Default Contructor
     */
    public QuickSort() {
        //Empty constructor --- do nothing
    }

    /**
     * The main function that implements QuickSort
     *
     * @param arr        --> Array to be sorted
     * @param startIndex --> First index of arr[]
     * @param lastIndex  --> Last index of arr[]
     * @param orderBy    --> compareX or compareY
     *                   compareX: sort minimum to maximum arr[] by X point
     *                   compareY: sort minimum to maximum arr[] by Y point
     */
    public void sort(Point2D.Double[] arr, int startIndex, int lastIndex, String orderBy) {
        if (orderBy.equals("compareX")) {
            sortX(arr, startIndex, lastIndex);
        } else if (orderBy.equals("compareY")) {
            sortY(arr, startIndex, lastIndex);
        }
    }

    private void sortX(Point2D.Double[] arr, int startIndex, int lastIndex) {
        if (startIndex < lastIndex) {
            int pivotIndex = partitionX(arr, startIndex, lastIndex);
            sortX(arr, startIndex, pivotIndex - 1);
            sortX(arr, pivotIndex + 1, lastIndex);
        }
    }

    private void sortY(Point2D.Double[] arr, int startIndex, int lastIndex) {
        if (startIndex < lastIndex) {
            int pivotIndex = partitionY(arr, startIndex, lastIndex);
            sortY(arr, startIndex, pivotIndex - 1);
            sortY(arr, pivotIndex + 1, lastIndex);
        }
    }

    private int partitionX(Point2D.Double[] arr, int startIndex, int lastIndex) {
        Point2D.Double pivot = getMedianX(arr, startIndex, lastIndex);
        int i = startIndex - 1;
        for (int j = startIndex; j < lastIndex; j++) {
            if (arr[j].getX() < pivot.getX()) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, lastIndex);
        return i + 1;
    }

    private int partitionY(Point2D.Double[] arr, int startIndex, int lastIndex) {
        Point2D.Double pivot = getMedianY(arr, startIndex, lastIndex);
        int i = startIndex - 1;
        for (int j = startIndex; j < lastIndex; j++) {
            if (arr[j].getY() < pivot.getY()) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, lastIndex);
        return i + 1;
    }

    private void swap(Point2D.Double[] arr, int i, int j) {
        Point2D.Double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private Point2D.Double getMedianX(Point2D.Double[] arr, int left, int right) {
        int mid = left + (right - left) / 2;
        if (arr[left].getX() > arr[mid].getX()) {
            swap(arr, left, mid);
        }
        if (arr[left].getX() > arr[right].getX()) {
            swap(arr, left, right);
        }
        if (arr[mid].getX() > arr[right].getX()) {
            swap(arr, mid, right);
        }
        return arr[mid];
    }

    private Point2D.Double getMedianY(Point2D.Double[] arr, int left, int right) {
        int mid = left + (right - left) / 2;
        if (arr[left].getY() > arr[mid].getY()) {
            swap(arr, left, mid);
        }
        if (arr[left].getY() > arr[right].getY()) {
            swap(arr, left, right);
        }
        if (arr[mid].getY() > arr[right].getY()) {
            swap(arr, mid, right);
        }
        return arr[mid];
    }
}
