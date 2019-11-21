/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.store.api;

import com.diviso.graeshoppe.client.store.model.PropreitorDTO;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-29T10:47:29.652+05:30[Asia/Calcutta]")

@Api(value = "PropreitorResource", description = "the PropreitorResource API")
public interface PropreitorResourceApi {

    @ApiOperation(value = "createPropreitor", nickname = "createPropreitorUsingPOST", notes = "", response = PropreitorDTO.class, tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PropreitorDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/propreitors",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<PropreitorDTO> createPropreitorUsingPOST(@ApiParam(value = "propreitorDTO" ,required=true )  @Valid @RequestBody PropreitorDTO propreitorDTO);


    @ApiOperation(value = "deletePropreitor", nickname = "deletePropreitorUsingDELETE", notes = "", tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/api/propreitors/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePropreitorUsingDELETE(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllPropreitors", nickname = "getAllPropreitorsUsingGET", notes = "", response = PropreitorDTO.class, responseContainer = "List", tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PropreitorDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/propreitors",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<PropreitorDTO>> getAllPropreitorsUsingGET(@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "getPropreitor", nickname = "getPropreitorUsingGET", notes = "", response = PropreitorDTO.class, tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PropreitorDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/propreitors/{id}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<PropreitorDTO> getPropreitorUsingGET(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "searchPropreitors", nickname = "searchPropreitorsUsingGET", notes = "", response = PropreitorDTO.class, responseContainer = "List", tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PropreitorDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/_search/propreitors",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<PropreitorDTO>> searchPropreitorsUsingGET(@NotNull @ApiParam(value = "query", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "updatePropreitor", nickname = "updatePropreitorUsingPUT", notes = "", response = PropreitorDTO.class, tags={ "propreitor-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = PropreitorDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/propreitors",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<PropreitorDTO> updatePropreitorUsingPUT(@ApiParam(value = "propreitorDTO" ,required=true )  @Valid @RequestBody PropreitorDTO propreitorDTO);

}