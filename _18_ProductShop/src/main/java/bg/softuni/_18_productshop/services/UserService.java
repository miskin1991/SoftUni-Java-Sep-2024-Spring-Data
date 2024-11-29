package bg.softuni._18_productshop.services;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;
    boolean isImported();

    void exportAllUsersFullNameWithAtLeastOneSoldItem() throws IOException;

    void exportAllUsersWithAtLeastOneSoldItem() throws IOException;
}
