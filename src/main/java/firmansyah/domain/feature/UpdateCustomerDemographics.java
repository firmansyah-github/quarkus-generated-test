// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.domain.model.customerDemographics.UpdateCustomerDemographicsInput;


public interface UpdateCustomerDemographics {
	CustomerDemographics handle(UpdateCustomerDemographicsInput updateCustomerDemographicsInput);
}
