package com.javaeeeee.dwstart.resources;

import com.javaeeeee.dwstart.db.AccountDAO;
import com.javaeeeee.dwstart.db.AccountDAOIMP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Account")
@Produces({ MediaType.APPLICATION_JSON })
public class AccountResource {
    AccountDAO accountDAO;

    public AccountResource(){
        this.accountDAO = new AccountDAOIMP();
    }


    @Path("/all")
    @GET
    public String getAccountTotal() {
        return this.accountDAO.seeAccounts();
    }


    @Path("/Info/{name}")
    @GET
    public String getAccount(@PathParam(value = "name") String name) {
        return this.accountDAO.getAccount(name);
    }


    @Path("/Favorites/{name}")
    @GET
    public String getFavorites(@PathParam(value = "name") String name) {
        return this.accountDAO.getFavorites(name);
    }


    @Path("/History/{name}")
    @GET
    public String getHistory(@PathParam(value = "name") String name) {
        return this.accountDAO.getHistory(name);
    }


    @Path("CreateAccount")
    @POST
    public String createAccount(@QueryParam(value = "name") String name, @QueryParam(value = "password") String password){
        return this.accountDAO.createAccount(name, password);
    }


    @Path("DeleteAccount")
    @POST
    public String deleteAccount(@QueryParam(value = "name") String name, @QueryParam(value = "password") String password){
        return this.accountDAO.deleteAccount(name, password);
    }

    @Path("DeleteFavorite/{name}/")
    @DELETE
    public String deleteFavorite(@PathParam(value = "name") String username, @QueryParam(value = "id") String id){
        return this.accountDAO.removeFavorite(username, id);
    }

    @Path("AddFavorite/{name}/")
    @POST
    public String addFavorite(@PathParam(value = "name") String username, @QueryParam(value = "id") String id){
        return this.accountDAO.addFavorite(username, id);
    }


    @Path("Login")
    @POST
    public String Login(@QueryParam(value = "name") String name, @QueryParam(value = "password") String password){
        return this.accountDAO.login(name, password);
    }

    @Path("Logout")
    @POST
    public String Logout(@QueryParam(value = "name") String name, @QueryParam(value = "password") String password){
        return this.accountDAO.logout(name, password);
    }
}
