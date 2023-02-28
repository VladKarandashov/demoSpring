package ru.abradox.demospring.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    @DateTimeFormat(pattern="YYYY-MM-dd")
    private LocalDate birthday;
    private String profession;
    @ManyToMany(mappedBy = "people", cascade = {CascadeType.ALL})
    @JsonBackReference
    private List<Film> films;

    public Person(String fullName) {
        this.fullName = fullName;
    }
}
