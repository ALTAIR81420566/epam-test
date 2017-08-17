import java.util.*;

public class OrderHandler {

    private Menu menu;
    private List<Order> orders =  new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private final String HELLO = "Hello! ";
    private final String MAINMENU = "\n\nTo make an order, enter    E\n"  +
            "\nTo print a report, enter   R\n" +
            "\nTo exit the program, enter  Q\n";
    private final String INCORRCOMMAND = "Incorrect command";
    private final String EMPLOYEE = "Employee: ";
    private final String TODAYMENU = "Today in the menu: ";
    private final String PITY = "It's a pity, you didn't order anything";
    private final String DISHNUM = "Enter the number of dish";
    private final String RUB = "rub";
    private final String THANKS = "Thanks for your order!";
    private final String COST = "cost: ";
    private final String WEIGHT = " weight: ";
    private final String GRAMM = " gramm";
    private final String ORDERCODE = "Order code: ";
    private final String ORDERINFO = " Information about order: \n";
    private final String TOTALCOST = "Total cost of the order: ";
    private final String EMPTYLIST = "List of orders is empty!";
    private final String ENTHERCODE = "Enter a code";
    private final String ENTERNAME = "Please, enter your name\n";
    private final String HEADOFREPORT = "Information about all orders for " +
            "distribution of ordered dishes to employees:\n";
    private final String ORDERINSTRUCTION ="Enter the order code of dishes one at a time. When done, enter X\n";

    public void start(){
        System.out.println(HELLO + "\n");
        showMenu();

        handler();

        scanner.close();
    }

    public OrderHandler(Menu menu){
        this.menu = menu;
    }

    private void handler(){
        String answer;

        do{
            System.out.println(MAINMENU);

            answer = scanner.nextLine().trim().toUpperCase();

            if(answer.equals("E")){
                createOrder();
            } else if(answer.equals("R")){
                printGeneralReport();
            }else if(!answer.equals("Q")){
                System.err.println(INCORRCOMMAND);
            }
        }
        while (!answer.equals("Q"));

    }

    private void printGeneralReport() {
        if(!orders.isEmpty()) {
            System.out.println(HEADOFREPORT);

            for(Order order : orders) {
                System.out.println(EMPLOYEE + order.getEmployeeName());
                for (Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()) {

                    System.out.printf("%-60s x%d\n", dish.getKey().getName(),dish.getValue());

                }
                System.out.printf("%s%s%s\n---------------\n", TOTALCOST, order.getTotalCost(),RUB);
            }
        }else {
            System.out.println(EMPTYLIST);
        }

    }

    private void printReportForDinningRoom(Order order) {
        System.out.println(ORDERINFO+"\n");

        System.out.println(EMPLOYEE + order.getEmployeeName());
        for(Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()){

            System.out.printf("%-60s %s %-4d%s\t %s %-3d%s\t x%d\n", dish.getKey().getName(),
                    WEIGHT,dish.getKey().getWeight(),GRAMM,COST,dish.getKey().getCost(),RUB,dish.getValue());

        }
        System.out.printf("%s%s%s", TOTALCOST, order.getTotalCost(),RUB);
    }

    private void createOrder() {
        System.out.println(ENTERNAME);
        String employeeName;

        employeeName = scanner.nextLine();

        System.out.println(HELLO + employeeName + "\n");

        Order order =  new Order(employeeName);

        System.out.println(ORDERINSTRUCTION);

        String answer;

        do{
            System.out.println(ENTHERCODE + "\n");
            answer = scanner.nextLine().toUpperCase().trim();
            if (answer.equals("X")){
                if(!order.isEmpty()) {
                    orders.add(order);
                    System.out.println(THANKS);
                    printReportForDinningRoom(order);
                }else {
                    System.out.println(PITY);
                }
            }else {
                try {
                    int dishNumber = Integer.parseInt(answer);
                    if (dishNumber > -1 && dishNumber < menu.getDishes().size()) {
                        int dishCount = 0;

                        do{
                            System.out.println(DISHNUM + "\n");
                            answer = scanner.nextLine();

                            dishCount = Integer.parseInt(answer);
                            if (dishCount <= 0) {
                                System.err.println(INCORRCOMMAND);
                            }
                        }
                        while (dishCount <= 0);

                        Dish dish = menu.getDishes().get(dishNumber);

                        order.addDish(dish, dishCount);
                    } else {
                        System.err.println(INCORRCOMMAND);
                    }
                } catch (Exception e) {
                    System.err.println(INCORRCOMMAND);
                }
            }
        }
        while (!answer.equals("X"));

    }

    private void showMenu(){
        System.out.println( TODAYMENU + "\n");
        int i = 0;

        for(Dish dish: menu.getDishes()){

            System.out.printf("%-60s %s %-4d%s\t %s %-3d%s\t %s %d\n", dish.getName(),
                    WEIGHT,dish.getWeight(),GRAMM,COST,dish.getCost(),RUB,ORDERCODE,i);
            i++;
        }
    }

}
