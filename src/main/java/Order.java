import java.util.HashMap;

public class Order {

    private String employeeName;
    private int totalCost;
    private HashMap<Dish, Integer> dishes =  new HashMap<>();

    public Order(String employeeName){
        this.employeeName = employeeName;
    }

    public void addDish(Dish dish, int count){
        dishes.put(dish,count);
        totalCost += dish.getCost() * count;
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }
    public HashMap<Dish, Integer> getDishes() {
        return dishes;
    }
    public int getTotalCost() {
        return totalCost;
    }

    public String getEmployeeName() {
        return employeeName;
    }

}
