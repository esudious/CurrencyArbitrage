package currencyarbitrage;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author Jeremy M
 */
public class CurrencyArbitrage{
    
    public static void main(String[] args) {
        CurrencyArbitrage ca = new CurrencyArbitrage();
    }
    
    CNode CAN; //Canadian Dollar
    CNode EUR; //Euro
    CNode JPY; //Japan Yen
    CNode NOK; //Norway Krone
    CNode SEK; //Swedish Krona
    CNode RUB; //Russian Ruble
    CNode AUD; //Australian Kangaroo Buck
    CNode GBP; //UK Pound
    CNode USD;
    public ArrayList<Double> empty_list;
    public ArrayList<CNode> cur_list;
    public ArrayList<CNode> path1;
    public TreeMap<Double, ArrayList> val_ex;
    
    CurrencyArbitrage(){
        addExchanges();
        
        genAllTrades();
        
        //createRandomPaths();
        
    }
    
    void genAllTrades(){
        for(CNode c: cur_list){
            c.exchangePath(c, 2, 1000, null);
        }
        val_ex = CNode.value_exch;
        for(Double d: val_ex.keySet()){
           System.out.printf("%f %s \n", d, val_ex.get(d));
        }
        System.out.println(val_ex.size());
    }
    
    void createRandomPaths(){
        for(int i = 0;i<50;i++){
            path1 = CNode.createRandomPath(null, null);
        
            System.out.println(path1.toString());
            System.out.println(CNode.singlePathRate(path1, 1000));
        }
    }
    
    void addExchanges(){
        CAN = new CNode("CAN"); //Canadian Dollar
        EUR = new CNode("EUR"); //Euro
        JPY = new CNode("JPY"); //Japan Yen
        NOK = new CNode("NOK"); //Norway Krone
        SEK = new CNode("SEK"); //Swedish Krona
        RUB = new CNode("RUB"); //Russian Ruble
        AUD = new CNode("AUD"); //Australian Kangaroo Buck
        GBP = new CNode("GBP"); //UK Pound
        USD = new CNode("USD"); //USA Dollar
        
        cur_list = new ArrayList<>();
        empty_list = new ArrayList();
        
        CAN.addExchange(EUR, .68);
        CAN.addExchange(JPY, 78.926);
        CAN.addExchange(NOK, 6.205);
        CAN.addExchange(SEK, 6.707);
        CAN.addExchange(RUB, 48.45);
        CAN.addExchange(GBP, .59);
        CAN.addExchange(USD, .704);
        CAN.addExchange(AUD, .978);
        
        EUR.addExchange(JPY, 116.021);
        EUR.addExchange(NOK, 9.129);
        EUR.addExchange(SEK, 9.869);
        EUR.addExchange(RUB, 71.284);
        EUR.addExchange(GBP, .869);
        EUR.addExchange(USD, 1.090);
        EUR.addExchange(AUD, 1.440);
        
        JPY.addExchange(NOK, .079);
        JPY.addExchange(SEK, .085);
        JPY.addExchange(RUB, .615);
        JPY.addExchange(GBP, .007);
        JPY.addExchange(USD, .009);
        JPY.addExchange(AUD, .012);
        
        NOK.addExchange(SEK, 1.081);
        NOK.addExchange(RUB, 7.807);
        NOK.addExchange(GBP, .095);
        NOK.addExchange(USD, .119);
        NOK.addExchange(AUD, .171);
        
        SEK.addExchange(RUB, 7.225);
        SEK.addExchange(GBP, .088);
        SEK.addExchange(USD, .111);
        SEK.addExchange(AUD, .146);
        
        RUB.addExchange(GBP, .012);
        RUB.addExchange(USD, .015);
        RUB.addExchange(AUD, .020);
        
        AUD.addExchange(GBP, .604);
        AUD.addExchange(USD, .757);
        
        GBP.addExchange(USD, 1.254);
        
        cur_list.add(CAN);
        cur_list.add(EUR);
        cur_list.add(JPY);
        cur_list.add(NOK);
        cur_list.add(SEK);
        cur_list.add(RUB);
        cur_list.add(AUD);
        cur_list.add(GBP);
        cur_list.add(USD);
    }

    
    
}
