package com.javaeeeee.dwstart.db;

/**
 * This is not the DAO that was envisioned for these projects but JDBI and JDBC were honestly being a pain in the ass.
 * I spent multiple days and countless hours trying to get any of it working and I still could not.
 * I followed multiple tutorials, tried different maven dependencies and so many imports but was getting errors that
 * Google had no real results for anything similar.
 *
 * So I decided to create my own type of DAO. This interface and AccountDAOIMP are similar to how a DAO normally acts.
 * This separates the application later from the persistence layer. This interface 'hides' all the different functions
 * of my CRUD operations that are performed in AccountDAOIMP. I also tried to follow how jdbi has the @sql operation
 * and what the operation performed is.
 *
 * Additionally, with the instructions not stating that we need to use a certain library, or anything of the sort, I
 * figured it wouldn't be against the rules of the assignment to make my own type of DAO.
 */
public interface AccountDAO {

    // @SqlQuery("SELECT * FROM account")
    public String seeAccounts();

    // @SqlQuery("SELECT * FROM account WHERE username = :username")
    public String getAccount(String username);
    // @SqlQuery("SELECT favorites.bread, favorites.meat, favorites.cheese, favorites.sauce, favorites.veggies, favorites.extras, favorites.id FROM favorites WHERE favorites.username =: username")
    public String getFavorites(String username);
    // @SqlQuery("SELECT history.bread, history.meat, history.cheese, history.sauce, history.veggies, history.extras, history.id FROM history WHERE history.username =:username")
    public String getHistory(String username);
    // @SqlUpdate("INSERT into favorites (bread, meat, cheese, sauce, veggie, extras, username, id) values (:bread, :meat, :cheese, :sauce, :veggie, :extras, :username, :id)")
    public String addFavorite(String username, String id);
    // @SqlUpdate("DELETE from favorites WHERE favorites.username = :username AND favorites.id = :id")
    public String removeFavorite(String username, String id);

    // @SqlUpdate("INSERT into account (username, password) values (:username, :password)")
    public String createAccount(String username, String password);
    // @SqlUpdate("DELETE from favorites WHERE favorites.username = :username and favorites.password = :password")
    public String deleteAccount(String username, String password);

    // @SqlUpdate("UPDATE account SET login = 1 where account.username = :username")
    public String login(String username, String password);
    // @SqlUpdate("UPDATE account SET login = 0 where account.username = :username")
    public String logout(String username, String password);

    // @SqlQuery("INSERT into history (bread, meat, cheese, sauce, veggie, extras, username, id) values (:bread, :meat, :cheese, :sauce, :veggie, :extras, :username, :id)")
    public String generateSandwich(String username);
}
