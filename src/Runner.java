import custom_exceptions.ImpossibleModifyException;
import custom_exceptions.ImpossibleSwitchModeException;
import interfaces.NameReturnable;
import interfaces.UnitCompany;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws ImpossibleModifyException, ImpossibleSwitchModeException {
        Graph<UnitCompany> graph = new Graph<>();
        initialization(graph);

        System.out.println("Выберите вариант поиска: \n1.В глубину \n2.В ширину");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> printSearch(graph,SearchOptions.DFS);
            case 2 -> printSearch(graph,SearchOptions.BFC);
            default -> System.out.println("Выбран некорректынй вариант");
        }
    }
    public static void printSearch(Graph<UnitCompany> graph,SearchOptions s){
        if (s == SearchOptions.BFC)
            graph.changeDefaultSearch(SearchOptions.BFC);
        for (UnitCompany vertex : graph)
            printHelper(vertex);
    }
    public static void printHelper(UnitCompany v){
        if (v instanceof NameReturnable) {
            System.out.println(((NameReturnable) v).getName());
        }
        else if (v instanceof Group)
            System.out.println(((Group) v).getFullName());
    }
    public static void initialization(Graph<UnitCompany> graph) throws ImpossibleSwitchModeException, ImpossibleModifyException {
        graph.onlyMod();

        UnitCompany vA = new Department("A!");
        UnitCompany vB = new Department("B!");
        UnitCompany vC = new Department("C!");
        UnitCompany vD = new Department("D!");
        UnitCompany vE = new Division("E?");
        UnitCompany vF = new Division("F?");
        UnitCompany vG = new Division("G?");
        UnitCompany vH = new Group("H+");
        UnitCompany vI = new Group("I+");

        graph.addVertex(vA);
        graph.addVertex(vB);
        graph.addVertex(vC);
        graph.addVertex(vD);
        graph.addVertex(vE);
        graph.addVertex(vF);
        graph.addVertex(vG);
        graph.addVertex(vH);
        graph.addVertex(vI);

        graph.addEdge(vA,vB);
        graph.addEdge(vA,vC);
        graph.addEdge(vA,vD);
        graph.addEdge(vB,vE);
        graph.addEdge(vB,vF);
        graph.addEdge(vF,vI);
        graph.addEdge(vI,vH);
        graph.addEdge(vC,vG);
        graph.addEdge(vG,vH);
        graph.addEdge(vD,vH);
    }
}
