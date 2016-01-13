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
        @Override
        public double obliczCeneWysylki(String kraj,double cenaEkonomiczna){
            return 0;
        }
        @Override
        public String ustawStatusWmagazynie(){
            return "osobista";
        }
    }
    public class Ekonomincza implements SendStrategy{
        @Override
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
        @Override
        public String ustawStatusWmagazynie(){
            return "ekonomiczna";
        }
    }
    public class Ekspresowa implements SendStrategy{
        @Override
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
        @Override
        public String ustawStatusWmagazynie(){
            return "ekspresowa";
        }
    }
    public class Context{
        private SendStrategy strategy;
        /*
        //WYwolanie w innej klasie
              Context context = new Context(new Osobista());
                System.out.println(context.executeStrategy_obliczCeneWysylki("Polska", 2.5));
              Context context = new Context(new Osobista());
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
    public CenaWysylki countSendPrice(String krajDoKtoregoWysylamy, String metodaWysylki, double CenaEkonomiczna, double CenaEkspresowa ) {
        CenaWysylki ret = new CenaWysylki();
        ret.cena=-1;
        ret.status="Brak tego rodzaju strategii";
        if(metodaWysylki=="osobista") {
            SendMethods.Context context = new SendMethods.Context(new SendMethods.Osobista());
            ret.cena = context.executeStrategy_obliczCeneWysylki(krajDoKtoregoWysylamy, 0);
            System.out.println(ret.cena);
            context = new SendMethods.Context(new SendMethods.Osobista());
            ret.status = context.executeStrategy_ustawStatusWmagazynie();
            System.out.println(ret.status);
            return ret;
        }
        if(metodaWysylki=="ekonomiczna") {
            SendMethods.Context context = new SendMethods.Context(new SendMethods.Ekonomincza());
            ret.cena = context.executeStrategy_obliczCeneWysylki(krajDoKtoregoWysylamy, CenaEkonomiczna);
            System.out.println(ret.cena);
            context = new SendMethods.Context(new SendMethods.Ekonomincza());
            ret.status = context.executeStrategy_ustawStatusWmagazynie();
            System.out.println(ret.status);
            return ret;
        }
        if(metodaWysylki=="ekspresowa") {
            SendMethods.Context context = new SendMethods.Context(new SendMethods.Ekspresowa());
            ret.cena = context.executeStrategy_obliczCeneWysylki(krajDoKtoregoWysylamy, CenaEkspresowa);
            System.out.println(ret.cena);
            context = new SendMethods.Context(new SendMethods.Ekspresowa());
            ret.status = context.executeStrategy_ustawStatusWmagazynie();
            System.out.println(ret.status);
            return ret;
        }
        return ret;
    }
}
