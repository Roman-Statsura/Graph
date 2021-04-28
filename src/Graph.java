import custom_exceptions.ImpossibleModifyException;
import custom_exceptions.ImpossibleSwitchModeException;

import java.util.*;

public class Graph<T> implements Iterable<T>{
    private static final int UNLOCK_NUM = 0;
    private static final int ONLY_MOD_NUM = 1;
    private static final int LOCK_NUM = 2;

    private int possibilityMod;
    private SearchOptions defaultSearch = SearchOptions.DFS;
    private T rootVertex;

    private final Map<T, LinkedList<T>> listAdj = new HashMap<>();

    private final Deque<T> deque;
    private final Queue<T> queue;

    public Graph(){
        deque = new ArrayDeque<>();
        queue = new ArrayDeque<>();
    }
    public void changeDefaultSearch(SearchOptions s){
        defaultSearch = s;
    }
    public void addVertex(T vertex) throws ImpossibleModifyException {
        checkAdd();
        if (rootVertex == null)
            rootVertex = vertex;
        listAdj.put(vertex,new LinkedList<>());
    }
    //Удялеяет веришну, ищет в списке смежных вершин ребра с данной вершиной и удаляет их тоже
    public void deleteVertex(T vertex) throws ImpossibleModifyException {
        checkDelete();
        listAdj.remove(vertex);
        for (LinkedList<T> list : listAdj.values()){
            list.remove(vertex);
        }
    }
    public void addEdge(T start,T end) throws ImpossibleModifyException {
        checkAdd();
        listAdj.get(start).add(end);
    }
    //Удаляет ребро
    public void deleteEdge(T start,T end) throws ImpossibleModifyException {
        checkDelete();
        listAdj.get(start).remove(end);
    }
    private T checkVertex(T vertex,ArrayList<T> visitedVertex){
        return listAdj.get(vertex).stream().filter(x -> !visitedVertex.contains(x)).findFirst().orElse(null);
    }
    public List<T> dfs(T v){
        List<T> vert = new ArrayList<>();
        ArrayList<T> visitedVertex = new ArrayList<>();

        vert.add(v);
        visitedVertex.add(v);//Добавляем в список посещенных вершин
        deque.push(v);

        while (!deque.isEmpty()){
            T neigh = checkVertex(deque.peek(),visitedVertex);
            if (neigh == null){
                deque.pop();
            }
            else {
                vert.add(neigh);
                visitedVertex.add(neigh);//Добавляем в список посещенных вершин
                deque.push(neigh);
            }
        }
        return vert;
    }
    public List<T> bfc(T v){
        List<T> vert = new ArrayList<>();
        ArrayList<T> visitedVertex = new ArrayList<>();

        vert.add(v);
        visitedVertex.add(v);//Добавляем в список посещенных вершин
        queue.add(v);
        T vertex;

        while (!queue.isEmpty()){
            T qVertex = queue.remove();
            while ((vertex = checkVertex(qVertex,visitedVertex))!= null){
                vert.add(vertex);

                visitedVertex.add(vertex);//Добавляем в список посещенных вершин
                queue.add(vertex);
            }
        }
        return vert;
    }
    public void lock(){
        possibilityMod = LOCK_NUM;
    }
    public void onlyMod() throws ImpossibleSwitchModeException {
        if (possibilityMod == LOCK_NUM)
            throw new ImpossibleSwitchModeException("Нельзя перейте в этот режим из режима 'lock'");
        possibilityMod = ONLY_MOD_NUM;
    }
    public void unlock(){
        possibilityMod = UNLOCK_NUM;
    }
    private void checkAdd() throws ImpossibleModifyException {
        if (possibilityMod == LOCK_NUM)
            throw new ImpossibleModifyException("Операция добавления заблокированна");
    }
    private void checkDelete() throws ImpossibleModifyException {
        if (possibilityMod == LOCK_NUM || possibilityMod == ONLY_MOD_NUM)
            throw new ImpossibleModifyException("Операция удаления заблокированна");
    }

    @Override
    public Iterator<T> iterator() {
        List<T> list;
        if (defaultSearch == SearchOptions.DFS){
            list = dfs(rootVertex);
        }else {
            list = bfc(rootVertex);
        }
        return new GraphIterator(list);
    }
    private class GraphIterator implements Iterator<T>{
        private final List<T> data;
        private int index;

        public GraphIterator(List<T> data){
            index = 0;
            this.data = data;
        }
        @Override
        public boolean hasNext() {
            return data.size()!= index;
        }
        @Override
        public T next() {
            if(hasNext()) {
                return data.get(index++);
            } else {
                throw new NoSuchElementException("There are no elements size = " + data.size());
            }
        }
    }
}
