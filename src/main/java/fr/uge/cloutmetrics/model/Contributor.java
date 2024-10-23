package fr.uge.cloutmetrics.model;

import java.util.Objects;

/**
 * Defines the git's contributor.
 */
public class Contributor {

  private Long id;
  private String name;
  private String email;

  /**
   * Empty contributor constructor for JSONserializer.
   */
  public Contributor() {
  }

  /**
   * Contributor's constructor.
   *
   * @param id The contributor's id.
   * @param name The contributor's name or pseudo.
   * @param email The contributor's email.
   */
  public Contributor(Long id,
                     String name,
                     String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  /**
   * Return the contributor's id.
   *
   * @return The contributor's id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Modify the contributor's id.
   *
   * @param id contributor's id.
   */
  public void setId(Long id) {
    Objects.requireNonNull(id, "id cannot be null");
    this.id = id;
  }

  /**
   * Return the contributor's name.
   *
   * @return The contributor's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Modify the contributor's name.
   *
   * @param name The contributor's name.
   */
  public void setName(String name) {
    Objects.requireNonNull(name, "name cannot be null");
    this.name = name;
  }

  /**
   * Return the contributor's email.
   *
   * @return The contributor's email.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Modify the contributor's email.
   *
   * @param email The contributor's email.
   */
  public void setEmail(String email) {
    Objects.requireNonNull(email, "email cannot be null");
    this.email = email;
  }

}
