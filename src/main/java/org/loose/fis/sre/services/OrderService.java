package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.TypeAlreadyExistsException;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.model.User;

import java.util.ArrayList;
import java.util.Objects;

import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class OrderService {

    private static ObjectRepository<Order> orderRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("orders.db").toFile())
                .openOrCreate("test", "test");

        orderRepository = database.getRepository(Order.class);
    }

    public static void addOrder(User client, ProductType productType, Material material, String message) {
        orderRepository.insert(new Order(client,productType,material,message));
    }

    public static ArrayList<Order> orders() {
        ArrayList<Order> list = new ArrayList<>();
        for(Order order : orderRepository.find()) {
            list.add(order);
        }
        return list;
    }


}
