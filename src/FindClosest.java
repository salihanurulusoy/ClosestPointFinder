import java.awt.geom.Point2D;

public class FindClosest {

    private PointPair closestPointPair;
    private final QuickSort quicksort = new QuickSort();

    /** Constructor
     *
     * @param points --> point array
     */
    public FindClosest(Point2D.Double[] points) {
        // Sort points by X coordinate
        quicksort.sort(points, 0, points.length - 1, "compareX");
        // Find the closest pair of points recursively
        this.closestPointPair = calculateClosestPointPair(points, 0, points.length - 1);
    }

    /** Get closest Point Pair
     *
     * @return closestPointPair
     */
    public PointPair getClosestPointPair() {
        return this.closestPointPair;
    }

    /** Main method for calculating and returning the closest point pair
     *
     * @param p --> point array
     * @param startIndex --> First index of p[]
     * @param lastIndex --> last index of p[]
     * @return the closest point pair
     */
    private PointPair calculateClosestPointPair(Point2D.Double[] p, int startIndex, int lastIndex) {
        // If there are few enough points, solve the problem directly
        if (lastIndex - startIndex <= 3) {
            return bruteForceClosest(p, startIndex, lastIndex);
        }
        // Find the middle index
        int mid = (lastIndex + startIndex) / 2;
        // Recursively find the closest pair in left and right halves
        PointPair leftPair = calculateClosestPointPair(p, startIndex, mid);
        PointPair rightPair = calculateClosestPointPair(p, mid + 1, lastIndex);
        // Determine the minimum pair among left and right
        PointPair minPair = (leftPair.getDistance() < rightPair.getDistance()) ? leftPair : rightPair;
        // Construct a strip of points around the middle point within the minimum distance
        Point2D.Double[] strip = new Point2D.Double[lastIndex - startIndex + 1];
        int stripIndex = 0;
        for (int i = startIndex; i <= lastIndex; i++) {
            if (Math.abs(p[i].getX() - p[mid].getX()) < minPair.getDistance()) {
                strip[stripIndex++] = p[i];
            }
        }
        // Find the closest pair within the strip and update minPair if necessary
        return stripClosest(strip, stripIndex, minPair);
    }

    /**
     * Brute force method to find the closest pair within a small set of points
     *
     * @param points --> array of points
     * @param startIndex --> First index of points[]
     * @param lastIndex --> last index of points[]
     * @return the closest pair of points
     */
    private PointPair bruteForceClosest(Point2D.Double[] points, int startIndex, int lastIndex) {
        PointPair minPair = null;
        double minDistance = Double.MAX_VALUE;

        for (int i = startIndex; i < lastIndex; i++) {
            for (int j = i + 1; j <= lastIndex; j++) {
                // Calculate the distance between points[i] and points[j]
                double distance = points[i].distance(points[j]);

                // If the calculated distance is less than the current minimum distance
                // update minPair and minDistance
                if (distance < minDistance) {
                    minDistance = distance;
                    minPair = new PointPair(points[i], points[j]);
                }
            }
        }

        return minPair;
    }


    /**
     * Find the closest pair of points within the strip
     *
     * @param strip --> array of points
     * @param size --> size of the strip
     * @param shortestLine --> shortest line calculated so far
     * @return the closest pair of points within the strip
     */
    private PointPair stripClosest(Point2D.Double[] strip, int size, PointPair shortestLine) {
        double minDistance = shortestLine.getDistance();
        PointPair minPair = shortestLine;

        // Initialize indices for the closest points
        int closestAIndex = -1;
        int closestBIndex = -1;

        // Iterate through the strip to find the closest pair
        for (int i = 0; i < size; ++i) {
            // Iterate through the subsequent points in the strip
            for (int j = i + 1; j < size; ++j) {
                // If the difference in Y coordinates exceeds minDistance, break the inner loop
                if (strip[j].getY() - strip[i].getY() >= minDistance) {
                    break;
                }

                // Calculate the distance between the points
                double distance = strip[i].distance(strip[j]);

                // If the calculated distance is less than minDistance, update minDistance and minPair
                if (distance < minDistance) {
                    minDistance = distance;
                    minPair = new PointPair(strip[i], strip[j]);
                    closestAIndex = i;
                    closestBIndex = j;
                }
            }
        }

        // Additional optimization: Check if there's a closer pair among adjacent points
        if (closestAIndex != -1 && closestBIndex != -1) {
            for (int i = closestAIndex; i <= closestBIndex; i++) {
                for (int j = i + 1; j <= closestBIndex; j++) {
                    double distance = strip[i].distance(strip[j]);
                    if (distance < minDistance) {
                        minDistance = distance;
                        minPair = new PointPair(strip[i], strip[j]);
                    }
                }
            }
        }

        return minPair;
    }

}
