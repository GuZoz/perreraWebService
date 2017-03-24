package com.ipartek.formacion.perrera.controller;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/perro")
@Api(value = "/perro")
public class PerroController {

	private static final Logger LOG = Logger.getLogger(PerroController.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor") })
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") String campo) {
		try {

			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			ArrayList<Perro> perros = (ArrayList<Perro>) dao.getAll(orderBy, campo);
			return Response.ok().entity(perros).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Detalle de Perros", notes = "Detalle de perro existente en la BBDD", response = Perro.class, responseContainer = "Perro")

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro de esta id en la BBDD"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor") })
	public Response getById(@PathParam("id") long id) {
		try {

			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			Perro perro = (Perro) dao.getById(id);
			if (perro == null) {
				LOG.warn("Id especificada erronea");
				return Response.noContent().entity(perro).build();
			}
			return Response.ok().entity(perro).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Crear nuevo perro", notes = "Insert nuevo perro en la BBDD", response = Perro.class, responseContainer = "Perro")

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inesperado en el servidor") })
	public Response Insert(Perro perro) {
		try {

			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			dao.insert(perro);

			return Response.ok().entity(perro).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	/*
	 * @PUT
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Crear nuevo perro", notes =
	 * "Insert nuevo perro en la BBDD", response = Perro.class,
	 * responseContainer = "Perro")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Error inesperado en el servidor") })
	 * public Response Insert(Perro perro) { try {
	 * 
	 * PerroDAOImpl dao = PerroDAOImpl.getInstance(); dao.insert(perro);
	 * 
	 * return Response.ok().entity(perro).build();
	 * 
	 * } catch (Exception e) { return Response.serverError().build(); } }
	 */

}