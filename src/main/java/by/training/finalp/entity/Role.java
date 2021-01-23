package by.training.finalp.entity;

public enum Role {
    /**
     * Role 0.
     */
    GUEST("guest"),
    /**
     * Role 1.
     */
    MODERATOR("moderator"),
    /**
     * Role 2.
     */
    ADMINISTRATOR("administrator");
    /**
     * Name of the role, String.
     */
    private String name;

    private Role(final String gotName) {
        this.name = gotName;
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
     * Enum constant getter.
     *
     * @return Integer id.
     */
    public Integer getIdentity() {
        return ordinal();
    }

    /**
     * Name getter by id.
     *
     * @param identity Integer id.
     * @return String name.
     */
    public static Role getByIdentity(final Integer identity) {
        return Role.values()[identity];
    }
}
