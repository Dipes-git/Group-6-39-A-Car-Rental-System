package controller;

import dao.CarDao;
import dao.CarDaoImpl;
import dao.BrandDao;
import dao.BrandDaoImpl;
import model.Car;
import model.Brand;
import view.CarPanel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller class coordinating interactions between dashboards/panels (view layer) 
 * and database fleet operations (DAO layer).
 * Basic implementation with loading and populating operations.
 * 
 * @author dipes
 */
public class CarController {

    private final CarDao carDao;
    private final BrandDao brandDao;

    public CarController() {
        this.carDao = new CarDaoImpl();
        this.brandDao = new BrandDaoImpl();
    }

    public void populateBrandCombo(CarPanel view) {
        if (view == null) return;
        List<Brand> brands = brandDao.getAllBrands();
        view.getBrandCombo().removeAllItems();
        for (Brand b : brands) {
            view.getBrandCombo().addItem(b);
        }
    }

    public Car getCarById(int id) {
        return carDao.getCarById(id);
    }

    public void loadAdminCarTable(CarPanel view) {
        loadAdminCarTable(view, null);
    }

    public void loadAdminCarTable(CarPanel view, String brandFilter) {
        if (view == null) return;
        List<Car> cars = view.isAdminMode() ? carDao.getAllCars() : carDao.getAvailableCars();
        DefaultTableModel model = (DefaultTableModel) view.getCarTable().getModel();
        model.setRowCount(0);
        for (Car car : cars) {
            if (brandFilter != null && !brandFilter.isEmpty() && !car.getBrand().equalsIgnoreCase(brandFilter)) {
                continue;
            }
            model.addRow(new Object[]{
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getCategory(),
                String.format("$%.2f", car.getPricePerDay()),
                car.getStatus()
            });
        }
    }

    public boolean handleAddCar(CarPanel view) {
        return false;
    }

    public boolean handleUpdateCar(CarPanel view) {
        return false;
    }

    public boolean handleDeleteCar(CarPanel view) {
        return false;
    }

    public void handleRentRequest(CarPanel view) {
    }
}
