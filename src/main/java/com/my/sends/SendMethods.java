package com.my.sends;

import java.util.ArrayList;
import java.util.List;

public class SendMethods  {
    public class  CenyWysylki{
        String kraj;
        double cena;
        public double getCena() {return cena;}
        public void setCena(double a) {this.cena = a;}
        public String getKraj() {return kraj;}
        public void setKraj(String a) {this.kraj = a;}
    }
    public interface SendStrategy {
        public List<CenyWysylki> records = new ArrayList<CenyWysylki>();
        public double obliczCeneWysylki(String kraj,double cenaEkonomiczna);
        public String ustawStatusWmagazynie();
    }
    public class Osobista implements SendStrategy {
        public double obliczCeneWysylki(String kraj,double cenaEkonomiczna){
            return 0;
        }
        public String ustawStatusWmagazynie(){
            return "osobista";
        }
    }
    public class Ekonomincza implements SendStrategy{
        public double obliczCeneWysylki(String kraj,double cenaEkonomiczna){
            double ret = -1;
            for(int i=0;i<records.size() ;i++){
                if(records.get(i).kraj == kraj){
                       ret = records.get(i).cena;
                }
            }
            ret += cenaEkonomiczna;
            return ret;
        }
        public String ustawStatusWmagazynie(){
            return "ekonomiczna";
        }
    }
    public class Ekspresowa implements SendStrategy{
        public double obliczCeneWysylki(String kraj,double cenaEkspresowa){
            double ret = -1;
            for(int i=0;i<records.size() ;i++){
                if(records.get(i).kraj == kraj){
                    ret = records.get(i).cena;
                }
            }
            ret += cenaEkspresowa;
            return ret;
        }
        public String ustawStatusWmagazynie(){
            return "ekspresowa";
        }
    }
    public class Context{
        private SendStrategy strategy;
        /*
        WYwolanie w innej klasie
              Context context = new Context(new obliczCeneWysylki());
                System.out.println(context.executeStrategy_obliczCeneWysylki("Polska", 2.5));
                System.out.println(context.executeStrategy_ustawStatusWmagazynie());
         */
        public Context(SendStrategy strategy){
            this.strategy = strategy;
        }

        public void doKrajList(){
            // Tworzymy liste karjów do wysyłki
            CenyWysylki ad = new CenyWysylki();
            ad.setCena(1.5);
            ad.setKraj("Polska");
            strategy.records.add(ad);
            ad = new CenyWysylki();
            ad.setCena(0.75);
            ad.setKraj("Romunia");
            strategy.records.add(ad);
            ad.setCena(1.05);
            ad.setKraj("Rosja");
            strategy.records.add(ad);
        }
        public void printList( List<CenyWysylki> records){
            for(int i=0;i<records.size();i++){
                System.out.println(records.get(i).kraj + " " + records.get(i).cena );
            }
        }

        public double executeStrategy_obliczCeneWysylki(String kraj,double cena){
            return strategy.obliczCeneWysylki(kraj,cena);
        }
        public String executeStrategy_ustawStatusWmagazynie(){
            return strategy.ustawStatusWmagazynie();
        }
    }
}
