package bg.softuni._18_productshop.services;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    boolean isImported();

    void exportAllCategoriesByProducts() throws IOException;
}
