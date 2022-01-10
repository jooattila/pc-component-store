package edu.bbte.idde.jaim1826.desktop;

import edu.bbte.idde.jaim1826.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.jaim1826.backend.dao.ComponentDao;
import edu.bbte.idde.jaim1826.backend.dao.SellerDao;
import edu.bbte.idde.jaim1826.backend.model.Component;
import edu.bbte.idde.jaim1826.backend.model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Collection;

public class ComponentUI extends JFrame {

    static final Logger LOGGER = LoggerFactory.getLogger(ComponentUI.class);
    private static JFrame mainWindow;
    private static final SellerDao SELLER_DAO = AbstractDaoFactory.getDaoFactory().getSellerDao();
    private static final ComponentDao COMPONENT_DAO = AbstractDaoFactory.getDaoFactory().getComponentDao();
    private static JScrollPane jScrollPane;
    private static JButton button;

    public static void main(String[] args) {

        SELLER_DAO.create(new Seller("Emag", "Romania", true));
        SELLER_DAO.create(new Seller("PcGarage", "Romania", true));
        SELLER_DAO.create(new Seller("Nagy Jozsef", "Romania", false));

        COMPONENT_DAO.create(new Component("Video card", "GeForce RTX 2060", 2019, 3500.0, true, 1L));
        COMPONENT_DAO.create(new Component("Motherboard", "\n"
                + "Gigabyte GA-A320M-S2H", 2017, 500.0, false, 1L));
        COMPONENT_DAO.create(new Component("Memory RAM", "HyperX Fury RGB 16GB", 2020, 900.0, true, 1L));

        Component component = new Component("Memory RAM", "HyperX Fury RGB 32GB", 2020, 900.0, false, 1L);

        COMPONENT_DAO.create(component);
        component.setId(4L);
        COMPONENT_DAO.update(component);

        COMPONENT_DAO.findAll();

        COMPONENT_DAO.delete(component.getId());

        initialize();

        button.addActionListener(e -> {
            jScrollPane.setViewportView(getTable());
            mainWindow.repaint();
        });

    }

    public static void initialize() {
        mainWindow = new JFrame("Components");
        mainWindow.setBounds(500, 200, 500, 500);
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        button = new JButton("Get all components!");
        panel.add(button);
        jScrollPane = new JScrollPane(new JTable(0, 0));
        panel.add(jScrollPane);
        mainWindow.add(panel);
        mainWindow.setVisible(true);
    }

    public static JTable getTable() {
        Collection<Component> components = COMPONENT_DAO.findAll();
        String[] tableHeader = {"ID", "Category", "Model", "Release year", "Price(Lei)", "Availability"};
        String[][] data = new String[components.size()][6];

        int index = 0;
        for (Component dummy : components) {
            data[index][0] = dummy.getId().toString();
            data[index][1] = dummy.getCategory();
            data[index][2] = dummy.getModel();
            data[index][3] = dummy.getReleaseYear().toString();
            data[index][4] = dummy.getPrice().toString();
            data[index][5] = dummy.getAvailability().toString();
            index++;
        }
        return new JTable(data, tableHeader);
    }
}
