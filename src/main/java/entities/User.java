package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String userName;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String userPass;

  @ManyToMany(cascade = CascadeType.PERSIST)
  private Set<Role> roleSet = new HashSet<>();

  public List<String> getRolesAsStrings() {
    if (roleSet.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleSet.forEach((role) -> rolesAsStrings.add(role.getRoleName()));
    return rolesAsStrings;
  }

  public User() {}

   public boolean verifyPassword(String pw){
        return(BCrypt.checkpw(pw, userPass));
    }

  public User(String userName, String userPass) {
    this.userName = userName;
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }

  public Set<Role> getRoleList() {
    return roleSet;
  }

  public void setRoleList(Set<Role> roleList) {
    this.roleSet = roleList;
  }

  public void addRole(String role) {
    Role newRole = new Role(role);
    this.roleSet.add(newRole);
  }

  public void addRole(Role userRole) {
    this.roleSet.add(userRole);
  }
}
