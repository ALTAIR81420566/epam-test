import java.util.ArrayList;
import java.util.List;

public class Order {

    private String employeeName;
    private int totalCost;
    private List<Dish> dishes =  new ArrayList<>();

    public Order(String employeeName){
        this.employeeName = employeeName;
    }

    public void addDish(Dish dish){
        dishes.add(dish);
        totalCost += dish.getCost();
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }
    public List<Dish> getDishes() {
        return dishes;
    }
    public int getTotalCost() {
        return totalCost;
    }

    public String getEmployeeName() {
        return employeeName;
    }

}
