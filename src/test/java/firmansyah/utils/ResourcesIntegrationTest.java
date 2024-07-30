// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.utils;

import firmansyah.infrastructure.repository.hibernate.entity.ArticlesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CategoriesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CommentsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerCustomerDemoEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerCustomerDemoEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerDemographicsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CustomersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeeTerritoriesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeeTerritoriesEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntity;
import firmansyah.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.FollowRelationshipEntity;
import firmansyah.infrastructure.repository.hibernate.entity.FollowRelationshipEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.OrderDetailsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.OrderDetailsEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.OrdersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.ProductsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.RegionEntity;
import firmansyah.infrastructure.repository.hibernate.entity.ShippersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.SuppliersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.TagRelationshipEntity;
import firmansyah.infrastructure.repository.hibernate.entity.TagRelationshipEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.TagsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.TerritoriesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.UsStatesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeesEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import java.security.SecureRandom;



public class ResourcesIntegrationTest extends AbstractIntegrationTest {
	protected ArticlesEntity createArticles(String unique){
		final var usersAuthorIdEntity= createUsers(unique);
		return transaction(
        () -> {
		final var articlesEntity = new ArticlesEntity();
		articlesEntity.setCreatedat(LocalDateTime.now());
		articlesEntity.setUpdatedat(LocalDateTime.now());
		articlesEntity.setBody("body"+unique);
		articlesEntity.setDescription("description"+unique);
		articlesEntity.setId(generateRandomString(255)+unique);
		articlesEntity.setSlug("slug"+unique);
		articlesEntity.setTitle("title"+unique);
		articlesEntity.setUsersAuthorId(usersAuthorIdEntity);
          	entityManager.persist(articlesEntity);
            return articlesEntity;
        });
	}
	
	protected ArticlesEntity findArticlesEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(ArticlesEntity.class, id));
	}
  
	protected CategoriesEntity createCategories(String unique){
		return transaction(
        () -> {
		final var categoriesEntity = new CategoriesEntity();
		categoriesEntity.setCategoryId(2+Integer.parseInt(unique));
		categoriesEntity.setCategoryName("categoryName"+unique);
		categoriesEntity.setDescription("description"+unique);
		categoriesEntity.setPicture(new String("picture"+unique).getBytes());
          	entityManager.persist(categoriesEntity);
            return categoriesEntity;
        });
	}
	
	protected CategoriesEntity findCategoriesEntityByPK(Integer categoryId) {
	    
		return transaction(() -> entityManager.find(CategoriesEntity.class, categoryId));
	}
  
	protected CommentsEntity createComments(String unique){
		final var articlesArticleIdEntity= createArticles(unique);
		final var usersAuthorIdEntity= createUsers(unique);
		return transaction(
        () -> {
		final var commentsEntity = new CommentsEntity();
		commentsEntity.setCreatedat(LocalDateTime.now());
		commentsEntity.setUpdatedat(LocalDateTime.now());
		commentsEntity.setBody("body"+unique);
		commentsEntity.setId(generateRandomString(255)+unique);
		commentsEntity.setArticlesArticleId(articlesArticleIdEntity);
		commentsEntity.setUsersAuthorId(usersAuthorIdEntity);
          	entityManager.persist(commentsEntity);
            return commentsEntity;
        });
	}
	
	protected CommentsEntity findCommentsEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(CommentsEntity.class, id));
	}
  
	protected CustomerCustomerDemoEntity createCustomerCustomerDemo(String unique){
		final var customersCustomerIdEntity= createCustomers(unique);
		final var customerDemographicsCustomerTypeIdEntity= createCustomerDemographics(unique);
		return transaction(
        () -> {
		final var customerCustomerDemoEntity = new CustomerCustomerDemoEntity();
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		customerCustomerDemoEntityKey.setCustomersCustomerId(customersCustomerIdEntity);
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
		customerCustomerDemoEntity.setPrimaryKey(customerCustomerDemoEntityKey);
		customerCustomerDemoEntity.setCustomersCustomerId(customersCustomerIdEntity);
		customerCustomerDemoEntity.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
          	entityManager.persist(customerCustomerDemoEntity);
            return customerCustomerDemoEntity;
        });
	}
	
	protected CustomerCustomerDemoEntity findCustomerCustomerDemoEntityByPK(String customerId,String customerTypeId) {
	    
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		final var customersCustomerIdEntity = entityManager.find(CustomersEntity.class, customerId);
		customerCustomerDemoEntityKey.setCustomersCustomerId(customersCustomerIdEntity);
		
		final var customerDemographicsCustomerTypeIdEntity = entityManager.find(CustomerDemographicsEntity.class, customerTypeId);
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
		
		
		return transaction(() -> entityManager.find(CustomerCustomerDemoEntity.class, customerCustomerDemoEntityKey));
	}
  
	protected CustomerDemographicsEntity createCustomerDemographics(String unique){
		return transaction(
        () -> {
		final var customerDemographicsEntity = new CustomerDemographicsEntity();
		customerDemographicsEntity.setCustomerTypeId(generateRandomString(5)+unique);
		customerDemographicsEntity.setCustomerDesc("customerDesc"+unique);
          	entityManager.persist(customerDemographicsEntity);
            return customerDemographicsEntity;
        });
	}
	
	protected CustomerDemographicsEntity findCustomerDemographicsEntityByPK(String customerTypeId) {
	    
		return transaction(() -> entityManager.find(CustomerDemographicsEntity.class, customerTypeId));
	}
  
	protected CustomersEntity createCustomers(String unique){
		return transaction(
        () -> {
		final var customersEntity = new CustomersEntity();
		customersEntity.setCustomerId(generateRandomString(5)+unique);
		customersEntity.setCompanyName("companyName"+unique);
		customersEntity.setContactName("contactName"+unique);
		customersEntity.setContactTitle("contactTitle"+unique);
		customersEntity.setAddress("address"+unique);
		customersEntity.setCity("city"+unique);
		customersEntity.setRegion("region"+unique);
		customersEntity.setPostalCode("postalCode"+unique);
		customersEntity.setCountry("country"+unique);
		customersEntity.setPhone("phone"+unique);
		customersEntity.setFax("fax"+unique);
          	entityManager.persist(customersEntity);
            return customersEntity;
        });
	}
	
	protected CustomersEntity findCustomersEntityByPK(String customerId) {
	    
		return transaction(() -> entityManager.find(CustomersEntity.class, customerId));
	}
  
	protected EmployeeTerritoriesEntity createEmployeeTerritories(String unique){
		final var employeesEmployeeIdEntity= createEmployees(unique);
		final var territoriesTerritoryIdEntity= createTerritories(unique);
		return transaction(
        () -> {
		final var employeeTerritoriesEntity = new EmployeeTerritoriesEntity();
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
		employeeTerritoriesEntity.setPrimaryKey(employeeTerritoriesEntityKey);
		employeeTerritoriesEntity.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		employeeTerritoriesEntity.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
          	entityManager.persist(employeeTerritoriesEntity);
            return employeeTerritoriesEntity;
        });
	}
	
	protected EmployeeTerritoriesEntity findEmployeeTerritoriesEntityByPK(Integer employeeId,String territoryId) {
	    
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		final var employeesEmployeeIdEntity = entityManager.find(EmployeesEntity.class, employeeId);
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		
		final var territoriesTerritoryIdEntity = entityManager.find(TerritoriesEntity.class, territoryId);
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
		
		
		return transaction(() -> entityManager.find(EmployeeTerritoriesEntity.class, employeeTerritoriesEntityKey));
	}
  
	protected FavoriteRelationshipEntity createFavoriteRelationship(String unique){
		final var usersUserIdEntity= createUsers(unique);
		final var articlesArticleIdEntity= createArticles(unique);
		return transaction(
        () -> {
		final var favoriteRelationshipEntity = new FavoriteRelationshipEntity();
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		favoriteRelationshipEntity.setPrimaryKey(favoriteRelationshipEntityKey);
		favoriteRelationshipEntity.setUsersUserId(usersUserIdEntity);
		favoriteRelationshipEntity.setArticlesArticleId(articlesArticleIdEntity);
          	entityManager.persist(favoriteRelationshipEntity);
            return favoriteRelationshipEntity;
        });
	}
	
	protected FavoriteRelationshipEntity findFavoriteRelationshipEntityByPK(String articleId,String userId) {
	    
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		final var articlesArticleIdEntity = entityManager.find(ArticlesEntity.class, articleId);
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		
		final var usersUserIdEntity = entityManager.find(UsersEntity.class, userId);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		return transaction(() -> entityManager.find(FavoriteRelationshipEntity.class, favoriteRelationshipEntityKey));
	}
  
	protected FollowRelationshipEntity createFollowRelationship(String unique){
		final var usersFollowedIdEntity= createUsers(unique);
		final var usersUserIdEntity= createUsers(unique);
		return transaction(
        () -> {
		final var followRelationshipEntity = new FollowRelationshipEntity();
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		followRelationshipEntity.setPrimaryKey(followRelationshipEntityKey);
		followRelationshipEntity.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntity.setUsersUserId(usersUserIdEntity);
          	entityManager.persist(followRelationshipEntity);
            return followRelationshipEntity;
        });
	}
	
	protected FollowRelationshipEntity findFollowRelationshipEntityByPK(String followedId,String userId) {
	    
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		final var usersFollowedIdEntity = entityManager.find(UsersEntity.class, followedId);
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		
		final var usersUserIdEntity = entityManager.find(UsersEntity.class, userId);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		return transaction(() -> entityManager.find(FollowRelationshipEntity.class, followRelationshipEntityKey));
	}
  
	protected OrderDetailsEntity createOrderDetails(String unique){
		final var ordersOrderIdEntity= createOrders(unique);
		final var productsProductIdEntity= createProducts(unique);
		return transaction(
        () -> {
		final var orderDetailsEntity = new OrderDetailsEntity();
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		orderDetailsEntityKey.setOrdersOrderId(ordersOrderIdEntity);
		orderDetailsEntityKey.setProductsProductId(productsProductIdEntity);
		orderDetailsEntity.setPrimaryKey(orderDetailsEntityKey);
		orderDetailsEntity.setUnitPrice(3.0+Double.parseDouble(unique));
		orderDetailsEntity.setQuantity(2+Integer.parseInt(unique));
		orderDetailsEntity.setDiscount(3.0+Double.parseDouble(unique));
		orderDetailsEntity.setOrdersOrderId(ordersOrderIdEntity);
		orderDetailsEntity.setProductsProductId(productsProductIdEntity);
          	entityManager.persist(orderDetailsEntity);
            return orderDetailsEntity;
        });
	}
	
	protected OrderDetailsEntity findOrderDetailsEntityByPK(Integer orderId,Integer productId) {
	    
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		final var ordersOrderIdEntity = entityManager.find(OrdersEntity.class, orderId);
		orderDetailsEntityKey.setOrdersOrderId(ordersOrderIdEntity);
		
		final var productsProductIdEntity = entityManager.find(ProductsEntity.class, productId);
		orderDetailsEntityKey.setProductsProductId(productsProductIdEntity);
		
		
		return transaction(() -> entityManager.find(OrderDetailsEntity.class, orderDetailsEntityKey));
	}
  
	protected OrdersEntity createOrders(String unique){
		final var employeesEmployeeIdEntity= createEmployees(unique);
		final var shippersShipViaEntity= createShippers(unique);
		final var customersCustomerIdEntity= createCustomers(unique);
		return transaction(
        () -> {
		final var ordersEntity = new OrdersEntity();
		ordersEntity.setOrderId(2+Integer.parseInt(unique));
		ordersEntity.setOrderDate(LocalDateTime.now());
		ordersEntity.setRequiredDate(LocalDateTime.now());
		ordersEntity.setShippedDate(LocalDateTime.now());
		ordersEntity.setFreight(6.0+Double.parseDouble(unique));
		ordersEntity.setShipName("shipName"+unique);
		ordersEntity.setShipAddress("shipAddress"+unique);
		ordersEntity.setShipCity("shipCity"+unique);
		ordersEntity.setShipRegion("shipRegion"+unique);
		ordersEntity.setShipPostalCode("shipPostalCode"+unique);
		ordersEntity.setShipCountry("shipCountry"+unique);
		ordersEntity.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		ordersEntity.setShippersShipVia(shippersShipViaEntity);
		ordersEntity.setCustomersCustomerId(customersCustomerIdEntity);
          	entityManager.persist(ordersEntity);
            return ordersEntity;
        });
	}
	
	protected OrdersEntity findOrdersEntityByPK(Integer orderId) {
	    
		return transaction(() -> entityManager.find(OrdersEntity.class, orderId));
	}
  
	protected ProductsEntity createProducts(String unique){
		final var categoriesCategoryIdEntity= createCategories(unique);
		final var suppliersSupplierIdEntity= createSuppliers(unique);
		return transaction(
        () -> {
		final var productsEntity = new ProductsEntity();
		productsEntity.setProductId(2+Integer.parseInt(unique));
		productsEntity.setProductName("productName"+unique);
		productsEntity.setQuantityPerUnit("quantityPerUnit"+unique);
		productsEntity.setUnitPrice(6.0+Double.parseDouble(unique));
		productsEntity.setUnitsInStock(2+Integer.parseInt(unique));
		productsEntity.setUnitsOnOrder(2+Integer.parseInt(unique));
		productsEntity.setReorderLevel(2+Integer.parseInt(unique));
		productsEntity.setDiscontinued(7+Integer.parseInt(unique));
		productsEntity.setCategoriesCategoryId(categoriesCategoryIdEntity);
		productsEntity.setSuppliersSupplierId(suppliersSupplierIdEntity);
          	entityManager.persist(productsEntity);
            return productsEntity;
        });
	}
	
	protected ProductsEntity findProductsEntityByPK(Integer productId) {
	    
		return transaction(() -> entityManager.find(ProductsEntity.class, productId));
	}
  
	protected RegionEntity createRegion(String unique){
		return transaction(
        () -> {
		final var regionEntity = new RegionEntity();
		regionEntity.setRegionId(2+Integer.parseInt(unique));
		regionEntity.setRegionDescription("regionDescription"+unique);
          	entityManager.persist(regionEntity);
            return regionEntity;
        });
	}
	
	protected RegionEntity findRegionEntityByPK(Integer regionId) {
	    
		return transaction(() -> entityManager.find(RegionEntity.class, regionId));
	}
  
	protected ShippersEntity createShippers(String unique){
		return transaction(
        () -> {
		final var shippersEntity = new ShippersEntity();
		shippersEntity.setShipperId(2+Integer.parseInt(unique));
		shippersEntity.setCompanyName("companyName"+unique);
		shippersEntity.setPhone("phone"+unique);
          	entityManager.persist(shippersEntity);
            return shippersEntity;
        });
	}
	
	protected ShippersEntity findShippersEntityByPK(Integer shipperId) {
	    
		return transaction(() -> entityManager.find(ShippersEntity.class, shipperId));
	}
  
	protected SuppliersEntity createSuppliers(String unique){
		return transaction(
        () -> {
		final var suppliersEntity = new SuppliersEntity();
		suppliersEntity.setSupplierId(2+Integer.parseInt(unique));
		suppliersEntity.setCompanyName("companyName"+unique);
		suppliersEntity.setContactName("contactName"+unique);
		suppliersEntity.setContactTitle("contactTitle"+unique);
		suppliersEntity.setAddress("address"+unique);
		suppliersEntity.setCity("city"+unique);
		suppliersEntity.setRegion("region"+unique);
		suppliersEntity.setPostalCode("postalCode"+unique);
		suppliersEntity.setCountry("country"+unique);
		suppliersEntity.setPhone("phone"+unique);
		suppliersEntity.setFax("fax"+unique);
		suppliersEntity.setHomepage("homepage"+unique);
          	entityManager.persist(suppliersEntity);
            return suppliersEntity;
        });
	}
	
	protected SuppliersEntity findSuppliersEntityByPK(Integer supplierId) {
	    
		return transaction(() -> entityManager.find(SuppliersEntity.class, supplierId));
	}
  
	protected TagRelationshipEntity createTagRelationship(String unique){
		final var articlesArticleIdEntity= createArticles(unique);
		final var tagsTagIdEntity= createTags(unique);
		return transaction(
        () -> {
		final var tagRelationshipEntity = new TagRelationshipEntity();
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		tagRelationshipEntity.setPrimaryKey(tagRelationshipEntityKey);
		tagRelationshipEntity.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntity.setTagsTagId(tagsTagIdEntity);
          	entityManager.persist(tagRelationshipEntity);
            return tagRelationshipEntity;
        });
	}
	
	protected TagRelationshipEntity findTagRelationshipEntityByPK(String articleId,String tagId) {
	    
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		final var articlesArticleIdEntity = entityManager.find(ArticlesEntity.class, articleId);
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		
		final var tagsTagIdEntity = entityManager.find(TagsEntity.class, tagId);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		
		
		return transaction(() -> entityManager.find(TagRelationshipEntity.class, tagRelationshipEntityKey));
	}
  
	protected TagsEntity createTags(String unique){
		return transaction(
        () -> {
		final var tagsEntity = new TagsEntity();
		tagsEntity.setId(generateRandomString(255)+unique);
		tagsEntity.setName("name"+unique);
          	entityManager.persist(tagsEntity);
            return tagsEntity;
        });
	}
	
	protected TagsEntity findTagsEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(TagsEntity.class, id));
	}
  
	protected TerritoriesEntity createTerritories(String unique){
		final var regionRegionIdEntity= createRegion(unique);
		return transaction(
        () -> {
		final var territoriesEntity = new TerritoriesEntity();
		territoriesEntity.setTerritoryId(generateRandomString(20)+unique);
		territoriesEntity.setTerritoryDescription("territoryDescription"+unique);
		territoriesEntity.setRegionRegionId(regionRegionIdEntity);
          	entityManager.persist(territoriesEntity);
            return territoriesEntity;
        });
	}
	
	protected TerritoriesEntity findTerritoriesEntityByPK(String territoryId) {
	    
		return transaction(() -> entityManager.find(TerritoriesEntity.class, territoryId));
	}
  
	protected UsStatesEntity createUsStates(String unique){
		return transaction(
        () -> {
		final var usStatesEntity = new UsStatesEntity();
		usStatesEntity.setStateId(2+Integer.parseInt(unique));
		usStatesEntity.setStateName("stateName"+unique);
		usStatesEntity.setStateAbbr("stateAbbr"+unique);
		usStatesEntity.setStateRegion("stateRegion"+unique);
          	entityManager.persist(usStatesEntity);
            return usStatesEntity;
        });
	}
	
	protected UsStatesEntity findUsStatesEntityByPK(Integer stateId) {
	    
		return transaction(() -> entityManager.find(UsStatesEntity.class, stateId));
	}
  
	protected UsersEntity createUsers(String unique){
		return transaction(
        () -> {
		final var usersEntity = new UsersEntity();
		usersEntity.setBio("bio"+unique);
		usersEntity.setEmail("email"+unique);
		usersEntity.setId(generateRandomString(255)+unique);
		usersEntity.setImage("image"+unique);
		usersEntity.setPassword("password"+unique);
		usersEntity.setUsername("username"+unique);
          	entityManager.persist(usersEntity);
            return usersEntity;
        });
	}
	
	protected UsersEntity findUsersEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(UsersEntity.class, id));
	}
  
	protected EmployeesEntity createEmployees(String unique){
		final var employeesReportsToEntity= createEmployees(unique);
		return transaction(
        () -> {
		final var employeesEntity = new EmployeesEntity();
		employeesEntity.setEmployeeId(2+Integer.parseInt(unique));
		employeesEntity.setLastName("lastName"+unique);
		employeesEntity.setFirstName("firstName"+unique);
		employeesEntity.setTitle("title"+unique);
		employeesEntity.setTitleOfCourtesy("titleOfCourtesy"+unique);
		employeesEntity.setBirthDate(LocalDateTime.now());
		employeesEntity.setHireDate(LocalDateTime.now());
		employeesEntity.setAddress("address"+unique);
		employeesEntity.setCity("city"+unique);
		employeesEntity.setRegion("region"+unique);
		employeesEntity.setPostalCode("postalCode"+unique);
		employeesEntity.setCountry("country"+unique);
		employeesEntity.setHomePhone("homePhone"+unique);
		employeesEntity.setExtension("extension"+unique);
		employeesEntity.setPhoto(new String("photo"+unique).getBytes());
		employeesEntity.setNotes("notes"+unique);
		employeesEntity.setPhotoPath("photoPath"+unique);
		employeesEntity.setEmployeesReportsTo(employeesReportsToEntity);
          	entityManager.persist(employeesEntity);
            return employeesEntity;
        });
	}
	
	protected EmployeesEntity findEmployeesEntityByPK(Integer employeeId) {
	    
		return transaction(() -> entityManager.find(EmployeesEntity.class, employeeId));
	}
  
  
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be greater than 0");
        StringBuilder randomString = new StringBuilder(length);
        
        for (int i = 0; i < length-10; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(randomIndex));
        }
        
        return randomString.toString();
    }

}
