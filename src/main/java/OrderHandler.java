import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class OrderHandler {

    private Menu menu;

    private final String EMPLOYEE = "Employee: ";
    private final String RUB = "rub";
    private final String COST = "cost: ";
    private final String WEIGHT = " weight: ";
    private final String GRAMM = " gramm";
    private final String ORDERINFO = "\n Information about order: ";
    private final String TOTALCOST = "Total cost of the order: ";
    private final String EMPTYLIST = "List of orders is empty!";
    private final String HEADOFREPORT = "\n\nInformation about all orders for " +
            "distribution of ordered dishes to employees:\n";

    public void start(){
        List<Order> orders =  parse("orders.xml");
        for (Order order : orders){
            printReportForDinningRoom(order);
        }
        printGeneralReport(orders);
    }

    private List<Order> parse(String path) {
        List<Order> orders = new ArrayList<>();
        try {
            List<Dish> dishes = menu.getDishes();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document document = documentBuilder.parse(path);

            Node root = document.getDocumentElement();

            NodeList ordersNode = root.getChildNodes();
            for (int i = 0; i < ordersNode.getLength(); i++) {
                Node orderNode = ordersNode.item(i);
                Order order = null;
                if (orderNode.getNodeType() != Node.TEXT_NODE) {
                    NodeList orderProps = orderNode.getChildNodes();
                    for (int j = 0; j < orderProps.getLength(); j++) {
                        Node orderProp = orderProps.item(j);
                        if (orderProp.getNodeType() != Node.TEXT_NODE) {
                            if (orderProp.getNodeName().equals("employee")) {
                                order = new Order(orderProp.getTextContent());
                            } else {
                                NodeList ordersDish = orderProp.getChildNodes();
                                for (int k = 0; k < ordersDish.getLength(); k++) {
                                    Node orderDish = ordersDish.item(k);
                                    for (Dish dish : dishes) {
                                        if (dish.getName().equals(orderDish.getTextContent())) {
                                            order.addDish(dish);
                                        }
                                    }
                                }
                                orders.add(order);
                            }
                        }
                    }
                }

            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return orders;
    }

    public OrderHandler(Menu menu){
        this.menu = menu;
    }

    private void printGeneralReport(List<Order> orders) {
        if(!orders.isEmpty()) {
            System.out.println(HEADOFREPORT);

            for(Order order : orders) {
                System.out.println(EMPLOYEE + order.getEmployeeName());
                for (Dish dish : order.getDishes()) {

                    System.out.printf("%-60s \n", dish.getName());

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
        for(Dish dish : order.getDishes()){

            System.out.printf("%-60s %s %-4d%s\t %s %-3d%s\t \n", dish.getName(),
                    WEIGHT,dish.getWeight(),GRAMM,COST,dish.getCost(),RUB);

        }
        System.out.printf("%s%s%s\n", TOTALCOST, order.getTotalCost(),RUB);
    }

}
