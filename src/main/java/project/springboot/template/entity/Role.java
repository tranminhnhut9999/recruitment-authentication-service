package project.springboot.template.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "_role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<Account> accounts = new ArrayList<>();
}
