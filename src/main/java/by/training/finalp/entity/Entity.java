package by.training.finalp.entity;

public abstract class Entity {

    private Integer id;

    public void setId(final Integer gotId) {
        id = gotId;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj != this) {
                if (obj.getClass() == getClass() && id != null) {
                    return id.equals(((Entity) obj).id);
                }
                return false;
            }
            return true;
        }
        return false;
    }
}
