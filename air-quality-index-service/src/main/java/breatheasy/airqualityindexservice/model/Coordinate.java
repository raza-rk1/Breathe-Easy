package breatheasy.airqualityindexservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coordinate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String state;
    private String name;
    private Double lat;
    private Double lon;


}
