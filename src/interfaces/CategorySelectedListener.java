package interfaces;

import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.List;

public interface CategorySelectedListener {
    void categorySelected(String title, List<ProductCategory> categories, Selectable selectable);
    void goToHome();
}
