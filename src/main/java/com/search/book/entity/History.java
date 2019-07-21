package com.search.book.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 200, nullable = false)
    private String keyword;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getHistories().remove(this);
        }
        this.user = user;
        this.user.addUserKeyword(this);
    }

}
