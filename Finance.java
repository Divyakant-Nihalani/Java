import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Finance {
	
	//credit
	static int credit=(Integer) 40000;
		
	//financed invoice
	static HashMap<String,Integer> financed_invoice=new HashMap<String,Integer>();
	
		
	public void payBills(long invoice_amount, String invoice_number) {
		
		//finance invoices
		if (invoice_amount <= credit) {
			// paying complete amount of bills
			financed_invoice.put(invoice_number, (int) invoice_amount);
			credit = (int) (credit - invoice_amount);
		} else {
			if (credit != 0) {
				// paying only amount which is left
				financed_invoice.put(invoice_number, credit);
				credit = (Integer) 0;
			} else {
				financed_invoice.put(invoice_number, (Integer) 0);
			}
		}

	}
	
	
	public void currentStatus() {

		System.out.println("Financed amount of each invoice."+"\n");
		
		//Displaying data from financed_invoice hash map.
		for (Map.Entry m : financed_invoice.entrySet())
			System.out.println(m.getKey() + " " + m.getValue());

		System.out.println("\n"+"Remaining credit is " + credit + "." );

	}
	
	

	public static void main(String args[]) {

		Finance calculate = new Finance();

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try {
			
			// Read JSON file.
			Object read_file = jsonParser.parse(new FileReader("invoices.json"));
			
			// Storing JSON data into array.
			JSONArray employeeList = (JSONArray) read_file;
			
			//Creating JSON object to get data from every class.
			JSONObject jsonObj = null;

			//To traverse all the data.
			for (int i = 0; i < employeeList.size(); i++) {
				
				//Fetching "amount" and "Number" from JSON file.
				jsonObj = (JSONObject) employeeList.get(i);
				
				//Pass values to payBill method.
				calculate.payBills((long) jsonObj.get("amount"), (String) jsonObj.get("number"));
			}

		} catch (FileNotFoundException e) {
			System.out.println("Json file not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Given Json file is inappropriate.");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Exception in parisng Json file.");
			e.printStackTrace();
		}

		calculate.currentStatus();

	}
	   
}
