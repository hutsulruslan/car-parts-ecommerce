package com.hutsdev.ecom.order.domain.user.aggregate;

import com.hutsdev.ecom.order.domain.user.vo.*;
import com.hutsdev.ecom.shared.error.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class User {

  private UserLastname lastname;
  private UserFirstname firstname;
  private UserEmail email;
  private UserPublicId userPublicId;
  private Instant lastModifiedDate;
  private Instant createdDate;
  private Instant lastSeen;
  private Set<Authority> authorities;
  private Long dbId;
  private UserAddress userAddress;

  public User(UserLastname lastname, UserFirstname firstname, UserEmail email, UserPublicId userPublicId, Instant lastModifiedDate, Instant createdDate, Instant lastSeen, Set<Authority> authorities, Long dbId, UserAddress userAddress) {
    this.lastname = lastname;
    this.firstname = firstname;
    this.email = email;
    this.userPublicId = userPublicId;
    this.lastModifiedDate = lastModifiedDate;
    this.createdDate = createdDate;
    this.lastSeen = lastSeen;
    this.authorities = authorities;
    this.dbId = dbId;
    this.userAddress = userAddress;
  }

  private void assertMandatoryFields() {
    Assert.notNull("lastname", lastname);
    Assert.notNull("firstname", firstname);
    Assert.notNull("email", email);
    Assert.notNull("authorities", authorities);
  }

  public void updateFromUser(User user) {
    this.email = user.email;
    this.firstname = user.firstname;
    this.lastname = user.lastname;
  }

  public void initFieldForSignup() {
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }

  public static User fromTokenAttribute(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
    UserBuilder userBuilder = UserBuilder.user();

    if(attributes.containsKey("preferred_email")) {
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if(attributes.containsKey("last_name")) {
      userBuilder.lastname(new UserLastname(attributes.get("last_name").toString()));
    }

    if(attributes.containsKey("first_name")) {
      userBuilder.firstname(new UserFirstname(attributes.get("first_name").toString()));
    }

    if(attributes.containsKey("last_signed_in")) {
      userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken
      .stream()
      .map(authority -> AuthorityBuilder.authority().authorityName(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);

    return userBuilder.build();
  }

  public UserLastname getLastname() {
    return lastname;
  }

  public UserFirstname getFirstname() {
    return firstname;
  }

  public UserEmail getEmail() {
    return email;
  }

  public UserPublicId getUserPublicId() {
    return userPublicId;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public Long getDbId() {
    return dbId;
  }

  public UserAddress getUserAddress() {
    return userAddress;
  }

  public Instant getLastSeen() {
    return lastSeen;
  }
}
