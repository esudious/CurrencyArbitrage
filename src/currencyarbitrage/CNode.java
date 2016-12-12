package currencyarbitrage;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Random;
/**
 *
 * @author Jeremy M
 */
public class CNode{
    final String ISO;
    public HashMap<CNode, Double> exchanges;
    public static ArrayList<CNode> CNodeList;
    double rate;
    public static TreeMap<Double, ArrayList> value_exch;
    
    
    CNode(String cur){
        ISO = cur;
        exchanges = new HashMap();
        if (CNodeList==null) {
            CNodeList = new ArrayList<>();
        }
        if (value_exch==null){
            value_exch = new TreeMap<>();
        }
        CNodeList.add(this);
    }
    
    /*Add a exchange on this node and the exchange node with inverse
    * exchange rate
    */
    public void addExchange(CNode iso, double r){
        exchanges.put(iso, r);
        iso.exchanges.put(this, 1/r);
    }
    
    public TreeMap<Double, ArrayList> getTradeValues(){
        return value_exch;
    }
    
    /*
        Get exchange rate between 2 currencies
    */
    
    public static double getExchangeRate(CNode a, CNode b){
        if (a.equals(b)) return 1;
        double xr = a.exchanges.get(b);
        return xr;
    }
    
    public double getExchangeRate(CNode b){
        if (this.equals(b)) return 1;
        double xr = this.exchanges.get(b);
        return xr;
    }
    
    public CNode getCNode(String i){
        if (i.equals(ISO)){
            return this;
        } else {
            return null;
        }
    }
    
    @Override
    public String toString(){
        return ISO;
    }
    
    /* Create Exchange Paths with a cycle that ends where it start
     * Takes an int for nodes to traverse
     * Create a list of nodes traversed
    */ 
    public void exchangePath(CNode start, int n, 
            double trade, ArrayList<CNode> lst){
        
        if (lst == null) lst = new ArrayList<>();
        ArrayList<CNode> rlst = (ArrayList<CNode>) lst.clone();
        rlst.add(this);
        
        if (n <= -1){
            value_exch.put(trade, rlst);
            //System.out.println(Double.toString(trade) + rlst);
        }
        
        //last step return to start
        if (n == 0){
            trade = trade * getExchangeRate(this, start);
            start.exchangePath(start, n-1, trade, rlst);
        } else if (n > 0){
            
            for(CNode cn:CNodeList){
                //nodes not visited yet.
                
                if (!rlst.contains(cn)){
                    double temptrade = trade;
                    temptrade = temptrade * getExchangeRate(this, cn);
                    cn.exchangePath(start, n-1, temptrade, rlst);
                }
            }
            
        } 
    }
    
    public static double singlePathRate(ArrayList<CNode> cnlist, double trade){
        return trade *= getChainedExchangeRate(cnlist);
    }
    
    /* 
       A random path either created from a chosen start or if 
        start is null a random starting point.  Creates a path until
        a cycle is created.
    
    */
    
    public static ArrayList<CNode> createRandomPath(CNode start, ArrayList<CNode> cnlist){    
        Random r = new Random();
        if (start==null){
            start = CNodeList.get(r.nextInt(CNodeList.size()));
        }
        if (cnlist==null){
            cnlist = new ArrayList<>();
            cnlist.add(start);
        }
        
        CNode next = CNodeList.get(r.nextInt(CNodeList.size()));
        cnlist.add(next);
        if(next.equals(start)){
            return cnlist;
        } else{
            createRandomPath(start, cnlist);
        }
        return cnlist;
    }
    
    public static double getChainedExchangeRate(ArrayList<CNode> cnlist){
        double ex=1;
        for(int i = 0; i < cnlist.size()-1;i++){
            ex *= getExchangeRate(cnlist.get(i), cnlist.get(i+1));
        }
        return ex;
    }

}
