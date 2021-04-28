import interfaces.UnitCompany;

public class Group implements UnitCompany {
    private final String name;
    public Group(String name) {
        this.name = name;
    }
    public String getFullName() {
        return name;
    }
}
