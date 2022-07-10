package hai2022.team.busapplication.interfaces;

import hai2022.team.busapplication.models.User;

public interface UserCallback {
    void user_click_listener(User user);
    void remove_user(User user);
    void add_new_user(String type);
}
