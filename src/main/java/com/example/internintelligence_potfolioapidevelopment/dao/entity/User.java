package com.example.internintelligence_potfolioapidevelopment.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String title;
    private String about;
    private String phone;
    private LocalDate birthDay;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    //lazy olanda niye error verir?
//    org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.example.internintelligence_potfolioapidevelopment.dao.entity.User.authorities: could not initialize proxy - no Session
//    at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:636) ~[hibernate-core-6.5.3.Final.jar:6.5.3.Final]
//    at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:219) ~[hibernate-core-6.5.3.Final.jar:6.5.3.Final]
//    at org.hibernate.collection.spi.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:615) ~[hibernate-core-6.5.3.Final.jar:6.5.3.Final]
//    at org.hibernate.collection.spi.AbstractPersistentCollection.read(AbstractPersistentCollection.java:138) ~[hibernate-core-6.5.3.Final.jar:6.5.3.Final]
//    at org.hibernate.collection.spi.PersistentBag.iterator(PersistentBag.java:369) ~[hibernate-core-6.5.3.Final.jar:6.5.3.Final]
//    at java.base/java.util.Spliterators$IteratorSpliterator.estimateSize(Spliterators.java:1959) ~[na:na]

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Authority> authorities;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream() // Stream through the authorities
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name())) // Convert each Authority to GrantedAuthority
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
