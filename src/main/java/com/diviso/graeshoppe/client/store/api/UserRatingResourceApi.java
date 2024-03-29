/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.store.api;

import com.diviso.graeshoppe.client.store.model.UserRating;
import com.diviso.graeshoppe.client.store.model.UserRatingDTO;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-11-27T14:30:41.015+05:30[Asia/Kolkata]")

@Api(value = "UserRatingResource", description = "the UserRatingResource API")
public interface UserRatingResourceApi {

    @ApiOperation(value = "createUserRating", nickname = "createUserRatingUsingPOST", notes = "", response = UserRatingDTO.class, tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/user-ratings",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<UserRatingDTO> createUserRatingUsingPOST(@ApiParam(value = "userRatingDTO" ,required=true )  @Valid @RequestBody UserRatingDTO userRatingDTO);


    @ApiOperation(value = "deleteUserRating", nickname = "deleteUserRatingUsingDELETE", notes = "", tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/api/user-ratings/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserRatingUsingDELETE(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllUserRatings", nickname = "getAllUserRatingsUsingGET", notes = "", response = UserRatingDTO.class, responseContainer = "List", tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/user-ratings",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<UserRatingDTO>> getAllUserRatingsUsingGET(@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "getCount", nickname = "getCountUsingGET", notes = "", response = Integer.class, tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Integer.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/count/{rating}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<Integer> getCountUsingGET(@ApiParam(value = "rating",required=true) @PathVariable("rating") Double rating);


    @ApiOperation(value = "getUserRating", nickname = "getUserRatingUsingGET", notes = "", response = UserRatingDTO.class, tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/user-ratings/{id}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<UserRatingDTO> getUserRatingUsingGET(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "modelToDto", nickname = "modelToDtoUsingPOST1", notes = "", response = UserRatingDTO.class, tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/user-rating/modelToDto",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<UserRatingDTO> modelToDtoUsingPOST1(@ApiParam(value = "userRating" ,required=true )  @Valid @RequestBody UserRating userRating);


    @ApiOperation(value = "searchUserRatings", nickname = "searchUserRatingsUsingGET", notes = "", response = UserRatingDTO.class, responseContainer = "List", tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/_search/user-ratings",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<UserRatingDTO>> searchUserRatingsUsingGET(@NotNull @ApiParam(value = "query", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "updateUserRating", nickname = "updateUserRatingUsingPUT", notes = "", response = UserRatingDTO.class, tags={ "user-rating-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = UserRatingDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/user-ratings",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<UserRatingDTO> updateUserRatingUsingPUT(@ApiParam(value = "userRatingDTO" ,required=true )  @Valid @RequestBody UserRatingDTO userRatingDTO);

}
