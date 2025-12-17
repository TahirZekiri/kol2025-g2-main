package mk.ukim.finki.wp.kol2025g2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class SkiResort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;

    @OneToMany(mappedBy = "skiResort")
    private List<SkiSlope> skiSlopes;

    public SkiResort() {
    }

    public SkiResort(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SkiSlope> getSkiSlopes() {
        return skiSlopes;
    }

    public void setSkiSlopes(List<SkiSlope> skiSlopes) {
        this.skiSlopes = skiSlopes;
    }
}
