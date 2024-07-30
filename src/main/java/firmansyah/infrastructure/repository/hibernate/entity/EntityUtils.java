// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.ArticlesModelBuilder;
import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.categories.CategoriesModelBuilder;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.CommentsModelBuilder;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoModelBuilder;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsModelBuilder;
import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.model.customers.CustomersModelBuilder;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesModelBuilder;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipModelBuilder;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationshipModelBuilder;
import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.domain.model.orderDetails.OrderDetailsModelBuilder;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.orders.OrdersModelBuilder;
import firmansyah.domain.model.products.Products;
import firmansyah.domain.model.products.ProductsModelBuilder;
import firmansyah.domain.model.region.Region;
import firmansyah.domain.model.region.RegionModelBuilder;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.shippers.ShippersModelBuilder;
import firmansyah.domain.model.suppliers.Suppliers;
import firmansyah.domain.model.suppliers.SuppliersModelBuilder;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationshipModelBuilder;
import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.TagsModelBuilder;
import firmansyah.domain.model.territories.Territories;
import firmansyah.domain.model.territories.TerritoriesModelBuilder;
import firmansyah.domain.model.usStates.UsStates;
import firmansyah.domain.model.usStates.UsStatesModelBuilder;
import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UsersModelBuilder;
import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.employees.EmployeesModelBuilder;




import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class EntityUtils {
	private final ArticlesModelBuilder articlesBuilder;
	private final CategoriesModelBuilder categoriesBuilder;
	private final CommentsModelBuilder commentsBuilder;
	private final CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder;
	private final CustomerDemographicsModelBuilder customerDemographicsBuilder;
	private final CustomersModelBuilder customersBuilder;
	private final EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder;
	private final FavoriteRelationshipModelBuilder favoriteRelationshipBuilder;
	private final FollowRelationshipModelBuilder followRelationshipBuilder;
	private final OrderDetailsModelBuilder orderDetailsBuilder;
	private final OrdersModelBuilder ordersBuilder;
	private final ProductsModelBuilder productsBuilder;
	private final RegionModelBuilder regionBuilder;
	private final ShippersModelBuilder shippersBuilder;
	private final SuppliersModelBuilder suppliersBuilder;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final TagsModelBuilder tagsBuilder;
	private final TerritoriesModelBuilder territoriesBuilder;
	private final UsStatesModelBuilder usStatesBuilder;
	private final UsersModelBuilder usersBuilder;
	private final EmployeesModelBuilder employeesBuilder;
	

	public Articles articles(ArticlesEntity articlesEntity) {
    	return articlesBuilder.build(
				articlesEntity.getCreatedat(),
				articlesEntity.getUpdatedat(),
				articlesEntity.getBody(),
				articlesEntity.getDescription(),
				articlesEntity.getId(),
				articlesEntity.getSlug(),
				articlesEntity.getTitle(),
				null,
				null,
				null,
				users(articlesEntity.getUsersAuthorId())
        );
	}
	public Categories categories(CategoriesEntity categoriesEntity) {
    	return categoriesBuilder.build(
				categoriesEntity.getCategoryId(),
				categoriesEntity.getCategoryName(),
				categoriesEntity.getDescription(),
				categoriesEntity.getPicture(),
				null
        );
	}
	public Comments comments(CommentsEntity commentsEntity) {
    	return commentsBuilder.build(
				commentsEntity.getCreatedat(),
				commentsEntity.getUpdatedat(),
				commentsEntity.getBody(),
				commentsEntity.getId(),
				articles(commentsEntity.getArticlesArticleId()),
				users(commentsEntity.getUsersAuthorId())
        );
	}
	public CustomerCustomerDemo customerCustomerDemo(CustomerCustomerDemoEntity customerCustomerDemoEntity) {
    	return customerCustomerDemoBuilder.build(
				customers(customerCustomerDemoEntity.getCustomersCustomerId()),
				customerDemographics(customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId())
        );
	}
	public CustomerDemographics customerDemographics(CustomerDemographicsEntity customerDemographicsEntity) {
    	return customerDemographicsBuilder.build(
				customerDemographicsEntity.getCustomerTypeId(),
				customerDemographicsEntity.getCustomerDesc(),
				null
        );
	}
	public Customers customers(CustomersEntity customersEntity) {
    	return customersBuilder.build(
				customersEntity.getCustomerId(),
				customersEntity.getCompanyName(),
				customersEntity.getContactName(),
				customersEntity.getContactTitle(),
				customersEntity.getAddress(),
				customersEntity.getCity(),
				customersEntity.getRegion(),
				customersEntity.getPostalCode(),
				customersEntity.getCountry(),
				customersEntity.getPhone(),
				customersEntity.getFax(),
				null,
				null
        );
	}
	public EmployeeTerritories employeeTerritories(EmployeeTerritoriesEntity employeeTerritoriesEntity) {
    	return employeeTerritoriesBuilder.build(
				employees(employeeTerritoriesEntity.getEmployeesEmployeeId()),
				territories(employeeTerritoriesEntity.getTerritoriesTerritoryId())
        );
	}
	public FavoriteRelationship favoriteRelationship(FavoriteRelationshipEntity favoriteRelationshipEntity) {
    	return favoriteRelationshipBuilder.build(
				users(favoriteRelationshipEntity.getUsersUserId()),
				articles(favoriteRelationshipEntity.getArticlesArticleId())
        );
	}
	public FollowRelationship followRelationship(FollowRelationshipEntity followRelationshipEntity) {
    	return followRelationshipBuilder.build(
				users(followRelationshipEntity.getUsersFollowedId()),
				users(followRelationshipEntity.getUsersUserId())
        );
	}
	public OrderDetails orderDetails(OrderDetailsEntity orderDetailsEntity) {
    	return orderDetailsBuilder.build(
				orderDetailsEntity.getUnitPrice(),
				orderDetailsEntity.getQuantity(),
				orderDetailsEntity.getDiscount(),
				orders(orderDetailsEntity.getOrdersOrderId()),
				products(orderDetailsEntity.getProductsProductId())
        );
	}
	public Orders orders(OrdersEntity ordersEntity) {
    	return ordersBuilder.build(
				ordersEntity.getOrderId(),
				ordersEntity.getOrderDate(),
				ordersEntity.getRequiredDate(),
				ordersEntity.getShippedDate(),
				ordersEntity.getFreight(),
				ordersEntity.getShipName(),
				ordersEntity.getShipAddress(),
				ordersEntity.getShipCity(),
				ordersEntity.getShipRegion(),
				ordersEntity.getShipPostalCode(),
				ordersEntity.getShipCountry(),
				null,
				employees(ordersEntity.getEmployeesEmployeeId()),
				shippers(ordersEntity.getShippersShipVia()),
				customers(ordersEntity.getCustomersCustomerId())
        );
	}
	public Products products(ProductsEntity productsEntity) {
    	return productsBuilder.build(
				productsEntity.getProductId(),
				productsEntity.getProductName(),
				productsEntity.getQuantityPerUnit(),
				productsEntity.getUnitPrice(),
				productsEntity.getUnitsInStock(),
				productsEntity.getUnitsOnOrder(),
				productsEntity.getReorderLevel(),
				productsEntity.getDiscontinued(),
				null,
				categories(productsEntity.getCategoriesCategoryId()),
				suppliers(productsEntity.getSuppliersSupplierId())
        );
	}
	public Region region(RegionEntity regionEntity) {
    	return regionBuilder.build(
				regionEntity.getRegionId(),
				regionEntity.getRegionDescription(),
				null
        );
	}
	public Shippers shippers(ShippersEntity shippersEntity) {
    	return shippersBuilder.build(
				shippersEntity.getShipperId(),
				shippersEntity.getCompanyName(),
				shippersEntity.getPhone(),
				null
        );
	}
	public Suppliers suppliers(SuppliersEntity suppliersEntity) {
    	return suppliersBuilder.build(
				suppliersEntity.getSupplierId(),
				suppliersEntity.getCompanyName(),
				suppliersEntity.getContactName(),
				suppliersEntity.getContactTitle(),
				suppliersEntity.getAddress(),
				suppliersEntity.getCity(),
				suppliersEntity.getRegion(),
				suppliersEntity.getPostalCode(),
				suppliersEntity.getCountry(),
				suppliersEntity.getPhone(),
				suppliersEntity.getFax(),
				suppliersEntity.getHomepage(),
				null
        );
	}
	public TagRelationship tagRelationship(TagRelationshipEntity tagRelationshipEntity) {
    	return tagRelationshipBuilder.build(
				articles(tagRelationshipEntity.getArticlesArticleId()),
				tags(tagRelationshipEntity.getTagsTagId())
        );
	}
	public Tags tags(TagsEntity tagsEntity) {
    	return tagsBuilder.build(
				tagsEntity.getId(),
				tagsEntity.getName(),
				null
        );
	}
	public Territories territories(TerritoriesEntity territoriesEntity) {
    	return territoriesBuilder.build(
				territoriesEntity.getTerritoryId(),
				territoriesEntity.getTerritoryDescription(),
				null,
				region(territoriesEntity.getRegionRegionId())
        );
	}
	public UsStates usStates(UsStatesEntity usStatesEntity) {
    	return usStatesBuilder.build(
				usStatesEntity.getStateId(),
				usStatesEntity.getStateName(),
				usStatesEntity.getStateAbbr(),
				usStatesEntity.getStateRegion()
        );
	}
	public Users users(UsersEntity usersEntity) {
    	return usersBuilder.build(
				usersEntity.getBio(),
				usersEntity.getEmail(),
				usersEntity.getId(),
				usersEntity.getImage(),
				usersEntity.getPassword(),
				usersEntity.getUsername(),
				null,
				null,
				null,
				null,
				null
        );
	}
	public Employees employees(EmployeesEntity employeesEntity) {
    	return employeesBuilder.build(
				employeesEntity.getEmployeeId(),
				employeesEntity.getLastName(),
				employeesEntity.getFirstName(),
				employeesEntity.getTitle(),
				employeesEntity.getTitleOfCourtesy(),
				employeesEntity.getBirthDate(),
				employeesEntity.getHireDate(),
				employeesEntity.getAddress(),
				employeesEntity.getCity(),
				employeesEntity.getRegion(),
				employeesEntity.getPostalCode(),
				employeesEntity.getCountry(),
				employeesEntity.getHomePhone(),
				employeesEntity.getExtension(),
				employeesEntity.getPhoto(),
				employeesEntity.getNotes(),
				employeesEntity.getPhotoPath(),
				null,
				null,
				null,
				employees(employeesEntity.getEmployeesReportsTo())
        );
	}

    
}
