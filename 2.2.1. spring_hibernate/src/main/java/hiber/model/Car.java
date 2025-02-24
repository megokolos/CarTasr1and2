package hiber.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String model;
    @NonNull
    private int series;

    private int price;

    @ToString.Exclude
    @OneToOne(mappedBy = "car")
    private User user;

}
