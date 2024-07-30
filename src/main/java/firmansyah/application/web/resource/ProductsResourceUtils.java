// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.products.Products;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class ProductsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("PRODUCTID");
		lsField.add("PRODUCTNAME");
		lsField.add("SUPPLIERID");
		lsField.add("CATEGORYID");
		lsField.add("QUANTITYPERUNIT");
		lsField.add("UNITPRICE");
		lsField.add("UNITSINSTOCK");
		lsField.add("UNITSONORDER");
		lsField.add("REORDERLEVEL");
		lsField.add("DISCONTINUED");
	}

	private final FindCategoriesByPrimaryKey findCategoriesCategoryIdByPrimaryKey;
	private final FindSuppliersByPrimaryKey findSuppliersSupplierIdByPrimaryKey;
	
  
	public ProductsResponse productsResponse(Products products) {
		final var categoriesCategoryId =findCategoriesCategoryIdByPrimaryKey.handle(products.getCategoriesCategoryId().getCategoryId());
		final var suppliersSupplierId =findSuppliersSupplierIdByPrimaryKey.handle(products.getSuppliersSupplierId().getSupplierId());
		final var productsResponse = new ProductsResponse(products ,
										new CategoriesResponse(true, categoriesCategoryId),
										new SuppliersResponse(true, suppliersSupplierId) );
		
        return productsResponse;
	}

	public ProductsListResponse productsResponse(PageResult<Products> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(products -> productsResponse(products))
				.collect(Collectors.toList());
		return new ProductsListResponse(resultResponse, pageResult.getTotal());
	}
	
	@Override
	protected String validateField(String string) {
		if(lsField.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterFieldNotValidException();
		}
	}
	
	@Override
	protected String transformToSqlField(String string) {
		switch (string) {
			
		    case "productId":
				return "productId";
			
		    case "productName":
				return "productName";
			
			case "supplierId":
				return "suppliersSupplierId.supplierId";	
			
			case "categoryId":
				return "categoriesCategoryId.categoryId";	
			
		    case "quantityPerUnit":
				return "quantityPerUnit";
			
		    case "unitPrice":
				return "unitPrice";
			
		    case "unitsInStock":
				return "unitsInStock";
			
		    case "unitsOnOrder":
				return "unitsOnOrder";
			
		    case "reorderLevel":
				return "reorderLevel";
			
		    case "discontinued":
				return "discontinued";
			default:
				return "";
		}
	}
}
