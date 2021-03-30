import java.lang.*;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MerkleHashTree{
    public static void main(String args[]){
        // list with even number of files
        ArrayList<String> list = new ArrayList<String>();
        list.add("firstfile.txt");
        list.add("secondfile.txt");
        list.add("thirdfile.txt");
        list.add("lastfile.txt");
        MerkleHashTree tree = new MerkleHashTree();
        System.out.print("Even # of files: " + "\n" + tree.Hash(list) + "\n");
        
        // list with odd number of files
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("first.txt");
        list2.add("second.txt");
        list2.add("third.txt");
        MerkleHashTree tree2 = new MerkleHashTree();
        System.out.print("Odd # of files: " + "\n" + tree2.Hash(list2));
    }
    
    // this method converts each filename into its own unique hash
    public String Hash(ArrayList<String> filelist){
        // a new list for holding the file hashes
        ArrayList<String> hashlist = new ArrayList<String>();
        
        for(int i = 0; i < filelist.size(); i++){
            try{
                // converts the file to a hex string
                MessageDigest file = MessageDigest.getInstance("SHA-1");
                byte[] hash = file.digest(filelist.get(i).getBytes());
                BigInteger num = new BigInteger(1, hash);
                String hex = num.toString(16);
                
                // makes sure the hash string is 32 bits
                while(hex.length() < 32){
                    hex = "0" + hex;
                }
                
                hashlist.add(hex);
            }
            catch (NoSuchAlgorithmException e){
                throw new RuntimeException(e);
            }
        }
        
        return topHash(hashlist);
    }
    
    // calculates the top hash
    private String topHash(ArrayList<String> hashfiles){
        // a new list for holding the sum of the hashes
        ArrayList<String> hashsums = new ArrayList<String>();
        
        // checks if the number of hashed files is even or odd
        if (hashfiles.size() % 2 == 0){
            for (int i = 0; i < hashfiles.size(); i = i + 2){
                String hashsum = hashfiles.get(i).concat(hashfiles.get(i + 1));
                hashsums.add(hashsum);
            }
        }
        else{
            for (int i = 0; i + 1 < hashfiles.size(); i = i + 2){
                String hashsum = hashfiles.get(i).concat(hashfiles.get(i + 1));
                hashsums.add(hashsum);
            }
            
            hashsums.add(hashfiles.get(hashfiles.size() - 1));
        }
        
        String top = evenOrOdd(hashsums);
        return top;
    }
    
    // determines whether the list of file names is even or odd
    private String evenOrOdd(ArrayList<String> items){
        String sum = "";
        
        if (items.size() % 2 == 0){
            sum = items.get(0).concat(items.get(1));
        }
        else{
            sum = items.get(0).concat(items.get(1));
            sum = sum.concat(items.get(2));
        }
        
        return sum;
    }
}
