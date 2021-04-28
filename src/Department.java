import interfaces.NameReturnable;
import interfaces.UnitCompany;

public class Department implements NameReturnable, UnitCompany {
    private final String name;
    public Department(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
