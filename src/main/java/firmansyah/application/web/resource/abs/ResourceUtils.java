// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.application.web.resource.abs;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import jakarta.ws.rs.core.SecurityContext;

import firmansyah.domain.exception.FilterConjunctionNotValidException;
import firmansyah.domain.exception.FilterOperatorNotValidException;
import firmansyah.domain.exception.FilterValueNotValidException;


public abstract class ResourceUtils {

	private static final int DEFAULT_LIMIT = 20;
	private static List<String> lsConjunction = new ArrayList<String>();
	private static List<String> lsOperator = new ArrayList<String>();
	private List<String> lsField = new ArrayList<String>();
	
		
	static {
		
		lsConjunction.add("AND");
		lsConjunction.add("OR");
		
		lsOperator.add("EQ");
		lsOperator.add("NEQ");
		lsOperator.add("GT");
		lsOperator.add("GTE");
		lsOperator.add("LT");
		lsOperator.add("LTE");
		lsOperator.add("LIKE");
		lsOperator.add("NLIKE");
	}

	public UUID getLoggedUserId(SecurityContext securityContext) {
		Principal principal = securityContext.getUserPrincipal();
		return principal != null ? UUID.fromString(principal.getName()) : null;
	}

	public int getLimit(int limit) {
		return limit > 0 ? limit : DEFAULT_LIMIT;
	}
	
	public List<FilterCondition> parseFilterConditions(String filter, String conjunction) {
        List<FilterCondition> conditions = new ArrayList<>();
        List<String> operators = parseConjunctionOperators(conjunction);
        if (filter != null && !filter.isEmpty()) {
            String[] conditionStrings = filter.split("\\|\\|");
            int i = 0;
            for (String conditionString : conditionStrings) {
                String[] parts = conditionString.split("\\|");
                if (parts.length == 3) {
                    FilterCondition condition = new FilterCondition();
                    condition.setField(validateField(parts[0]));
                    condition.setFieldSQL(transformToSqlField(parts[0]));
                    condition.setOperator(validateOperator(parts[1]));
                    condition.setOperatorSQL(transformToSqlOperator(parts[1].toUpperCase().trim()));
                    condition.setValue(validateValue(parts[2]));
                    if(operators.size()>i) {
                    	condition.setConjunction(validateConjunction(operators.get(i)));
                    } else {
                    	condition.setConjunction("AND");
                    }
                    
                    if(conditionStrings.length-1==i) {
                    	condition.setConjunction("");
                    }
                    conditions.add(condition);
                }
                ++i;
            }
        }
        return conditions;
    }
    
    protected abstract String transformToSqlField(String string);

	private String transformToSqlOperator(String string) {
		switch (string) {
			case "EQ":
				return "=";
			case "NEQ":
				return "!=";
			case "GT":
				return ">";
			case "GTE":
				return ">=";
			case "LT":
				return "<";
			case "LTE":
				return "<=";
			case "LIKE":
				return "LIKE";
			case "NLIKE":
				return "NOT LIKE";

			default:
				return "=";
		}
	}

	private String validateConjunction(String string) {
		if(lsConjunction.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterConjunctionNotValidException();
		}
	}

	private String validateValue(String string) {
		// Implement your validation logic here to ensure the input is safe
        // You can use regex or other custom rules to check for potential SQL injection patterns
        // Return true if the input is deemed safe, or false if it's considered potentially unsafe
        // Note that this is a simplified example and may not cover all possible cases
        // return !string.matches(".*[;\"'\\)].*");"[;\"'\\)]" matches("[;\"'\\)]")
		String normalizedInput = normalizeInput(string);
		if (isSafe(normalizedInput)) {
			return string.trim();
        } else {
        	throw new FilterValueNotValidException();
        }
	}
	
	private String normalizeInput(String input) {
        return Normalizer.normalize(input, Form.NFC); // NFC: Normalization Form C
    }
	
	private boolean isSafe(String input) {
        // Perform your safety checks here
        return !input.contains(";") && !input.contains("\"") && !input.contains("'") && !input.contains(")") && !input.contains("\\");
    }

	private String validateOperator(String string) {
		if(lsOperator.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterOperatorNotValidException();
		}
	}

	protected abstract String validateField(String string);

	private List<String> parseConjunctionOperators(String conjunctions) {
        List<String> operators = new ArrayList<>();
        if (conjunctions != null && !conjunctions.isEmpty()) {
            operators = Arrays.asList(conjunctions.split(","));
        }
        return operators;
    }

	public List<SortCondition> parseSortConditions(String sort) {
        List<SortCondition> conditions = new ArrayList<>();
        if (sort != null && !sort.isEmpty()) {
            String[] sortStrings = sort.split(",");
            for (String sortString : sortStrings) {
                boolean descending = sortString.startsWith("-");
                String field = descending ? sortString.substring(1) : sortString;
                SortCondition condition = new SortCondition();
                condition.setField(validateField(field));
                condition.setFieldSQL(transformToSqlField(field));
                condition.setDescending(descending);
                conditions.add(condition);
            }
        }
        return conditions;
    }
}