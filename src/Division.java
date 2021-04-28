import interfaces.NameReturnable;
import interfaces.UnitCompany;

public class Division implements NameReturnable, UnitCompany {
    private final String name;
    public Division(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
