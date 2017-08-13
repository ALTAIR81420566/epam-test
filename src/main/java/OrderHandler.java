import java.util.*;

public class OrderHandler {

    private Menu menu;
    private List<Order> orders =  new ArrayList<>();
    private boolean isFinish = false;
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

            System.out.println(MAINMENU);

            String answer = scanner.nextLine().trim().toUpperCase();

            if(answer.equals("E")){
                createOrder();
            } else if(answer.equals("R")){
                printGeneralReport();
            }else if(answer.equals("Q")){
                isFinish = true;
            }else{
                System.err.println(INCORRCOMMAND);
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
            System.out.println(HEADOFREPORT);
            StringBuilder reportStr = new StringBuilder();

            for(Order order : orders) {
                System.out.println(EMPLOYEE + order.getEmployeeName());
                for (Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()) {

                    String name = makeName(dish.getKey().getName());
                    reportStr.append(name).append("x").append(dish.getValue()).append("\n");

                }
                reportStr.append(TOTALCOST).append(order.getTotalCost()).append(RUB)
                .append("\n----------------");
                System.out.println(reportStr);
                reportStr.delete(0,reportStr.length());
            }
        }else {
            System.out.println(EMPTYLIST);
        }

    }

    private void printReportForDinningRoom(Order order) {
        System.out.println(ORDERINFO);
        StringBuilder reportStr =  new StringBuilder();
        StringBuilder nameBuilder = new StringBuilder();
        int i = 0;

        System.out.println(EMPLOYEE + order.getEmployeeName());
        for(Map.Entry<Dish, Integer> dish : order.getDishes().entrySet()){

            String name = makeName(dish.getKey().getName());

            reportStr.append(name).append(WEIGHT).append(dish.getKey()
                    .getWeight()).append(GRAMM).append("\t").append(COST)
                    .append(dish.getKey().getCost()).append(RUB).append("\t")
                    .append("x").append(dish.getValue()).append("\n");

            nameBuilder.delete(0,nameBuilder.length());
            i++;
        }
        reportStr.append(TOTALCOST).append(order.getTotalCost()).append(RUB);
        System.out.println(reportStr);

    }

    private void createOrder() {
        System.out.println(ENTERNAME);
        String employeeName;

        employeeName = scanner.nextLine();

        System.out.println(HELLO + employeeName + "\n");

        Order order =  new Order(employeeName);

        System.out.println(ORDERINSTRUCTION);

        boolean isContinue = true;

        while (isContinue){
            System.out.println(ENTHERCODE + "\n");
            String answer = scanner.nextLine().toUpperCase().trim();
            if (answer.equals("X")){
                if(!order.isEmpty()) {
                    orders.add(order);
                    System.out.println(THANKS);
                    printReportForDinningRoom(order);
                }else {
                    System.out.println(PITY);
                }
                isContinue = false;
                break;
            }
            try {
                int dishNumber = Integer.parseInt(answer);
                if (dishNumber >  -1 && dishNumber < menu.getDishes().size()){
                    int dishCount = 0;

                    boolean isIncorrectInput = true;

                    while (isIncorrectInput) {
                        System.out.println(DISHNUM+ "\n");
                        answer = scanner.nextLine();

                         dishCount = Integer.parseInt(answer);
                         if (dishCount > 0){
                             isIncorrectInput = false;
                         }else {
                             System.err.println(INCORRCOMMAND);
                         }
                    }

                    Dish dish = menu.getDishes().get(dishNumber);

                    order.addDish(dish, dishCount);
                }else{
                    System.err.println(INCORRCOMMAND);
                }
            }catch (Exception e){
                System.err.println(INCORRCOMMAND);
            }

        }


    }

    private void showMenu(){
        System.out.println( TODAYMENU + "\n");
        StringBuilder menuStr =  new StringBuilder();
        StringBuilder nameBuilder = new StringBuilder();
        int i = 0;

        for(Dish dish: menu.getDishes()){

            String name = makeName(dish.getName());

            menuStr.append(name).append(WEIGHT)
                    .append(dish.getWeight()).append(GRAMM).append("\t").append(COST)
                    .append(dish.getCost()).append(RUB).append("\t").append(ORDERCODE)
                    .append(i).append("\n");

            nameBuilder.delete(0,60);
            i++;
        }
        System.out.println(menuStr);
    }

}
