public class Main {

    public static void main(String[] args) {

       new OrderHandler(new Menu("menu.xml")).printReports("orders.xml");
    }
}
