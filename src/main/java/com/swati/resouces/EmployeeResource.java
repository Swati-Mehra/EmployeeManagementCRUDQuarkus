package com.swati.resouces;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/employeeManagement")
public class EmployeeResource {
    private static final Logger log = LoggerFactory.getLogger(EmployeeResource.class);
    List<Employee> employeeList = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeDetails(){
        return Response.ok(employeeList).build();
    }

    @Path("/addEmployee")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee employee){
        employeeList.add(employee);
        return Response.ok(employeeList).build();
    }

    @Path("/updateEmployee/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Employee employee){
        employeeList=employeeList.stream().map(updatedEmployee ->{
           if (employee.getId()==id){
               return updatedEmployee;
           }else {
               return employee;
           }
        }).collect(Collectors.toList());
        return Response.ok(employeeList).build();
    }

    @Path("/deleteEmployee/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") int employeeIdToDeleteEmployee){
        Optional<Employee> deletedEmployee = employeeList.stream().filter(employee -> employee.getId()==employeeIdToDeleteEmployee).findFirst();
        if (deletedEmployee.isPresent()){
            Employee employee = deletedEmployee.get();
            employeeList.remove(employee);
            return Response.ok(employeeList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") int id){
        Optional<Employee> employeeDetail = employeeList.stream().filter(employee -> employee.getId()==id).findFirst();
        if (employeeDetail.isPresent()){
            return Response.ok(employeeDetail.get()).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
