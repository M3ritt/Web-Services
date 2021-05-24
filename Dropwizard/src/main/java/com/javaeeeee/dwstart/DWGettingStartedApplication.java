package com.javaeeeee.dwstart;

import com.javaeeeee.dwstart.db.AccountDAO;
import com.javaeeeee.dwstart.resources.AccountResource;
import com.javaeeeee.dwstart.resources.HelloResource;
import com.javaeeeee.dwstart.resources.SandwichResource;
import io.dropwizard.Application;
//import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


//import javax.sql.DataSource;
////import org.jdbi.v3.core.DBI;
//import org.jdbi.v3.core.Jdbi;

/**
 * Notes to myself for clarity:
 *
 * To run:
 *  mvn package
 *  java -jar target/DWGettingStarted-1.0-SNAPSHOT.jar server config.yml
 *
 * SQL :
 *  /mysql/bin --> mysqld --console
 *  mysql -u myuser -p      Jam3ritt
 *
 *
 * Current endpoints:
 *  - GET /Account/all
 *  - GET /Account/Info/{name}
 *  - GET /Account/History/{name}
 *  - GET /Account/Favorites/{name}
 *  - POST /Account//CreateAccount/{name}{password}
 *  - POST /Account/DeleteAccount/{name}{password}
 *  - DELETE /Account/DeleteFavorite/{name}{id}
 *  - POST /Account/AddFavorite/{name}{id}
 *  - POST /Account/Login/{name}{password}
 *  - POST /Account/Logout/{name}{password}
 *
 *  - POST /Sandwich/GenerateSandwich/{name}
 *
 *  Implemented improvements from last assignment:
 *      - Gave sandwiches an id to make it easier to reference them in the database and endpoints
 *      - Can delete a User if correct username and password
 *      - Checks if a username is in a database when creating a new user
 *      - Added a login system -> 1 in the database for login is logged in, 0 is not logged in.
 *          - Creating a new account automatically sets it to 0
 *
 */

public class DWGettingStartedApplication extends Application<DWGettingStartedConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DWGettingStartedApplication().run(args);
    }

    @Override
    public String getName() {
        return "DWGettingStarted";
    }

    @Override
    public void initialize(final Bootstrap<DWGettingStartedConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DWGettingStartedConfiguration configuration,
                    final Environment environment) {

        environment.jersey().register(new HelloResource());
        environment.jersey().register(new AccountResource());
        environment.jersey().register(new SandwichResource());
    }

}
