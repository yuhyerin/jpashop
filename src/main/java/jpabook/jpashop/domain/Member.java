package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // order 테이블에있는 member필드에 의해 매핑된것이라는 의미.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
