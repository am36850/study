package com.project.batch.springparallelprocessing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "person", catalog = "spring_batch")
public class Person implements java.io.Serializable {

  private static final long serialVersionUID = 2790470855818509350L;
  private Long personId;
  private String firstName;
  private String lastName;
  private String email;
  private Integer age;

  public Person() {}

  public Person(String firstName, String lastName, String email, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "person_id", unique = true, nullable = false)
  public Long getPersonId() {
    return this.personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  @Column(name = "first_name", length = 40)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name", length = 40)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Column(name = "email", length = 100)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "age")
  public Integer getAge() {
    return this.age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

}
