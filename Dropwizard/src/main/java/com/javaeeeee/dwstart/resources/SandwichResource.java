package com.javaeeeee.dwstart.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.javaeeeee.dwstart.db.AccountDAO;
import com.javaeeeee.dwstart.db.AccountDAOIMP;

@Path("/Sandwich")
@Produces({ MediaType.APPLICATION_JSON })
public class SandwichResource {

    AccountDAO accountDAO;

    public SandwichResource(){
        this.accountDAO = new AccountDAOIMP();
    }

    @Path("/Generate/{name}")
    @POST
    public String getAccount(@PathParam(value = "name") String name) {
        return this.accountDAO.generateSandwich(name);
    }
}
