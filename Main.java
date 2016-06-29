package main;

/** Main
 * 
 * Description: Deletes duplicate values in a given database and collection
 */

import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class Main {
	public static final String DATABASE = "testDatabase";
	public static final String COLLECTION = "testActivity";
	public static final String DUPLICATE_TAG = "title";
	
	public static void main(String[] args) {
		//Get The Collection as an Iterator
		MongoClient client = new MongoClient();
		MongoCollection collection = client.getDatabase(DATABASE).getCollection(COLLECTION);
		FindIterable<Document> iterable = collection.find();
		
		//Add To Found Terms or delete duplicate
		Set<String> foundTerms = new HashSet<String>();
		
		//Loop Through Iterator
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	String value = document.getString(DUPLICATE_TAG);
		        if (foundTerms.contains(value)) {
		        	collection.deleteOne(document);
		        } else {
		        	foundTerms.add(value);
		        }
		    }
		});	
		
		client.close();
	}
}
