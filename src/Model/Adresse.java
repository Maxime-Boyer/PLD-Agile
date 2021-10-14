package Model;

import java.util.List;

public class Adresse {
    private float latitude;
    private float longitude;
    private List<Segment> segmentSortant;

    public Adresse(float latitude, float longitude, List<Segment> segmentSortant) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.segmentSortant = segmentSortant;
    }
}
