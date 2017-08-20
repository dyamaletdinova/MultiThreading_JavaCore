/**
 * 
 */
package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Diana Yamaletdinova
 *
 * Aug 18, 2017
 */
public class Demo {

	public static int threadCount = 4; // imaginedprocessors
	public static int stop = 200000;
	
	public static void main(String[] args) {
		//each thread is able to put something in and out of the box, and isPrime is a black box
		System.out.println("Spawning threads...");
		
		List<Thread> threads = new ArrayList<>();		
		int incrAmnt = stop/threadCount;
		int starting = 1; 
		for (int i = 0; i <threadCount; i++){
			//for every thread but the final one
			if (!((i+1) == threadCount)){ // if not the final thread
				//adding a new object by creating it from a PrimeThread obj
				//which you put inside of the thread, and than add it to the thread list
				threads.add(new Thread(new PrimeThread(starting, starting+incrAmnt, i + ".txt", false)));
				starting += incrAmnt;
			}else{//if last one => true
				threads.add(new Thread(new PrimeThread(starting, starting+incrAmnt, i + ".txt", true)));
			}
		}
		
		//start threads (4 in my case)
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).start();
		}
		
		//once you start all 4 threads, you have to join them => wait until the threads are done first
		for (int i = 0; i < threads.size(); i++) {		
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		List<Integer> primes = new ArrayList<>();//the list of all prime numbers we found
		
		//since we had as many files created with primes as # of threads (imagined processors),
		//we need to pull the info from the files, and concat all this into one file
		//first we need to get all the prime numbers from all the 4 threads and add them all to one list
		for (int i = 0; i < threads.size(); i++) {
			//for each thread that made a file(4 total files), pull the data out of them and add primes to the list
			try {
				File f = new File(i+".txt");
				Scanner scan = new Scanner(new File(i+".txt"));
				while(scan.hasNextInt()){
					primes.add(scan.nextInt());
				}
				scan.close();
				f.delete();//after getting all the data, delete the files
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		//add all the prime numbers form the list above to the main primes.txt file
		try {
			PrintWriter print = new PrintWriter(new File("primes.txt"));
			for (int i = 0; i < primes.size(); i++) {
				print.println(primes.get(i));
			}
			print.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
