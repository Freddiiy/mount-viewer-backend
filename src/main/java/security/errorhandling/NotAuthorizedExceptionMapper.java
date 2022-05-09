package security.errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.ExceptionDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final int ERROR_CODE = 401;
    @Context
    ServletContext context;
    

    @Override
    public Response toResponse(NotAuthorizedException ex) {
        //Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        ExceptionDTO err = new ExceptionDTO(ERROR_CODE, ex.getMessage());
        return Response.status(ERROR_CODE).entity(gson.toJson(err)).type(MediaType.APPLICATION_JSON).build();
              
    }   
}