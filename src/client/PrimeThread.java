/**
 * 
 */
package client;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Diana Yamaletdinova
 *
 * Aug 18, 2017
 */
public class PrimeThread implements Runnable{
	
	//class variables
	//if we do not put access modifiers, prevent them to be shared
	//package access by default, any in the package can access/modify
	int start, stop; 
	String file;
	boolean lead = false; //shows progress or not, biggest prime num processing thread
	
	@Override
	public void run() {
		
		List<Integer> listPrimes = new ArrayList<>();
		//starting at start and ending at stop
		for (int i = start; i <= stop; i+=2){
			if (isPrime(i)){
				listPrimes.add(i);
			}
			//if lead and 
			if (lead && (i+1)%10000 == 0){//i+1 makes it even, b/c i always will be odd if we do i+2
				System.out.println(i + " / " + stop);
			}
			
		}
		
		//return the values back to the thread
		try {
			PrintWriter print = new PrintWriter(new File(file));
			for(int i=0; i < listPrimes.size(); i++){
				print.println(listPrimes.get(i));
			}
			print.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public PrimeThread(int startVal, int stopVal, String name, boolean isLead){
		this.start = startVal;
		this.stop = stopVal;
		this.file = name;//saves the prime nums to the file 
		this.lead = isLead;
	}
	
	boolean isPrime(int n) {
	    //checking if n is a multiple of 2
	    if ( n%2 == 0 ) {
	    	return false;
	    }
	    //if it is not, then check the odds
	    for( int i=3; i*i <= n; i+=2 ) {
	        if( n%i == 0 ){
	        	 return false;
	        }       
	    }
	    return true;
	}

}
