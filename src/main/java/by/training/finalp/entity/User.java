package by.training.finalp.entity;

public class User extends Entity {

    private String login;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String address;

    /**
     * Login getter.
     *
     * @return String login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Login setter.
     *
     * @param gotLogin
     */
    public void setLogin(final String gotLogin) {
        this.login = gotLogin;
    }

    /**
     * Password getter.
     *
     * @return String password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password setter.
     *
     * @param gotPassword String password.
     */
    public void setPassword(final String gotPassword) {
        this.password = gotPassword;
    }

    /**
     * Role getter.
     *
     * @return Role role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Role setter.
     *
     * @param gotRole Role role.
     */
    public void setRole(final Role gotRole) {
        this.role = gotRole;
    }

    /**
     * Name getter.
     *
     * @return String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     *
     * @param gotName String name.
     */
    public void setName(final String gotName) {
        this.name = gotName;
    }

    /**
     * Surname getter.
     *
     * @return String surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Surname setter.
     *
     * @param gotSurname String surname.
     */
    public void setSurname(final String gotSurname) {
        this.surname = gotSurname;
    }

    /**
     * Mail getter.
     *
     * @return String mail.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Mail setter.
     *
     * @param gotMail Mail mail.
     */
    public void setMail(final String gotMail) {
        this.mail = gotMail;
    }

    /**
     * Phone getter.
     *
     * @return String phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Phone setter.
     *
     * @param gotPhone String phone.
     */
    public void setPhone(final String gotPhone) {
        this.phone = gotPhone;
    }

    /**
     * Address getter.
     *
     * @return String address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter.
     *
     * @param gotAddress String address.
     */
    public void setAddress(final String gotAddress) {
        this.address = gotAddress;
    }
}
