package currencyarbitrage;

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.File;
import java.util.Scanner;

/**
 * @author Jeremy M
 */
public class CurrencyArbitrage {

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
    int currencies;

    CurrencyArbitrage() {
        cur_list = new ArrayList<>();
        importExchanges();
        
        //addExchanges();
        genAllTrades();
        createRandomPaths(25);
        
    }

    void genAllTrades() {
        for (CNode c : cur_list) {
            c.exchangePath(c, 7, 1000, null);
        }
        val_ex = CNode.value_exch;
        for (Double d : val_ex.keySet()) {
            System.out.printf("%f %s \n", d, val_ex.get(d));
        }
        System.out.println(val_ex.size());
    }

    void createRandomPaths(int n) {
        for (int i = 0; i < n; i++) {
            path1 = CNode.createRandomPath(null, null);

            System.out.println(path1.toString());
            System.out.println(CNode.singlePathRate(path1, 1000));
        }
    }

    private void importExchanges() {
        Scanner in;
        String line;
        String[] exchanges;
        CNode cn1 = null;
        CNode cn2 = null;
        try {
            in = new Scanner(new File("exchangeRatesF2016.txt"));
        } catch (Exception e) {
            System.out.println(e);
            in = new Scanner(System.in);
        }
        do {
            line = in.nextLine();
        } while (!line.contains("*"));

        currencies = Integer.parseInt(in.nextLine());

        while (in.hasNext()) {
            exchanges = in.nextLine().split("\\s+", 3);
            double rate = Double.parseDouble(exchanges[2].trim());

            cn1 = getCNodeFromString(exchanges[0]);
            cn2 = getCNodeFromString(exchanges[1]);            
            if(!cur_list.contains(cn1)) cur_list.add(cn1);
            if(!cur_list.contains(cn2)) cur_list.add(cn2);
            
            cn1.setExchangeRate(cn2, rate);
        }
    }
    
    public CNode getCNodeFromString(String s){
        if (CNode.CNodeList==null){
            return new CNode(s);
        }
        if (CNode.CurrencyList.contains(s)){
            for(CNode c:CNode.CNodeList){
                if (s.equals(c.getCurrency())) return c;
            }
        } else {
            return new CNode(s);
        }
        return null;
    }

    void addExchanges() {
        CAN = new CNode("CAN"); //Canadian Dollar
        EUR = new CNode("EUR"); //Euro
        JPY = new CNode("JPY"); //Japan Yen
        NOK = new CNode("NOK"); //Norway Krone
        SEK = new CNode("SEK"); //Swedish Krona
        RUB = new CNode("RUB"); //Russian Ruble
        AUD = new CNode("AUD"); //Australian Kangaroo Buck
        GBP = new CNode("GBP"); //UK Pound
        USD = new CNode("USD"); //USA Dollar

        empty_list = new ArrayList();

        CAN.setExchangeRate(EUR, .68);
        CAN.setExchangeRate(JPY, 78.926);
        CAN.setExchangeRate(NOK, 6.205);
        CAN.setExchangeRate(SEK, 6.707);
        CAN.setExchangeRate(RUB, 48.45);
        CAN.setExchangeRate(GBP, .59);
        CAN.setExchangeRate(USD, .704);
        CAN.setExchangeRate(AUD, .978);

        EUR.setExchangeRate(JPY, 116.021);
        EUR.setExchangeRate(NOK, 9.129);
        EUR.setExchangeRate(SEK, 9.869);
        EUR.setExchangeRate(RUB, 71.284);
        EUR.setExchangeRate(GBP, .869);
        EUR.setExchangeRate(USD, 1.090);
        EUR.setExchangeRate(AUD, 1.440);

        JPY.setExchangeRate(NOK, .079);
        JPY.setExchangeRate(SEK, .085);
        JPY.setExchangeRate(RUB, .615);
        JPY.setExchangeRate(GBP, .007);
        JPY.setExchangeRate(USD, .009);
        JPY.setExchangeRate(AUD, .012);

        NOK.setExchangeRate(SEK, 1.081);
        NOK.setExchangeRate(RUB, 7.807);
        NOK.setExchangeRate(GBP, .095);
        NOK.setExchangeRate(USD, .119);
        NOK.setExchangeRate(AUD, .171);

        SEK.setExchangeRate(RUB, 7.225);
        SEK.setExchangeRate(GBP, .088);
        SEK.setExchangeRate(USD, .111);
        SEK.setExchangeRate(AUD, .146);

        RUB.setExchangeRate(GBP, .012);
        RUB.setExchangeRate(USD, .015);
        RUB.setExchangeRate(AUD, .020);

        AUD.setExchangeRate(GBP, .604);
        AUD.setExchangeRate(USD, .757);

        GBP.setExchangeRate(USD, 1.254);

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
