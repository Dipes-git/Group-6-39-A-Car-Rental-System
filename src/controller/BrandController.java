package controller;

import dao.BrandDao;
import model.Brand;
import view.BrandPanel;
import view.AdminDashboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Controller class coordinating interactions between BrandPanel
 * and database brand persistent transactions (DAO layer).
 * 
 * @author dipes
 */
public class BrandController {

    private final BrandDao brandDao;
    private final BrandPanel view;

    public BrandController(BrandPanel view) {
        this.brandDao = new BrandDao();
        this.view = view;
        
        // Initial load
        loadBrandTable();
        
        // Wire listeners
        this.view.getBtnAddBrand().addActionListener(new AddBrandListener());
        this.view.getBtnUpdateBrand().addActionListener(new UpdateBrandListener());
        this.view.getBtnDeleteBrand().addActionListener(new DeleteBrandListener());
        this.view.getBtnBrowseLogo().addActionListener(new BrowseLogoListener());
        this.view.getBtnRefreshBrand().addActionListener(new RefreshBrandListener());

        // Wire list selection model selection listener to keep logic in Controller (strict MVC)
        this.view.getBrandsTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = this.view.getBrandsTable().getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int brandId = Integer.parseInt(this.view.getBrandsTable().getValueAt(selectedRow, 0).toString());
                        Brand brand = brandDao.getBrandById(brandId);
                        if (brand != null) {
                            this.view.populateEditorFields(brand);
                        }
                    } catch (Exception ex) {
                        System.err.println("[BrandController] Row selection mapping error: " + ex.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Helper default constructor for cross-controller updates.
     */
    public BrandController() {
        this.brandDao = new BrandDao();
        this.view = null;
    }

    /**
     * Fetches all brands and populates the view's Brands JTable.
     */
    public void loadBrandTable() {
        if (view == null) return;

        List<Brand> brands = brandDao.getAllBrands();
        DefaultTableModel model = (DefaultTableModel) view.getBrandsTable().getModel();
        model.setRowCount(0); // Clear table rows

        for (Brand b : brands) {
            model.addRow(new Object[]{
                b.getId(),
                b.getName(),
                b.getLogoPath()
            });
        }
    }

    class BrowseLogoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Brand Logo Image");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images (PNG, JPG, JPEG)", "png", "jpg", "jpeg"));

            int result = fileChooser.showOpenDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                view.setSelectedLogoFile(selectedFile);
                view.showLogoPreview(selectedFile.getAbsolutePath());
            }
        }
    }

    class AddBrandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            String name = view.getBrandNameInput().trim();
            File selectedFile = view.getSelectedLogoFile();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please enter a Brand Name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String logoPath = "/images/logos/default.png"; // Default fallback
            if (selectedFile != null) {
                logoPath = copyLogoToAssets(selectedFile);
                if (logoPath == null) return; // Error occurred while copying
            }

            Brand brand = new Brand(name, logoPath);
            boolean success = brandDao.addBrand(brand);

            if (success) {
                JOptionPane.showMessageDialog(view, "Brand '" + name + "' added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearBrandInputs();
                loadBrandTable();
                
                // Dynamic update: reload the Brand dropdown in the Cars tab of AdminDashboard if embedded there!
                java.awt.Window parent = SwingUtilities.getWindowAncestor(view);
                if (parent instanceof AdminDashboard) {
                    new CarController().populateBrandCombo(((AdminDashboard) parent).getCarPanel());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add brand. The name might already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class UpdateBrandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            int selectedRow = view.getBrandsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a brand from the table to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(view.getBrandsTable().getValueAt(selectedRow, 0).toString());
            String oldLogoPath = view.getBrandsTable().getValueAt(selectedRow, 2).toString();
            String name = view.getBrandNameInput().trim();
            File selectedFile = view.getSelectedLogoFile();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Brand Name cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String logoPath = oldLogoPath; // Maintain existing logo by default
            if (selectedFile != null) {
                logoPath = copyLogoToAssets(selectedFile);
                if (logoPath == null) return;
            }

            Brand brand = new Brand(id, name, logoPath);
            boolean success = brandDao.updateBrand(brand);

            if (success) {
                JOptionPane.showMessageDialog(view, "Brand record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearBrandInputs();
                loadBrandTable();
                
                // Dynamic update: reload the Brand dropdown in the Cars tab of AdminDashboard if embedded there!
                java.awt.Window parent = SwingUtilities.getWindowAncestor(view);
                if (parent instanceof AdminDashboard) {
                    new CarController().populateBrandCombo(((AdminDashboard) parent).getCarPanel());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update brand record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteBrandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view == null) return;

            int selectedRow = view.getBrandsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Please select a brand to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(view.getBrandsTable().getValueAt(selectedRow, 0).toString());
            String name = view.getBrandsTable().getValueAt(selectedRow, 1).toString();

            int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete the brand '" + name + "'?\nWARNING: This will delete all cars linked to this brand!",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = brandDao.deleteBrand(id);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Brand removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearBrandInputs();
                    loadBrandTable();
                    
                    // Sync updates in AdminDashboard if embedded there!
                    java.awt.Window parent = SwingUtilities.getWindowAncestor(view);
                    if (parent instanceof AdminDashboard) {
                        AdminDashboard adminView = (AdminDashboard) parent;
                        new CarController().populateBrandCombo(adminView.getCarPanel());
                        new CarController().loadAdminCarTable(adminView.getCarPanel()); // Update car list in case some cars were deleted cascade
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete brand.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class RefreshBrandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadBrandTable();
            view.clearBrandInputs();
        }
    }

    /**
     * Copy selected logo file to local source assets and build folders at runtime.
     */
    private String copyLogoToAssets(File selectedFile) {
        String relativePath = "/images/logos/" + selectedFile.getName();
        
        // 1. Copy to source folder (src/images/logos/)
        File srcDir = new File("src/images/logos/");
        if (!srcDir.exists()) srcDir.mkdirs();
        File srcFile = new File(srcDir, selectedFile.getName());

        // 2. Copy to compile target folder (build/classes/images/logos/)
        File buildDir = new File("build/classes/images/logos/");
        if (!buildDir.exists()) buildDir.mkdirs();
        File buildFile = new File(buildDir, selectedFile.getName());

        try {
            Files.copy(selectedFile.toPath(), srcFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(selectedFile.toPath(), buildFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return relativePath;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Failed to save logo image file: " + e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
