package com.assignment.WebMvc.Service;

import com.assignment.WebMvc.dao.UserDao;
import com.assignment.WebMvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserDao userDao;

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Gets user by its id.
     *
     * @param userId
     * @return User.
     */
    public User getUserById(long userId) {
        logger.debug("Getting user details for {}", userId);
        return userDao.get(userId)
                .orElse(null);
    }

    /**
     * Gets user by its email. Email is strictly matched.
     *
     * @param email
     * @return User.
     */
    public User getUserByEmail(String email) {
        logger.debug("Getting user details for {}", email);
        return userDao.findByEmail(email);
    }

    /**
     * Get list of users by matching name. Name is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param name     Users name or it's part.
     * @param pageSize Pagination param. Number of users to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of users.
     */
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        logger.debug("Getting user list for {}", name);
        return userDao.findByName(name)
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Creates new user. User id should be auto-generated.
     *
     * @param user User data.
     * @return Created User object.
     */
    public User createUser(User user) {
        logger.debug("Creating a user");
        return userDao.save(user);
    }

    /**
     * Updates user using given data.
     *
     * @param user User data for update. Should have id set.
     * @return Updated User object.
     */
    public User updateUser(User user) {
        logger.debug("Updating user details for {}", user.getId());
        return userDao.update(user);
    }

    /**
     * Deletes user by its id.
     *
     * @param userId User id.
     * @return Flag that shows whether user has been deleted.
     */
    public boolean deleteUser(long userId) {
        logger.debug("Deleting user {}", userId);
        return userDao.delete(userId);
    }
}