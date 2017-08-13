import java.util.ArrayList;
import java.util.List;

public class StandartMenu implements Menu {

    private static StandartMenu instance;

    private List<Dish> dishes = new ArrayList<>();

    private StandartMenu(){
        dishes.add(new Dish("Рулет с изюмом", 75, 15));
        dishes.add(new Dish("Пирог с повидлом", 60, 13));
        dishes.add(new Dish("Пирог с яблоками", 60, 13));
        dishes.add(new Dish("Салат изюминка", 100, 35));
        dishes.add(new Dish("Салат лукошко", 100, 40));
        dishes.add(new Dish("Салат Капустный с помидором", 100, 19));
        dishes.add(new Dish("Салат харчно с говядиной", 250, 31));
        dishes.add(new Dish("Салат куриный с грибами", 250, 34));
        dishes.add(new Dish("Салат болгарский вегетарианский", 250, 21));
        dishes.add(new Dish("Рыбное филе с помидоркой", 80, 54));
        dishes.add(new Dish("Печень по королевски", 150, 54));
        dishes.add(new Dish("Свинина запеченая \"По-гусарски\"", 80, 68));
        dishes.add(new Dish("Греча с маслом", 170, 17));
        dishes.add(new Dish("Макароны отварные", 170, 15));
        dishes.add(new Dish("Рис отварной с маслом", 170, 16));
    }

    @Override
    public List<Dish> getDishes() {
        return dishes;
    }

    public static StandartMenu getInstance(){
        if(instance == null){
            instance = new StandartMenu();
        }
        return instance;
    }
}
