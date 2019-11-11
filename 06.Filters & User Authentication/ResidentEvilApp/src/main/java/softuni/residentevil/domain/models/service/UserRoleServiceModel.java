package softuni.residentevil.domain.models.service;

public class UserRoleServiceModel extends BaseServiceModel {

    private String authority;

    public UserRoleServiceModel() {
    }

    public UserRoleServiceModel(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
