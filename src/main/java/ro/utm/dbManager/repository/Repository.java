package ro.utm.dbManager.repository;

import java.sql.Connection;
import java.util.ArrayList;

public interface Repository<T> {
    /**
     * Find one item by id and return it if exist / else return null.
     *
     * @param id
     * @return
     */
    public T find(long id);

    /**
     * Find all items and return a list.
     *
     * @return
     */
    public ArrayList<T> findAll();

    /**
     * Create a new object and return it / else return null.
     *
     * @param obj
     * @return
     */
    public T create(T obj);

    /**
     * Update an existant object and return it / else return null.
     *
     * @param obj
     * @return
     */
    public T update(T obj);

    /**
     * Delete an item by id and return 1 on success.
     *
     * @param id
     * @return
     */
    public int delete(long id);

    /**
     * Check if a table exists in the database.
     *
     * @param connection
     * @param tableName
     * @return - true if table exists, false otherwise
     */
    public boolean checkIfTableExists(Connection connection, String tableName);
}
