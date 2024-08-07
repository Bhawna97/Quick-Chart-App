package org.devcenter.quickchart.resources;
import com.google.gson.Gson;
import org.devcenter.quickchart.implementation.PatientChartRepository;
import org.devcenter.quickchart.implementation.PatientRepository;
import org.devcenter.quickchart.implementation.UserRepository;
import org.devcenter.quickchart.model.*;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Class to prepare the API for Quick Chart Project
 */
@Path("QuickChart")
public class QuickChartResource {
    public Logger logger = Logger.getAnonymousLogger();
    DatabaseConnection databaseConnection = new DatabaseConnection();
    UserRepository userRepo = new UserRepository(databaseConnection);
    PatientRepository patientRepo = new PatientRepository(databaseConnection);
    PatientChartRepository patientChartRepo = new PatientChartRepository(databaseConnection);

    public QuickChartResource() {}

    public QuickChartResource(UserRepository userRepo, PatientRepository patientRepo, PatientChartRepository patientChartRepo) {
        this.userRepo = userRepo;
        this.patientRepo = patientRepo;
        this.patientChartRepo = patientChartRepo;
    }

    /**
     * Post API to add new user details into DB
     * @param user - object of User class to access the properties of user from request
     * @return - the response of API as OK if user details gets registered successfully else if any SQL Exception occurs returns EXPECTATION_FAILED
     */
    @POST
    @Path("addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        try {
            if(userRepo.addUserIntoDB(user)==1){
                return Response.status(Response.Status.OK).entity(new Gson().toJson("User Account Created Successfully for user - "+user.getUsername())).build();
            } else if (userRepo.addUserIntoDB(user)==2){
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new Gson().toJson("Username is Taken, Enter Different Username")).build();
            } else {
                return Response.status(Response.Status.NOT_MODIFIED).entity(new Gson().toJson("User Account didn't created! try again")).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the addUserIntoDB(user) method of userRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Post API to validate user details from DB
     * @param user - object of User class to access the properties of user from request
     * @return - the response of API as OK if user is valid/user details exist in DB else returns UNAUTHORIZED
     */
    @POST
    @Path("validateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUser(User user) {
        try {
            if(userRepo.validateUser(user)==1){
                return Response.status(Response.Status.OK).entity(new Gson().toJson(user.getUsername()+" - Valid User")).build();
            }else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(new Gson().toJson(user.getUsername() + " - UnAuthorised User")).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the validateUser(user) method of userRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Post API to check username into DB and get three unique username
     * @param user object of User class to access firstname and lastname
     * @return the list of three unique usernames with status 200
     */
    @POST
    @Path("checkUsername")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUsername(User user) {
        try {
            String response = new Gson().toJson(userRepo.checkUsernameIntoDB(user));
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the checkUsernameIntoDB(user) method of userRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Post API to add new patient details into DB
     * @param patient object of Patient class to access the properties of Patient from request
     * @return the response of API as OK if user details gets added successfully else if any SQL Exception occurs returns EXPECTATION_FAILED
     */
    @POST
    @Path("addPatient")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        try {
            if(patientRepo.addPatientIntoDB(patient)){
                return Response.status(Response.Status.OK).entity(new Gson().toJson(patient.getFirstname()+" - Patient Details Added Successfully")).build();
            }else{
                return Response.status(Response.Status.NOT_MODIFIED).entity(new Gson().toJson("Patient details didn't added, Please try again.")).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the addPatientIntoDB(patient) method of patientRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get API to get the list of all patients from DB
     * @return all patient details available in DB with status code OK and if no patient id available in DB then return the status code as NO_CONTENT
     */
    @GET
    @Path("PatientList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientList() {
        try {
            if(patientRepo.getPatientList().isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).entity(new Gson().toJson("No Data Found")).build();
            }else{
                String response = new Gson().toJson(patientRepo.getPatientList());
                return Response.status(Response.Status.OK).entity(response).build();
            }
        }catch (Exception e){
            logger.log(Level.INFO, "An exception occurred while accessing the getPatientList() method of patientRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get API to get the sorted list of all patients from DB
     * @param columnName string to identify by which value want to sort the list
     * @param order chart to identify in which order want to sort the list
     * @return the sorted patient list with status code 200
     */
    @GET
    @Path("SortedPatientList/columnName/{columnName}/sortOrder/{order}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSortedPatientList(@PathParam("columnName") ColumnName columnName, @PathParam("order") SortOrder order) {
        try {
            if(patientRepo.getSortedPatientList(columnName, order).isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).entity(new Gson().toJson("No Data Found")).build();
            } else {
                String response = new Gson().toJson(patientRepo.getSortedPatientList(columnName, order));
                return Response.status(Response.Status.OK).entity(response).build();
            }
        }catch (Exception e){
            logger.log(Level.INFO, "An exception occurred while accessing the getSortedPatientList(columnName, order) method of patientRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Post API to add new patient Chart details into DB
     * @param patientChart object of PatientChart class to access the properties of PatientChart from request
     * @return the response of API as OK if Patient Chart gets added successfully else if any SQL Exception occurs returns EXPECTATION_FAILED
     */
    @POST
    @Path("addPatientChart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatientChart(PatientChart patientChart) {
        try {
            if(patientChartRepo.addPatientChartIntoDB(patientChart)){
                return Response.status(Response.Status.OK).entity(new Gson().toJson(patientChart.getChartName()+" -  Added Successfully")).build();
            }else{
                return Response.status(Response.Status.NOT_MODIFIED).entity(new Gson().toJson("Patient Chart didn't added, Please try again.")).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the addPatientChartIntoDB(patientChart) method of patientChartRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get API to get the list of charts of the patient from DB
     * @param patientId for getting the list of charts for a particular patientId
     * @return the list of charts if charts are available into DB with status code 200
     */
    @GET
    @Path("PatientChartList/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientChartList(@PathParam("patientId") int patientId) {
        try {
            if(patientChartRepo.getPatientChartList(patientId).isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).entity(new Gson().toJson("No Data Found")).build();
            }else{
                String response = new Gson().toJson(patientChartRepo.getPatientChartList(patientId));
                return Response.status(Response.Status.OK).entity(response).build();
            }
        }catch (Exception e){
            logger.log(Level.INFO, "An exception occurred while accessing the getPatientChartList(patientId) method of patientChartRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get API to get the Chart details of the patient from DB
     * @param chartId for getting the chart details for a particular chartId
     * @return the chart details if details are available into DB with status code 200
     */
    @GET
    @Path("PatientChart/{chartId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientChart(@PathParam("chartId") int chartId) {
        try {
            if(patientChartRepo.getPatientChart(chartId).isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).entity(new Gson().toJson("No Data Found")).build();
            }else{
                String response = new Gson().toJson(patientChartRepo.getPatientChart(chartId));
                return Response.status(Response.Status.OK).entity(response).build();
            }
        }catch (Exception e){
            logger.log(Level.INFO, "An exception occurred while accessing the getPatientChart(chartId) method of patientChartRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get API to get the sorted list of all patients from DB
     * @param columnName string to identify by which value want to sort the list
     * @param order chart to identify in which order want to sort the list
     * @return the sorted patient list with status code 200
     */
    @GET
    @Path("SortedChartList/{patientId}/columnName/{columnName}/sortOrder/{order}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSortedChartList(@PathParam("patientId") int patientId, @PathParam("columnName") ColumnName columnName, @PathParam("order") SortOrder order) {
        try {
            if(patientChartRepo.getSortedChartList(patientId, columnName, order).isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).entity(new Gson().toJson("No Data Found")).build();
            } else {
                String response = new Gson().toJson(patientChartRepo.getSortedChartList(patientId, columnName, order));
                return Response.status(Response.Status.OK).entity(response).build();
            }
        }catch (Exception e){
            logger.log(Level.INFO, "An exception occurred while accessing the getSortedChartList(patientId, columnName, order) method of patientChartRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Post API to update the details of a particular chart of the patient
     * @param patientChart Object of PatientChart class to access properties of patient chart
     * @return the api status 200 ok if the chart details gets updated successfully
     */
    @POST
    @Path("updatePatientChart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatientChart(PatientChart patientChart) {
        try {
            if(patientChartRepo.updatePatientChart(patientChart)){
                return Response.status(Response.Status.OK).entity(new Gson().toJson(patientChart.getChartId()+" - Chart Updated Successfully")).build();
            }else{
                return Response.status(Response.Status.NOT_MODIFIED).entity(new Gson().toJson("Patient Chart didn't updated, Please try again.")).build();
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An exception occurred while accessing the updatePatientChart(patientChart) method of patientChartRepository.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
