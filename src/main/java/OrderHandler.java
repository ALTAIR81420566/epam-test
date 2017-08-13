import java.util.*;

public class OrderHandler {

    private Menu menu;
    private List<Order> orders =  new ArrayList<>();
    private boolean isFinish = false;
    private Scanner scanner = new Scanner(System.in);

    public void start(){
        System.out.println("Здравствуйте\n");
        showMenu();

        while (isFinish == false) {
            handler();
        }
        scanner.close();
    }

    public OrderHandler(Menu menu){
        this.menu = menu;
    }

    private void handler(){

        while (!isFinish) {

            System.out.println("\n\nЧтобы сделать заказ введите   Е\n" +
                    "Чтобы вывести отчет введите   R\n" +
                    "Для выхода из программы введите   Q\n");

            String answer = scanner.nextLine().trim().toUpperCase();

            if(answer.equals("E")){
                createOrder();
            } else if(answer.equals("R")){
                printGeneralReport();
            }else if(answer.equals("Q")){
                isFinish = true;
            }else{
                System.err.println("Неверная команда");
                continue;
            }

        }


    }

    //Makes a name of 60 characters long, for a even showing
    private String makeName(String name){
        StringBuilder nameBuilder = new StringBuilder();
        if (name.length() > 60) {
            name = name.substring(0, 55);
            name += "...";
        }

        nameBuilder.append(name);
        while (nameBuilder.length() < 60) {
            nameBuilder.append(" ");
        }
        return  nameBuilder.toString();
    }


    private void printGeneralReport() {
        if(!orders.isEmpty()) {
            System.out.println("Информация о всех заказах для раздачи заказанных блюд сотрудникам: \n");
            StringBuilder reportStr = new StringBuilder();

            for(Order order : orders) {
                System.out.println("Заказчик: " + order.getEmployeeName());
                for (Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()) {

                    String name = makeName(dish.getKey().getName());
                    reportStr.append(name).append("x").append(dish.getValue()).append("\n");

                }
                reportStr.append("Общая стоимость заказа: ").append(order.getTotalCost()).append("руб")
                .append("\n----------------");
                System.out.println(reportStr);
                reportStr.delete(0,reportStr.length());
            }
        }else {
            System.out.println("Список заказов пуст!");
        }

    }

    private void printReportForDinningRoom(Order order) {
        System.out.println("Информация о заказе: \n");
        StringBuilder reportStr =  new StringBuilder();
        StringBuilder nameBuilder = new StringBuilder();
        int i = 0;

        System.out.println("Заказчик: " + order.getEmployeeName());
        for(Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()){

            String name = makeName(dish.getKey().getName());

            reportStr.append(nameBuilder).append(" вес: ").append(dish.getKey()
                    .getWeight()).append(" гр.").append("\tцена: ")
                    .append(dish.getKey().getCost()).append("руб.\t")
                    .append("x").append(dish.getValue()).append("\n");

            nameBuilder.delete(0,nameBuilder.length());
            i++;
        }
        reportStr.append("Общая стоимость заказа: ").append(order.getTotalCost()).append("руб");
        System.out.println(reportStr);

    }

    private void createOrder() {
        System.out.println("Пожалуйста, представтесь\n");
        String employeeName;

        employeeName = scanner.nextLine();

        System.out.println("Добрый день " + employeeName + "\n");

        Order order =  new Order(employeeName);

        System.out.println("Вводите код заказа блюд по одному. Когда закончите, введите X\n");

        boolean isContinue = true;

        while (isContinue){
            System.out.println("Введите код\n");
            String answer = scanner.nextLine().toUpperCase().trim();
            if (answer.equals("X")){
                if(!order.isEmpty()) {
                    orders.add(order);
                    System.out.println("Спасибо за заказ!");
                    printReportForDinningRoom(order);
                }else {
                    System.out.println("Очень жаль, вы ничего не заказали");
                }
                isContinue = false;
            }
            try {
                int dishNumber = Integer.parseInt(answer);
                if (dishNumber >  -1 && dishNumber < menu.getDishes().size()){
                    int dishCount = 0;

                    boolean isIncorrectInput = true;

                    while (isIncorrectInput) {
                        System.out.println("Введите количество блюд\n");
                        answer = scanner.nextLine();

                         dishCount = Integer.parseInt(answer);
                         if (dishCount > 0){
                             isIncorrectInput = false;
                         }else {
                             System.err.println("Неверное значение!!!");
                         }
                    }

                    Dish dish = menu.getDishes().get(dishNumber);

                    order.addDish(dish, dishCount);
                }else{
                    System.err.println("Неверное значение!");
                }
            }catch (Exception e){
                System.err.println("Введите числовое значение");
            }

        }


    }

    private void showMenu(){
        System.out.println("Сегодня в меню: \n");
        StringBuilder menuStr =  new StringBuilder();
        StringBuilder nameBuilder = new StringBuilder();
        int i = 0;

        for(Dish dish: menu.getDishes()){

            String name = makeName(dish.getName());

            menuStr.append(nameBuilder).append(" вес: ").append(dish.getWeight()).append(" гр.").append("\tцена: ")
                    .append(dish.getCost()).append("руб.\t").append("код заказа: ").append(i).append("\n");
            nameBuilder.delete(0,60);
            i++;
        }
        System.out.println(menuStr);
    }

}
